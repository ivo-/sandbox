require "test_helper"

class TestEmptyDeck < MiniTest::Unit::TestCase
  include Rpoker::Data

  def card(stength)
    Card.new :test_suite, stength.to_s.intern, stength
  end

  def setup
    @deck = Rpoker::Deck::Empty.new

    @card_2 = Card.new :test_suite, 2, 2
    @card_3 = Card.new :test_suite, 3, 3
    @card_4 = Card.new :test_suite, 4, 4
  end

  def test_initialization
    assert_equal 0, @deck.length
    assert_equal 0, @deck.length_burned
    assert_equal "DECK(0)", @deck.to_s
  end

  def test_card_adding
    @deck.add @card_1
    assert_equal 1, @deck.length
    assert_equal @card_1, @deck.last

    @deck.add @card_2
    assert_equal 2, @deck.length
    assert_equal @card_2, @deck.last
  end

  def test_pop_cards
    @deck.add @card_2
    @deck.add @card_3
    @deck.add @card_4

    assert_equal 3, @deck.length
    assert_equal @card_4, @deck.pop

    assert_equal 2, @deck.length
    assert_equal @card_3, @deck.pop

    assert_equal 1, @deck.length
    assert_equal @card_2, @deck.pop

    refute @deck.pop
    assert @deck.length == 0
  end

  def test_burn_cards
    @deck.add @card_2
    @deck.add @card_3
    @deck.add @card_4

    assert_equal 2, @deck.burn.length
    assert_equal 1, @deck.length_burned

    assert_equal 1, @deck.burn.length
    assert_equal 2, @deck.length_burned

    assert_equal 0, @deck.burn.length
    assert_equal 3, @deck.length_burned

    assert_equal 0, @deck.burn.length
    assert_equal 3, @deck.length_burned
  end

  def test_merge
    @deck.add @card_2
    @deck.add @card_3
    @deck.add @card_4

    @deck.burn.burn

    assert_equal 1, @deck.length
    assert_equal 2, @deck.length_burned

    @deck.merge

    assert_equal 3, @deck.length
    assert_equal 0, @deck.length_burned
  end
end
