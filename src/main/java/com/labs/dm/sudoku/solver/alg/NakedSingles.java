package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;

import java.util.*;

/**
 * Naked Pairs Algorithm Implementation
 * http://www.learn-sudoku.com/naked-singles.html
 * <p/>
 * Created by daniel on 2016-02-15.
 */
public class NakedSingles implements IAlgorithm {

    @Override
    public void execute(IMatrix matrix) {
        //findNakedPairsInCols(matrix);
        findNakedSinglesInRows(matrix);
        //findNakedPairsInBlocks(matrix);
    }

    private void findNakedPairsInBlocks(IMatrix matrix) {
        for (int rowGroup = 0; rowGroup < IMatrix.BLOCK_SIZE; rowGroup++) {
            for (int colGroup = 0; colGroup < IMatrix.BLOCK_SIZE; colGroup++) {
                Map<Collection<Integer>, Integer> map = new HashMap<>();
                for (int row = rowGroup * IMatrix.BLOCK_SIZE; row < (rowGroup + 1) * IMatrix.BLOCK_SIZE; row++) {
                    for (int col = colGroup * IMatrix.BLOCK_SIZE; col < (colGroup + 1) * IMatrix.BLOCK_SIZE; col++) {
                        count(matrix, map, row, col);
                    }
                }

                for (Map.Entry<Collection<Integer>, Integer> entry : map.entrySet()) {
                    if (entry.getValue() == 2) {
                        for (int row = rowGroup * IMatrix.BLOCK_SIZE; row < (rowGroup + 1) * IMatrix.BLOCK_SIZE; row++) {
                            for (int col = colGroup * IMatrix.BLOCK_SIZE; col < (colGroup + 1) * IMatrix.BLOCK_SIZE; col++) {
                                if (!matrix.getCandidates(row, col).equals(entry.getKey()) && !Collections.disjoint(matrix.getCandidates(row, col), entry.getKey())) {
                                    matrix.getCandidates(row, col).removeAll(entry.getKey());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void count(IMatrix matrix, Map<Collection<Integer>, Integer> map, int row, int col) {
        Collection<Integer> key = matrix.getCandidates(row, col);
        if (key.size() == 2) {
            Integer val = map.get(key);
            if (val == null) {
                val = 0;
            }
            map.put(new HashSet<>(key), ++val);
        }
    }

    private void findNakedSinglesInRows(IMatrix matrix) {
        for (int row = 0; row < IMatrix.SIZE; row++) {
            Map<Collection<Integer>, Integer> map = new HashMap<>();
            for (int col = 0; col < IMatrix.SIZE; col++) {
                count(matrix, map, row, col);
            }

            for (Map.Entry<Collection<Integer>, Integer> entry : map.entrySet()) {
                if (entry.getValue() == 2) {
                    for (int col = 0; col < IMatrix.SIZE; col++) {
                        Collection<Integer> candidates = matrix.getCandidates(row, col);
                        if (!candidates.equals(entry.getKey()) && !Collections.disjoint(candidates, entry.getKey())) {
                            candidates.removeAll(entry.getKey());
                        }
                    }
                }
            }

        }
    }

    private void findNakedSinglesInCols(IMatrix matrix) {
        for (int col = 0; col < IMatrix.SIZE; col++) {
            Map<Collection<Integer>, Integer> map = new HashMap<>();
            for (int row = 0; row < IMatrix.SIZE; row++) {
                count(matrix, map, row, col);
            }

            for (Map.Entry<Collection<Integer>, Integer> entry : map.entrySet()) {
                if (entry.getValue() == 2) {
                    for (int row = 0; row < IMatrix.SIZE; row++) {
                        if (!matrix.getCandidates(row, col).equals(entry.getKey()) && !Collections.disjoint(matrix.getCandidates(row, col), entry.getKey())) {
                            matrix.getCandidates(row, col).removeAll(entry.getKey());
                        }
                    }
                }
            }

        }
    }
}