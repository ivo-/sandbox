require "test_helper"

class TestStreakSetOfKind  < MiniTest::Unit::TestCase
  def get_card(suite, rank, strength)
    Rpoker::Data::Card.new suite, rank, strength
  end

  def setup
    @streak = ::Rpoker::Streak::SetOfKind

    @cards = [
              get_card(:"{}", :A, 1),
              get_card(:"{}", :B, 2),
              get_card(:"{}", :C, 3),
              get_card(:"[]", :A, 1),
              get_card(:"+" , :A, 1),
              get_card(:"{}", :B, 2),
              get_card(:"{}", :D, 4),
              get_card(:"{}", :C, 3),
             ]
  end

  def test_accuracy
    assert @streak.test(@cards)
    refute @streak.test([
                         get_card(:"{}", :A, 1),
                         get_card(:"{}", :B, 2),
                         get_card(:"{}", :C, 3),
                         get_card(:"{}", :D, 4),
                        ])

    assert_equal 2, @streak.test(@cards).cards.length

    assert_equal get_card(:"{}", :C, 3), @streak.test(@cards).cards.first
    assert_equal get_card(:"{}", :D, 4), @streak.test(@cards).kickers.first
  end

  def test_comparison
    assert @streak.test(@cards) == @streak.test(@cards)

    @streak_c = @streak.test @cards
    @streak_b = @streak.test [
                              get_card(:"{}", :B, 2),
                              get_card(:"{}", :B, 2),
                              get_card(:"{}", :C, 3),
                             ]
    @streak_b_a = @streak.test [
                              get_card(:"{}", :B, 2),
                              get_card(:"{}", :B, 2),
                              get_card(:"{}", :A, 1),
                             ]

    assert @streak_c > @streak_b
    assert @streak_c > @streak_b_a

    assert @streak_b < @streak_c
    assert @streak_b > @streak_b_a
  end
end
