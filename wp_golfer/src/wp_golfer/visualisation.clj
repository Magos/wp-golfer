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
  (let[root (node-ize :start (partial get *parents*) (width) (height))
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


(defrecord node [^long x ^long y children ^String name])


(defn node-ize 
  "Takes a name and an fn to get children of a name.
  Returns the root node of the corresponding tree of nodes."
  [name children width height]
  (let[child-list (children name)
       nodes (for [child child-list](node-ize child children width height))]
    (->node (rand-int width) (rand-int height) nodes name)
    )
  )


(def realistic-example {"Mixed_radix" "Arithmetic", "Simon_Stevin" "Arithmetic", "Business" "Arithmetic", "Orders_of_magnitude" "Arithmetic", "Europe" "Arithmetic", "Finite_geometry" "Arithmetic", "Calculator" "Arithmetic", "Set_theory" "Arithmetic", "Order_of_operations" "Arithmetic", "Henry_Burchard_Fine" "Arithmetic", "Stepped_Reckoner" "Arithmetic", "Diophantine_equations" "Arithmetic", "Roman_abacus" "Arithmetic", "Commutativity" "Arithmetic", "Numerical_analysis" "Arithmetic", "Method_of_complements" "Arithmetic", "Mathematical_optimization" "Arithmetic", "Brahmagupta" "Arithmetic", "Mathematical_Reviews" "Arithmetic", "Science" "Arithmetic", "Square_root" "Arithmetic", "Algorism" "Arithmetic", "Radix" "Arithmetic", "Logarithm" "Arithmetic", "Numerical_digit" "Arithmetic", "Arithmetic_progression" "Arithmetic", "Fibonacci" "Arithmetic", "Category_theory" "Arithmetic", "Multiplication" "Arithmetic", "Microsoft_Excel" "Arithmetic", "Inverse_function" "Arithmetic", "Multiplicative_inverse" "Arithmetic", "Archimedes" "Arithmetic", "Chinese_numerals" "Arithmetic", "Multilinear_algebra" "Arithmetic", "Computation" "Arithmetic", "Control_theory" "Arithmetic", "Jainism" "Arithmetic", "Multiplicative_identity" "Arithmetic", "New_Math" "Arithmetic", "Areas_of_mathematics" "Arithmetic", "Mathematical_logic" "Arithmetic", "Arabic_numerals" "Arithmetic", "Islamic" "Arithmetic", "Severus_Sebokht" "Arithmetic", "Zero" "Arithmetic", "Algebra" "Arithmetic", "Arithmetic_properties" "Arithmetic", "Computational_mathematics" "Arithmetic", "Prime_number" "Arithmetic", "Introduction_to_Arithmetic" "Arithmetic", "Number" "Arithmetic", "Sexagesimal" "Arithmetic", "Arithmetic" "Arithmetic", "Slide_rule" "Arithmetic", "Number_line" "Arithmetic", "Calculus" "Arithmetic", "Abstract_algebra" "Arithmetic", "Quantity" "Arithmetic", "Outline_of_mathematics" "Arithmetic", "Primary_education" "Arithmetic", "Lists_of_mathematics_topics" "Arithmetic", "Nomograph" "Arithmetic", :start "Arithmetic", "Positional_notation" "Arithmetic", "Inverse_element" "Arithmetic", "Cyrillic_numerals" "Arithmetic", "Leonhard_Euler" "Arithmetic", "Arithmetic_function" "Arithmetic", "International_Standard_Book_Number" "Arithmetic", "Linear_algebra" "Arithmetic", "Codex_Vigilanus" "Arithmetic", "Probability_theory" "Arithmetic", "Aryabhata" "Arithmetic", "Algebraic_geometry" "Arithmetic", "Topology" "Arithmetic", "Leonard_Eugene_Dickson" "Arithmetic", "Pure_mathematics" "Arithmetic", "Addition_of_natural_numbers" "Arithmetic", "Ishango_bone" "Arithmetic", "Mathematical_physics" "Arithmetic", "List_of_important_publications_in_mathematics" "Arithmetic", "Discrete_mathematics" "Arithmetic", "Differential_equation" "Arithmetic", "Numeral_system" "Arithmetic", "Associative" "Arithmetic", "Field_mathematics" "Arithmetic", "Vigesimal" "Arithmetic", "Decimal" "Arithmetic", "Additive_identity" "Arithmetic", "History_of_the_Theory_of_Numbers" "Arithmetic", "Geometry" "Arithmetic", "Game_theory" "Arithmetic", "Renaissance" "Arithmetic", "Harold_Davenport" "Arithmetic", "Hellenistic_civilization" "Arithmetic", "Egyptian_mathematics" "Arithmetic", "Liberal_arts" "Arithmetic", "Summation" "Arithmetic", "Counting" "Arithmetic", "Babylonian_numerals" "Arithmetic", "Mental_calculation" "Arithmetic", "Mathematical_analysis" "Arithmetic", "Tally_marks" "Arithmetic", "Euclid" "Arithmetic", "Subtraction" "Arithmetic", "Greek_numerals" "Arithmetic", "Greek_Numerals" "Arithmetic", "MacTutor_History_of_Mathematics_archive" "Arithmetic", "Applied_mathematics" "Arithmetic", "The_Sand_Reckoner" "Arithmetic", "Fundamental_theorem_of_arithmetic" "Arithmetic", "Main_Page" "Arithmetic", "Radix_point" "Arithmetic", "Dynamical_systems_theory" "Arithmetic", "Syriac_Christianity" "Arithmetic", "Outline_of_arithmetic" "Arithmetic", "Elementary_algebra" "Arithmetic", "Differential_geometry" "Arithmetic", "Graph_theory" "Arithmetic", "Mathematics" "Arithmetic", "India" "Arithmetic", "Divisibility" "Arithmetic", "Greek_language" "Arithmetic", "Arithmetic_fraction" "Arithmetic", "Democratic_Republic_of_the_Congo" "Arithmetic", "Roman_numerals" "Arithmetic", "Greek_mathematics" "Arithmetic", "Pythagoreanism" "Arithmetic", "Babylonian_mathematics" "Arithmetic", "Discrete_geometry" "Arithmetic", "Pythagoras" "Arithmetic", "Maya_numerals" "Arithmetic", "Information_theory" "Arithmetic", "Egyptian_numerals" "Arithmetic", "Theory_of_computation" "Arithmetic", "Medieval" "Arithmetic", "Mathematics_in_medieval_Islam" "Arithmetic", "Mathematician" "Arithmetic", "Liber_Abaci" "Arithmetic", "Counting_board" "Arithmetic", "Arithmetic_mean" "Arithmetic", "Associativity" "Arithmetic", "Mathematical_statistics" "Arithmetic", "Percentage" "Arithmetic", "Louis_Charles_Karpinski" "Arithmetic", "Commutative" "Arithmetic", "Identity_element" "Arithmetic", "Number_theory" "Arithmetic", "Decimal_representation" "Arithmetic", "Exponentiation" "Arithmetic", "Lie_theory" "Arithmetic", "Addition" "Arithmetic", "Elementary_arithmetic" "Arithmetic", "Additive_inverse" "Arithmetic", "Combinatorics" "Arithmetic", "Distributivity" "Arithmetic", "Division_by_zero" "Arithmetic", "Integer" "Arithmetic", "Nicomachus" "Arithmetic", "Middle_Ages" "Arithmetic", "Natural_number" "Arithmetic", "Arithmetic_coding" "Arithmetic", "University_of_St_Andrews" "Arithmetic", "Finite_field_arithmetic" "Arithmetic"})