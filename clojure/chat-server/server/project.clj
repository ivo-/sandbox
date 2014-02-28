(defproject chat-server "0.1.0-SNAPSHOT"
  :description "A simple chat service for educational purpose."
  :url "https://github.com/ivo-/chat-server/server"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clj-time "0.6.0"]
                 [crypto-password "0.1.1"]
                 [prismatic/schema "0.1.10"]
                 [com.novemberain/monger "1.5.0"]

                 [me.raynes/fs "1.4.4"]
                 [ring/ring-core "1.2.1"]
                 [ring/ring-jetty-adapter "1.2.1"]

                 [http-kit "2.1.13"]
                 [ring-cors "0.1.0"]
                 [ring/ring-devel "1.2.1"]
                 [org.clojure/core.async "0.1.267.0-0d7780-alpha"]]
  :main ^:skip-aot chat-server.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
