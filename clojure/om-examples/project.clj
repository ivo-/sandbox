(defproject om-examples "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2138"]
                 [org.clojure/core.async "0.1.267.0-0d7780-alpha"]
                 [com.cemerick/piggieback "0.1.2"]
                 [om "0.1.1"]]

  :plugins [[lein-cljsbuild "1.0.1-SNAPSHOT"]]

  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
  :injections [(require '[cljs.repl.browser :refer (repl-env)]
                        '[cemerick.piggieback :refer (cljs-repl)])
               (defn repl [] (cljs-repl :repl-env (repl-env :port 9000)))]

  :source-paths ["src"]

  :cljsbuild
  {:builds [{:id "todo"
             :source-paths ["src/om_examples/todo"]
             :compiler {:pretty-print true
                        :output-to "todo.js"
                        :optimizations :whitespace}}]}

  :profiles {:uberjar {:aot :all}}
  :main ^:skip-aot om-examples.core
  :target-path "target/%s")
