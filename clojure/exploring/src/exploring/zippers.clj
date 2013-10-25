;;; Zippers are tree walking/editing API, purely functional(structural sharing)
;;; and fast.
;;;
;;; Zipper = tree + location
;;;
;;; * location in the tree, targeting specific node
;;;
(ns explore.zippers
  (:require [clojure.zip :as z]))

(comment
  zipper seq-zip vector-zip xml-zip               ;; create
  up down left right prev next leftmost rightmost ;; navigate

  branch?  ;; is current node a branch
  children ;; child nodes(for branches)
  lefts    ;; left siblings
  rights   ;; right siblings

  node     ;; returns current node
  root     ;; returns the root node

  (make-node loc node children)
  replace
  make-node
  append-child
  insert
  insert-left
  insert-right
  edit
  remove
  )

(def v [[1 2 [3 4]] [5 [6 [7 [8 [9]]]]]])

(-> v z/vector-zip z/node)                ;;= [[1 2 [3 4]] [5 [6 [7 [8 [9]]]]]]
(-> v z/vector-zip z/down z/node)         ;;= [1 2 [3 4]]
(-> v z/vector-zip z/down z/right z/node) ;;= [5 [6 [7 [8 [9]]]]]
(-> v
    z/vector-zip
    z/down
    z/right
    z/down
    z/right
    z/down
    z/node) ;;= 6

;; Binary tree.
(-> [[[2 4] [6 8]] [10 12]]
    z/vector-zip
    z/down
    z/down
    z/branch?
    ) ;;= ture
