(defproject rad "0.1.0-SNAPSHOT"
  :description "Restoration Agriculture Development Investment Platform"
  :min-lein-version "2.6.1"

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.229"]
                 [commons-io "2.5"]

                 [navis/untangled-client "0.5.6-SNAPSHOT"]
                 [untangled/om-css "1.0.0"]
                 [org.omcljs/om "1.0.0-alpha45"]

                 [navis/untangled-spec "0.3.8" :scope "test"]

                 [com.taoensso/timbre "4.3.1"]
                 [navis/untangled-server "0.6.1"]]

  :plugins [[lein-cljsbuild "1.1.4"]
            [lein-heroku "0.5.3"]
            [com.jakemccrary/lein-test-refresh "0.15.0"]]

  :hooks [leiningen.cljsbuild]

  :uberjar-name "template.jar"
  :heroku {:app-name      "your-app-name"
           :jdk-version   "1.8"
           :include-files ["target/template.jar"]}

  :test-refresh {:report       untangled-spec.reporters.terminal/untangled-report
                 :with-repl    false
                 :changes-only true}

  :source-paths ["src/server"]
  :test-paths ["specs" "specs/server" "specs/config"]
  :clean-targets ^{:protect false} ["target" "resources/public/js"]

  :figwheel {:css-dirs ["resources/public/css"]}

  :profiles {:uberjar {:main      template.core
                       :aot       :all
                       :cljsbuild {:builds [{:id           "production"
                                             :source-paths ["src/client"]
                                             :jar          true
                                             :compiler     {:main          template.main
                                                            :output-to     "resources/public/js/template.min.js"
                                                            :output-dir    "resources/public/js/prod"
                                                            :asset-path    "js/prod"
                                                            :optimizations :simple}}]}}
             :dev     {:source-paths ["dev/client" "dev/server" "src/client" "src/server"]
                       :dependencies [[binaryage/devtools "0.6.1"]
                                      [figwheel-sidecar "0.5.5"]
                                      [lein-cljsbuild "1.1.4"]
                                      [devcards "0.2.1-7" :exclusions [org.omcljs/om]]]
                       :cljsbuild    {:builds [{:id           "dev"
                                                :figwheel     true
                                                :source-paths ["src/client" "dev/client"]
                                                :compiler     {:main                 cljs.user
                                                               :output-to            "resources/public/js/template.js"
                                                               :output-dir           "resources/public/js/dev"
                                                               :asset-path           "js/dev"
                                                               :source-map-timestamp true
                                                               :optimizations        :none}}
                                               {:id           "test"
                                                :source-paths ["specs/client" "src/client"]
                                                :figwheel     true
                                                :compiler     {:main          template.spec-main
                                                               :output-to     "resources/public/js/specs.js"
                                                               :output-dir    "resources/public/js/specs"
                                                               :asset-path    "js/specs"
                                                               :optimizations :none}}
                                               {:id           "cards"
                                                :figwheel     {:devcards true}
                                                :source-paths ["src/client" "src/cards"]
                                                :compiler     {:main                 template.cards
                                                               :output-to            "resources/public/js/cards.js"
                                                               :output-dir           "resources/public/js/cards"
                                                               :asset-path           "js/cards"
                                                               :optimizations        :none
                                                               :source-map-timestamp true}}
                                               ]}
                       :repl-options {:init-ns user}}})
