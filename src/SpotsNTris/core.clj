; L-system
; 2012, Joel Carlbark

(ns SpotsNTris.core
    (:use quil.core)
    (:gen-class))

(defn setup []
    (smooth)
    (frame-rate 1)
    (background 200)
    (stroke 50 75 120))

; Go forward d pixels: "F",
; returns the altered state
(defn forward [state d]
    (let [x (+ (get state "x") (* d (cos (get state "a")))),
            y (+ (get state "y") (* d (sin (get state "a")))),
            a (get state "a")]
        (line (get state "x") (get state "y") x y)
        {"x" x, "y" y, "a" a}))

    ;{"x" (+ (get state "x") (* d (cos (get state "a")))),
    ;"y" (+ (get state "y") (* d (sin (get state "a")))),
    ;"a" (get state "a")})

; Turn right d degrees: "-". Returns altered state
(defn right [state d]
    {"x" (get state "x"),
    "y" (get state "y"),
    "a" (+ (get state "a") (radians d))})

; Turn left d degrees, "+". return the aleterd state
(defn left [state d]
    {"x" (get state "x"),
    "y" (get state "y"),
    "a" (- (get state "a") (radians d))})

; parse a single command/character
; in a certain state s
; Supported commands: F, + and -
(defn parse-cmd [s cmd]
    (if (.equals cmd "F") (forward s 20)
        (if (.equals cmd "+") (left s 90)
            (if (.equals cmd "-") (right s 90)))))

; Parse the whole string of commands
; in the state s
(defn parse [s cmds]
    (if (zero? (count cmds)) s
        (recur (parse-cmd s (str (first cmds))) (apply str (rest cmds)))))

; Produce a new command string from the axiom string
; and the rules applied to it
(defn produce [axiom rules]
    (apply str
        (seq (for [i axiom]
            (get rules (str i) (str i))))))

; Recursively apply the rules to the result of a produced
; command string
(defn evolve [n axiom rules]
    (if (zero? n) axiom (recur (dec n) (produce axiom rules) rules)))

(defn draw []
    (background 200)
    (stroke-weight (random 8))
    (parse {"x" 0, "y" 299, "a" (radians 0)} (evolve 3 "F" {"F" "F+F-F-F+F"})))
    ;(parse {"x" 0, "y" 150, "a" (radians 0)} "F-FF+FF"))

(defsketch spots-n-tris
    :title "Clojure L-system"
    :setup setup
    :draw draw
    :size [600 300])

(defn -main []
    (println "Starting..."))
