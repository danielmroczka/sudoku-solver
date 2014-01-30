/*
 * Copyright daniel.mroczka@gmail.com. All rights reserved. 
 */
package com.labs.dm.sudoku.solver.core;

import java.util.Set;

/**
 *
 * @author daniel
 */
public interface IMatrix extends Iterable<Integer> {

    int EMPTY_VALUE = 0;

    /**
     * Number of rows and columns
     */
    int SIZE = 9;

    final int BLOCK_SIZE = SIZE / 3;

    /**
     * Returns cell value from provided position
     *
     * @param row
     * @param col
     * @return value
     */
    int getCellValue(int row, int col);

    /**
     * Sets cell value at provided position
     *
     * @param row
     * @param col
     * @param value
     */
    void setCellValue(int row, int col, int value);

    /**
     * Returns true if matrix is solved. Matrix is solved when all elements are
     * valid and are not empty
     *
     * @return
     */
    boolean isSolved();

    /**
     * Returns true if all elements in matrix are empty
     *
     * @return
     */
    boolean isEmpty();

    /**
     * Fills all cells with empty value.
     */
    void clear();

    /**
     * Fills all cells with provided value.
     *
     * @param value
     */
    void fillWithValue(int value);

    /**
     * Returns SIZE element array items from rowGroup and colGroup
     *
     * @param rowGroup range value 0..2
     * @param colGroup range value 0..2
     * @return
     */
    int[] getElemsInBlock(int rowGroup, int colGroup);

    /**
     * Returns SIZE element array items form col
     *
     * @param col range value 0..8
     * @return
     */
    int[] getElemsInCol(int col);

    /**
     * Returns SIZE element array items form row
     *
     * @param row range value 0..8
     * @return
     */
    int[] getElemsInRow(int row);

    /**
     * Sets elements in col
     *
     * @param col
     * @param cols
     */
    void setCols(int col, int[] cols);

    /**
     * Sets elements in row
     *
     * @param row
     * @param rows
     */
    void setRows(int row, int[] rows);

    /**
     * Sets elements in block
     *
     * @param row
     * @param col
     * @param block
     */
    void setBlock(int row, int col, int[] block);

    Set<Integer> getPossibleValues(int row, int col);
    
    void setPossibleValues(int row, int col, Set<Integer> set);

    /**
     * Returns SIZE*SIZE elements array starting from first row, iterate through
     * columns and go to next row.
     *
     * Matrix: 1,2,3 4,5,6 7,8,9
     *
     * Result: 1,2,3,4,5,6,7,8,9
     *
     * @return
     */
    int[] toArray();

    boolean validate();

    int getSetElems();
    
    boolean isCellSet(int row, int col);

}
