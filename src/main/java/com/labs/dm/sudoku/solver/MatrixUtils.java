package com.labs.dm.sudoku.solver;

import com.labs.dm.sudoku.solver.core.IMatrix;

import java.util.HashSet;

/**
 * Created by daniel on 2016-02-11.
 */
public class MatrixUtils {

    public static void initCandiates(IMatrix matrix) {
        for (int row = 0; row < matrix.SIZE; row++) {
            for (int col = 0; col < matrix.SIZE; col++) {
                matrix.setPossibleValues(row, col, new HashSet<Integer>());
            }
        }
    }
}
