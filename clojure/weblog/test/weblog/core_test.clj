(ns weblog.core-test
  (:use clojure.test ring.mock.request weblog.core))

(deftest test-app
  (testing "Home"
    (is (= (:status (app (request :get "/"))) 302))
    (is (= (:status (app (request :get "/1"))) 200)))

  (testing "About"
    (is (= (:status (app (request :get "/about"))) 200))
    (is (= (:status (app (request :get "/about/"))) 404)))

  (testing "Admin"
    (is (= (:status (app (request :get "/admin"))) 302))
    (is (= (:status (app (request :get "/admin/login"))) 200))
    (is (= (:status (app (request :get "/admin/login/articles"))) 302))
    (is (= (:status (app (request :get "/admin/login/articles/new"))) 302))
    (is (= (:status (app (request :get "/admin/login/articles/edit"))) 302)))

  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))
