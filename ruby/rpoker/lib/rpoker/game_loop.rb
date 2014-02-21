module Rpoker
  class GameLoop
    include Rpoker::Data

    attr_accessor :game, :interface, :observers, :delay

    def initialize(game, interface)
      @game = game
      @interface = interface

      # TODO:
      # @observers = []
    end

    def start
      interface.on_start_game(game)
      game.players.each { |player| interface.on_new_player player }

      unless game.enough_players?
        interface.on_error "Not enough players."
        return
      end

      deal        = nil
      state       = nil
      player_data = nil

      while (deal = game.next_deal)
        interface.on_deal_resolve game.last_deal if game.last_deal
        interface.on_new_deal deal

        while (state = deal.current_state)
          interface.on_new_state state, deal

          while (player_data = deal.current_player)
            player            = player_data.player
            player_full_state = deal.get_full_state player

            interface.on_player_turn player, player_full_state, deal

            choice            = get_player_choice player, player_full_state

            deal << choice

            interface.on_player_choice player, choice, deal
            deal.next_player

            # Pause after each choice if requested.
            sleep delay if delay
          end

          deal.next_state
        end
      end

      interface.on_deal_resolve game.current_deal
      interface.on_end_game(game)
    end

    def get_player_choice(player, player_state, input = {})
      choice = player.action(player_state, input)

      if choice.is_a? Choice
        choice
      elsif choice.is_a? Request::Input
        input[choice.key] = interface.on_player_request player, choice.message
        get_player_choice player, player_state, input
      else
        raise "Don't know how to handle: " + choice.to_s
      end
    end
  end
end
