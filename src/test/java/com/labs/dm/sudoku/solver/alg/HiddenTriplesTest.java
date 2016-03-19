package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Daniel Mroczka on 2016-02-19.
 */
public class HiddenTriplesTest {
    private final HiddenTriples hiddenTriples = new HiddenTriples();

    @Test
    public void shouldFoundTripleInSmallBlock() {
        //GIVEN
        IMatrix matrix = new Matrix();

        matrix.addCandidates(0, 0, new Integer[]{1, 2, 4, 5});
        matrix.addCandidates(1, 1, new Integer[]{4, 5});
        matrix.addCandidates(2, 2, new Integer[]{2, 5, 6});
        //WHEN
        hiddenTriples.execute(matrix);
        //THEN
        assertEquals(2, matrix.getCandidates(0, 0).size());
        assertEquals(2, matrix.getCandidates(1, 1).size());
        assertEquals(2, matrix.getCandidates(2, 2).size());
    }

    @Test
    public void shouldFoundTripleInBlock() {
        //GIVEN
        IMatrix matrix = new Matrix();
        //Triple 2,4,5
        matrix.addCandidates(0, 0, new Integer[]{1, 7, 8});
        matrix.addCandidates(0, 2, new Integer[]{7, 8});
        matrix.addCandidates(1, 0, new Integer[]{6, 7});
        matrix.addCandidates(1, 1, new Integer[]{4, 5});
        matrix.addCandidates(2, 0, new Integer[]{1, 6});
        matrix.addCandidates(2, 1, new Integer[]{1, 2, 4, 5});
        matrix.addCandidates(2, 2, new Integer[]{2, 5, 6});

        matrix.addCandidates(2, 7, new Integer[]{1, 2, 4, 5});

        //WHEN
        hiddenTriples.execute(matrix);
        //THEN
        assertEquals(3, matrix.getCandidates(2, 1).size());
        assertEquals(2, matrix.getCandidates(2, 2).size());
    }

    @Test
    public void shouldFoundTripleInBlock2() {
        //GIVEN
        IMatrix matrix = new Matrix();
        // triple = 2,4,5
        matrix.addCandidates(0, 0, new Integer[]{1, 7, 8});
        matrix.addCandidates(0, 2, new Integer[]{7, 8});
        matrix.addCandidates(1, 0, new Integer[]{7, 6});
        matrix.addCandidates(1, 1, new Integer[]{4, 5});
        matrix.addCandidates(2, 0, new Integer[]{1, 6});
        matrix.addCandidates(2, 1, new Integer[]{1, 2, 4, 5});
        matrix.addCandidates(2, 2, new Integer[]{2, 5, 6});

        matrix.addCandidates(2, 7, new Integer[]{1, 2, 4, 5});

        //WHEN
        hiddenTriples.execute(matrix);
        //THEN
        assertEquals(3, matrix.getCandidates(2, 1).size());
        assertEquals(2, matrix.getCandidates(2, 2).size());
    }

    @Test
    public void shouldFoundTripleInColumn() {
        //GIVEN
        IMatrix matrix = new Matrix();

        matrix.addCandidates(0, 0, new Integer[]{1, 4});
        matrix.addCandidates(1, 0, new Integer[]{4, 3, 7});
        matrix.addCandidates(2, 0, new Integer[]{1, 3, 4, 7});
        matrix.addCandidates(3, 0, new Integer[]{1, 2, 4, 5, 6, 7, 8});
        matrix.addCandidates(4, 0, new Integer[]{1, 3, 4, 7, 8});
        matrix.addCandidates(5, 0, new Integer[]{1, 2, 4, 5, 6, 8});
        matrix.addCandidates(6, 0, new Integer[]{1, 2, 3, 4, 6, 8});
        matrix.addCandidates(7, 0, new Integer[]{1, 3, 4});
        //WHEN
        hiddenTriples.execute(matrix);
        //THEN
        assertEquals(3, matrix.getCandidates(3, 0).size());
        assertEquals(3, matrix.getCandidates(5, 0).size());
        assertEquals(2, matrix.getCandidates(6, 0).size());
    }

    @Test
    public void shouldFoundTripleInRow() {
        //GIVEN
        IMatrix matrix = new Matrix();

        matrix.addCandidates(0, 0, new Integer[]{1, 2, 4, 5});
        matrix.addCandidates(0, 1, new Integer[]{4, 5});
        matrix.addCandidates(0, 2, new Integer[]{2, 5, 6});
        matrix.addCandidates(0, 3, new Integer[]{1, 7, 8});
        matrix.addCandidates(0, 4, new Integer[]{7, 8});
        matrix.addCandidates(0, 5, new Integer[]{7, 6});
        matrix.addCandidates(0, 6, new Integer[]{1, 6});

        //WHEN
        hiddenTriples.execute(matrix);
        //THEN
        assertEquals(3, matrix.getCandidates(0, 0).size());
        assertEquals(2, matrix.getCandidates(0, 2).size());
    }

}