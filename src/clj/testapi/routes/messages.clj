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



(defn getUserMessage [messageid]
  (let [message (first  (into []   (db/get-message 17592186046535)      )  )
        result {:Bcc [] :body (nth message 1) :Cc [] :senddate (.toString (nth message 5))  :subject (nth message 3) :To (nth message 2)}
       ]
    result
  ) 

)

(defn getUserMessages [user page]
  (take  20 (drop (* page 20 )  (map dbmessage-to-json (db/get-user-messages user)  )  ))   
)

(defn build-messages-by-page [usercode page]
  {
  :Data {:msgcount (:db/id  (first (getMessagesCount usercode)   )  ) }
  :Messages (getUserMessages usercode  page)
  :MyApplications []
  :PendingApplications []
  }

)

(defn build-message-detail [messageid]
  (println messageid)
  (getUserMessage messageid)
)

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
      :query-params [{messageid :- Long -1},{page :- Long 0} ]
      :summary      "x+y with query-parameters. y defaults to 1."
      (ok 
       (if (> messageid 0)
         (build-message-detail messageid)
         (build-messages-by-page (apicore/get-usercode-by-token (nth (str/split authorization #" ") 1)) page )
       )
      )
    )

    (OPTIONS "/messages" []
      ;;:header-params [authorization :- String]
      :summary      "x+y with query-parameters. y defaults to 1."
      (ok "")
    )

  )
)
