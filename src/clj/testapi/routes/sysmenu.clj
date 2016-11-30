(ns testapi.routes.sysmenu
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

(defn getSysMenu2 [] 
  [
    {
      :menucode "WEBSITE"
      :menuopt 21
      :name "NewEmployees"
      :submenu "SYSCFG"
      :menulevel 0
      :menuorder 23
      :urltarget ""
    }
    {
      :menucode "WEBSITE"
      :menuopt 23
      :name "ImageExample"
      :submenu "SYSUTY"
      :menulevel 0
      :menuorder 22
      :urltarget ""
    }
    {
      :menucode "WEBSITE"
      :menuopt 22
      :name "ListViewExample"
      :submenu "SYSMAS"
      :menulevel 0
      :menuorder 21
      :urltarget ""
    }
  ]
)

(defn getSysMenu [authorization]
  (let [
        usercode (apidb/get-usercode-by-token (nth (str/split authorization #" ") 1))
        res (map dbmenu-to-json (db/get-user-menu usercode)  )    
        ]
    ;(db/get-user-menu usercode)
    (into [] res )              ;res 
  )

)
