module Rpoker::Streak
  class ThreeOfKind < SetOfKind
    def self.length; 3 end
    def self.strength; 3 end
  end
end
