(ns SpotsNTris.core
    (:use quil.core)
    (:gen-class))

; Example:
; Axiom: F
; Rule: "F+F-F-F+F"

(defn setup []
    (smooth)
    (frame-rate 1)
    (background 200)
    (stroke 0)
    (stroke-weight (random 6)))

(defn forward [state d]
    (let [x (+ (get state "x") (* d (cos (get state "a")))),
            y (+ (get state "y") (* d (sin (get state "a")))),
            a (get state "a")]
        (line (get state "x") (get state "y") x y)
        {"x" x, "y" y, "a" a}))

    ;{"x" (+ (get state "x") (* d (cos (get state "a")))),
    ;"y" (+ (get state "y") (* d (sin (get state "a")))),
    ;"a" (get state "a")})

(defn right [state d]
    {"x" (get state "x"),
    "y" (get state "y"),
    "a" (+ (get state "a") (radians d))})

(defn left [state d]
    {"x" (get state "x"),
    "y" (get state "y"),
    "a" (- (get state "a") (radians d))})

(defn parse-cmd [s cmd]
    (if (.equals cmd "F") (forward s 20)
        (if (.equals cmd "+") (left s 90)
            (if (.equals cmd "-") (right s 90)))))

(defn parse [s cmds]
    (if (zero? (count cmds)) s
        (recur (parse-cmd s (str (first cmds))) (apply str (rest cmds)))))

(defn produce [axiom rules]
    (apply str
        (seq (for [i axiom]
            (get rules (str i) (str i))))))

(defn evolve [n axiom rules]
    (if (zero? n) axiom (recur (dec n) (produce axiom rules) rules)))

(defn draw []
    (background 200)
    (stroke-weight (random 8))
    (parse {"x" 0, "y" 299, "a" (radians 0)} (evolve 3 "F" {"F" "F+F-F-F+F"})))
    ;(parse {"x" 0, "y" 150, "a" (radians 0)} "F-FF+FF"))

(defsketch spots-n-tris
    :title "SpotsNTris"
    :setup setup
    :draw draw
    :size [600 300])

(defn -main []
    (println "Starting..."))
