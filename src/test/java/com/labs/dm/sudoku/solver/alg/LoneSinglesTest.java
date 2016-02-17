/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;

/**
 * @author daniel
 */
public class LoneSinglesTest {

    private final LoneSingles loneSingles = new LoneSingles();

    @Test
    public void testExecute() throws IOException {
        //GIVEN
        IMatrix matrix = new Matrix();
        IMatrix resolvedMatrix = new com.labs.dm.sudoku.solver.io.MatrixLoader().load("src/test/resources/patterns/solved.txt");

        for (int row = 0; row < IMatrix.SIZE; row++) {
            for (int col = 0; col < IMatrix.SIZE; col++) {
                matrix.getCandidates(row, col).add(resolvedMatrix.getCellValue(row, col));
            }
        }
        //WHEN
        loneSingles.execute(matrix);
        //THEN
        matrix.validate();
        assertArrayEquals(resolvedMatrix.toArray(), matrix.toArray());
    }
}
