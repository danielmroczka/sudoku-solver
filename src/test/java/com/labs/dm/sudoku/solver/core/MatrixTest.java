/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.core;

import com.labs.dm.sudoku.solver.io.MatrixLoader;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * @author Daniel Mroczka
 */
public class MatrixTest {

    private IMatrix matrix;

    @Before
    public void setUp() {
        matrix = new Matrix();
    }

    @Test
    public void testEmptyCellValue() {
        assertEquals(IMatrix.EMPTY_VALUE, matrix.getValueAt(0, 0));
    }

    @Test
    public void testSetCellValue() {
        for (int row = 0; row < IMatrix.SIZE; row++) {
            for (int col = 0; col < IMatrix.SIZE; col++) {
                matrix.setValueAt(row, col, 9);
                assertEquals(9, matrix.getValueAt(row, col));
                assertTrue(matrix.getCandidates(row, col).isEmpty());
            }
        }
    }

    @Test
    public void testSetCellValueRemoveCandidates() {
        for (int row = 0; row < IMatrix.SIZE; row++) {
            for (int col = 0; col < IMatrix.SIZE; col++) {
                matrix.addCandidates(row, col, new Integer[]{1, 2, 3});
                matrix.setValueAt(row, col, 1);
                assertTrue(matrix.getCandidates(row, col).isEmpty());
            }
        }
    }

    @Test
    public void shouldPopulateRemovingCandidates() {
        //GIVEN
        for (int row = 0; row < IMatrix.SIZE; row++) {
            for (int col = 0; col < IMatrix.SIZE; col++) {
                matrix.addCandidates(row, col, new Integer[]{1});
            }
        }

        int candidates = matrix.getCandidatesCount();
        //WHEN
        matrix.setValueAt(5, 5, 1);
        //THEN
        assertEquals(candidates - 21, matrix.getCandidatesCount());
        assertEquals(60, matrix.getCandidatesCount());
    }

