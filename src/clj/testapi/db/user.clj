(ns testapi.db.user
  (:require [datomic.api :as d]
            [mount.core :refer [defstate]]
            [testapi.db.core :refer [conn] ]
            [testapi.config :refer [env]]))




(defn find-user [login]
  (let [users (d/q '[:find ?login ?e ?dm ?tm ?lang
                      :in $ ?login
                      :where
                      [?u :user/code ?login]
                      [?u :user/employee ?e]
                      [?u :user/datemask ?dm]
                      [?u :user/timemask ?tm]
                      [?u :user/language ?lang]
                     ]
                     ;; [:find ?p
                     ;;  :in $ ?login
                     ;;  :where
                     ;;  [?p :user/code ?login]
                     ;; ]
                     (d/db conn) login)
    ]
    ;(touch conn users)
    users
  )
)


