(ns wp-golfer.gui-test
  (:use clojure.test
        wp-golfer.gui))

(deftest crash-test
  (is true (not (nil? (gui))))
  )