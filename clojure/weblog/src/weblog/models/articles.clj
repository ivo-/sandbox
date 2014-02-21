(ns weblog.models.articles
  (:use korma.db
        korma.core
        weblog.config)
  (:require [weblog.models.tags :as tags-model]
            [clojure.math.numeric-tower :as math]))

(def ^:dynamic *on-page* 5)

;;; Query partials
;;; ----------------------------------

(defn- all* [page]
  (-> (select* articles)
      (order :id :DESC)))

(defn- tag* [tag]
  (-> (select* tags)
      (limit 1)
      (fields :id)
      (where {:value tag})))

(defn- page* [query page]
  (-> query
      (offset (* (dec page) *on-page*))
      (limit (* page *on-page*))))

(defn- year* [year]
  (-> (select* articles)
      (where {:created_at [>= (str year "-01-01 00:00:00")]})
      (where {:created_at [<= (str year "-12-31 23:59:59")]})
      (order :created_at :ASC)))

;;; Public API
;;; ----------------------------------

(defn all
  "Get all articles on some page."
  [page]
  (-> (all* page)
      (page* page)
      (with tags)
      (select)))

(defn all-count
  "Count all pages."
  []
  (-> (select articles (aggregate (count :*) :count))
      (first)
      (:count)))

(defn title
  "Find article with some title."
  [title]
  (select articles
          (where {:title title})
          (limit 1)))

;;; OPTIMIZE: tag and tag-count are quite similar.

(defn tag
  "Find all articles with some tag on some page."
  [tag page]
  (-> (tag* tag)
      (with articles
            (with tags)
            (limit (* page *on-page*))
            (offset (* (dec page) *on-page*)))
      (select)
      (first)
      (:articles)))

(defn tag-count
  "Count all articles with some tag."
  [tag]
  (-> (tag* tag)
      (with articles (aggregate (count :*) :count))
      (select)
      (first)
      (:articles)
      (first)
      (:count)))

(defn year
  "Get all articles from some year on some page."
  [year page]
  (-> year
      (year*)
      (page* page)
      (with tags)
      (select)))

(defn year-count
  "Count all articles from some year."
  [year]
  (-> year
      (year*)
      (aggregate (count :*) :count)
      (select)
      (first)
      (:count)))

(defn to-pages
  "Calculate number of pages by articles number."
  [cnt]
  (math/ceil (/ cnt *on-page*)))

(defn create [article tags new-tags]
  (transaction
   (tags-model/create-tags new-tags)
   (let [aid (:GENERATED_KEY (insert articles (values [article])))
         tids (tags-model/ids tags)]
     (insert articles_tags
             (values (map #(hash-map :articles_id aid
                                     :tags_id %) tids))))))
