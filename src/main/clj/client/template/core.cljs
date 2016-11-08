(ns template.core
  (:require [om.next :as om]
            [untangled.client.core :as uc]
            [untangled.client.data-fetch :as f]
            template.state.mutations
            [untangled.client.logging :as log]))

(defn handle-returns [env k p]
  (log/info "Got return value for " k " -> " p))

(defonce app
         (atom (uc/new-untangled-client
                 :return-handler handle-returns
                 :started-callback (fn [{:keys [reconciler]}]
                                     (f/load-data reconciler [:logged-in? :current-user]
                                                  :post-mutation 'login/login-complete)
                                     ;;TODO: initial load of data
                                     ))))
