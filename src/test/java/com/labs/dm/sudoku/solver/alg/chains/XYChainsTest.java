package com.labs.dm.sudoku.solver.alg.chains;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Daniel Mroczka on 4/7/2016.
 */
public class XYChainsTest {

    private final XYChains xyChains = new XYChains();

    @Test
    @Disabled
    public void execute() {
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 1, new Integer[]{1, 5});
        matrix.addCandidates(5, 1, new Integer[]{4, 5});
        matrix.addCandidates(5, 0, new Integer[]{1, 7});
        matrix.addCandidates(5, 8, new Integer[]{4, 7});

        xyChains.execute(matrix);

        assertTrue(matrix.isCellSet(0, 1));
        assertTrue(matrix.isCellSet(5, 1));
        assertTrue(matrix.isCellSet(5, 0));
        assertTrue(matrix.isCellSet(5, 8));
    }

}
