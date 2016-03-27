package com.labs.dm.sudoku.solver.io;

import com.labs.dm.sudoku.solver.core.IMatrix;
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

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotGenerateMatrixToBigValue() {
        gen.generate(100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotGenerateMatrixegativeVal() {
        gen.generate(-1);
    }

}