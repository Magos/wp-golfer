(ns wp-golfer.core-test
  (:use clojure.test
        wp-golfer.core))


(deftest offline-test
  (testing "Offline (communication mocked-out) search and path reconstruction"
    (with-redefs [child-articles (fn [a] 
                                   (cond (= a "Philosophy") (vector "Plato" "Confucius" "Forms")
                                         (= a "Plato") (vector "Aristotle" "Socrates" "Natural_philosophy")
                                         (= a "Functional_programming") (vector "Haskell" "Lisp")
                                         (= a "Lisp") (vector "Common_Lisp" "Scheme" "Clojure")
                                         true nil
                                         )
                                   )
                  ]
      (is (golf "Philosophy" "Philosophy") (vector "Philosophy"))
      (is (golf "Philosophy" "Plato") (vector "Philosophy" "Plato" ))
      (is (golf "Philosophy" "Aristotle") (vector "Philosophy" "Plato" "Aristotle"))
      (is (golf "Functional_programming" "Functional_programming") (vector "Functional_programming"))
      (is (golf "Functional_programming" "Lisp") (vector "Functional_programming" "Lisp"))
      (is (golf "Functional_programming" "Clojure") (vector "Functional_programming" "Lisp" "Clojure"))
      )
    )
  )
