(ns chat-server.avatars
  (:require [monger.gridfs :as gfs]
            [me.raynes.fs :as fs])
  (:use ring.adapter.jetty
        ring.util.response
        ring.middleware.params
        ring.middleware.multipart-params))

(defn fallback []
  (redirect "http://lorempixel.com/40/40/"))

(defn handler [request]
  (if-let [filename ((:query-params request) "name")]
    (if-let [file (gfs/find-one {:filename filename})]
      {:status 200
       :body (.getInputStream file)}
      (fallback))
    (let [params    (request :multipart-params)
          name      (params "name")
          data      (params "file")
          tempfile  (:tempfile data)
          extension (->> (data :filename)
                      (fs/extension)
                      (rest)
                      (apply str))
          content-type (:content-type data)]
      (if tempfile
        (do
          (gfs/remove {:filename name})
          (gfs/store-file (gfs/make-input-file tempfile)
            (gfs/filename name)
            (gfs/metadata {:format extension})
            (gfs/content-type content-type))
          {:status 200
           :body "<script>window.close();</script>"})
        (fallback)))))

(def avatars
  (-> handler
    (wrap-params)
    (wrap-multipart-params)))

(defonce server (run-jetty #'avatars {:port 8011
                                      :join? false}))
(defn restart []
  (.stop server)
  (.start server))
