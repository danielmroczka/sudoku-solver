/*
 * Copyright daniel.mroczka@gmail.com. All rights reserved. 
 */

package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;

/**
 * Hidden Singles algorithm implementation
 *
 * @author daniel
 * @see http://www.learn-sudoku.com/hidden-singles.html
 */
public class HiddenSingles implements IAlgorithm {

    @Override
    public void execute(IMatrix matrix) {

        init(matrix);
        findInRows(matrix);
        findInCols(matrix);
        findInBlock(matrix);
    }

    private void findInBlock(IMatrix matrix) {

    }

    private void findInCols(IMatrix matrix) {
        for (int col = 0; col < IMatrix.SIZE; col++) {
            //matrix.getPossibleValues(row)
            //int[] cols = matrix.getElemsInCol(col);
        }
    }

    private void findInRows(IMatrix matrix) {
        for (int row = 0; row < IMatrix.SIZE; row++) {
            for (int col = 0; col < IMatrix.SIZE; col++) {
                matrix.getPossibleValues(row, col);
            }
        }
    }

    private void init(IMatrix matrix) {
        //if (matrix.getPossibleValues() == null) {

        // }
    }

}
