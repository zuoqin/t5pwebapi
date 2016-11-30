(ns testapi.routes.employee
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [clojure.string :as str]

            [clj-jwt.core  :refer :all]
            [clj-jwt.key   :refer [private-key]]
            [clj-time.core :refer [now plus days]]

            [testapi.routes.dbservices :as apidb]
            [testapi.db.employee :as db]
))

(defn getemployeemap [employee]

  {:Emphr {:empid (nth employee 0) :EmpName (nth employee 1) :portrait (str "/content/portrait/" (nth employee 3))}  }
)

(defn getEmployee [authorization]
  (let [isauthorized (apidb/checkToken authorization)
        username (if (= isauthorized true ) (apidb/get-usercode-by-token (nth (str/split authorization #" ") 1)) "")
        employee (if (> (count username) 0) (getemployeemap (db/get-employee username) )  {:Emphr {:empid 10289 :EmpName "Nacho" :portrait (str "/content/portrait/corina.jpg")}})
        ]

        ;(println employee)
        (ok employee)
    )
)

(defn getDashBoard []
  (ok 
   [{:age 31 :birthday "1985-10-26" :name "First Employee"}
    {:age 41 :birthday "1975-10-26" :name "Barbara Trend"}
    {:age 51 :birthday "1965-10-26" :name "Alexey Zuo"}
    {:age 61 :birthday "1955-10-26" :name "Amck Liu"}
    {:age 71 :birthday "1945-10-26" :name "jolly Tao"}
    {:age 81 :birthday "1935-10-26" :name "Maggie Ha"}
    ]

)
)


