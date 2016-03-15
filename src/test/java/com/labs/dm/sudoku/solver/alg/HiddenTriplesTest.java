package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by daniel on 2016-02-19.
 */
public class HiddenTriplesTest {
    private final HiddenTriples hiddenTriples = new HiddenTriples();

    @Test
    public void testExecute() {
        //GIVEN
        IMatrix matrix = new Matrix();

        matrix.addCandidates(0, 0, new Integer[]{1, 2, 5, 6, 8});
        matrix.addCandidates(0, 1, new Integer[]{4, 5, 7, 8});
        matrix.addCandidates(0, 2, new Integer[]{5, 7, 8});
        matrix.addCandidates(0, 3, new Integer[]{5, 8});
        matrix.addCandidates(0, 5, new Integer[]{1, 2, 5, 6});
        matrix.addCandidates(0, 7, new Integer[]{1, 2, 6});
        matrix.addCandidates(0, 8, new Integer[]{4, 7});
        //WHEN
        hiddenTriples.execute(matrix);
        //THEN
        assertEquals(3, matrix.getCandidates(0, 0).size());
        assertEquals(3, matrix.getCandidates(0, 5).size());
        assertEquals(3, matrix.getCandidates(0, 7).size());

    }
}