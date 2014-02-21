module Rpoker::Finder
  class Holdem < Empty
    include Rpoker::Streak

    def initialize
      super

      add_streak HighCard
      add_streak TwoOfKind
      add_streak ThreeOfKind
      add_streak Straight
      add_streak Flush
      add_streak FullHouse
      add_streak FourOfKind
      add_streak StraightFlush
    end
  end

  def to_s
    "FINDER::HOLDEM"
  end
end
