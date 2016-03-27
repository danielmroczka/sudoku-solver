package com.labs.dm.sudoku.solver.alg.fish;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by Daniel Mroczka on 08-Mar-16.
 */
public class JellyFishTest {

    private final JellyFish jellyFish = new JellyFish();

    @Test
    public void simpleJellyFish() {
        //GIVEN
        IMatrix matrix = new Matrix();
        //candidates in swordfish
        matrix.addCandidates(0, 0, new Integer[]{2, 4, 9});
        matrix.addCandidates(0, 7, new Integer[]{2, 4, 6, 9});
        matrix.addCandidates(0, 8, new Integer[]{2, 6, 9});

        matrix.addCandidates(3, 0, new Integer[]{3, 4, 8, 9});
        matrix.addCandidates(3, 4, new Integer[]{2, 3});
        matrix.addCandidates(3, 8, new Integer[]{2, 8});

        matrix.addCandidates(5, 0, new Integer[]{3, 8});
        matrix.addCandidates(5, 4, new Integer[]{2, 3});
        matrix.addCandidates(5, 7, new Integer[]{2, 6, 8});
        matrix.addCandidates(5, 8, new Integer[]{2, 6, 8});

        matrix.addCandidates(8, 0, new Integer[]{1, 2, 4, 5, 8});
        matrix.addCandidates(8, 4, new Integer[]{1, 4, 8});
        matrix.addCandidates(8, 7, new Integer[]{2, 5, 8});
        matrix.addCandidates(8, 8, new Integer[]{2, 5, 8});

        matrix.addCandidates(1, 7, new Integer[]{2, 4, 6, 9});
        matrix.addCandidates(2, 7, new Integer[]{2, 4, 5, 6});
        matrix.addCandidates(2, 8, new Integer[]{2, 3, 5, 6});
        matrix.addCandidates(6, 7, new Integer[]{2, 8, 9});
        int candidates = matrix.getCandidatesCount();

        //WHEN
        jellyFish.execute(matrix);

        //THEN
        assertFalse(matrix.getCandidates(1, 7).contains(2));
        assertFalse(matrix.getCandidates(2, 7).contains(2));
        assertFalse(matrix.getCandidates(2, 8).contains(2));
        assertFalse(matrix.getCandidates(6, 7).contains(2));
        assertEquals(4, candidates - matrix.getCandidatesCount());
    }
}