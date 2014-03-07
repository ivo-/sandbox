(ns om-examples.intro
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [clojure.string :as s]
            [clojure.browser.repl :as repl]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs.core.async :as async :refer [>! <! put! chan timeout]]))

(enable-console-print!)
(repl/connect "http://localhost:9000/repl")

(defn log [& items]
  (.log js/console (apply pr-str items)))

;; =============================================================================

(def state (atom {:title "Favorite bands:"
                  :list [{:name "Pink Floyd"
                          :members ["Nick Mason"
                                    "Roger Waters"
                                    "Richard Wright"
                                    "Syd Barrett"
                                    "David Gilmour"]
                          :formed "1965" }
                         {:name "Beatles"
                          :members ["John Lennon"
                                    "Paul McCartney"
                                    "George Harrison"
                                    "Ringo Starr"]
                          :formed "1960" }
                         {:name "Oasis"
                          :members ["Liam Gallagher"
                                    "Paul 'Guigsy' McGuigan"
                                    "Paul 'Bonehead' Arthurs"
                                    "Tony McCarroll"
                                    "Noel Gallagher"
                                    "Alan White"
                                    "Gem Archer"
                                    "Andy Bell"]
                          :formed "1991" }]}))

(defn band [data owner]
  (reify
    om/IRender
    (render [this]
      (dom/li nil
        (dom/strong nil (str (:name data) " (" (:formed data) ")"))
        (dom/div nil
          (str "Members: ")
          (s/join ", " (:members data)))))))

(defn widget [data owner]
  (reify
    om/IRender
    (render [this]
      (dom/section
        nil
        (dom/h1 nil (:title data))
        (apply dom/ul nil
          (om/build-all band (:list data)))))))

(om/root widget state
  {:target js/document.body})
