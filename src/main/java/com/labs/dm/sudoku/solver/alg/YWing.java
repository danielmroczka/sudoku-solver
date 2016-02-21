package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Pair;
import com.labs.dm.sudoku.solver.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.labs.dm.sudoku.solver.core.IMatrix.BLOCK_SIZE;

/**
 * Created by daniel on 2016-02-21.
 */
public class YWing implements IAlgorithm {

    private void eliminateCandidate(IMatrix matrix, Pair pair1, Pair pair2) {
        int candidate = candidateToEliminate(matrix, pair1.getRow(), pair1.getCol(), pair2.getRow(), pair2.getCol());

        if (candidate < 1) {
            return;
        }
        for (Pair neighbor : Utils.intersection(pair1, pair2)) {
            System.out.println("Eliminate at " + neighbor.toString());
            matrix.getCandidates(neighbor.getRow(), neighbor.getCol()).remove(candidate);
        }
    }

    private int candidateToEliminate(IMatrix matrix, int row, int col, int row2, int col2) {
        List<Integer> common = new ArrayList<>(matrix.getCandidates(row, col2));
        common.retainAll(matrix.getCandidates(row2, col));
        if (common.size() == 1) {
            return common.get(0);
        }

        return 0;
    }

    @Override
    public void execute(IMatrix matrix) {
        for (int row = 0; row < IMatrix.SIZE; row++) {
            for (int col = 0; col < IMatrix.SIZE; col++) {
                // candidate for pivot
                if (matrix.getCandidates(row, col).size() == 2) {
                    System.out.println("Pivot in: " + row + "," + col);
                    for (int c = 0; c < IMatrix.SIZE; c++) {
                        if (c == col) continue;

                        if (matrix.getCandidates(row, c).size() == 2 && !Collections.disjoint(matrix.getCandidates(row, col), matrix.getCandidates(row, c))) {
                            for (int r = 0; r < IMatrix.SIZE; r++) {
                                if (r == row) continue;

                                if (matrix.getCandidates(r, col).size() == 2 && !Collections.disjoint(matrix.getCandidates(row, col), matrix.getCandidates(r, col))) {
                                    eliminateCandidate(matrix, new Pair(row, col), new Pair(r, c));
                                }
                            }

                            for (int rb = row / 3 * BLOCK_SIZE; rb < (row / 3 + 1) * BLOCK_SIZE; rb++) {
                                for (int cb = col / 3 * BLOCK_SIZE; cb < (col / 3 + 1) * BLOCK_SIZE; cb++) {
                                    if (rb == row && cb == col) {
                                        continue;
                                    }
                                    if (matrix.getCandidates(rb, cb).size() == 2 && !Collections.disjoint(matrix.getCandidates(row, col), matrix.getCandidates(rb, cb))) {
                                        eliminateCandidate(matrix, new Pair(row, col), new Pair(rb, cb));
                                    }
                                }
                            }

                        }
                    }

                    for (int r = 0; r < IMatrix.SIZE; r++) {
                        if (r == row) continue;

                        if (matrix.getCandidates(r, col).size() == 2 && !Collections.disjoint(matrix.getCandidates(row, col), matrix.getCandidates(r, col))) {
                            for (int c = 0; c < IMatrix.SIZE; c++) {
                                if (c == col) continue;

                                if (matrix.getCandidates(row, c).size() == 2 && !Collections.disjoint(matrix.getCandidates(row, col), matrix.getCandidates(row, c))) {
                                    eliminateCandidate(matrix, new Pair(row, col), new Pair(r, c));
                                }
                            }

                            for (int rb = row / 3 * BLOCK_SIZE; rb < (row / 3 + 1) * BLOCK_SIZE; rb++) {
                                for (int cb = col / 3 * BLOCK_SIZE; cb < (col / 3 + 1) * BLOCK_SIZE; cb++) {
                                    if (rb == row && cb == col) {
                                        continue;
                                    }
                                    if (matrix.getCandidates(rb, cb).size() == 2 && !Collections.disjoint(matrix.getCandidates(row, col), matrix.getCandidates(rb, cb))) {
                                        eliminateCandidate(matrix, new Pair(row, col), new Pair(rb, cb));
                                    }
                                }
                            }

                        }
                    }

                    for (int rb = row / BLOCK_SIZE * BLOCK_SIZE; rb < (row / BLOCK_SIZE + 1) * BLOCK_SIZE; rb++) {
                        for (int cb = col / BLOCK_SIZE * BLOCK_SIZE; cb < (col / BLOCK_SIZE + 1) * BLOCK_SIZE; cb++) {
                            if (rb == row && cb == col) {
                                continue;
                            }

                            if (matrix.getCandidates(rb, cb).size() == 2 && !Collections.disjoint(matrix.getCandidates(row, col), matrix.getCandidates(rb, cb))) {

                                for (int c = 0; c < IMatrix.SIZE; c++) {
                                    if (c == col) continue;

                                    if (matrix.getCandidates(row, c).size() == 2 && !Collections.disjoint(matrix.getCandidates(row, col), matrix.getCandidates(row, c))) {
                                        eliminateCandidate(matrix, new Pair(row, col), new Pair(rb, cb));
                                    }
                                }

                                for (int r = 0; r < IMatrix.SIZE; r++) {
                                    if (r == row) continue;

                                    if (matrix.getCandidates(r, col).size() == 2 && !Collections.disjoint(matrix.getCandidates(row, col), matrix.getCandidates(r, col))) {
                                        eliminateCandidate(matrix, new Pair(row, col), new Pair(r, cb));
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
