package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Pair;
import com.labs.dm.sudoku.solver.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.labs.dm.sudoku.solver.core.IMatrix.SIZE;
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
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (matrix.getCandidates(row, col).size() == pivotLength) {
                    Pair pivot = new Pair(row, col);
                    List<Pair> pincets = new ArrayList<>();

                    for (int c = 0; c < SIZE; c++) {
                        if (c != col && acceptPincet(matrix.getCandidates(pivot), matrix.getCandidates(row, c), pivotLength)) {
                            pincets.add(new Pair(row, c));
                        }
                    }

                    for (int r = 0; r < SIZE; r++) {
                        if (r != row && acceptPincet(matrix.getCandidates(pivot), matrix.getCandidates(r, col), pivotLength)) {
                            pincets.add(new Pair(r, col));
                        }
                    }

                    for (int rb : Utils.blockElems(row)) {
                        for (int cb : Utils.blockElems(col)) {
                            if (rb != row && cb != col && acceptPincet(matrix.getCandidates(pivot), matrix.getCandidates(rb, cb), pivotLength)) {
                                pincets.add(new Pair(rb, cb));
                            }
                        }
                    }

                    // eliminate pincets
                    for (int i = 0; i < pincets.size(); i++) {
                        for (int j = i + 1; j < pincets.size(); j++) {
                            if (acceptPivotAndPincets(matrix, pivot, pincets.get(i), pincets.get(j), pivotLength)) {
                                eliminateCandidate(matrix, pivot, pincets.get(i), pincets.get(j));
                            }
                        }
                    }
                }
            }
        }
    }

    private void eliminateCandidate(IMatrix matrix, Pair pivot, Pair pair1, Pair pair2) {
        int candidate = candidateToEliminate(matrix, pair1, pair2);

        if (candidate < 1) {
            return;
        }
        for (Pair neighbor : pairsOnIntersections(pair1, pair2)) {
            if (!pivot.equals(neighbor) && matrix.getCandidates(neighbor).contains(candidate)) {
                matrix.removeCandidate(neighbor.getRow(), neighbor.getCol(), candidate);
            }
        }
    }

    private int candidateToEliminate(IMatrix matrix, Pair pair1, Pair pair2) {
        List<Integer> common = new ArrayList<>(matrix.getCandidates(pair1));
        common.retainAll(matrix.getCandidates(pair2));
        return (common.size() == 1) ? common.get(0) : 0;
    }

}

