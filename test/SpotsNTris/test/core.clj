(ns SpotsNTris.test.core
  (:use [SpotsNTris.core])
  (:use [clojure.test]))

;(deftest replace-me ;; FIXME: write
;  (is false "No tests have been written."))

(deftest produce-f
    (= "F+F--F+F--F+F--F+F--F+F--F+F" (produce "F--F--F" {"F" "F+F--F+F"})))

