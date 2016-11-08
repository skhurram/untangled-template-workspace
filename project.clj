(defproject template "0.1.0-SNAPSHOT"
  :description "Template Project"
  :min-lein-version "2.6.1"

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.229"]
                 [commons-io "2.5"]

                 [navis/untangled-client "0.6.0-SNAPSHOT"]
                 [untangled/om-css "1.0.0"]
                 [org.omcljs/om "1.0.0-alpha45"]

                 [navis/untangled-spec "0.3.8" :scope "test"]
                 [lein-doo "0.1.7" :scope "test"]
                 [org.clojure/core.async "0.2.391"]
                 [junit/junit "4.8.2"]
                 [org.flywaydb/flyway-core "4.0.2"]
                 [postgresql/postgresql "9.1-901-1.jdbc4"]
                 [com.zaxxer/HikariCP "2.4.7"]
                 [http-kit "2.2.0"]
                 [com.taoensso/timbre "4.3.1"]
                 [navis/untangled-server "0.6.1"]]

  :plugins [[lein-cljsbuild "1.1.4"]
            [lein-doo "0.1.7"]
            [com.jakemccrary/lein-test-refresh "0.15.0"]]

  :doo {:build "automated-tests"
        :paths {:karma "node_modules/karma/bin/karma"}}

  :uberjar-name "template.jar"

  :test-refresh {:report       untangled-spec.reporters.terminal/untangled-report
                 :with-repl    false
                 :changes-only true}

  :source-paths ["src/main/java" "src/main/clj/server" "src/main/clj/client"]
  :java-source-paths ["src/main/java"]
  :test-paths ["src/test/java" "src/test/clj/client" "src/test/clj/server" "src/test/clj/config"]
  :clean-targets ^{:protect false} ["target" "resources/public/js" "resources/private"]

  :figwheel {:css-dirs ["resources/public/css"]}

  :cljsbuild {:builds [{:id           "production"
                        :source-paths ["src/main/clj/client"]
                        :jar          true
                        :compiler     {:main          template.main
                                       :output-to     "resources/public/js/template.min.js"
                                       :output-dir    "resources/public/js/prod"
                                       :asset-path    "js/prod"
                                       :optimizations :simple}}
                       {:id           "dev"
                        :figwheel     true
                        :source-paths ["src/main/clj/client" "dev/client"]
                        :compiler     {:main                 cljs.user
                                       :output-to            "resources/public/js/template.js"
                                       :output-dir           "resources/public/js/dev"
                                       :asset-path           "js/dev"
                                       :source-map-timestamp true
                                       :optimizations        :none}}
                       {:id           "test"
                        :source-paths ["src/test/clj/client" "src/main/clj/client"]
                        :figwheel     true
                        :compiler     {:main          template.spec-main
                                       :output-to     "resources/public/js/specs.js"
                                       :output-dir    "resources/public/js/specs"
                                       :asset-path    "js/specs"
                                       :optimizations :none}}
                       {:id           "automated-tests"
                        :source-paths ["src/test/clj/client" "src/main/clj/client"]
                        :compiler     {:output-to     "resources/private/js/unit-tests.js"
                                       :main          template.all-tests
                                       :asset-path    "js/ci"
                                       :output-dir    "resources/private/js/ci"
                                       :optimizations :none}}
                       {:id           "cards"
                        :figwheel     {:devcards true}
                        :source-paths ["src/main/clj/client" "src/cards"]
                        :compiler     {:main                 template.cards
                                       :output-to            "resources/public/js/cards.js"
                                       :output-dir           "resources/public/js/cards"
                                       :asset-path           "js/cards"
                                       :optimizations        :none
                                       :source-map-timestamp true}}
                       ]}

  :profiles {:uberjar {:main       template.core
                       :aot        :all
                       :prep-tasks ["compile"
                                    ["cljsbuild" "once" "production"]]}
             :dev     {:source-paths ["dev/client" "dev/server" "src/client" "src/server"]
                       :dependencies [[binaryage/devtools "0.6.1"]
                                      [org.clojure/tools.namespace "0.2.11"]
                                      [com.cemerick/piggieback "0.2.1"]
                                      [figwheel-sidecar "0.5.7" :exclusions [org.clojure/tools.reader]]
                                      [devcards "0.2.2" :exclusions [org.omcljs/om]]]
                       :repl-options {:init-ns          user
                                      :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}})
