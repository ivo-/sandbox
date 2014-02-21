require "test_helper"

class TestStreakStraightFlush < MiniTest::Unit::TestCase #
  def get_card(suite, rank, strength)
    Rpoker::Data::Card.new suite, rank, strength
  end

  def setup
    @streak = Rpoker::Streak::StraightFlush
    @cards = [
              get_card(:"{}", :A, 1),
              get_card(:"{}", :B, 2),
              get_card(:"{}", :C, 3),
              get_card(:"{}", :E, 5),
              get_card(:"()", :B, 2),
              get_card(:"{}", :D, 4),
              get_card(:"()", :G, 6),
              get_card(:"{}", :G, 6)
              ]
  end

  def test_accuracy
    assert @streak.test(@cards)
    refute @streak.test(@cards[1..5])

    test_streak = @streak.test(@cards)

    assert_equal 5, test_streak.cards.length
    assert_equal 6, test_streak.cards.first.strength
    assert_equal 5, test_streak.cards.select { |c| c.suite == :"{}" }.length

    assert_equal get_card(:"{}", :G, 6), test_streak.cards[0]
    assert_equal get_card(:"{}", :E, 5), test_streak.cards[1]
    assert_equal get_card(:"{}", :D, 4), test_streak.cards[2]
    assert_equal get_card(:"{}", :C, 3), test_streak.cards[3]
    assert_equal get_card(:"{}", :B, 2), test_streak.cards[4]

    refute @streak.test(@cards[1..6])
  end

  def test_comparison
    assert @streak.test(@cards)       == @streak.test(@cards)
    assert @streak.test(@cards[0..6]) <  @streak.test(@cards)
    assert @streak.test(@cards)       >  @streak.test(@cards[0..6])
  end
end
