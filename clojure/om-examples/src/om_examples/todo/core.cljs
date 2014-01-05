(ns om-examples.todo
  (:require [clojure.browser.repl :as repl]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs.core.async :as async :refer [>! <! put! chan timeout]])
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:import [goog.ui IdGenerator]))

(enable-console-print!)
(repl/connect "http://localhost:9000/repl")

;;; ------------------------------------------------------------------
;;; Helpers

(defn guid []
  (.getNextUniqueId (.getInstance IdGenerator)))

(defn log [& items]
  (.log js/console (apply pr-str items)))

;;; ------------------------------------------------------------------
;;; State

(def app-state (atom {:items []}))
(def input-chan (chan))

;;; Event listeners
;;; ------------------------------------------------------------------

(defn on-value [node e app]
  (put! input-chan
    {:id (guid)
     :text (.-value (om/get-node node
                      "field"))})
  (aset (om/get-node node "field") "value" ""))

(defn on-mark [node e {:keys [id]}]
  (let [state (om/get-state node [:marked])]
    (om/set-state! node [:marked]
      ((if (state id) disj conj) state id))))

(defn on-delete [items e item]
  (om/update! items #(vec (remove #{(om/value item)} %)))
  (.stopPropagation e))

;;; ------------------------------------------------------------------
;;; Components

(defn input-component [app node]
  (om/component
    (dom/section #js {:style #js {:text-align "center"}}
      (dom/form #js {:action "javascript:void(0);"
                     :onSubmit (om/bind (partial on-value node) app)}
        (dom/input #js {:ref "field"
                        :style #js {:display "inline-block"}
                        :placeholder "Have something to do?"})))))

(defn item-component [{:keys [id text] :as item} node items]
  (reify
    om/IInitState
    (init-state [_]
      {:marked #{}})
    om/IRender
    (render [_]
      (let [marked (om/get-state node [:marked])]
        (dom/li #js {:onClick (om/bind (partial on-mark node) item)
                     :style #js {:background (if (marked id) "red" "white")}}
          (dom/a #js {:href "javascript:void(0);"
                      :onClick (om/bind (partial on-delete items) item)}
            "[x]")
          " "
          text)))))

(defn todo-component [app node]
  (reify
    om/IWillMount
    (will-mount [_]
      (go (while true
            (let [v (<! input-chan)]
              (om/transact! app [:items] #(conj % v))))))
    om/IRender
    (render [_]
      (dom/section
        #js {:id "main"
             :style #js {:width "300px"
                         :border "solid"
                         :margin "auto auto"}}
        (om/build input-component app)
        (dom/ul nil
          (om/build-all item-component (:items app)
            {:key :id :opts (:items app)}))))))

;;; ------------------------------------------------------------------
;;; Lest start

(om/root app-state todo-component js/document.body)

(go (while true
      (<! (timeout 3000))
      (>! input-chan (let [i (str (guid))]
                       {:id i :text (str "Item " i)}))))
