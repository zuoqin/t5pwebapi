(ns testapi.routes.payrollgroup
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            

            [clj-jwt.core  :refer :all]
            [clj-jwt.key   :refer [private-key]]
            [clj-time.core :refer [now plus days]]

            [testapi.db.payrollgroup :as db1]

            [testapi.routes.dbservices :as apidb]
            [clojure.string :as str]
))



(defn dbpayrollgroup-to-json [payrollgroup]
   (let [result {:payrollgroupid (nth payrollgroup 0) 
                 :name (nth payrollgroup 1)
                 }

    ]

    result
  )
)


(defn getPayrollgroups [authorization]
  (let [payrollgroups (map dbpayrollgroup-to-json (db1/get-all))
        ]
        ;(println employee)
        payrollgroups
    )
)



