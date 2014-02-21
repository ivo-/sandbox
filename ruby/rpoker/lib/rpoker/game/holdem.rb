module Rpoker::Game
  class Holdem
    include Rpoker::Data

    attr_accessor :deck, :finder
    attr_accessor :start_chips, :blind_chips

    attr_reader :deals, :players, :removed_players

    def initialize
      @deck, @finder = nil

      @deals = []
      @players = []
      @players_off = []

      @blind_chips = 5
      @start_chips = 2000
    end

    def first_deal?
      deals.length == 0
    end

    def enough_players?
      players.length > 1
    end

    def current_deal
      deals.last
    end

    def current_deal_finished?
      current_deal && current_deal.resolved?
    end

    def finish_current_deal
      return if not current_deal
      return if current_deal_finished?

      if current_deal.resolve get_deal_winners(current_deal)
        clear_dropped_players
        current_deal
      else
        raise RpokerError
          .new("Current deal cannot be resolved and you cannot move to new one.")
      end
    end

    def last_deal
      deals[-2]
    end

    def last_deal_finished?
      last_deal && last_deal.resolved?
    end

    def get_deal_winners(deal)
      data = deal.active_players.map do |player|
        {
          player: player,
          streak: finder.find(deal.board + player.pocket)
        }
      end

      max_streak = data
        .sort { |prev, nxt| nxt[:streak] <=> prev[:streak] }
        .first[:streak]

       data
        .select { |p| p[:streak] == max_streak }
        .map    { |p| p[:player].player }
    end

    def next_deal
      finish_current_deal if not current_deal_finished?
      return              if not enough_players?

      shuffle_deck
      players_data = new_players_data

      if first_deal?
        blind = move_blind rand(players.length)
      else
        blind = move_blind current_deal.blind
        copy_chips current_deal.players_data, players_data
      end

      set_blind players_data, blind

      deals << Deal.new(players_data, get_board)
      current_deal
    end

    def new_players_data
      players.each_with_index.map do |player, index|
        Player.new player, start_chips, 0, get_poket, false
      end
    end

    def clear_dropped_players
      return if first_deal?

      current_deal.players_data.each do |player_data|
        if player_data.chips == 0
          @players.delete player_data.player
          @players_off << player_data.player
        end
      end
    end

    def get_board
      [deck.pop, deck.pop, deck.pop, deck.burn.pop, deck.burn.pop]
    end

    def get_poket
      [deck.pop, deck.pop]
    end

    def move_blind(prev)
      blind = prev + 1
      blind = 0 if blind == players.length
      blind
    end

    def copy_chips(players_state_source, players_state_target)
      players_state_target.each do |target|
        players_state_source.each do |source|
          target.chips = source.chips if target.player == source.player
        end
      end
    end

    def set_blind(players_state, blind)
      players_state.each_with_index do |player_data, index|
        if blind == index && player_data.chips >= blind_chips
          player_data.blind = true
          player_data.plot += blind_chips
          player_data.chips -= blind_chips
          break
        end
      end
    end

    def shuffle_deck
      deck.merge.shuffle
    end
  end
end
