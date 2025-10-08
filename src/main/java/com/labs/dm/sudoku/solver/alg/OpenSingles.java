/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;

import static com.labs.dm.sudoku.solver.core.Matrix.BLOCK_SIZE;

/**
 * Implements the Open Singles algorithm.
 * An open single is a cell that is the only empty cell in a row, column, or block.
 * The value of that cell must be the missing number from that row, column, or block.
 *
 * @see <a href="http://www.learn-sudoku.com/open-singles.html">Open Singles</a>
 */
public class OpenSingles implements IAlgorithm {

    private static final int SUM_1_TO_9 = 45;

    @Override
    public void execute(IMatrix matrix) {
        fillOpenSinglesInRows(matrix);
        fillOpenSinglesInCols(matrix);
        fillOpenSinglesInBlocks(matrix);
    }

    /**
     * Finds a single empty cell in a row, column or block and fills it with the missing number.
     *
     * @param tab An array representing a row, column, or block.
     * @return The position of the filled cell, or -1 if no cell was filled.
     */
    protected int fillOpenSingles(int[] tab) {
        if (tab.length != Matrix.SIZE) {
            throw new IllegalArgumentException("Invalid array size.");
        }

        int emptyPos = -1;
        int emptyCount = 0;
        int sum = 0;
        for (int i = 0; i < tab.length; i++) {
            if (tab[i] == Matrix.EMPTY_VALUE) {
                emptyCount++;
                emptyPos = i;
            }
            sum += tab[i];
        }

        if (emptyCount == 1) {
            int missingNumber = SUM_1_TO_9 - sum;
            tab[emptyPos] = missingNumber;
            return emptyPos;
        }

        return -1;
    }

    /**
     * Finds and fills open singles in each row of the matrix.
     *
     * @param matrix The Sudoku matrix.
     */
    private void fillOpenSinglesInRows(IMatrix matrix) {
        for (int row = 0; row < Matrix.SIZE; row++) {
            int[] rows = matrix.getElemsInRow(row);
            int pos = fillOpenSingles(rows);
            if (pos >= 0) {
                matrix.setValueAt(row, pos, rows[pos]);
            }
        }
    }

    /**
     * Finds and fills open singles in each column of the matrix.
     *
     * @param matrix The Sudoku matrix.
     */
    private void fillOpenSinglesInCols(IMatrix matrix) {
        for (int col = 0; col < Matrix.SIZE; col++) {
            int[] cols = matrix.getElemsInCol(col);
            int pos = fillOpenSingles(cols);
            if (pos >= 0) {
                matrix.setValueAt(pos, col, cols[pos]);
            }
        }
    }

    /**
     * Finds and fills open singles in each block of the matrix.
     *
     * @param matrix The Sudoku matrix.
     */
    private void fillOpenSinglesInBlocks(IMatrix matrix) {
        for (int rowGroup = 0; rowGroup < Matrix.BLOCK_SIZE; rowGroup++) {
            for (int colGroup = 0; colGroup < Matrix.BLOCK_SIZE; colGroup++) {
                int[] block = matrix.getElemsInBlock(rowGroup, colGroup);
                int pos = fillOpenSingles(block);
                if (pos >= 0) {
                    int row = rowGroup * BLOCK_SIZE + pos / 3;
                    int col = colGroup * BLOCK_SIZE + pos % 3;
                    matrix.setValueAt(row, col, block[pos]);
                }
            }
        }
    }

}
