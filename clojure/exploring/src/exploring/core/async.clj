;;; Copy of Go's original idea of CSP.

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

;;; Blocking I/O outside of go block.
(<!! chan)
(>!! chan val)
