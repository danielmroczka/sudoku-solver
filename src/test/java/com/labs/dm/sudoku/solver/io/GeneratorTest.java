package com.labs.dm.sudoku.solver.io;

import com.labs.dm.sudoku.solver.alg.Flow;
import com.labs.dm.sudoku.solver.core.IMatrix;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by daniel on 2016-02-19.
 */
public class GeneratorTest {

    private final Generator gen = new Generator();

    @Test
    public void shouldGenerateMatrix() {
        IMatrix matrix = gen.generate(67);
        assertEquals(67, matrix.getSolvedItems());
        assertTrue(matrix.validate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotGenerateMatrixToBigValue() {
        gen.generate(100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotGenerateMatrixegativeVal() {
        gen.generate(-1);
    }

    @Test
    @Ignore
    public void shouldGenerateMatrixFull() {
        IMatrix matrix = gen.generate(27);
        Flow flow = new Flow();
        flow.execute(matrix);
    }
}