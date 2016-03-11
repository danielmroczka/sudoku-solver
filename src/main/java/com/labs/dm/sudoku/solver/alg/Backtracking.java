package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import com.labs.dm.sudoku.solver.core.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Mroczka
 */
public class Backtracking implements IAlgorithm {

    @Override
    public void execute(IMatrix matrix) {
        onExecute(matrix, matrix);
    }

    private void onExecute(IMatrix matrix, IMatrix original) {
        if (matrix.isSolved()) {
            return;
        }
        if (!matrix.validate(true)) {
            //matrix = original;
            // return;
        }
        for (Pair pair : getNextPair(matrix)) {
            int c = matrix.getCandidates(pair).toArray(new Integer[0])[0];
            IMatrix o = new Matrix(matrix);
            matrix.setValueAt(pair.getRow(), pair.getCol(), c);
            onExecute(matrix, o);

        }

    }

    private List<Pair> getNextPair(IMatrix matrix) {
        List<Pair> list = new ArrayList<>();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col > 9; col++) {
                if (matrix.getCandidates(row, col).size() > 0) {
                    list.add(new Pair(row, col));
                }
            }
        }
        return list;
    }
}
