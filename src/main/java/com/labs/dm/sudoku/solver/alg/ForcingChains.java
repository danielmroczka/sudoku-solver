package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import com.labs.dm.sudoku.solver.core.Pair;
import com.labs.dm.sudoku.solver.utils.CounterHashMap;
import com.labs.dm.sudoku.solver.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 2016-03-09.
 */
public class ForcingChains implements IAlgorithm {

    @Override
    public void execute(IMatrix matrix) {
        for (int row = 0; row < IMatrix.SIZE; row++) {
            for (int col = 0; col < IMatrix.SIZE; col++) {
                if (matrix.getCandidates(row, col).size() < 2) {
                    continue;
                }

                IMatrix[] clones = new IMatrix[matrix.getCandidates(row, col).size()];
                int index = 0;

                for (int candidate : matrix.getCandidates(row, col)) {
                    clones[index] = new Matrix((Matrix) matrix);
                    List<Integer> l = new ArrayList<>((clones[index].getCandidates(row, col)));
                    l.remove((Integer) candidate);
                    clones[index].setValueAt(row, col, candidate);
                    //propagateChange(clones[index], row, col, l.get(0));
                    removeOneCandidate(clones[index]);
                    //System.out.println(clones[index].printCandidates());
                    index++;
                }

                for (int r = 0; r < IMatrix.SIZE; r++) {
                    for (int c = 0; c < IMatrix.SIZE; c++) {
                        if (matrix.getValueAt(r, c) == 0) {
                            boolean theSame = true;
                            for (int id = 0; id < clones.length - 1; id++) {
                                if (clones[id].getValueAt(r, c) == 0) {
                                    theSame = false;
                                    break;
                                }
                                if (clones[id].getValueAt(r, c) != clones[id + 1].getValueAt(r, c)) {
                                    theSame = false;
                                    break;
                                }
                            }

                            if (theSame) {
                                matrix.setValueAt(r, c, clones[0].getValueAt(r, c));
                            }
                        }
                    }
                }
            }
        }
    }

    private void propagateChange(IMatrix clone, int row, int col, int value) {
        for (Pair pair : Utils.getSurroundings(row, col)) {
            int cnt = 0;
            for (Pair n : Utils.getSurroundings(pair.getRow(), pair.getCol())) {
                if (clone.getCandidates(n).contains(value)) {
                    cnt++;
                }
            }
            if (cnt == 0) {

                if (clone.getCandidates(pair).contains(value)) {
                    clone.setValueAt(pair.getRow(), pair.getCol(), value);
                }
            }
        }
    }

    private void removeOneCandidate(IMatrix matrix) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                CounterHashMap<Integer> map = new CounterHashMap<>();
                for (Pair pair : Utils.getSurroundings(row, col)) {
                    for (int cand : matrix.getCandidates(pair)) {
                        map.inc(cand);
                    }
                }
                List<Integer> toRemove = new ArrayList<>();
                for (int cand : matrix.getCandidates(row, col)) {
                    if (map.get(cand) == null) {
                        toRemove.add(cand);
                    }
                }

                matrix.removeCandidate(row, col, toRemove);
            }
        }
    }
}
