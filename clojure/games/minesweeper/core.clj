(ns game.core
  (:require [game.ui :as ui])
  (:gen-class))

(defn -main
  [& args]
  (ui/start))
