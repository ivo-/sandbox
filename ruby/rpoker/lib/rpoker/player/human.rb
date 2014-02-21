module Rpoker::Player
  class Human
    include Rpoker::Data

    attr_reader :id

    def initialize(id)
      @id = id
    end

    # TODO: prettify and clarify

    def action(game_state, input = {})
      if input[:choice]
        choice = find_choice game_state.choices, input[:choice]

        if choice
          if choice.type == :raise
            chips = input[:chips].to_i

            if !chips || chips <= 0 || chips >= game_state.player_state.chips
              return Request::Input.new :chips,
              "Enter raise chips amoutnt [1 - " +
              "#{game_state.player_state.chips - choice.chips}]: "
            else
              choice.chips += chips
            end
          end

          choice
        else
          action game_state
        end
      else
        Request::Input.new :choice,
          "Make choice -#{list_choices(game_state.choices)}: "
      end
    end

    def find_choice(choices, choice)
      choices.select { |state| state.type.to_s == choice.to_s }.first
    end

    def list_choices(choices)
      choices.inject("") do |str, choice|
        chips = choice.chips
        chips = "#{chips}+" if choice.type == :raise

        "#{str} #{choice.type.to_s}(#{chips}),"
      end.chop
    end

    def to_s
      "#@id(Human)"
    end
  end
end
