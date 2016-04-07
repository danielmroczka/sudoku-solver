package com.labs.dm.sudoku.solver.alg.chains;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * Created by Daniel Mroczka on 4/7/2016.
 */
public class XYChainsTest {

    private final XYChains xyChains = new XYChains();

    @Test
    public void execute() throws Exception {
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 2, new Integer[]{1, 2});

        xyChains.execute(matrix);

        assertFalse(matrix.isSolved());

    }

}