package com.labs.dm.sudoku.solver.alg.experimental;

import com.labs.dm.sudoku.solver.alg.IAlgorithm;
import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;

/**
 * Created by Daniel Mroczka on 2016-03-21.
 */
public abstract class HiddenSubSet implements IAlgorithm {
    @Override
    public void execute(IMatrix matrix) {
        findHiddenPairsInRows(matrix);
        findHiddenPairsInCols(matrix);
        findHiddenPairsInBlocks(matrix);
    }

    private void findHiddenPairsInRows(IMatrix matrix) {
        int[][] tab = new int[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                for (int candidate : matrix.getCandidates(row, col)) {
                    tab[candidate - 1][col] = col + 1;
                }
            }
        }
        System.out.println(tab);
    }

    private void findHiddenPairsInCols(IMatrix matrix) {

    }

    private void findHiddenPairsInBlocks(IMatrix matrix) {
    }

    public static void main(String[] args) {
        com.labs.dm.sudoku.solver.alg.experimental.HiddenSubSet subset = new com.labs.dm.sudoku.solver.alg.experimental.HiddenSubSet() {
        };

        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{1, 2, 3, 6, 7});
        matrix.addCandidates(0, 1, new Integer[]{2, 4, 5, 6, 7});
        matrix.addCandidates(0, 2, new Integer[]{1, 4, 5, 6, 7, 8});
        matrix.addCandidates(0, 3, new Integer[]{5, 9});
        matrix.addCandidates(0, 4, new Integer[]{4, 2});
        matrix.addCandidates(0, 5, new Integer[]{7, 8});
        matrix.addCandidates(0, 6, new Integer[]{4, 3, 9});
        matrix.addCandidates(0, 7, new Integer[]{4, 2, 5});
        matrix.addCandidates(0, 8, new Integer[]{2, 3, 4, 5, 9});
        //WHEN
        subset.execute(matrix);

        System.out.println(subset);

    }

}
