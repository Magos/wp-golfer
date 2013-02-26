(ns wp-golfer.core (:require [wp-golfer.retrieval :as re]))

(defn expand
  "Given a frontier and parent-map,
  expands them with the results of (related-articles (first frontier))
  while respecting existing relationships."
  [frontier parent-map related-articles]
  (let[article (first frontier)
       not-existing? (comp not (partial contains? parent-map))
       related (filter not-existing? (related-articles article))
       nfrontier (drop 1 (into frontier related))
       nparent-map (into parent-map (zipmap related (repeat article)))
       ]
    {:frontier nfrontier :parents nparent-map}
    )
  )

(defn search 
  "Perform bi-directional search."
  [f-frontier b-frontier f-parents b-parents]
  (if (some (partial contains? f-parents) (keys b-parents))
    {:forward f-parents :backward b-parents}
    (let [forward (expand f-frontier f-parents re/child-articles)
          backward (expand b-frontier b-parents re/parent-articles)
          ]
      (recur (:frontier forward) (:frontier backward) (:parents forward) (:parents backward))
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
  (let [;;Perform search.
        result (search [start] [finish] {start :start} {finish :finish})]
  ;;Reconstruct path using resulting parent-map and return.
     (reconstruct-path result)
    )
  )
