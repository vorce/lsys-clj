# lsys-clj

Basic l-system as a first clojure contact. Disregard.

## Pre-requisites

- Java 8. [Quil](https://github.com/quil/quil/issues/228) (and Processing?) does not fully support Java 9 yet.

## Usage

`lein run`

The core.clj file contains a bunch of different examples in the draw method.
Sierpinksi triangle, dragon curve, quadratic koch island and a koch curve.

See: https://en.wikipedia.org/wiki/L-system

The parse method takes a start state and a command string. The command string
can be obtained by running the evolve method.

### Tests

`lein test`

## License

Copyright (C) 2012 - 2018 Joel Carlbark

Distributed under the Eclipse Public License, the same as Clojure.
