package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.utils.CounterHashMap;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static com.labs.dm.sudoku.solver.core.IMatrix.BLOCK_SIZE;

/**
 * Naked Pairs Algorithm Implementation
 * http://www.learn-sudoku.com/naked-pairs.html
 * <p/>
 * Created by daniel on 2016-02-11.
 */
public class NakedPairs implements IAlgorithm {

    protected int SIZE = 2;

    @Override
    public void execute(IMatrix matrix) {
        findNakedPairsInCols(matrix);
        findNakedPairsInRows(matrix);
        findNakedPairsInBlocks(matrix);
    }

    private void findNakedPairsInBlocks(IMatrix matrix) {
        for (int rowGroup = 0; rowGroup < BLOCK_SIZE; rowGroup++) {
            for (int colGroup = 0; colGroup < BLOCK_SIZE; colGroup++) {
                CounterHashMap<Collection<Integer>> map = new CounterHashMap<>();
                for (int row = rowGroup * BLOCK_SIZE; row < (rowGroup + 1) * BLOCK_SIZE; row++) {
                    for (int col = colGroup * BLOCK_SIZE; col < (colGroup + 1) * BLOCK_SIZE; col++) {
                        count(matrix, map, row, col);
                    }
                }

                for (Map.Entry<Collection<Integer>, Integer> entry : map.entrySet()) {
                    if (accept(entry.getValue())) {
                        for (int row = rowGroup * BLOCK_SIZE; row < (rowGroup + 1) * BLOCK_SIZE; row++) {
                            for (int col = colGroup * BLOCK_SIZE; col < (colGroup + 1) * BLOCK_SIZE; col++) {
                                if (!matrix.getCandidates(row, col).equals(entry.getKey()) && !Collections.disjoint(matrix.getCandidates(row, col), entry.getKey())) {
                                    matrix.removeCandidate(row, col, entry.getKey());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void findNakedPairsInRows(IMatrix matrix) {
        for (int row = 0; row < IMatrix.SIZE; row++) {
            CounterHashMap<Collection<Integer>> map = new CounterHashMap<>();
            for (int col = 0; col < IMatrix.SIZE; col++) {
                count(matrix, map, row, col);
            }

            for (Map.Entry<Collection<Integer>, Integer> entry : map.entrySet()) {
                if (accept(entry.getValue())) {
                    for (int col = 0; col < IMatrix.SIZE; col++) {
                        if (!matrix.getCandidates(row, col).equals(entry.getKey()) && !Collections.disjoint(matrix.getCandidates(row, col), entry.getKey())) {
                            matrix.removeCandidate(row, col, entry.getKey());
                        }
                    }
                }
            }
        }
    }

    private void findNakedPairsInCols(IMatrix matrix) {
        for (int col = 0; col < IMatrix.SIZE; col++) {
            CounterHashMap<Collection<Integer>> map = new CounterHashMap<>();
            for (int row = 0; row < IMatrix.SIZE; row++) {
                count(matrix, map, row, col);
            }

            for (Map.Entry<Collection<Integer>, Integer> entry : map.entrySet()) {
                if (accept(entry.getValue())) {
                    for (int row = 0; row < IMatrix.SIZE; row++) {
                        if (!matrix.getCandidates(row, col).equals(entry.getKey()) && !Collections.disjoint(matrix.getCandidates(row, col), entry.getKey())) {
                            matrix.removeCandidate(row, col, entry.getKey());
                        }
                    }
                }
            }
        }
    }

    private void count(IMatrix matrix, CounterHashMap<Collection<Integer>> map, int row, int col) {
        Collection<Integer> key = matrix.getCandidates(row, col);
        if (accept(key.size())) {
            map.inc(key);
        }
    }

    private boolean accept(int value) {
        return value == SIZE;//>= 2 && value <= SIZE;
    }

}
