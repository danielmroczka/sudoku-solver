package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by daniel on 2016-02-19.
 */
public class NakedTripletsTest {

    private final NakedTriplets nakedTriplets = new NakedTriplets();

    @Test
    public void allThreeCandidatesInAllThreeCellsBlock() {
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{1, 2, 4, 5, 6});
        matrix.addCandidates(0, 2, new Integer[]{1, 2, 5, 6, 8});
        matrix.addCandidates(1, 1, new Integer[]{1, 2, 5, 6, 8});
        matrix.addCandidates(0, 1, new Integer[]{1, 2, 6});
        matrix.addCandidates(2, 0, new Integer[]{1, 2, 6});
        matrix.addCandidates(2, 2, new Integer[]{1, 2, 6});
        //WHEN
        nakedTriplets.execute(matrix);
        //THEN
        assertEquals(2, matrix.getCandidates(0, 0).size());
        assertEquals(2, matrix.getCandidates(0, 2).size());
        assertEquals(2, matrix.getCandidates(1, 1).size());
    }

    @Test
    public void allThreeCandidatesInAllThreeCellsRow() {
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
        nakedTriplets.execute(matrix);
        //THEN
        assertEquals(2, matrix.getCandidates(0, 7).size());
        assertEquals(2, matrix.getCandidates(0, 8).size());
    }

    @Test
    @Ignore //TODO
    public void notAllThreeCanidatesInAllThreeCells() {
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.setValueAt(0, 1, 1);
        matrix.setValueAt(0, 4, 4);
        matrix.setValueAt(0, 7, 2);

        matrix.addCandidates(0, 0, new Integer[]{7, 8, 9});
        matrix.addCandidates(0, 2, new Integer[]{7, 8});
        matrix.addCandidates(0, 3, new Integer[]{3, 5, 9});
        matrix.addCandidates(0, 5, new Integer[]{5, 6, 8, 9});
        matrix.addCandidates(0, 6, new Integer[]{7, 9});
        matrix.addCandidates(0, 8, new Integer[]{3, 5, 6, 7, 8, 9});
        //WHEN
        nakedTriplets.execute(matrix);
        //THEN
        assertEquals(2, matrix.getCandidates(0, 3).size());
        assertEquals(2, matrix.getCandidates(0, 5).size());
        assertEquals(2, matrix.getCandidates(0, 8).size());
    }

}