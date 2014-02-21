module Rpoker::Data
  module Action
    class PlayerChoice
      attr_reader :player, :choice

      def initialize(player, choice)
        @player, @choice = player, choice
      end

      def to_s
        "ACTION(#@player -> #@choice)"
      end
    end

    class StateChange
      attr_reader :previous, :current

      def initialize(prev, current)
        @previous, @current = previous, current
      end

       def to_s
        "STATE_CHANGE(#@previous -> #@current)"
      end
    end
  end
end
