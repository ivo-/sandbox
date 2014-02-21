module Rpoker::Data
  class Choice
    attr_reader :chips

    def initialize(chips = 0)
      @chips = chips
    end

    def type
      self.class.to_s.split("::").last.downcase.intern
    end

    def ==(other)
      self.to_s == other.to_s
    end

    def to_s
      "#{type}(#@chips)"
    end
  end

  class Choice
    class Raise  < Choice
      attr_writer :chips

      # NOTE: Two rise choices are always interchangeable, despite
      # chips. Other choices are equal only when their chips and types
      # are equal.
      def ==(other)
        self.type == other.type
      end
    end
    class Fold  < Choice; end
    class Call  < Choice; end
    class Check < Choice; end
    class AllIn < Choice; end
  end
end
