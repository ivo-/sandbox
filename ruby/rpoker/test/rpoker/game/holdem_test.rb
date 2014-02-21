require "test_helper"

class TestHoldemGame  < MiniTest::Unit::TestCase
  include Rpoker::Player

  def fast_finish_deal(deal)
    deal.players_data.each { |player| player.plot += 1 }
    while deal.next_state do deal.next_state end
  end

  def setup
    @game = Rpoker::Game::Holdem.new
    @deck = Rpoker::Deck::French.new.shuffle
    @finder = Rpoker::Finder::Holdem.new
    @players = 4.times.to_a.map { |n| Human.new ("ivo" + n.to_s).intern }
  end

  def test_basic_funcionality
    assert @game.first_deal?
    refute @game.current_deal
    refute @game.enough_players?

    @game.deck = @deck
    @game.finder = @finder
    @game.players.push *@players

    assert_equal @deck, @game.deck
    assert_equal @finder, @game.finder
    assert_equal @players, @game.players

    assert @game.enough_players?

    assert_equal 5, @game.get_board.length
    assert_equal 2, @game.get_poket.length

    @game.shuffle_deck
  end

  def test_players_data
    @game.deck = @deck
    @game.finder = @finder
    @game.players.push *@players

    data = @game.new_players_data

    assert_equal @players[0], data[0].player
    assert_equal @players[1], data[1].player
    assert_equal @players[2], data[2].player
    assert_equal @players[3], data[3].player

    @game.set_blind data, 1

    refute data[0].blind
    assert data[1].blind
    refute data[2].blind

    assert_equal @game.blind_chips, data[1].plot
    assert_equal @game.start_chips, data[1].chips + data[1].plot

    assert_equal 0, data[0].plot
    assert_equal 0, data[2].plot

    assert_equal @game.start_chips, data[0].chips
    assert_equal @game.start_chips, data[2].chips

    assert_equal 1, @game.move_blind(0)
    assert_equal 2, @game.move_blind(1)
    assert_equal 3, @game.move_blind(2)
    assert_equal 0, @game.move_blind(3)

    n_data = @game.new_players_data

    @game.copy_chips data, n_data

    assert_equal n_data[0].chips, data[0].chips
    assert_equal n_data[1].chips, data[1].chips
    assert_equal n_data[2].chips, data[2].chips
    assert_equal n_data[3].chips, data[3].chips
  end

  def test_deals_iterator
    @game.deck = @deck
    @game.finder = @finder
    @game.players.push *@players

    assert @game.first_deal?
    refute @game.current_deal

    assert @game.next_deal, @game.current_deal
    refute @game.first_deal?
    refute @game.current_deal_finished?

    fast_finish_deal(@game.current_deal)

    assert @game.get_deal_winners(@game.current_deal)
    assert @game.next_deal, @game.current_deal
    assert @game.last_deal_finished?

    fast_finish_deal(@game.current_deal)
    @game.players.slice!(0, 3)

    refute @game.next_deal
    assert @game.last_deal_finished?
    assert_equal 1, @game.players.length
  end
end
