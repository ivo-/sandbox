module Rpoker::Data
  class Player
    attr_reader :player
    attr_accessor :plot, :chips, :pocket, :playing, :blind

    alias :playing? :playing

    def initialize(player, chips, plot, pocket, blind)
      @player = player

      @plot = plot
      @chips = chips
      @pocket = pocket

      @blind = blind
      @playing = true
    end

    def to_s
      "PLAYER_DATA(#@player -> #@chips #@plot #@pocket #@blind)"
    end
  end
end
