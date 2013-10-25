

(ns exploring.algorithms.maze
  "Explore Wilson's maze generation algorithm.

  Wilson’s algorithm is a carving algorithm it takes a fully walled “maze” and
  carves an actual maze out of it by removing some walls. Its principle is:

  1. Randomly pick a location and mark it as visited.
  2. Randomly pick a location that isn’t visited yet—if there’s none, return
    the maze.
  3. Perform a random walk starting from the newly picked location until you
    stumble on a location that is visited—if you pass through a location more
    than once during the random walk, always remember the direction you take
    to leave it.
  4. Mark all the locations of the random walk as visited, and remove walls
    according to the last known “exit direction.”
  5. Repeat from 2.

  The maze algorithm uses a matrix to represent the maze, and each cell of it
  keeps info about walls, directions and other data."
  (:require [clojure.pprint :refer (pprint)]))

#_(comment

  ;; It is difficult to represent our problem's data with appropriate data
  ;; structures. It is importent to make this as simple as possible using
  ;; natural identifiers. We used structures based on their properties to give
  ;; us exactly what we want to characterize data.
  ;;
  ;; For example if we represent maze with vector, our code possibly will not
  ;; change, but vector gives us something we don't need - order and indexes.
  ;; Set gives us just wat we want - unique, unorderd collection, that match the
  ;; properties of data we describe. Using set will also make our code more
  ;; readable and predictable.

  [x y]             ;; Location.
  #{[x y]}          ;; Visited locations set.

  #{[x y] [x y]}    ;; Wall between two locations.
  [[x y] [x y]]     ;; Direction from location to location.

  [[x y] [x y]]     ;; Walk - seq of locations.
  #{#{[x y] [x y]}} ;; Maze - set of lications.

  )

(defn maze
  "Returns a random maze carved out of walls; walls is a set of
   2-item sets #{a b} where a and b are locations.
   The returned maze is a set of the remaining walls."
  [walls]
  (let [paths (reduce (fn [index [a b]]
                        (merge-with into index {a [b] b [a]}))
                      {} (map seq walls))
        start-loc (rand-nth (keys paths))]
    (loop [walls walls
           unvisited (disj (set (keys paths)) start-loc)]
      (if-let [loc (when-let [s (seq unvisited)] (rand-nth s))]
        (let [walk (iterate (comp rand-nth paths) loc)
              steps (zipmap (take-while unvisited walk) (next walk))]
          (recur (reduce disj walls (map set steps))
                 (reduce disj unvisited (keys steps))))
        walls))))

(defn grid
  [w h]
  (set (concat
        (for [i (range (dec w)) j (range h)] #{[i j] [(inc i) j]})
        (for [i (range w) j (range (dec h))] #{[i j] [i (inc j)]}))))

(defn draw
  [w h maze]
  (doto (javax.swing.JFrame. "Maze")
    (.setContentPane
     (doto (proxy [javax.swing.JPanel] []
             (paintComponent [^java.awt.Graphics g]
               (let [g (doto ^java.awt.Graphics2D (.create g)
                             (.scale 10 10)
                             (.translate 1.5 1.5)
                             (.setStroke (java.awt.BasicStroke. 0.4)))]
                 (.drawRect g -1 -1 w h)
                 (doseq [[[xa ya] [xb yb]] (map sort maze)]
                   (let [[xc yc] (if (= xa xb)
                                   [(dec xa) ya]
                                   [xa (dec ya)])]
                     (.drawLine g xa ya xc yc))))))
       (.setPreferredSize (java.awt.Dimension.
                           (* 10 (inc w)) (* 10 (inc h))))))
    .pack
    (.setVisible true)))

(draw 40 40 (maze (grid 40 40)))
