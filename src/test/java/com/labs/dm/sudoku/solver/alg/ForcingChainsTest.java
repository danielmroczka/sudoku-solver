package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * Created by daniel on 2016-03-09.
 */
public class ForcingChainsTest {

    private IAlgorithm alg = new ForcingChains();

    @Test
    @Ignore
    public void simple() {
        IMatrix matrix = new Matrix();
        //candidates in swordfish
        matrix.addCandidates(0, 2, new Integer[]{1, 2});
        matrix.addCandidates(3, 2, new Integer[]{1, 4});
        matrix.addCandidates(3, 0, new Integer[]{5, 7});

        matrix.addCandidates(5, 3, new Integer[]{4, 7});

        //candidates should be removed
        alg.execute(matrix);
        assertFalse(matrix.getCandidates(3, 0).contains(5));
    }
}