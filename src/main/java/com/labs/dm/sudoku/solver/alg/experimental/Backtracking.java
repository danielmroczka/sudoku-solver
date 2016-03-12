package com.labs.dm.sudoku.solver.alg.experimental;

import com.labs.dm.sudoku.solver.alg.IAlgorithm;
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
        onExecute(matrix, new Matrix(matrix));
        // System.out.println(matrix.printCandidates());
    }

    private void onExecute(IMatrix matrix, IMatrix original) {
        if (matrix.isSolved()) {
            return;
        }

        for (Pair pair : getNextPair(matrix)) {
            for (int candidate : new ArrayList<>(matrix.getCandidates(pair))) {

                if (matrix.isSolved()) {
                    return;
                }
                IMatrix copy = new Matrix(matrix);
                matrix.setValueAt(pair.getRow(), pair.getCol(), candidate);
                if (matrix.validate(true)) {
                    onExecute(matrix, copy);
                } else {
                    matrix = new Matrix(original);
                }

            }
        }
    }

    private List<Pair> getNextPair(IMatrix matrix) {
        List<Pair> list = new ArrayList<>();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (matrix.getCandidates(row, col).size() > 0) {
                    list.add(new Pair(row, col));
                    //ap.put(new Pair(row, col), matrix.getCandidates(row,col).size());
                }
            }
        }
        return list;
    }
}
