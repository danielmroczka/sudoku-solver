package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * Created by daniel on 2016-03-06.
 */
public class SwordfishTest {

    private IAlgorithm swordfish = new Swordfish();

    @Test
    //@Ignore
    public void minimumSwordfishInCols() {
        IMatrix matrix = new Matrix();
        //candidates in swordfish
        matrix.addCandidates(1, 0, new Integer[]{2, 9});
        matrix.addCandidates(1, 4, new Integer[]{2, 9});
        matrix.addCandidates(2, 4, new Integer[]{2, 9});
        matrix.addCandidates(2, 7, new Integer[]{2, 9});
        matrix.addCandidates(8, 0, new Integer[]{2, 8, 5});
        matrix.addCandidates(8, 7, new Integer[]{2, 8, 5});

        //candidates should be removed
        matrix.addCandidates(6, 0, new Integer[]{2, 5, 8, 9});
        matrix.addCandidates(4, 0, new Integer[]{2, 5, 8, 9});
        matrix.addCandidates(5, 7, new Integer[]{2, 5, 8, 9});

        // matrix.addCandidates(4, 7, new Integer[]{2, 8});

        swordfish.execute(matrix);

        assertFalse(matrix.getCandidates(6, 0).contains(2));
        assertFalse(matrix.getCandidates(4, 0).contains(2));
        assertFalse(matrix.getCandidates(5, 7).contains(2));

        //   assertTrue(matrix.getCandidates(4, 7).contains(2));
    }

    @Test
    //@Ignore
    public void fullSwordfishInCols() {
        IMatrix matrix = new Matrix();
        //candidates in swordfish
        matrix.addCandidates(1, 0, new Integer[]{2, 9});
        matrix.addCandidates(1, 4, new Integer[]{2, 9});
        matrix.addCandidates(1, 7, new Integer[]{2, 9});
        matrix.addCandidates(2, 0, new Integer[]{2, 9});
        matrix.addCandidates(2, 4, new Integer[]{2, 9});
        matrix.addCandidates(2, 7, new Integer[]{2, 9});
        matrix.addCandidates(8, 0, new Integer[]{2, 8, 5});
        matrix.addCandidates(8, 4, new Integer[]{2, 8, 5});
        matrix.addCandidates(8, 7, new Integer[]{2, 9});

        //candidates should be removed
        matrix.addCandidates(6, 0, new Integer[]{2, 5, 8, 9});
        matrix.addCandidates(5, 7, new Integer[]{2, 8});

        swordfish.execute(matrix);

        assertFalse(matrix.getCandidates(6, 0).contains(2));
        assertFalse(matrix.getCandidates(5, 7).contains(2));
    }
}