(ns SpotsNTris.core
    (:use quil.core)
    (:gen-class))

(defn setup []
    (smooth)
    (frame-rate 1)
    (background 200))

(defn draw []
    (stroke (random 255)))

(defn produce [axiom rules]
    (apply str
        (seq (for [i axiom]
            (get rules (str i) (str i))))))

(defn evolve [n axiom rules]
;    {:pre [(>= n 0)]}
    (if (zero? n) axiom (recur (dec n) (produce axiom rules) rules)))
;    (evolve (- n 1) (produce axiom rules) rules))

;(defsketch spots-n-tris
;    :title "SpotsNTris"
;    :setup setup
;    :draw draw
;    :size [600 300])

(defn -main []
    (println "Starting..."))
