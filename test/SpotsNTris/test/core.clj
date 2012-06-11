(ns SpotsNTris.test.core
  (:use [SpotsNTris.core])
  (:use [clojure.test]))

;(deftest replace-me ;; FIXME: write
;  (is false "No tests have been written."))

(deftest produce-f
    (is true (.equals "F+F--F+F--F+F--F+F--F+F--F+F" (produce "F--F--F" {"F" "F+F--F+F"}))))

(deftest produce-koch
    (is true (.equals "F+F-F-F+F+F+F-F-F+F-F+F-F-F+F-F+F-F-F+F+F+F-F-F+F" (evolve 2 "F" {"F" "F+F-F-F+F"}))))


