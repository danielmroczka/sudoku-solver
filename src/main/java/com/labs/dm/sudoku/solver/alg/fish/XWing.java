package com.labs.dm.sudoku.solver.alg.fish;

import com.labs.dm.sudoku.solver.alg.IAlgorithm;
import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;

import java.util.List;

import static com.labs.dm.sudoku.solver.utils.Utils.theSameBlock;

/**
 * Created by Daniel Mroczka on 2016-02-15.
 */
public class XWing implements IAlgorithm {

    @Override
    public void execute(IMatrix matrix) {
        for (int row = 0; row < Matrix.SIZE; row++) {
            for (int col = 0; col < Matrix.SIZE; col++) {

                List<Integer> candidates = matrix.getCandidates(row, col);

                for (int candidate : candidates) {
                    if (matrix.candidatesCountInCol(col, candidate) == 2) {
                        //detect in rows:
                        for (int tempCol = col + 1; tempCol < Matrix.SIZE; tempCol++) {

                            if (theSameBlock(col, tempCol) || matrix.candidatesCountInCol(tempCol, candidate) != 2) {
                                continue;
                            }

                            if (matrix.getCandidates(row, tempCol).contains(candidate)) {
                                for (int tempRow = row + 1; tempRow < Matrix.SIZE; tempRow++) {

                                    if (theSameBlock(row, tempRow)) {
                                        continue;
                                    }

                                    if (matrix.getCandidates(tempRow, col).contains(candidate) && matrix.getCandidates(tempRow, tempCol).contains(candidate)) {
                                        for (int c = 0; c < Matrix.SIZE; c++) {
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
                        for (int tempRow = row + 1; tempRow < Matrix.SIZE; tempRow++) {

                            if (theSameBlock(row, tempRow) || matrix.candidatesCountInRow(tempRow, candidate) != 2) {
                                continue;
                            }

                            if (matrix.getCandidates(tempRow, col).contains(candidate)) {
                                for (int tempCol = col + 1; tempCol < Matrix.SIZE; tempCol++) {

                                    if (theSameBlock(col, tempCol)) {
                                        continue;
                                    }

                                    if (matrix.getCandidates(row, tempCol).contains(candidate) && matrix.getCandidates(tempRow, tempCol).contains(candidate)) {
                                        for (int r = 0; r < Matrix.SIZE; r++) {
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
