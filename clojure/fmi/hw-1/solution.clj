;; Bisection method

(defn bisect [f -p +p =?]
  (let [p   (/ (+ -p +p) 2)
        fp  (f p)]
    (cond
     (=? -p +p) p
     (zero? fp) p
     :else (if (neg? fp)
             (recur f p +p =?)
             (recur f -p p =?)))))

(defn make-bisector [tolerance]
  (letfn [(=? [x y] (< (- x y) tolerance))]
    (fn [f a b]
      (let [val-arg (sorted-map (f a) a (f b) b)
            p- (-> val-arg first first)
            p+ (-> val-arg second first)]
        (cond
         (->> val-arg keys (filter pos?) count #{0 2}) nil
         :else (bisect f p- p+ =?))))))

;; Queue

(def make-queue vector)
(def push-to-queue conj)
(def peek-at-queue first)
(def pop-from-queue (comp vec rest))
(def empty-queue? empty?)
