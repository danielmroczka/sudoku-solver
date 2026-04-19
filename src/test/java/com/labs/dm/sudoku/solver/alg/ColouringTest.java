package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Daniel Mroczka on 4/12/2016.
 */
public class ColouringTest {

    private final Colouring colouring = new Colouring();

    @Test
    public void executeRemovesCandidateSeenByBothColours() {
        // GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{5, 9});
        matrix.addCandidates(0, 4, new Integer[]{5, 8});
        matrix.addCandidates(4, 4, new Integer[]{5, 7});
        matrix.addCandidates(4, 0, new Integer[]{5, 6});
        matrix.addCandidates(1, 1, new Integer[]{2, 5});
        matrix.addCandidates(2, 0, new Integer[]{1, 5});

        // WHEN
        colouring.execute(matrix);

        // THEN
        assertEquals(1, matrix.getValueAt(2, 0));
    }

    @Test
    public void executeDoesNotRemoveWhenCellSeesSingleColourOnly() {
        // GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{5, 9});
        matrix.addCandidates(0, 4, new Integer[]{5, 8});
        matrix.addCandidates(4, 4, new Integer[]{5, 7});
        matrix.addCandidates(4, 0, new Integer[]{5, 6});
        matrix.addCandidates(8, 8, new Integer[]{1, 5});

        // WHEN
        colouring.execute(matrix);

        // THEN
        assertEquals(0, matrix.getValueAt(8, 8));
        assertTrue(matrix.getCandidates(8, 8).contains(5));
    }
}
