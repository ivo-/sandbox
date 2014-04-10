ns exploring.macros "
  ============================================================================
  Clojure compilation pipeline:

    read -> compile (analyze + emit) -> run

  Reader macros: reader hooks
  Macros:        compiler hooks

  ============================================================================

  So, a macro is a tool for transforming an arbitrary data structure
  into one which can be evaluated by Clojure. It is like a compiler plugin
  system.

  When a language lacks proper means of abstraction, the result is boilerplate
  and repetition, both signs of fundamental weaknesses in that language. Macros
  are powerful because they give you a way to define entirely new levels of
  abstraction within the language itself, using the language itself.

  ============================================================================

  Macros are are special functions called during compilation with unevaluated
  data structures that represent the code itself and return the the new code
  that should actually be evaluated.
  "

;; ============================================================================
;; Samples

(meta #'or) ;= {:macro true, :ns ... }

;; ----------------------------------------------------------------------------
;; Basic


(def x 1)
(defmacro ignore [& body] nil)
(do (ignore (def x 2)) x)

(defmacro rev [[fun & args]]
  (list* fun (reverse args)))
(macroexpand '(rev (prn 1 2 3)))

;; ----------------------------------------------------------------------------
;; Syntax quote

(def a `(map inc (reduce + (range 10))))
a

(do `(+ ~(map inc (range 5))))
(do `(+ ~@(map inc (range 5))))

;; ----------------------------------------------------------------------------
;; Gensym

(gensym)
(gensym "clj-")

(eval `(let [name# "James Bond"]
         (prn "Bond, " name#)))

;; ----------------------------------------------------------------------------
;; Macroexpand

(require '[clojure.walk :refer [macroexpand-all]])

macroexpand     ;=> single
macroexpand-1   ;=> top level
macroexpand-all ;=> all levels

;; ============================================================================
;; Errors

;; ----------------------------------------------------------------------------
;; Double evaluation

(defmacro report
  "OOPS... dobuble evaluation."
  [to-try]
  `(if ~to-try
     (println (quote ~to-try) "was successful:" ~to-try)
     (println (quote ~to-try) "was not successful:" ~to-try)))

(defmacro report
  "Its O.K. now."
  [to-try]
  `(let [result# ~to-try
         form#   (quote ~to-try)]
     (if result#
       (println form# "was successful:" ~to-try)
       (println form# "was not successful:" ~to-try))))

;; ----------------------------------------------------------------------------
;; Errors

;;; Macro will not produce error for not defined symbols until it is used, in
;;; contrast with functions. Macros are executed at compile time and it
;;; impossible for Clojure to know if symbol is bound to value or not. The macro
;;; sees and returns only symbols, keywords and data structures. Whether those
;;; symbols are when code produced by the macro is executed is not for the macro
;;; to decide. This makes debugging tricky but we have some good tools for
;;; that.
(comment
  (defn oops [arg] (frobnicate arg))       ;= #<CompilerException ..
  (defmacro oops [arg] `(frobnicate ~arg)) ;= #'user/oops
  (oops 123))                              ;= #<CompilerException ..


;; ----------------------------------------------------------------------------
;; Higiene

;; ---

;; ============================================================================
;; Runtime vs Compile-time

;; ----------------------------------------------------------------------------
;; Errors

(defmacro fail-me
  [& body]
  (assert false (apply str "You are failed with: " body)))
(defn test-fail [] (fail-me "some test data"))

(defn fail-me-fn
  [& body]
  (assert false (apply str "You are failed with: " body)))
(defn test-fail-fn [] (fail-me-fn "some test data"))

;; ----------------------------------------------------------------------------
;; Phases

(read-string "(+ 1 3 4)") ;; => read
(eval (list + 1 3 4))     ;; => eval

#_(eval (macro (read-string "(+ 1 3 4)")))
#_(function (eval (read-string "(+ 1 3 4)")))

;; ============================================================================
;; Macros for speed and elegance

(defmacro dofs [pair & body]
  (let [[n vs] pair]
    `(let [c# (count ~vs)]
       (loop [i# 0]
         (when (< i# c#)
           (let [~n (aget ~vs i#)]
             ~@body
             (recur (inc i#))))))))

(defmacro ? [obj k]
  `(aget ~obj ~k))

(defmacro ! [obj k v]
  `(aset ~obj ~k ~v))

(defn active-nil->false [coll]
  (dofs [item coll]
    (when (= (? item :active) nil)
      (! item :active false))))

;; ============================================================================
;; Deep walking macros

;; Implement tail recursion variant of defn.
;; Bonus: leve call that are not in tail position (with if)
;; Parse and transform play

(clojure.walk/prewalk-replace {'a :aaa} '(a [[[[a [[a]]]]]]))
