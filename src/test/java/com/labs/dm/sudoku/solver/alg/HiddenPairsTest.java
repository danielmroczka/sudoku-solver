/*
 * Copyright daniel.mroczka@gmail.com. All rights reserved. 
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

/**
 * @author daniel
 */
public class HiddenPairsTest {

    private final HiddenPairs hiddenPairs = new HiddenPairs();

    @Test
    @Ignore
    public void testExecute() {
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.setCellValue(3, 3, 3);
        matrix.setCellValue(3, 5, 1);
        matrix.setCellValue(4, 4, 2);

        matrix.setPossibleValues(5, 3, new HashSet<>(Arrays.asList(new Integer[]{6, 7, 8, 9})));
        matrix.setPossibleValues(5, 4, new HashSet<>(Arrays.asList(new Integer[]{7, 8})));
        matrix.setPossibleValues(5, 5, new HashSet<>(Arrays.asList(new Integer[]{6, 8, 9})));
        //WHEN
        hiddenPairs.execute(matrix);
        //THEN
        assertEquals(2, matrix.getPossibleValues(5, 3).size());
    }

}
