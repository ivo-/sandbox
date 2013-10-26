(ns exploring.core
  (:gen-class))

(defn -main
  [& args]
  (alter-var-root #'*read-eval* (constantly false))
  (println "Hello, Clojure!"))
