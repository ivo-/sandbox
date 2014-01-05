;;; TODO: make it super simple demo of async & om

(ns om-examples.todo
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs.core.async :as async :refer [>! <! chan timeout]])
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:import [goog.ui IdGenerator]))

(defn guid []
  (.getNextUniqueId (.getInstance IdGenerator)))

(defn log [& items]
  (.log js/console (apply pr-str items)))

(def app-state (atom {:items []}))
(def input-chan (chan))

;; (om/root app-state todo-component js/document.body)
