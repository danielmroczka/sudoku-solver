package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import com.labs.dm.sudoku.solver.executors.Executor;
import org.junit.Test;

/**
 * Created by daniel on 2016-03-27.
 */
public class SandboxTest {
    @Test
    public void name() throws Exception {
        //GIVEN
        IMatrix matrix = new Matrix();

        matrix.addCandidates(0, 0, new Integer[]{3, 5, 7});
        matrix.addCandidates(0, 1, new Integer[]{5, 6, 7});
        matrix.addCandidates(2, 0, new Integer[]{3, 5});
        //WHEN
        Executor.run(matrix, Reduction.class);
        //THEN
        // assertEquals(1, matrix.getCandidates(2,0).size());
    }
}
