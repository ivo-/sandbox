module Rpoker::Finder
  class Empty
    def initialize
      @streaks = []
    end

    def add_streak(streak)
      @streaks << streak
    end

    def find(cards)
      find_all(cards).first
    end

    def find_all(cards)
      @streaks
        .map    { |streak| streak.test cards }
        .select { |streak| streak }
        .sort   { |prev, nxt| nxt <=> prev }
    end

    def to_s
      "FINDER::EMPTY"
    end
  end
end
