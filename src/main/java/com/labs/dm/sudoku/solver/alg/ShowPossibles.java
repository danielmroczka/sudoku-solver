/*
 * Copyright daniel.mroczka@gmail.com. All rights reserved. 
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import static com.labs.dm.sudoku.solver.core.IMatrix.EMPTY_VALUE;
import static com.labs.dm.sudoku.solver.core.IMatrix.SIZE;

/**
 * @author daniel
 */
public class ShowPossibles implements IAlgorithm {

    @Override
    public void execute(IMatrix matrix) {

    }

    private void init(IMatrix matrix) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (matrix.getCellValue(row, col) != EMPTY_VALUE) {
                }
            }
        }
    }

}
