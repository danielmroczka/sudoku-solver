package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Pair;
import com.labs.dm.sudoku.solver.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.labs.dm.sudoku.solver.utils.Utils.compare;

/**
 * XY Wing implementation
 *
 * Created by daniel on 2016-02-21.
 */
public class YWing implements IAlgorithm {

    private void eliminateCandidate(IMatrix matrix, Pair pivot, Pair pair1, Pair pair2) {
        int candidate = candidateToEliminate(matrix, pair1.getRow(), pair1.getCol(), pair2.getRow(), pair2.getCol());

        if (candidate < 1) {
            return;
        }
        for (Pair neighbor : Utils.intersection(pair1, pair2)) {
            if (pivot.equals(neighbor)) continue;
            if (matrix.getCandidates(neighbor.getRow(), neighbor.getCol()).contains(candidate)) {
                System.out.println("Eliminate at " + neighbor + " candidate=" + candidate);
                System.out.println("\tPivot " + pivot + " cell1=" + pair1 + " cell2=" + pair2);
                matrix.getCandidates(neighbor.getRow(), neighbor.getCol()).remove(candidate);
            }
        }
    }

    private int candidateToEliminate(IMatrix matrix, int row, int col, int row2, int col2) {
        List<Integer> common = new ArrayList<>(matrix.getCandidates(row, col));
        common.retainAll(matrix.getCandidates(row2, col2));
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
                    Pair pivot = new Pair(row, col);
                    for (int c = 0; c < IMatrix.SIZE; c++) {
                        if (c == col) continue;
                        if (matrix.getCandidates(row, c).size() == 2 && !Collections.disjoint(matrix.getCandidates(row, col), matrix.getCandidates(row, c))) {
                            for (int r = 0; r < IMatrix.SIZE; r++) {
                                if (r == row) continue;

                                if (matrix.getCandidates(r, col).size() == 2 && !Collections.disjoint(matrix.getCandidates(r, col), matrix.getCandidates(row, col))) {
                                    if (compare(matrix.getCandidates(row, col), matrix.getCandidates(r, col), matrix.getCandidates(row, c))) {
                                        eliminateCandidate(matrix, pivot, new Pair(row, c), new Pair(r, col));
                                    }
                                }
                            }

                            for (int rb : Utils.it(row)) {
                                for (int cb : Utils.it(col)) {
                                    if (rb == row && cb == col) {
                                        continue;
                                    }
                                    //if (matrix.getCandidates(rb, cb).size() == 2 && !Collections.disjoint(matrix.getCandidates(row, c), matrix.getCandidates(rb, cb))) {
                                    if (compare(matrix.getCandidates(row, col), matrix.getCandidates(row, c), matrix.getCandidates(rb, cb))) {
                                        eliminateCandidate(matrix, pivot, new Pair(row, c), new Pair(rb, cb));
                                    }
                                    //  }
                                }
                            }

                        }
                    }

                    for (int r = 0; r < IMatrix.SIZE; r++) {
                        if (r == row) continue;

                        if (matrix.getCandidates(r, col).size() == 2 && !Collections.disjoint(matrix.getCandidates(row, col), matrix.getCandidates(r, col))) {

                            for (int c = 0; c < IMatrix.SIZE; c++) {
                                if (c == col) continue;

                                if (compare(matrix.getCandidates(row, col), matrix.getCandidates(r, col), matrix.getCandidates(row, c))) {
                                    eliminateCandidate(matrix, pivot, new Pair(row, c), new Pair(r, col));
                                }
                            }

                            for (int rb : Utils.it(row)) {
                                for (int cb : Utils.it(col)) {
                                    if (rb == row && cb == col) {
                                        continue;
                                    }
                                    //  if (matrix.getCandidates(rb, cb).size() == 2 && !Collections.disjoint(matrix.getCandidates(row, col), matrix.getCandidates(rb, cb))) {
                                    if (compare(matrix.getCandidates(row, col), matrix.getCandidates(r, col), matrix.getCandidates(rb, cb))) {
                                        eliminateCandidate(matrix, pivot, new Pair(r, col), new Pair(rb, cb));
                                    }
                                    //  }
                                }
                            }

                        }
                    }


                    /*for (int rb : Utils.it(row)) {
                        for (int cb : Utils.it(col)) {
                            if (rb == row && cb == col) {
                                continue;
                            }

                            if (matrix.getCandidates(rb, cb).size() == 2 && !Collections.disjoint(matrix.getCandidates(row, col), matrix.getCandidates(rb, cb))) {

                                for (int c = 0; c < IMatrix.SIZE; c++) {
                                    if (c == col) continue;

                                    if (matrix.getCandidates(row, c).size() == 2 && !Collections.disjoint(matrix.getCandidates(row, col), matrix.getCandidates(row, c))) {
                                        if (compare(matrix.getCandidates(row, col), matrix.getCandidates(rb, cb), matrix.getCandidates(row, c))) {
                                            eliminateCandidate(matrix, pivot, new Pair(rb, cb), new Pair(rb, c));
                                        }
                                    }
                                }


                                for (int r = 0; r < IMatrix.SIZE; r++) {
                                    if (r == row) continue;

                                    if (matrix.getCandidates(r, col).size() == 2 && !Collections.disjoint(matrix.getCandidates(row, col), matrix.getCandidates(r, col))) {
                                        if (compare(matrix.getCandidates(row, col), matrix.getCandidates(r, col), matrix.getCandidates(row, c))) {
                                            eliminateCandidate(matrix, pivot, new Pair(rb, cb), new Pair(r, cb));
                                        }
                                    }
                                }
                            }
                        }
                    }*/
                }
            }
        }
    }
}
