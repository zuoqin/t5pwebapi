(ns testapi.db.newemp
  (:require [datomic.api :as d]
            [mount.core :refer [defstate]]
            [testapi.db.core :refer [conn] ]
            [testapi.config :refer [env]]))




(defn get-all []
  (let [employees (d/q '[:find ?e ?en ?ch ?m ?c ?h ?g ?b
                         :where
                         [?e :emp_new/empcode ?c]
                         [?e :emp_new/hirestatus ?h]
                         [?e :emp_new/birthday ?b]
                         [?e :emp_new/chinese ?ch]
                         [?e :emp_new/english ?en]
                         [?e :emp_new/gender ?g]
                         [?e :emp_new/major ?m]
                        ]

                     (d/db conn))
    ]
    (into [] employees) 
  )
)


