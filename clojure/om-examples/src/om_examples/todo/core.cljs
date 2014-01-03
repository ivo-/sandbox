(ns om-examples.todo
  (:require [clojure.browser.repl :as repl]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs.core.async :as async :refer [>! <! chan timeout]])
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:import [goog.ui IdGenerator]))

(enable-console-print!)
(repl/connect "http://localhost:9000/repl")

(defn guid []
  (.getNextUniqueId (.getInstance IdGenerator)))

(defn log [& items]
  (.log js/console (apply pr-str items)))

(def app-state (atom {:items []}))
(def input-chan (chan))

(defn todo [app node]
  (reify
    om/IWillMount
    (will-mount [_]
      (go (while true
            (om/update! app
              update-in [:items] conj (<! input-chan)))))
    om/IRender
    (render [_]
      (apply (partial dom/ul #js {:style #js {:width "300px"
                                              :border "solid"
                                              :margin "auto auto"}})
        (for [{:keys [id text]} (:items app)]
          (dom/li #js {:key id} text))))))

(om/root app-state todo js/document.body)

(go (while true
      (<! (timeout 3000))
      (>! input-chan (let [i (guid)]
                       {:id (guid) :text (str "Item " i)}))))
