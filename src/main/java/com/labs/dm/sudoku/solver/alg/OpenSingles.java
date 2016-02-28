/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;

import java.util.ArrayList;
import java.util.List;

/**
 * Open Singles Algorithm.
 *
 * @author daniel
 *         http://www.learn-sudoku.com/open-singles.html
 */
public class OpenSingles implements IAlgorithm {

    @Override
    public void execute(IMatrix matrix) {
        matrix.validate();
        fillOpenSinglesInRows(matrix);
        fillOpenSinglesInCols(matrix);
        fillOpenSinglesInBlocks(matrix);
    }

    private void fillOpenSinglesInRows(IMatrix matrix) {
        for (int row = 0; row < IMatrix.SIZE; row++) {
            int[] rows = matrix.getElemsInRow(row);
            int pos = fillOpenSingles(rows);
            if (pos >= 0) {
                matrix.setValueAt(row, pos, rows[pos]);
            }
        }
    }

    private void fillOpenSinglesInCols(IMatrix matrix) {
        for (int col = 0; col < IMatrix.SIZE; col++) {
            int[] cols = matrix.getElemsInCol(col);
            int pos = fillOpenSingles(cols);
            if (pos >= 0) {
                matrix.setValueAt(pos, col, cols[pos]);
            }
        }
    }

    private void fillOpenSinglesInBlocks(IMatrix matrix) {
        for (int rowGroup = 0; rowGroup < IMatrix.BLOCK_SIZE; rowGroup++) {
            for (int colGroup = 0; colGroup < IMatrix.BLOCK_SIZE; colGroup++) {
                int[] block = matrix.getElemsInBlock(rowGroup, colGroup);
                int pos = fillOpenSingles(block);
                if (pos >= 0) {
                    matrix.setValueAt(pos - (pos / 3) + rowGroup * IMatrix.BLOCK_SIZE, pos / 3 + colGroup * IMatrix.BLOCK_SIZE, block[pos]);
                }
            }
        }
    }

    /**
     * Returns true if array has only one empty element
     *
     * @param tab
     * @return
     */
    protected boolean fillOpenSinglesNew(int[] tab) {
        if (tab.length != IMatrix.SIZE) {
            throw new IllegalArgumentException("Invalid array size.");
        }

        int position = -1, cnt = 0, sum = 0;
        for (int elem = 0; elem < tab.length; elem++) {
            if (tab[elem] == IMatrix.EMPTY_VALUE) {
                if (++cnt > 1) {
                    return false;
                }
                position = elem;
            }
            sum += tab[elem];
        }

        if (cnt == 1) {
            tab[position] = 45 - sum;
            return true;
        }
        return false;
    }

    protected int fillOpenSingles(int[] tab) {
        if (tab.length != IMatrix.SIZE) {
            throw new IllegalArgumentException("Invalid array size.");
        }

        List<Integer> set = new ArrayList<>();
        int position = -1;
        for (int elem = 1; elem <= IMatrix.SIZE; elem++) {
            set.add(elem);
        }
        for (int elem = 0; elem < tab.length; elem++) {
            if (tab[elem] == IMatrix.EMPTY_VALUE) {
                position = elem;
            } else {
                set.remove(set.indexOf(tab[elem]));
            }
        }

        if (set.size() == 1) {
            tab[position] = set.get(0);
            return position;
        }
        return -1;
    }


}
