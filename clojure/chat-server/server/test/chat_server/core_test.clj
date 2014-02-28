(ns chat-server.core-test
  (:require [clojure.test :refer :all]
            [chat-server.core :refer :all]))

;;; ----------------------------------------------------------------------------
;;; Helpers

(extend-type clojure.lang.Atom
  org.httpkit.server/Channel
  (open? [ch] (not (nil? ch)))
  (close [ch] (reset! ch nil))
  (websocket? [ch] false)
  (send!
    ([ch data] (swap! ch conj data))
    ([ch data close-after-send?]
       (swap! ch conj data)
       (reset! ch nil)))
  (on-receive [ch callback] nil)
  (on-close [ch callback] nil))

(def open? org.httpkit.server/open?)
(def send! org.httpkit.server/send!)
(def close org.httpkit.server/close)
(def websocket? org.httpkit.server/websocket?)

(defn new-chan []
  (atom []))

(defn new-state []
  {:online  (ref (hash-map (atom nil) {}
                           (atom nil) {}))
   :pending (ref (hash-set (atom nil) {}
                           (atom nil) {}))})

(defmacro last-received? [chan msg]
  `(is (= (last (deref ~chan)) ~msg)))

;;; ----------------------------------------------------------------------------
;;; Tests

(deftest helpers-test
  (let [chan (new-chan)]
    (send! chan :test-msg-1)
    (is (last-received? chan :test-msg-1))
    (send! chan :test-msg-2)
    (is (last-received? chan :test-msg-2))
    (send! chan :test-msg-3)
    (send! chan :test-msg-4)
    (last-received? chan :test-msg-4)))

(deftest messages
  (testing "allowed messages"
    (let [chan (new-chan)]
      (push! chan :info "test" "info")
      (last-received? chan "{:type :info, :text testinfo}")
      (push! chan :warn "test" "warn")
      (last-received? chan "{:type :warn, :text testwarn}")
      (push! chan :auth "test" "auth")
      (last-received? chan "{:type :auth, :text testauth}")
      (push! chan :post "test" "post")
      (last-received? chan "{:type :post, :text testpost}")
      (is (thrown? Exception (push! chan :not-existing "msg")))))

  (testing "messages format"
    (let [chan (new-chan)]
      (push! chan :post ["test" "post"])
      (last-received? chan "{:type :post, :text testpost}")
      (push! chan :warn [[["test"]] "warn"])
      (last-received? chan "{:type :warn, :text testwarn}")))

  (testing "messages broadcasting "
    (let [{:keys [online _] :as state} (new-state)]
      (broadcast! state :post "test")
      (doseq [[chan _] @online]
        (last-received? chan "{:type :post, :text test}")))))
