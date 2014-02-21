(ns weblog.handlers.admin
  (:require [weblog.templates.admin :as template]
            [weblog.models.tags :as tags-model]
            [weblog.models.articles :as articles-model]
            [weblog.helpers.url :as url]
            [clojure.string :as str]
            [clojure.set :as st]))

(defn login [{:keys [params]}]
  (template/login params))

(defn articles [{:keys [params]}]
  (template/articles (assoc params :articles (articles-model/all 1))))

(defn articles-new [{:keys [params request-method]}]
  (let [all-tags (set (map :value (tags-model/all)))
        params (assoc params :all-tags all-tags)]
    (case request-method
      :get (template/articles-new params)
      :post (let [{:keys [content title tags]} params
                  tags (set (str/split tags #"\s*,\s*"))
                  new-tags (st/difference tags all-tags)]
              (articles-model/create {:content content :title title}
                                     tags new-tags)
              (url/redirect "/")))))
