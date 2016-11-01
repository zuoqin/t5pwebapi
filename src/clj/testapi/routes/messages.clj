(ns testapi.routes.messages
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            

            [clj-jwt.core  :refer :all]
            [clj-jwt.key   :refer [private-key]]
            [clj-time.core :refer [now plus days]]

            
            [testapi.db.messages :as db]

            [testapi.routes.dbservices :as apidb]
            [clojure.string :as str]
))

(defn getMessagesCount [user]
  (db/get-user-messages_count user)
)

(defn dbmessage-to-json [message]
   (let [result {:messageid (nth message 3) 
                 :senddate (.toString (nth message 0))
                 :subject (nth message 1)
                 }

    ]

    result
  )
)


(defn recipient-to-json [recipient]
  {:empname recipient}
)

(defn getUserMessage [messageid]
  (let [ dbmessage (db/get-message messageid) 
        ;message (first (into [] dbmessage))       
        result { :body (nth dbmessage 3) 
                :Cc (map recipient-to-json (nth dbmessage 5)) :senddate (.toString (nth dbmessage 2))  
                :subject (nth dbmessage 0) :To (map recipient-to-json (nth dbmessage 4))
                }            

        
        ]
   result
  ) 

)

(defn getUserMessages [user page]
  (map dbmessage-to-json (db/get-user-messages user page)  )     
)

(defn build-messages-by-page [usercode page]
  {
  :Data {:msgcount (first  (first (getMessagesCount usercode)   )  ) }
  :Messages (getUserMessages usercode  page)
  :MyApplications []
  :PendingApplications []
  }

)

(defn build-message-detail [messageid]
  (println messageid)
  (getUserMessage messageid)
)



(defn getMessages [authorization messageid page]

  (ok 
    (if (> messageid 0)
      (build-message-detail messageid)
      (build-messages-by-page (apidb/get-usercode-by-token (nth (str/split authorization #" ") 1)) page )
    )
  )
)


;; (defapi messages-routes
;;   {:swagger {:ui "/swagger-ui"
;;              :spec "/swagger.json"
;;              :data {:info {:version "1.0.0"
;;                            :title "Take5 HR Cloud API"
;;                            :description "Messaging Services"}}}}
  
;;   (context "/api" []
;;     :tags ["messages"]

;;     (GET "/messages" []
;;       :header-params [authorization :- String]
;;       :query-params [{messageid :- Long -1},{page :- Long 0} ]
;;       :summary      "retrieve messages or message"
      
;;     )

;;     (OPTIONS "/messages" []
;;       ;;:header-params [authorization :- String]
;;       :summary      "x+y with query-parameters. y defaults to 1."
;;       (ok "")
;;     )

;;   )
;; )
