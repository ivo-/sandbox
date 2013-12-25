;;; TODO: http://www.ibm.com/developerworks/library/j-clojure-protocols/
(ns exploring.multi-methods
  "TODO: multiple dispatch")

(declare do-a-thing-with-a-vector)
(declare do-a-thing-with-a-map)

(defn do-a-thing
  "Do a thing to a vec or a hash-map"
  [in]
  (cond
    (instance? clojure.lang.PersistentVector in)
      (do-a-thing-with-a-vector in)
    (instance? clojure.lang.PersistentArrayMap in)
      (do-a-thing-with-a-map in)))

(defn do-a-thing-with-a-vector [in]
  "A vector (via function)")

(defn do-a-thing-with-a-map [in]
  "A map (via function)")

(defmulti do-a-thing class)
(defmethod do-a-thing clojure.lang.PersistentVector [in]
  "A vector (via multimethod)")
(defmethod do-a-thing clojure.lang.PersistentArrayMap [in]
  "A map (via multimethod)")
