;;; Gamge of life
;;;
;;; 1. Each cell with less than two living neighbors dies.
;;; 2. Each cell with more than three living neighbors dies.
;;; 4. Each cell with two or three living neighbors lives.
;;; 5. Each died cell with exactly three living neighbors revives.

(ns explore.game-of-life
  (:require [clojure.pprint :refer (pprint)]))

(defn neighbors
  "Get cell neighbors relative to it's coordinates.

   This is the only function that cares about the content of the cell and by
   tweaking it we will be able to support different kinds of grids."
  [[x y]]
  (for [cx [1 0 -1]
        cy [1 0 -1]
        :when (not= 0 x y)]
    [(+ x cx) (+ y cy)]))

(defn step
  "Get generation and produce next one.

   This implementation is more generic than one we can do using indexes, that
   would limit us on using only vectors ot represent the board. Here the main
   focus is on using natural identifiers(skipping indexes) and the right data
   structures that allows use to produce solution independent for board or cells
   specifics."
  [board]
  (set (for [[cell nbrs] (frequencies (mapcat neighbors board))
             :when (or (= 3 nbrs) (and (= 2 nbrs) (board cell)))]
         cell)))

(comment

  (let [board #{[2 0] [2 1] [2 2] [1 2] [0 1]}]
    (pprint (take 8 (iterate step board))))

  )
