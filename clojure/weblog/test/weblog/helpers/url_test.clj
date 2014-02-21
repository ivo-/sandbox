(ns weblog.helpers.url-test
  (:use clojure.test weblog.helpers.url))

(deftest test-app
  (testing "encode-decode"
    (let [s "Ф,+!@№!$№€§**()_<>?,.'"]
      (is (= s (decode (encode s)))))))
