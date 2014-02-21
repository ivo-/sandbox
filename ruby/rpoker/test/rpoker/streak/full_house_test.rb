require "test_helper"

class TestStreakFullHouse  < MiniTest::Unit::TestCase
  def get_card(suite, rank, strength)
    Rpoker::Data::Card.new suite, rank, strength
  end

  def setup
    @streak = ::Rpoker::Streak::FullHouse

    @cards = [
              get_card(:"{}", :A, 1),
              get_card(:"{}", :B, 2),
              get_card(:"{}", :C, 3),
              get_card(:"[]", :A, 1),
              get_card(:"+" , :A, 1),
              get_card(:"{}", :B, 2),
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

    assert_equal 3, @streak.test(@cards).three.length
    assert_equal 2, @streak.test(@cards).pair.length

    assert_equal get_card(:"{}", :A, 1), @streak.test(@cards).three.first
    assert_equal get_card(:"{}", :C, 3), @streak.test(@cards).pair.first
  end

  def test_comparison
    assert @streak.test(@cards) == @streak.test(@cards)

    @ac = @streak.test @cards
    @bc = @streak.test [
                              get_card(:"{}", :B, 2),
                              get_card(:"{}", :B, 2),
                              get_card(:"{}", :B, 2),
                              get_card(:"{}", :C, 3),
                              get_card(:"{}", :C, 3),
                             ]
    @ba = @streak.test [
                              get_card(:"{}", :B, 2),
                              get_card(:"{}", :B, 2),
                              get_card(:"{}", :B, 2),
                              get_card(:"{}", :A, 1),
                              get_card(:"{}", :A, 1),
                             ]
    assert @ac < @bc
    assert @ac < @ba

    assert @bc > @ba
    assert @ba < @bc

    assert @bc == @bc
  end
end
