(ns user
  (:require [mount.core :as mount]
            testapi.core))

(defn start []
  (mount/start-without #'testapi.core/repl-server))

(defn stop []
  (mount/stop-except #'testapi.core/repl-server))

(defn restart []
  (stop)
  (start))


