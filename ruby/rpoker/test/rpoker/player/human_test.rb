require "test_helper"

class TestHumanPlayer  < MiniTest::Unit::TestCase
  include Rpoker::Deck
  include Rpoker::Data
  include Rpoker::Player

  def setup
    @player = Human.new :I

    @deck = French.new.shuffle
    @cards = 5.times.map { |_| @deck.pop }
    @pocket = 2.times.map { |_| @deck.pop }

    @choices = [
                Choice::Fold.new(),
                Choice::Call.new(20),
                Choice::Raise.new(50),
                Choice::Check.new(200),
               ]
    @deal_state = :flop
    @player_state = Player.new @player, 200, 0, @pocket, false
    @oponents_state = []

    @game_state = State.new *[
                              @deal_state,
                              @player_state,
                              @oponents_state,
                              @cards,
                              @choices
                             ]
  end

  def test_action
    input_request = @player.action @game_state

    assert input_request.instance_of? Request::Input
    assert_equal input_request.key, :choice

    chips_request = @player.action @game_state, {choice: :raise}
    assert chips_request.instance_of? Request::Input
    assert_equal chips_request.key, :chips

    choice = @player.action @game_state, {choice: :raise, chips: 20}
    assert choice.is_a? Choice
    assert_equal 70, choice.chips
    assert_equal :raise, choice.type

    choice = @player.action @game_state, {choice: :fold}
    assert choice.is_a? Choice
    assert_equal 0, choice.chips
    assert_equal :fold, choice.type
  end
end
