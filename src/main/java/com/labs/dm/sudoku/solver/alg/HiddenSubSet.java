package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Pair;
import com.labs.dm.sudoku.solver.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.labs.dm.sudoku.solver.core.IMatrix.BLOCK_SIZE;

/**
 * Created by Daniel Mroczka on 2016-03-15.
 */
public abstract class HiddenSubSet implements IAlgorithm {

    protected int SIZE;

    @Override
    public void execute(IMatrix matrix) {
        Map<Pair, List<Integer>> toRemove = new HashMap<>();
        findHiddenPairsInRows(matrix, toRemove);
        findHiddenPairsInCols(matrix, toRemove);
        findHiddenPairsInBlocks(matrix, toRemove);

        //remove(matrix, toRemove);
    }

    private void remove(IMatrix matrix, Map<Pair, List<Integer>> toRemove) {
        for (Map.Entry<Pair, List<Integer>> entry : toRemove.entrySet()) {

            removeCandidate(matrix, entry.getKey().getRow(), entry.getKey().getCol(), entry.getValue());
        }
    }

    private boolean findHiddenPairsInRows(IMatrix matrix, Map<Pair, List<Integer>> toRemove) {
        boolean res = false;
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
                    List<Integer> val = toRemove.get(new Pair(row, col));
                    if (val == null) {
                        val = new ArrayList<>();
                    }
                    val.addAll(subset);
                    toRemove.put(new Pair(row, col), val);

                    removeCandidate(matrix, row, col, subset);
                }
                res = true;
            }
        }
        return res;
    }

    private boolean findHiddenPairsInBlocks(IMatrix matrix, Map<Pair, List<Integer>> toRemove) {
        boolean res = false;
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
                            List<Integer> val = toRemove.get(new Pair(row, col));
                            if (val == null) {
                                val = new ArrayList<>();
                            }
                            val.addAll(subset);
                            toRemove.put(new Pair(row, col), val);

                        }
                    }
                    res = true;
                }
            }
        }
        return res;
    }

    private boolean findHiddenPairsInCols(IMatrix matrix, Map<Pair, List<Integer>> toRemove) {
        boolean res = false;

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
                    List<Integer> val = toRemove.get(new Pair(row, col));
                    if (val == null) {
                        val = new ArrayList<>();
                    }
                    val.addAll(subset);
                    toRemove.put(new Pair(row, col), val);

                }
                res = true;
            }
        }

        return res;
    }

    private void removeCandidate(IMatrix matrix, int row, int col, List<Integer> subset) {
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
