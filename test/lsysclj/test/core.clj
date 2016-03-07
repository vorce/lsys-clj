(ns lsysclj.test.core
  (:use [lsysclj.core])
  (:use [clojure.test]))

; TODO more test cases

(deftest produce-f
    (is true (.equals "F+F--F+F--F+F--F+F--F+F--F+F" (produce "F--F--F" {"F" "F+F--F+F"}))))

(deftest produce-koch
    (is true (.equals "F+F-F-F+F+F+F-F-F+F-F+F-F-F+F-F+F-F-F+F+F+F-F-F+F" (evolve 2 "F" {"F" "F+F-F-F+F"}))))


