(ns exploring.algorithms.linear-sorts)

;;; Stable sort: numbers with the same value appear in the output array in the
;;; same order they appear in the input array.

;;; It is stable sorting algorithm and this property is important when it is
;;; used as subroutine for radix sort.
(defn counting-sort [xs]
  (->> xs
       (reduce (fn [[result c] x]
                 [(assoc result (dec (c x)) x)
                  (update-in c [x] dec)])
               [xs (->> xs
                        (reduce (fn [c x]
                                  (update-in c [x] inc))
                                (vec (repeat (inc (apply max xs)) 0)))
                        (reductions +)
                        (vec))])
       (first)))

(assert (= (counting-sort [0 1 6 5 7 3 5 4]) [0 1 3 4 5 5 6 7]))

;;;
(defn radix-sort [xs]
  )
