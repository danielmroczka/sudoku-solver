/*
 * Copyright Daniel Mroczka. All rights reserved.
 */

package com.labs.dm.sudoku.solver.alg.hidden;

import com.labs.dm.sudoku.solver.alg.IAlgorithm;
import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Daniel Mroczka
 */
public class HiddenSinglesTest {

    @Test
    public void testExecute() {
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.setValueAt(0, 1, 6);
        matrix.setValueAt(0, 3, 4);
        matrix.setValueAt(0, 4, 2);
        matrix.setValueAt(0, 7, 8);
        matrix.setValueAt(0, 8, 9);

        matrix.addCandidates(0, 0, new Integer[]{7, 5, 3});
        matrix.addCandidates(0, 2, new Integer[]{1, 3});
        matrix.addCandidates(0, 5, new Integer[]{1, 5});
        matrix.addCandidates(0, 6, new Integer[]{1, 5, 3});

        IAlgorithm hiddenSingles = new HiddenSingles();
        //WHEN
        hiddenSingles.execute(matrix);
        //THEN
        assertEquals(7, matrix.getValueAt(0, 0));
        assertTrue(matrix.validate());
    }

    @Test
    public void testExecuteBlock() {
        //GIVEN
        IMatrix matrix = new Matrix();
        for (int row = 0; row < Matrix.SIZE; row++) {
            for (int col = 0; col < Matrix.SIZE; col++) {
                matrix.addCandidates(row, col, new Integer[]{1, 2, 3});
            }
        }

        matrix.addCandidates(4, 4, new Integer[]{4});

        int resolved = matrix.getSolvedItems();
        IAlgorithm hiddenSingles = new HiddenSingles();
        //WHEN
        hiddenSingles.execute(matrix);
        //THEN
        assertEquals(resolved + 1, matrix.getSolvedItems());
        assertTrue(matrix.validate());
    }

}

