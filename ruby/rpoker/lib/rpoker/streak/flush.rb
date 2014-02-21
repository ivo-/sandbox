module Rpoker::Streak
  class Flush
    extend Helpers
    include Comparable

    attr_reader :strength, :cards

    def initialize(cards)
      @cards = cards
      @strength = 5
    end

    def <=>(other)
      result = 0

      return result if (result = self.strength <=> other.strength)       != 0
      return result if (result = self.cards.first <=> other.cards.first) != 0

      0
    end

    def self.test(cards)
      longer_suite = get_suites cards, 1, cards.length
      longer_suite = power_sort_cards power_sort_sets(longer_suite).first

      longer_suite.length < 5 ? false : new(longer_suite[0..4])
    end
  end
end
