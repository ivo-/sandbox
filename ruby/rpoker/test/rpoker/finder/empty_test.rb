require "test_helper"

class TestStreak
  attr_reader :strength

  def initialize(strength)
    @strength = strength
  end

  def test(_)
    @strength
  end
end

class TestEmptyFinder < MiniTest::Unit::TestCase
  def setup
    @finder = Rpoker::Finder::Empty.new
  end

  def test_finder
    @finder.add_streak TestStreak.new(1)
    @finder.add_streak TestStreak.new(2)
    @finder.add_streak TestStreak.new(3)
    @finder.add_streak TestStreak.new(4)
    @finder.add_streak TestStreak.new(0)

    assert_equal [4, 3, 2, 1, 0], @finder.find_all("_")
    assert_equal 4, @finder.find("")
  end
end
