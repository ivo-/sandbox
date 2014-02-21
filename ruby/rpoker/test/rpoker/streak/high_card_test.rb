require "test_helper"

class TestStreakHighCard  < MiniTest::Unit::TestCase
  def get_card(suite, rank, strength)
    Rpoker::Data::Card.new suite, rank, strength
  end

  def setup
    @streak = ::Rpoker::Streak::HighCard
  end

  def test_accuracy
    assert_equal get_card(:"{}", :C, 3), @streak.test([
                                                       get_card(:"{}", :A, 1),
                                                       get_card(:"{}", :C, 3),
                                                       get_card(:"[]", :A, 1),
                                                      ]).card
  end

  def test_comparison
    assert @streak.test([get_card(:"{}", :B, 2)]) == @streak.test([get_card(:"{}", :B, 2)])
    assert @streak.test([get_card(:"{}", :C, 3)]) > @streak.test([get_card(:"{}", :B, 2)])
    assert @streak.test([get_card(:"{}", :B, 2)]) < @streak.test([get_card(:"{}", :C, 3)])
  end
end
