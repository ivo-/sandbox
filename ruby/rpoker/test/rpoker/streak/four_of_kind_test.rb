require "test_helper"

class TestStreakFourOfKind  < MiniTest::Unit::TestCase
  def get_card(suite, rank, strength)
    Rpoker::Data::Card.new suite, rank, strength
  end

  def setup
    @streak = ::Rpoker::Streak::FourOfKind

    @cards = [
              get_card(:"{}", :A, 1),
              get_card(:"{}", :B, 2),
              get_card(:"{}", :C, 3),
              get_card(:"[]", :A, 1),
              get_card(:"+" , :A, 1),
              get_card(:"{}", :A, 1),
              get_card(:"{}", :D, 4),
              get_card(:"{}", :C, 3)
             ]
  end

  def test_accuracy
    assert @streak.test(@cards)
    refute @streak.test([
                         get_card(:"{}", :A, 1),
                         get_card(:"{}", :B, 2),
                         get_card(:"{}", :C, 3),
                        ])

    assert @streak.test(@cards) == @streak.test(@cards)
    assert_equal 4, @streak.test(@cards).cards.length

    assert_equal get_card(:"{}", :A, 1), @streak.test(@cards).cards.first
  end
end
