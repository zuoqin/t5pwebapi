(ns testapi.routes.employee
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            

            [clj-jwt.core  :refer :all]
            [clj-jwt.key   :refer [private-key]]
            [clj-time.core :refer [now plus days]]

            [testapi.routes.dbservices :as apidb]
))

(defn getEmployee [authorization mainpath]
  (ok {:Emphr {:empid 10289 :EmpName "Nacho" :portrait (str "/content/portrait/corina.jpg")}})
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


