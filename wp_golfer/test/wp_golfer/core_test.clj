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
