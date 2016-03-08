package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by daniel on 2016-03-06.
 */
public class SwordFishTest {

    private IAlgorithm swordfish = new SwordFish();

    @Test
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

    @Test
    public void advanced() {
        IMatrix matrix = new Matrix();
        //candidates in swordfish
        matrix.addCandidates(1, 0, new Integer[]{2, 4});
        matrix.addCandidates(1, 3, new Integer[]{1, 2, 4});
        matrix.addCandidates(1, 5, new Integer[]{1, 4});
        matrix.addCandidates(2, 1, new Integer[]{2, 4});
        matrix.addCandidates(2, 3, new Integer[]{2, 4});
        matrix.addCandidates(3, 5, new Integer[]{1, 4});
        matrix.addCandidates(3, 8, new Integer[]{2, 4});
        matrix.addCandidates(4, 3, new Integer[]{1, 4});
        matrix.addCandidates(4, 6, new Integer[]{4, 6});
        matrix.addCandidates(7, 1, new Integer[]{4, 6});
        matrix.addCandidates(7, 6, new Integer[]{4, 6});
        matrix.addCandidates(8, 0, new Integer[]{4, 6});
        matrix.addCandidates(8, 8, new Integer[]{3, 4});
        int candidates = matrix.getCandidatesCount();
        //candidates should be removed
        swordfish.execute(matrix);
        assertFalse(matrix.getCandidates(1, 3).contains(4));
        assertEquals(candidates - 1, matrix.getCandidatesCount());
    }

    @Test
    public void advanced2() {
        IMatrix matrix = new Matrix();
        //candidates in swordfish
        matrix.addCandidates(0, 5, new Integer[]{2, 6});
        matrix.addCandidates(0, 8, new Integer[]{2, 3});
        matrix.addCandidates(4, 0, new Integer[]{2, 4});
        matrix.addCandidates(4, 8, new Integer[]{2, 7, 9});
        matrix.addCandidates(8, 0, new Integer[]{2, 3, 7});
        matrix.addCandidates(8, 5, new Integer[]{1, 2, 7, 9});

        matrix.addCandidates(1, 4, new Integer[]{2, 6});
        matrix.addCandidates(1, 6, new Integer[]{2, 7, 8});
        matrix.addCandidates(1, 8, new Integer[]{2, 3, 7});
        matrix.addCandidates(5, 0, new Integer[]{2, 4, 6});
        matrix.addCandidates(5, 2, new Integer[]{1, 2});
        matrix.addCandidates(5, 6, new Integer[]{2, 4, 6});
        matrix.addCandidates(7, 0, new Integer[]{2, 7});

        matrix.addCandidates(7, 2, new Integer[]{1, 2, 7, 9});
        matrix.addCandidates(7, 4, new Integer[]{1, 2, 7});

        //candidates should be removed
        swordfish.execute(matrix);
        assertFalse(matrix.getCandidates(5, 0).contains(2));
        assertFalse(matrix.getCandidates(7, 0).contains(2));
        assertFalse(matrix.getCandidates(1, 8).contains(2));
    }
}