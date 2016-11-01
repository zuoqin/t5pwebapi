(ns testapi.routes.user
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            

            [clj-jwt.core  :refer :all]
            [clj-jwt.key   :refer [private-key]]
            [clj-time.core :refer [now plus days]]

            [testapi.db.user :as db]

            [clojure.string :as str]
))

(defn getUser [token]
  (let [
    usercode (:iss (-> token str->jwt :claims)  ) 
    result (first (into [] (db/find-user usercode)   )) 
    res 
    {
     :userid 121
     :usercode (nth result 0)
     :empid (nth result 1)
     :datemask (nth result 2)
     :timemask (nth result 3)
     :language (nth result 4)
    }
        
    ]
    ;(println result)
    res
  )
 
)


