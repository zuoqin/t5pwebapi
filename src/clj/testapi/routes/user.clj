(ns testapi.routes.user
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            

            [clj-jwt.core  :refer :all]
            [clj-jwt.key   :refer [private-key]]
            [clj-time.core :refer [now plus days]]

            [testapi.db.core :refer [conn] ]
            [testapi.db.core :as db]

            [testapi.routes.services :as apicore]

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


(defapi user-routes
  {:swagger {:ui "/swagger-ui"
             :spec "/swagger.json"
             :data {:info {:version "1.0.0"
                           :title "Sample API"
                           :description "Sample Services"}}}}
  
  (context "/api" []
    :tags ["thingie"]

    (GET "/user" []
      :header-params [authorization :- String]
      :summary      "x+y with query-parameters. y defaults to 1."
      (if (= (apicore/checkToken authorization) true) 
        (ok (getUser (nth (str/split authorization #" ") 1)))
        (ok 0)
      )
      ;;(ok "This is the User")
    )

    (OPTIONS "/user" []
      ;;:header-params [authorization :- String]
      :summary      "x+y with query-parameters. y defaults to 1."
      (ok "")
    )    
  )
)
