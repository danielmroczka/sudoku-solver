package com.labs.dm.sudoku.solver.io;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;

public class Generator {

    public static void main(String[] args) {
        new Generator().generate(68);
    }

    public IMatrix generate(int filledCount) {
        if (filledCount < 1 || filledCount > 81) {
            throw new IllegalArgumentException("Incorrect argument. Valid values are between 1 and 81");
        }

        IMatrix matrix = new Matrix();

        int probe = 0;
        while (probe < filledCount) {
            int idx = (int) (Math.random() * 81);
            int row = idx / 9;
            int col = idx % 9;

            if (matrix.getValueAt(row, col) != IMatrix.EMPTY_VALUE) {
                continue;
            }

            int value = (int) (Math.random() * 9) + 1;
            matrix.setValueAt(row, col, value);

            boolean validate = false;
            try {
                validate = matrix.validate();
            } catch (IllegalStateException e) {

            }

            if (validate) {
                //System.out.println("Step=" + probe);
                probe++;
            } else {
                matrix.setValueAt(row, col, IMatrix.EMPTY_VALUE);
            }

        }

        System.out.println(matrix);
        return matrix;
    }


}
