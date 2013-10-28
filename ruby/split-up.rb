module Enumerable
  def split_up(n, step, pad, &block)
    each_slice(step)
      .map { |part| (part.count < n ? part + pad : pad).take n }
      .each &block

    # concat(pad).take((count/step.to_f).ceil)
    #   .each_slice(step)
    #   .map { |part| part.take n }
    #   .each &block

    # items = each_slice(step).map { |part| part.take n }
    # items[-1] = items[-1].concat(pad).take n
    # items.each &block
  end
end
