(defproject weblog "0.1.2-SNAPSHOT"
  :description "Blog application experiment with Clojure."
  :url "http://github.com/ivo-/weblog"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [com.cemerick/friend "0.1.5"]
                 [hiccup "1.0.3"]
                 [korma "0.3.0-RC5"]
                 [compojure "1.1.5"]
                 [ring/ring-jetty-adapter "1.1.6"]
                 [mysql/mysql-connector-java "5.1.25"]
                 [org.clojure/math.numeric-tower "0.0.2"]]
  :plugins [[lein-ring "0.8.5"]]
  :main weblog.core
  :ring {:init weblog.core/init
         :handler weblog.core/app
         :destroy weblog.core/destroy
         :nrepl {:start? true}}
  :profiles {:dev {:dependencies [[ring-mock "0.1.5"]
                                  [lein-annotations "0.1.0"]]}})
