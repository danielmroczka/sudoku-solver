package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by daniel on 2016-02-21.
 */
public class NakedQuadsTest {

    private final NakedQuads nakedQuads = new NakedQuads();

    @Test
    @Ignore
    public void testExecute() {
        //GIVEN
        IMatrix matrix = new Matrix();

        matrix.setValueAt(0, 1, 5);
        matrix.setValueAt(2, 2, 2);
        matrix.addCandidates(0, 0, new Integer[]{1, 9});
        matrix.addCandidates(0, 2, new Integer[]{1, 3, 6, 8});
        matrix.addCandidates(1, 1, new Integer[]{1, 7, 9});
        matrix.addCandidates(0, 1, new Integer[]{1, 3, 7, 9});
        matrix.addCandidates(2, 0, new Integer[]{1, 4, 8, 9});
        matrix.addCandidates(2, 2, new Integer[]{1, 4, 6, 9});
        //WHEN
        nakedQuads.execute(matrix);
        //THEN
        assertEquals(2, matrix.getCandidates(0, 2).size());
        assertEquals(2, matrix.getCandidates(2, 0).size());
        assertEquals(2, matrix.getCandidates(2, 1).size());
    }

    @Test
    @Ignore
    public void allFourCandidatesInAllFourCells() {
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.setValueAt(0, 2, 7);
        matrix.setValueAt(0, 3, 2);
        matrix.setValueAt(0, 4, 3);
        matrix.setValueAt(0, 5, 4);

        matrix.addCandidates(0, 0, new Integer[]{1, 5, 9});
        matrix.addCandidates(0, 1, new Integer[]{1, 5, 9});
        matrix.addCandidates(0, 6, new Integer[]{1, 5, 9});
        matrix.addCandidates(0, 7, new Integer[]{1, 6, 8, 9});
        matrix.addCandidates(0, 8, new Integer[]{5, 6, 8, 9});
        //WHEN
        nakedQuads.execute(matrix);
        //THEN
        assertEquals(2, matrix.getCandidates(0, 7).size());
        assertEquals(2, matrix.getCandidates(0, 8).size());
    }
}