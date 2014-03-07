(ns om-examples.intro
  (:require [clojure.browser.repl :as repl]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs.core.async :as async :refer [>! <! put! chan timeout]])
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:import [goog.ui IdGenerator]))

(enable-console-print!)
(repl/connect "http://localhost:9000/repl")

(defn guid []
  (.getNextUniqueId (.getInstance IdGenerator)))

(defn log [& items]
  (.log js/console (apply pr-str items)))

;; =============================================================================

(def state (atom {:text "Hello world!"}))

(defn widget [data owner]
  (reify
    om/IRender
    (render [this]
      (dom/h1 nil (:text data)))))

(om/root widget state
  {:target (. js/document (getElementById "my-app"))})
