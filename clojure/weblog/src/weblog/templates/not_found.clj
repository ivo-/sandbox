(ns weblog.templates.not-found
  (:use [hiccup.core :only (html)]
        [hiccup.page :only (html5 include-css include-js)]))

(defn index []
  (html5
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
    [:meta {:name "viewport"
            :content "width=device-width, initial-scale=1, maximum-scale=1"}]
    [:title "Page not found."]]
   [:body [:h1 "Page not found."]]))
