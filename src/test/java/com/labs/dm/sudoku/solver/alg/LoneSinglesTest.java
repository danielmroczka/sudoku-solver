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
        IMatrix matrix = new Matrix();
        for (int row = 0; row < IMatrix.SIZE; row++) {
            for (int col = 0; col < IMatrix.SIZE; col++) {
                if (matrix.getPossibleValues()[row][col] == null) {
                    matrix.getPossibleValues()[row][col] = new HashSet<>();
                }
                matrix.getPossibleValues()[row][col].add(1);
            }
        }
        loneSingles.execute(matrix);
        
        for (int row = 0; row < IMatrix.SIZE; row++) {
            for (int col = 0; col < IMatrix.SIZE; col++) {
                assertEquals(1, matrix.getCellValue(row, col));
            }
        }
        
    }

}
