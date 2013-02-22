(ns wp-golfer.core (:require [wp-golfer.retrieval :as re]))
(def ^:dynamic *depth-limit* 200)

(defn search 
  "Perform bi-directional search."
  [f-frontier b-frontier f-parents b-parents]
  (if (some (partial contains? f-parents) (keys b-parents))
    {:forward f-parents :backward b-parents}
    (let [fwd-article (first f-frontier)
          bck-article (first b-frontier)
          not-in-f (comp not (partial contains? f-parents))
          not-in-b (comp not (partial contains? b-parents))
          fwd-children (filter not-in-f (re/child-articles fwd-article))
          bck-parents (filter not-in-b (re/parent-articles bck-article))
          nf-parents (into f-parents (zipmap fwd-children (repeat fwd-article)))
          nb-parents (into b-parents (zipmap bck-parents (repeat bck-article)))
          nf-frontier (drop 1 (into f-frontier fwd-children))
          nb-frontier (drop 1 (into b-frontier bck-parents))
          ]
      (recur nf-frontier nb-frontier nf-parents nb-parents)
      )
    )
  )
  
  (defn reconstruct-path [{:keys [forward backward]}]
    (let [overlap (some (set (keys forward)) (keys backward))
          forward-path (take-while #(not= :start %1) (iterate #(get forward %1) overlap))
          backward-path (take-while #(not= :finish %1) (iterate #(get backward %1) overlap))
          ]
      (concat (reverse (drop 1 forward-path)) backward-path)
      )
    )
  
  
  
(defn golf [start finish] 
  ;;Bi-directional breadth-first search.
  ;;Create frontier (things to search) as vector.
  ;;Create map of parents.
  ;;Populate.
  (let [ffrontier [start]
        bfrontier [finish]
        fparents {start :start}
        bparents {finish :finish}
        ;;Perform search.
        result (search ffrontier bfrontier fparents bparents)]
  ;;Reconstruct path using resulting parent-map and return.
    (if (= :depth-limit-exceeded result)
      (str result)
     (reconstruct-path result)
     )
    )
  )