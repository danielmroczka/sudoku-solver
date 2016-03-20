package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.LogListener;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by daniel on 2016-03-09.
 */
public class ForcingChainsTest {

    private final IAlgorithm alg = new ForcingChains();

    @Test
    public void simple() {
        IMatrix matrix = new Matrix();
        matrix.addMatrixListener(new LogListener());
        matrix.addCandidates(0, 2, new Integer[]{1, 2});
        matrix.addCandidates(3, 2, new Integer[]{1, 4});
        matrix.addCandidates(3, 0, new Integer[]{5, 7});

        matrix.addCandidates(3, 5, new Integer[]{4, 7});

        matrix.addCandidates(0, 5, new Integer[]{2, 7});
        matrix.addCandidates(0, 6, new Integer[]{1, 4});
        matrix.addCandidates(2, 8, new Integer[]{1, 4});
        matrix.addCandidates(2, 0, new Integer[]{4, 7});
        matrix.addCandidates(3, 2, new Integer[]{1, 4});
        matrix.addCandidates(3, 5, new Integer[]{4, 7});
        matrix.addCandidates(3, 6, new Integer[]{1, 8});
        matrix.addCandidates(8, 6, new Integer[]{4, 8});

        //candidates should be removed
        alg.execute(matrix);
        assertFalse(matrix.getCandidates(3, 0).contains(7));
    }

    @Test
    public void simple2() {
        IMatrix matrix = new Matrix();
        matrix.addMatrixListener(new LogListener());
        matrix.addCandidates(0, 1, new Integer[]{2, 7});
        matrix.addCandidates(0, 7, new Integer[]{2, 3});
        matrix.addCandidates(1, 0, new Integer[]{1, 2});

        matrix.addCandidates(4, 0, new Integer[]{1, 2});

        matrix.addCandidates(5, 1, new Integer[]{1, 2});
        matrix.addCandidates(5, 7, new Integer[]{1, 3});

        //candidates should be removed
        alg.execute(matrix);
        assertEquals(7, matrix.getValueAt(0, 1));
    }
}