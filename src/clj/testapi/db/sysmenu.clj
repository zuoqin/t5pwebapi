(ns testapi.db.sysmenu
  (:require [datomic.api :as d]
            [mount.core :refer [defstate]]
            [testapi.db.core :refer [conn] ]
            [testapi.config :refer [env]]))


(defmacro q_get_menu [language]
  `'
  '[:find ~'?name ~'?code ~'?level ~'?u ~'?s ~'?opt ~'?o 
    :where
      [~'?m ~language ~'?name]
      [~'?m :sysmenu/menuopt ~'?opt]
      [~'?m :sysmenu/menuorder ~'?o]
      [~'?m :sysmenu/menucode ~'?code]
      [~'?m :sysmenu/menulevel ~'?level]
      [~'?m :sysmenu/urltarget ~'?u]
      [~'?m :sysmenu/submenu ~'?s]
    ]
)

(defn get-user-menu [name]
  (let [
        language_num (first (first (into [] (d/q '[:find ?l
                                 :in $ ?name
                                 :where
                                 [?u :user/code ?name]
                                 [?u :user/language ?l]
                                ]                          
                                (d/db conn) name ))))

        ;; language (if (= language_num 0) "sysmenu/english" 
        ;;             (if (= language_num 1) "sysmenu/chinese"  
        ;;                 "sysmenu/big5"
        ;;                 )
        ;;              )


        menus (if (= language_num 0) 

                (d/q (eval (q_get_menu :sysmenu/english))
                       (d/db conn))

                    (if (= language_num 1) 
                      (d/q (eval (q_get_menu :sysmenu/chinese))
                        (d/db conn))
                        
                     ;;here should be big5 or japanese
                     (d/q (eval (q_get_menu :sysmenu/chinese))
                        (d/db conn))
                        
                        )
                     )
        
                    ]
    (into [] menus) 
   ;language_num
  )
)

(defn get-sysmenu [authorization]
  (let [
        menus (into [] (d/q '[:find ?c ?e ?code ?l ?u ?s ?opt ?o
                               :where
                               [?m :sysmenu/chinese ?c]
                               [?m :sysmenu/english ?e]
                               [?m :sysmenu/menuopt ?opt]
                               [?m :sysmenu/menuorder ?o]
                               [?m :sysmenu/menucode ?code]
                               [?m :sysmenu/menulevel ?l]
                               [?m :sysmenu/urltarget ?u]
                               [?m :sysmenu/submenu ?s]
                              ]
                                (d/db conn)) ) 

                 ]
    menus
  )
)
