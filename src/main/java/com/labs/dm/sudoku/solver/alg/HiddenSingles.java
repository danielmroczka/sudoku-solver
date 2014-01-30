/*
 * Copyright daniel.mroczka@gmail.com. All rights reserved. 
 */

package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;

/**
 * Hidden Singles algorithm implementation
 * 
 * @see http://www.learn-sudoku.com/hidden-singles.html
 * 
 * @author daniel
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
            //int[] cols = matrix.getElemsInCol(col);
        }
    }

    private void findInRows(IMatrix matrix) {
    }

    private void init(IMatrix matrix) {
        //if (matrix.getPossibleValues() == null) {
            
       // }
    }

}
