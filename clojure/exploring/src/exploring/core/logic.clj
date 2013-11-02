(ns exploring.core.logic
  "Resources:
    - https://github.com/swannodette/logic-tutorial
    - https://github.com/clojure/core.logic/wiki
  "
  (:refer-clojure :exclude [==])
  (:use [clojure.core.logic :exclude [appendo]]))

(comment

  ;; Define some relations/predicates. Unlike Prolog here we have to explicitly
  ;; define relations before we can add facts and rules to them.
  (defrel male x)

  (fact male 'Ivo)
  (fact male 'Hristo)
  (fact male 'Yordan)

  ;; Makes a query to the logic engine. By convention we declare a logic
  ;; variable "q" and ask for the possible values for it. First argument
  ;; specifies maximum number of answers we want to get.
  ;;
  ;; (run) will give us up to some number answers.
  ;; (run*) will give us all the answers the engine can find.
  ;;
  (run 1 [q] (male q)) ;= (Hristo)

  (defrel fun x)
  (fact fun 'Ivo)
  (fact fun 'Yordan)
  (run* [q] (male q) (fun q)) ;= (Yordan, Ivo)

  (defrel female x)
  (fact female 'Julia)
  (fact female 'Tanya)
  (fact female 'Monica)
  (fact female 'Jennifer)

  (defrel likes x y)
  (fact likes 'Ivo 'Monica)
  (fact likes 'Julia 'Hristo)
  (fact likes 'Yordan 'Tanya)

  ;; Create two more logic variables and store their results in vector which is
  ;; assigned as value for "q". (flesh) is actually a way for introducing new
  ;; variables.
  (run* [q] (fresh [x y] (likes x y) (== q [x y])))
  (run* [q] (fresh [x y] (likes x y) (likes y x) (== q [x y]))) ;; AND
  (run* [q] (conde ((fun q)) ((likes 'Julia q))))               ;; OR

  (defrel parent x y)

  ;; Declare predicates using rules. Rules in core.logic looks like just a
  ;; normal functions.
  (defn child [x y] (parent y x))
  (defn son [x y] (all (child x y) (male x)))
  (defn daughter [x y] (all (child x y) (female x)))
  (defn grandparent [x y] (fresh [z] (parent x z) (parent z y)))
  (defn granddaughter [x y] (fresh [z] (daughter x z) (child z y)))

  ;; Unification is the process where the engine takes two terms attempts to
  ;; make them equal. If logic variables occur in either of terms, the engine
  ;; will try to bind that variables to whatever value matches in other term. If
  ;; this is impossible, unification and query fails.
  (run* [q] (== 5 5))                   ;= (_0) -> success but unbound variable
  (run* [q] (== 5 4))                   ;= ()   -> false
  (run* [q] (== q 4) (== 4 q))          ;= (4)  ->
  (run* [q] (== q 4) (== 5 q))          ;= ()   -> we cannot unify twice

  ;; In order for [x 2] and [1 y] to be unified x=1, y=2
  (run* [q] (fresh [x y] (== [x 2] [1 y]) (== q [x y])))  ;= ([1 2])
  (run* [q] (fresh [x y] (== x y) (== x 1) (== q [x y]))) ;= ([1 1])

  )


(comment

  (defn appendo
    "Concatenates l1 and l2 into o. Relational functions differ from their
    functional counterparts. Instead of return value we usually make the final
    parameter be output value that we'll unify the answer to."
    [l1 l2 o]
    (conde
     ((== l1 ()) (== l2 o))
     ((fresh [a d r]
             (conso a d l1)
             (conso a r o)
             (appendo d l2 r)))))

  (run* [q] (appendo [1 2] [3 4] q))
  (run* [q] (appendo [1 2] q [1 2 3 4]))

  )
