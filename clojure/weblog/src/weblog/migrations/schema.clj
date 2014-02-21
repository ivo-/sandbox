(ns weblog.migrations.schema
  (:require [weblog.config :as config]
            [clojure.java.jdbc :as jdbc]))

(def mysql-db {:classname "com.mysql.jdbc.Driver"
               :subprotocol "mysql"
               :subname "//localhost:3306/weblog"
               :user "root"
               :password ""})

(defn up []
  (jdbc/with-connection mysql-db
    (jdbc/create-table
     :articles
     [:id :integer "PRIMARY KEY" "AUTO_INCREMENT"]
     [:title "varchar(255)" "CHARACTER SET utf8" "NOT NULL"]
     [:content :text "CHARACTER SET utf8" "NOT NULL"]
     [:creted_at :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"])

    (jdbc/create-table
     :tags
     [:id :integer "PRIMARY KEY" "AUTO_INCREMENT"]
     [:value "varchar(100)" "CHARACTER SET utf8" "NOT NULL"])

    (jdbc/create-table
     :articles_tags
     [:articles_id :integer "NOT NULL"]
     [:tags_id :integer "NOT NULL"])))

(defn down []
  (jdbc/with-connection mysql-db
    (jdbc/drop-table :articles)
    (jdbc/drop-table :tags)
    (jdbc/drop-table :articles_tags)))

(defn reset []
  (down)
  (up))
