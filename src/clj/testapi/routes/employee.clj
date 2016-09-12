(ns testapi.routes.employee
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

(defapi employee-routes
  {:swagger {:ui "/swagger-ui"
             :spec "/swagger.json"
             :data {:info {:version "1.0.0"
                           :title "Sample API"
                           :description "Sample Services"}}}}
  
  (context "/api" []
    :tags ["thingie"]

    (GET "/employee" []
      :header-params [authorization :- String]
      :summary      "x+y with query-parameters. y defaults to 1."
      (ok {:Emphr {:empid 10289 :EmpName "Nacho" :portrait "http://localhost:3000/content/portrait/corina.jpg"}})
    )

    (OPTIONS "/employee" []
      ;;:header-params [authorization :- String]
      :summary      "x+y with query-parameters. y defaults to 1."
      (ok "")
    )

  )
)
