package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.utils.Utils;

/**
 * Created by daniel on 2016-02-15.
 */
public class XWing implements IAlgorithm {

    @Override
    public void execute(IMatrix matrix) {
        for (int row = 0; row < IMatrix.SIZE; row++) {
            for (int col = 0; col < IMatrix.SIZE; col++) {
                for (int candidate : matrix.getCandidates(row, col)) {
                    if (matrix.occurenciesInCol(col, candidate) == 2) {
                        //detect in rows:
                        for (int tempCol = col + 1; tempCol < IMatrix.SIZE; tempCol++) {

                            if (Utils.theSameBlock(col, tempCol) && matrix.occurenciesInCol(tempCol, candidate) != 2) {
                                continue;
                            }

                            if (matrix.getCandidates(row, tempCol).contains(candidate)) {
                                for (int tempRow = row + 1; tempRow < IMatrix.SIZE; tempRow++) {

                                    if (Utils.theSameBlock(row, tempRow)) {
                                        continue;
                                    }

                                    if (matrix.getCandidates(tempRow, col).contains(candidate) && matrix.getCandidates(tempRow, tempCol).contains(candidate)) {
                                        for (int c = 0; c < IMatrix.SIZE; c++) {
                                            if (c != col && c != tempCol) {
                                                matrix.getCandidates(row, c).remove(candidate);
                                                matrix.getCandidates(tempRow, c).remove(candidate);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (matrix.occurenciesInRow(row, candidate) == 2) {
                        //detect in cols:
                        for (int tempRow = row + 1; tempRow < IMatrix.SIZE; tempRow++) {

                            if (Utils.theSameBlock(row, tempRow) && matrix.occurenciesInRow(tempRow, candidate) != 2) {
                                continue;
                            }

                            if (matrix.getCandidates(tempRow, col).contains(candidate)) {
                                for (int tempCol = col + 1; tempCol < IMatrix.SIZE; tempCol++) {

                                    if (Utils.theSameBlock(col, tempCol)) {
                                        continue;
                                    }

                                    if (matrix.getCandidates(row, tempCol).contains(candidate) && matrix.getCandidates(tempRow, tempCol).contains(candidate)) {
                                        for (int r = 0; r < IMatrix.SIZE; r++) {
                                            if (r != row && r != tempRow) {
                                                matrix.getCandidates(r, col).remove(candidate);
                                                matrix.getCandidates(r, tempCol).remove(candidate);
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
    }
}
