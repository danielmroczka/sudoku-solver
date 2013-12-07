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
        findInRows(matrix);
        findInCols(matrix);
        findInBlock(matrix);
    }

    private void findInBlock(IMatrix matrix) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void findInCols(IMatrix matrix) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void findInRows(IMatrix matrix) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
