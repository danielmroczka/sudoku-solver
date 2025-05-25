/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import com.labs.dm.sudoku.solver.io.MatrixLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Daniel Mroczka
 */
public class OpenSinglesTest {

    private final OpenSingles singles = new OpenSingles();
    private IMatrix matrix;

    @BeforeEach
    public void setUp() {
        matrix = new Matrix();
    }

    @Test
    public void testOpenSingle() {
        int[] input = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        assertEquals(0, singles.fillOpenSingles(input));
        assertArrayEquals(new int[]{9, 1, 2, 3, 4, 5, 6, 7, 8}, input);
    }

    @Test
    public void testOpenSingleFilledArray() {
        int[] input = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertEquals(-1, singles.fillOpenSingles(input));
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, input);
    }

    @Test
    public void testOpenSingleFilledArrayInvalid() {
        int[] input = new int[]{0, 1, 1, 4, 5, 6, 7, 8, 9};
        assertEquals(-1, singles.fillOpenSingles(input));
        assertArrayEquals(new int[]{0, 1, 1, 4, 5, 6, 7, 8, 9}, input);
    }

    @Test
    public void testOpenSingleMoreGaps() {
        int[] input = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 0};
        assertEquals(-1, singles.fillOpenSingles(input));
        assertArrayEquals(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 0}, input);
    }

    @Test
    public void shouldThrowExceptionWhenInputInvalid() {
        int[] input = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertThrows(IllegalArgumentException.class, () -> singles.fillOpenSingles(input));
    }

    @Test
    public void testOpenSingleInCol() {
        matrix.setCols(4, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8});
        singles.execute(matrix);
        assertArrayEquals(new int[]{9, 1, 2, 3, 4, 5, 6, 7, 8}, matrix.getElemsInCol(4));
    }

    @Test
    public void testOpenSingleInRow() {
        matrix.setRows(4, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8});
        singles.execute(matrix);
        assertArrayEquals(new int[]{9, 1, 2, 3, 4, 5, 6, 7, 8}, matrix.getElemsInRow(4));
    }

    @Test
    public void testOpenSingleInBlock() {
        matrix.setBlock(1, 1, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8});
        singles.execute(matrix);
        assertArrayEquals(new int[]{9, 1, 2, 3, 4, 5, 6, 7, 8}, matrix.getElemsInBlock(1, 1));
    }

    @Test
    public void testFromFile() throws IOException {
        matrix = new MatrixLoader().load("patterns/alg/opensingles/001.txt");
        singles.execute(matrix);
        assertTrue(matrix.isSolved());
    }

    @Test
    public void testFromFile2() throws IOException {
        matrix = new MatrixLoader().load("patterns/alg/opensingles/002.txt");
        singles.execute(matrix);
        assertTrue(matrix.isSolved());
    }
}
