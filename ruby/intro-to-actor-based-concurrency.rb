## A gentle introduction to actor-based concurrency
## https://practicingruby.com/articles/gentle-intro-to-actor-based-concurrency?u=e43d1d9b8c
##

# Celluloid framework has brought us a convenient and clean way to implement
# actor-based concurrent systems in Ruby.
require 'thread'
require 'celluloid'

# The Dining Philosophers Problem
#
# In this problem, five philosophers meet to have dinner. They sit at a round
# table and each one has a bowl of rice in front of them. There are also five
# chopsticks, one between each philosopher. The philosophers spent their time
# thinking about The Meaning of Life. Whenever they get hungry, they try to eat.
# But a philosopher needs a chopstick in each hand in order to grab the rice. If
# any other philosopher has already taken one of those chopsticks, the hungry
# philosopher will wait until that chopstick is available.

class Chopstick
  def initialize
    @mutex = Mutex.new
  end

  def take
    @mutex.lock
  end

  def drop
    @mutex.unlock
  rescue ThreadError
    puts "Trying to drop a chopstick not acquired."
  end

  def in_use?
    @mutex.locked?
  end
end

class Table
  def initialize(num_seats)
    @chopsticks  = num_seats.times.map { Chopstick.new }
  end

  def left_chopstick_at(position)
    index = (position - 1) % @chopsticks.size
    @chopsticks[index]
  end

  def right_chopstick_at(position)
    index = position % @chopsticks.size
    @chopsticks[index]
  end

  def chopsticks_in_use
    @chopsticks.select { |f| f.in_use? }.size
  end
end

# Solution that leads to Deadlock
# ==============================================================================
# The problem here is due to fact that philosopher must have both chopsticks
# around him to be able to eat. In this solution he may get only one of them,
# holding it while waiting for the other one. If every philosopher takes one
# chopstick, it will lead to deadlock.
class Philosopher
  def initialize(name)
    @name = name
  end

  def dine(table, position)
    @left_chopstick  = table.left_chopstick_at(position)
    @right_chopstick = table.right_chopstick_at(position)

    loop do
      think
      eat
    end
  end

  def think
    puts "#{@name} is thinking"
  end

  def eat
    take_chopsticks

    puts "#{@name} is eating."

    drop_chopsticks
  end

  def take_chopsticks
    @left_chopstick.take
    @right_chopstick.take
  end

  def drop_chopsticks
    @left_chopstick.drop
    @right_chopstick.drop
  end
end

begin
  names = %w{Heraclitus Aristotle Epictetus Schopenhauer Popper}

  table        = Table.new(names.size)
  philosophers = names.map { |name| Philosopher.new(name) }

  threads = philosophers.map.with_index do |philosopher, i|
    Thread.new { philosopher.dine(table, i) }
  end

  threads.each(&:join)
end if false

# Coordinated mutex based solution
# ==============================================================================
# This solution introduces Waiter object. It will make sure that if the number
# of used chopsticks is four(or more), philosopher will wait until someone
# finishes eating. This will ensure that at leas one philosopher will be able to
# eat at any time, avoiding deadlock condition.
class Waiter
  def initialize(capacity)
    @capacity = capacity
    @mutex    = Mutex.new
  end

  def serve(table, philosopher)
    # Here is critical region of the solution. If we didn't protect this code
    # with mutex, there is a chance that scheduler will take controll from the
    # philosopher right after the chopsticks_in_use check and another
    # philosopher may take the chopsticks, leading again to deadlock.
    @mutex.synchronize do
      sleep(rand) while table.chopsticks_in_use >= @capacity
      philosopher.take_chopsticks
    end

    philosopher.eat
  end
end

class Philosopher

  # ... all omitted code same as before

  def dine(table, position, waiter)
    @left_chopstick  = table.left_chopstick_at(position)
    @right_chopstick = table.right_chopstick_at(position)

    loop do
      think

      # instead of calling eat() directly, make a request to the waiter
      waiter.serve(table, self)
    end
  end

  def eat
    # removed take_chopsticks call, as that's now handled by the waiter

    puts "#{@name} is eating."

    drop_chopsticks
  end
end

begin
  names = %w{Heraclitus Aristotle Epictetus Schopenhauer Popper}

  philosophers = names.map { |name| Philosopher.new(name) }

  table  = Table.new(philosophers.size)
  waiter = Waiter.new(philosophers.size - 1)

  threads = philosophers.map.with_index do |philosopher, i|
    Thread.new { philosopher.dine(table, i, waiter) }
  end

  threads.each(&:join)
end if false

