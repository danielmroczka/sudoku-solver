/*
 * Copyright daniel.mroczka@gmail.com. All rights reserved. 
 */
package com.labs.dm.sudoku.solver.core;

import java.util.Iterator;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
        for (int value = 0; value <= 9; value++) {
            matrix.setCellValue(0, 0, value);
        }
    }

    @Test
    public void loadFromArray() {
        int[] tab = new int[81];
        tab[10] = 5;
        ((Matrix) matrix).loadFromArray(tab);
        assertEquals(5, matrix.getCellValue(1, 1));
    }

    @Test
    public void getElemsFromRow() {
        for (int col = 0; col < 9; col++) {
            matrix.setCellValue(0, col, col + 1);
        }
        int[] rows0 = matrix.getElemsInRow(0);
        int[] rows1 = matrix.getElemsInRow(1);

        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, rows0);
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0}, rows1);
    }

    @Test
    public void getElemsFromCol() {
        for (int row = 0; row < 9; row++) {
            matrix.setCellValue(row, 0, row + 1);
        }
        int[] cols0 = matrix.getElemsInCol(0);
        int[] cols1 = matrix.getElemsInCol(1);

        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, cols0);
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0}, cols1);
    }

    @Test
    public void getElemsFromBox() {
        int index = 1;
        for (int row = 3; row < 6; row++) {
            for (int col = 3; col < 6; col++) {
                matrix.setCellValue(row, col, index++);
            }
        }

        int[] box1 = matrix.getElemsInBox(1, 1);
        int[] box0 = matrix.getElemsInBox(0, 0);
        int[] box2 = matrix.getElemsInBox(2, 2);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, box1);
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0}, box0);
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0}, box2);
    }
    
    @Test
    public void shouldNewMatrixBeEmpty() {
        assertTrue(matrix.isEmpty());
    }
    
    @Test
    public void shouldChangedMatrixBeNotEmpty() {
        matrix.setCellValue(5, 5, 1);
        assertFalse(matrix.isEmpty());
    }
    
    @Test
    public void shouldClearMatrix() {
        matrix.setCellValue(5, 5, 1);
        matrix.clear();
        assertTrue(matrix.isEmpty());
    }
    
}
