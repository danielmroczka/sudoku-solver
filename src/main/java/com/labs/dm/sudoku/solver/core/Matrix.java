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
        loadFromArray(items);
    }

    @Override
    public int getCellValue(int row, int col) {
        validateInputIndex(row, col);
        return tab[row][col];
    }

    @Override
    public void setCellValue(int row, int col, int value) {
        validateInputIndex(row, col);
        validateInputValue(value);
        tab[row][col] = value;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean isSolved() {
        return false;
    }

    @Override
    public void clear() {
        fillWithValue(EMPTY_VALUE);
    }

    @Override
    public void fillWithValue(int value) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                setCellValue(row, col, value);
            }
        }
    }

    protected final void loadFromArray(int... items) {
        if (items.length != SIZE * SIZE) {
            throw new IllegalArgumentException("Ïncorrect input array length!");
        }
        int index = 0;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                setCellValue(row, col, items[index++]);
            }
        }
    }

    @Override
    public final Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < SIZE * SIZE;
            }

            @Override
            public Integer next() {
                int value = getCellValue(currentIndex / SIZE, currentIndex % SIZE);
                currentIndex++;
                return value;
            }

            @Override
            public void remove() {
            }
        };
    }

    private void validateInputIndex(int row, int col) {
        if (row < 0 || row > SIZE - 1) {
            throw new IllegalArgumentException("Invalid row index=" + row);
        }
        if (col < 0 || col > SIZE - 1) {
            throw new IllegalArgumentException("Invalid column index=" + col);
        }
    }

    private void validateInputValue(int value) {
        if (value < 0 || value > SIZE) {
            throw new IllegalArgumentException("Invalid input value=" + value);
        }
    }

    @Override
    public boolean isEmpty() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (getCellValue(row, col) != EMPTY_VALUE) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int[] getElemsInRow(int row) {
        return tab[row];
    }

    @Override
    public int[] getElemsInCol(int col) {
        int[] result = new int[SIZE];
        for (int row = 0; row < SIZE; row++) {
            result[row] = getCellValue(row, col);
        }
        return result;
    }

    @Override
    public int[] getElemsInBox(int rowGroup, int colGroup) {
        int index = 0;
        final int boxSize = 3;
        int[] result = new int[SIZE];
        for (int row = rowGroup * boxSize; row < (rowGroup * boxSize) + boxSize; row++) {
            for (int col = colGroup * boxSize; col < (rowGroup * boxSize) + boxSize; col++) {
                result[index++] = getCellValue(row, col);
            }
        }
        return result;
    }

}
