(ns weblog.handlers.home
  (:require [weblog.templates.home :as template]
            [weblog.models.articles :as articles]
            [weblog.helpers.url :as url]))

(defn index [{{:keys [page]} :params}]
  (let [page (read-string page)]
    (template/index {:page page
                     :active "home"
                     :articles (articles/all page)
                     :last-page (articles/to-pages
                                 (articles/all-count))})))

(defn about [_]
  (template/about {:active "about"}))

(defn tag [{{:keys [tag page]} :params uri :uri}]
  (let [page (read-string page)]
    (template/index {:page page
                     :active "home"
                     :subtitle (str "tag: " tag)
                     :articles (articles/tag tag page)
                     :last-page (articles/to-pages
                                 (articles/tag-count tag))})))

(defn year [{{:keys [year page]} :params uri :uri}]
  (let [page (read-string page)
        year (read-string year)]
    (template/index {:page page
                     :active "home"
                     :subtitle (str "year: " year)
                     :articles (articles/year year page)
                     :last-page (articles/to-pages
                                 (articles/year-count year))})))

(defn article [{{:keys [article]} :params}]
  (template/article {:article (articles/title (url/decode article))}))
