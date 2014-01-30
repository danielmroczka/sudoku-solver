/*
 * Copyright daniel.mroczka@gmail.com. All rights reserved. 
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import java.util.HashSet;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author daniel
 */
public class LoneSinglesTest {

    private final LoneSingles loneSingles = new LoneSingles();

    @Test
    public void testExecute() {
        //GIVEN
        IMatrix matrix = new Matrix();
        for (int row = 0; row < IMatrix.SIZE; row++) {
            for (int col = 0; col < IMatrix.SIZE; col++) {
                if (matrix.getPossibleValues(row,col) == null) {
                    matrix.setPossibleValues(row,col,new HashSet<Integer>());
                }
                matrix.getPossibleValues(row,col).add(1);
            }
        }
        //WHEN
        loneSingles.execute(matrix);
        //THEN
        for (int row = 0; row < IMatrix.SIZE; row++) {
            for (int col = 0; col < IMatrix.SIZE; col++) {
                assertEquals(1, matrix.getCellValue(row, col));
            }
        }
    }
}
