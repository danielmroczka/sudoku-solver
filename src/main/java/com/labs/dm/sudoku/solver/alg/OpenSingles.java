/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;

import java.util.ArrayList;
import java.util.List;

import static com.labs.dm.sudoku.solver.core.Matrix.BLOCK_SIZE;
import static com.labs.dm.sudoku.solver.utils.Utils.FULL_LIST;

/**
 * Open Singles Algorithm.
 *
 * @author Daniel Mroczka
 * @see <a href="http://www.learn-sudoku.com/open-singles.html">Open SIngles</a>
 */
public class OpenSingles implements IAlgorithm {

    @Override
    public void execute(IMatrix matrix) {
        fillOpenSinglesInRows(matrix);
        fillOpenSinglesInCols(matrix);
        fillOpenSinglesInBlocks(matrix);
    }

    /**
     * Returns true if an array has only one empty element
     *
     * @param tab
     * @return
     */
    protected int fillOpenSingles(int[] tab) {
        if (tab.length != Matrix.SIZE) {
            throw new IllegalArgumentException("Invalid array size.");
        }

        List<Integer> list = new ArrayList<>(FULL_LIST);
        int position = -1;

        for (int idx = 0; idx < tab.length; idx++) {
            if (tab[idx] == Matrix.EMPTY_VALUE) {
                position = idx;
            } else {
                list.remove((Integer) tab[idx]);
            }
        }

        if (list.size() == 1 && position >= 0) {
            tab[position] = list.get(0);
            return position;
        }
        return -1;
    }

    private void fillOpenSinglesInRows(IMatrix matrix) {
        for (int row = 0; row < Matrix.SIZE; row++) {
            int[] rows = matrix.getElemsInRow(row);
            int pos = fillOpenSingles(rows);
            if (pos >= 0) {
                matrix.setValueAt(row, pos, rows[pos]);
            }
        }
    }

    private void fillOpenSinglesInCols(IMatrix matrix) {
        for (int col = 0; col < Matrix.SIZE; col++) {
            int[] cols = matrix.getElemsInCol(col);
            int pos = fillOpenSingles(cols);
            if (pos >= 0) {
                matrix.setValueAt(pos, col, cols[pos]);
            }
        }
    }

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
