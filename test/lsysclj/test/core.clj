(ns lsysclj.test.core
  (:use [lsysclj.core])
  (:use [clojure.test]))

(deftest produce-f
    (is true (.equals "F+F--F+F--F+F--F+F--F+F--F+F" (produce "F--F--F" {"F" "F+F--F+F"}))))

(deftest produce-koch
    (is true (.equals "F+F-F-F+F+F+F-F-F+F-F+F-F-F+F-F+F-F-F+F+F+F-F-F+F" (evolve 2 "F" {"F" "F+F-F-F+F"}))))

(deftest push-to-stack
  (let [state {"stack" [] "x" 1}]
    (is true (.equals {"stack" [state]} (cmd-push state)))))

(deftest pop-from-stack
  (let [state {"stack" [] "x" 1}]
    (is true (.equals state (cmd-pop (cmd-push state))))))
