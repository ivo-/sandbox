(ns homework.hw_1)

; Helpers

(defn- abs [x] (if (neg? x) (- x) x))
(defn- avg [x y] (/ (+ x y) 2))
(defn- match-signs? [nums]
  (->> nums (filter pos?) count #{0 (count nums)} boolean))

; Bisection

(defn bisect [f -p +p =?]
  (let [p   (avg -p +p)
        fp  (f p)]
    (if (or (=? -p +p) (zero? fp))
      p
      (if (neg? fp)
        (recur f p +p =?)
        (recur f -p p =?)))))

(defn make-bisector [tolerance]
  (letfn [(=? [x y] (< (abs (- x y)) tolerance))]
    (fn [f a b]
      (let [val-arg (sorted-map (f a) a (f b) b)
            p- (-> val-arg first first)
            p+ (-> val-arg second first)]
        (when-not (match-signs? (keys val-arg))
          (bisect f p- p+ =?))))))

; Queue

(def make-queue vector)
(def push-to-queue conj)
(def peek-at-queue first)
(def pop-from-queue (comp vec rest))
(def empty-queue? empty?)
