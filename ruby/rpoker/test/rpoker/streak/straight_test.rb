require "test_helper"

class TestStreakStraight < MiniTest::Unit::TestCase
  def get_card(suite, rank, strength)
    Rpoker::Data::Card.new suite, rank, strength
  end

  def setup
    @streak = Rpoker::Streak::Straight
    @cards = [
              get_card(:"{}", :A, 1),
              get_card(:"{}", :B, 2),
              get_card(:"{}", :C, 3),
              get_card(:"[]", :A, 1),
              get_card(:"+" , :E, 5),
              get_card(:"{}", :B, 2),
              get_card(:"{}", :D, 4),
              get_card(:"{}", :G, 6),
              get_card(:"{}", :C, 3),
              ]
  end

  def test_accuracy
    assert @streak.test(@cards)
    refute @streak.test(@cards[1..3])

    assert_equal 5, @streak.test(@cards).cards.length

    assert_equal get_card(:"{}", :G, 6), @streak.test(@cards).cards[0]
    assert_equal get_card(:"{}", :E, 5), @streak.test(@cards).cards[1]
    assert_equal get_card(:"{}", :D, 4), @streak.test(@cards).cards[2]
    assert_equal get_card(:"{}", :C, 3), @streak.test(@cards).cards[3]
    assert_equal get_card(:"{}", :B, 2), @streak.test(@cards).cards[4]
  end

  def test_saller_streak
    assert @streak.test([
                         get_card(:"{}", :A, 12),
                         get_card(:"{}",  2, 0),
                         get_card(:"{}",  2, 0),
                         get_card(:"{}",  3, 1),
                         get_card(:"{}",  4, 2),
                         get_card(:"{}",  5, 3),
                         get_card(:"{}",  7, 5),
                         get_card(:"{}",  8, 6),
                        ])
  end

  def test_comparison
    assert @streak.test(@cards) == @streak.test(@cards)
    assert @streak.test(@cards) == @streak.test(@cards[1..7])

    assert @streak.test(@cards)       > @streak.test(@cards[0..6])
    assert @streak.test(@cards[1..7]) > @streak.test(@cards[0..6])
  end
end
