package com.labs.dm.sudoku.solver.alg.chains;

import com.labs.dm.sudoku.solver.alg.IAlgorithm;
import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;

import static com.labs.dm.sudoku.solver.core.Matrix.SIZE;

/**
 * @author Daniel Mroczka
 */
public class ForcingChains implements IAlgorithm {

    @Override
    public void execute(IMatrix matrix) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (matrix.getCandidates(row, col).size() < 2) {
                    continue;
                }

                /** creates clone instances from matrix and set cell at row and col different value from a candidate list **/
                IMatrix[] clones = new IMatrix[matrix.getCandidates(row, col).size()];
                int index = 0;

                for (int candidate : matrix.getCandidates(row, col)) {
                    /** each clone will have different value cell at the same position **/
                    clones[index] = new Matrix(matrix);
                    clones[index++].setValueAt(row, col, candidate);
                }

                for (int r = 0; r < SIZE; r++) {
                    for (int c = 0; c < SIZE; c++) {
                        if (!matrix.isCellSet(r, c)) {
                            boolean theSame = true;
                            for (int cloneId = 0; cloneId < clones.length - 1; cloneId++) {
                                if (clones[cloneId].getValueAt(r, c) != clones[cloneId + 1].getValueAt(r, c)) {
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
