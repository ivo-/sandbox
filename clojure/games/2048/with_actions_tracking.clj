;; ============================================================================
;; Game

(defn shrink
  "Shrink vector according to 2048's rules. It is right->left
   based and adds meta data for actions, taken during the operation.

   Currently the only supported actions are cells merges in the
   form:

     [[x y] [i j] -> y is merged with x, j is merged with i
     [[0 3]]      -> last cell is merged with first cell"
  [coll]
  (loop [[[i x] [j y] :as all] (->> coll
                                    (map-indexed vector)
                                    (remove (comp zero? second)))
         result  []
         actions []]
    (cond
     (empty? all)
     (with-meta  (->> (concat result (repeat 0))
                      (take (count coll))
                      (vec))
       {:actions actions})

     (= x y)
     (recur (nnext all)
            (conj result (+ x y))
            (conj actions [j i]))

     :else
     (recur (next all)
            (conj result x)
            actions))))

(defn transpose
  "Transpose matrix while keeping the meta data."
  [board]
  (-> (apply mapv vector board)
      (with-meta (meta board))))

(defn reverse-with-actions
  "Black magic. It just subtracts (count board) from each index in :actions
   meta while reversing all the rows. This will keep actions correct."
  [board]
  (letfn [(action [a] (mapv (partial - (count board)) a))
          (group  [g] (mapv action g))]
    (-> (mapv (comp vec reverse) board)
        (vary-meta assoc :actions (->> board
                                       (meta)
                                       (:actions)
                                       (mapv group))))))

(defn shrink-all
  "Shrinks all the rows from the matrix. Extracts meta data from each
   row and stores it :actions key."
  [board]
  (let [state (mapv shrink board)
        actions (mapv (comp :actions meta) state)]
    (with-meta state {:actions actions})))

(defn move
  "Moves the game in some direction. Adds meta for all actions, taken
   during the shrinking. Depending on provided direction, actions will
   refer to rows (left, right) or columns (top, bottom).

   SEE: shrink"
  [board dir]
  (case dir
    :left
    (shrink-all board)

    :right
    (->> board
         (reverse-with-actions)
         (shrink-all)
         (reverse-with-actions))

    :top
    (->> board
         (transpose)
         (shrink-all)
         (transpose))

    :bottom
    (->> board
         (transpose)
         (reverse-with-actions)
         (shrink-all)
         (reverse-with-actions)
         (transpose))))

;; ============================================================================
;; Playground

(def tmp [[2 0 4 8]
          [2 8 0 0]
          [0 8 0 0]
          [0 0 4 0]])

;; ----------------------------------------------------------------------------
;; Shrink
;; ----------------------------------------------------------------------------
;; (shrink (first tmp))
;;
;; (shrink [2 2 0 0])
;; (shrink [2 0 0 2])
;; (shrink [0 2 0 2])
;; (shrink [0 0 2 2])
;; (shrink [2 0 2 0])
;; (shrink [2 2 2 2])
;;
;; (meta (shrink [2 2 2 2]))
;; ----------------------------------------------------------------------------

;; ----------------------------------------------------------------------------
;; Move
;; ----------------------------------------------------------------------------
;;
;; (move tmp :top)
;; (meta (move tmp :top))

;; (-> tmp
;;     (move :top)
;;     (move :left)
;;     (move :left)
;;     (move :right)
;;     (move :bottom))

;; (-> tmp
;;     (move :bottom)
;;     (move :right)
;;     (move :right)
;;     (meta))
;;
;; ----------------------------------------------------------------------------
