;;; http://clojure.com/blog/2013/06/28/clojure-core-async-channels.html

;;; Copy of Go's original idea of CSP.
;;; Unbounded buffering is discouraged!

;;; Ordinary and Inversion-of-control(IOC) threads. IOC threads are created via
;;; (go) blocks.

;;; (GO) takes body and examines it for any channel operations. It will turn the
;;; body into state machine. When reaching a blocking operation state machine
;;; will be parked and the actual thread of control will be released. When the
;;; blocking operation completes the machine will be resumed. This encapsulates
;;; the inversion of control and makes the code look linear.

;;; There comes a time in all good programs when components or subsystems must
;;; stop communicating directly with one another.

;;; (go) macro gives us an ilusion
;; (prn (go 5))

;;; The go blocks are run on a fix sized thread pool (2+number of cores) – this
;;; is plenty for asynchronous workflows where we spend almost all our time
;;; waiting for callbacks and pushing / popping data on channels.
;;; Never Thread/sleep in a go block, use (<! (timeout *msec*))!
;;;
;;; When using multiple threads and blocking I/O.
;;;
;;; It helps coordinating multiple processes with one or more coordination
;;; processes, without any obvious use of mutation.
;;;
;;; go blocks paired with loop/recur allow us to create local event loops
;;;
;;; alts! gives us non-deterministic choice over multiple streams. We will read
;;; from whichever channel has information. When reading from a channel alts!
;;; will return a tuple, the first element is the value read from the channel
;;; and the second element is the channel that was read from. This allows us to
;;; conditionally handle results from different channels as you can see with our
;;; use of condp.

;;; Read from timeout channel instead of sleep to be able to to block the
;;; thread, but to release it for usage from other channels.
(<! (timeout (rand-int 100)))

;;; In traditional JavaScript all the things has callbacks, these callbacks are
;;; called all the time and in any time without any coordination.

;;; The main feature is go blocks in which threads can be parked and resumed on
;;; the (potentially) blocking calls dealing with core.async’s channels. However
;;; if you are using go blocks and blocking IO calls you’re in trouble.

;;; A channel is a queue with one or more publishers and one or more consumers.
;;; The mechanism is simple: producers put data onto the queue, consumers take
;;; data from the queue. Because data in Clojure/ClojureScript is immutable,
;;; channels provide a safe way to communicate between threads. Although the
;;; latter this is not a particularly interesting feature in the context of
;;; ClojureScript, because JavaScript is single-threaded.

;; core.async offers two ways to write to and read from channels: blocking and
;; non-blocking. A blocking write blocks the thread until the channel has space
;; to be written to (the buffer size of a channel is configurable), a blocking
;; read blocks a thread until a value becomes available on the queue to be read.
;; More interesting, and the only type supported in ClojureScript, are
;; asynchronous channel reads and writes to channels, which are only allowed in
;; "go blocks". Go blocks are written in a synchronous style, and internally
;; converted to a state machine that executes them asynchronously.

;; Any JavaScript programmer, looking at this while loop will immediately freak
;; out: you cannot do blocking loops like this: the browser will freeze up for 5
;; seconds. The "magic" of core.async is that internally it converts the body of
;; each go block into a state machine and turns the synchronous-looking channel
;; reads and writes into asynchronous calls.

;;; This seems to be a magic in the compiler, but in Clojure it can be
;;; implemented just as a library.
(let [ch (chan)]
  (go (while true
        (let [v (<! ch)]
          (println "Read: " v))))
  (go (>! ch "hi")
      (<! (timeout 5000))
      (>! ch "there")))

(ns exploring.core.async
  "It provides a convenient way to decouple program’s consumers and producers by
   introducing explicit queues between them. This allows the program components
   to communicate asynchronously and core.async provides the methods needed to
   coordinate asynchronous channels.

     | There comes a time in all good programs when components or subsystems must
     | stop communicating directly with one another.

        — Rich Hickey


   "
  (:require [clojure.core.async :refer [<! >! chan put! take! timeout close!
                                        go alt!]]))

;;; ------------------------------------------------------------------
;;; Synchronous approach

(defn consumer-1 [msg] (prn "1 received" msg))
(defn consumer-2 [msg] (prn "2 received" msg))
(defn producer-1 [& consumers]
  (doseq [msg      (rand-int 100)
          consumer consumers]
    (consumer msg)))

