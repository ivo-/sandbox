(ns challenges.c1)

(defn histogram [xs]
  (let [fr-map (frequencies xs)]
    (mapv #(fr-map % 0) (->> xs (apply max) inc range))))
