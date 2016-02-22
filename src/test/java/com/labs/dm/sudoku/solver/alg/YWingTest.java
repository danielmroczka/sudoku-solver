package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import com.labs.dm.sudoku.solver.io.MatrixLoader;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by daniel on 2016-02-21.
 */
public class YWingTest {

    private IAlgorithm alg = new YWing();
    private IAlgorithm cand = new GenerateCandidates();

    @Test
    public void simple() {

        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{2, 7});
        matrix.addCandidates(0, 4, new Integer[]{1, 2});
        matrix.addCandidates(5, 0, new Integer[]{1, 7});
        matrix.addCandidates(5, 4, new Integer[]{1, 9});
        //WHEN
        new YWing().execute(matrix);
        //THEN
        assertEquals(1, matrix.getCandidates(5, 4).size());
    }

    @Test
    public void testFromFile1() throws IOException {
        IMatrix matrix = new MatrixLoader().load("src/test/resources/patterns/xwing/incol.txt");
        cand.execute(matrix);
        assertTrue(matrix.getCandidatesCount() == 60);
        alg.execute(matrix);
        matrix.validate();
        assertTrue(matrix.getCandidatesCount() < 58);
    }

}