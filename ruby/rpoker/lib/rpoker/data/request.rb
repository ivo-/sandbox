class Rpoker::Data::Request
  class Input < self
    attr_accessor :key, :message

    def initialize(key, message)
      @key, @message = key, message
    end

    def to_s
      "REQUEST::INPUT(#@key -> #@message)"
    end
  end
end
