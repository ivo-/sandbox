module Rpoker::Interface
  class Cli
    include Rpoker::Player

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

    def newline
      out ""
    end

    def on_start_game(game)
      out "Start Game"
      delimiter
    end

    def on_end_game(game)
      newline
      delimiter "##"
      newline

      out "Winner: #{game.players.first}"

      newline
      delimiter "##"
      newline

      delimiter
      out "End Game"
      delimiter
    end

    def on_deal_resolve(deal)
      delimiter "*"
      out "Deal winners: "
      newline

      deal.winners.each do |winner|
        out "  #{winner.player} => #{winner.pocket}"
      end

      newline
      delimiter "*"
    end

    def on_player_turn(player, state, deal)
      return unless player.instance_of? Human

      out "#{state.player_state.player.id}(You):
  pocket => #{state.player_state.pocket}
  chips  => #{state.player_state.chips}
  plot   => #{state.player_state.plot}
          "

      state.oponents_state.each do |oponent|
        next if not oponent.playing?

        out "#{oponent.player.id}:
  chips => #{oponent.chips}
  plot  => #{oponent.plot}
            "
      end
    end

    def on_player_request(player, text)
      out "#{text}"
      get
    end

    def on_player_choice(player, choice, deal)
      delimiter "-"
      out "#{player} => #{choice}"
      delimiter "-"
    end

    def on_new_player(player)
      out "New player => #{player}"
    end

    def on_new_deal(deal)
      newline
      delimiter ">"
      out "New deal: "
      delimiter ">"
      newline
    end

    def on_new_state(state, deal)
      newline
      delimiter "="
      out "Deal state => #{state}"
      out "Board      => #{deal.board.to_s}"
      delimiter "="
      newline
    end

    def on_error(message)
      raise message
    end
  end
end
