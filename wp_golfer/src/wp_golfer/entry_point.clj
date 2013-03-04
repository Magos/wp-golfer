(ns wp-golfer.entry-point
  (:use wp-golfer.core wp-golfer.gui)
  (:gen-class)
  )

(defn -main 
  "Entry-point. Calls with exactly 2 args interpreted as a search.
  Any other calls open the GUI."
  [& args]
  (if (= 2 (count args))
    (println (apply golf args))
    (wp-golfer.gui/gui)
   )
  )