(ns template.system
  (:require
    [untangled.server.core :as core]
    [com.stuartsierra.component :as component]

    [om.next.server :as om]
    [template.api.read :as r]
    [template.api.mutations :as mut]
    [untangled.server.impl.components.handler :as h]

    [taoensso.timbre :as timbre]
    [ring.middleware.cookies :as cookies]))

(defn logging-mutate [env k params]
  (timbre/info "Entering mutation:" k)
  (mut/apimutate env k params))

(defrecord AdditionalPipeline [handler]
  component/Lifecycle
  (start [this]
    (let [vanilla-pipeline (h/get-pre-hook handler)]
      (h/set-pre-hook! handler (comp vanilla-pipeline
                                     (partial cookies/wrap-cookies))))
    this)
  (stop [this] this))

(defn make-system [config-path]
  (core/make-untangled-server
    :config-path config-path
    :parser (om/parser {:read r/api-read :mutate logging-mutate})
    :parser-injections #{:config}
    :components {:pipeline (component/using
                             (map->AdditionalPipeline {})
                             [:handler])}))
