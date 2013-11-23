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

    final int BOX_SIZE = SIZE / 3;

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
    int[] getElemsInBox(int rowGroup, int colGroup);

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
     * Sets elements in box
     *
     * @param row
     * @param col
     * @param box
     */
    void setBox(int row, int col, int[] box);

    Set<Integer>[][] getPossibleValues();

}
