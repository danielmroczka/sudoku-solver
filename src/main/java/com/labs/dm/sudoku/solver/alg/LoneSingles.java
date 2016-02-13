/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;

import static com.labs.dm.sudoku.solver.core.IMatrix.SIZE;

/**
 * Lone Singles Algorithm Implementation
 *
 * @author daniel
 * http://www.learn-sudoku.com/lone-singles.html
 */
public class LoneSingles implements IAlgorithm {

    @Override
    public void execute(IMatrix matrix) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (!matrix.isCellSet(row, col) && matrix.getPossibleValues(row, col).size() == 1) {
                    Integer[] p = matrix.getPossibleValues(row, col).toArray(new Integer[0]);
                    int value = p[0];
                    matrix.setCellValue(row, col, value);
                }
            }
        }
    }

}
