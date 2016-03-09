package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by daniel on 2016-03-09.
 */
public class ExecutorTest {

    @Test
    public void name() throws Exception {
        IMatrix matrix = new Matrix();
        IAlgorithm algorithm = new IAlgorithm() {
            @Override
            public void execute(IMatrix matrix) {
                matrix.setValueAt(0, 0, 9);
            }
        };
        new Executor(matrix, algorithm).run();

        assertEquals(9, matrix.getValueAt(0, 0));
    }
}