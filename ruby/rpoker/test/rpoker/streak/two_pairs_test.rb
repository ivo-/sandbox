require "test_helper"

class TestStreakTwoPairs  < MiniTest::Unit::TestCase
  def get_card(suite, rank, strength)
    Rpoker::Data::Card.new suite, rank, strength
  end

  def setup
    @streak = ::Rpoker::Streak::TwoPairs
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


    assert_equal 2, @streak.test(@cards).pairs.length

    assert_equal get_card(:"{}", :C, 3), @streak.test(@cards).pairs.first.first
    assert_equal get_card(:"{}", :B, 2), @streak.test(@cards).pairs.last.first
  end

  def test_comparison
    assert @streak.test(@cards) == @streak.test(@cards)

    @cb = @streak.test @cards
    @ba = @streak.test [
                        get_card(:"{}", :B, 2),
                        get_card(:"{}", :B, 2),
                        get_card(:"{}", :A, 1),
                        get_card(:"{}", :A, 1),
                       ]
    @db = @streak.test [
                        get_card(:"{}", :B, 2),
                        get_card(:"{}", :B, 2),
                        get_card(:"{}", :D, 4),
                        get_card(:"{}", :D, 4),
                       ]

    @ca = @streak.test [
                        get_card(:"{}", :C, 3),
                        get_card(:"{}", :C, 3),
                        get_card(:"{}", :A, 1),
                        get_card(:"{}", :A, 1),
                        get_card(:"{}", :B, 2),
                       ]

    assert @ca < @cb
    assert @cb > @ba
    assert @db > @cb
    assert @ba < @db

    assert @cb == @cb
    assert @db == @db
    assert @ba == @ba

    assert @streak.test([
                        get_card(:"{}", :C, 3),
                        get_card(:"{}", :C, 3),
                        get_card(:"{}", :A, 1),
                        get_card(:"{}", :A, 1),
                        get_card(:"{}", :B, 2),
                        get_card(:"{}", :D, 4),
                       ]) < @streak.test([
                                          get_card(:"{}", :C, 3),
                                          get_card(:"{}", :C, 3),
                                          get_card(:"{}", :A, 1),
                                          get_card(:"{}", :A, 1),
                                          get_card(:"{}", :B, 2),
                                          get_card(:"{}", :E, 5),
                                         ])
  end
end
