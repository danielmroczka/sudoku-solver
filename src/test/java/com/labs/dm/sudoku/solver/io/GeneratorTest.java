package com.labs.dm.sudoku.solver.io;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.executors.Flow;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Daniel Mroczka on 2016-02-19.
 */
public class GeneratorTest {

    private final Generator gen = new Generator();

    @Test
    @Ignore
    public void shouldGenerateMatrix() {
        IMatrix matrix = gen.generate(67);
        assertEquals(67, matrix.getSolvedItems());
        assertTrue(matrix.validate());
    }

    @Test
    public void shouldGenerateMatrixLoop() {
        IMatrix matrix;

        for (int i = 0; i <= 81; i++) {
            matrix = gen.generateNew(i);
            assertTrue(matrix.validate());
        }
    }

    @Test
    @Ignore
    public void shouldGenerateMatrixLoopAndSolve() {
        IMatrix matrix;
        Flow flow = new Flow();

        for (int i = 0; i <= 75; i++) {
            matrix = gen.generateNew(i);
            flow.execute(matrix);
            //assertTrue(matrix.validate(true));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotGenerateMatrixToBigValue() {
        gen.generate(100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotGenerateMatrixegativeVal() {
        gen.generate(-1);
    }

}