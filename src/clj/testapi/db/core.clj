(ns testapi.db.core
  (:require [datomic.api :as d]
            [mount.core :refer [defstate]]
            [testapi.config :refer [env]]))


(defn startdb []
  (println (:database-url env))
  (-> env :database-url d/connect)
)

(defstate conn
          :start (startdb)                  ;(-> env :database-url d/connect)
          :stop (-> conn .release))

(defn create-schema []
  (let [schema [{:db/ident              :user/id
                 :db/valueType          :db.type/string
                 :db/cardinality        :db.cardinality/one
                 :db.install/_attribute :db.part/db}
                {:db/ident              :user/first-name
                 :db/valueType          :db.type/string
                 :db/cardinality        :db.cardinality/one
                 :db.install/_attribute :db.part/db}
                {:db/ident              :user/last-name
                 :db/valueType          :db.type/string
                 :db/cardinality        :db.cardinality/one
                 :db.install/_attribute :db.part/db}
                {:db/ident              :user/email
                 :db/valueType          :db.type/string
                 :db/cardinality        :db.cardinality/one
                 :db.install/_attribute :db.part/db}]]
    @(d/transact conn schema)))

(defn entity [conn id]
  (d/entity (d/db conn) id))

(defn touch [conn results]
  "takes 'entity ids' results from a query
    e.g. '#{[272678883689461] [272678883689462] [272678883689459] [272678883689457]}'"
  (let [e (partial entity conn)]
    (map #(-> % first e d/touch) results)))

(defn add-user [conn {:keys [id first-name last-name email]}]
  @(d/transact conn [{:db/id           id
                      :user/first-name first-name
                      :user/last-name  last-name
                      :user/email      email}]))


;; (defn q_search_employee [field text]
;;   '[:find ?p
;;      :in $ 
;;      :where
;;      [?p field text]
;;      ] )

(defmacro q_search_employee [field text]
  `''[:find ~'?p  :where [~'?p ~field ~text] ]
)

(defn find-employee2 [name]
  (let [employees (d/q (eval (q_search_employee :employee/english "Nacho"))  
                       (d/db conn))
                    ]
   
    (touch conn employees)
  )
)


(defn get-message [id]

  (let [entity (d/q '[:find ?entity ?b ?r ?e ?c ?s
                      :in $ ?entity
                      :where
                      [?entity :message/body ?b]
                      [?entity :message/recipients ?r]
                      [?entity :message/english ?e]
                      [?entity :message/chinese ?c]
                      [?entity :message/senddate ?s]
                      [?entity]
                     ]
                    (d/db conn) id)
    ]
    entity
  )
)

(defn find-employee [name]
  (let [employees (d/q '[:find ?employee
                         :in $ ?reference ?name
                         :where
                         [?employee ?reference ?name]
]
                      (d/db conn)
                      :employee/english
                      name
                      )
                    ]
    (touch conn employees)
  )
)


(defn find-user [login]
  (let [users (d/q '[:find ?login ?e ?dm ?tm ?lang
                      :in $ ?login
                      :where
                      [?u :user/code ?login]
                      [?u :user/employee ?e]
                      [?u :user/datemask ?dm]
                      [?u :user/timemask ?tm]
                      [?u :user/language ?lang]
                     ]
                     ;; [:find ?p
                     ;;  :in $ ?login
                     ;;  :where
                     ;;  [?p :user/code ?login]
                     ;; ]
                     (d/db conn) login)
    ]
    ;(touch conn users)
    users
  )
)


(defn get-user-messages_count [conn login]
  (let [messages_count (d/q '[:find (count ?m)
                         :where
                         [?m :message/senddate ?date]
                         [?m :message/recipients ?e]
                         [?u :user/employee ?e]
                         [?u :user/code ?login]
                         [((fn [dt] (.getTime dt)) ?date) ?year]
                        ]
                     (d/db conn) login)
    ]
    (touch conn messages_count)
  )
)

(defn get-user-messages_count [login]
  (let [messages_count (d/q '[:find (count ?m)
                              :in $ ?login
                              :where
                              [?m :message/senddate ?date]
                              [?m :message/recipients ?e]
                              [?u :user/employee ?e]
                              [?u :user/code ?login]
                              [((fn [dt] (.getTime dt)) ?date) ?year]
                             ]

                     (d/db conn) login)
    ]
    (touch conn messages_count)
  )
)




(defn get-user-messages [login]
  (let [messages (->> 

(d/q '[:find ?date ?en ?ch ?m
                           :in $ ?login
                           :where
                           [?m :message/senddate ?date]
                           [?m :message/recipients ?e]
                           [?u :user/employee ?e]
                           [?u :user/code ?login]
                           [?m :message/chinese ?ch]
                           [?m :message/english ?en]
                           ]

                         (d/db conn) login)
                         (sort-by first)
                         (reverse)   
) 
       

;[{:message/senddate #inst "2014-09-08T17:29:57.273-00:00"} {:message/senddate #inst "2014-09-09T17:29:57.273-00:00"}]
                                  
       ]
    ;(touch conn messages)
    messages
  )
)
