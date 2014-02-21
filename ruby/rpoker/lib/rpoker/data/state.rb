module Rpoker::Data
  class State
    attr_reader :deal_state, :player_state, :oponents_state, :cards, :choices

    def initialize(deal_state, player_state, oponents_state, cards, choices)
      @deal_state = deal_state
      @player_state = player_state
      @oponents_state = oponents_state

      @cards = cards
      @choices = choices
    end

    def to_s
      "STATE(#@player_stata -> #@choices)"
    end
  end
end
