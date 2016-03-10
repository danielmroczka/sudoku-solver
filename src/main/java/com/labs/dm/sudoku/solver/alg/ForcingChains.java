package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Mroczka
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
                    clones[index] = new Matrix(matrix);
                    List<Integer> l = new ArrayList<>((clones[index].getCandidates(row, col)));
                    l.remove((Integer) candidate);
                    clones[index].setValueAt(row, col, candidate);
                    new HiddenSingles().execute(clones[index]);
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
}
