(ns challenges.c3)

(defn- posfn? [f nums]
  (when (and (seq nums)
             (-> f meta :arglists first count))
    (every? #(pos? (f %)) nums)))

(defn inspect [n]
  (loop [items (->> (ns-map n)
                    (filter #(-> % val meta :interesting)))
         funcs    #{}
         vectors  #{}
         strings  #{}
         integers #{}]
    (if (seq items)
      (let [item  (first items)
            name  (key item)
            value (deref (val item))]
        (recur (rest items)
               (if (fn? value) (conj funcs item) funcs)
               (if (vector? value) (conj vectors value) vectors)
               (if (string? value) (conj strings value) strings)
               (if (integer? value) (conj integers value) integers)))
      {:funcs    (map key funcs)
       :vectors  vectors
       :strings  strings
       :integers integers
       :positive (->> funcs
                      (filter #(posfn? (val %) integers))
                      (map key)
                      (set))})))
