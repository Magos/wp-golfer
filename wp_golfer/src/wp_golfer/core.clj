(ns wp-golfer.core)
(def *wiki* "http://en.wikipedia.org/wiki/")
(def *link-header* "/wiki/")
(def *link* (re-pattern (str *link-header* "[a-zA-Z_]*")))

(defn child-articles 
  "Retrieve the articles linked to by this article."
  [article]
	(let [url (str *wiki* article)
        html (slurp url) 
        links (re-seq *link* html)]
    (map (fn [it] (.replaceAll it *link-header* "")) links)
    )
  )

(defn expand [parents frontier parent children]
	(def ^:dynamic nParents parents)
	(def ^:dynamic nFrontier (drop 1 frontier))
  (doseq [article children]
    (if (contains? nParents article)
      ;;If this article already has a parent, do nothing.
      nil
      ;;If it doesn't, add it and its parent.
      (
       (def nParents (assoc nParents article parent))
       (def nFrontier (conj nFrontier article))
       )
      )
    )
  (vector nParents nFrontier)
  )

(defn search [frontier parents finish depth]
  ;;If map contains finish
  (if (or (< 50 depth) (contains? parents finish))
    ;;then return it.
    parents
    ;;else:
    (let [;;Pop first member off queue
          article (nth frontier 0)
          ;;Get child articles.
          children (child-articles article)
          ;;Add each to map and seq if not found in map already.
          [nParents nFrontier] (expand parents frontier article children)
          ;;Recur with smaller frontier, larger map.
          ]
      ;;nil
      (recur nFrontier nParents finish (+ 1 depth))
      )
    )
  )
        
(defn reconstruct-path [parents finish] 
  (pop (reverse (reconstruct-1 (vector finish) parents)))
  )

(defn reconstruct-1 [path parents]
  (if (some (fn [x] (= :start x)) path)
    path
    (let [node (peek path)
          parent (get parents node)
          nPath (conj path parent)]
      (recur nPath parents)
      )
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