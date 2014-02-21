module Rpoker::Deck
  class Empty
    def initialize
      @cards = []
      @removed = []
    end

    def add(card)
      @cards << card
      self
    end

    def pop
      @removed.push(@cards.pop).last
    end

    def last
      @cards.last
    end

    def first
      @cards.first
    end

    def merge
      @cards.concat(@removed)
      @removed.clear
      self
    end

    def shuffle
      @cards.shuffle!
      self
    end

    def burn
      @removed.push(@cards.pop) if @cards.length > 0
      self
    end

    def length
      @cards.length
    end

    def length_burned
      @removed.length
    end

    def to_s
      "DECK(#{length})"
    end
  end
end
