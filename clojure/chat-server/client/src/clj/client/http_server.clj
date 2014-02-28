(ns client.http-server
  (:require [ring.adapter.jetty :refer (run-jetty)]
            [ring.middleware.file :refer (wrap-file)]))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello World"})

(defn start []
  (run-jetty (wrap-file handler "./compiled")
             {:port 3124}))
