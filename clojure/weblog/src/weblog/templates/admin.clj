(ns weblog.templates.admin
  (:use [hiccup.core :only (html)]
        [hiccup.element :only (link-to)]
        hiccup.form)
  (:require [weblog.templates.layout :as layout]
            [weblog.helpers.url :as url]))

(defn login [params]
  (layout/admin-login params [:div [:h2 "Login"]
                        (form-to ["post" "/admin/login"]
                                 (label "username" "Username")
                                 (text-field "username")
                                 (label "password" "Password")
                                 (password-field "password")
                                 [:br]
                                 (submit-button {:class "btn"} "Login"))]))

(defn articles [params]
  (layout/admin-logged params (html [:div
                                     (for [article (:articles params)]
                                       [:div (link-to (str "/article/"
                                                           (url/encode (:title article)))
                                                      (:title article))])
                                     [:br]
                                     (link-to {:class "btn"} "/admin/articles/new" "New")])))

(defn articles-new [params]
  (layout/admin-logged params (form-to ["post" "/admin/articles/new"]
                                 (label "title" "Title")
                                 (text-field "title")
                                 (label "content" "Content")
                                 (text-area "content")
                                 (label "tags" "Tags")
                                 (for [tag (:all-tags params)]
                                   (html (link-to {:class "tag"}
                                                  (str "#" tag) tag)
                                         " "))
                                 [:br]
                                 (text-field {:id "tags"} "tags")
                                 [:br]
                                 (submit-button {:class "btn"} "Create")
                                 [:script "
$('a.tag').click(function(){
  var v = $('#tags').val();
  if (v) v += ', ';
  $('#tags').val(v + this.textContent);
});"])))
