package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.utils.Utils;

/**
 * Created by daniel on 2016-02-15.
 */
public class XWing implements IAlgorithm {
    @Override
    public void execute(IMatrix matrix) {
        removeInRows(matrix);
        removeInCols(matrix);
    }

    private void removeInRows(IMatrix matrix) {
        int reduced = 0;
        for (int row = 0; row < IMatrix.SIZE; row++) {
            for (int col = 0; col < IMatrix.SIZE; col++) {

                for (int cand : matrix.getPossibleValues(row, col)) {

                    if (matrix.occurenciesInCol(col, cand) != 2) {
                        continue;
                    }

                    //detect in rows:
                    for (int tempCol = col + 1; tempCol < IMatrix.SIZE; tempCol++) {

                        if (Utils.theSameBlock(col, tempCol)) {
                            continue;
                        }
                        if (matrix.occurenciesInCol(tempCol, cand) != 2) {
                            continue;
                        }

                        if (matrix.getPossibleValues(row, tempCol).contains(cand)) {
                            for (int tempRow = row + 1; tempRow < IMatrix.SIZE; tempRow++) {

                                if (Utils.theSameBlock(row, tempRow)) {
                                    continue;
                                }

                                boolean matchedInFirstRow = matrix.getPossibleValues(row, tempCol).contains(cand);
                                boolean matchedInSecondRow = matrix.getPossibleValues(tempRow, tempCol).contains(cand);

                                if (matchedInFirstRow && matchedInSecondRow) {
                                    for (int c = 0; c < IMatrix.SIZE; c++) {
                                        if (c != col && c != tempCol) {
                                            if (matrix.getPossibleValues(row, c).remove(cand)) reduced++;
                                            if (matrix.getPossibleValues(tempRow, c).remove(cand)) reduced++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }

        System.out.println("Reduced in rows: " + reduced);
    }

    private void removeInCols(IMatrix matrix) {
        int reduced = 0;
        for (int row = 0; row < IMatrix.SIZE; row++) {
            for (int col = 0; col < IMatrix.SIZE; col++) {

                for (int cand : matrix.getPossibleValues(row, col)) {

                    if (matrix.occurenciesInRow(row, cand) != 2) {
                        continue;
                    }

                    //detect in cols:
                    for (int tempRow = row + 1; tempRow < IMatrix.SIZE; tempRow++) {

                        if (Utils.theSameBlock(row, tempRow)) {
                            continue;
                        }
                        if (matrix.occurenciesInRow(tempRow, cand) != 2) {
                            continue;
                        }

                        if (matrix.getPossibleValues(tempRow, col).contains(cand)) {
                            for (int tempCol = col + 1; tempCol < IMatrix.SIZE; tempCol++) {

                                if (Utils.theSameBlock(col, tempCol)) {
                                    continue;
                                }

                                boolean matchedInFirstCol = matrix.getPossibleValues(tempRow, col).contains(cand);
                                boolean matchedInSecondCol = matrix.getPossibleValues(tempRow, tempCol).contains(cand);

                                if (matchedInFirstCol && matchedInSecondCol) {
                                    for (int r = 0; r < IMatrix.SIZE; r++) {
                                        if (r != row && r != tempRow) {
                                            if (matrix.getPossibleValues(r, col).remove(cand)) {
                                                reduced++;
                                            }
                                            if (matrix.getPossibleValues(r, tempCol).remove(cand)) {
                                                reduced++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
        System.out.println("Reduced in cols: " + reduced);

    }

}
