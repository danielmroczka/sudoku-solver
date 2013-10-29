/*
 * Copyright daniel.mroczka@gmail.com. All rights reserved. 
 */
package com.labs.dm.sudoku.solver.core;

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

    /**
     * Returns cell value from provided position
     *
     * @param row
     * @param col
     * @return
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
     * Returns true if matrix is solved.
     *
     * @return
     */
    boolean isSolved();

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

}
