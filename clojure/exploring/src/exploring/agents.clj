(ns exploring.agents
  "Agents are uncoordinated, asynchronous reference type. That means changes to
  multiple atoms shouldn't be coordinated and those changes happen away from
  thread of execution that schedules them. Some characteristics:

    1. I/O and other side-effecting functions may be safely used with agents.
    2. Agents are STM-aware, and they can be used in context of transactions to
       poduce side effects when transaction passes.

  Each agent can receive actions, which will be added to queue and executed in
  receive order. Each action is queued on send and it returns immediately.
  Actions queue is processed sequentially and each action is executed from
  specified by send function thread pool. Result from each action is used as new
  agent state.
  ")

(comment
  (let [a (agent 10)]
    ;; Send and send-off are used to change agent state. They follow the same
    ;; semantics as equivalents from other reference types. Difference between
    ;; them is the thread pools each of them uses. They both return the agent
    ;; involved.
    ;;
    ;; Send uses fixed sized thread pool configured to use maximum available
    ;; parallelizablity from CPU and obviously is suitable for non-blocking
    ;; CPU-bound operations and never for actions that perform I/O, which will
    ;; prevent other CPU-bound operations from executing.
    (send a + 2)
    ;; Send-off is evaluated in non-limited thread poll(the same one used by
    ;; futures), which allows any number of potentially blocking, non-CPU-bound
    ;; actions to be evaluated concurrently.
    (send-off a + 3)

    (prn (.getQueueCount a))

    ;; Block on agent until all pending actions send from current thread are
    ;; finished.
    (await a)
    (await-for 1000 a) ;; Set 1s timeout.

    ;; As others reference types deref will never block and will return agent's
    ;; current state.
    @a)


  ;; By default, encountering an error in some action will cause an agent to
  ;; fail silently. Last value will still be available trough dereferencing, but
  ;; further actions will fail to queue up, causing an error in thread sending
  ;; the new actions.
  (let [a (agent 10)]
    (send a (fn [_] (throw (Exception. "Some error."))))

    ;; Returns error or nil.
    (Thread/sleep 100)
    (when (agent-error a) (prn (agent-error a)))

    ;; Error. Sending to failed agent.
    (send a identity)

    ;; If actions are not cleared, they will be attempted immediately.
    (restart-agent a 42)
    (restart-agent a 42 :clear-actions true))

  (let [a (agent 10 :error-mode :fail)
        ;; Errors are ignored and error actions are skipped.
        b (agent 10
                 :error-mode :continue
                 ;; Optional, but strongly recommendable error handler.
                 :error-handler (fn [the-agent exception]
                                  (prn (.getMessage exception))))]
    (set-error-mode! a :fail)
    (set-error-handler! a (fn [the-agent exception]
                            (when (= "FATAL" (.getMessage exception))
                              (set-error-mode! the-agent :fail)))))


  ;; Atoms, unlike other reference types, are perfectly capable to coordinate
  ;; I/O and other blocking operations. Thanks to their semantics they are often
  ;; an ideal construct for simplifying async processing involving I/O.
  ;;
  ;; Atoms serialize all actions send to them, and this way provide natural
  ;; synchronization. Agent's state is a resource and each action will have
  ;; guaranteed exclusive access to it. In this characteristic they remaind me
  ;; to actors and is extremely useful for logging or synchronizing access to
  ;; shared resource. Example application for this will be state logger.
  ;;
  ;; Other agents workflow can be one representing finite state machine. In this
  ;; scenario we create a pool of agents. Each one of them is representing an
  ;; instance of our finite state machine. In first state it pools data from
  ;; shared state queue and in last state it persists data with other atoms. Any
  ;; intermediate states are used for I/O or computations. We should implement
  ;; state changer function too. There is super interesting example for this
  ;; Clojure programing book.
  (let [a (agent 10)
        r (ref 10)]
    ;; Actors are integrated with STM and actions send to them will be held
    ;; until transaction is committed successfully.
    (dosync
     (alter r + 10)
     (send-off a + @r))

    ;; Nested send(-off)s are held until action is completed and will be
    ;; interrupted on error or validation fail.
    (send a (fn [v]
              (send a + v)
              *agent*
              (+ v 10)))
    @r)
  )
