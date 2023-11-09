package com.labs.dm.sudoku.solver.io;

import com.labs.dm.sudoku.solver.alg.GenerateCandidates;
import com.labs.dm.sudoku.solver.alg.IAlgorithm;
import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Generator {

    public static void main(String[] args) {
        new Generator().generateNew(77);
    }


    public IMatrix generateNew(int filledCount) {
        IMatrix matrix = new Matrix();
        IAlgorithm candidate = new GenerateCandidates();
        candidate.execute(matrix);

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                IMatrix clone = new Matrix(matrix);
                if (matrix.getValueAt(row, col) == 0) {
                    List<Integer> candidates = matrix.getCandidates(row, col);
                    for (int i = 0; i < candidates.size(); i++) {
                        int index = ThreadLocalRandom.current().nextInt(candidates.size());
                        int value = candidates.get(index);
                        clone.setValueAt(row, col, value);
                        if (clone.validate(true)) {
                            matrix.setValueAt(row, col, value);
                            break;
                        } else {
                            clone.setValueAt(row, col, Matrix.EMPTY_VALUE);
                            candidates.remove(index);
                        }
                    }
                }
            }
        }

        System.out.println(matrix);

        while (matrix.getSolvedItems() > filledCount) {
            int row = ThreadLocalRandom.current().nextInt(9);
            int col = ThreadLocalRandom.current().nextInt(9);
            if (matrix.getValueAt(row, col) != Matrix.EMPTY_VALUE) {
                matrix.setValueAt(row, col, Matrix.EMPTY_VALUE);
            }
        }
        // System.out.println(matrix.validate());
        System.out.println(matrix);
        System.out.println(matrix.validate());
        return matrix;
    }

    public IMatrix generate(int filledCount) {
        if (filledCount < 1 || filledCount > 81) {
            throw new IllegalArgumentException("Incorrect argument. Valid values are between 1 and 81");
        }

        IMatrix matrix = new Matrix();
        Random rand = new Random();
        IAlgorithm candidate = new GenerateCandidates();
        candidate.execute(matrix);


        //   int probe = 0;
        while (matrix.getSolvedItems() < filledCount) {
            //int idx = rand.nextInt(81);
            int row = rand.nextInt(9);
            int col = rand.nextInt(9);

            if (matrix.getValueAt(row, col) != Matrix.EMPTY_VALUE) {
                continue;
            }

            IMatrix clone = new Matrix(matrix);
            int value;


            List<Integer> candidates = matrix.getCandidates(row, col);
            assert (candidates.size() > 0);
            for (int i = 0; i < candidates.size(); i++) {
                int index = rand.nextInt(candidates.size());
                value = candidates.get(index);
                clone.setValueAt(row, col, value);
                boolean validate = clone.validate(true);

                if (validate) {

                    // probe++;
                    matrix.setValueAt(row, col, value);
                    // System.out.println(matrix);
                    break;
                } else {
                    candidates.remove(index);
                }
            }


        }

        System.out.println(matrix);
        return matrix;
    }


}
