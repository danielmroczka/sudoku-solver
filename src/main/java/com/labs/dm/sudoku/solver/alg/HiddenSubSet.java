package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.labs.dm.sudoku.solver.core.IMatrix.BLOCK_SIZE;

/**
 * Created by Daniel Mroczka on 2016-03-15.
 */
public abstract class HiddenSubSet implements IAlgorithm {

    protected int SIZE;

    @Override
    public void execute(IMatrix matrix) {
        findHiddenPairsInRows(matrix);
        findHiddenPairsInCols(matrix);
        findHiddenPairsInBlocks(matrix);
    }

    private void findHiddenPairsInRows(IMatrix matrix) {
        for (int row = 0; row < IMatrix.SIZE; row++) {

            /* Collect candidates in row */
            List<List<Integer>> list = new ArrayList<>();
            for (int col = 0; col < IMatrix.SIZE; col++) {
                list.add(matrix.getCandidates(row, col));
            }

            /* Find hidden subset */
            List<Integer> subset = Utils.hiddenSubset(list, SIZE);

            /* Remove candidate on cells where hidden subset has been found */
            if (!subset.isEmpty()) {
                for (int col = 0; col < IMatrix.SIZE; col++) {
                    removeCandidate(matrix, row, col, subset);
                }
            }
        }
    }

    private void findHiddenPairsInBlocks(IMatrix matrix) {
        for (int rowGroup = 0; rowGroup < BLOCK_SIZE; rowGroup++) {
            for (int colGroup = 0; colGroup < BLOCK_SIZE; colGroup++) {

                /* Collect candidates in block */
                List<List<Integer>> list = new ArrayList<>();
                for (int row = rowGroup * BLOCK_SIZE; row < (rowGroup + 1) * BLOCK_SIZE; row++) {
                    for (int col = colGroup * BLOCK_SIZE; col < (colGroup + 1) * BLOCK_SIZE; col++) {
                        list.add(matrix.getCandidates(row, col));
                    }
                }

                /* Find hidden subset */
                List<Integer> subset = Utils.hiddenSubset(list, SIZE);

                /* Remove candidate on cells where hidden subset has been found */
                if (!subset.isEmpty()) {
                    for (int row = rowGroup * BLOCK_SIZE; row < (rowGroup + 1) * BLOCK_SIZE; row++) {
                        for (int col = colGroup * BLOCK_SIZE; col < (colGroup + 1) * BLOCK_SIZE; col++) {
                            removeCandidate(matrix, row, col, subset);
                        }
                    }
                }
            }
        }
    }

    private void findHiddenPairsInCols(IMatrix matrix) {
        for (int col = 0; col < IMatrix.SIZE; col++) {

            /* Collect candidates in column */
            List<List<Integer>> list = new ArrayList<>();
            for (int row = 0; row < IMatrix.SIZE; row++) {
                list.add(matrix.getCandidates(row, col));
            }

            /* Find hidden subset */
            List<Integer> subset = Utils.hiddenSubset(list, SIZE);

            /* Remove candidate on cells where hidden subset has been found */
            if (!subset.isEmpty()) {
                for (int row = 0; row < IMatrix.SIZE; row++) {
                    removeCandidate(matrix, row, col, subset);
                }
            }
        }
    }

    protected void removeCandidate(IMatrix matrix, int row, int col, List<Integer> subset) {
        if (matrix.getCandidates(row, col).isEmpty()) {
            return;
        }

        List<Integer> common = new ArrayList<>(matrix.getCandidates(row, col));
        common.retainAll(subset);

        if (common.size() > 0) {
            List<Integer> diff = new ArrayList<>();

            for (int candidate : matrix.getCandidates(row, col)) {
                if (!subset.contains(candidate)) {
                    diff.add(candidate);
                }
            }

            matrix.removeCandidate(row, col, diff);
        }
    }

}
