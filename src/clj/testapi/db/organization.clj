(ns testapi.db.organization
  (:require [datomic.api :as d]
            [mount.core :refer [defstate]]
            [testapi.db.core :refer [conn] ]
            [testapi.config :refer [env]]))




(defn get-all []
  (let [organizations (d/q '[:find ?o ?en ?ch ?c
                         :where
                         [?o :organization/chinese ?ch]
                         [?o :organization/english ?en]
                         [?o :organization/orgcode ?c]
                        ]

                     (d/db conn))
    ]
    (into [] organizations) 
  )
)


