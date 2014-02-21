module Rpoker::Player
  class Computer < Human
    def action(game_state, input = {})
      # TODO: AI

      choice = game_state.choices.sample
      choice.chips += 1 if choice.type == :raise
      choice
    end

    def to_s
      "#@id(Computer)"
    end
  end
end
