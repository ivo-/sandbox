;;; TODO: make it super simple demo of async & om
;;; TODO: twitter, facebook, so on events(100 in s)
;;; TODO: Log them all with om
;;; TODO: show stats
(ns om-examples.events
  (:require [clojure.browser.repl :as repl]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs.core.async :as async :refer [>! <! put! chan timeout]])
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:import [goog.ui IdGenerator]))

(enable-console-print!)
(repl/connect "http://localhost:9000/repl")

;;; ------------------------------------------------------------------
;;; Data

(deftype Event [id source text date])

;;; ------------------------------------------------------------------
;;; Helpers

(defn now []
  (int (/ (.getTime (java.util.Date.)) 1000)))

(defn guid []
  (.getNextUniqueId (.getInstance IdGenerator)))

(defn log [& items]
  (.log js/console (apply pr-str items)))

;;; ------------------------------------------------------------------
;;; Fake data

(defn random-event [source]
  (let [id (guid)]
    (Event. id source (str "Event -> " id) (now))))

(defn send-random-event [c source]
  (go (while true
        (<! (timeout (+ 200 (rand-int 3000))))
        (>! c (random-event source)))))

;;; ------------------------------------------------------------------
;;; Streams

(defn input-stream []
  (let [c (chan)]
    (send-random-event c :input)
    c))

(defn twitter-stream []
  (let [c (chan)]
    (send-random-event c :twitter)
    c))

(defn facebook-stream []
  (let [c (chan)]
    (send-random-event c :facebook)
    c))

(defn events-stream []
  (async/merge [(input-stream)
                (twitter-stream)
                (facebook-stream)]))

;;; ------------------------------------------------------------------
;;; App

(defn item [{:keys [text source]} node]
  (om/component
    (dom/li nil
      text "(" (dom/strong nil  source) ")")))

;; (defn statistic)

(defn stream [app node]
  (reify
    om/IDidMount
    (did-mount [_ _]
      (let [s (events-stream)]
        (go (while true
              (let [e (<! s)]
                ;; (om/transact! app [:events] )
                nil)))))

    om/IRender
    (render [_]
      (dom/section nil
        (dom/ul nil
          (om/build-all item (:events app) {:key :id}))))))

(om/root app-state {:events []} js/document.body)
