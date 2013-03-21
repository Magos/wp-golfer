(ns wp-golfer.gui (:use wp-golfer.core seesaw.core)
  (:require [seesaw.bind :as bind] [seesaw.mig])
  )

(def targets (atom () :validator seq?))

(defn golf-panel[]
  (let[tee (text :columns 10 :tip "The article players start at." :id :tee)
       hole (text :columns 10 :tip "The article players are to navigate to." :id :hole)
       tbl (table :minimum-size [300 :by 125]
                  :model [:columns [{:key :article :text "Article"}]
                          :rows []]
                  :id :table
                  )
       update (fn [e] (let[result (golf (first @targets) (last @targets))]
                        (config! tbl :model [:columns [{:key :article :text "Article"}]
                                             :rows (map vector result)])
                        )
                )
       btn (button :action (action :handler update
                                   :name "Search"
                                   :tip "Perform a search")
                   :id :button
                   )
       ]
    (bind/bind
     (bind/funnel tee hole)
     (bind/transform (partial map #(.replaceAll % " " "_")))
     targets
     )
    (listen tee :action update)
    (listen hole :action update)
    (seesaw.mig/mig-panel :items [["Tee:" ""][tee ""]
                                  ["Hole:" ""][hole ""]
                                  [btn "wrap"]
                                  [(scrollable tbl) "span"]])
    )
  )

(defn gui[]
  (let [pnl (golf-panel)]
    (-> (frame :title "Wikipedia Golfer" :content pnl :on-close :dispose)
        pack!
        show!
        )
    )
  )


