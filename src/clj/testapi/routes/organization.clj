(ns testapi.routes.organization
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            

            [clj-jwt.core  :refer :all]
            [clj-jwt.key   :refer [private-key]]
            [clj-time.core :refer [now plus days]]

            [testapi.db.organization :as db1]

            [testapi.routes.dbservices :as apidb]
            [clojure.string :as str]
))



(defn dbmenu-to-json [menu]
   (let [result {:menucode (nth menu 1) 
                 :menuopt (nth menu 5)
                 :name (nth menu 0)
                 :submenu (nth menu 4)
                 :menulevel (nth menu 2)
                 :menuorder (nth menu 6)
                 :urltarget (nth menu 3)
                 }

    ]

    result
  )
)


(defn organization-to-json [organization]
   (let [result {:orgid (nth organization 0) 
                 :orgname (nth organization 1)
                 :orgcode (nth organization 3)
                 }

    ]

    result
  )
)


(defn getOrganizations [authorization]
  (let [organizations (into [] (map organization-to-json (db1/get-all)) ) 
        ]
        ;(println employee)
        organizations 
    )
)




(defn getOrganization [authorization]
[
  {
    :orglevel 3,
    :orgcode "Accounting",
    :orgname "Accounting",
    :parentorgid 13,
    :orgid 35
  },
  {
    :orglevel 3,
    :orgcode "BC",
    :orgname "Consumer Counter",
    :parentorgid 18,
    :orgid 66
  },
  {
    :orglevel 3,
    :orgcode "BD_HCMC",
    :orgname "Business Development",
    :parentorgid 75,
    :orgid 77
  },

]
)
