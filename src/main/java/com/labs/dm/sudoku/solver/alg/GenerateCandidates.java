/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.labs.dm.sudoku.solver.core.Matrix.BLOCK_SIZE;
import static com.labs.dm.sudoku.solver.core.Matrix.SIZE;

/**
 * Generates all possible candidates for each cell without value.
 * It takes into account surroundings cells with already set value and removes them from the list.
 *
 * @author Daniel Mroczka
 */
public class GenerateCandidates implements IAlgorithm {

    private static final List<Integer> fullSet = Utils.FULL_LIST;

    @Override
    public void execute(IMatrix matrix) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (matrix.isCellSet(row, col)) {
                    matrix.setCandidates(row, col, new ArrayList<>());
                    continue;
                }
                List<Integer> set = new ArrayList<>(GenerateCandidates.fullSet);

                int[] cols = matrix.getElemsInCol(col);
                int[] rows = matrix.getElemsInRow(row);
                int[] blocks = matrix.getElemsInBlock(row / BLOCK_SIZE, col / BLOCK_SIZE);

                for (int i : cols) {
                    set.remove((Integer) i);
                }
                for (int i : rows) {
                    set.remove((Integer) i);
                }
                for (int i : blocks) {
                    set.remove((Integer) i);
                }

                matrix.setCandidates(row, col, set);
            }
        }
    }
}
