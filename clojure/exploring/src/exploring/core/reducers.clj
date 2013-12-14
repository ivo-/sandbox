(ns exploring.core.reducers
  (:require [clojure.core.reducers :as r]))

;;; This lib takes different approach at realizing the fundamental functions
;;; map, filter and reduce. It makes the reasonable choice of treating reduce
;;; singularly, and as a cornerstone of computing involving collections. It
;;; defines:
;;;
;;; - "reducibles" things that can be reduced. They are basically functions
;;;   holding collections.
;;; - instead of transforming the collection, map and filter transform the
;;;   reducer.
;;; - actual work is done only when the reduce is called.
;;;

;;; Old functional api is more simpler but it takes more time to prepare lazy
;;; sequences before after applying new map/filer/...
(time (reduce + (map inc (map inc (map inc (range 10e5))))))
(time (r/reduce + (r/map inc (r/map inc (r/map inc (range 10e3))))))

;;; core.reducers/fold works similarly to map, but it is: automatically
;;; parallizable, and implementing a “reduce/combine” model.
;;; TODO:
(time (r/fold + (r/map inc (r/map inc (r/map inc (range 10e5))))))

(defn benchmark [f N times]
    (let [nums (vec (range N))
          start (java.lang.System/currentTimeMillis)]
      (dotimes [n times]
        (f nums))
      (- (java.lang.System/currentTimeMillis) start)))

(defn eager-map [& args] (doall (apply map args)))
(defn eager-filter [& args] (doall (apply filter args)))

(defn eager-test [nums] (eager-filter even? (eager-map inc nums)))
(defn lazy-test [nums] (doall (filter even? (map inc nums))))
(defn reducer-test [nums] (into [] (r/filter even? (r/map inc nums))))

(println "Eager test:    " (benchmark eager-test 1000000 10) "ms")
(println "Lazy test:     " (benchmark lazy-test 1000000 10) "ms")
(println "Reducers test: " (benchmark reducer-test 1000000 10) "ms")

(defn old-reduce [nums]
  (reduce + (map inc (map inc (map inc nums)))))

(defn new-reduce [nums]
  (reduce + (r/map inc (r/map inc (r/map inc nums)))))

(defn new-fold [nums]
  (r/fold + (r/map inc (r/map inc (r/map inc nums)))))

(println "Old reduce: " (benchmark old-reduce 10e5 10) "ms")
(println "New reduce: " (benchmark new-reduce 10e5 10) "ms")
(println "New fold:   " (benchmark new-fold 10e5 10) "ms")
