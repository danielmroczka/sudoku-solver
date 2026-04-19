package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Daniel Mroczka on 4/12/2016.
 */
public class RemotePairsTest {

    private final RemotePairs remotePairs = new RemotePairs();

    @Test
    public void executeRemovesPairFromCellSeeingOppositeColours() {
        // GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{4, 7});
        matrix.addCandidates(0, 4, new Integer[]{4, 7});
        matrix.addCandidates(4, 4, new Integer[]{4, 7});
        matrix.addCandidates(4, 0, new Integer[]{4, 7});
        matrix.addCandidates(2, 0, new Integer[]{1, 4, 7});

        // WHEN
        remotePairs.execute(matrix);

        // THEN
        assertEquals(1, matrix.getValueAt(2, 0));
        assertTrue(matrix.getCandidates(2, 0).isEmpty());
    }

    @Test
    public void executeDoesNothingWhenCellCannotSeeBothColours() {
        // GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{4, 7});
        matrix.addCandidates(0, 4, new Integer[]{4, 7});
        matrix.addCandidates(4, 4, new Integer[]{4, 7});
        matrix.addCandidates(4, 0, new Integer[]{4, 7});
        matrix.addCandidates(8, 8, new Integer[]{1, 4, 7});

        // WHEN
        remotePairs.execute(matrix);

        // THEN
        assertEquals(0, matrix.getValueAt(8, 8));
        assertEquals(3, matrix.getCandidates(8, 8).size());
        assertTrue(matrix.getCandidates(8, 8).contains(4));
        assertTrue(matrix.getCandidates(8, 8).contains(7));
    }
}
