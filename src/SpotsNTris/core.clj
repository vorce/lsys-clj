; L-system
; 2012, Joel Carlbark

(ns SpotsNTris.core
    (:use quil.core)
    (:gen-class))

(defn setup []
    (smooth)
    (frame-rate 1)
    (background 200)
    (stroke 50 75 120)
    (stroke-weight 2))

; Go forward s pixels: "F" (or "B"),
; returns the altered state
(defn forward [state]
    (let [ d (get state "d" (radians 90)),
            s (get state "s" 10),
            x (+ (get state "x") (* s (cos (get state "a")))),
            y (+ (get state "y") (* s (sin (get state "a")))),
            a (get state "a")]
        (line (get state "x") (get state "y") x y)
        {"x" x, "y" y, "a" a, "d" d, "s" s}))

; Turn right d degrees: "-". Returns altered state
(defn right [state]
    {"x" (get state "x"),
    "y" (get state "y"),
    "a" (+ (get state "a") (get state "d")),
    "d" (get state "d" (radians 90)),
    "s" (get state "s" 10)})

; Turn left d degrees, "+". return the aleterd state
(defn left [state]
    {"x" (get state "x"),
    "y" (get state "y"),
    "a" (- (get state "a") (get state "d")),
    "d" (get state "d" (radians 90)),
    "s" (get state "s" 10)})

; parse a single command/character
; in a certain state s
; Supported commands: F, B + and -
(defn parse-cmd [s cmd]
    (if (.equals cmd "F") (forward s)
        (if (.equals cmd "+") (left s)
            (if (.equals cmd "-") (right s)
                (if (.equals cmd "B") (forward s)
                    s)))))

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
    ;(stroke-weight (random 8))

    ; Sierpinski 2
    (parse {"x" 100, "y" 50, "a" 0.0, "s" 30, "d" (radians 120)} (evolve 4
        "F-B-B" {"F" "F-B+F+B-F", "B" "BB"})))

    ; Sierpinski triangle 1
    ;(parse {"x" 100, "y" 450, "a" 0.0, "s" 30, "d" (radians 60)} (evolve 4 "F" {"F" "B-F-B", "B" "F+B+F"})))

    ; Dragon curve
    ;(parse {"x" 350, "y" 350, "a" 0.0, "s" 8, "d" (radians 90)} (evolve 10 "FX" {"X" "X+YF", "Y" "FX-Y"})))

    ; quad koch island
    ;(parse {"x" 125, "y" 400, "a" 0.0, "s" 5, "d" (radians 90)} (evolve 3 "F+F+F+F" {"F" "F+F-F-FF+F+F-F"})))

    ; Koch curve
    ;(parse {"x" 80, "y" 480, "a" 0.0, "s" 20, "d" (radians 90)} (evolve 3 "F" {"F" "F+F-F-F+F"})))

    ; Test
    ;(parse {"x" 0, "y" 150, "a" 0.0, "s" 10, "d" (radians 90)} "F-FF+FF"))

(defsketch spots-n-tris
    :title "Clojure L-system"
    :setup setup
    :draw draw
    :size [700 500])

(defn -main []
    (println "Starting..."))
