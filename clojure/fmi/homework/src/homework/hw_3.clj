(ns homework.hw_3)

(def ^:dynamic *counter* (atom 0))

(def reset-counter! #(reset! *counter* 0))
(def count!         #(swap! *counter* inc))
(def get-count      #(deref *counter*))
