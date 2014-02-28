(ns chat-server.storage
  (:require [monger.core :as mg]
            [monger.query :as q]
            [monger.collection :as db]
            [clj-time.core :as time]
            [clj-time.coerce :as time.coerce]
            [crypto.password.pbkdf2 :refer (encrypt check)])
  (:import  [org.bson.types ObjectId]))

;;; ----------------------------------------------------------------------------
;;; Connect

(mg/connect!)
(mg/set-db! (mg/get-db "chat-server"))

;;; ----------------------------------------------------------------------------
;;; Helpers

(defn ->id [id]
  (if (string? id)
    (ObjectId. id)
    id))

(defn get-blocked-users [id]
  (->> {:user_id (->id id)}
    (db/find-maps "blocks")
    (map :blocked_id)
    (map str)
    (set)
    (vec)))

(defn fix-user-data [user]
  (-> user
      (assoc :_id (str (:_id user)))
      (dissoc :password)
      (assoc :blocked (set (get-blocked-users (:_id user))))))

(defn fix-post-data [post]
  (-> post
    (assoc :_id (str (:_id post)))
    (assoc :user_id (str (:user_id post)))
    (assoc :user_name (->> {:_id (->id (:user_id post))}
                        (db/find-one-as-map :users)
                        (:name)))
    (assoc :likes (db/count "likes" {:post_id (:_id post)}))))

;;; ----------------------------------------------------------------------------
;;; Constructors

(defn new-user
  "Create new user record."
  [name pass]
  (let [user {:_id        (ObjectId.)
              :name       name
              :password   (encrypt pass)
              :created_at (time.coerce/to-long (time/now))}]
    (db/insert :users user)
    (fix-user-data user)))

(defn new-post
  "Create new post record for user."
  [user_id text]
  (let [post {:_id        (ObjectId.)
              :user_id    user_id
              :text       text
              :created_at (time.coerce/to-long (time/now))}]
    (db/insert :posts post)
    (fix-post-data post)))

(defn new-like
  "Create new like record for user and post."
  [user_id post_id]
  (db/insert :likes {:user_id (->id user_id)
                     :post_id (->id post_id)}))

(defn new-block
  "Create new block record."
  [user_id blocked_id]
  (db/insert :blocks {:user_id (->id user_id)
                      :blocked_id (->id blocked_id)}))

;;; ----------------------------------------------------------------------------
;;; Queries

(defn get-post
  "Finds post by id."
  [id]
  (fix-post-data
    (db/find-one-as-map :posts {:_id (->id id)})))

(defn get-user
  "Finds user by name"
  [name]
  (db/find-one-as-map :users {:name name}))

(defn get-user-by-id
  [id]
  (db/find-one-as-map :users {:_id (->id id)}))

(defn get-rang-list []
  (->> (q/with-collection "users"
         (q/find {})
         (q/limit 20))
    (map fix-user-data)
    (vec)))

(defn get-stats
  []
  {:users (db/count "users")
   :posts (db/count "posts")})

(defn get-user-likes [id]
  (db/find-maps "likes" {:user_id (->id id)}))

(defn get-post-likes [id]
  (db/find-maps "likes" {:post_id (->id id)}))

(defn get-stream
  [& {:keys [limit from blocked]
      :or   {from 0 blocked []}}]
  (->> (q/with-collection "posts"
         (q/find {:user_id { "$nin" blocked }})
         (q/limit limit)
         (q/sort (array-map :created_at -1)))
    (map fix-post-data)
    (vec)))

;;; ----------------------------------------------------------------------------
;;; Authentication

(defn auth-user
  "Authenticates or creates the user"
  [name pass]
  (if-let [user (get-user name)]
    (when (check pass (user :password))
      (fix-user-data user))
    (new-user name pass)))
