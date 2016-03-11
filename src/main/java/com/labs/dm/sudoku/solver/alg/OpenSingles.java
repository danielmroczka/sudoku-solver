/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;

import java.util.ArrayList;
import java.util.List;

import static com.labs.dm.sudoku.solver.core.IMatrix.*;

/**
 * Open Singles Algorithm.
 *
 * @author Daniel Mroczka
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
        for (int row = 0; row < SIZE; row++) {
            int[] rows = matrix.getElemsInRow(row);
            int pos = fillOpenSingles(rows);
            if (pos >= 0) {
                matrix.setValueAt(row, pos, rows[pos]);
            }
        }
    }

    private void fillOpenSinglesInCols(IMatrix matrix) {
        for (int col = 0; col < SIZE; col++) {
            int[] cols = matrix.getElemsInCol(col);
            int pos = fillOpenSingles(cols);
            if (pos >= 0) {
                matrix.setValueAt(pos, col, cols[pos]);
            }
        }
    }

    private void fillOpenSinglesInBlocks(IMatrix matrix) {
        for (int rowGroup = 0; rowGroup < BLOCK_SIZE; rowGroup++) {
            for (int colGroup = 0; colGroup < BLOCK_SIZE; colGroup++) {
                int[] block = matrix.getElemsInBlock(rowGroup, colGroup);
                int pos = fillOpenSingles(block);
                if (pos >= 0) {
                    matrix.setValueAt((pos - (pos / BLOCK_SIZE)) + (rowGroup * BLOCK_SIZE), pos / BLOCK_SIZE + colGroup * BLOCK_SIZE, block[pos]);
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
    protected int fillOpenSingles(int[] tab) {
        if (tab.length != SIZE) {
            throw new IllegalArgumentException("Invalid array size.");
        }

        List<Integer> list = new ArrayList<>();
        int position = -1;
        for (int elem = 1; elem <= SIZE; elem++) {
            list.add(elem);
        }
        for (int elem = 0; elem < tab.length; elem++) {
            if (tab[elem] == EMPTY_VALUE) {
                position = elem;
            } else {
                list.remove(list.indexOf(tab[elem]));
            }
        }

        if (list.size() == 1) {
            tab[position] = list.get(0);
            return position;
        }
        return -1;
    }


}
