(ns wp-golfer.gui (:use wp-golfer.core)
  (:import (javax.swing JFrame JPanel JButton JLabel JTextField JList event.DocumentListener) )
  )

(defmacro set-grid! [constraints field value]
  `(set! (. ~constraints ~(symbol (name field)))
         ~(if (keyword? value)
            `(. java.awt.GridBagConstraints
                ~(symbol (name value)))
            value)))

(defmacro grid-bag-layout [container & body]
  (let [c (gensym "c")
        cntr (gensym "cntr")]
    `(let [~c (new java.awt.GridBagConstraints)
           ~cntr ~container]
       ~@(loop [result '() body body]
           (if (empty? body)
             (reverse result)
             (let [expr (first body)]
               (if (keyword? expr)
                 (recur (cons `(set-grid! ~c ~expr
                                          ~(second body))
                              result)
                        (next (next body)))
                 (recur (cons `(.add ~cntr ~expr ~c)
                              result)
                        (next body)))))))))

(defn gui []
  (let [
        tee-field (JTextField. 15)
        hole-field (JTextField. 15)
        output (JLabel. "Lorem ipsum dolor sit amet, consectetur adicisiping elit.")
        update (fn [] (.setText output (golf (.getText tee-field) (.getText hole-field))))
        panel (doto (JPanel.)
                (grid-bag-layout 
                 :fill :BOTH, :insets ;(Insets. 5 5 5 5),
                 :gridx 0, :gridy 0
                 (JLabel. "Tee:")
                 :gridx 1
                 tee-field
                 :gridx 2
                 (JLabel. "Hole:")
                 :gridx 3
                 hole-field
                 :gridx 0, :gridy 1, :gridwidth 2
                 output
                 )
                )
        ]
    ;;(.addDocumentListener tee-field (proxy ))
    (doto (JFrame. "Test")
      (.add panel)
      (.pack)
      (.setVisible true)
      (.setDefaultCloseOperation (. JFrame DISPOSE_ON_CLOSE))
      )
    )
  )

;(gui)