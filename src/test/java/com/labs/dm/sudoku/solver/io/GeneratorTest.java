package com.labs.dm.sudoku.solver.io;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.executors.Flow;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Daniel Mroczka on 2016-02-19.
 */
public class GeneratorTest {

    private final Generator gen = new Generator();

    @Test
    @Disabled
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
    @Disabled
    public void shouldGenerateMatrixLoopAndSolve() {
        IMatrix matrix;
        Flow flow = new Flow();

        for (int i = 0; i <= 75; i++) {
            matrix = gen.generateNew(i);
            flow.execute(matrix);
            //assertTrue(matrix.validate(true));
        }
    }

    @Test
    public void shouldNotGenerateMatrixToBigValue() {
        assertThrows(IllegalArgumentException.class, () -> gen.generate(100));
    }

    @Test
    public void shouldNotGenerateMatrixegativeVal() {
        assertThrows(IllegalArgumentException.class, () -> gen.generate(-1));
    }

}

