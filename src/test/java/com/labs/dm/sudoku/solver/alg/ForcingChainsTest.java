package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * Created by daniel on 2016-03-09.
 */
public class ForcingChainsTest {

    private final IAlgorithm alg = new ForcingChains();

    @Test
    public void simple() {
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 2, new Integer[]{1, 2});
        matrix.addCandidates(3, 2, new Integer[]{1, 4});
        matrix.addCandidates(3, 0, new Integer[]{5, 7});

        matrix.addCandidates(3, 5, new Integer[]{4, 7});

        //candidates should be removed
        alg.execute(matrix);
        System.out.println(matrix.printCandidates());
        assertFalse(matrix.getCandidates(3, 0).contains(7));
    }
}