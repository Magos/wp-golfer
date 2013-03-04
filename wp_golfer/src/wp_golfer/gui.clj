(ns wp-golfer.gui (:use wp-golfer.core seesaw.core)
  (:require [seesaw.bind :as bind] [seesaw.mig])
  )
(def targets (atom () :validator seq?))


(defn golf-panel[]
  (let[tee (text :columns 10)
       hole (text :columns 10)
       tbl (table :minimum-size [300 :by 125]
                  :model [:columns [:article]
                          :rows nil
                          ]
                  )
       update (fn [e] (let[result (golf (first @targets) (last @targets))]
                        (config! tbl :model [:columns [:article]
                                             :rows (map vector result)])
                        )
                )
       btn (button :action (action :handler update
                                   :name "Search"
                                   :tip "Perform a search")
                   )
       ]
    (bind/bind
     (bind/funnel tee hole)
     (bind/transform (partial map #(.replaceAll % " " "_")))
     targets
     )
    (listen tee
            :action update)
    (listen hole 
            :action update)
    (seesaw.mig/mig-panel :items [[tee ""][hole ""] [btn "wrap"] [tbl "span"]])
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