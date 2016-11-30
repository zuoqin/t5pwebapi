(ns testapi.routes.position
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            

            [clj-jwt.core  :refer :all]
            [clj-jwt.key   :refer [private-key]]
            [clj-time.core :refer [now plus days]]

            [testapi.db.position :as db1]

            [testapi.routes.dbservices :as apidb]
            [clojure.string :as str]
))



(defn dbposition-to-json [position]
   (let [result {:positionid (nth position 0) 
                 :positioncode (nth position 2)
                 :name (nth position 1)
                 :orgid (nth position 3)
                 :orgname (nth position 4)
                 }

    ]

    result
  )
)


(defn getPositions [authorization]
  (let [
        usercode (apidb/get-usercode-by-token (nth (str/split authorization #" ") 1))
        res (map dbposition-to-json (db1/get-user-positions usercode)  )    
        ]
    (into [] res )
  )
)



(defn getPosition [authorization]
[
  {
    :positionid 63,
    :positioncode "BC_01",
    :name "Consultant",
    :orgid 18,
    :orgname "Consumer Product "
  },
  {
    :positionid 66,
    :positioncode "BC_02",
    :name "Beauty Make Up Artist",
    :orgid 18,
    :orgname "Consumer Product "
  },
  {
    :positionid 67,
    :positioncode "BC_03",
    :name "Boutique Consultants",
    :orgid 25,
    :orgname "GY Hong Kong Limited"
  },
  {
    :positionid 68,
    :positioncode "BC_04",
    :name "Boutique - Tailor",
    :orgid 25,
    :orgname "GY Hong Kong Limited"
  },
  {
    :positionid 69,
    :positioncode "BC_05",
    :name "Consultants - CPF",
    :orgid 18,
    :orgname "Consumer Product "
  },
  {
    :positionid 103,
    :positioncode "BC_Default",
    :name "Boutique Counter Employee",
    :orgid 66,
    :orgname "Consumer Counter"
  },
  {
    :positionid 147,
    :positioncode "BD_HCMC_Default",
    :name "Business Development Employee",
    :orgid 77,
    :orgname "Business Development"
  }


]
)
