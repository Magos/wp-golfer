(ns wp-golfer.visualisation (:use quil.core))

(defn setup []
  (smooth)                          ;;Turn on anti-aliasing
  (frame-rate 1)                    ;;Set framerate to 1 FPS
  (background 0)
  (text-align :center :top)
  (draw)
  )                 ;;Set the background colour to
                                    ;;  a nice shade of grey.
(defn draw []
  (background 0)
  (stroke 255)       
  (stroke-weight 3)
  (fill 255)
  (let[root nil
     sequence (tree-seq #(not (nil? (:children %))) #(:children %) root)
     filtered (filter #(contains? *parents* (:name %)) sequence)
       ]
    (doseq [par filtered]
      (drawnode par)
      (doseq [chld (:children par)]
        (line (:x par) (:y par) (:x chld) (:y chld))
        (drawnode chld)
        )
      )
    )
  )

(defn drawnode[{:keys [x y name]:as node}]
    (ellipse  x y 5 5)
    (text (str name) x y)
  )

(comment 
  (defsketch visualization            ;;Define a new sketch named visualization
    :title "Drawing test" 		 				;;Set the title of the sketch
    :setup setup                     	;;Specify the setup fn
    :draw draw         		            ;;Specify the draw fn
    :size [400 520])   
  )

(def ^:dynamic *parents* (hash-map :start ["Philosophy"] "Philosophy" (vector "Plato" "Confucius" "Forms")
          "Plato" (vector "Aristotle" "Socrates" "Natural_philosophy"))
          )


(defrecord Node [ x  y parent name])


(def realistic-example {"Mixed_radix" "Arithmetic", "Simon_Stevin" "Arithmetic", "Business" "Arithmetic", "Orders_of_magnitude" "Arithmetic", "Europe" "Arithmetic",
                        "Arithmetic" :start, "Positional_notation" "Arithmetic", "Inverse_element" "Arithmetic", "Cyrillic_numerals" "Arithmetic", "Leonhard_Euler" "Arithmetic", "Arithmetic_function" "Arithmetic", "International_Standard_Book_Number" "Arithmetic",
                        "Linear_algebra" "Arithmetic", "Codex_Vigilanus" "Arithmetic", "Probability_theory" "Arithmetic", "Aryabhata" "Arithmetic", "Algebraic_geometry" "Arithmetic", "Topology" "Arithmetic",
                        "Leonard_Eugene_Dickson" "Arithmetic", "Pure_mathematics" "Arithmetic", "Addition_of_natural_numbers" "Arithmetic", "Ishango_bone" "Arithmetic", "Mathematical_physics" "Arithmetic"
                        "Polish_notation" "Positional_notation"}
  )

(defn make-tree [parent-map]
  (let[root-val (some #(if (= :start (val %)) (key %) nil) parent-map)
       root (->Node 1 1 nil root-val)
       source (atom parent-map)
       collection (transient {root-val root})
       ]
    (while (> (count @source) 0)
      (doseq [entry @source]
        (let [par (get collection (val entry))
              node (->Node 1 1 par (str (key entry)))]
          (if (nil? par)
            nil
            (do
              (assoc! collection (key entry) node)
              (swap! source #(dissoc % (key entry)))
              )
            )          
          )
        )
      )
    (persistent! collection)
    )
  )



;(make-tree realistic-example)

