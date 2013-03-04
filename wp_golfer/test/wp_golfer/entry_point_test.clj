(ns wp-golfer.entry-point-test
  (:use clojure.test
        wp-golfer.entry-point
        ))

(deftest single-search
  (testing "Perform a single search from command line."
    (is ["Philosophy" "Existence" "Truth"] (with-out-str (-main "Philosophy" "Truth")))
    (is ["Theory_of_forms" "Plato"] (with-out-str (-main "Theory of forms" "Plato"))) ;;Check that spaces are handled.
    )
  )

(deftest gui
  (testing "Opening the GUI."
    (-main)
    )
  )