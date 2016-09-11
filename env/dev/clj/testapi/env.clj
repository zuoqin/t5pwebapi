(ns testapi.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [testapi.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[testapi started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[testapi has shut down successfully]=-"))
   :middleware wrap-dev})
