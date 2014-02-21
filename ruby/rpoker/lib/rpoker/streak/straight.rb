module Rpoker::Streak
  class Straight
    extend Helpers
    include Comparable

    attr_reader :strength, :cards

    def initialize(cards)
      @cards = cards
      @strength = 4
    end

    def <=>(other)
      result = 0

      return result if (result = self.strength <=> other.strength)       != 0
      return result if (result = self.cards.first <=> other.cards.first) != 0

      0
    end

    def self.test(cards)
      return false if cards.length < 5

      straight = get_straights(cards).first || smaller_straight(cards)
      straight ? new(straight) : false
    end

    def self.smaller_straight(cards)
      cards    = power_sort_cards cards.uniq { |c| c.strength }
      straight = []

      return false if cards.length < 5

      straight << cards[-1] if cards[-1].rank ==  2
      straight << cards[-2] if cards[-2].rank ==  3
      straight << cards[-3] if cards[-3].rank ==  4
      straight << cards[-4] if cards[-4].rank ==  5
      straight << cards[ 0] if cards[ 0].rank == :A

      straight.length == 5 ? straight : false
    end
  end
end
