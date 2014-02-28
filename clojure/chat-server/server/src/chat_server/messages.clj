(ns chat-server.messages
  (:require [schema.core :as scm]
            [clojure.edn :as edn]
            [chat-server.storage :as s]
            [chat-server.texts :as texts]
            [chat-server.server :as server]
            [chat-server.messages :as mesgs]
            [chat-server.texts :refer [texts]]
            [org.httpkit.server :refer :all]))

;; =============================================================================
;; Sockets

(def ^:const output-types
  #{:post
    :auth
    :user
    :stats
    :stream
    :rang-list
    :logged
    :info
    :warn})

(defn socket-message
  [type mesg]
  (pr-str {:type type
           :mesg mesg}))

(defn push!
  [socket type & text]
  (if (output-types type)
    (let [mesg (flatten text)
          mesg (if (= (count mesg) 1)
                 (first mesg)
                 (apply str mesg))]
      (send! socket (socket-message type mesg)))
    (throw (Exception. (str (:err-unknown-type texts) type)))))

(defn broadcast!
  [online type & text]
  (doseq [[socket _] online]
    (push! socket type text)))

;; =============================================================================
;; Utils

(defn update-stats!
  "Update chat statistics for all online users."
  [online]
  (broadcast! online :stats
    {:stats (assoc (s/get-stats) :online (count online))}))

;; =============================================================================
;; Handler

(defmulti handler
  (fn [_ _ {:keys [type]}]
    type))

(defmethod handler
  :post
  [socket {:keys [online]} {:keys [mesg]}]
  (let [users @online
        user  (users socket)]
    (if user
      (broadcast! users :post (s/new-post (user :_id) mesg))
      (push! socket :warn (:err-auth-required texts)))))

(defmethod handler
  :auth
  [socket {:keys [online pending] :as state} {{:keys [name pass]} :mesg}]
  (if (@pending socket)
    (if-let [user (s/auth-user name pass)]
      (do
        (swap! pending disj socket)
        (swap! online assoc socket user)

        (update-stats! @online)
        (broadcast! @online :info (:name user) (:logged texts))

        (push! socket :logged user)
        (push! socket :stream
          {:stream (s/get-stream
                     :limit 20
                     :blocked (s/get-blocked-users (:_id user)))}))
      (push! socket :warn (:err-wrong-user texts)))
    (push! socket :warn (:err-not-pending texts))))

(defmethod handler
  :like
  [socket {:keys [online]} {{post_id :post_id} :mesg}]
  (let [user (@online socket)
        post (s/get-post post_id)]
    (cond
      (and user post) (do (s/new-like (user :_id) post_id)
                          (push! socket :info "You liked a post.")
                          (let [post (s/get-post post_id)
                                owner (->> @online
                                        (filter (fn [[_ {:keys [_id]}]]
                                                  (= (str (:user_id post))
                                                    (str _id))))
                                        (first))]
                            (push! socket :post post)
                            (when owner
                              (push! (first owner) :info "Your post was liked."))))
      (not user) (push! socket :warn "You are not authenticated.")
      (not post) (push! socket :warn "Post does not exist."))))

(defmethod handler
  :block
  [socket {:keys [online]} {{user_id :user_id} :mesg}]
  (let [user (@online socket)
        blocked (s/get-user-by-id user_id)]
    (cond
      (and user blocked) (do (s/new-block (user :_id) (blocked :_id))
                             (push! socket :info "You blocked " (:name blocked) ".")
                             (->> (user :name)
                               (s/get-user)
                               (s/fix-user-data)
                               (swap! online assoc socket))
                             (push! socket :user (@online socket)))
      (not user) (push! socket :warn "You are not authenticated.")
      (not blocked) (push! socket :warn "User does not exists."))))

(defmethod handler
  :rang-list
  [socket {:keys [online]} _]
  (push! socket :rang-list {:list (s/get-rang-list)}))
