/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.core;

import com.labs.dm.sudoku.solver.core.listener.IMatrixListener;
import com.labs.dm.sudoku.solver.executors.ContextItem;

import java.util.List;

/**
 * @author Daniel Mroczka
 */
public interface IMatrix extends Iterable<Integer> {

    /**
     * Returns cell value from provided position
     *
     * @param row
     * @param col
     * @return value
     */
    int getValueAt(int row, int col);

    /**
     * Sets cell value at provided position
     *
     * @param row
     * @param col
     * @param value
     */
    void setValueAt(int row, int col, int value);

    void removeCandidate(int row, int col, int value);

    void removeCandidate(int row, int col, List<Integer> values);

    /**
     * Returns true if matrix is solved.
     * Matrix is solved when all elements are valid and are not empty
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
     * Return count of all candidates
     *
     * @return
     */
    int getCandidatesCount();

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

    /**
     * Get collection of candidates for selected cell
     *
     * @param row
     * @param col
     * @return
     */
    List<Integer> getCandidates(int row, int col);

    /**
     * Get collection of candidates for selected cell
     *
     * @param pair
     * @return
     */
    List<Integer> getCandidates(Pair pair);

    void setCandidates(int row, int col, List<Integer> set);

    /**
     * Returns SIZE*SIZE elements array starting from first row, iterate through
     * columns and go to next row.
     * <p/>
     * Matrix: 1,2,3 4,5,6 7,8,9
     * <p/>
     * Result: 1,2,3,4,5,6,7,8,9
     *
     * @return
     */
    int[] toArray();

    /**
     * Prints matrix values or candidates
     *
     * @return
     */
    String printCandidates();

    /**
     * Validates values uniqueness
     *
     * @return
     */
    boolean validate();

    boolean validate(boolean silentMode);

    /**
     * Returns number of already resolved cells
     *
     * @return
     */
    int getSolvedItems();

    /**
     * Returns information if cell is set (value >0 or value <10)
     *
     * @param row
     * @param col
     * @return
     */
    boolean isCellSet(int row, int col);

    int candidatesCountInRow(int row, int value);

    int candidatesCountInCol(int col, int value);

    void addCandidates(int row, int col, Integer[] array);

    List<List<Integer>> getCandidatesInRow(int row);

    List<List<Integer>> getCandidatesInCol(int col);

    List<List<Integer>> getCandidatesInBlock(int rowBlockIndex, int colBlockIndex);

    void addMatrixListener(IMatrixListener listener);

    void removeMatrixListener();

    List<ContextItem> getContext();
}
