package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

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
        fill(matrix, 3);

        //WHEN
        int cnt = matrix.getCandidatesCount();
        wing.execute(matrix);
        //THEN
        assertEquals(0, matrix.getCandidates(0, 0).size());
        assertEquals(0, matrix.getCandidates(0, 2).size());
        assertEquals(cnt - 5, matrix.getCandidatesCount());
    }

    @Test
    public void theSameBlock() {

        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{1, 2, 3});
        matrix.addCandidates(2, 1, new Integer[]{1, 3});
        matrix.addCandidates(1, 2, new Integer[]{2, 3});
        fill(matrix, 3);
        //WHEN
        int cnt = matrix.getCandidatesCount();
        wing.execute(matrix);
        //THEN
        assertEquals(cnt - 6, matrix.getCandidatesCount());
        assertEquals(0, matrix.getCandidates(1, 1).size());
        assertEquals(0, matrix.getCandidates(2, 2).size());
    }

    @Test
    public void testTheSameRowBlock() {

        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{1, 2, 3});
        matrix.addCandidates(0, 4, new Integer[]{2, 3});
        fill(matrix, 3);
        matrix.setCandidates(2, 1, new HashSet<Integer>(Arrays.asList(new Integer[]{1, 3})));
        //WHEN
        int candidates = matrix.getCandidatesCount();
        wing.execute(matrix);
        //THEN
        assertEquals(candidates - 5, matrix.getCandidatesCount());
    }

    @Test
    public void testTheSameRow() {

        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{1, 2, 3});
        matrix.addCandidates(0, 2, new Integer[]{2, 3});

        fill(matrix, 3);
        matrix.setCandidates(0, 4, new HashSet<Integer>(Arrays.asList(new Integer[]{1, 3})));
        int candidates = matrix.getCandidatesCount();
        //WHEN

        wing.execute(matrix);
        //THEN
        assertEquals(candidates - 6, matrix.getCandidatesCount());
    }

    private void fill(IMatrix matrix, int value) {
        for (int row = 0; row < IMatrix.SIZE; row++) {
            for (int col = 0; col < IMatrix.SIZE; col++) {
                matrix.addCandidates(row, col, new Integer[]{value});
            }
        }
    }
}