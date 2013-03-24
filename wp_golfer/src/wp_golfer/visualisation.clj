(ns wp-golfer.visualisation (:use [quil.core :exclude [random]])
  (:require wp-golfer.core))

(defn setup []
  (smooth)                          ;;Turn on anti-aliasing
  (frame-rate 1)                    ;;Set framerate to 1 FPS
  (background 0)
  (text-align :center :top)
  ;(draw)
  )                 ;;Set the background colour to
                                    ;;  a nice shade of grey.
(defn draw []
  (background 0)
  (stroke 240)       
  (stroke-weight 3)
  (fill 255)
  (let[
     sequence (tree-seq #(:children %) #(:children %) *parents*)
       ]
    (doseq [par sequence]
      (drawnode par)
      (doseq [child (:children par)]
        (line (* (:x par) (width)) (* (:y par) (height)) (* (:x child) (width)) (* (:y child) (height))
              )
        )
      )
    )
  )

(defn drawnode[{:keys [x y name]}]
  (let[x (* x (width))
       y (* y (height))
       ]
    (ellipse  x y 5 5)
    (stroke 50)
    (text (str name) x y)
    (stroke 240)
    )
  )


;(comment 
  (defsketch visualization            ;;Define a new sketch named visualization
    :title "Drawing test" 		 				;;Set the title of the sketch
    :setup setup                     	;;Specify the setup fn
    :draw draw         		            ;;Specify the draw fn
    :size [400 520])   
  ;)


(defrecord Node [ x  y name children])


(+)
(def example {:path ["Pathfinding" "Bidirectional_search" "A*"], 
              :forward {"Digital_object_identifier" "Pathfinding", "Bidirectional_search" "Pathfinding", "Pathfinding" {:parent :start, :children ["Graph_traversal" "Tree_traversal" "Beam_search" "Bidirectional_search" "Branch_and_bound" "British_Museum_algorithm" "Hill_climbing" "Dynamic_programming" "Graph_traversal" "Tree_traversal" "Search_game" "Shortest_path_problem" "Dynamic_programming" "Contraction_hierarchies" "Video_game" "Game_artificial_intelligence" "Navigation_mesh" "Digital_object_identifier" "Main_Page" "Main_Page"]}, "Search_game" "Pathfinding", "Contraction_hierarchies" "Pathfinding", "Hill_climbing" "Pathfinding", "Graph_traversal" "Pathfinding", "Main_Page" "Pathfinding", "Video_game" "Pathfinding", "Game_artificial_intelligence" "Pathfinding", "Beam_search" "Pathfinding", "Navigation_mesh" "Pathfinding", "Dynamic_programming" "Pathfinding", "Branch_and_bound" "Pathfinding", "Shortest_path_problem" "Pathfinding", "Tree_traversal" "Pathfinding", "British_Museum_algorithm" "Pathfinding"},
              :backward {"Incremental_heuristic_search" "A*", "Bidirectional_search" "A*", "Artificial_intelligence" "A*", "Pathfinding" "A*", "A*" {:parent :finish, :children ["Artificial_intelligence" "List_of_algorithms" "Priority_queue" "Monotonic_function" "Heuristic" "Pathfinding" "Bidirectional_search" "PROGOL" "DRAKON" "Fringe_search" "Incremental_heuristic_search"]}, "Heuristic" "A*", "List_of_algorithms" "A*", "Monotonic_function" "A*", "Priority_queue" "A*", "DRAKON" "A*", "PROGOL" "A*", "Fringe_search" "A*"}
              }
  )
(def ^:dynamic *parents* (node-ize (:backward example) "A*"))



(defn node-ize [parent-map root-node]
 (let[wdth (rand )
      hght (rand )
      children (:children (get parent-map root-node))
      child-nodes (map node-ize (repeat parent-map) children)
      ]
   (->Node wdth hght root-node child-nodes)
   )
  )
