package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.Test;

/**
 * Created by daniel on 2016-02-19.
 */
public class NakedSinglesTest {

    private NakedSingles nakedSingles = new NakedSingles();

    @Test
    public void execute() throws Exception {
        IMatrix matrix = new Matrix();
        nakedSingles.execute(matrix);

    }
}