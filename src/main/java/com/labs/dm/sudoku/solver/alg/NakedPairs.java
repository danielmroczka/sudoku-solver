package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;

import java.util.*;

/**
 * Naked Pairs Algorithm Implementation
 * http://www.learn-sudoku.com/naked-pairs.html
 * <p/>
 * Created by daniel on 2016-02-11.
 */
public class NakedPairs implements IAlgorithm {

    @Override
    public void execute(IMatrix matrix) {
        findNakedPairsInCols(matrix);
        findNakedPairsInRows(matrix);
        findNakedPairsInBlocks(matrix);
    }

    private void findNakedPairsInBlocks(IMatrix matrix) {
        for (int rowGroup = 0; rowGroup < IMatrix.BLOCK_SIZE; rowGroup++) {
            for (int colGroup = 0; colGroup < IMatrix.BLOCK_SIZE; colGroup++) {
                Map<Collection<Integer>, Integer> map = new HashMap<>();
                for (int row = rowGroup * IMatrix.BLOCK_SIZE; row < (rowGroup + 1) * IMatrix.BLOCK_SIZE; row++) {
                    for (int col = colGroup * IMatrix.BLOCK_SIZE; col < (colGroup + 1) * IMatrix.BLOCK_SIZE; col++) {
                        onFind(matrix, map, row, col);
                    }
                }

                for (Map.Entry<Collection<Integer>, Integer> entry : map.entrySet()) {
                    if (entry.getValue() == 2) {
                        for (int row = rowGroup * IMatrix.BLOCK_SIZE; row < (rowGroup + 1) * IMatrix.BLOCK_SIZE; row++) {
                            for (int col = colGroup * IMatrix.BLOCK_SIZE; col < (colGroup + 1) * IMatrix.BLOCK_SIZE; col++) {
                                if (!Collections.disjoint(matrix.getPossibleValues(row, col), entry.getKey())) {
                                    matrix.getPossibleValues(row, col).removeAll(entry.getKey());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void onFind(IMatrix matrix, Map<Collection<Integer>, Integer> map, int row, int col) {
        if (matrix.getPossibleValues(row, col).size() == 2) {
            Integer val = map.get(matrix.getPossibleValues(row, col));
            if (val == null) {
                val = 0;
            }
            map.put(new HashSet<>(matrix.getPossibleValues(row, col)), ++val);
        }
    }

    private void findNakedPairsInRows(IMatrix matrix) {

        for (int row = 0; row < IMatrix.SIZE; row++) {
            Map<Collection<Integer>, Integer> map = new HashMap<>();
            for (int col = 0; col < matrix.SIZE; col++) {
                onFind(matrix, map, row, col);
            }

            for (Map.Entry<Collection<Integer>, Integer> entry : map.entrySet()) {
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

    private void findNakedPairsInCols(IMatrix matrix) {
        for (int col = 0; col < matrix.SIZE; col++) {
            Map<Collection<Integer>, Integer> map = new HashMap<>();
            for (int row = 0; row < matrix.SIZE; row++) {
                onFind(matrix, map, row, col);
            }

            for (Map.Entry<Collection<Integer>, Integer> entry : map.entrySet()) {
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
