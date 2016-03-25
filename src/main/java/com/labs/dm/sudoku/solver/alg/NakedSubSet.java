package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;

import java.util.List;

import static com.labs.dm.sudoku.solver.core.IMatrix.BLOCK_SIZE;
import static com.labs.dm.sudoku.solver.utils.SubSetUtils.nakedSubset;

/**
 * Created by Daniel Mroczka on 2016-03-15.
 */
public abstract class NakedSubSet implements IAlgorithm {

    protected int SIZE;

    @Override
    public void execute(IMatrix matrix) {
        findNakedPairsInRows(matrix);
        findNakedPairsInCols(matrix);
        findNakedPairsInBlocks(matrix);
    }

    private void findNakedPairsInRows(IMatrix matrix) {
        for (int row = 0; row < IMatrix.SIZE; row++) {
            List<List<Integer>> list = matrix.getCandidatesInRow(row);
            List<List<Integer>> res = nakedSubset(list, SIZE);

            if (res.size() != SIZE + 1) {
                continue;
            }

            for (int col = 0; col < IMatrix.SIZE; col++) {
                removeCandidate(matrix, res, row, col);
            }
        }
    }

    private void findNakedPairsInCols(IMatrix matrix) {
        for (int col = 0; col < IMatrix.SIZE; col++) {
            List<List<Integer>> list = matrix.getCandidatesInCol(col);
            List<List<Integer>> res = nakedSubset(list, SIZE);

            if (res.size() != SIZE + 1) {
                continue;
            }

            for (int row = 0; row < IMatrix.SIZE; row++) {
                removeCandidate(matrix, res, row, col);
            }
        }
    }

    private void findNakedPairsInBlocks(IMatrix matrix) {
        for (int rowGroup = 0; rowGroup < BLOCK_SIZE; rowGroup++) {
            for (int colGroup = 0; colGroup < BLOCK_SIZE; colGroup++) {
                List<List<Integer>> list = matrix.getCandidatesInBlock(rowGroup, colGroup);
                List<List<Integer>> res = nakedSubset(list, SIZE);

                if (res.size() != SIZE + 1) {
                    continue;
                }

                for (int row = rowGroup * BLOCK_SIZE; row < (rowGroup + 1) * BLOCK_SIZE; row++) {
                    for (int col = colGroup * BLOCK_SIZE; col < (colGroup + 1) * BLOCK_SIZE; col++) {
                        removeCandidate(matrix, res, row, col);
                    }
                }

            }
        }
    }

    private void removeCandidate(IMatrix matrix, List<List<Integer>> res, int row, int col) {
        if (matrix.getCandidates(row, col).isEmpty()) {
            return;
        }
        boolean contains = false;
        for (List<Integer> item : res) {
            if (item.equals(matrix.getCandidates(row, col))) {
                contains = true;
                break;
            }
        }
        if (!contains) {
            matrix.removeCandidate(row, col, res.get(0));
        }
    }
}
