(ns testapi.db.employee
  (:require [datomic.api :as d]
            [mount.core :refer [defstate]]
            [testapi.db.core :refer [conn] ]
            [testapi.config :refer [env]]))




(defn get-employee [login]
  (let [employees (d/q '[:find ?e ?en ?ch ?p
                     :in $ ?login
                     :where
                     [?u :user/code ?login]
                     [?u :user/employee ?e]
                     [?e :employee/chinese ?ch]
                     [?e :employee/english ?en]
                     [?e :employee/portrait ?p]
                    ]
                     (d/db conn) login)
    ]
    (first employees)
  )
)


