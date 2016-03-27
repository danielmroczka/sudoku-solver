/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.alg;

/**
 * Hidden Pairs algorithm implementation
 * <p>
 * http://www.learn-sudoku.com/hidden-pairs.html
 *
 * @author Daniel Mroczka
 */
public class HiddenPairs extends HiddenSubset {

    public HiddenPairs() {
        subsetSize = 2;
    }

}
