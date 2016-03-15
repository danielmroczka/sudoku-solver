/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.utils.CounterHashMap;
import com.labs.dm.sudoku.solver.utils.Utils;

import java.util.Collection;
import java.util.Map.Entry;

import static com.labs.dm.sudoku.solver.core.IMatrix.BLOCK_SIZE;
import static com.labs.dm.sudoku.solver.core.IMatrix.SIZE;

/**
 * Hidden Singles algorithm implementation
 *
 * @author Daniel Mroczka
 *         <p>
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
        for (int rowGroup = 0; rowGroup < BLOCK_SIZE; rowGroup++) {
            for (int colGroup = 0; colGroup < BLOCK_SIZE; colGroup++) {

                CounterHashMap<Integer> counter = new CounterHashMap<>();
                for (int row : Utils.blockElems(rowGroup * BLOCK_SIZE)) {
                    for (int col : Utils.blockElems(colGroup * BLOCK_SIZE)) {
                        onFind(matrix, counter, row, col);
                    }
                }

                for (Entry<Integer, Integer> entry : counter.entrySet()) {
                    if (entry.getValue() == 1) {
                        for (int row : Utils.blockElems(rowGroup * BLOCK_SIZE)) {
                            for (int col : Utils.blockElems(colGroup * BLOCK_SIZE)) {
                                if (matrix.getCandidates(row, col).contains(entry.getKey())) {
                                    matrix.setValueAt(row, col, entry.getKey());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void findInCols(IMatrix matrix) {
        for (int col = 0; col < SIZE; col++) {
            CounterHashMap<Integer> counter = new CounterHashMap<>();
            for (int row = 0; row < SIZE; row++) {
                onFind(matrix, counter, row, col);
            }

            for (Entry<Integer, Integer> entry : counter.entrySet()) {
                if (entry.getValue() == 1) {
                    for (int row = 0; row < SIZE; row++) {
                        if (matrix.getCandidates(row, col).contains(entry.getKey())) {
                            matrix.setValueAt(row, col, entry.getKey());
                        }
                    }
                }
            }
        }
    }

    private void findInRows(IMatrix matrix) {
        for (int row = 0; row < SIZE; row++) {
            CounterHashMap<Integer> counter = new CounterHashMap<>();
            for (int col = 0; col < SIZE; col++) {
                onFind(matrix, counter, row, col);
            }

            for (Entry<Integer, Integer> entry : counter.entrySet()) {
                if (entry.getValue() == 1) {
                    for (int col = 0; col < SIZE; col++) {
                        if (matrix.getCandidates(row, col).contains(entry.getKey())) {
                            matrix.setValueAt(row, col, entry.getKey());
                        }
                    }
                }
            }
        }
    }

    private void onFind(IMatrix matrix, CounterHashMap<Integer> counter, int row, int col) {
        Collection<Integer> candidates = matrix.getCandidates(row, col);
        for (int item : candidates) {
            counter.inc(item);
        }
    }
}
