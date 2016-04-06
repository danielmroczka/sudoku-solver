/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.core;

import com.labs.dm.sudoku.solver.core.listener.IMatrixListener;
import com.labs.dm.sudoku.solver.executors.ContextItem;
import com.labs.dm.sudoku.solver.utils.Utils;

import java.io.Serializable;
import java.util.*;

import static com.labs.dm.sudoku.solver.utils.Utils.deepCopy;

/**
 * Class represents main matrix
 *
 * @author Daniel Mroczka
 */
public class Matrix implements IMatrix, Serializable {

    public static final int MIN_VALUE = 1;
    public static final int MAX_VALUE = 9;
    public static final int EMPTY_VALUE = 0;
    /**
     * Number of rows and columns
     */
    public static final int SIZE = 9;
    /**
     * Size of the block
     */
    public static final int BLOCK_SIZE = SIZE / 3;

    private final List<ContextItem> context = new ArrayList<>();
    private final int[][] tab;
    protected final Map<Pair, List<Integer>> possibleValues;

    private IMatrixListener listener;

    public Matrix() {
        this(new int[SIZE][SIZE]);
    }

    public Matrix(IMatrix copy) {
        tab = new int[SIZE][SIZE];
        deepCopy(((Matrix) copy).tab, tab);
        possibleValues = new HashMap<>();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                setCandidates(row, col, new ArrayList<>(copy.getCandidates(row, col)));
            }
        }
    }

    private Matrix(int[][] tab) {
        this.tab = tab;
        possibleValues = new HashMap<>();
        initCandidates();
    }

    public Matrix(int... items) {
        this();
        loadFromArray(items);
    }

    private void initCandidates() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                setCandidates(row, col, new ArrayList<Integer>());
            }
        }
    }

    @Override
    public int getValueAt(int row, int col) {
        validateInputIndex(row, col);
        return tab[row][col];
    }

    @Override
    public void setValueAt(int row, int col, int value) {
        validateInputIndex(row, col);
        validateInputValue(value);
        tab[row][col] = value;
        if (listener != null) {
            listener.onChangeValue(row, col, value);
        }
        if (isSetValue(value)) {
            getCandidates(row, col).clear();
            removeSurroundingCandidates(row, col, value);
        }

        if (listener != null && isSolved()) {
            listener.onResolved();
        }
    }

    @Override
    public void removeCandidate(int row, int col, int value) {
        if (getCandidates(row, col).remove((Integer) value)) {
            if (listener != null) {
                listener.onRemoveCandidate(row, col, value);
            }
            switch (getCandidates(row, col).size()) {
                case 1:
                    setValueWithCandidate(row, col);
                    break;
                case 0:
                    setValueAt(row, col, EMPTY_VALUE);
                    break;
            }
        }
    }

    @Override
    public void removeCandidates(int row, int col, Collection<Integer> values) {
        for (int candidate : values) {
            removeCandidate(row, col, candidate);
        }
    }

    private void removeSurroundingCandidates(int row, int col, int value) {
        if (isSetValue(value)) {
            for (int index = 0; index < SIZE; index++) {
                if (index != row) {
                    removeCandidate(index, col, value);
                }
                if (index != col) {
                    removeCandidate(row, index, value);
                }
            }
            for (int rowGroup : Utils.blockElems((row))) {
                for (int colGroup : Utils.blockElems(col)) {
                    if (row != rowGroup && col != colGroup) {
                        removeCandidate(rowGroup, colGroup, value);
                    }
                }
            }
        }
    }

    @Override
    public boolean isSolved() {
        return getSolvedItems() == SIZE * SIZE && getCandidatesCount() == 0 && validate(true);
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
    public int getCandidatesCount() {
        int counter = 0;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                counter += getCandidates(row, col).size();
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
                setValueAt(row, col, value);
            }
        }
    }

    protected final void loadFromArray(int... items) {
        if (items.length != SIZE * SIZE) {
            throw new IllegalArgumentException("Incorrect input array length!");
        }
        int index = 0;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                setValueAt(row, col, items[index++]);
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
                int value = getValueAt(currentIndex / SIZE, currentIndex % SIZE);
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
        if (value < 0 || value > MAX_VALUE) {
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
            result[row] = getValueAt(row, col);
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
                result[index++] = getValueAt(row, col);
            }
        }
        return result;
    }

    @Override
    public void setCols(int col, int[] cols) {
        validateInputArray(cols);
        for (int row = 0; row < SIZE; row++) {
            setValueAt(row, col, cols[row]);
        }
    }

    @Override
    public void setRows(int row, int[] rows) {
        validateInputArray(rows);
        for (int col = 0; col < SIZE; col++) {
            setValueAt(row, col, rows[col]);
        }
    }

    @Override
    public void setBlock(int rowGroup, int colGroup, int[] block) {
        validateInputArray(block);
        int index = 0;
        for (int row = rowGroup * BLOCK_SIZE; row < (rowGroup * BLOCK_SIZE) + BLOCK_SIZE; row++) {
            for (int col = colGroup * BLOCK_SIZE; col < (colGroup * BLOCK_SIZE) + BLOCK_SIZE; col++) {
                setValueAt(row, col, block[index++]);
            }
        }
    }

    @Override
    public List<Integer> getCandidates(int row, int col) {
        return getCandidates(new Pair(row, col));
    }

    @Override
    public List<Integer> getCandidates(Pair pair) {
        return possibleValues.get(pair);
    }

    @Override
    public int[] toArray() {
        int[] result = new int[SIZE * SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                result[row + (SIZE * col)] = getValueAt(row, col);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return Utils.printMatrix(this);
    }

    @Override
    public String printCandidates() {
        return Utils.printCandidates(this);
    }

    @Override
    public boolean validate() {
        validateRows();
        validateCols();
        return true;
    }

    @Override
    public boolean validate(boolean silentMode) {
        boolean state = false;
        try {
            state = validate();
        } catch (IllegalStateException ex) {
            if (!silentMode) {
                throw ex;
            }
        }

        return state;
    }

    private void validateCols() {
        Set<Integer> set = new HashSet<>();
        for (int col = 0; col < SIZE; col++) {
            int[] cols = getElemsInCol(col);
            for (int c : cols) {
                validateInputValue(c);
                if (!set.add(c) && isSetValue(c)) {
                    throw new IllegalStateException("Value " + c + " is not unique in col: " + col);
                }
            }
            set.clear();
        }
    }

    private void validateRows() {
        Set<Integer> set = new HashSet<>();
        for (int row = 0; row < SIZE; row++) {
            int[] rows = getElemsInRow(row);
            for (int r : rows) {
                validateInputValue(r);
                if (!set.add(r) && isSetValue(r)) {
                    throw new IllegalStateException("Value " + r + " is not unique in row: " + row);
                }
            }
            set.clear();
        }
    }

    @Override
    public boolean isCellSet(int row, int col) {
        return getValueAt(row, col) != EMPTY_VALUE;
    }

    @Override
    public void setCandidates(int row, int col, List<Integer> set) {
        for (int value : set) {
            validateInputValue(value);
        }
        possibleValues.put(new Pair(row, col), set);
    }

    @Override
    public int candidatesCountInRow(int row, int value) {
        int cnt = 0;
        for (int col = 0; col < SIZE; col++) {
            if (getCandidates(row, col).contains(value)) {
                cnt++;
            }
        }

        return cnt;
    }

    @Override
    public int candidatesCountInCol(int col, int value) {
        int cnt = 0;
        for (int row = 0; row < SIZE; row++) {
            if (getCandidates(row, col).contains(value)) {
                cnt++;
            }
        }

        return cnt;
    }

    @Override
    public void addCandidates(int row, int col, Integer[] array) {
        for (int value : array) {
            validateInputValue(value);
        }
        List<Integer> toAdd = Arrays.asList(array);
        for (int item : toAdd) {
            if (!getCandidates(row, col).contains(item)) {
                getCandidates(row, col).add(item);
            }
        }
        Collections.sort(getCandidates(row, col));
    }

    @Override
    public List<List<Integer>> getCandidatesInRow(int row) {
        List<List<Integer>> result = new ArrayList<>();
        for (int col = 0; col < SIZE; col++) {
            result.add(new ArrayList<>(getCandidates(row, col)));
        }
        return result;
    }

    @Override
    public List<List<Integer>> getCandidatesInCol(int col) {
        List<List<Integer>> result = new ArrayList<>();
        for (int row = 0; row < SIZE; row++) {
            result.add(new ArrayList<>(getCandidates(row, col)));
        }
        return result;
    }

    @Override
    public List<List<Integer>> getCandidatesInBlock(int rowBlockIndex, int colBlockIndex) {
        List<List<Integer>> result = new ArrayList<>();
        for (int row = rowBlockIndex * BLOCK_SIZE; row < rowBlockIndex * BLOCK_SIZE + BLOCK_SIZE; row++) {
            for (int col = colBlockIndex * BLOCK_SIZE; col < colBlockIndex * BLOCK_SIZE + BLOCK_SIZE; col++) {
                result.add(new ArrayList<>(getCandidates(row, col)));
            }
        }
        return result;

    }

    private boolean isSetValue(int val) {
        return MIN_VALUE <= val && val <= MAX_VALUE;
    }

    @Override
    public void addMatrixListener(IMatrixListener listener) {
        this.listener = listener;
    }

    @Override
    public void removeMatrixListener() {
        listener = null;
    }

    @Override
    public List<ContextItem> getContext() {
        return context;
    }

    @Override
    public void setValueWithCandidate(int row, int col) {
        if (getCandidates(row, col).size() == 1) {
            setValueAt(row, col, getCandidates(row, col).get(0));
        }
    }

}
