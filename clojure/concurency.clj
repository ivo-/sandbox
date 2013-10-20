;;; Writing multi-threaded programs is one of the most difficult things we will
;;; face as programmers.
;;;
;;; DEADLOCKS - situation in which two or more competing actions
;;; are each waiting for the other to finish, and thus neither ever does.
;;;
;;; RACE CONDITION - multiple threads try to access/change shared resource at
;;; the same time(check-then-act).
;;;
;;; In which order should locks be acquired and released?
;;; Does a reader have to acquire a lock to read a value another thread might be
;;; writing to?
;;; How can multithreaded programs that rely upon locks be comprehensively tested?
;;;

(comment

  ;; Delays delay computation and evaluates it only once. Multiple threads can
  ;; make attempt to dereference a delay and all of them will wait until first
  ;; one does this. They can be used as optimization mechanism to prevent
  ;; execution of heavy tasks that possibly won't be executed.
  (let [d (delay (+ 1 2))]
    ;; Deref abstraction is defined in clojure.lang.IDeref and any type that
    ;; implements it acts as container for values that can be dereferenced. Many
    ;; of clojure's entities are dereferenceable: delays, futures, reference
    ;; types, promises.
    @d        ;; Nearly always preferable.
    (deref d) ;;

    (realized? d)
    )

  ;; Future evaluates a body of code in another thread and returns immediately
  ;; allowing current thread to continue working. The result of future's
  ;; computation will be retained by the future and will be available when
  ;; future is dereferenced. Just like delay, dereferencing will block until the
  ;; future's body is executed. Future is executed only once and value is
  ;; cached, so next dereferencing will return immediately.
  (let [f (future (Thread/sleep 1000) (+ 1 2))]
    @f                             ;;
    (deref f 1000 :interupt-value) ;; timeout + default value

    ;; Call zero-argument function in new future.
    (future-call #(+ 1 2))

    ;; Futures can be used to perform some operation for retrieving value
    ;; cocurent, not blocking main thread and in very simple way, just like we
    ;; will do it with (delay). When value is requested then thread should be
    ;; blocked. Ex.: file conetent, api call, ect.
    ;; How beautiful is that we can interchange (delay) and (future).

    ;; Futures and agent use the same thread pool and this makes them more
    ;; effective than creating new thread every time on need.
    )

  ;; Promises share many of the mechanisms in delays and futures. Promise can be
  ;; dereferenced with optional timeout and it will block until it has value to
  ;; deliver. A promise can only ever have one value. We can think of them as
  ;; single time pipes. They can produce deterministic behavior in concurrent
  ;; progrram and define explicit relationships between concurrent processes.
  (let [p (promise)]
    (realized? p)
    (deliver p 42)
    @p)
  )


;;; CONCURRENCY is coordination of multiple threads working on shared state.
;;; Multiple threads are making progress.
;;;
;;; PARALLELISM is when multiple threads working on shared state at the same
;;; time(parallel), minimizing coordination overhead. Multiple threads are
;;; making progress at the same time(on different cores).
(comment
  ;; (pmap) behaves like (map), but all iterations are parallelized. It uses as
  ;; may futures as cores available to produce better results.
  (time (dorun (map #(dorun (map + (range %))) (repeat 1 9e6))))
  (time (dorun (pmap #(dorun (map + (range %))) (repeat 4 9e6))))

  (pacalls)
  (pvalues)

  (prn (+ 2 (.. Runtime getRuntime availableProcessors)))

  )

;;; State and Identity
;;;
;;; State - data at exact time. Changing state is unnatural. Every data change
;;; represents completely new state, not old state but changed.
;;;
;;; Identity - current representation of an object trough time. Identity has a
;;; particular state at any point in time, but it doesn't change the history.
;;; Every state should be consistent at any point in time, not like situation
;;; with race-conditions.
;;;
;;; Immutable data structures are perfect for representing state.
;;;
;;; Referenct type - containers with defined concurrency and change semantics,
;;; that can hold any value and be used as a stable identity. That value can be
;;; changed by certain functions, and can be accessed with dereferencing for all
;;; reference types. Dereferencing returns immediately current value in ref
;;; type and will never blcok, regardless of operations being applied to it by
;;; other threads.
;;;
;;; Each reference type has its own semantics and operations for managing
;;; change of the state. All reference types:
;;;
;;; - can have meta data and it can be changed with (alter-meta!)
;;; - can notify functions you specify for state changes(watchers)
;;; - can enforce state constraints(validators)
;;;
;;; Coordination. Coordinated operation is where multiple actors must cooperate
;;; to produce correct result. Uncoordinated operations is where multiple actors
;;; cannot impact each other negatively because their contexts are separated.
;;;
;;; Synchronization. Synchronized operations are those where caller's thread
;;; waits/blocks/sleeps until it may have exclusive access to a given context,
;;; whereas asynchronous operations are those that can be started or scheduled
;;; without blocking caller's thread.
;;;
;;; Atoms  - uncoordinated, synchronous
;;; Refs   - coordinated, synchronous
;;; Agents - uncoordinated, asynchronous
;;;
;;; * coordinated and asynchronous is more common in distributed systems, but
;;;   clojure's goal is to address in-process concurrency and parallelism.
;;;
;;;

(defmacro futures
  "Creates n futures for each expression provided."
  [n & exprs]
  (vec (for [_ (range n)
             expr exprs]
         `(future ~expr))))

(defmacro wait-futures
  "Creates n futures for each expression provided and
   waits for them to finish."
  [& args]
  `(doseq [f# (futures ~@args)]
     @f#))

;;; Atoms are the most basic reference type; they are identities that implement
;;; synchronous, uncoordinated, atomic compare-and-set modification. Thus,
;;; operations that modify the state of atoms block until the modification is
;;; complete, and each modification is isolated. There is no way to coordinate
;;; modifications of two or more atoms.
(comment
  (let [a (atom {:a 1 :b 2})]
    ;; Compare and set semantics means that if value of the atom is changed
    ;; before update function returns, it will be retried until it succeeds.
    ;; This may cause live-lock.
    (swap! a assoc :c 3) ;;= {:a 1 :b 2 :c 3}
    @a)

  (let [a (atom 1)]
    ;; If value for a is know, we can just compare and set. It doesn't respect
    ;; value semantics. It requires expected value to be identical to current
    ;; one.
    (compare-and-set! a 1 2) ;;= true|false

    ;; Just set new value. It is not preferred, but it's good for debugging.
    (reset! a 3)
    @a)

  ;; Watchers are functions that are called each time reference type's state is
  ;; changed. All ref types starts with no watchers, but they can be registered
  ;; at any time. Watch functions are called synchronously on the same thread,
  ;; that affected the reference. Even if the new state is identical to the old
  ;; one, watcher will be called.
  (let [a (atom 1)
        w (fn [key identity old new]
            (println key old "=>" new))]
    (add-watch a :echo w)
    (swap! a inc)
    (remove-watch a :echo)
    @a)

  ;; Validators allow us to constrain reference's state however we want.
  ;; Validator is a function with one argument that is invoked just before any
  ;; propose new state is installed into reference. If a validator returns
  ;; logically false or throws an exception, then state change is aborted with
  ;; an exception. It is part of all reference types.
  ;; Validator can throw an exception instead of returning false, to produce
  ;; better message.
  (let [a (atom 1 :validator pos?)]
    (set-validator! a pos?) ;; add/change validator
    (swap! a dec))          ;;= exception

  ;; Refs are clojure's coordinated reference type. They guarantee:
  ;;  - all involved refs to never be in observable inconsistent state
  ;;  - no possibility of race conditions among the involved refs
  ;;  - no manually use of locks or other low level synchronization primitives
  ;;  - no possibility of deadlocks
  ;;  - no possibility of livelocks
  ;; This is made possible by Clojure's implementation of software transaction
  ;; memory, which is used to manage all change applied to refs.
  (let [r (ref 1)]
    )
  )
