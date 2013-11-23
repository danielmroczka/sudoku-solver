/*
 * Copyright daniel.mroczka@gmail.com. All rights reserved. 
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import com.labs.dm.sudoku.solver.io.MatrixLoader;
import java.io.IOException;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author daniel
 */
public class ShowPossiblesTest {

    private final ShowPossibles alg = new ShowPossibles();
    private IMatrix matrix;

    @Before
    public void setUp() {
        matrix = new Matrix();
    }

    @Test
    public void shouldSet() {
        alg.execute(matrix);
        assertEquals(9, matrix.getPossibleValues()[4][4].size());
    }

    @Test
    public void shouldSet2() throws IOException {
        matrix = new MatrixLoader().load("patterns/easy1.txt");
        alg.execute(matrix);
        Set<Integer> set = matrix.getPossibleValues()[4][4];
        assertEquals(3, set.size());
        assertTrue(set.contains(5));
        assertTrue(set.contains(7));
        assertTrue(set.contains(8));
    }

    @Test
    public void shouldSet1() throws IOException {
        matrix = new MatrixLoader().load("patterns/solved.txt");
        alg.execute(matrix);
        assertEquals(0, matrix.getPossibleValues()[4][4].size());
    }
}
