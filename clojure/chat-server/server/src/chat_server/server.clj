(ns chat-server.server
  (:require [org.httpkit.server :refer :all]
            [clojure.core.async :as async :refer [>! <! chan put! go go-loop]]
            [chat-server.texts :refer [texts]]
            [ring.middleware.cors :refer [wrap-cors]]))

;; =============================================================================
;; State

(defn- empty-state []
  {:online  (atom {})
   :pending (atom #{})
   :channels {:init (chan)
              :close (chan)
              :receive (chan)}})

;; =============================================================================
;; Handler

(defn listener [channel socket]
  (fn [msg]
    (put! channel [socket msg])))

(defn handler
  [{:keys [channels] :as state} request]
  (with-channel request socket
    (if-not (websocket? socket)
      (send! socket (:err-http texts))
      (do
        ((listener (:init channels) socket) request)
        (on-close socket (listener (:close channels) socket))
        (on-receive socket (listener (:receive channels) socket))))))

(defn- wrapped-handler [state]
  (-> (partial #'handler state)
    (wrap-cors :access-control-allow-origin #".+")))

;; =============================================================================
;; Server

(defonce current
  (atom nil))

(def options
  {:port 8080
   :join? false})

(defn start []
  (when @current
    (throw (Exception. (:err-running texts))))
  (let [state   (empty-state)
        stop-fn (run-server (wrapped-handler state) options)]
    (reset! current {:state   state
                     :stop-fn stop-fn})
    state))

(defn stop []
  (when @current
    ((:stop-fn @current))
    (reset! current nil)))

(defn restart []
  (stop)
  (start))
