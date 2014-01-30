/*
 * Copyright daniel.mroczka@gmail.com. All rights reserved. 
 */

package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import static org.junit.Assert.assertEquals;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author daniel
 */
public class HiddenPairsTest {
    
    private final HiddenPairs hiddenPairs = new HiddenPairs();
    private final ShowPossibles pos = new ShowPossibles();

    @Test
    @Ignore
    public void testExecute() {
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.setCellValue(3, 3, 3);
        matrix.setCellValue(3, 5, 1);
        matrix.setCellValue(4, 4, 2);
        pos.execute(matrix);
        //WHEN
        hiddenPairs.execute(matrix);
        //THEN
        assertEquals(2, matrix.getPossibleValues(5,3).size());
    }
    
}
