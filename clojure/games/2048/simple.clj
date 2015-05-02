(def data [[2 0 4 8]
           [2 8 0 0]
           [0 8 0 0]
           [0 0 4 0]])

(defn transpose
  "Simple matrix transpose."
  [board]
  (apply mapv vector board))

(defn shrink
  "Shrink vector according to 2048's rules. It is right->left based."
  [coll]
  (loop [[x y :as all] (remove zero? coll)
         result []]
    (cond
     (empty? all)
     (->> (concat result (repeat 0))
          (take (count coll))
          (vec))

     (= x y)
     (recur (nnext all) (conj result (+ x y)))

     :else
     (recur (next all) (conj result x)))))

(defn move
  "Move the game in some direction."
  [board dir]
  (case dir
    :left
    (->> board
      (mapv shrink))

    :right
    (->> board
      (map reverse)
      (map shrink)
      (mapv (comp vec reverse)))

    :top
    (->> board
      (transpose)
      (map shrink)
      (transpose))

    :bottom
    (->> board
      (transpose)
      (map reverse)
      (map shrink)
      (map reverse)
      (transpose))))
