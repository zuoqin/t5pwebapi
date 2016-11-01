(ns testapi.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [testapi.layout :refer [error-page]]


            [testapi.routes.home :refer [home-routes]]
            [testapi.routes.services :refer [service-routes]]
            ;[testapi.routes.user :refer [user-routes]]
            ;[testapi.routes.employee :refer [employee-routes]]
            ;[testapi.routes.sysmenu :refer [sysmenu-routes]]
            ;[testapi.routes.messages :refer [messages-routes]]

            

            [compojure.route :as route]
            [testapi.env :refer [defaults]]
            [mount.core :as mount]
            [testapi.middleware :as middleware]))

(mount/defstate init-app
                :start ((or (:init defaults) identity))
                :stop  ((or (:stop defaults) identity)))

(def app-routes
  (routes
    (-> #'home-routes
        (wrap-routes middleware/wrap-csrf)
        (wrap-routes middleware/wrap-formats))
    #'service-routes
    ; #'user-routes
    ; #'employee-routes
    ; #'messages-routes
    ; #'sysmenu-routes
    
    (route/resources "/")
    (route/not-found
      (:body
        (error-page {:status 404
                     :title "page not found"})))))



(defn app [] 
  (middleware/wrap-base  #'app-routes)
)
