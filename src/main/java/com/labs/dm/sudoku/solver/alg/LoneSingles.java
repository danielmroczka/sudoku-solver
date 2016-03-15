/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;

import static com.labs.dm.sudoku.solver.core.IMatrix.SIZE;

/**
 * Lone Singles (Naked Singles) Algorithm Implementation.
 * <p>
 * Finds cells with only one candidate and updates the cell value with candidate value.
 *
 * @author Daniel Mroczka
 *         http://www.learn-sudoku.com/lone-singles.html
 */
public class LoneSingles implements IAlgorithm {

    @Override
    public void execute(IMatrix matrix) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (!matrix.isCellSet(row, col) && matrix.getCandidates(row, col).size() == 1) {
                    int val = matrix.getCandidates(row, col).get(0);
                    matrix.setValueAt(row, col, val);
                }
            }
        }
    }

}
