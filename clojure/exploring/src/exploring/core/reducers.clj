;;; Rich Hickey's posts:
;;;
;;; http://clojure.com/blog/2012/05/08/reducers-a-library-and-model-for-collection-processing.html
;;; http://clojure.com/blog/2012/05/15/anatomy-of-reducer.html
(ns exploring.core.reducers
  "It is a new look at the fundamental functional operations map, filter, reduce.
   Traditional realizations work upon collections/sequences, transforming them
   and returning new collections/sequences. Reducers take different approach by
   working upon different abstraction - reducing functions.

   Reducing function is just a binary function that can be passed to reduce.
   Each of the map, filter, mapcat works by just producing and transforming
   reducing function not knowing anything about the thing that will be reduced.
   Reducing functions produced by map/filter/.. bound to collections are also
   called reducibles.

   Actual work is done by the reducer functions. There are two types of them:
     (reduce) -> respects the order
     (fold)   -> parallel, doesn't respect the order (trade-offs)

   You wanna use reducers instead of core API when:
     - you want speed
     - you don't care about laziness"
  (:require [clojure.core.reducers :as r]))

;;; Equivalents
(r/reduce + (r/map inc [1 2 3]))
(reduce (fn [ret x] (+ ret (inc x))) (+) [1 2 3])

;;; Mapping transformation of reducing function.
(defn mapping [f]
  (fn [f1]
    (fn [result input]
      (f1 result (f input)))))

;;; Getting collection as a result.
(into [] (r/filter even? (r/map inc [1 1 1 2])))
(into #{} (r/filter even? (r/map inc [1 1 1 2]))) ;;= coll type as you wish

(r/reduce + (r/filter even? (r/map inc [1 1 1 2])))
(r/fold + (r/filter even? (r/map inc [1 1 1 2])))
