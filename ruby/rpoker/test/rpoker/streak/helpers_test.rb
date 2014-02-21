require "test_helper"

class TestStreaksHelpers  < MiniTest::Unit::TestCase
  def get_card(suite, rank, strength)
    Rpoker::Data::Card.new suite, rank, strength
  end

  def setup
    @helper = Rpoker::Streak::Helpers
    @cards = [
              get_card(:"{}", :A, 1),
              get_card(:"{}", :B, 2),
              get_card(:"{}", :C, 3),
              get_card(:"[]", :A, 1),
              get_card(:"+" , :A, 1),
              get_card(:"{}", :B, 2),
              get_card(:"{}", :D, 4),
              get_card(:"{}", :G, 6)
              ]
  end

  def test_frequency_map
    fmap = @helper.get_frequency_map(@cards)

    assert_equal 5, fmap.length

    assert_equal 3, fmap[:A].length
    assert_equal 2, fmap[:B].length
    assert_equal 1, fmap[:C].length
    assert_equal 1, fmap[:D].length
    assert_equal 1, fmap[:G].length
  end

  def test_get_sets
    sets = @helper.get_sets(@cards, 2, 3)

    assert_equal 2, sets.length
    assert_equal 5, sets.first.length + sets.last.length

    assert_equal 5, @helper.get_sets(@cards, 1, 3).length
    assert_equal 1, @helper.get_sets(@cards, 3, 3).length
  end

  def test_get_suites
    sets = @helper.get_suites(@cards, 1, 6)

    assert_equal 3, sets.length
    assert_equal 8, sets.inject(0) { |sum, set| sum + set.length }

    assert_equal 2, @helper.get_suites(@cards, 1, 1).length
  end

  def test_power_sort
    sets = @helper.get_sets @cards, 1, 3
    sets_sorted = @helper.power_sort_sets sets

    assert_equal sets_sorted[0][0], get_card(:"{}", :A, 1)
    assert_equal sets_sorted[1][0], get_card(:"{}", :B, 2)
    assert_equal sets_sorted[2][0], get_card(:"{}", :G, 6)
    assert_equal sets_sorted[3][0], get_card(:"{}", :D, 4)
    assert_equal sets_sorted[4][0], get_card(:"{}", :C, 3)

    assert_equal get_card(:"{}", :G, 6),  @helper.power_sort_cards(@cards)[0]
    assert_equal get_card(:"{}", :D, 4),  @helper.power_sort_cards(@cards)[1]
    assert_equal get_card(:"{}", :C, 3),  @helper.power_sort_cards(@cards)[2]
  end

  def test_get_straights
    cards = [
             get_card(:"{}", :A, 1),
             get_card(:"{}", :B, 2),
             get_card(:"{}", :C, 3),
             get_card(:"[]", :A, 1),
             get_card(:"+" , :E, 5),
             get_card(:"{}", :B, 2),
             get_card(:"{}", :D, 4),
             get_card(:"{}", :G, 6)
            ]

    straight = @helper.get_straights(cards)

    assert_equal get_card(:"{}", :G, 6), straight[0][0]
    assert_equal get_card(:"{}", :E, 5), straight[0][1]
    assert_equal get_card(:"{}", :D, 4), straight[0][2]
    assert_equal get_card(:"{}", :C, 3), straight[0][3]
    assert_equal get_card(:"{}", :B, 2), straight[0][4]

    straight = @helper.get_straights(cards[1..6])

    assert_equal get_card(:"{}", :E, 5), straight[0][0]
    assert_equal get_card(:"{}", :D, 4), straight[0][1]
    assert_equal get_card(:"{}", :C, 3), straight[0][2]
    assert_equal get_card(:"{}", :B, 2), straight[0][3]
    assert_equal get_card(:"{}", :A, 1), straight[0][4]

    assert @helper.get_straights(cards[3..7]) # !
  end
end
