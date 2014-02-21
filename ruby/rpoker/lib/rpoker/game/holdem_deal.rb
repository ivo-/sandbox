class Rpoker::Game::Holdem
  class Deal
    include Rpoker::Data

    attr_reader :board, :actions, :cards, :states
    attr_reader :players_data, :winners

    def initialize(players_data, cards)
      @board = []
      @actions = []

      @cards = cards
      @players_data = players_data

      @turn_counter = 0
      @player_counter = blind

      @states = [:start, :flop, :turn, :river]
    end

    def blind
      players_data.each_with_index { |player, i| return i if player.blind }

      # NOTE: in case no player is markerd as blind player, let the
      # first one be it.
      0
    end

    def players_length
      @players_data.length
    end

    def combined_plot
      @players_data.inject(0) { |plot, nxt| plot += nxt.plot }
    end

    def max_plot
      @players_data.inject { |prev, nxt| prev.plot > nxt.plot ? prev : nxt }.plot
    end

    def active_players
      @players_data.select { |player| player.playing }
    end

    def inactive_players
      @players_data - active_players
    end

    def get_player_data(player)
      @players_data.select { |data| data.player == player }.first
    end

    # --------------------------
    # Players iterator
    # --------------------------

    def reset_turn_counter
      @turn_counter = 0
    end

    def no_more_turns?
      @turn_counter == players_length
    end

    def current_player
      players_data[@player_counter] unless no_more_turns?
    end

    def start_new_bets
      @turn_counter = -1
      @player_counter = blind - 1

      next_player
    end

    def next_player
      # NOTE: skip turns if there is only one active player.
      @turn_counter = players_length if active_players.length == 1
      @turn_counter += 1             unless no_more_turns?

      return if no_more_turns?

      @player_counter += 1
      @player_counter = 0 if @player_counter == players_length

      # NOTE: skip not active or all-in players turns
      if !current_player.playing || current_player.chips == 0
        next_player
      else
        current_player
      end
    end

    # --------------------------
    # Deals state iterator
    # --------------------------

    def current_state
      @states.first
    end

    def next_state
      prev = @states.shift

      if current_state
        case current_state
        when :flop
          @board.concat cards[0..2]
        when :turn
          @board << cards[3]
        when :river
          @board << cards[4]
        end

        @actions << Action::StateChange.new(prev, current_state)

        # NOTE: new state means new bets automatically
        start_new_bets
      end

      current_state
    end

    # --------------------------
    # Data states
    # --------------------------

    def get_oponents_state(player)
      @players_data
        .select { |data| data.player != player }
        .map    { |data| data.clone }
        .each   { |data| data.pocket = nil }
    end

    def get_full_state(player)
      State.new *[
                  current_state                 ,
                  get_player_data(player)       ,
                  get_oponents_state(player)    ,

                  board                         ,
                  get_possible_choices(player)  ,
                 ]
    end

    # --------------------------
    # Choices
    # --------------------------

    def valid_choice?(choice)
      get_possible_choices(current_player.player).include? choice
    end

    def get_possible_choices(player)
      choices           = []
      player_data       = get_player_data player
      max_plot_dist     = max_plot - player_data.plot

      return [] if player_data.chips == 0

      choices << Choice::Fold.new()
      choices << Choice::AllIn.new(player_data.chips)
      choices << Choice::Check.new() if max_plot_dist == 0

      if max_plot_dist < player_data.chips
        choices << Choice::Call.new(max_plot_dist) if max_plot > player_data.plot
        choices << Choice::Raise.new(max_plot_dist) # NOTE: to be increased
      end

      choices
    end

    def <<(choice)
      raise RpokerError.new("Unknown choice #{choice}.") unless choice.is_a? Choice
      raise RpokerError.new("Invalid choice.") unless valid_choice?(choice)
      raise RpokerError.new("Multiiple choices for #{current_player.player}") if
        @actions.last && @actions.last == current_player.player

      if choice.is_a? Choice::Raise
        if choice.chips > current_player.chips
          raise RpokerError.new("Maximum number of chips exceeded.")
        elsif choice.chips <= (current_player.plot - max_plot)
          raise RpokerError.new("Raise must be bigger than call.")
        else
          reset_turn_counter
        end
      elsif choice.is_a? Choice::Fold
        current_player.playing = false
      elsif choice.is_a? Choice::AllIn
        reset_turn_counter if (current_player.plot + choice.chips) > max_plot
      end

      current_player.plot += choice.chips
      current_player.chips -= choice.chips

      # NOTE: clone player data in action, to keep current state.
      @actions << Action::PlayerChoice.new(current_player.player.clone, choice)

      self
    end

    # --------------------------
    # Resoving winner
    # --------------------------

    def resolved?
      !!@winners
    end

    def resolve(players)
      # Deal can be resolved only on last state and with one of the
      # active players remaining.

      return @winners if resolved?
      return if current_state

      players = [players] if not players.is_a? Array

      winner_players_data = players.map { |player| get_player_data player }
      winner_players_data.each do |player_data|
        unless active_players.include?(player_data)
          raise RpokerError("#{player_data.player} cannot be winner.")
        end
      end

      all_plot = combined_plot
      winners_plot =  winner_players_data.inject(0) { |prev, nxt| prev + nxt.plot }

      # It is possible that all-in player with smaller than maximum
      # plot is the winner. In this case difference between his plot
      # and plots of loosers bigger than his should be substracted and
      # returned to loosers chips.

      max_winners_plot = winner_players_data.inject do |prev, nxt|
        prev.plot > nxt.plot ? prev : nxt
      end.plot

      if max_winners_plot < max_plot
        loosers = active_players - winner_players_data

        loosers.each do |looser|
          if looser.plot > max_winners_plot
            plots_difference = looser.plot - max_winners_plot

            looser.chips += plots_difference
            all_plot     -= plots_difference
          end
        end
      end

      @winners = winner_players_data.each do |player_data|
        # Make sure every winner will get part of the combined plot,
        # that equals his part in winners plot. Considering that some
        # all-in players may have smaller plot than not all-in players.
        partition = player_data.plot/winners_plot
        player_data.chips += partition * all_plot
      end
    end
  end
end
