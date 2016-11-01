(ns testapi.db.messages
  (:require [datomic.api :as d]
            [mount.core :refer [defstate]]
            [testapi.db.core :refer [conn] ]
            [testapi.config :refer [env]]))


(defn get-message [id]

  (let [
        entity (first (into [] (d/q '[:find ?e ?c ?s ?b
                                  :in $ ?entity
                                  :where
                                  [?entity :message/english ?e]
                                  [?entity :message/chinese ?c]
                                  [?entity :message/senddate ?s]
                                  [?entity :message/body ?b]
                                  [?entity]
                                  ]
                                (d/db conn) id) ) )  

        recipientstypes (sort-by first 
                          (into []  
                            (d/q '[:find ?eid ?t
                                   :in $ [?t ...]
                                   :where
                                   [?eid :db/ident ?t]
                                  ]
                                (d/db conn) 
                [:recipient.type/to :recipient.type/cc :recipient.type/bcc])

                                          )) 

        allrecipients (into [] (d/q '[:find ?t ?e 
                                   :in $ ?m
                                   :where
                                   [?m :message/recipients ?r]
                                   [?r :recipient/employee ?w]
                                   [?r :recipient/type ?t]
                                   [?w :employee/english ?e]
                                   ]
                                 (d/db conn) id) )   

       ;recipients (map (fn [x]))
;;         allmessages (map (fn [x] [(nth x 0) (nth x 1) (nth x 2) (nth x 3) 

;; (if (= (nth x 4) (first (nth recipientstypes 0))) "To" 
;;  (if (= (nth x 4) (first (nth recipientstypes 1)))  "Cc"  "Bcc") 
;;   )


 

;; ]   )  tomessages)
        torecipients (into [] (map (fn [x] (nth x 1)) (filter (fn [x] (if (= (first x)  (first (nth recipientstypes 0 ))   )   true false    )) allrecipients)  )   ) 

        ccrecipients (into [] (map (fn [x] (nth x 1)) (filter (fn [x] (if (= (first x)  (first (nth recipientstypes 1 ))   )   true false    )) allrecipients)  )  ) 

        message [(nth entity 0) (nth entity 1) (nth entity 2) (nth entity 3 )
                 torecipients ccrecipients
                 ]
    ]
    message
    ;(nth recipientstypes 0)
  )
)


(defn get-user-messages_count [login]
  (let [messages_count (d/q '[:find (count ?m)
                              :in $ ?login
                              :where
                              [?m :message/senddate ?date]
                              [?u :user/employee ?e]
                              [?u :user/code ?login]
                             ]

                     (d/db conn) login)
    ]
    messages_count
  )
)




(defn get-user-messages [login page]
  (let [
        tomessages (take 20
                 (drop (* page 20)
                 (reverse (sort-by first 
                                   (d/q '[:find ?date ?en ?ch ?m
                                          :in $ ?login
                                          :where
                                          [?m :message/senddate ?date]
                                          [?m :message/recipients ?r]
                                          [?r :recipient/employee ?e]
                                          [?u :user/employee ?e]
                                          [?u :user/code ?login]
                                          [?m :message/chinese ?ch]
                                          [?m :message/english ?en]
                                          ]


;; [:find ?date ?en ?ch ?m ?t
;;  :in $ ?login
;;  :where
;;  [?m :message/senddate ?date]
;;  [?m :message/Recipients ?r]
;;  [?r :recipient/type ?t]
;;  [?r :recipient/employee ?e]
;;  [?u :user/employee ?e]
;;  [?u :user/code ?login]
;;  [?m :message/chinese ?ch]
;;  [?m :message/english ?en]
;;  ]
                                        (d/db conn) login)
                                   ))



                 ))   
  


        allmessages_sorted tomessages 


        

;[{:message/senddate #inst "2014-09-08T17:29:57.273-00:00"} {:message/senddate #inst "2014-09-09T17:29:57.273-00:00"}]
                                  
       ]
    ;(touch conn messages)
    ;(clojure.set/union tomessages ccmessages bccmessages )
    ;(println (count tomessages))
    ;(println (count ccmessages))
    ;(println (count bccmessages))
    ;(println (count allmessages_sorted))
    ;allmessages_sorted
    allmessages_sorted
    ;(println recipientstypes)
  )
)
