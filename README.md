# Sudoku solver
Library designed to solving Sudoku problem written in Java.

[![Build Status](https://travis-ci.org/danielmroczka/sudoku-solver.png?branch=master)](https://travis-ci.org/danielmroczka/sudoku-solver)
[![Build Status](https://circleci.com/gh/danielmroczka/sudoku-solver.png?circle-token=3f8fd98345eb1b26fb8c83749fcefd6fdffb6f94)](https://circleci.com/gh/danielmroczka/sudoku-solver)
[![Coverage Status](https://coveralls.io/repos/github/danielmroczka/sudoku-solver/badge.svg?branch=master)](https://coveralls.io/github/danielmroczka/sudoku-solver?branch=master)

## Features
Implemented algorithms:
- [x]  Lone Singles
- [x]  Open Singles
- [x]  Hidden Singles
- [x]  Hidden Pairs/Triples
- [x]  Naked Subset (Pairs/Triples/Quads)
- [x]  Locked Candidates (Pointing Pairs/Claiming)
- [x]  X-Wing
- [x]  XY-Wing
- [x]  XYZ-Wing
- [x]  Sword-Fish
- [x]  Jelly-Fish
- [x]  Forcing Chains

                           
## Usage

Maven:

```xml
<dependency>
    <groupId>com.labs.dm</groupId>
    <artifactId>sudoku-solver</artifactId>
    <version>0.0.9-SNAPSHOT</version>
</dependency>
```
Command line:

```
java -jar sudoku-solver.jar <txt file with sudoku>
```

JavaDoc: [javadoc](http://danielmroczka.github.io/sudoku-solver/api/)


