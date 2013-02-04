(ns wp-golfer.retrieval)

(def ^:dynamic *wiki* "http://en.wikipedia.org/wiki/")
(def link-header "href=\"/wiki/")
(def link (re-pattern (str link-header "[a-zA-Z_]*\"")))

(defn trim [it] (subs it (.length link-header) (dec (.length it))))

(defn child-articles 
  "Retrieve the articles linked to by this article."
  [article]
	(let [url (str *wiki* article)
        html (slurp url) 
        links (re-seq link html)
        ]
    (map trim links)
    )
  )

(def back-link-page "Special:WhatLinksHere/")

(defn parent-articles [article]
  "Retrieve the articles which link to this article."
  (let[url (str *wiki* back-link-page article)
       html (slurp url)
       links (re-seq link html)
       ]
    (map trim links)
    )
  )