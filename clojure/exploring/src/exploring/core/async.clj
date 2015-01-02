;;; http://clojure.com/blog/2013/06/28/clojure-core-async-channels.html
;;; http://hueypetersen.com/posts/2013/07/10/code-read-of-core-async-timeouts
;;; http://hueypetersen.com/posts/2013/08/02/the-state-machines-of-core-async/
(ns exploring.core.async
  "| There comes a time in all good programs when components or subsystems
   | must stop communicating directly with one another.
        — Rich Hickey

   Core.async provides a convenient way to decouple program’s consumers and
   producers by introducing explicit queues/channels between them and tools to
   coordinate these channels. Sync or async I/O from channels is supported but
   async the interesting one.

   It is implementation of CSP mostly inspired by GO, but it is implemented as
   pure clojure lib based upon sexp manipulation and this is the big deal
   about it. It is excellent showcase of how powerful macros are.

   In ClojureScript environment core.async can be used to simplify asynchronous
   programming by substituting callbacks hell with more simple and concise
   abstraction - channels.

   Practices:

      - It helps coordinating multiple processes with one or more coordination
     processes, without any obvious use of mutation.

      - It is always safe to put clojure data structures into channels and don't
     care, because they are immutable. This makes the language even better fit
     this style of programming than GO.

      - It introduces the possibility of deadlock.

   It should be noted that Clojure's mechanisms for concurrent use of state
   remain viable, and channels are oriented towards the flow aspects of a
   system, but they can be used to parallel work."
  (:require [clojure.core.async :refer :all]))
(let [c (chan)]
  (go
    (>! c 10)
    (prn (<! c))))
;;; Channels can be thought as blocking queues where values can be pushed or
;;; pulled in. By default pushing and pulling are blocking until value is
;;; consumed/provided. We can can use buffered(bounded) channels to make the
;;; communitcation asynchronous. Unbounded buffering is discouraged and
;;; considered a bad practice. There are two ways to perform I/O from channels
;;; and they are often combined.
(chan)
(chan 10)                   ;; blocks when full
(chan (dropping-buffer 10)) ;; drops newest
(chan (sliding-buffer 10))  ;; drops oldest

;;; When channel is closed it receives nil message. Explicit passing of nil into
;;; the channel is not supported.
(close! (chan))

;;; Blocking I/O from channels. These operations work in ordinary(native)
;;; threads and blocks them when waiting is required.
(<!! chan)
(>!! chan val)
(alts!! [chan1 chan2])
(thread
;;; Uses native thread and returns a channel. Should be used over future.
 )

;;; Non-blocking I/O(inside of a go block). Inversion-of-control(IOC) thread is
;;; created with each go block, it's body is examined for any channel operations
;;; and then transformed into state machine. When reaching a blocking operation
;;; state machine will be parked and the actual thread of control will be
;;; released. When the blocking operation completes the machine will be resumed.
;;; This encapsulates the inversion of control and makes the code look linear.
;;;
;;; This approach helps us avoid wasting system resources by blocking threads
;;; for channel I/O. It reuses a couple of non blocking threads witch is proved
;;; to be a performance boost. It is especially useful when working with async
;;; APIs. Adn since go blocks are lightweight processes not bound to threads, we
;;; can have LOTS of them!
;;;
;;; Go block returns immediately. Its body is executed in the same CPU-bound
;;; thread pool used also in futures and agents. This means that blocking I/O in
;;; go blocks is not a good idea.
;;;
(let [ch (chan)]
  (go
   (>! chan 10)
   (let [v (<! chan)]
     (prn v)) ;;= 10
   ))

(<!! (go 10))                   ; Blocks calling thread until go block finishes.
(<!! (go))                      ;= nil Block is garbage-collected and closed.

;;; Returns a channel that will close itself in {time} ms. It is used instead
;;; of Thread/sleep in (go) blocks.
(go
 ;; Waits 1000 ms.
 (<! (timeout 1000)))

;;; Ability to pull from many channels and executes code for the first available
;;; value. This is the killer future of channels over queues. It is common to
;;; use in combination with (timeout N) to set reading timeout from channel.
(go (alts! [ch1 ch2 ch3]))              ;= [val provider]

;;; Common macro for introducing local event loops. It basically expnads
;;; to (go (loop ...))
(go-loop [v (<! ch)]
         ;; Do stuff
         (recur (<! ch)))

;;; ----------------------------------------------------------------------------
;;; ClojureScript
;;; ----------------------------------------------------------------------------

