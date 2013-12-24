;;; http://clojure.com/blog/2013/06/28/clojure-core-async-channels.html

(ns exploring.core.async
  "| There comes a time in all good programs when components or subsystems
   | must stop communicating directly with one another.
        — Rich Hickey

   Core.async provides a convenient way to decouple program’s consumers and
   producers by introducing explicit queues between them. This allows the
   program components to communicate asynchronously(indirectly) and core.async
   provides the methods needed to coordinate asynchronous channels.

   It is implementation of CSP mostly inspired by GO, but it is implemented as
   just a clojure lib based upon sexp manipulation and this is the big deal
   about it. It is excellent showcase how powerful macros are.

   It should be noted that Clojure's mechanisms for concurrent use of state
   remain viable, and channels are oriented towards the flow aspects of a
   system.

   In ClojureScript environment core.async can be used to simplify asynchronous
   programming by substituting callbacks with channels.

   Practices:

      - It helps coordinating multiple processes with one or more coordination
     processes, without any obvious use of mutation.

      - It is always safe to put clojure data structures into channels and don't
     care, because they are immutable. This makes the language even better fit
     this style of programming than GO.

      - Possible deadlocks.
   "
  (:require [clojure.core.async :refer :all]))

;;; Channels can be thought as blocking queues where values can be pushed or
;;; pulled in. By default pushing and pulling are blocking until value is
;;; consumed/provided. We can can use buffered(bounded) channels to make the
;;; communitcation asynchronous. Unbounded buffering is discouraged and
;;; considered a bad practice. There are two ways to perform I/O from channels
;;; and they are often combined.
;;;
;;; When channel is closed it receives nil message. Explicit passing of nil into
;;; the channel is not supported.
(chan)
(chan 10)
;; sliding-buffer 64

;;; Blocking I/O from channels. These operations work in ordinary(native)
;;; threads and blocks them when waiting is required.
(<!! chan)
(>!! chan val)
(thread
;;; Uses native thread and returns a channel. Should be used over future.
 )

;;; Non-blocking I/O(inside of go block). Inversion-of-control(IOC) thread is
;;; created with each go block, it's body is examined for any channel operations
;;; and then transformed into state machine. When reaching a blocking operation
;;; state machine will be parked and the actual thread of control will be
;;; released. When the blocking operation completes the machine will be resumed.
;;; This encapsulates the inversion of control and makes the code look linear.
;;;
;;; This approach helps us avoid wasting system resources by blocking threads
;;; for channel I/O. It reuses a couple of non blocking threads witch is proved
;;; performance boost.
;;;
;;; Go block returns immediately. Its body is run in the same CPU-bound thread
;;; pool used also in futures and agents. This means that blocking I/O in go
;;; blocks is not a good idea.
;;;
(let [ch (chan)]
  (go
   (>! chan 10)
   (let [v (<! chan)]
     (prn v)) ;;= 10
   ))

(<!! (go 10))                   ; Blocks calling thread until go block finishes.

;;; Returns a channel that will close itself in {time} ms. It is used instead
;;; of Thread/sleep in (go) blocks.
(go
 ;; Waits 1000 ms.
 (<! (timeout 1000)))

;;; Ability to pull from many channels and executes code for the first available
;;; value. It is common to use in combination with (timeout N) to set reading
;;; timeout from channel.
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
