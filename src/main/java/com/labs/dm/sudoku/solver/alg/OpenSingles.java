/*
 * Copyright daniel.mroczka@gmail.com. All rights reserved. 
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import java.util.HashSet;
import java.util.Set;

/**
 * Open Singles Algorithm.
 *
 * @see http://www.learn-sudoku.com/open-singles.html
 *
 * @author daniel
 */
public class OpenSingles implements IAlgorithm {

    @Override
    public void execute(IMatrix matrix) {
        fillOpenSinglesInRows(matrix);
        fillOpenSinglesInCols(matrix);
        fillOpenSinglesInBoxes(matrix);
    }

    //TODO
    private boolean fillOpenSinglesInRows(IMatrix matrix) {
        for (int row = 0; row < IMatrix.SIZE; row++) {
            Set<Integer> values = new HashSet<>();
            int[] rows = matrix.getElemsInRow(row);
            int counter = 0;
            int firstIndex = -1;
            for (int index = 0; index < rows.length; index++) {
                if (counter > 0) {
                    return false;
                }
                if (rows[index] == IMatrix.EMPTY_VALUE) {
                    counter++;
                    firstIndex = index;
                }
            }

        }

        return false;

    }

    private boolean fillOpenSinglesInCols(IMatrix matrix) {
        return false;
    }

    private boolean fillOpenSinglesInBoxes(IMatrix matrix) {
        return false;
    }

}
