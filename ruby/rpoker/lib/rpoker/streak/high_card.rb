module Rpoker::Streak
  class HighCard
    extend Helpers
    include Comparable

    attr_reader :strength, :card

    def initialize(card)
      @card = card
      @strength = 0
    end

    def <=>(other)
      result = 0

      return result if (result = self.strength <=> other.strength) != 0
      return result if (result = self.card <=> other.card)         != 0

      0
    end

    def self.test(cards)
      new power_sort_cards(cards).first
    end
  end
end
