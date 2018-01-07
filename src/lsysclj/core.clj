; L-system
; 2012 - 2018 Joel Carlbark

(ns lsysclj.core
    (:require [quil.core :as q]
              [quil.middleware :as m]))

(defn setup []
    (q/smooth)
    (q/frame-rate 1)
    (q/background 200)
    (q/stroke 50 75 120)
    (q/stroke-weight 1))

; Go forward s pixels: "F" (or "B"),
; returns the altered state
(defn cmd-forward [state]
    (let [ d (state "d" (q/radians 90)),
            s (state "s" 10),
            x (+ (state "x") (* s (q/cos (state "a")))),
            y (+ (state "y") (* s (q/sin (state "a")))),
            a (state "a")]
        (q/line (state "x") (state "y") x y)
        (-> (assoc state "x" x) (assoc "y" y))))

; Turn right d degrees: "-". Returns altered state
(defn cmd-right [state]
  (assoc state "a" (+ (state "a") (state "d"))))

; Turn left d degrees, "+". return the aleterd state
(defn cmd-left [state]
  (assoc state "a" (- (state "a") (state "d"))))

; Push the current state onto the stack
(defn cmd-push [state]
  (assoc state "stack" (conj (state "stack") state)))

; Take the last state from the stack
(defn cmd-pop [state]
  (if (empty? (state "stack"))
    state
    (last (state "stack"))))

; parse a single command/character
; in a certain state s
; Supported commands: F, B + and -
(defn parse-cmd [s cmd]
  (cond
    (= cmd "F") (cmd-forward s)
    (= cmd "B") (cmd-forward s)
    (= cmd "+") (cmd-left s)
    (= cmd "-") (cmd-right s)
    (= cmd "[") (cmd-push s)
    (= cmd "]") (cmd-pop s)
    :else s))

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

; Sierpinski triangle 1
(def sierpinski-triangle1 (evolve 4 "F" {"F" "B-F-B", "B" "F+B+F"}))

; Sierpinski 2
(def sierpinski-triangle2 (evolve 4 "F-B-B" {"F" "F-B+F+B-F", "B" "BB"}))

; Dragon curve
(def dragon-curve (evolve 10 "FX" {"X" "X+YF", "Y" "FX-Y"}))

; quadratic koch island
(def quadratic-koch-island (evolve 3 "F+F+F+F" {"F" "F+F-F-FF+F+F-F"}))

; Koch curve
(def koch-curve (evolve 3 "F" {"F" "F+F-F-F+F"}))

; Gosper curve
(def gosper-curve (evolve 3 "F" {"F" "F-B--B+F++FF+B-", "B" "+F-BB--B-F++F+B"}))

(def plant (evolve 6 "F" {"F" "F[+F][-F]"}))

(def advanced-plant (evolve 6 "X" {"X" "F[−X][X]F[−X]+FX", "F" "FF"}))

(def penrose (evolve 2 "[X]++[X]++[X]++[X]++[X]" {"X" "+YF--ZF[3-WF--XF]+", "W" "YF++ZF4-XF[-YF4-WF]++", "Y" "-WF++XF[+++YF++ZF]-", "Z" "--YF++++WF[+ZF++++XF]--XF"}))

(def pattern (evolve 7 "W" {"W" "+++X--F--ZFX+", "X" "---W++F++YFW-", "Y" "+ZFX--F--Z+++", "Z" "-YFW++F++Y---"}))

; https://es.wikipedia.org/wiki/Teselaci%C3%B3n_de_Penrose
(def penrose3 (evolve 3 "[N]++[N]++[N]++[N]++[N]" {"M" "OF++PF----NF[-OF----MF]++", "N" "+OF--PF[---MF--NF]+", "O" "-MF++NF[+++OF++PF]-", "P" "--OF++++MF[+PF++++NF]--NF"}))

(defn draw []
    (q/background 200)
    ;(stroke-weight (random 8))

    ; Sierpinski 2
    ; (parse {"x" 100, "y" 50, "a" 0.0, "s" 30, "d" (q/radians 120)} sierpinski-triangle2))

    ; Sierpinski triangle 1
    ;(parse {"x" 100, "y" 450, "a" 0.0, "s" 30, "d" (q/radians 60)} sierpinski-triangle1))

    ; Dragon curve
    ;(parse {"x" 350, "y" 350, "a" 0.0, "s" 8, "d" (q/radians 90)} dragon-curve))

    ; quad koch island
    ;(parse {"x" 125, "y" 400, "a" 0.0, "s" 5, "d" (q/radians 90)} quadratic-koch-island))

    ; Koch curve
    ;(parse {"x" 15, "y" 420, "a" 0.0, "s" 25, "d" (q/radians 90)} koch-curve))

    ; Test
    ;(parse {"x" 0, "y" 150, "a" 0.0, "s" 10, "d" (q/radians 90)} "F-FF+FF"))

    ; Gosper
    ; (parse {"x" 350 "y" 30, "a" 0.0 "s" 20 "d" (q/radians 60)} gosper-curve))

    ; Plant (stack test)
    ; (parse {"stack" [] "x" 350 "y" 450 "a" -1.6 "s" 50 "d" (q/radians 20)} plant))

    ; Advanced plant
    ; (parse {"stack" [] "x" -600 "y" 600 "a" 0.0 "s" 8 "d" (q/radians 25)} advanced-plant))

    ; Penrosy
    ;(parse {"stack" [] "x" 350 "y" 250 "a" 0.0 "s" 25 "d" (q/radians 36)} penrose))

    ; Pattern
    ;(parse {"stack" [] "x" 50 "y" 450 "a" 0.0 "s" 7 "d" (q/radians 30)} pattern))

    ; Penrose3
    (parse {"stack" [] "x" 350 "y" 250 "a" 0.0 "s" 30 "d" (q/radians 36)} penrose3))

(defn key-released []
  (q/save
    (str "lsysclj_"
         (q/year) "-"
         (q/month) "-"
         (q/day) "_"
         (q/hour) "_"
         (q/minute) "_"
         (q/seconds) ".png")))

(q/defsketch lsysclj
    :title "Clojure L-system"
    :setup setup
    :draw draw
    :size [700 500]
    :features [:keep-on-top]
    :mouse-clicked key-released)
