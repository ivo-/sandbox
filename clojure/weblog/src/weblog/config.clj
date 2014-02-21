(ns weblog.config
  (:use korma.db)
  (:use korma.core)
  (:require [cemerick.friend.credentials :as creds]))

(def roles #{:admin})
(def admins {"admin" {:username "admin"
                      :password (creds/hash-bcrypt "password")
                      :roles roles}})

(defdb db (mysql {:db "weblog"
                  :user "root"
                  :password ""
                  :host "localhost"}))

(declare articles tags articles_tags)

(defentity tags (many-to-many articles :articles_tags))
(defentity articles (many-to-many tags :articles_tags))
(defentity articles_tags (pk :articles_id))
