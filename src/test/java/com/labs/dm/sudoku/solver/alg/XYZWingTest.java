package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by daniel on 24-Feb-16.
 */
public class XYZWingTest {

    private XYZWing wing = new XYZWing();

    @Test
    public void simpleTest() {

        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 1, new Integer[]{1, 2, 3});
        matrix.addCandidates(0, 4, new Integer[]{2, 3});
        matrix.addCandidates(1, 2, new Integer[]{1, 3});
        matrix.addCandidates(0, 0, new Integer[]{3});
        matrix.addCandidates(0, 2, new Integer[]{3});
        //WHEN
        wing.execute(matrix);
        //THEN
        assertEquals(0, matrix.getCandidates(0, 0).size());
        assertEquals(0, matrix.getCandidates(0, 2).size());
    }
}