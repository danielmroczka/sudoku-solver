package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Daniel Mroczka on 15-Mar-16.
 */
public class HiddenQuadsTest {
    private final HiddenQuads hiddenQuads = new HiddenQuads();

    @Test
    //  @Ignore
    public void testExecute() {
        //Should find hidden quad: 1, 6, 7, 8
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{1, 2, 3, 6, 7});
        matrix.addCandidates(0, 1, new Integer[]{2, 4, 5, 6, 7});
        matrix.addCandidates(0, 2, new Integer[]{1, 4, 5, 6, 7, 8});
        matrix.addCandidates(0, 3, new Integer[]{5, 9});
        matrix.addCandidates(0, 4, new Integer[]{4, 2});
        matrix.addCandidates(0, 5, new Integer[]{7, 8});
        matrix.addCandidates(0, 6, new Integer[]{4, 3, 9});
        matrix.addCandidates(0, 7, new Integer[]{4, 2, 5});
        matrix.addCandidates(0, 8, new Integer[]{2, 3, 4, 5, 9});
        //WHEN
        hiddenQuads.execute(matrix);
        //THEN
        assertEquals(3, matrix.getCandidates(0, 0).size());
        assertEquals(2, matrix.getCandidates(0, 1).size());
        assertEquals(4, matrix.getCandidates(0, 2).size());
        assertEquals(2, matrix.getCandidates(0, 5).size());
    }
}
