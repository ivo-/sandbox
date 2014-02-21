(ns weblog.models.tags
  (:use korma.core
        weblog.config))

(defn all []
  (select tags))

(defn ids [vals]
  (map :id (select tags
                   (fields :id)
                   (where (and {:value [in vals]})))))

(defn create-tags [coll]
  (insert tags (values (map #(hash-map :value %) coll))))
