(ns chat-server.core
  (:gen-class)
  (:require [schema.core :as scm]
            [clojure.edn :as edn]
            [chat-server.storage :as s]
            [chat-server.avatars :as a]
            [chat-server.texts :refer [texts]]
            [chat-server.server :as server]
            [chat-server.messages :as mesgs]
            [clojure.core.async :as async :refer [>! go-loop]]))

(defn validate! [data]
  (scm/validate {:type (scm/enum :auth :post :like :block :rang-list)
                 :mesg scm/Any} data))

(defn -main [& args]
  (let [{:keys [online pending channels]
         :as   state}
        (server/start)]

    (go-loop []
      (when-let [[socket msg] (<! (:init channels))]
        (swap! pending conj socket)
        (mesgs/push! socket :auth (:prompt-name texts))
        (recur)))

    (go-loop []
      (when-let [[socket mesg] (<! (:receive channels))]
        (try
          (let [data (edn/read-string mesg)]
            (validate! data)
            (mesgs/handler socket state data))
          (catch RuntimeException e
            (let [msg (str (:err-parsing texts) (.getMessage e))]
              (mesgs/push! socket :warn msg))))
        (recur)))

    (go-loop []
      (when-let [[socket msg] (<! (:close channels))]
        (when-let [user (@online socket)]
          (swap! online dissoc socket)
          (mesgs/broadcast! @online :info (:name user) " left.")
          (mesgs/update-stats! @online))
        (when (@pending socket)
          (swap! pending disj socket))
        (recur)))))

(comment

  (do
    ;; Restart server
    (do
      (server/stop)
      (-main))

    ;; Restart avatars server
    (do
      (a/restart)))

  )
