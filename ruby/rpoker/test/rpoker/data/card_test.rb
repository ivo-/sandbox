require "test_helper"

class TestCardData < MiniTest::Unit::TestCase
  include Rpoker::Data

  def test_initialization
    card = Card.new :test, 2, 2

    assert_equal 2, card.rank
    assert_equal 2, card.strength
    assert_equal :test, card.suite
  end

  def test_comparison
    with_strength = Proc.new do |strength|
      Card.new :test, :rank, strength
    end

    assert with_strength.call(2) < with_strength.call(3)
    assert with_strength.call(3) > with_strength.call(2)
    assert with_strength.call(2) == with_strength.call(2)

    assert_equal "CARD(rank test)", with_strength.call(0).to_s
  end
end
