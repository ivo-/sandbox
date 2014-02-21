module Rpoker::Streak
  class SetOfKind
    extend Helpers
    include Comparable

    attr_reader :strength, :cards, :kickers

    def initialize(cards, kickers, strength)
      @cards = cards
      @kickers = kickers
      @strength = strength
    end

    def <=>(other)
      result = 0

      return result if (result = self.strength <=> other.strength)       != 0
      return result if (result = self.cards.first <=> other.cards.first) != 0
      return result if (result = self.kickers <=> other.kickers)         != 0

      0
    end

    def self.length; 2 end
    def self.strength; 2 end

    def self.test(cards)
      cards_set = power_sort_sets(get_sets(cards, length, length)).first

      if cards_set
        kickers  = power_sort_cards(cards) - cards_set
        new(cards_set, kickers, strength)
      else false end
    end
  end
end
