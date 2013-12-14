(ns exploring.concurrency
  "Writing multi-threaded programs is one of the most difficult things we will
  face as programmers.

  DEADLOCKS - situation in which two or more competing actions
  are each waiting for the other to finish, and thus neither ever does.

  RACE CONDITION - multiple threads try to access/change shared resource at
  the same time(check-then-act).

  In which order should locks be acquired and released?
  Does a reader have to acquire a lock to read a value another thread might be
  writing to?
  How can multithreaded programs that rely upon locks be comprehensively tested?")


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
    ;; effective than creating new thread every time on need. Futures use
    ;; unbound thread pool suitable for I/O bound operations.
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
  ;; This is made possible by Clojure's implementation of software transactional
  ;; memory, which is used to manage all change applied to refs.
  ;;
  ;; Software transactional memory is any method for coordinating multiple
  ;; concurent modification to a shared set of storage locations. It is
  ;; simplification to concurrent programming just like garbage collection is
  ;; simplification to memory management. It helps you avoid manual locks
  ;; management.
  ;;
  ;; Each STM transaction ensures that the changes to refs are made:
  ;;   - atomically => all or none
  ;;   - consistently => changes must satisfy constrains or transaction fails
  ;;   - in isolation => transaction doesn't affect states of involved refs
  ;; outside itself during its execution. It uses internal snapshot.
  ;;
  (let [r (ref 1)
        t (ref 2)
        y (ref 3)]
    ;; Establishes scope of a transaction. All ref modifications must happen
    ;; into a transaction, the processing of which happens synchronously. The
    ;; thread that initializes transaction will block until transaction passes.
    ;; Similarly to atoms at the bottom is comapre and set for each changed ref
    ;; and if at least one of them fails, whole transaction is retired. Nested
    ;; (dosync) calls are joined into one transaction. Other threads can safely
    ;; read the refs involved in transaction and this cannot block them.
    (dosync
     (alter r inc)   ;; compare-and-set
     (ref-set y 4)   ;; compare-and-set

     ;; May be used when functions changing value of ref are reorderable and we
     ;; don't care about intermediate values, but only of the final one.
     ;; In-transaction value it is possible never to be actual value. Function
     ;; passed to commute will be called once again at the end with last value
     ;; to produce committed value. It should never cause retry.
     (commute t #(do (prn %) (inc %))))
    [@r @t @y])

  ;; Will throw error when used in transaction.
  (defn unsafe []
    (io! (println "writing to database...")))

  ;; 1. No I/O in transactions.
  ;; 2. Refs should hold immutable data.
  ;; 3. Transactions should be small and fast in oreder to avoid retries.
  ;; Live-lock strategies:
  ;;
  ;; 1. Barging - giving priority to older transactions.
  ;; 2. Retry limit.
  (dosync
   @(future (dosync (ref-set t 0)))
   (ref-set t 1))

  (let [r (ref 1)]
    ;; Readers may retry
    ;;
    ;; Fore reference types deref is guaranteed to never block, but inside of
    ;; transaction it may trigger a retry. If value for ref since beginning of
    ;; the transaction is requested later, but then it is not in the history due
    ;; to ref changes by other threads, it can be deliverd and this will trigger
    ;; retry. History is limited and when ref is frequently updated such
    ;; scenarios are possible. History size can be changed.
    (future (dotimes [_ 500] (dosync (Thread/sleep 20) (alter r inc))))
    @(future (dosync (print @r "|") (Thread/sleep 1000) @r))

    (comment
      ;; This will work perfectly. By increasing max-history, we ensure that
      ;; there will be enough space for our in-transaction value to fill in, and
      ;; by increasing min-history we ensure that our in-transaction value will
      ;; be kept long enough for transaction to finish.
      (let [(r (ref 1 :min-history 50 :max-history 100))]
        (future (dotimes [_ 500] (dosync (Thread/sleep 20) (alter r inc))))
        @(future (dosync (print @r "|") (Thread/sleep 1000) @r))))

    ;; Workarounds that won't trigger retry.
    (comment
      @(future (dosync (print @r "|") (Thread/sleep 1000)))
      @(future (dosync (let [c @r] (print c "|") (Thread/sleep 1000) c))))

    [(ref-min-history r)
     (ref-max-history r)
     (ref-history-count r)
     r])

  ;; Ensure will make sure that ref is in last actual state during transaction.
  ;; It will make transaction retry if ref value is changed by other thread.
  (let [r (ref 1)
        t (ref 1)]
    (dosync (let [rr (ensure r)
                  tt (ensure t)]
              [rr tt])))

  ;; Vars differ from other reference types in that their state changes are not
  ;; managed in time, but they provide namespace global identities, that should
  ;; be immutable(or we introduce global mutable state) or dynamically scoped.
  ;;
  ;; Evaluating a symbol normally results in looking for variable in current
  ;; namespace and dereferencing it to obtain its value.
  ;;
  ;; All top level functions and values are stored in vars and defined using
  ;; def special form. Def not only installs var into a namespace, but copies
  ;; the meta-data found on the symbol provide for its name to var itself. This
  ;; metadata may alter the behavior of the var.
  (do
    (def v
      "Some not so useful variable."
      10)
    ;; Print docs to stdout.
    (doc v)
    [v  #'v @#'v])

  ;; Private vars are accessible only by it's full name in other namespaces.
  (do
    (def ^:private p 10)
    @#'p)

  ;; Constant variable. All its uses(until it is redefined) will be in-lined.
  (do
    (def ^:const c 10)
    @#'c)

  ;; Dynamic vars allow gives us an opportunity to override the root binding of
  ;; the var inside a (binding) form body only for current thread.
  ;; It has a variety of uses. It's common to use it as extra implicit argument
  ;; for functions. But reversed use is also very handy - funcions to provide
  ;; multiple side channels of return values. See second example.
  (do
    (def ^:dynamic *d* 10)
    (binding [*d* 20]
      (prn *d*)
      ;; Will not work.
      (doto (Thread. #(prn *d*))
        .start
        .join)
      ;; Clojure build-in primitives are smart.
      (future (prn *d*))

      ;; This will not work, if lazy seq is realized outside of the
      ;; biniding body.
      (map prn [*d*])

      (binding [*d* 30]
        (prn *d*)
        (binding [*d* 40]
          (prn *d*)))))

  (do
    (def ^:dynamic *response-code* nil)
    (defn http-get
      [url-string]
      (let [conn (-> url-string java.net.URL. .openConnection)
            response-code (.getResponseCode conn)]
        (when (thread-bound? #'*response-code*)
          (set! *response-code* response-code))
        (when (not= 404 response-code) (-> conn .getInputStream slurp))))
    (binding [*response-code* nil]
      (let [content (http-get "http://google.com/bad-url")]
        (println "Response code was:" *response-code*))))


  )
