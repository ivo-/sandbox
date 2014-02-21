(ns weblog.helpers.url
  (:import java.net.URLEncoder
           java.net.URLDecoder))

(def ^:dynamic *encoding* "UTF-8")

(defn encode [s]
  (URLEncoder/encode s *encoding*))

(defn decode [s]
  (URLDecoder/decode s *encoding*))

(defn redirect [u]
  (str "<script>window.location.href = '" u "';</script>"))
