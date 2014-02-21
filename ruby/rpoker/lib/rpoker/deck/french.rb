module Rpoker::Deck
  class French < Empty
    include Rpoker::Data

    attr_reader :suites, :ranks

    def initialize
      super

      @suites = [
                Suite.new(:spades,   :S),
                Suite.new(:hearts,   :H),
                Suite.new(:diamonds, :D),
                Suite.new(:clubs,    :C),
               ]

      @ranks = [
                2,
                3,
                4,
                5,
                6,
                7,
                8,
                9,
                10,
                :J,
                :Q,
                :K,
                :A,
               ]

      @suites.each do |suite|
        @ranks.each_with_index do |rank, index|
          add Card.new(suite, rank, index)
        end
      end
    end
  end
end
