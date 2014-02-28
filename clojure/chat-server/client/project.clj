(defproject client "0.1.0-SNAPSHOT"
  :description "Chat service client."
  :url "https://github.com/ivo-/chat-server/client"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2156"]

                 ;; Front-end
                 [om "0.4.0"]
                 [prismatic/schema "0.1.10"]
                 [org.clojure/core.async "0.1.267.0-0d7780-alpha"]

                 ;; Back-end
                 [hiccup "1.0.4"]
                 [me.raynes/fs "1.4.4"]
                 [ring/ring-core "1.2.1"]
                 [ring/ring-jetty-adapter "1.2.1"]
                 [com.cemerick/piggieback "0.1.2"]]

  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
  :injections [(require '[cljs.repl.browser :refer (repl-env)]
                        '[cemerick.piggieback :refer (cljs-repl)]
                        '[cemerick.austin.repls :as austin])
               (defn browser-repl []
                 (cljs-repl :repl-env (repl-env :port 9000)))
               (defn phantomjs-repl []
                 (cemerick.austin.repls/exec))]

  :plugins [[lein-cljsbuild "1.0.1-SNAPSHOT"]
            [com.cemerick/austin "0.1.3"]
            [com.cemerick/clojurescript.test "0.2.1"]]

  :source-paths ["src/clj" "src/cljs"]
  :test-source-paths ["test/clj" "test/cljs"]

  :cljsbuild {
              :builds {
                       :dev
                       {:source-paths ["src/cljs" "src/cljs/repl"]
                        :compiler {:output-to "compiled/js/main-debug.js"
                                   :output-dir "compiled/js"
                                   :optimizations :none
                                   :output-wrapper true
                                   :source-map true}}
                       :prod
                       {:source-paths ["src/cljs"]
                        :compiler {:output-to "compiled/js/main.js"
                                   :optimizations :advanced
                                   :pretty-print false}}
                       :test
                       {:source-paths ["src/cljs" "test/cljs/client" "src/cljs/repl"]
                        :compiler {:output-to "compiled/js/unit-test.js"
                                   :optimizations :whitespace
                                   :output-wrapper true
                                   :pretty-print true}}
                       }}
  :main ^:skip-aot client.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
