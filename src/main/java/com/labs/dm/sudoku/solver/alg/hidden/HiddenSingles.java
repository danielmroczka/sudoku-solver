/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.alg.hidden;

/**
 * Hidden Singles algorithm implementation
 *
 * @author Daniel Mroczka
 *         <p>
 *         http://www.learn-sudoku.com/hidden-singles.html
 */
public class HiddenSingles extends HiddenSubset {
    public HiddenSingles() {
        subsetSize = 1;
        minSize = 1;
    }
}