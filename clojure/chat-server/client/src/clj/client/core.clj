(ns client.core
  "Watch and compile the html template on modification."
  (:gen-class)
  (:require [client.template :as template]
            [me.raynes.fs :as fs]
            [client.http-server :as http]))

(def ^:dynamic *production* :dev)
(def ^:const template-file "./src/clj/client/template.clj")
(def ^:const compiled-file "./compiled/index.html")

(defn -main
  [& args]
  (binding [*production* (= (first args) 'production)]
    (future
      (prn "Starting HTTP server ...")
      (http/start)
      (prn "Srver running on http://localhost:3124/"))
    (let [last-modified (atom (fs/mod-time template-file))]
      (future
        (loop [modified (fs/mod-time template-file)]
          (when (> modified @last-modified)
            (require 'client.template :reload)
            (pr (str "Compiling " compiled-file "... "))
            (pr (time (spit compiled-file (template/main *production*))))
            (reset! last-modified modified))
          (Thread/sleep 200)
          (recur (fs/mod-time template-file)))))
    (prn "Starting in production: " *production*)
    (prn "Watching template.clj ...")))
