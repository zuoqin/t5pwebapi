(ns testapi.db.position
  (:require [datomic.api :as d]
            [mount.core :refer [defstate]]
            [testapi.db.core :refer [conn] ]
            [testapi.config :refer [env]]))


(defmacro q_get_positions [language]
  (let [lang1 (keyword (str "position/" language))
        lang2 (keyword (str "organization/" language))
    ]
  `'
  '[:find ~'?p ~'?name ~'?code ~'?o ~'?oname 
    :where
      [~'?p ~lang1 ~'?name]
      [~'?p :position/positioncode ~'?code]
      [~'?p :position/organization ~'?o]
      [~'?o ~lang2 ~'?oname]
    ]
  )
)

(defn get-user-positions [name]
  (let [
        language_num (first (first (into [] (d/q '[:find ?l
                                 :in $ ?name
                                 :where
                                 [?u :user/code ?name]
                                 [?u :user/language ?l]
                                ]                          
                                (d/db conn) name ))))


        positions (if (= language_num 0) 

                (d/q (eval (q_get_positions "english"))
                       (d/db conn))

                    (if (= language_num 1) 
                      (d/q (eval (q_get_positions "chinese"))
                        (d/db conn))
                        
                     ;;here should be big5 or japanese
                     (d/q (eval (q_get_positions "chinese"))
                        (d/db conn))
                        
                        )
                     )
        
                    ]
    (into [] positions) 
  )
)



; (defn get-all []
;   (let [positions (d/q '[:find ?p ?name ?c ?o ?oname
;                          :where
;                          [?p :position/chinese ?ch]
;                          [?p :position/english ?en]
;                          [?p :position/organization ?o]
;                          [?o :organization/english ?oname]
;                         ]

;                      (d/db conn))
;     ]
;     (into [] positions) 
;   )
; )


