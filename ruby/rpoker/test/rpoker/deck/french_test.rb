require "test_helper"

class TestFrenchDeck < MiniTest::Unit::TestCase
  def test_stats
    @deck = Rpoker::Deck::French.new

    assert_equal 52, @deck.length
    assert_equal 13, @deck.ranks.length
    assert_equal 4 , @deck.suites.length
  end
end
