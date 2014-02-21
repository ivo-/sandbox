require "test_helper"

class TestDealData  < MiniTest::Unit::TestCase
  include Rpoker::Data
  include Rpoker::Deck
  include Rpoker::Game

  def setup
    @deck = French.new.shuffle
    @cards = 1.upto(5).map { |_| @deck.pop }

    @players_data = ["P1", "P2", "P3", "P4"].map do
      |p| Player.new p, 200, 0, [@deck.pop, @deck.pop], false
    end

    @players_data.first.blind =  true
    @players_data.first.chips -= 2
    @players_data.first.plot  =  2

    @deal = Holdem::Deal.new @players_data.clone, @cards.clone
  end

  def test_initializaton
    assert_equal @cards, @deal.cards
    assert_equal @players_data, @deal.players_data

    assert_equal @players_data.first, @deal.current_player
    assert_equal @players_data.length, @deal.active_players.length
  end

  def test_utils
    assert_equal 0, @deal.blind

    assert_equal @players_data.first.plot, @deal.max_plot
    assert_equal @players_data.first.plot, @deal.combined_plot

    assert_equal @players_data.length, @deal.active_players.length
  end

  def test_players_iterator
    refute @deal.no_more_turns?
    assert_equal @players_data[0].player, @deal.current_player.player

    assert_equal @players_data[1].player, @deal.next_player.player
    assert_equal @players_data[1].player, @deal.current_player.player

    assert_equal @players_data[2].player, @deal.next_player.player
    assert_equal @players_data[2].player, @deal.current_player.player

    assert_equal @players_data[3].player, @deal.next_player.player
    assert_equal @players_data[3].player, @deal.current_player.player

    refute @deal.next_player
    refute @deal.current_player
    assert @deal.no_more_turns?

    @deal.reset_turn_counter

    assert_equal @players_data[0].player, @deal.next_player.player
    assert_equal @players_data[1].player, @deal.next_player.player
    assert_equal @players_data[2].player, @deal.next_player.player

    refute @deal.next_player
    refute @deal.current_player
    assert @deal.no_more_turns?

    @deal.start_new_bets

    assert_equal @players_data[0].player, @deal.current_player.player
    assert_equal @players_data[1].player, @deal.next_player.player
    assert_equal @players_data[2].player, @deal.next_player.player
    assert_equal @players_data[3].player, @deal.next_player.player

    refute @deal.next_player
    refute @deal.current_player
    assert @deal.no_more_turns?
  end

  def test_deal_state_iterator
    assert_equal :start, @deal.current_state
    assert_equal [], @deal.board

    assert_equal :flop, @deal.next_state
    assert_equal :flop, @deal.current_state
    assert_equal @cards[0..2], @deal.board

    assert_equal :turn, @deal.next_state
    assert_equal :turn, @deal.current_state
    assert_equal @cards[0..3], @deal.board

    assert_equal :river, @deal.next_state
    assert_equal :river, @deal.current_state
    assert_equal @cards[0..4], @deal.board

    refute @deal.next_state
  end

  def test_choice_making
    blind_choices = @deal.get_possible_choices @deal.current_player.player
    assert_equal 4, blind_choices.length
    assert_equal 0, blind_choices.select { |choice| choice.type == :call }.length

    second_choices = @deal.get_possible_choices @players_data[1].player
    assert_equal 4, second_choices.length
    assert_equal 0, second_choices.select { |choice| choice.type == :check }.length

    assert_raises(RpokerError) do
      @deal << Choice::Raise.new(1000)
    end
    assert_raises(RpokerError) do
      @deal << Choice::Fold.new(20)
    end
    assert_raises(RpokerError) do
      @deal << 20
    end

    allin = blind_choices.select { |choice| choice.type == :allin }.first
    @deal << allin

    assert_equal 0, @deal.current_player.chips
    assert_equal 200, @deal.current_player.plot

    assert_equal allin, @deal.actions.last.choice
    assert_equal @deal.current_player.player, @deal.actions.last.player

    assert_raises(RpokerError) do
      @deal << blind_choices.select { |choice| choice.type == :check }
    end
  end

  def test_player_state_generation
    state = @deal.get_oponents_state @deal.current_player

    assert_equal 0, state.select { |p| p.pocket != nil }.length
    assert_equal 0, state.select { |p| p.player == @deal.current_player }.length

    full_state = @deal.get_full_state @deal.current_player.player

    assert_equal full_state.cards, @deal.board
    assert_equal full_state.player_state.player, @deal.current_player.player
    assert_equal full_state.deal_state, @deal.current_state
  end

  def test_resolve
    @deal.next_state
    @deal.next_state
    @deal.next_state

    refute @deal.resolve(@deal.current_player)

    @deal.next_state # end game

    chips_before_resolve = @deal.current_player.chips

    assert_equal @deal.current_player, @deal.resolve(@deal.current_player.player)[0]
    assert_equal @deal.winners[0], @deal.current_player
    assert_equal @deal.winners[0].chips, chips_before_resolve + @deal.combined_plot
  end
end
