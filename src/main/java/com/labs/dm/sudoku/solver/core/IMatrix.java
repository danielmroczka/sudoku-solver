/*
 * Copyright daniel.mroczka@gmail.com. All rights reserved. 
 */
package com.labs.dm.sudoku.solver.core;

import java.util.Iterator;

/**
 *
 * @author daniel
 */
public interface IMatrix {

    int EMPTY_VALUE = 0;
    
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

    void clear();

    void fillWithValue(int value);
    
    Iterator<Integer> iterator();
}
