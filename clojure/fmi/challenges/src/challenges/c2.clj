(ns challenges.c2)

(def make-account #(atom 0))
(def balance deref)

(defn deposit [account amount]
  (if (integer? amount)
    (do (swap! account (partial + amount)) account)
    (throw (IllegalStateException. (str amount " is not an integer.")))))

(defn withdraw [account amount]
  (deposit account (- amount)))
