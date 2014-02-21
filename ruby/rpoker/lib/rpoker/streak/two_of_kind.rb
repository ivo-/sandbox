module Rpoker::Streak
  class TwoOfKind < SetOfKind
    def self.length; 2 end
    def self.strength; 1 end
  end
end
