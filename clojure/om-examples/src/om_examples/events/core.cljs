(ns om-examples.events
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs.core.async :as async :refer [>! <! put! chan timeout]])
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:import [goog.ui IdGenerator]))

(enable-console-print!)

;;; ------------------------------------------------------------------
;;; Helpers

(defn now []
  (+ (js/Date.)))

(defn guid []
  (.getNextUniqueId (.getInstance IdGenerator)))

(defn count-source [events source]
  (count (filter #(= source (:source %)) events)))

;;; ------------------------------------------------------------------
;;; Fake data

(defn random-event [source]
  (let [id (guid)]
    {:id     id
     :source source
     :text   (str "Event -> " id)
     :date   (now)}))

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
      text "(" (dom/strong nil (str source)) ")")))

(defn statistics [events node]
  (om/component
    (dom/table nil
      (dom/tr nil
        (dom/th nil "Input")
        (dom/th nil "Twitter")
        (dom/th nil "Facebook"))
      (dom/tr nil
        (dom/td nil (count-source events :input))
        (dom/td nil (count-source events :twitter))
        (dom/td nil (count-source events :facebook))))))

(defn stream [{:key [events]} node]
  (reify
    om/IDidMount
    (did-mount [_ _]
      (let [s (events-stream)]
        (go (while true
              (let [e (<! s)]
                (om/transact! app [:events] conj e))))))
    om/IRender
    (render [_]
      (dom/section nil (om/build statistics events)
        (dom/ul nil (om/build-all item events {:key :id}))))))

(om/root {:events [(random-event :input)]} stream js/document.body)
