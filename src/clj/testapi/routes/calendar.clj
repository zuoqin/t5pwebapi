(ns testapi.routes.calendar
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            

            [clj-jwt.core  :refer :all]
            [clj-jwt.key   :refer [private-key]]
            [clj-time.core :refer [now plus days]]

            [testapi.db.sysmenu :as db]

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


(defn getCalendar [authorization]
[
  {
    :code "Statutory",
    :name "Statutory",
    :lastdate "2011-08-01",
    :countryname ""
  },
  {
    :code "SC_14",
    :name "Standard Calendar China 14",
    :lastdate "2015-10-10",
    :countryname "China Mainland"
  },
  {
    :code "SC_25",
    :name "Standard Calendar China 25",
    :lastdate "2015-10-10",
    :countryname "China Mainland"
  },
  {
    :code "SC_36",
    :name "Standard Calendar China 36",
    :lastdate "2015-10-10",
    :countryname "China Mainland"
  },
  {
    :code "SC_CN",
    :name "Standard Calendar China",
    :lastdate "2015-10-10",
    :countryname "China Mainland"
  }



]
)

