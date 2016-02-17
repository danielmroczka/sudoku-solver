/*
 * Copyright Daniel Mroczka. All rights reserved.
 */

package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author daniel
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
        matrix.setValueAt(3, 3, 6);
        matrix.setValueAt(3, 4, 4);
        matrix.setValueAt(3, 5, 2);
        matrix.setValueAt(4, 3, 8);
        matrix.setValueAt(4, 4, 9);

        matrix.addCandidates(4, 5, new Integer[]{7, 5, 3});
        matrix.addCandidates(5, 3, new Integer[]{1, 3});
        matrix.addCandidates(5, 4, new Integer[]{1, 5});
        matrix.addCandidates(5, 5, new Integer[]{1, 5, 3});

        IAlgorithm hiddenSingles = new HiddenSingles();
        //WHEN
        hiddenSingles.execute(matrix);
        //THEN
        //assertEquals(7, matrix.getValueAt(4,5));
        assertTrue(matrix.validate());
    }

}
