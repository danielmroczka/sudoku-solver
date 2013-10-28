/*
 * Copyright daniel.mroczka@gmail.com. All rights reserved. 
 */
package com.labs.dm.sudoku.solver.core;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Class represents main matrix
 * 
 * @author daniel
 */
public class Matrix implements IMatrix {

    private final int[][] tab;

    private final Set<Integer>[][] possibleValues;

    public Matrix() {
        tab = new int[SIZE][SIZE];
        possibleValues = new HashSet[SIZE][SIZE];
    }

    public Matrix(int... items) {
        this();
    }

    @Override
    public int getCellValue(int row, int col) {
        validateInputIndex(row, col);
        return tab[row][col];
    }

    public void setCellValue(int row, int col, int value) {
        validateInputIndex(row, col);
        tab[row][col] = value;
    }

    private void validateInputIndex(int row, int col) {
        if (row < 0 || row > SIZE - 1) {
            throw new IllegalArgumentException("Invalid row index");
        }
        if (col < 0 || col > SIZE - 1) {
            throw new IllegalArgumentException("Invalid column index");
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public boolean isSolved() {
        return false;
    }

    public void clear() {
        fillWithValue(EMPTY_VALUE);
    }

    public void fillWithValue(int value) {

    }

    @Override
    public final Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            private int currentIndex = 0;

            public boolean hasNext() {
                return currentIndex < SIZE * SIZE;
            }

            public Integer next() {
                int value = getCellValue(currentIndex / SIZE, currentIndex % SIZE);
                currentIndex++;
                return value;
            }

            public void remove() {
            }
        };
    }

}
