/*
 * Copyright daniel.mroczka@gmail.com. All rights reserved. 
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;

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
        for (int col = 0; col < IMatrix.SIZE; col++) {
            for (int row = 0; row < 3; row++) {
//                ICell[] cells = matrix.getCellsInBox(row, col);
//
//                Set<Integer> values = new HashSet<Integer>();
//                ICell emptyCell = null;
//
//                for (ICell cell : cells) {
//                    if (cell.isSet()) {
//                        values.add(cell.getValue());
//                    } else {
//                        emptyCell = cell;
//                    }
//                }
//
//                if (values.size() == 8) {
//                    for (int i = 1; i < 10; i++) {
//                        if (!values.contains(i)) {
//                            emptyCell.setValue(i);
//                        }
//                    }
//                }
            }
        }
    }

}
