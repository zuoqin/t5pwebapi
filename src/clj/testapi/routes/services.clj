(ns testapi.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            
            [clojure.string :as str]
            [clj-jwt.core  :refer :all]
            [clj-jwt.key   :refer [private-key]]
            [clj-time.core :refer [now plus days]]

            [testapi.routes.dbservices :as dbservices]
            [testapi.routes.messages :as messagesapi]
            [testapi.routes.employee :as employeeapi]
            [testapi.routes.newemp :as newempapi]
            [testapi.routes.user :as userapi]
            [testapi.routes.sysmenu :as sysmenuapi]
            [testapi.routes.payrollgroup :as payrollgroupapi]
            [testapi.routes.position :as positionapi]
            [testapi.routes.organization :as organizationapi]
            [testapi.routes.calendar :as calendarapi]
))


(defapi service-routes
  {:swagger {:ui "/swagger-ui"
             :spec "/swagger.json"
             :data {:info {:version "1.0.0"
                           :title "Take5 HR Cloud API"
                           :description "Core Services"}}}}
  (context "/" []
    :tags ["authorization"]
    (POST "/token" []
      ;:return String
      :form-params [grant_type :- String, username :- String, password :- String]
      :summary     "login/password with form-parameters"
      (ok (if (dbservices/checkUser username)
            {:access_token (-> (dbservices/claim username) jwt to-str) :expires_in 99999 :token_type "bearer"}
            ""
         )
      )
    )

  )

  
  (context "/api" []
    :tags ["message"]

    (GET "/messages" []
      :header-params [authorization :- String]
      :query-params [{messageid :- Long -1} ]
      :summary      "retrieve messages or message"

      (messagesapi/getMessages authorization messageid 0)
    )


    (GET "/messages/messages" []
      :header-params [authorization :- String]
      :query-params [{page :- Long 0} ]
      :summary      "retrieve next pagination messages or message"

      (messagesapi/getMessages authorization -1 page)
    )

    (OPTIONS "/messages" []
      :query-params [{messageid :- Long -1},{page :- Long 0} ]
      :summary      "T5P HR cloud allows OPTIONS requests"
      (ok "")
    )


    (OPTIONS "/messages/messages" []
      :summary      "T5P HR cloud allows OPTIONS requests"
      (ok "")
    )
  )


  (context "/api" []
    :tags ["employee"]
  

    (GET "/employee" []
      :name :getemployeeapi
      :header-params [authorization :- String]
      :query-params [{type :- Long 0} ]
      :summary      "Retrieve current employee data"
      
      (if (= type 1)
        (employeeapi/getDashBoard )
        (employeeapi/getEmployee authorization)
        )
      
    )

    (OPTIONS "/employee" []
      :query-params [{type :- Long 0} ]
      :summary      "T5P HR cloud allows OPTIONS requests"
      (ok "")
    )
  )

  (context "/api" []
    :tags ["new employee"]
  

    (GET "/empnew" []
      :name :getnewempapi
      :header-params [authorization :- String]
      :query-params [{id :- Long 0} ]
      :summary      "Retrieve new employees data"
      
      (if (= id 0)  (newempapi/getEmployees)  (newempapi/getEmployee id))       
    )

    (OPTIONS "/empnew" []
      :query-params [{type :- Long 0} ]
      :summary      "T5P HR cloud allows OPTIONS requests"
      (ok "")
    )
  )


  (context "/api" []
    :tags ["user"]
  
    (GET "/user" []
      :header-params [authorization :- String]
      :summary      "x+y with query-parameters. y defaults to 1."
      (if (= (dbservices/checkToken authorization) true) 
        (ok (userapi/getUser (nth (str/split authorization #" ") 1)))
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


  (context "/api" []
    :tags ["system menu"]

    (GET "/sysmenu" []
      :header-params [authorization :- String]
      :summary      "HRMS menu for current user"
      (ok (sysmenuapi/getSysMenu authorization))
    )
    (GET "/sysmenu2" []
      :header-params [authorization :- String]
      :summary      "HRMS menu for current user"
      (ok (sysmenuapi/getSysMenu2))
    )


    (OPTIONS "/sysmenu" []
      ;;:header-params [authorization :- String]
      :summary      "T5P HR cloud allows OPTIONS requests"
      (ok "")
    )    
  )


  (context "/api" []
    :tags ["payrollgroups"]

    (GET "/payrollgroups" []
      :header-params [authorization :- String]
      :summary      "Payrollgroup for current user"
      (ok (payrollgroupapi/getPayrollgroups authorization))
    )

    (OPTIONS "/payrollgroups" []
      ;;:header-params [authorization :- String]
      :summary      "T5P HR cloud allows OPTIONS requests"
      (ok "")
    )    
  )


  (context "/api" []
    :tags ["position"]

    (GET "/position" []
      :header-params [authorization :- String]
      :summary      "Position for current user"
      (ok (positionapi/getPositions authorization))
    )

    (OPTIONS "/position" []
      ;;:header-params [authorization :- String]
      :summary      "T5P HR cloud allows OPTIONS requests"
      (ok "")
    )    
  )


  (context "/api" []
    :tags ["organization"]

    (GET "/organization" []
      :header-params [authorization :- String]
      :summary      "Organization for current user"
      (ok (organizationapi/getOrganizations authorization))
    )

    (OPTIONS "/organization" []
      ;;:header-params [authorization :- String]
      :summary      "T5P HR cloud allows OPTIONS requests"
      (ok "")
    )    
  )

  (context "/api" []
    :tags ["calendar"]

    (GET "/calendar" []
      :header-params [authorization :- String]
      :summary      "Organization for current user"
      (ok (calendarapi/getCalendar authorization))
    )

    (OPTIONS "/calendar" []
      ;;:header-params [authorization :- String]
      :summary      "T5P HR cloud allows OPTIONS requests"
      (ok "")
    )    
  )

)
