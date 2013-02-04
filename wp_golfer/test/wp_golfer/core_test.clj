(ns wp-golfer.core-test
  (:use clojure.test
        wp-golfer.core
        wp-golfer.retrieval))


(deftest offline-test
  (testing "Offline (communication mocked-out) search and path reconstruction"
    (let [resultmap (hash-map "Philosophy" (vector "Plato" "Confucius" "Forms")
          "Plato" (vector "Aristotle" "Socrates" "Natural_philosophy")
          "Functional_programming" (vector "Haskell" "Lisp")
          "Lisp" (vector "Common_Lisp" "Scheme" "Clojure")
          )]
    (with-redefs [child-articles (fn [a] (get resultmap a))]
      (is (golf "Philosophy" "Philosophy") (vector "Philosophy"))
      (is (golf "Philosophy" "Plato") (vector "Philosophy" "Plato" ))
      (is (golf "Philosophy" "Aristotle") (vector "Philosophy" "Plato" "Aristotle"))
      (is (golf "Functional_programming" "Functional_programming") (vector "Functional_programming"))
      (is (golf "Functional_programming" "Lisp") (vector "Functional_programming" "Lisp"))
      (is (golf "Functional_programming" "Clojure") (vector "Functional_programming" "Lisp" "Clojure"))
      )
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