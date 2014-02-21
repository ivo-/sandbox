(ns weblog.routes
  (:use compojure.core
        [ring.util.response :only [redirect]])
  (:require [weblog.config :as config]
            [weblog.handlers.home :as home]
            [weblog.handlers.admin :as admin]
            [weblog.templates.not-found :as not-found]
            [compojure.route :as route]
            [cemerick.friend :as friend]
            [cemerick.friend.credentials :as creds]
            [cemerick.friend.workflows :as workflows]))

(def ^:private rnum #"[0-9]+")

(defn- logged? [req]
  (friend/authorized? config/roles (friend/identity req)))

(defroutes admin-routes
  (GET "/articles" req (admin/articles req))
  (ANY "/articles/new" req (admin/articles-new req))
  (ANY "/articles/edit/:id" req (admin/articles req)))

(defroutes base-routes
  (GET "/" _ (redirect "/1"))
  (GET "/tag/:tag" {uri :uri} (redirect (str uri "/1")))
  (GET "/year/:year" {uri :uri} (redirect (str uri "/1")))

  (GET ["/:page", :page rnum] req (home/index req))
  (GET ["/tag/:tag/:page", :page rnum] req (home/tag req))
  (GET ["/year/:year/:page", :page rnum :year rnum] req (home/year req))

  (GET "/about" req (home/about req))
  (GET "/article/:article" req (home/article req))

  (context "/admin" []
           (GET "/" req (redirect (if (logged? req)
                                    "/admin/articles"
                                    "/admin/login")))
           (GET "/login" req (if (logged? req)
                               (redirect "/admin/articles")
                               (admin/login req)))

           (friend/wrap-authorize admin-routes config/roles)
           (friend/logout (ANY "/logout" req (redirect "/"))))

  (route/resources "/")
  (route/not-found (not-found/index)))

(def app
  (friend/authenticate base-routes
                       {:login-uri "/admin/login"
                        :default-landing-uri "/admin/articles"
                        :credential-fn (partial
                                        creds/bcrypt-credential-fn
                                        config/admins)
                        :workflows [(workflows/interactive-form)]}))