;;; ------------------------------------------------------------------
;;; Asynchronous approach

(defn consumer-1
  "Instead of receiving messages via function invocation, consumers now draw
   messages from a buffered channel. Where a consumer used to consume a single
   message at a time, it now uses a go-loop to continuously consume messages
   from its producer."
  []
  ;; Buffered channel allows the consumer to lag behind the producer by
  ;; specified amount.
  (let [in (chan (sliding-buffer 64))]
    ;; Go block is asynchronous. It returns immediately, but inside its body <!
    ;; and !> will block.
    (go-loop [data (<! in)]
             ;; Channels return nil when closed.
             (when data
               (prn "1 received" data)
               ;; Block and recur with next value.
               (recur (<! in))))
    in))

(defn consumer-2 []
  (let [in (chan (sliding-buffer 64))]
    (go-loop [data (<! in)]
             (when data
               (prn "2 received" data)
               (recur (<! in))))
    in))


;;; Producer is asynchronous implicitly by using buffered channels. Otherwise if
;;; some of the consumers works slowly it (>!) will wait until it is ready to
;;; consume more messages.
(defn producer-1
  [& consumers]
  (go
   (doseq [msg      (rand-int 100)
           consumer  consumers]
     ;; Take from a timeout channel to simulate a short pause.
     (<! (timeout 100))

     ;; Put a message into out channel.
     (>! out msg))))

;;; ------------------------------------------------------------------
;;; Synchronous approach

;;; Blocking I/O outside of go block. Not supported by ClojureScript because
;;; JavaScript has no threads. They are used with native threads instead of go
;;; blocks. They can be combined with thread macro.
(<!! chan)
(>!! chan val)

;;; Uses native thread and returns a channel. Should be used over future.
;;; Channels can be used from bothg <!/<!! and !</!!<
(thread)

;;; Blocking I/O inside of go block.
(go (let [v (<! chan)] (prn v))
    (>! chan val))

;;; Returns a channel that will have value pushed into time ms. After value is
;;; suplied it will be closed.
(timeout time)

(go
 ;; Spawns a lightweight thread. Returns a channel with queued last value in
 ;; block. By wrapping with <!! it will block until block is completed.
 )

;;; combining blocks and channels make it interesting
;;; channels can be thought as blocking queues that go blocks can push or pull
;;; values from
;;; By default pushing and pulling are blocking until value is consumed/provided.

;;; Ability to pull from many channels and executes code for the first available
;;; value.
(go (alts! ch1 ch2 ch3)) ;= [val provider]

;;; How alts! can be use to set timeout for channel to provide a value.
(defn -main [& args]
  (let [c (chan)]
    (go
      (Thread/sleep (rand-int 1000))
      (>! c "success!"))

    (<!!
      (go
        (let [[result source] (alts! [c (timeout 500)])]
          (if (= source c)
            (println "Got a value!")
            (println "Timeout!")))))))

;;; It is always safe to put clojure data structures into channels and don't
;;; care, because they are immutable.

(alt ) ;; (alts) but supports priority.

;; It should be noted that Clojure's mechanisms for concurrent use of state
;; remain viable, and channels are oriented towards the flow aspects of a
;; system.

;;; Possible deadlocks.

;; The big deal
;; But here’s the big deal about this: although core.async looks like it’s
;; deeply integrated into the language, it is just a library!
;; It’s not a separate compiler. It’s not a new language. And it’s not a special
;; version of Clojure.
;; Since Clojure supports macros - like all Lisps - the core team was able to
;; create the syntax required to easily use core.async. And that’s the beauty of
;; it!
;; The Lisp advantage, once again.
;; Clojure’s advantage
;; Now one thing I haven’t mentioned is that Clojure is particularly well suited
;; for this - and in a way even more so than Go: Clojure is opinionated and
;; favours immutability.
;; That means that when using channels - and in fact any type of concurrent
;; programming - you can safely share your data structures between concurrent
;; units of work. Since they’re immutable, you can’t shoot yourself in the foot.
;; One last thing: core.async states as one of its goals Clojurescript
;; compatibility, bringing channel based concurrent programming to the browser.
;; Exciting stuff.

(defn putdown [ch] (>! ch :fork))
(go (putdown (chan)))
