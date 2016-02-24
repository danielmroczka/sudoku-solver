package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import com.labs.dm.sudoku.solver.io.MatrixLoader;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by daniel on 2016-02-21.
 */
public class XYWingTest {

    private IAlgorithm yWing = new XYWing();
    private IAlgorithm cand = new GenerateCandidates();

    @Test
    public void differentBlocks() {

        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{2, 7});
        matrix.addCandidates(0, 4, new Integer[]{1, 2});
        matrix.addCandidates(5, 0, new Integer[]{1, 7});
        matrix.addCandidates(5, 4, new Integer[]{1, 9});
        //WHEN
        yWing.execute(matrix);
        //THEN
        assertEquals(1, matrix.getCandidates(5, 4).size());
    }

    @Test
    public void testTheSameRowBlock() {

        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{2, 7});
        matrix.addCandidates(0, 4, new Integer[]{1, 2});
        matrix.addCandidates(2, 1, new Integer[]{1, 7});

        matrix.addCandidates(0, 1, new Integer[]{1, 8, 9});
        matrix.addCandidates(0, 2, new Integer[]{1, 8, 9});
        matrix.addCandidates(2, 3, new Integer[]{1, 8, 9});
        matrix.addCandidates(2, 4, new Integer[]{1, 8, 9});
        matrix.addCandidates(2, 5, new Integer[]{1, 8, 9});

        //WHEN
        int candidates = matrix.getCandidatesCount();
        yWing.execute(matrix);
        //THEN
        assertEquals(candidates - 5, matrix.getCandidatesCount());
        assertFalse(matrix.getCandidates(0, 1).contains(1));
        assertEquals(2, matrix.getCandidates(0, 1).size());
        assertEquals(2, matrix.getCandidates(0, 2).size());
        assertEquals(2, matrix.getCandidates(2, 3).size());
        assertEquals(2, matrix.getCandidates(2, 4).size());
        assertEquals(2, matrix.getCandidates(2, 5).size());
    }

    @Test
    public void testTheSameRow() {

        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{2, 7});
        matrix.addCandidates(0, 2, new Integer[]{1, 2});
        matrix.addCandidates(0, 4, new Integer[]{1, 7});

        matrix.addCandidates(0, 1, new Integer[]{1, 8, 9});
        matrix.addCandidates(0, 3, new Integer[]{1, 8, 9});
        matrix.addCandidates(0, 5, new Integer[]{1, 8, 9});
        int candidates = matrix.getCandidatesCount();
        //WHEN

        yWing.execute(matrix);
        //THEN
        assertEquals(candidates - 3, matrix.getCandidatesCount());
        assertEquals(2, matrix.getCandidates(0, 1).size());
    }


    @Test
    public void testFromFile1() throws IOException {
        IMatrix matrix = new MatrixLoader().load("src/test/resources/patterns/xwing/incol.txt");
        cand.execute(matrix);
        assertTrue(matrix.getCandidatesCount() == 60);
        yWing.execute(matrix);
        matrix.validate();
        assertTrue(matrix.getCandidatesCount() <= 60);
    }

}