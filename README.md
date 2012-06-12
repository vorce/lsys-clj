lsys-clj
========
Basic l-system as a first clojure contact. Disregard.

## Usage

Try these start states, axioms and rules:


Koch curve:

Start state: {"x" 0 "y" 499 "a" 0.0}

Axiom: "F"

Rules: {"F" "F+F-F-F+F"}

(parse {"x" 0, "y" 299, "a" 0.0} (evolve 3 "F" {"F" "F+F-F-F+F"})))


Quadratic koch island:

Start state: {"x" 0 "y" 499 "a" 0.0}

Axiom: "F+F+F+F"

Rules: {"F" "F+F-F-FF+F+F-F"}

(parse {"x" 0, "y" 499, "a" 0.0} (evolve 3 "F+F+F+F" {"F" "F+F-F-FF+F+F-    F"})))

## License

Copyright (C) 2012 Joel Carlbark

Distributed under the Eclipse Public License, the same as Clojure.
