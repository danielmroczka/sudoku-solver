package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;

/**
 * Created by Daniel Mroczka on 15-Mar-16.
 */
public class HiddenQuads extends HiddenSubSet {
    public HiddenQuads() {
        size = 4;
    }

    //TODO fix to support findHiddenPairsInBlock
    @Override
    public void execute(IMatrix matrix) {
        findHiddenPairsInRows(matrix);
        findHiddenPairsInCols(matrix);
    }
}
