module Rpoker::Data
  class Card
    include Comparable
    attr_reader :suite, :rank, :strength

    def initialize(suite, rank, strength)
      @suite, @rank, @strength = suite, rank, strength
    end

    def <=>(card)
      self.strength <=> card.strength
    end

    def to_s
      "CARD(#{@rank.to_s} #{@suite.to_s})"
    end
  end
end
