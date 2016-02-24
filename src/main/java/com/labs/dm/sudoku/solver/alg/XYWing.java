package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Pair;
import com.labs.dm.sudoku.solver.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.labs.dm.sudoku.solver.utils.Utils.*;

/**
 * XY Wing implementation
 * <p/>
 * Created by daniel on 2016-02-21.
 */
public class XYWing implements IAlgorithm {

    protected int pivotLength = 2;

    @Override
    public void execute(IMatrix matrix) {
        for (int row = 0; row < IMatrix.SIZE; row++) {
            for (int col = 0; col < IMatrix.SIZE; col++) {
                if (matrix.getCandidates(row, col).size() == pivotLength) {
                    Pair pivot = new Pair(row, col);
                    List<Pair> pincets = new ArrayList<>();

                    for (int c = 0; c < IMatrix.SIZE; c++) {
                        if (c != col && accept(matrix.getCandidates(row, c), matrix.getCandidates(row, col))) {
                            pincets.add(new Pair(row, c));
                        }
                    }

                    for (int r = 0; r < IMatrix.SIZE; r++) {
                        if (r != row && accept(matrix.getCandidates(r, col), matrix.getCandidates(row, col))) {
                            pincets.add(new Pair(r, col));
                        }
                    }

                    for (int rb : Utils.it(row)) {
                        for (int cb : Utils.it(col)) {
                            if (rb != row && cb != col && accept(matrix.getCandidates(rb, cb), matrix.getCandidates(row, col))) {
                                pincets.add(new Pair(rb, cb));
                            }
                        }
                    }

                    // eliminate pincets
                    for (int i = 0; i < pincets.size(); i++) {
                        for (int j = i; j < pincets.size(); j++) {
                            if (compare(matrix, pivot, pincets.get(i), pincets.get(j))) {
                                eliminateCandidate(matrix, pivot, pincets.get(i), pincets.get(j));
                            }
                        }
                    }
                }
            }
        }
    }

    private void eliminateCandidate(IMatrix matrix, Pair pivot, Pair pair1, Pair pair2) {
        int candidate = candidateToEliminate(matrix, pair1.getRow(), pair1.getCol(), pair2.getRow(), pair2.getCol());

        if (candidate < 1) {
            return;
        }
        for (Pair neighbor : intersection(pair1, pair2)) {
            if (!pivot.equals(neighbor) && matrix.getCandidates(neighbor).contains(candidate)) {
                //System.out.println("Eliminate at " + neighbor + " candidate=" + candidate);
                //System.out.println("\tPivot " + pivot + " cell1=" + pair1 + " cell2=" + pair2);
                matrix.getCandidates(neighbor).remove(candidate);
            }
        }
    }

    private int candidateToEliminate(IMatrix matrix, int row, int col, int row2, int col2) {
        List<Integer> common = new ArrayList<>(matrix.getCandidates(row, col));
        common.retainAll(matrix.getCandidates(row2, col2));
        return (common.size() == 1) ? common.get(0) : 0;
    }

}

