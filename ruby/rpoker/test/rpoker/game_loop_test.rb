require "test_helper"

class TestGameLoop  < MiniTest::Unit::TestCase
  def setup
    @game = Rpoker::Game::Holdem.new
    @deck = Rpoker::Deck::French.new.shuffle
    @finder = Rpoker::Finder::Holdem.new
    @interface = Rpoker::Interface::CliDebug.new

    @players = [:p, :k, :m, :a].map do |name|
      Rpoker::Player::Human.new name
    end

    @game.deck = @deck
    @game.finder = @finder
    @game.players.push *@players

    @game_loop = Rpoker::GameLoop.new @game, @interface
  end

  def test_initialization
    # TODO:
    #
    # NOTE: use bin/rpoker for now
    #
  end
end
