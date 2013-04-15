(ns challenges.c4)

(def ^:private denoms [1 2 5 10 20 50 100])
(def ^:private denoms-len (dec (count denoms)))
(def ^:private count-change-helper
    (memoize (fn [n m]
       (cond
        (= n 0) 1
        (< n 0) 0
        (and (< m 0) (>= n 1)) 0
        :else (+ (count-change-helper n (dec m))
                 (count-change-helper (- n (denoms m)) m))))))

(defn count-change [n] (count-change-helper n denoms-len))

;; (prn (count-change 1)) ; 1
;; (prn (count-change 4)) ; 3
;; (prn (count-change 7)) ; 6
