(defproject om-examples "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
                 [org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2173"]
                 [org.clojure/core.async "0.1.267.0-0d7780-alpha"]
                 [om "0.5.1"]
                 [com.cemerick/piggieback "0.1.2"]]

  :plugins [[lein-cljsbuild "1.0.1-SNAPSHOT"]]

  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
  :injections [(require '[cljs.repl.browser :refer (repl-env)]
                        '[cemerick.piggieback :refer (cljs-repl)])
               (defn repl [] (cljs-repl :repl-env (repl-env :port 9000)))]

  :source-paths ["src"]

  :cljsbuild
  {:builds [{:id "intro"
             :source-paths ["src/om_examples/intro"]
             :compiler {:pretty-print true
                        :output-to "intro.js"
                        :optimizations :whitespace}}
            {:id "todo"
             :source-paths ["src/om_examples/todo"]
             :compiler {:pretty-print true
                        :output-to "todo.js"
                        :optimizations :whitespace}}
            {:id "events"
             :source-paths ["src/om_examples/events"]
             :compiler {:pretty-print true
                        :output-to "events.js"
                        :optimizations :whitespace}}
            {:id "om41"
             :source-paths ["src/om_examples/om41"]
             :compiler {:pretty-print true
                        :output-to "om41.js"
                        :optimizations :whitespace}}]}

  :profiles {:uberjar {:aot :all}}
  :main ^:skip-aot om-examples.core
  :target-path "target/%s")
