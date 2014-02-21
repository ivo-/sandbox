module Rpoker::Streak
  class StraightFlush
    extend Helpers
    include Comparable

    attr_reader :strength, :cards

    def initialize(cards)
      @cards = cards
      @strength = 8
    end

    def <=>(other)
      result = 0

      return result if (result = self.strength <=> other.strength)       != 0
      return result if (result = self.cards.first <=> other.cards.first) != 0

      0
    end

    def self.test(cards)
      longer_suite = get_suites cards, 5, cards.length
      longer_suite = power_sort_sets(longer_suite).first || []

      straight = get_straights(longer_suite).first || Straight.smaller_straight(longer_suite)
      straight ? new(straight) : false
    end
  end
end
