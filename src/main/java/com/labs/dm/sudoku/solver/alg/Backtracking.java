package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;

/**
 * Created by daniel on 2016-03-10.
 */
public class Backtracking implements IAlgorithm {

    @Override
    public void execute(IMatrix matrix) {
        if (matrix.isSolved()) {
            return;
        }


    }
}
