(ns wp-golfer.gui (:use wp-golfer.core seesaw.core)
  (:import (javax.swing JTextField ) )
  )
(defn golf-gui []
  (native!)
  (let [tee-lbl "Tee:"
        hole-lbl "Hole:"
        tee (JTextField. 15)
        hole (JTextField. 15)
        storage (atom "Output." :validator-fn string?)
        label (javax.swing.JLabel. @storage)
        do-search (fn [x] (golf (.getText tee) (.getText hole)))
        update (fn [evt] (invoke-later (swap! storage do-search) (.setText label (str @storage))))
        update-action (action :handler update :name "Search")
        pan (flow-panel 
             :align :left
             :hgap 20
             :items [tee-lbl tee hole-lbl hole update-action label]
             )
        ]
    ;(listen tee :key update)
    ;(listen hole :key update)
    (doto (frame :content pan :on-close :dispose)
      (pack!)
      (show!))
    )
  )

(golf-gui)