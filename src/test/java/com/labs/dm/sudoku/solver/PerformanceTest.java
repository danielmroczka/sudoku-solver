package com.labs.dm.sudoku.solver;

import com.labs.dm.sudoku.solver.alg.Flow;
import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.io.MatrixLoader;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * Created by daniel on 2016-02-24.
 */
public class PerformanceTest {

    private final MatrixLoader loader = new MatrixLoader();

    final String[] files = {"tough1.txt"};

    @Test
    public void pass() throws IOException {
        for (String file : files) {
            IMatrix matrix = loader.load("src/test/resources/patterns/" + file);
            Flow flow = new Flow();
            flow.execute(matrix);
            assertTrue(matrix.isSolved());
        }
    }

}
