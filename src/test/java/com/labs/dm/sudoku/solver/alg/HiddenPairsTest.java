/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import com.labs.dm.sudoku.solver.io.MatrixLoader;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author daniel
 */
public class HiddenPairsTest {

    private final HiddenPairs hiddenPairs = new HiddenPairs();

    @Test
    public void testExecute() {
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.setCellValue(0, 3, 3);
        matrix.setCellValue(0, 5, 1);
        matrix.setCellValue(0, 4, 2);

        matrix.addCandidates(0, 1, new Integer[]{6, 7, 8, 9});
        matrix.addCandidates(0, 6, new Integer[]{7, 8});
        matrix.addCandidates(0, 7, new Integer[]{6, 8, 9});
        //WHEN
        hiddenPairs.execute(matrix);
        //THEN
        assertEquals(2, matrix.getCandidates(0, 1).size());
        assertEquals(2, matrix.getCandidates(0, 7).size());
    }

    @Test
    public void testBlock() {
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.setCellValue(0, 0, 3);
        matrix.setCellValue(0, 2, 1);
        matrix.setCellValue(1, 1, 2);

        matrix.addCandidates(0, 1, new Integer[]{4, 5, 8});
        matrix.addCandidates(1, 0, new Integer[]{4, 5, 7});
        matrix.addCandidates(1, 2, new Integer[]{4, 5});
        matrix.addCandidates(2, 0, new Integer[]{6, 7, 8, 9});
        matrix.addCandidates(2, 1, new Integer[]{7, 8});
        matrix.addCandidates(2, 2, new Integer[]{6, 8, 9});
        //WHEN
        int candidates = matrix.getCandidatesCount();
        hiddenPairs.execute(matrix);
        //THEN
        assertEquals(2, matrix.getCandidates(2, 0).size());
        assertEquals(2, matrix.getCandidates(2, 2).size());
        // assertEquals(candidates-3, matrix.getCandidatesCount());
    }

    @Test
    public void test() throws IOException {
        MatrixLoader loader = new MatrixLoader();
        IMatrix matrix = loader.load("src/test/resources/patterns/hiddenPair2.txt");
        IAlgorithm alg = new HiddenPairs();
        IAlgorithm cand = new GenerateCandidates();

        cand.execute(matrix);
        alg.execute(matrix);
    }

}