    @Test
    public void testIsSetValue() {
        matrix.setValueAt(0, 0, 9);
        assertTrue(matrix.isCellSet(0, 0));
        assertFalse(matrix.isCellSet(0, 1));
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
        matrix.setValueAt(0, 0, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAcceptValueLessThanZero() {
        matrix.setValueAt(0, 0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAcceptPositionOutOfRange() {
        matrix.setValueAt(10, 10, 1);
    }

    @Test
    public void shouldAcceptValue0to9() {
        for (int value = 0; value <= 9; value++) {
            matrix.setValueAt(0, 0, value);
            assertEquals(value, matrix.getValueAt(0, 0));
        }
    }

    @Test
    public void loadFromArray() {
        int[] tab = new int[81];
        tab[10] = 5;
        ((Matrix) matrix).loadFromArray(tab);
        assertEquals(5, matrix.getValueAt(1, 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void loadFromArrayWrongSize() {
        int[] tab = new int[0];
        ((Matrix) matrix).loadFromArray(tab);
    }

    @Test
    public void getElemsFromRow() {
        for (int col = 0; col < 9; col++) {
            matrix.setValueAt(0, col, col + 1);
        }
        int[] rows0 = matrix.getElemsInRow(0);
        int[] rows1 = matrix.getElemsInRow(1);

        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, rows0);
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0}, rows1);
    }

    @Test
    public void getElemsFromCol() {
        for (int row = 0; row < 9; row++) {
            matrix.setValueAt(row, 0, row + 1);
        }
        int[] cols0 = matrix.getElemsInCol(0);
        int[] cols1 = matrix.getElemsInCol(1);

        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, cols0);
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0}, cols1);
    }

    @Test
    public void getElemsFromBlock() {
        int index = 1;
        for (int row = 3; row < 6; row++) {
            for (int col = 3; col < 6; col++) {
                matrix.setValueAt(row, col, index++);
            }
        }

        int[] block0 = matrix.getElemsInBlock(0, 0);
        int[] block1 = matrix.getElemsInBlock(1, 1);
        int[] block2 = matrix.getElemsInBlock(2, 2);
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0}, block0);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, block1);
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0}, block2);
    }

    @Test
    public void shouldNewMatrixBeEmpty() {
        assertTrue(matrix.isEmpty());
    }

    @Test
    public void shouldChangedMatrixBeNotEmpty() {
        matrix.setValueAt(5, 5, 1);
        assertFalse(matrix.isEmpty());
    }

    @Test
    public void shouldClearMatrix() {
        matrix.setValueAt(5, 5, 1);
        matrix.clear();
        assertTrue(matrix.isEmpty());
    }

    @Test
    public void shouldSetCols() {
        int[] sampleSet = new int[]{9, 1, 2, 3, 4, 5, 6, 7, 8};
        for (int row = 0; row < IMatrix.SIZE; row++) {
            matrix.setCols(row, sampleSet);
            assertArrayEquals(sampleSet, matrix.getElemsInCol(row));
        }
    }

    @Test
    public void shouldSetRows() {
        int[] sampleSet = new int[]{9, 1, 2, 3, 4, 5, 6, 7, 8};
        for (int col = 0; col < IMatrix.SIZE; col++) {
            matrix.setRows(col, sampleSet);
            assertArrayEquals(sampleSet, matrix.getElemsInRow(col));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionInvalidCols() {
        matrix.setCols(0, new int[]{1, 2, 3});
    }

    @Test
    public void shouldReturnToArraz() {
        assertEquals(81, matrix.toArray().length);
    }

    @Test
    public void shouldValidateEmptyMatrix() {
        assertTrue(matrix.validate());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotValidateInvalidCol() {
        matrix.setCols(4, new int[]{1, 2, 3, 4, 4, 6, 7, 8, 9});
        matrix.validate();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotValidateInvalidRow() {
        matrix.setRows(4, new int[]{1, 2, 3, 4, 4, 6, 7, 8, 9});
        matrix.validate();
    }

    @Test
    public void shouldValidateSolvedMatrix() throws IOException {
        matrix = new MatrixLoader().load("patterns/solved.txt");
        assertTrue(matrix.validate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateInputRowIndex() throws Exception {
        matrix.setValueAt(10, 0, 1);

    }

    @Test(expected = IllegalArgumentException.class)
    public void validateInputColIndex() throws Exception {
        matrix.setValueAt(0, 10, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCellValueBelowRange() {
        matrix.getValueAt(-1, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCellValueAboveRange() {
        matrix.getValueAt(10, 10);
    }

    @Test
    public void testGetCandidatesInRow() throws Exception {
        assertEquals(9, matrix.getCandidatesInRow(0).size());
        //TODO add cases
    }

    @Test
    public void testGetCandidatesInCol() throws Exception {
        assertEquals(9, matrix.getCandidatesInCol(0).size());
        //TODO add cases
    }

    @Test
    public void testGetCandidatesInBlock() throws Exception {
        assertEquals(9, matrix.getCandidatesInBlock(0, 0).size());
        //TODO add cases
    }

    @Test
    public void shouldPrintCandidated() {
        assertNotNull(matrix.printCandidates());
    }

    @Test
    public void shouldSortNumberInAddCandidates() {
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{9, 5, 1});

        assertArrayEquals(new Integer[]{1, 5, 9}, matrix.getCandidates(0, 0).toArray(new Integer[3]));

    }

    @Test
    public void cloneTest() {
        IMatrix original = new Matrix();
        original.setValueAt(0, 0, 9);
        original.addCandidates(1, 1, new Integer[]{1, 2, 3});

        IMatrix copy = new Matrix(original);

        assertArrayEquals(original.toArray(), copy.toArray());
        assertEquals(9, copy.getValueAt(0, 0));
        assertArrayEquals(original.getCandidates(1, 1).toArray(), copy.getCandidates(1, 1).toArray());
        assertFalse(original.getCandidates(1, 1) == copy.getCandidates(1, 1));
        assertNotEquals(copy, original);
    }

    @Test
    public void changeValuePropagation() throws Exception {
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 2, new Integer[]{1, 2});
        matrix.addCandidates(3, 2, new Integer[]{1, 4});
        matrix.addCandidates(3, 0, new Integer[]{5, 7});

        matrix.addCandidates(3, 5, new Integer[]{4, 7});

        matrix.setValueAt(0, 2, 1); //TODO set to 2 should also pass the test
        assertEquals(5, matrix.getValueAt(3, 0));
    }
}
