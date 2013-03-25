(ns homework.hw_2)

(require '[clojure.set    :as set]
         '[clojure.string :as str])

;; Helpers
;; ===========================

(defn- fib
  "Fibonacci numbers stream."
  ([] (concat [0 1] (fib 0 1)))
  ([a b] (cons (+ a b) (lazy-seq (fib b (+ a b))))))

(def ^:private fib-1-99 (take-while (partial >= 100) (fib 0 1)))
(def ^:private magic-keys #{":list" ":vector" ":map" ":set" ":lazyseq" ":cons"})

(defn- lazy-seq?
  "Check if something is lazy sequence."
  [item]
  (instance? clojure.lang.LazySeq item))

(defn- rvec
  "Reverse vector."
  [v]
  (vec (rseq v)))

(defn- avg-of-colls
  "Find average of all numbers in sequence of sequences."
  [& items]
  (let [items  (flatten items)]
    (if (seq items)
      (/ (reduce + items) (count items))
      0)))

(defn- avg-of-squares
  "Find average of squares of items in seqence of seqences."
  [items]
  (if (seq items)
    (/ (->> (map #(if (empty? %) [0] %) items)
            (flatten)
            (map #(* % %))
            (reduce +))
       (count items))
    0))

(defn- abs
  "Return number absolute value."
  [x]
  (if (neg? x) (- x) x))

(defn- digits-count
  "Return number of digits."
  [n]
  (count (str (abs n))))

(defn- combination-2
  "Return lazy seq containing pairs of 2-combination of collection."
  [coll]
  (when (seq coll)
    (let [item (first coll)]
      (concat (map #(vector item %) (rest coll))
              (lazy-seq (combination-2 (rest coll)))))))

(defn- every-pair?
  "Return true if (pred x y) is logical true for every pair in coll."
  [pred coll]
  (every? pred (combination-2 coll)))

(defn- commutative-in?
  "Check if function is commutative for each pair form coll."
  [f coll]
  (every-pair? (fn [[a b]]
                 (= (f a b) (f b a)))
               coll))

(defn- constant-for?
  "Check if function is constant for each pair form coll."
  [f coll val]
  (every-pair? (fn [[a b]]
                 (= val (f a b) (f b a)))
               coll))

(defn sum-values-for-key
  "Sum values for key in sequence of maps."
  [maps k]
  (->> maps (map k) (remove nil?) (apply +)))

(defn- ultimate-values-map?
  "Check if value for each key of map is bigger than the sum of all values
for the same key in coll of maps."
  [m others]
  (every? (fn [[k v]] (< (sum-values-for-key others k)  v)) m))

(defn- cartesian-product
  "Returns lazy seq of cartesian product from two seqs."
  [a b]
  (when (and (seq a) (seq b))
    (let [item (first a)]
      (concat (map #(vector item %) b)
              (lazy-seq (cartesian-product (rest a) b))))))

(defn- parse-player-data
  "Parse player data."
  [data]
  (loop
      [data             data
       maps             []
       sets             []
       funcs            []
       vectors          []
       lazy-seqs        []]
    (if (seq data)
      (let [item (-> data first val)]
        (recur
         (rest data)
         (if (map? item)      (conj maps item)      maps)
         (if (set? item)      (conj sets item)      sets)
         (if (fn?  item)      (conj funcs item)     funcs)
         (if (vector?   item) (conj vectors item)   vectors)
         (if (lazy-seq? item) (conj lazy-seqs item) lazy-seqs)))
      {:maps        maps
       :sets        sets
       :funcs       funcs
       :vectors     vectors
       :lazy-seqs   lazy-seqs
       :avg         (avg-of-colls vectors
                                  (mapv seq sets)
                                  (mapv vals maps))
       })))

;; Rules
;; ===========================

(defn- first-three-last-three
  [{vectors :vectors} _]
  (let [vecs        (filter #(>= (count %) 3) vectors)
        sub-vecs    (map    #(subvec % 0 3) vecs)
        r-sub-vecs  (map    #(-> % rvec (subvec 0 3) rvec) vecs)]
    (count (filter (set r-sub-vecs) sub-vecs))))

(defn- average-bigger-than-all
  [{vectors :vectors}
   {avg :avg}]
  (count (filter #(< avg (avg-of-squares %)) vectors)))

(defn- commutative-fibonacci [{funcs :funcs} _]
  (letfn [(weirdly-special? [f]
            (and (zero? (f 0 0))
                 (commutative-in? f (range 100))
                 (constant-for?   f fib-1-99  111)))]
    (count (filter weirdly-special? funcs))))

(defn- uf-compare-numbers
  [funcs x y]
  (let [count-fn (fn [n] (count (filter #(= (% n n) n) funcs)))
        result-d (compare (digits-count x)  ; ascending order
                          (digits-count y))
        result-f (compare (count-fn y)      ; descending order
                          (count-fn x))]
    ;   If comparator returns 0 sorted-map treats keys as equal and
    ; will replace value.
    (first (remove zero? [result-d result-f 1]))))

(defn- uf-sorted-keys
  [m funcs]
  (->> m
       (set/map-invert)
       (into (sorted-map-by
              (partial uf-compare-numbers funcs)))
       (vals)
       (mapv str/lower-case)))

(defn- uf-map-points [all-maps funcs m]
  (let [sorted-keys (uf-sorted-keys m funcs)
        other-maps  (remove #(identical? % m) all-maps)]
    (if (and (ultimate-values-map? m other-maps)
             (> (count sorted-keys) 5)
             (= (set (subvec sorted-keys 0 6)) magic-keys))
      3 0)))

(defn- uf
  [{all-maps :maps funcs :funcs} _]
  (let [points (partial uf-map-points all-maps funcs)]
    (reduce + (map points all-maps))))

(defn- lazy-paris-points [max-len valid? [x y]]
  (loop [cache (hash-set)
         items (cartesian-product x y)]
    (cond
     (empty? items) (if (valid? cache) 5 0)
     (> (count cache) max-len)  0
     :else (let [[a b] (first items)]
             (recur
              (into cache [(+ a b) (* a b)])
              (rest items))))))

(defn- lazy-pairs [{sets :sets lazy-seqs :lazy-seqs} _]
  (cond
   (< (count sets) 1)      0
   (< (count lazy-seqs) 2) 0
   :else (let [longest (apply max (map count sets))
               subset? (fn [s] (some #(set/subset? s %) sets))
               points  (partial lazy-paris-points longest subset?)]
           (apply + (map points (combination-2 lazy-seqs))))))

;; Game
;; ===========================

(defn game
  [p1 p2]
  (let [p1 (parse-player-data p1)
        p2 (parse-player-data p2)
        rules [uf
               lazy-pairs
               commutative-fibonacci
               first-three-last-three
               average-bigger-than-all]
        points (fn [a b] (reduce + (map #(% a b) rules)))]
    [(points p1 p2)
     (points p2 p1)]))
