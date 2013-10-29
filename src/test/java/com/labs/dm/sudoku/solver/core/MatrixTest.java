/*
 * Copyright daniel.mroczka@gmail.com. All rights reserved. 
 */
package com.labs.dm.sudoku.solver.core;

import java.util.Iterator;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author daniel
 */
public class MatrixTest {

    private IMatrix matrix;

    @Before
    public void setUp() {
        matrix = new Matrix();
    }

    @Test
    public void testGetCellValue() {
        assertEquals(IMatrix.EMPTY_VALUE, matrix.getCellValue(0, 0));
    }

    @Test
    public void testSetCellValue() {
        for (int row = 0; row < IMatrix.SIZE; row++) {
            for (int col = 0; col < IMatrix.SIZE; col++) {
                matrix.setCellValue(row, col, 9);
                assertEquals(9, matrix.getCellValue(row, col));
            }
        }
    }

    @Test
    public void shouldIterateForAllItems() {
        Iterator it = matrix.iterator();
        int counter = 0;
        while (it.hasNext()) {
            it.next();
            counter++;
        }

        assertEquals(IMatrix.SIZE * IMatrix.SIZE, counter);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAcceptValueGreaterThanNine() {
        matrix.setCellValue(0, 0, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAcceptValueLessThanZero() {
        matrix.setCellValue(0, 0, -1);
    }
    
    @Test
    public void shouldAcceptValue0to9() {
        for (int value  = 0; value <= 9; value++) {
            matrix.setCellValue(0, 0, value);
        }
    }
    

}
