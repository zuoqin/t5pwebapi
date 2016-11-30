(ns testapi.routes.newemp
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [clojure.string :as str]

            [clj-jwt.core  :refer :all]
            [clj-jwt.key   :refer [private-key]]
            [clj-time.core :refer [now plus days]]

            [testapi.routes.dbservices :as apidb]
            [testapi.db.newemp :as db]
))


(defn employee-to-json [employee]
   (let [result {:empid (nth employee 0) 
                 :empcode (nth employee 4)
                 :empname (nth employee 1)
                 :hirestatus (nth employee 5)
                 :birthday (.toString (nth employee 7))
                 :gender (nth employee 6)
                 :major (nth employee 3)
                 }

    ]

    result
  )
)


(defn getEmployee [id]
  (let [employees (map employee-to-json (db/get-all))

        employee (filter (fn [x] (if (= (:empid x) id) true false))  employees)
        ]
        ;(println employee)
        (ok employee)
    )
)

(defn getEmployees []
  (let [employees (map employee-to-json (db/get-all))
        ]
        ;(println employee)
        (ok employees)
    )
)



