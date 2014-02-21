module Rpoker::Streak
  class FourOfKind < SetOfKind
    def self.length; 4 end
    def self.strength; 7 end
  end
end
