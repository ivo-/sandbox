module Rpoker::Streak
  module Helpers
    extend self

    def get_map_by(cards, field)
      cards.each_with_object({}) do |card, map|
        key = card.send field

        map[key] ||= []
        map[key] << card
      end
    end

    def get_suites_map(cards)
      get_map_by cards, :suite
    end

    def get_frequency_map(cards)
      get_map_by cards, :rank
    end

    def get_sets(cards, min_length = 2, max_length = 3)
      get_frequency_map(cards).values.select do |cards|
        cards.length.between? min_length, max_length
      end
    end

    def get_suites(cards, min_length = 5, max_length = cards.length)
      get_suites_map(cards).values.select do |cards|
        cards.length.between? min_length, max_length
      end
    end

    def power_sort_sets(sets)
      sets.sort do |prev, nxt|
        if    (result = nxt.length <=> prev.length)  != 0 then result
        elsif (result = nxt.first  <=> prev.first )  != 0 then result
        else  (0)                                         end
      end
    end

    def power_sort_cards(cards)
      cards.sort { |prev, nxt| nxt <=> prev }
    end

    # NOTE: this function will not match game specific straights, such
    # as A-5 in holdem. It is general purpose and depends on only on cards strngth.
    def get_straights(cards)
      straights = power_sort_cards(cards.uniq { |c| c.strength })
        .each_cons(5)
        .to_a

      # Make sure all elements are immediately consecutive.
      straights.select do |straight|
        !!straight.inject do |prev, nxt|
          break false if prev.strength != (nxt.strength + 1)
          nxt
        end
      end
    end
  end
end
