(ns testapi.routes.messages
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

(defapi messages-routes
  {:swagger {:ui "/swagger-ui"
             :spec "/swagger.json"
             :data {:info {:version "1.0.0"
                           :title "Sample API"
                           :description "Sample Services"}}}}
  
  (context "/api" []
    :tags ["thingie"]

    (GET "/messages" []
      :header-params [authorization :- String]
      :summary      "x+y with query-parameters. y defaults to 1."
      (ok {
          :Data {:msgcount 707}
          :Messages [
            {:messageid 10001 :senddate "2016-02-02 15:35:12" :subject "The first subject"}
            {:messageid 10002 :senddate "2016-02-02 16:35:12" :subject "The second subject"}
            {:messageid 10003 :senddate "2016-02-02 17:35:12" :subject "The third subject"}
            {:messageid 10004 :senddate "2016-02-02 18:35:12" :subject "The fourth subject"}
            ]
          :MyApplications []
          :PendingApplications []
          }
      )
    )

    (OPTIONS "/messages" []
      ;;:header-params [authorization :- String]
      :summary      "x+y with query-parameters. y defaults to 1."
      (ok "")
    )

  )
)
