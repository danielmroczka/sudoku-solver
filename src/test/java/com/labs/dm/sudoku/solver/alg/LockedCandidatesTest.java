package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by Daniel Mroczka on 2016-02-15.
 */
public class LockedCandidatesTest {

    private final IAlgorithm reduction = new Reduction();

    @Test
    public void lockedInRowReduceInBlock() throws Exception {
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{1, 2});
        matrix.addCandidates(0, 1, new Integer[]{1, 2, 3});
        matrix.addCandidates(0, 2, new Integer[]{2, 3, 4});
        matrix.addCandidates(0, 3, new Integer[]{2, 3, 4, 5});

        matrix.addCandidates(1, 1, new Integer[]{1, 4, 5, 6});
        reduction.execute(matrix);
        assertFalse(matrix.getCandidates(1, 1).contains(1));
    }

    @Test
    public void lockedInColReduceInBlock() throws Exception {
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{1, 2});
        matrix.addCandidates(1, 0, new Integer[]{1, 2, 3});
        matrix.addCandidates(2, 0, new Integer[]{2, 3, 4});
        matrix.addCandidates(3, 0, new Integer[]{2, 3, 4, 5});

        matrix.addCandidates(1, 1, new Integer[]{1, 4, 5, 6});
        reduction.execute(matrix);
        assertFalse(matrix.getCandidates(1, 1).contains(1));
    }

    @Test
    public void simple2() throws Exception {
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{4, 6});
        matrix.addCandidates(0, 1, new Integer[]{1, 4, 5, 6});
        matrix.addCandidates(0, 4, new Integer[]{1, 2, 6});

        matrix.addCandidates(1, 2, new Integer[]{1, 4, 5, 6});
        matrix.addCandidates(2, 1, new Integer[]{1, 4, 6, 9});
        matrix.addCandidates(2, 2, new Integer[]{1, 4, 6, 9});

        matrix.addCandidates(3, 0, new Integer[]{1, 5, 6});
        matrix.addCandidates(3, 1, new Integer[]{1, 5, 6});
        matrix.addCandidates(3, 2, new Integer[]{1, 5, 6});
        matrix.addCandidates(3, 3, new Integer[]{1, 5, 6});

        matrix.addCandidates(1, 4, new Integer[]{1, 5, 6});
        matrix.addCandidates(2, 4, new Integer[]{1, 5, 6});
        matrix.addCandidates(3, 4, new Integer[]{1, 5, 6});
        int before = matrix.getCandidatesCount();
        reduction.execute(matrix);
        // assertEquals(23, before - matrix.getCandidatesCount());
    }

    @Test
    public void execute2() throws Exception {
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{4, 6});
        matrix.addCandidates(0, 1, new Integer[]{1, 4, 5, 6});
        matrix.addCandidates(0, 4, new Integer[]{6});

        matrix.addCandidates(1, 0, new Integer[]{4, 6});
        matrix.addCandidates(2, 2, new Integer[]{4, 6});

        matrix.addCandidates(1, 4, new Integer[]{4, 6});
        matrix.addCandidates(2, 4, new Integer[]{4, 6});
        matrix.addCandidates(3, 0, new Integer[]{4, 6});
        matrix.addCandidates(3, 1, new Integer[]{4, 6});

        int before = matrix.getCandidatesCount();
        reduction.execute(matrix);

        assertEquals(0, matrix.getCandidates(1, 0).size());
        assertEquals(0, matrix.getCandidates(2, 2).size());

        assertEquals(0, matrix.getCandidates(1, 4).size());
        assertEquals(0, matrix.getCandidates(2, 4).size());
        assertEquals(0, matrix.getCandidates(3, 0).size());
        assertEquals(0, matrix.getCandidates(3, 1).size());

        assertEquals(17, before - matrix.getCandidatesCount());
    }

    @Test
    public void execute4() throws Exception {
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{4, 6});
        matrix.addCandidates(1, 0, new Integer[]{1, 4, 5, 6});
        matrix.addCandidates(4, 0, new Integer[]{6});

        matrix.addCandidates(0, 1, new Integer[]{4, 6});
        matrix.addCandidates(2, 2, new Integer[]{4, 6});

        matrix.addCandidates(4, 1, new Integer[]{4, 6});
        matrix.addCandidates(4, 2, new Integer[]{4, 6});
        matrix.addCandidates(0, 3, new Integer[]{4, 6});
        matrix.addCandidates(1, 3, new Integer[]{4, 6});

        int before = matrix.getCandidatesCount();
        reduction.execute(matrix);

        assertEquals(0, matrix.getCandidates(0, 1).size());
        assertEquals(0, matrix.getCandidates(2, 2).size());

        assertEquals(0, matrix.getCandidates(4, 1).size());
        assertEquals(0, matrix.getCandidates(4, 2).size());
        assertEquals(0, matrix.getCandidates(0, 3).size());
        assertEquals(0, matrix.getCandidates(1, 3).size());

        assertEquals(17, before - matrix.getCandidatesCount());
    }

    @Test
    @Ignore
    public void execute3() throws Exception {
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{4, 6});
        matrix.addCandidates(0, 1, new Integer[]{1, 4, 5, 6});
        matrix.addCandidates(0, 2, new Integer[]{4, 6});
        matrix.addCandidates(0, 3, new Integer[]{6});

        matrix.addCandidates(1, 0, new Integer[]{4, 6});
        matrix.addCandidates(1, 1, new Integer[]{4, 6});
        matrix.addCandidates(1, 2, new Integer[]{4, 6});
        matrix.addCandidates(2, 0, new Integer[]{4, 6});
        matrix.addCandidates(2, 1, new Integer[]{4, 6});
        matrix.addCandidates(2, 2, new Integer[]{4, 6});
        matrix.addCandidates(0, 4, new Integer[]{6});
        matrix.addCandidates(1, 4, new Integer[]{4, 6});
        matrix.addCandidates(2, 4, new Integer[]{4, 6});

        int before = matrix.getCandidatesCount();
        reduction.execute(matrix);
        assertEquals(6, before - matrix.getCandidatesCount());
    }

    @Test
    public void testPointingInRow() throws Exception {
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{1, 2});
        matrix.addCandidates(0, 1, new Integer[]{1, 2, 3});
        matrix.addCandidates(0, 2, new Integer[]{2, 3, 4});
        matrix.addCandidates(1, 0, new Integer[]{2, 3, 4, 5});
        matrix.addCandidates(0, 4, new Integer[]{1, 4, 5, 6});
        matrix.addCandidates(1, 4, new Integer[]{1, 4, 5, 6});
        matrix.addCandidates(0, 7, new Integer[]{1, 4, 5, 6});
        matrix.addCandidates(1, 7, new Integer[]{1, 4, 5, 6});
        //WHEN
        reduction.execute(matrix);
        //THEN
        assertFalse(matrix.getCandidates(0, 4).contains(1));
        assertFalse(matrix.getCandidates(0, 7).contains(1));
    }

    @Test
    public void testPointingInCol() throws Exception {
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{1, 2});
        matrix.addCandidates(1, 0, new Integer[]{1, 2, 3});
        matrix.addCandidates(2, 0, new Integer[]{2, 3, 4});
        matrix.addCandidates(0, 1, new Integer[]{2, 3, 4, 5});
        matrix.addCandidates(4, 0, new Integer[]{1, 4, 5, 6});
        matrix.addCandidates(4, 1, new Integer[]{1, 4, 5, 6});
        matrix.addCandidates(7, 0, new Integer[]{1, 4, 5, 6});
        matrix.addCandidates(7, 1, new Integer[]{1, 4, 5, 6});
        //WHEN
        reduction.execute(matrix);
        //THEN
        assertFalse(matrix.getCandidates(4, 0).contains(1));
        assertFalse(matrix.getCandidates(7, 0).contains(1));
    }


    @Test
    public void testClaimingRows() throws Exception {
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{1, 2});
        matrix.addCandidates(0, 1, new Integer[]{1, 2, 3});
        matrix.addCandidates(0, 2, new Integer[]{1, 2, 3, 4});

        matrix.addCandidates(0, 3, new Integer[]{1, 2, 3, 4, 5});
        matrix.addCandidates(0, 6, new Integer[]{1, 2, 4, 5, 6});
        //WHEN
        reduction.execute(matrix);
        //THEN
        assertFalse(matrix.getCandidates(0, 3).contains(1));
        assertFalse(matrix.getCandidates(0, 6).contains(1));
        assertFalse(matrix.getCandidates(0, 3).contains(2));
        assertFalse(matrix.getCandidates(0, 6).contains(2));
    }

    @Test
    public void testClaimingCols() throws Exception {
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{1, 2});
        matrix.addCandidates(1, 0, new Integer[]{1, 2, 3});
        matrix.addCandidates(2, 0, new Integer[]{1, 2, 3, 4});

        matrix.addCandidates(3, 0, new Integer[]{1, 2, 3, 4, 5});
        matrix.addCandidates(6, 0, new Integer[]{1, 2, 4, 5, 6});
        //WHEN
        reduction.execute(matrix);
        //THEN
        assertFalse(matrix.getCandidates(3, 0).contains(1));
        assertFalse(matrix.getCandidates(6, 0).contains(1));
        assertFalse(matrix.getCandidates(3, 0).contains(2));
        assertFalse(matrix.getCandidates(6, 0).contains(2));
    }
}