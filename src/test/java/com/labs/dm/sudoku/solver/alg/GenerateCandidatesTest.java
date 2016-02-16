/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import com.labs.dm.sudoku.solver.io.MatrixLoader;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author daniel
 */
public class GenerateCandidatesTest {

    private final IAlgorithm alg = new GenerateCandidates();
    private IMatrix matrix;

    @Before
    public void setUp() {
        matrix = new Matrix();
    }

    @Test
    public void shouldSet() {
        alg.execute(matrix);
        assertEquals(9, matrix.getCandidates(4, 4).size());
    }

    @Test
    public void shouldSet2() throws IOException {
        matrix = new MatrixLoader().load("patterns/easy1.txt");
        alg.execute(matrix);
        Collection<Integer> set = matrix.getCandidates(4, 4);
        assertEquals(3, set.size());
        assertTrue(set.contains(5));
        assertTrue(set.contains(7));
        assertTrue(set.contains(8));
    }

    @Test
    public void shouldSet1() throws IOException {
        matrix = new MatrixLoader().load("patterns/solved.txt");
        alg.execute(matrix);
        assertEquals(0, matrix.getCandidates(4, 4).size());
    }

    @Test
    public void shouldSet3() throws IOException {
        int[] ones = new int[81];
        Arrays.fill(ones, 1);
        matrix = new Matrix(ones);
        alg.execute(matrix);
        // assertEquals(8, matrix.getCandidates(4, 4).size());
    }
}
