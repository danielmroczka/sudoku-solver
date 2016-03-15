package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import com.labs.dm.sudoku.solver.io.MatrixLoader;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by daniel on 2016-02-15.
 */
public class XWingTest {

    private final IAlgorithm alg = new XWing();
    private final IAlgorithm cand = new GenerateCandidates();

    @Test
    public void shouldTestEmptyMarix() {
        IMatrix matrix = new Matrix();
        cand.execute(matrix);
        alg.execute(matrix);
    }

    @Test
    public void testInRow() {
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{1, 3});
        matrix.addCandidates(0, 4, new Integer[]{1, 3});
        matrix.addCandidates(4, 0, new Integer[]{1, 3});
        matrix.addCandidates(4, 4, new Integer[]{1, 3});

        matrix.addCandidates(0, 6, new Integer[]{1, 4, 5});
        matrix.addCandidates(4, 6, new Integer[]{1, 5, 6});
        //WHEN
        alg.execute(matrix);
        //THEN
        assertEquals(2, matrix.getCandidates(0, 0).size());
        assertEquals(2, matrix.getCandidates(0, 4).size());
        assertEquals(2, matrix.getCandidates(4, 0).size());
        assertEquals(2, matrix.getCandidates(4, 4).size());

        assertEquals(2, matrix.getCandidates(0, 6).size());
        assertEquals(2, matrix.getCandidates(4, 6).size());
    }

    @Test
    public void testInCol() {
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{1, 3});
        matrix.addCandidates(0, 4, new Integer[]{1, 3});
        matrix.addCandidates(4, 0, new Integer[]{1, 3});
        matrix.addCandidates(4, 4, new Integer[]{1, 3});

        matrix.addCandidates(6, 0, new Integer[]{1, 4, 5});
        matrix.addCandidates(6, 4, new Integer[]{1, 5, 6});
        //WHEN
        alg.execute(matrix);

        //THEN
        assertEquals(2, matrix.getCandidates(0, 0).size());
        assertEquals(2, matrix.getCandidates(0, 4).size());
        assertEquals(2, matrix.getCandidates(4, 0).size());
        assertEquals(2, matrix.getCandidates(4, 4).size());

        assertEquals(2, matrix.getCandidates(6, 0).size());
        assertEquals(2, matrix.getCandidates(6, 4).size());
    }

    @Test
    @Ignore
    public void testFromFile() throws IOException {
        IMatrix matrix = new MatrixLoader().load("patterns/hiddenPair.txt");
        cand.execute(matrix);
        assertTrue(matrix.getCandidatesCount() <= 98);
        alg.execute(matrix);
        assertTrue(matrix.getCandidatesCount() <= 98);
    }

    @Test
    public void testFromFile1() throws IOException {
        IMatrix matrix = new MatrixLoader().load("patterns/xwing/incol.txt");
        cand.execute(matrix);
        assertTrue(matrix.getCandidatesCount() == 60);
        alg.execute(matrix);
        assertTrue(matrix.getCandidatesCount() == 56);
    }
}