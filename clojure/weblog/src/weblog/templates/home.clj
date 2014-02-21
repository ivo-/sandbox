(ns weblog.templates.home
  (:use [hiccup.core :only (html)]
        [hiccup.element :only (link-to)])
  (:require [weblog.templates.layout :as layout]
            [weblog.helpers.url :as url]))

;;; Partials
;;; --------------------------------

;;; TODO: comments-partial
(defn- comments-partial [])

(defn- pagination-partial [page last-page]
  [:ul {:class "pager"}
   (if (= page 1)
     [:li {:class "previous disabled"} (link-to "#" "&larr; Older")]
     [:li {:class "previous"} (link-to
                               (str "/" (dec page))
                               "&larr; Older")])
   (if (= page last-page)
     [:li {:class "next disabled"} (link-to "#" "Newer &rarr;")]
     [:li {:class "next"} (link-to
                           (str "/" (inc page))
                           "Newer &rarr;")])])

(defn- article-partial [item]
  [:article {:class "item"}
   [:header {:style "color: #08c;"}
    [:h1 (link-to (str "/article/"
                       (url/encode (:title item)))
                  (:title item))]]
   [:p
    [:ul {:class "inline" :style "display: inline;"}
     [:li [:em {:class "post-date"} "03 Mar 2013"]]
     [:li [:i {:class "icon-comment"}]]
     [:li (link-to "10" "10")]
     [:li [:i {:class "icon-tags"}]]
     (for [tag (:tags item)
           :let [value (:value tag)]]
       [:li (link-to (str "/tag/" value) value)])]]
   [:p (:content item)]
   [:footer
    [:span (:created-at item)]]])

;;; Templates
;;; --------------------------------

(defn index [{:keys [articles page last-page] :as params}]
  (layout/main params
               (html (for [item articles]
                       (article-partial item))
                     (pagination-partial page last-page))))

(defn article [{:keys [article] :as params}]
  (layout/main params (html (for [item article]
                              (article-partial item)))))

(defn about [_]
  (layout/main _ [:h2 "About"]))
