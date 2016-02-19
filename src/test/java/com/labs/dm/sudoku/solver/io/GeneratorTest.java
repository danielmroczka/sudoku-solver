package com.labs.dm.sudoku.solver.io;

import com.labs.dm.sudoku.solver.core.IMatrix;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by daniel on 2016-02-19.
 */
public class GeneratorTest {

    private Generator gen = new Generator();

    @Test
    public void shouldGenerateMatrix() {
        IMatrix matrix = gen.generate(67);
        assertEquals(67, matrix.getSolvedItems());
        assertTrue(matrix.validate());
    }
}