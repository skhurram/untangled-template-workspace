(ns template.main
  (:require [template.core :refer [app]]
            [untangled.client.core :as core]
            [template.ui.root :as root]))

(reset! app (core/mount @app root/Root "app"))
