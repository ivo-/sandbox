module Rpoker::Data
  class Suite
    attr_reader :name, :symbol

    def initialize(name, symbol)
      @name, @symbol = name, symbol
    end

    def to_s
      @symbol.to_s
    end
  end
end
