package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;

import java.util.*;

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
        for (int rowGroup = 0; rowGroup < IMatrix.BLOCK_SIZE; rowGroup++) {
            for (int colGroup = 0; colGroup < IMatrix.BLOCK_SIZE; colGroup++) {
            }
        }
    }

    private void detectNakedPairsInRows(IMatrix matrix) {

        for (int row = 0; row < matrix.SIZE; row++) {
            Map<Collection<Integer>, Integer> map = new HashMap<>();
            for (int col = 0; col < matrix.SIZE; col++) {
                if (matrix.getPossibleValues(row, col).size() == 2) {
                    Integer val = map.get(matrix.getPossibleValues(row, col));
                    if (val == null) {
                        val = 0;
                    }
                    map.put(new HashSet<Integer>(matrix.getPossibleValues(row, col)), ++val);
                }
            }

            for (final Map.Entry<Collection<Integer>, Integer> entry : map.entrySet()) {
                if (entry.getValue() == 2) {
                    for (int col = 0; col < matrix.SIZE; col++) {
                        if (!Collections.disjoint(matrix.getPossibleValues(row, col), entry.getKey())) {
                            matrix.getPossibleValues(row, col).removeAll(entry.getKey());
                        }
                    }
                }
            }

        }
    }

    private void detectNakedPairsInCols(IMatrix matrix) {
        for (int col = 0; col < matrix.SIZE; col++) {
            Map<Collection<Integer>, Integer> map = new HashMap<>();
            for (int row = 0; row < matrix.SIZE; row++) {
                if (matrix.getPossibleValues(row, col).size() == 2) {
                    Integer val = map.get(matrix.getPossibleValues(row, col));
                    if (val == null) {
                        val = 0;
                    }
                    map.put(new HashSet<Integer>(matrix.getPossibleValues(row, col)), ++val);
                }
            }

            for (final Map.Entry<Collection<Integer>, Integer> entry : map.entrySet()) {
                if (entry.getValue() == 2) {
                    for (int row = 0; row < matrix.SIZE; row++) {
                        if (!Collections.disjoint(matrix.getPossibleValues(row, col), entry.getKey())) {
                            matrix.getPossibleValues(row, col).removeAll(entry.getKey());
                        }
                    }
                }
            }

        }
    }
}
