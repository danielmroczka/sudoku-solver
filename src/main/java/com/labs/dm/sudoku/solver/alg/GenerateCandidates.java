/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.utils.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.labs.dm.sudoku.solver.core.Matrix.BLOCK_SIZE;
import static com.labs.dm.sudoku.solver.core.Matrix.SIZE;

/**
 * Generates all possible candidates for each empty cell.
 * It inspects the row, column, and block for the cell and eliminates those numbers from the list of candidates.
 */
public class GenerateCandidates implements IAlgorithm {

    @Override
    public void execute(IMatrix matrix) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (matrix.isCellSet(row, col)) {
                    matrix.setCandidates(row, col, new ArrayList<>());
                } else {
                    matrix.setCandidates(row, col, generateCandidatesForCell(matrix, row, col));
                }
            }
        }
    }

    private List<Integer> generateCandidatesForCell(IMatrix matrix, int row, int col) {
        List<Integer> candidates = new ArrayList<>(Utils.FULL_LIST);
        Set<Integer> presentValues = new HashSet<>();

        for (int val : matrix.getElemsInCol(col)) {
            presentValues.add(val);
        }
        for (int val : matrix.getElemsInRow(row)) {
            presentValues.add(val);
        }
        for (int val : matrix.getElemsInBlock(row / BLOCK_SIZE, col / BLOCK_SIZE)) {
            presentValues.add(val);
        }

        candidates.removeAll(presentValues);
        return candidates;
    }
}
