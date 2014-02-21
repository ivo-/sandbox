module Rpoker::Streak
  class FullHouse
    extend Helpers
    include Comparable

    attr_reader :strength, :three, :pair

    def initialize(three, pair)
      @pair = pair
      @three = three

      @strength = 6
    end

    def <=>(other)
      result = 0

      return result if (result = self.strength <=> other.strength) != 0
      return result if (result = self.three[0] <=> other.three[0]) != 0
      return result if (result = self.pair[0] <=> other.pair[0])   != 0

      0
    end

    def self.test(cards)
      pair = power_sort_sets get_sets(cards, 2, 2)
      three = power_sort_sets get_sets(cards, 3, 3)

      pair = three[1][0..1] if pair.length == 0 && three.length > 1

      three.first && pair.first ? new(three.first, pair.first) : false
    end
  end
end
