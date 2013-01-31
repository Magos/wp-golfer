(ns wp-golfer.core)
(def ^:dynamic *wiki* "http://en.wikipedia.org/wiki/")
(def link-header "href=\"/wiki/")
(def link (re-pattern (str link-header "[a-zA-Z_]*")))

(defn child-articles 
  "Retrieve the articles linked to by this article."
  [article]
	(let [url (str *wiki* article)
        html (slurp url) 
        links (re-seq link html)]
    (map (fn [it] (.replaceAll it link-header "")) links)
    )
  )

(defn search [frontier parents finish depth]
  ;;If map contains finish
  (if (or (< 50 depth) (contains? parents finish))
    ;;then return it.
    parents
    ;;else:
    (let [;;Pop first member off queue
          article (nth frontier 0)
          ;;Get new child articles.
          not-in-parents (fn [x] (not (contains? parents x)))
          children (filter not-in-parents (child-articles article))
          ;;Add each to map and seq if not found in map already.
          nFrontier (drop 1 (into frontier children))
          nParents (conj parents (zipmap children (repeat article)))
          ;;Recur with smaller frontier, larger parent map.
          ]
      ;;nil
      (recur nFrontier nParents finish (inc depth))
      )
    )
  )
           

(defn reconstruct-path [parents finish]
  (let [r1 (fn [path parents]
             (if (some (fn [x] (= :start x)) path)
               path
               (let [node (peek path)
                     parent (get parents node)
                     nPath (conj path parent)]
                 (recur nPath parents)
                 )
               ))]
    (pop (reverse (r1 (vector finish) parents)))
    )
  )


        
(defn golf [start finish] 
  ;;Naive forward breadth-first, to start with.
  ;;Create frontier (things to search) as vector.
  ;;Create map of parents.
  ;;Populate.
  (let [frontier (vector start)
        parents (hash-map start :start)
        ;;Perform search.
        result (search frontier parents finish 0)]
  ;;Reconstruct path using resulting parent-map and return.
    (reconstruct-path result finish)
    )
  )