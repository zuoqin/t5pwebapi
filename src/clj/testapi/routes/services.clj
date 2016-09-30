(ns testapi.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            
            [clojure.string :as str]
            [clj-jwt.core  :refer :all]
            [clj-jwt.key   :refer [private-key]]
            [clj-time.core :refer [now plus days]]

            [testapi.db.core :refer [conn] ]
            [testapi.db.core :as db]
))


(defn claim [user] 
  {:iss user
   :exp (plus (now) (days 1))
   :iat (now)
  }
)


(defn checkUser [user]
  (if (> (count (db/find-user  user)) 0)
    true
    false
  )

  ;conn
)

(defn get-usercode-by-token [token]
  (let [
    usercode (:iss (-> token str->jwt :claims)  ) 
    result (first (into [] (db/find-user usercode)   )) 
    ]
    (nth result 0)
  )
 
)


(defn verifyToken [token]
  (try
     (-> token str->jwt :claims)
     (catch Exception e {}))


)

(defn checkToken [token]
  (let [key (nth (str/split token #" ") 1)]

    (if (= (:iss 
      (verifyToken key) ) nil)
      false
      true
    )

  )
)

(defapi service-routes
  {:swagger {:ui "/swagger-ui"
             :spec "/swagger.json"
             :data {:info {:version "1.0.0"
                           :title "Sample API"
                           :description "Sample Services"}}}}
  (POST "/token" []
    ;:return String
    :form-params [grant_type :- String, username :- String, password :- String]
    :summary     "login/password with form-parameters"
    (ok (if (checkUser username)
          {:access_token (-> (claim username) jwt to-str) :expires_in 99999 :token_type "bearer"}
          ""
       )
    )
  )
  
  (context "/api" []
    :tags ["thingie"]


    (GET "/plus" []
      :return       Long
      :query-params [x :- Long, {y :- Long 1}]
      :summary      "x+y with query-parameters. y defaults to 1."
      (ok (+ x y)))

    (POST "/minus" []
      :return      Long
      :body-params [x :- Long, y :- Long]
      :summary     "x-y with body-parameters."
      (ok (- x y)))

    (GET "/times/:x/:y" []
      :return     Long
      :header-params [authorization :- String]
      :path-params [x :- Long, y :- Long]
      :summary     "x*y with path-parameters"
      (if (= (checkToken authorization) true) 
        (ok ( * x  y))
        (ok 0)
      )
    )

    (POST "/divide" []
      :return      Double
      :form-params [x :- Long, y :- Long]
      :summary     "x/y with form-parameters"
      (ok (/ x y)))

    (GET "/power" []
      :return      Long
      :header-params [x :- Long, y :- Long]
      :summary     "x^y with header-parameters"
      (ok (long (Math/pow x y))))))
