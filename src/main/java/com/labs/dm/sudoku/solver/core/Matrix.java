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
        if (items.length % SIZE != 0) {
            throw new IllegalArgumentException("Ïncorrect input array length!");
        }
        //TODO: load data
    }
    
    @Override
    public int getCellValue(int row, int col) {
        validateInputIndex(row, col);
        return tab[row][col];
    }
    
    @Override
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
    
}
