(ns om-examples.om41
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(defn on-delete [e item items]
  (.log js/console js/arguments)
  (om/update! items #(vec (remove #{(om/value item)} %))))

(defn item [item node items]
  (om/component
    (dom/li nil
      (dom/a #js {:onClick (om/bind on-delete item items)}))))

(defn app [cursor node]
  (reify
    om/IDidMount
    (did-mount [_ _]
      (let [links (.call (.-slice js/Array.prototype)
                    (.getElementsByTagName js/document "a"))]
        (doseq [l links] (.click l))))
    om/IRender
    (render [_]
      (dom/section nil
        (om/build-all item (:items cursor)
          {:opts (:items cursor)})))))

(om/root {:items [[1] [2] [3]]} app js/document.body)
