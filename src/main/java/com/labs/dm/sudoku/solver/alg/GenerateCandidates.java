/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.labs.dm.sudoku.solver.core.IMatrix.BLOCK_SIZE;
import static com.labs.dm.sudoku.solver.core.IMatrix.SIZE;

/**
 * @author daniel
 */
public class GenerateCandidates implements IAlgorithm {

    private final static List<Integer> fullSet = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

    @Override
    public void execute(IMatrix matrix) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (matrix.isCellSet(row, col)) {
                    matrix.setCandidates(row, col, new HashSet<Integer>());
                    continue;
                }
                Set<Integer> set = new HashSet<>(fullSet);

                int[] cols = matrix.getElemsInCol(col);
                int[] rows = matrix.getElemsInRow(row);
                int[] blocks = matrix.getElemsInBlock(row / BLOCK_SIZE, col / BLOCK_SIZE);

                for (int i : cols) {
                    set.remove(i);
                }
                for (int i : rows) {
                    set.remove(i);
                }
                for (int i : blocks) {
                    set.remove(i);
                }

                matrix.setCandidates(row, col, set);
            }
        }
    }
}
