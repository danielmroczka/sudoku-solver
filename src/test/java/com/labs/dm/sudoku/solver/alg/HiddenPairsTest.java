/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import com.labs.dm.sudoku.solver.io.MatrixLoader;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

/**
 * @author daniel
 */
public class HiddenPairsTest {

    private final HiddenPairs hiddenPairs = new HiddenPairs();

    @Test
    // @Ignore
    public void testExecute() {
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.setCellValue(0, 3, 3);
        matrix.setCellValue(0, 5, 1);
        matrix.setCellValue(0, 4, 2);

        matrix.setPossibleValues(0, 1, new HashSet<>(Arrays.asList(new Integer[]{6, 7, 8, 9})));
        matrix.setPossibleValues(0, 6, new HashSet<>(Arrays.asList(new Integer[]{7, 8})));
        matrix.setPossibleValues(0, 7, new HashSet<>(Arrays.asList(new Integer[]{6, 8, 9})));
        //WHEN
        hiddenPairs.execute(matrix);
        System.out.println(matrix.printCandidates());
        hiddenPairs.execute(matrix);
        //THEN
        assertEquals(2, matrix.getPossibleValues(0, 1).size());
        assertEquals(2, matrix.getPossibleValues(0, 7).size());
    }

    @Test
    public void test() throws IOException {
        MatrixLoader loader = new MatrixLoader();
        IMatrix matrix = loader.load("src/test/resources/patterns/hiddenPair2.txt");
        IAlgorithm alg = new HiddenPairs();
        IAlgorithm cand = new GenerateCandidates();

        cand.execute(matrix);
        System.out.println(matrix.toString());
        alg.execute(matrix);
    }

}
