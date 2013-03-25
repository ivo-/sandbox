(ns challenges.c1)

(defn histogram [xs]
  (let [fr-map (frequencies xs)]
    (mapv #(fr-map % 0) (->> xs (apply max) inc range))))

(prn
 (histogram [20 1 2 1 1 3 2 2 2 2 3 4 5 6 1 2]) ; [0 4 6 2 1 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 1]
 (histogram [0 0 0 0 2 2])                      ; [4 0 2]
 (histogram '(1 1 1 4))                         ; [0 3 0 0 1]
 (histogram [0]))                               ; [1]