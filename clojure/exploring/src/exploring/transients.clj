(ns exploring.transients
  "Mutable variants of Clojure's data structures. The idiomatic usage is for
  local mutation when performance should be improved.")

(comment

  ;; Usage is very similar.
  (let [x (transient [1 2 3])]
    (persistent! (conj! x 4)))

  ;; Transients are functions.
  ((transient [1 2 3]) 1)

  ;; Transients don't have value semantics.
  (= (transient [1 2]) (transient [1 2]))

  ;; We can show thay X and Y have equal lengths, but we shouldn't
  ;; rely on X and any older versions of transients.
  (let [x (transient [1 2 3])]
    (let [y (conj! x 4)]
      (= (count x) (count y))))

  ;; Changes on tranisents are faster and doesn't consume memory for
  ;; older versions like persistent data structures. This examples shows
  ;; idiomatic transients code transformation.
  (defn into-persistent [x y]
    (reduce conj x y))
  (defn into-transient [x y]
    (persistent! (reduce conj! (transient x) y)))

  (time (dorun (into [] (range 1e4))))            ;;=> Elapsed time: 2.111204 msecs
  (time (dorun (into-persistent [] (range 1e4)))) ;;=> Elapsed time: 3.243993 msecs
  (time (dorun (into-transient [] (range 1e4))))  ;;=> Elapsed time: 1.985909 msecs

  ;; Transients doesn't keep meta data.
  (let [x ^{:m true} []] (meta x))
  (let [x ^{:m true} []] (meta (transient x)))
  (let [x ^{:m true} []] (meta (with-meta (persistent! (transient x)) (meta x))))

  ;; Check if transient can be produces by coll. Sorted colls cannot
  ;; produce transients.
  (instance? clojure.lang.IEditableCollection coll)

  ;; Idiomatic usage of transients for local optimization.
  (defn into [to from]
    (if (instance? clojure.lang.IEditableCollection to)
      (-> (reduce conj! (transient to) from)
        (persistent!)
        (with-meta (meta to)))
      (reduce conj to from)))

  ;; Transient coll is unusable after turning it into persistent.
  (let [x (transient [1 2 3])]
    (persistent! x)
    (count x)) ;;=> Error!

  ;; Transient structures doesn't support all clojure.core functions.
  (find (transient {:a 1 :b 2}) :a) ;;=> Error!

  ;; Transients cannot be used as mutable variables. When tranisent is
  ;; chenged always the result should be used. Previous versions
  ;; shouldn't be touched event for reading.
  (let [tm (transient {})]
    (doseq [x (range 100)]
      (assoc! tm x 0))
    (persistent! tm))

  ;; Tranisents should be used only by the thread that created them.
  (let [t (transient {})]
    @(future (get t :a)))

  )
