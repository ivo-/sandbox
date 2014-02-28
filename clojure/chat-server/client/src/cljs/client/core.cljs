(ns client.core
  (:require-macros [cljs.core.async.macros :refer [go go-loop alt!]])
  (:require [goog.events :as events]
            [cljs.reader :refer [read-string]]
            [cljs.core.async :refer [>! <! chan put!]]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true])
  (:import [goog.ui IdGenerator]))


(declare render)

(def stream-history-count
  "How many messages to show."
  20)

;; =============================================================================
;; Utilities

(defn guid
  "Generate unique ID."
  []
  (.getNextUniqueId (.getInstance IdGenerator)))

(defn log [m]
  (.log js/console
    (if (string? m) m (pr-str m))))

(defn event->data [e]
  (let [data (.-data (.-event_ e))]
    ;; DEBUG:
    (do
      (log "RECEIVED: ")
      (log data))
    (read-string data)))

(defn socket-send! [socket val]
  ;; DEBUG:
  (do
    (log "SEND: ")
    (log (pr-str val)))
  (.send socket
    (if (string? val) val (pr-str val))))

(defn get-event-chan
  "Get event stream channel."
  [node event]
  (let [ch (chan)]
    (events/listen node event #(put! ch %))
    ch))

;; =============================================================================
;; Components

;; -----------------------------------------------------------------------------
;; Top

(declare avatar-form)

(defn statistics [{:keys [user stats]} owner]
  (om/component
    (dom/span nil
      (dom/span nil (dom/b nil (:online stats)) " online")
      " | "
      (dom/span nil (dom/b nil (:messages stats)) " messages")
      " | "
      (dom/span nil (dom/b nil (:registered stats)) " users"))))

(defn header [{:keys [user stats] :as data} owner]
  (om/component
    (dom/header nil
      (dom/h1 nil "Chat")
      (dom/div nil
        (dom/span nil "User: ")
        (dom/strong nil (:name user))
        " | "
        (om/build statistics data)
        (om/build avatar-form data)))))

(defn avatar-form-submit [owner _]
  (.setTimeout js/window #(render) 500))

(defn avatar-form [data owner]
  (reify
    om/IRender
    (render [_]
      (dom/form #js {:action "http://localhost:8011/"
                     :encType "multipart/form-data"
                     :target "_blank"
                     :method "post"
                     :onSubmit (partial avatar-form-submit owner)}
        (dom/div #js {:ref "fileds"}
          (dom/input #js {:type "file" :name "file"})
          (dom/input #js {:type "hidden" :name "name" :value (-> data :user :_id)})
          (dom/input #js {:type "submit"}))))))

(defn submit-message [owner _]
  (let [node  (om/get-node owner "field")
        value (aget node "value")
        input-chan (om/get-shared owner :input-chan)]
    (when (not= value "")
      (aset node "value" "")
      (go (>! input-chan value)))))

(defn form [data owner]
  (reify
    om/IRender
    (render [this]
      (dom/section nil
        (dom/form #js {:action "javascript:void(0);"
                       :onSubmit (partial submit-message owner)}
          (dom/input #js {:type "text"
                          :ref "field"}))))))

;; -----------------------------------------------------------------------------
;; Rang list


(defn rang-list-item [user owner]
  (om/component
    (dom/div nil
      (dom/img #js {:src (str "http://localhost:8011/?name=" (:_id user))
                    :style #js {:width "40px"
                                :height "40px"}})
      (dom/div nil
        (dom/span nil (:name user))))))

(defn rang-list [data owner]
  (om/component
    (dom/section nil
    (dom/h2 nil "Rang list")
    (apply dom/ul nil
      (om/build-all
        rang-list-item
        (:list data)
        {:key :_id})))))

;; -----------------------------------------------------------------------------
;; Stream

(defn like-post [owner _]
  (socket-send! (om/get-shared owner :ws)
    {:type :like
     :mesg {:post_id (:_id @(om/get-props owner))}}))

(defn block-user [user_id owner _]
  (socket-send! (om/get-shared owner :ws)
    {:type :block
     :mesg {:user_id user_id}}))

(defn avatar [{:keys [user_id] :as data} owner]
  (om/component
    (dom/div nil
      (dom/img #js {:src (str "http://localhost:8011/?name=" (:user_id data))
                    :style #js {:width "40px"
                                :height "40px"}})
      (dom/div nil
        (dom/span nil (:user_name data))
        " "
        (dom/a #js {:href "javascript: void(0);"
                    :onClick #(block-user user_id owner %)} "[block]")))))

(defn item [data owner]
  (om/component
    (if (:user_id data)
      (dom/li nil
        (om/build avatar data)
        (dom/div nil (:text data))
        (dom/span nil
          (:likes data) " likes")
        (dom/div nil
          (.toString (js/Date. (:created_at data))))
        (dom/a #js {:href "javascript: void(0);"
                    :onClick (partial like-post owner)} "[like]"))
      (dom/li nil
        (dom/div nil (:text data))))))

(defn stream [data _]
  (om/component
    (dom/section nil
      (dom/h2 nil "Stream")
      (apply dom/ul nil
        (om/build-all
          item
          (->> (:messages data)
            (take-last stream-history-count)
            (map (fn [itm]
                   (if ((-> data :user :blocked) (:user_id itm))
                     {:_id (guid) :text "blocked message"}
                     itm))))
          {:key :_id}))
      (dom/hr #js {:ref "bottom"}))))

;; -----------------------------------------------------------------------------
;; Login

(defn login-submit [_ owner]
  (socket-send! (om/get-shared owner :ws)
    {:type :auth
     :mesg {:name (aget (om/get-node owner "user") "value")
            :pass (aget (om/get-node owner "pass") "value")}}))

(defn login [data owner]
  (reify
    om/IRender
    (render [_]
      (dom/section nil
        (dom/h2 nil "Login")
        (dom/form #js {:action "javascript:void(0);"
                       :onSubmit (partial login-submit data owner)}
          (dom/input #js {:type "text" :ref "user" })
          (dom/input #js {:type "password" :ref "pass"})
          (dom/input #js {:type "submit"}))))))

;; =============================================================================
;; Streams

(defn server-stream [ch error-chan]
  (let [output-chan (chan)]
    (go-loop []
      (let [data (event->data (<! ch))]
        (>! output-chan data))
      (recur))
    output-chan))

(defn user-input-stream [ch error-chan]
  (let [output-chan (chan)]
    (go-loop []
      (let [data (<! ch)]
        (>! output-chan {:type :post, :mesg data}))
      (recur))
    output-chan))

;; =============================================================================
;; Messages handler

(defmulti message-handler
  (fn [_ {:keys [type]}]
    type))

(defmethod message-handler :auth
  [app val]
  nil)

(defmethod message-handler :logged
  [owner {:keys [mesg]}]
  (doto (om/get-props owner)
    (om/update! :user mesg)
    (om/transact! :messages
      #(conj % {:_id (guid)
                :text (str (:name mesg) " joined.")}))))

(defmethod message-handler :user
  [owner {:keys [mesg]}]
  (doto (om/get-props owner)
    (om/update! :user mesg)
    (render)))

(defmethod message-handler :stats
  [owner {{:keys [stream stats]} :mesg}]
  (doto (om/get-props owner)
    (om/update! [:stats :messages] (:posts stats))
    (om/update! [:stats :registered] (:users stats))
    (om/update! [:stats :online] (:online stats))))

(defmethod message-handler :stream
  [owner {{:keys [stream stats]} :mesg}]
  (doto (om/get-props owner)
    (om/transact! :messages #(into % (reverse stream)))))

(defmethod message-handler :rang-list
  [owner {:keys [mesg]}]
  (om/update! (om/get-props owner) :rang-list mesg))

(defmethod message-handler :like
  [owner {:keys [mesg]}]
  (js/alert "like")
  (js/alert mesg))

(defmethod message-handler :info
  [owner {:keys [mesg]}]
  (om/transact! (om/get-props owner) :messages #(conj % {:_id (guid) :text mesg})))

(defmethod message-handler :post
  [owner {:keys [mesg]}]
  (let [state (om/get-props owner)
        index (->> (map :_id (:messages @state))
                (map-indexed vector)
                (filter #(= (second %) (:_id mesg)))
                (first)
                (first))]
    (if index
      (doto state
        (om/update! [:messages index] mesg))
      (doto state
        (om/transact! :messages #(conj % mesg))
        (om/transact! [:stats :messages] inc)))))

(defmethod message-handler :warn
  [_ val]
  (js/alert (:mesg val)))

(defmethod message-handler :error
  [app val]
  nil)

;; =============================================================================
;; State and data

(def app-state
  (atom {:user nil
         :messages []
         :rang-list []
         :stats {:online 0
                 :messages 0
                 :registered 0}}))

(def shared-data
  (let [ws          (new js/WebSocket "ws://localhost:8080")
        socket-chan (get-event-chan ws "message")
        input-chan  (chan)
        error-chan  (chan)]
    {:ws          ws
     :input-chan  input-chan
     :socket-chan socket-chan
     :error-chan  error-chan
     :server-chan (server-stream socket-chan error-chan)
     :user-chan   (user-input-stream input-chan error-chan)}))

;; =============================================================================
;; Init

(defn show-rang-list [owner _]
  (om/set-state! owner :rang-list-show true)
  (socket-send! (om/get-shared owner :ws)
    {:type :rang-list, :mesg {}}))

(defn chat [app owner]
  (reify
    om/IWillUpdate
    (will-update [_ next-props _]
      (let [c (count (:messages next-props))]
        (when (> c stream-history-count)
          (om/transact! app :messages
            #(vec (drop (- c stream-history-count) %))))))

    om/IWillMount
    (will-mount [_]
      (let [{:keys [ws user-chan server-chan error-chan]}
            (om/get-shared owner)]
        (go (while true
              (let [[val ch] (alts! [user-chan
                                     server-chan
                                     error-chan])]
                (cond
                  (= ch user-chan)
                  (socket-send! ws val)

                  (= ch server-chan)
                  (message-handler owner val)

                  (= ch error-chan)
                  (log val)))))))

    om/IInitState
    (init-state [_]
      {:rang-list-show false})

    om/IRender
    (render [this]
      (if (:user app)
        (if (om/get-state owner :rang-list-show)
          (dom/section nil
            (dom/a #js {:href "javascript: void(0);"
                        :onClick (fn [_]
                                   (om/set-state! owner
                                     :rang-list-show false))}
              "Chat")
            (om/build rang-list (:rang-list app)))
          (dom/section nil
            (dom/a #js {:href "javascript: void(0);"
                        :onClick (partial show-rang-list owner)} "Rang list")
            (om/build header app)
            (om/build stream app)
            (om/build form app)))
        (om/build login app)))))

(defn render []
  (om/root
    chat
    app-state
    {:shared shared-data
     :target (. js/document (getElementById "chat"))}))

(render)

(comment

  (log @app-state)

  )
