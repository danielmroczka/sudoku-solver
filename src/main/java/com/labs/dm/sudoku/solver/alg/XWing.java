package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 2016-02-15.
 */
public class XWing implements IAlgorithm {

    @Override
    public void execute(IMatrix matrix) {
        for (int row = 0; row < IMatrix.SIZE; row++) {
            for (int col = 0; col < IMatrix.SIZE; col++) {

                List<Integer> candidates = new ArrayList<>(matrix.getCandidates(row, col));

                for (int candidate : candidates) {
                    if (matrix.candidatesCountInCol(col, candidate) == 2) {
                        //detect in rows:
                        for (int tempCol = col + 1; tempCol < IMatrix.SIZE; tempCol++) {

                            if (Utils.theSameBlock(col, tempCol) || matrix.candidatesCountInCol(tempCol, candidate) != 2) {
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
                                                matrix.removeCandidate(row, c, candidate);
                                                matrix.removeCandidate(tempRow, c, candidate);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (matrix.candidatesCountInRow(row, candidate) == 2) {
                        //detect in cols:
                        for (int tempRow = row + 1; tempRow < IMatrix.SIZE; tempRow++) {

                            if (Utils.theSameBlock(row, tempRow) || matrix.candidatesCountInRow(tempRow, candidate) != 2) {
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
                                                matrix.removeCandidate(r, col, candidate);
                                                matrix.removeCandidate(r, tempCol, candidate);
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
