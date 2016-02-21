package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by daniel on 2016-02-19.
 */
public class NakedTriplesTest {

    private final NakedTriples nakedTriples = new NakedTriples();

    @Test
    public void testExecute() {
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{1, 2, 4, 5, 6});
        matrix.addCandidates(0, 2, new Integer[]{1, 2, 5, 6, 8});
        matrix.addCandidates(1, 1, new Integer[]{1, 2, 5, 6, 8});
        matrix.addCandidates(0, 1, new Integer[]{1, 2, 6});
        matrix.addCandidates(2, 0, new Integer[]{1, 2, 6});
        matrix.addCandidates(2, 2, new Integer[]{1, 2, 6});
        //WHEN
        nakedTriples.execute(matrix);
        //THEN
        assertEquals(2, matrix.getCandidates(0, 0).size());
        assertEquals(2, matrix.getCandidates(0, 2).size());
        assertEquals(2, matrix.getCandidates(1, 1).size());
    }

}