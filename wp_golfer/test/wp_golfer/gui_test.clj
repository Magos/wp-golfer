(ns wp-golfer.gui-test
  (:use clojure.test
        wp-golfer.gui
        seesaw.core
        seesaw.table))

(deftest behaviour-test
  (testing "Basic behaviour test with simulated user input."
    (let [frame (gui)
          {:keys [tee hole button table]} (group-by-id frame)
          ]
      (is frame) ;;Verfy the frame was built.
      (value! tee "Plato")
      (value! hole "Theory of forms")
      (.doClick button)
      (is (=
           ["Plato" "Theory_of_forms"]
           (map :article (value-at table (range (row-count table))))
           )
          )
      (value! tee "Rock")
      (value! hole "Geography")
      (.doClick button)
      (is (= 
           ["Rock" "Rock_music" "Cultural_imperialism" "Anthropology" "Geography"]
           (map :article (value-at table (range (row-count table))))
           )
          )
      )
    )
  )   
