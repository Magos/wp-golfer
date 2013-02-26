(ns wp-golfer.gui (:use wp-golfer.core seesaw.core)
  (:require [seesaw.bind :as bind] [seesaw.mig])
  )
(def targets (atom ()))


(defn golf-panel[]
  (let[tee (text :columns 10)
       hole (text :columns 10)
       btn (button :text "Search")
       output (label "")
       update (fn [e] (let[result (wp-golfer.core/golf (first @targets) (last @targets))]
                        (text! output (apply str (interpose " " result)))
                        )
                )
       ]
    (bind/bind
     (bind/funnel tee hole)
     (bind/transform (partial map #(.replaceAll % " " "_")))
     targets
     )
    (listen btn :action update)
    (seesaw.mig/mig-panel :items [[tee ""][hole ""] [btn "wrap"] [output "span"]])
    )
  )

(defn gui[]
  (let [pnl (golf-panel)]
  (-> (frame :title "Wikipedia Golfer" :content pnl)
      pack!
      show!
   )
  )
  )