module Rpoker::Interface
  class CliDebug
    def out(message)
      puts message
    end

    def get(message = nil)
      out message if message
      gets.chomp
    end

    def delimiter(char = "=")
      out char * 20
    end

    def on_start_game(game)
      out "Start Game"
      delimiter
    end

    def on_end_game(game)
      out "Winner: " + game.players.first.to_s
      delimiter

      out "End Game"
      delimiter
    end

    def on_deal_resolve(deal)
      delimiter "."
      out "Deal winners: #{deal.winners}"
      delimiter "."
    end


    def on_player_request(player, text)
      out "(#{player}) #{text}"
      get
    end

    def on_player_turn(player, state, deal)
    end

    def on_player_choice(player, choice, deal)
    end

    def on_new_player(player)
      out "New player => #{player}"
    end

    def on_new_deal(deal)
      delimiter "."
      out "New deal: "
      delimiter "."

      out "  => cards => #{deal.cards}"
      deal.players_data.each do |player_data|
        out "    => #{player_data}"
      end
    end

    def on_new_state(state, deal)
      delimiter "."
      out "Deal state => #{state}"
      out "Board => #{deal.board.to_s}"
      delimiter "."
    end

    def on_error(message)
      raise message
    end
  end
end