# An actor-based solution using Celluloid
# ==============================================================================
#
# * Each class that mixes in Celluloid becomes an actor with its own thread of
#   execution.
# * Each method call run trough async proxy will be intercepted and stored into
#   actor's mailbox. The actor's thread will sequentially execute these methods
#   one after another.
# * This behavior makes it so that we don't need to manage threads and mutex
#   synchronization explicitly. The Celluloid handles that for us under the
#   hood.
# * If we encapsulate all data inside actors objects, only the actor's thread
#   will be able to access and modify its own data. That prevents the
#   possibility of two threads writing to a critical region at the same time,
#   which eliminates the risk of deadlocks and data corruption.
#
class Philosopher
  include Celluloid

  def initialize(name)
    @name = name
  end

  # Switching to the actor model requires us get rid of our
  # more procedural event loop in favor of a message-oriented
  # approach using recursion. The call to think() eventually
  # leads to a call to eat(), which in turn calls back to think(),
  # completing the loop.
  def dine(table, position, waiter)
    @waiter = waiter
    @position = position

    @left_chopstick  = table.left_chopstick_at(position)
    @right_chopstick = table.right_chopstick_at(position)

    think
  end

  def think
    puts "#{@name}(#{@position}) is thinking."
    sleep(rand)

    # Asynchronously notifies the waiter object that the philosophor
    # is ready to eat.
    @waiter.async.request_to_eat(Actor.current)
  end

  def eat
    puts "#{@name} (#{@position}) wants to eat."
    take_chopsticks

    puts "#{@name} (#{@position}) is eating."
    sleep(rand)

    drop_chopsticks

    # Asynchronously notifies the waiter that the philosopher
    # has finished eating.
    @waiter.async.done_eating(Actor.current)

    think
  end

  def take_chopsticks
    @left_chopstick.take
    @right_chopstick.take
  end

  def drop_chopsticks
    @left_chopstick.drop
    @right_chopstick.drop
  end

  # This code is necessary in order for Celluloid to shut down cleanly.
  def finalize
    drop_chopsticks
  end
end

class Waiter
  include Celluloid

  def initialize
    @eating   = []
  end

  # Because synchronized data access is ensured by the actor model, this code is
  # much more simple than its mutex-based counterpart. However, this approach
  # requires two methods (one to start and one to stop the eating process),
  # where the previous approach used a single serve() method.
  def request_to_eat(philosopher)
    return if @eating.include?(philosopher)

    @eating << philosopher
    philosopher.async.eat
  end

  def done_eating(philosopher)
    @eating.delete(philosopher)
  end
end

begin
  names = %w{Heraclitus Aristotle Epictetus Schopenhauer Popper}

  philosophers = names.map { |name| Philosopher.new(name) }

  # No longer needs a "capacity" argument.
  waiter = Waiter.new
  table = Table.new(philosophers.size)

  philosophers.each_with_index do |philosopher, i|
    # No longer manually create a thread, rely on async() to do that for us.
    philosopher.async.dine(table, i, waiter)
  end

  sleep
end

# Log
#
### Heraclitus(0) is thinking.
### Aristotle(1) is thinking.
### Epictetus(2) is thinking.
### Schopenhauer(3) is thinking.
### Popper(4) is thinking.
#?? Epictetus (2) wants to eat.
#-> Epictetus (2) is eating.
#?? Aristotle (1) wants to eat.
#?? Schopenhauer (3) wants to eat.
#-> Aristotle (1) is eating.
### Epictetus(2) is thinking.
#-> Schopenhauer (3) is eating.
### Aristotle(1) is thinking.
#?? Popper (4) wants to eat.
#?? Heraclitus (0) wants to eat.
#-> Heraclitus (0) is eating.
#?? Epictetus (2) wants to eat.
#-> Epictetus (2) is eating.
### Schopenhauer(3) is thinking.
### Epictetus(2) is thinking.
#?? Aristotle (1) wants to eat.
#?? Schopenhauer (3) wants to eat.
#-> Aristotle (1) is eating.
#-> Popper (4) is eating.
### Heraclitus(0) is thinking.
#?? Epictetus (2) wants to eat.
### Popper(4) is thinking.
#-> Schopenhauer (3) is eating.
#?? Heraclitus (0) wants to eat.
### Aristotle(1) is thinking.
#-> Heraclitus (0) is eating.
#?? Aristotle (1) wants to eat.
### Heraclitus(0) is thinking.
#?? Popper (4) wants to eat.
#-> Epictetus (2) is eating.
### Schopenhauer(3) is thinking.
#-> Popper (4) is eating.
#?? Heraclitus (0) wants to eat.
#?? Schopenhauer (3) wants to eat.
### Popper(4) is thinking.
#-> Aristotle (1) is eating.
### Epictetus(2) is thinking.
#-> Schopenhauer (3) is eating.
# .....

# Actors implementation
# ==============================================================================

module Actor
  module ClassMethods
    def new(*args, &block)
      Proxy.new(super)
    end
  end

  class << self
    def included(klass)
      klass.extend(ClassMethods)
    end

    def current
      Thread.current[:actor]
    end
  end

  class Proxy
    def initialize(target)
      @target  = target
      @mailbox = Queue.new
      @mutex   = Mutex.new
      @running = true

      @async_proxy = AsyncProxy.new(self)

      @thread = Thread.new do
        Thread.current[:actor] = self
        process_inbox
      end
    end

    def async
      @async_proxy
    end

    def send_later(meth, *args)
      @mailbox << [meth, args]
    end

    def terminate
      @running = false
    end

    def method_missing(meth, *args)
      process_message(meth, *args)
    end

    private

    def process_inbox
      while @running
        meth, args = @mailbox.pop
        process_message(meth, *args)
      end

    rescue Exception => ex
      puts "Error while running actor: #{ex}"
    end

    def process_message(meth, *args)
      @mutex.synchronize do
        @target.public_send(meth, *args)
      end
    end
  end

  class AsyncProxy
    def initialize(actor)
      @actor = actor
    end

    def method_missing(meth, *args)
      @actor.send_later(meth, *args)
    end
  end
end
