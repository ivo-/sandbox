(ns weblog.templates.layout
  (:use [hiccup.core :only (html)]
        [hiccup.page :only (html5 include-css include-js)]
        [hiccup.element :only (link-to)]))

(defn- stylesheets []
  (include-css "//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css"
               "/css/base.css"))
(defn- javascripts []
  (include-js "//ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"
              "//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"
              "/js/base.js"))

(defn main [{:keys [title active]
             :or {:title "Weblog"}
             :as params} content]
  (html5
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
    [:meta {:name "viewport"
            :content "width=device-width, initial-scale=1.0"}]
    [:title title]
    (stylesheets)]
   [:body
    [:div {:class "container-fluid"}
     ;; Header
     ;; --------------------------

     [:header {:class "row-fluid header"}
      [:h1 {:class "span12"} "WebLog.clj "
       [:small "... let's test Clojure for web."]]]

     ;; Navigation
     ;; --------------------------

     [:nav
      [:ul {:class "nav nav-tabs"}
       [:li (when (= active "home") {:class "active"})
        [:a {:href "/", :title "Home"}
         [:i {:class "icon-home"}]
         "Home"]]
       [:li (when (= active "about") {:class "active"})
        [:a {:href "/about", :title "About"}
         [:i {:class "icon-user"}]
         "About"]]]]

     [:div {:class "row-fluid"}

      ;; Content
      ;; -------------------------

      [:section {:id "content" :class "span9"}
       (when (:subtitle params) [:h3 (:subtitle params)])
       content]

      ;; Sidebar
      ;; -------------------------

      [:section {:id "sidebar"
                 :class "span3"}
       [:ul {:class "nav nav-list"}
        [:li {:class "nav-header"} "Tags"]
        [:li [:a {:href "/tag/Clojure", :title "Clojure"} "Clojure"]]
        [:li [:a {:href "/tag/Datomic", :title "Datomic"} "Datomic"]]

        [:li {:class "nav-header"} "Achieves"]
        [:li [:a {:href "/year/2013", :title "2013"} "2013"]]]]]

     ;; Footer
     ;; -------------------------

     [:footer {:class "footer row-fluid"}
      [:div {:class "container"}
       [:p "Developed using Compojure, Hiccup, Korma."]
       [:p "Distributed under the Eclipse Public License, the same as Clojure."]
       [:ul {:class "footer-links"}
        [:li (link-to "https://github.com/ivo-/weblog" "Source code")]
        [:li {:class "muted"} "&middot;"]
        [:li (link-to "http://fmi.clojure.bg/" "Clojure course")]]]]]]
   (javascripts)))

(defn- admin-base
  [params content nav]
  (html5
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
    [:meta {:name "viewport"
            :content "width=device-width, initial-scale=1.0"}]
    [:title "Admin"]
    (stylesheets)
    (javascripts)]
   [:body [:div {:class "container-fluid"}
           nav
           [:div {:class "row"}
            [:div {:class "span12"}
             content]]]]))

(defn admin-logged
  [params content]
  (admin-base params content [:nav
            [:ul {:class "nav nav-pills"}
             [:li {:class "active"} [:a {:href "/admin/articles",
                                         :title "Articles"}
                                     [:i {:class "icon-list"}]
                                     "Articles"]]
             [:li [:a {:href "/admin/logout", :title "Logout"}
                   [:i {:class "icon-off"}]
                   "Logout"]]]]))

(defn admin-login
  [params content]
  (admin-base params content [:br]))
