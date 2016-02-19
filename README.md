#Sudoku solver
Library designed to solving sudoku problem.

[![Build Status](https://travis-ci.org/danielmroczka/sudoku-solver.png?branch=master)](https://travis-ci.org/danielmroczka/sudoku-solver)
[![Coverage Status](https://coveralls.io/repos/github/danielmroczka/sudoku-solver/badge.svg?branch=master)](https://coveralls.io/github/danielmroczka/sudoku-solver?branch=master)

##Features
Implemented algorithms:
- [x]  Lone Singles
- [x]  Open Singles
- [x]  Hidden Singles
- [x]  Hidden Pairs/Triples
- [x]  Naked Pairs/Triples
- [x]  Line/Block Reduction
- [x]  XWing

##Usage

Maven:

```xml
<dependency>
    <groupId>com.labs.dm</groupId>
    <artifactId>sudoku-solver</artifactId>
    <version>0.0.5-SNAPSHOT</version>
</dependency>
```
Command line:
```
java -jar sudoku-solver.jar <txt file with sudoku>

```


