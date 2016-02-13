/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Hidden Singles algorithm implementation
 *
 * @author daniel
 *         http://www.learn-sudoku.com/hidden-singles.html
 */
public class HiddenSingles implements IAlgorithm {

    @Override
    public void execute(IMatrix matrix) {
        findInRows(matrix);
        findInCols(matrix);
        findInBlock(matrix);
    }

    private void findInBlock(IMatrix matrix) {
        for (int rowGroup = 0; rowGroup < IMatrix.BLOCK_SIZE; rowGroup++) {
            for (int colGroup = 0; colGroup < IMatrix.BLOCK_SIZE; colGroup++) {

                Map<Integer, Integer> counter = new HashMap<>();
                for (int row = rowGroup * IMatrix.BLOCK_SIZE; row < (rowGroup + 1) * IMatrix.BLOCK_SIZE; row++) {
                    for (int col = colGroup * IMatrix.BLOCK_SIZE; col < (colGroup + 1) * IMatrix.BLOCK_SIZE; col++) {
                        onFind(matrix, counter, row, col);
                    }
                }

                for (Map.Entry<Integer, Integer> entry : counter.entrySet()) {
                    if (entry.getValue() == 1) {
                        for (int row = rowGroup * IMatrix.BLOCK_SIZE; row < (rowGroup + 1) * IMatrix.BLOCK_SIZE; row++) {
                            for (int col = colGroup * IMatrix.BLOCK_SIZE; col < (colGroup + 1) * IMatrix.BLOCK_SIZE; col++) {
                                if (matrix.getPossibleValues(row, col).contains(entry.getKey())) {
                                    matrix.setCellValue(row, col, entry.getKey());
                                    matrix.getPossibleValues(row, col).clear();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void onFind(IMatrix matrix, Map<Integer, Integer> counter, int row, int col) {
        Collection<Integer> candidates = matrix.getPossibleValues(row, col);
        for (int item : candidates) {
            Integer value = counter.get(item);
            if (value == null) {
                value = 0;
            }
            counter.put(item, ++value);
        }
    }

    private void findInCols(IMatrix matrix) {
        for (int col = 0; col < matrix.SIZE; col++) {
            Map<Integer, Integer> counter = new HashMap<>();
            for (int row = 0; row < matrix.SIZE; row++) {
                onFind(matrix, counter, row, col);
            }

            for (Map.Entry<Integer, Integer> entry : counter.entrySet()) {
                if (entry.getValue() == 1) {
                    for (int row = 0; row < IMatrix.SIZE; row++) {
                        if (matrix.getPossibleValues(row, col).contains(entry.getKey())) {
                            matrix.setCellValue(row, col, entry.getKey());
                            matrix.getPossibleValues(row, col).clear();
                        }
                    }
                }
            }
        }
    }

    private void findInRows(IMatrix matrix) {
        for (int row = 0; row < IMatrix.SIZE; row++) {
            Map<Integer, Integer> counter = new HashMap<>();
            for (int col = 0; col < IMatrix.SIZE; col++) {
                onFind(matrix, counter, row, col);
            }

            for (Map.Entry<Integer, Integer> entry : counter.entrySet()) {
                if (entry.getValue() == 1) {
                    for (int col = 0; col < IMatrix.SIZE; col++) {
                        if (matrix.getPossibleValues(row, col).contains(entry.getKey())) {
                            matrix.setCellValue(row, col, entry.getKey());
                            matrix.getPossibleValues(row, col).clear();
                        }
                    }
                }
            }
        }
    }

}
