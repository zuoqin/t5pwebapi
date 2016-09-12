(ns testapi.routes.sysmenu
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


(defn getSysMenu []
  (let [res 
  [
    {
      :menucode "WEBSITE"
      :menuopt 21
      :name "System Configuration"
      :submenu "SYSCFG"
      :menulevel 0
      :menuorder 23
      :urltarget ""
    }
    {
      :menucode "WEBSITE"
      :menuopt 23
      :name "System Utility"
      :submenu "SYSUTY"
      :menulevel 0
      :menuorder 22
      :urltarget ""
    }
    {
      :menucode "WEBSITE"
      :menuopt 22
      :name "System Maintenance"
      :submenu "SYSMAS"
      :menulevel 0
      :menuorder 21
      :urltarget ""
    }
  ]
        ]

    res 
  )

)



(defapi sysmenu-routes
  {:swagger {:ui "/swagger-ui"
             :spec "/swagger.json"
             :data {:info {:version "1.0.0"
                           :title "Sample API"
                           :description "Sample Services"}}}}
  
  (context "/api" []
    :tags ["thingie"]

    (GET "/sysmenu" []
      :header-params [authorization :- String]
      :summary      "x+y with query-parameters. y defaults to 1."
      (ok (getSysMenu))
    )

    (OPTIONS "/sysmenu" []
      ;;:header-params [authorization :- String]
      :summary      "x+y with query-parameters. y defaults to 1."
      (ok "")
    )    
  )
)
