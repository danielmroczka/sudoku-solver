/*
 * Copyright daniel.mroczka@gmail.com. All rights reserved. 
 */
package com.labs.dm.sudoku.solver.core;

import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

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
        matrix.setCellValue(0, 0, 9);
        assertEquals(9, matrix.getCellValue(0, 0));
    }
    
    @Test
    public void shouldIterateForAllItems() {
        Iterator it = matrix.iterator();
        int counter = 0;
        while (it.hasNext()) {
            it.next();
            counter++;
        }
        
        assertEquals(81, counter);
    }

}
