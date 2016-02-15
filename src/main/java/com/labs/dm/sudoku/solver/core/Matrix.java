/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.core;

import java.util.Collection;
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

    private final Collection<Integer>[][] possibleValues;

    public Matrix() {
        tab = new int[SIZE][SIZE];
        possibleValues = new HashSet[SIZE][SIZE];
        initCandidates();
    }

    private void initCandidates() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                setPossibleValues(row, col, new HashSet<Integer>());
            }
        }
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
        getPossibleValues(row, col).clear();
        removeCandidates(row, col, value);
    }

    private void removeCandidates(int row, int col, int value) {
        if (value > 0 && value < 10) {
            for (int r = 0; r < SIZE; r++) {
                getPossibleValues(r, col).remove(value);
            }
            for (int c = 0; c < SIZE; c++) {
                getPossibleValues(row, c).remove(value);
            }
            int rowStart = (row / 3) * IMatrix.BLOCK_SIZE;
            int colStart = (col / 3) * IMatrix.BLOCK_SIZE;
            for (int rowGroup = rowStart; rowGroup < rowStart + IMatrix.BLOCK_SIZE; rowGroup++) {
                for (int colGroup = colStart; colGroup < colStart + IMatrix.BLOCK_SIZE; colGroup++) {
                    getPossibleValues(rowGroup, colGroup).remove(value);
                }
            }
        }
    }

    @Override
    public boolean isSolved() {
        return getSolvedItems() == SIZE * SIZE;
    }

    @Override
    public int getSolvedItems() {
        int counter = 0;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (isCellSet(row, col)) {
                    counter++;
                }
            }
        }
        return counter;
    }

    @Override
    public int getCandidates() {
        int counter = 0;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                counter += getPossibleValues(row, col).size();
            }
        }
        return counter;
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
                //Do nothing
            }
        };
    }

    private void validateInputIndex(int row, int col, int range) {
        if (row < 0 || row > range) {
            throw new IllegalArgumentException("Invalid row index=" + row);
        }
        if (col < 0 || col > range) {
            throw new IllegalArgumentException("Invalid column index=" + col);
        }
    }

    private void validateInputIndex(int row, int col) {
        validateInputIndex(row, col, SIZE - 1);
    }

    private void validateInputIndex(int index) {
        if (index < 0 || index > SIZE - 1) {
            throw new IllegalArgumentException("Invalid index=" + index);
        }
    }

    private void validateInputValue(final int value) {
        if (value < 0 || value > SIZE) {
            throw new IllegalArgumentException("Invalid input value=" + value);
        }
    }

    private void validateInputArray(final int[] array) {
        if (array == null || array.length != SIZE) {
            throw new IllegalArgumentException("Invalid input array size");
        }
        for (int value : array) {
            validateInputValue(value);
        }
    }

    @Override
    public boolean isEmpty() {
        return getSolvedItems() == 0;
    }

    @Override
    public int[] getElemsInRow(int row) {
        validateInputIndex(row);
        return tab[row];
    }

    @Override
    public int[] getElemsInCol(int col) {
        validateInputIndex(col);
        int[] result = new int[SIZE];
        for (int row = 0; row < SIZE; row++) {
            result[row] = getCellValue(row, col);
        }
        return result;
    }

    @Override
    public int[] getElemsInBlock(int rowGroup, int colGroup) {
        validateInputIndex(rowGroup, colGroup, BLOCK_SIZE);
        int index = 0;
        int[] result = new int[SIZE];
        for (int row = rowGroup * BLOCK_SIZE; row < (rowGroup * BLOCK_SIZE) + BLOCK_SIZE; row++) {
            for (int col = colGroup * BLOCK_SIZE; col < (colGroup * BLOCK_SIZE) + BLOCK_SIZE; col++) {
                result[index++] = getCellValue(row, col);
            }
        }
        return result;
    }

    @Override
    public void setCols(int col, int[] cols) {
        validateInputArray(cols);
        for (int row = 0; row < SIZE; row++) {
            setCellValue(row, col, cols[row]);
        }
    }

    @Override
    public void setRows(int row, int[] rows) {
        validateInputArray(rows);
        tab[row] = rows;
    }

    @Override
    public void setBlock(int rowGroup, int colGroup, int[] block) {
        validateInputArray(block);
        int index = 0;
        for (int row = rowGroup * BLOCK_SIZE; row < (rowGroup * BLOCK_SIZE) + BLOCK_SIZE; row++) {
            for (int col = colGroup * BLOCK_SIZE; col < (colGroup * BLOCK_SIZE) + BLOCK_SIZE; col++) {
                setCellValue(row, col, block[index++]);
            }
        }
    }

    @Override
    public Collection<Integer> getPossibleValues(int row, int col) {
        return possibleValues[row][col];
    }

    @Override
    public int[] toArray() {
        int[] result = new int[SIZE * SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                result[row + (SIZE * col)] = getCellValue(row, col);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(100);
        sb.append("Solved= " + getSolvedItems() + ", candidates= " + getCandidates());
        sb.append(System.lineSeparator());
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                sb.append(getCellValue(row, col));
                if (col < SIZE - 1) {
                    if (col % 3 == 2) {
                        sb.append(" | ");
                    } else {
                        sb.append(" ");
                    }
                }
            }
            sb.append(System.lineSeparator());
            if (row % 3 == 2 && row < SIZE - 1) {
                sb.append("------+-------+------").append(System.lineSeparator());
            }

        }
        return sb.toString();
    }

    @Override
    public String printCandidates() {
        StringBuilder sb = new StringBuilder(100);
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                sb.append("(" + row + ", " + col + ") - " + getCellValue(row, col) + " ");
                sb.append(getPossibleValues(row, col));
                sb.append(System.lineSeparator());
            }
            sb.append("------").append(System.lineSeparator());

        }
        return sb.toString();
    }

    @Override
    public boolean validate() {
        Set<Integer> set = new HashSet<>();
        validateRows(set);
        validateCols(set);
        return true;
    }

    private void validateCols(Set<Integer> set) {
        for (int col = 0; col < SIZE; col++) {
            int[] cols = getElemsInCol(col);
            for (int c : cols) {
                validateInputValue(c);
                if (!set.add(c) && c != EMPTY_VALUE) {
                    throw new IllegalStateException("Value " + c + " is not unique in col: " + col);
                }
            }
            set.clear();
        }
    }

    private void validateRows(Set<Integer> set) {
        for (int row = 0; row < SIZE; row++) {
            int[] rows = getElemsInRow(row);
            for (int r : rows) {
                validateInputValue(r);
                if (!set.add(r) && r != EMPTY_VALUE) {
                    throw new IllegalStateException("Value " + r + " is not unique in row: " + row);
                }
            }
            set.clear();
        }
    }

    @Override
    public boolean isCellSet(int row, int col) {
        return getCellValue(row, col) != EMPTY_VALUE;
    }

    @Override
    public void setPossibleValues(int row, int col, Collection<Integer> set) {
        possibleValues[row][col] = set;
    }

    @Override
    public int occurenciesInRow(int row, int value) {
        int cnt = 0;
        for (int col = 0; col < IMatrix.SIZE; col++) {
            if (getPossibleValues(row, col).contains(value)) {
                cnt++;
            }
        }

        return cnt;
    }

    @Override
    public int occurenciesInCol(int col, int value) {
        int cnt = 0;
        for (int row = 0; row < IMatrix.SIZE; row++) {
            if (getPossibleValues(row, col).contains(value)) {
                cnt++;
            }
        }

        return cnt;
    }
}
