module Rpoker::Streak
  class TwoPairs
    extend Helpers
    include Comparable

    attr_reader :strength, :pairs, :kickers

    def initialize(pairs, kickers)
      @pairs = pairs
      @kickers = kickers
      @strength = 2
    end

    def <=>(other)
      result = 0

      return result if (result = self.strength <=> other.strength)       != 0
      return result if (result = self.pairs[0][0] <=> other.pairs[0][0]) != 0
      return result if (result = self.pairs[1][0] <=> other.pairs[1][0]) != 0
      return result if (result = self.kickers <=> other.kickers)         != 0

      0
    end

    def self.test(cards)
      pairs = power_sort_sets get_sets(cards, 2, 2)
      kickers  = power_sort_cards(cards) - pairs.flatten

      pairs.length < 2 ? false : new(pairs, kickers)
    end
  end
end
