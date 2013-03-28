(ns challenges.c2)

(def balance deref)
(def make-account (partial atom 0))

(defn deposit [account amount]
  (if (integer? amount)
    (do (swap! account + amount) account)
    (throw (IllegalStateException. (str amount " is not an integer.")))))

(defn withdraw [account amount]
  (deposit account (- amount)))
