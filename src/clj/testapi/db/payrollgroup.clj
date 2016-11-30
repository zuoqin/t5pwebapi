(ns testapi.db.payrollgroup
  (:require [datomic.api :as d]
            [mount.core :refer [defstate]]
            [testapi.db.core :refer [conn] ]
            [testapi.config :refer [env]]))




(defn get-all []
  (let [payrollgroups (d/q '[:find ?p ?en ?ch
                         :where
                         [?p :payrollgroup/chinese ?ch]
                         [?p :payrollgroup/english ?en]
                        ]

                     (d/db conn))
    ]
    (into [] payrollgroups) 
  )
)


