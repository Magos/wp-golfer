(ns wp-golfer.core-test
  (:use clojure.test
        wp-golfer.core
        wp-golfer.retrieval)
  (:require clojure.set))

(deftest offline-test
  (testing "Offline (communication mocked-out) search and path reconstruction"
    (let [resultmap (hash-map "Philosophy" (vector "Plato" "Confucius" "Forms")
          "Plato" (vector "Aristotle" "Socrates" "Natural_philosophy")
          "Functional_programming" (vector "Haskell" "Lisp")
          "Lisp" (vector "Common_Lisp" "Scheme" "Clojure")
          )
          parentmap (clojure.set/map-invert resultmap)]
    (with-redefs [child-articles (fn [a] (get resultmap a))
                  parent-articles (fn [a] (get parentmap a))
                  ]
      (are [x y] (= x y)
           (golf "Philosophy" "Philosophy") (vector "Philosophy")
           (golf "Philosophy" "Plato") (vector "Philosophy" "Plato" )
           (golf "Philosophy" "Aristotle") (vector "Philosophy" "Plato" "Aristotle")
           (golf "Functional_programming" "Functional_programming") (vector "Functional_programming")
           (golf "Functional_programming" "Lisp") (vector "Functional_programming" "Lisp")
           (golf "Functional_programming" "Clojure") (vector "Functional_programming" "Lisp" "Clojure")
           )      )
     )
    )
  )

(deftest league-wiki
  (testing "A small (<2000 article) wiki limits the branching factor."
    (with-redefs [*wiki* "http://leagueoflegends.wikia.com/wiki/"]
      (let [search (golf "Caitlyn" "Minion")]
        (is search (vector "Caitlyn" "League_of_Legends_Wiki" "List_of_champions" "Base_champion_statistics" "Movement_Speed" "Blade_of_the_Ruined_King" "Minion"))
        )
      )
   )
  )