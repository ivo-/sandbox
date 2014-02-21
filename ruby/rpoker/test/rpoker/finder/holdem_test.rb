require "test_helper"

class TestHoldemFinder  < MiniTest::Unit::TestCase
  include Rpoker::Data
  include Rpoker::Streak

  def card(symbol)
    parts = symbol.to_s.split("_")
    Card.new parts[1], parts[0], @ranks.index(parts[0])
  end

  def setup
    @finder = Rpoker::Finder::Holdem.new
    @ranks = [
              "2",
              "3",
              "4",
              "5",
              "6",
              "7",
              "8",
              "9",
              "10",
              "J",
              "Q",
              "K",
              "A",
             ]
  end

  def test_high_card
    cards = [
             card("Q_S"), card("2_S"), card("3_H"),
             card("10_C"), card("7_D"), card("J_S"), card("5_H")
             ]

    assert_equal 1, @finder.find_all(cards).length
    assert_equal 0, @finder.find(cards).strength

    assert_equal card("Q_S"), @finder.find(cards).card
    assert_instance_of HighCard, @finder.find(cards)
  end

  def test_of_kind
    cards = [
             card("Q_S"), card("2_S"), card("Q_H"),
             card("Q_C"), card("Q_D"), card("J_S"), card("J_H")
             ]

    assert_equal 3, @finder.find_all(cards).length

    assert_instance_of HighCard, @finder.find_all(cards)[2]
    assert_instance_of TwoOfKind, @finder.find_all(cards)[1]
    assert_instance_of FourOfKind, @finder.find_all(cards)[0]

    assert_instance_of FourOfKind, @finder.find(cards)
  end

  def test_full_house
    cards = [
             card("Q_S"), card("2_S"), card("Q_H"),
             card("Q_C"), card("K_D"), card("J_S"), card("J_H")
             ]

    assert_equal 4, @finder.find_all(cards).length

    assert_instance_of HighCard, @finder.find_all(cards)[3]
    assert_instance_of TwoOfKind, @finder.find_all(cards)[2]
    assert_instance_of ThreeOfKind, @finder.find_all(cards)[1]
    assert_instance_of FullHouse, @finder.find_all(cards)[0]

    assert_equal 2, @finder.find(cards).pair.length
    assert_equal 3, @finder.find(cards).three.length

    assert_equal card("Q_S"), @finder.find(cards).three.first
    assert_equal card("J_S"), @finder.find(cards).pair.first
  end

  def test_straight
    cards = [
             card("4_S"), card("2_S"), card("3_H"),
             card("6_C"), card("7_D"), card("5_S"), card("5_H")
             ]

    assert_equal 3, @finder.find_all(cards).length

    assert_instance_of HighCard, @finder.find_all(cards)[2]
    assert_instance_of TwoOfKind, @finder.find_all(cards)[1]
    assert_instance_of Straight, @finder.find_all(cards)[0]

    assert_instance_of Straight, @finder.find(cards)

    assert_equal card("7_D"), @finder.find(cards).cards[0]
    assert_equal card("3_H"), @finder.find(cards).cards[4]
  end

  def test_flush
    cards = [
             card("4_S"), card("2_S"), card("3_S"),
             card("8_S"), card("7_D"), card("5_S"), card("5_H")
             ]

    assert_equal 3, @finder.find_all(cards).length

    assert_instance_of HighCard, @finder.find_all(cards)[2]
    assert_instance_of TwoOfKind, @finder.find_all(cards)[1]
    assert_instance_of Flush, @finder.find_all(cards)[0]

    assert_instance_of Flush, @finder.find(cards)

    assert_equal card("8_S"), @finder.find(cards).cards[0]
    assert_equal card("2_S"), @finder.find(cards).cards[4]
  end

  def test_straight_flush
    cards = [
             card("4_S"), card("2_S"), card("3_S"),
             card("6_S"), card("7_D"), card("5_S"), card("5_H")
             ]

    assert_equal 5, @finder.find_all(cards).length

    assert_instance_of HighCard, @finder.find_all(cards)[4]
    assert_instance_of TwoOfKind, @finder.find_all(cards)[3]
    assert_instance_of Straight, @finder.find_all(cards)[2]
    assert_instance_of Flush, @finder.find_all(cards)[1]
    assert_instance_of StraightFlush, @finder.find_all(cards)[0]

    assert_instance_of StraightFlush, @finder.find(cards)

    assert_equal card("6_S"), @finder.find(cards).cards[0]
    assert_equal card("2_S"), @finder.find(cards).cards[4]
  end
end
