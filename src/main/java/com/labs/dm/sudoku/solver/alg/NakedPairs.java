package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;

import java.util.Map;

/**
 * Naked Pairs Algorithm Implementation
 * http://www.learn-sudoku.com/naked-pairs.html
 * <p>
 * Created by daniel on 2016-02-11.
 */
public class NakedPairs implements IAlgorithm {
    @Override
    public void execute(IMatrix matrix) {
        detectNakedPairs(matrix);
    }

    private void detectNakedPairs(IMatrix matrix) {
        detectNakedPairsInCols(matrix);
        detectNakedPairsInRows(matrix);
        detectNakedPairsInBlock(matrix);
    }

    private void detectNakedPairsInBlock(IMatrix matrix) {

    }

    private void detectNakedPairsInRows(IMatrix matrix) {

        for (int row = 0; row < matrix.SIZE; row++) {
            Map map;
            for (int col = 0; col < matrix.SIZE; col++) {
                if (matrix.getPossibleValues(row, col).size() == 2) {

                }
            }
        }
    }

    private void detectNakedPairsInCols(IMatrix matrix) {
    }
}
