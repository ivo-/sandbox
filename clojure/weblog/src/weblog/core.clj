(ns weblog.core
  (:require [weblog.config :as config]
            [weblog.routes :as routes]
            [ring.adapter.jetty :as jetty]
            [compojure.handler :as handler]))

(defn init [] nil)
(defn destroy [] nil)

(def app (handler/site routes/app))

(defn -main []
  (jetty/run-jetty app {:port 8080
                        :join? false})) ; Don't blocks the thread.
