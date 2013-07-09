(ns homework.hw_4)

;;; Обитателя спира и никога повече не мърда, ако направи 100
;;; премествания или бъде заклещен.

(def visitors [])

(def moves-limit 100)
(def move-rules [[dec dec] [inc inc] [dec inc] [inc dec]])

(def shop-empty :-)
(def shop-visitor :X)

(def state-stuck :stuck)
(def state-exhausted :no-more)

(defn- shop-empty? [shop]
  (= @shop shop-empty))

(defn- parse [data]
  (letfn [(to-rows [i row]
            (vec (map-indexed (partial to-itms i) row)))
          (to-itms [i j itm]
            (ref {:i i
                  :j j
                  :visitor (to-visitor itm)}))
          (to-visitor [itm]
            (when (= shop-visitor itm)
                (agent {:moves 100
                        :active true})))]
    (vec (map-indexed to-rows data))))

(defn- visitors [data]
  (remove nil?
          (map #(-> % deref :visitor) (flatten data))))

(defn- active-visitors? [vstrs]
  (dosync (some #(-> % deref :active) vstrs)))

(defn- get-free-position [data visitor]
  (letfn [(to-shop [[move-i move-j]]
            (get-in data [(move-i (:i shop))
                          (move-j (:j shop))]))]
   (first (drop-while #(-> % deref :visitor)
                      (map to-shop move-rules)))))

(defn- move [visitor]
  (if (:active visitor)

    false))

(defn play-mall
  [data]
  (let [log (ref [])
        data (parse data)
        visitors (visitors data)]
    (doseq [visitor visitors]
      (send visitor (fn [v]
                      (while
                          ))))))

(play-mall [[:- :- :- :-]
            [:- :- :- :-]
            [:- :- :- :-]
            [:- :- :- :X]])
