(ns client.template
  (:use [hiccup.core :only (html)]
        [hiccup.page :only (html5 include-css include-js)]
        [hiccup.element :only (link-to javascript-tag)]))

(defn main [production?]
  (html5
   [:head
    [:meta {:charset "utf-8"}]
    [:title "The Badass of the Chats"]]
   [:body
    [:div {:id "chat"}]
    [:footer
     [:div {:class "copyright"} "Copyright (c) I&G"]
     [:nav {:class "footer-menu"}
      [:ul
       [:li (link-to "https://github.com/ivo-/chat-server" "Source")]]]]
    (when production?
      (include-js "js/main.js"))
    (when-not production?
      (html (include-js "http://fb.me/react-0.8.0.js"
              "js/goog/base.js"
              "js/main-debug.js")
        (javascript-tag "goog.require('client.core');goog.require('repl.core');")))]))