;;; ClojureScript compatibility is one of the main goals of the lib, but there
;;; are some limitations of the JavaScript platform.
;;;
;;;   - Thread blocking operations are not supported since there are no threads
;;;     in JS.
;;;   - All the code is executed in the single thread of execution provided by
;;;     the JS VM. This eliminates all the possibilities for parallel
;;;     computation.
;;;
;;; In traditional JavaScript all the things has callbacks, these callbacks are
;;; called all the time and in any time without any coordination. This is the
;;; main source of incidental complexity in the language. Core.async addresses
;;; this by providing channels as a clean and more simple way for synchronizing
;;; all the asynchronous operations going on.

;;; ============================================================================
;;; EXAMPLE 1
;;; ============================================================================

;;; Synchronous approach when consumers communicate directly.

(defn consumer-1 [msg] (future (prn "1 received" msg)))
(defn consumer-2 [msg] (future (prn "2 received" msg)))
(defn producer-1 [& consumers]
  (doseq [msg      (rand-int 100)
          consumer consumers]
    (consumer msg)))

;;; Asynchronous approach where there is no direct communication between
;;; producers and consumers. There are explicit queues between them.

(defn consumer-1 []
  (let [ch (chan 10)]
    (go-loop [val (<! ch)]
             (when val
               (prn "1 received" val)
               (recur (<! ch))))
    ch))

(defn consumer-2 []
  (let [ch (chan 10)]
    (go-loop [val (<! ch)]
             (when val
               (prn "2 received" val)
               (recur (<! ch))))
    ch))

(defn producer-1
  [& consumers]
  (go
   (doseq [message(rand-int 100)
           consumer consumers]
     (<! (timeout 100))
     (>! consumer msg))))

;;; ============================================================================
;;; EXAMPLE 2
;;; ============================================================================

(let [ch (chan)]
  (go-loop [v (<! ch)]
           (if v
             (do (prn "Read: " v)
                 (recur (<! ch)))
             (prn "Channel closed!")))

  (go (>! ch "1")
      (<! (timeout 500))
      (>! ch "2")
      (<! (timeout 100))
      (>! ch "3")
      (<! (timeout 1000))
      (>! ch "4")
      (close! ch)))

;;; ============================================================================
;;; EXAMPLE 3
;;; ============================================================================

(let [ch (chan)]
  (go
   (<! (timeout (rand-int 1000)))
   (>! ch "value"))

  (go
   (let [[res source] (alts! [ch (timeout 500)])]
     (if (= source ch)
       (prn "Value")
       (prn "Timeout")))))

;;; ============================================================================
;;; EXAMPLE 4
;;; ============================================================================

(let [ch (chan 40)]
  ;; Producer
  (go-loop [i 60]
           (when-not (zero? i)
             (>! ch (rand-int 10))
             (<! (timeout (rand-int 50)))
             (recur (dec i))))

  ;; Parallel consumers
  (go-loop [v (<! ch)]
           (when v (<! (timeout (rand-int 50))) (prn "1 -> " v) (recur (<! ch))))
  (go-loop [v (<! ch)]
           (when v (<! (timeout (rand-int 50))) (prn "2 -> " v) (recur (<! ch))))
  (go-loop [v (<! ch)]
           (when v (<! (timeout (rand-int 50))) (prn "3 -> " v) (recur (<! ch))))
  (go-loop [v (<! ch)]
           (when v (<! (timeout (rand-int 50))) (prn "4 -> " v) (recur (<! ch)))))

;;; ============================================================================
;;; EXAMPLE 5
;;; ============================================================================

(defn pmax
  "Process messages from input in parallel with at most max concurrent
   operations, but creating the minimum amount threads to keep up with the
   input speed.

   Invokes f on values taken from input channel. f must return a
   channel, whose first value (if not closed) will be put on the output
   channel.

   Returns a channel which will be closed when the input channel is
   closed and all operations have completed.

   Creates new operations lazily: if processing can keep up with input,
   the number of parallel operations may be less than max.

   Note: the order of outputs may not match the order of inputs.
   Source: http://stuartsierra.com/2013/12/08/parallel-processing-with-core-async"
  [max f input output]
  (go-loop [tasks #{input}] ;; Start only with input task.
    (when (seq tasks)
      ;; Read value from a task channel ready to provide it. If there are
      ;; multiple values ready, non-deterministic choice will be made as by
      ;; alts! semantics.
      (let [[value task] (alts! (vec tasks))]
        (if (= task input)
          (if (nil? value)
            ;; When input is closed.
            (recur (disj tasks task))

            ;; When max - 1 tasks running temporarily stop reading input.
            (recur (conj (if (= max (count tasks))
                           (disj tasks input)
                           tasks)
                         ;; Add new processing task.
                         (f value))))

          ;; One processing task finished: continue reading input.
          (do (when-not (nil? value) (>! output value))
              ;; Add input after each processing task.
              (recur (-> tasks (disj task) (conj input)))))))))
