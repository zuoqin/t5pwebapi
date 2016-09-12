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
))

(defn getUser [token]
  (let [res 
    {
     :userid 121
     :usercode "nacho"
     :empid 10289
     :datemask "yyyy/MM/dd"
     :timemask "HH:mm:ss"
     :language 0
    }
        
    ]

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
        (ok (getUser authorization))
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
