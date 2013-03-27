(ns homework.hw_3)

(def ^:dynamic *counter* (atom 0))

(def reset-counter! #(reset! *counter* 0))
(def count!         #(swap! *counter* inc))
(def get-count      #(deref *counter*))

;;; ==================================

(def calls-data (atom {}))
(def safe-inc (fnil inc 0))

(defn add-metrics [f fkey]
  (fn [& args]
    (swap! calls-data #(update-in % [fkey args] safe-inc))
    (apply f args)))

(defn calls [fkey & args]
  (or (get-in @calls-data [fkey args]) 0))

;;; ==================================

(def ^:dynamic *after* {})
(def ^:dynamic *before* {})

(defn add-events [f fkey]
  (fn [& args]
    (when-let [before (*before* fkey)]
      (apply before args))
    (let [result (apply f args)]
      (when-let [after (*after* fkey)]
        (apply after result args))
      result)))

;;; ==================================
