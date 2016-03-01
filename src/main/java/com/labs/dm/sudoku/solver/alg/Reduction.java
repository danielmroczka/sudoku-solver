/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Pair;
import com.labs.dm.sudoku.solver.utils.CounterHashMap;
import com.labs.dm.sudoku.solver.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.labs.dm.sudoku.solver.core.IMatrix.BLOCK_SIZE;
import static com.labs.dm.sudoku.solver.core.IMatrix.SIZE;

/**
 * Redcution Algorithm.
 * http://www.thonky.com/sudoku/box-line-reduction/
 *
 * @author daniel
 */
public class Reduction implements IAlgorithm {

    @Override
    public void execute(IMatrix matrix) {
        reduceInRows(matrix);
        reduceInCols(matrix);
        reduceInBlock(matrix);
    }

    private void reduceInBlock(IMatrix matrix) {
        for (int colGroup = 0; colGroup < BLOCK_SIZE; colGroup++) {
            for (int rowGroup = 0; rowGroup < BLOCK_SIZE; rowGroup++) {
                CounterHashMap<Integer> map = getOccurenceInBlockMap(matrix, rowGroup, colGroup);

                for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                    if (entry.getValue() == 2 || entry.getValue() == 3) {
                        List<Pair> list = new ArrayList<>();
                        for (int col = colGroup * BLOCK_SIZE; col < (colGroup + 1) * BLOCK_SIZE; col++) {
                            for (int row = rowGroup * BLOCK_SIZE; row < (rowGroup + 1) * BLOCK_SIZE; row++) {
                                if (matrix.getCandidates(row, col).contains(entry.getKey())) {
                                    list.add(new Pair(row, col));
                                }
                            }
                        }
                        if (list.isEmpty()) {
                            continue;
                        }

                        boolean theSameRow = true;
                        boolean theSameCol = true;
                        Pair item = list.get(0);

                        for (Pair p : list) {
                            theSameCol = theSameCol && item.getCol() == p.getCol();
                            theSameRow = theSameRow && item.getRow() == p.getRow();
                        }

                        if (theSameCol) {
                            for (int row = 0; row < IMatrix.SIZE; row++) {
                                boolean found = true;
                                for (Pair p : list) {
                                    if (p.getRow() == row) {
                                        found = false;
                                    }
                                }
                                if (found) {
                                    matrix.removeCandidate(row, item.getCol(), entry.getKey());
                                }
                            }
                        }

                        if (theSameRow) {
                            for (int col = 0; col < IMatrix.SIZE; col++) {
                                boolean found = true;
                                for (Pair p : list) {
                                    if (p.getCol() == col) {
                                        found = false;
                                    }
                                }
                                if (found) {
                                    matrix.removeCandidate(item.getRow(), col, entry.getKey());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void reduceInCols(IMatrix matrix) {
        for (int col = 0; col < SIZE; col++) {
            CounterHashMap<Integer> map = getOccurenceInColMap(matrix, col);

            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                if (entry.getValue() == 2 || entry.getValue() == 3) {
                    List<Integer> pos = getPositions(matrix, col, entry.getKey());
                    if (pos.size() > 0 && Utils.theSameBlock(pos.toArray(new Integer[pos.size()]))) {
                        removeInBlockCol(matrix, col, entry.getKey(), pos);
                    }
                }
            }
        }
    }

    private void reduceInRows(IMatrix matrix) {
        for (int row = 0; row < SIZE; row++) {
            CounterHashMap<Integer> map = getOccurenceInRowMap(matrix, row);

            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                if (entry.getValue() == 2 || entry.getValue() == 3) {
                    List<Integer> pos = getPositions2(matrix, row, entry.getKey());
                    if (pos.size() > 0 && Utils.theSameBlock(pos.toArray(new Integer[pos.size()]))) {
                        removeInBlockRow(matrix, row, entry.getKey(), pos);
                    }
                }
            }
        }
    }

    private List<Integer> getPositions(IMatrix matrix, int col, int key) {
        List<Integer> pos = new ArrayList<>();
        for (int row = 0; row < SIZE; row++) {
            if (matrix.getCandidates(row, col).contains(key)) {
                pos.add(row);
            }
        }
        return pos;
    }

    private List<Integer> getPositions2(IMatrix matrix, int row, int key) {
        List<Integer> pos = new ArrayList<>();
        for (int col = 0; col < SIZE; col++) {
            if (matrix.getCandidates(row, col).contains(key)) {
                pos.add(col);
            }
        }
        return pos;
    }

    private CounterHashMap<Integer> getOccurenceInBlockMap(IMatrix matrix, int rowGroup, int colGroup) {
        CounterHashMap<Integer> map = new CounterHashMap();

        for (int col = colGroup * BLOCK_SIZE; col < (colGroup + 1) * BLOCK_SIZE; col++) {
            for (int row = rowGroup * BLOCK_SIZE; row < (rowGroup + 1) * BLOCK_SIZE; row++) {
                for (int key : matrix.getCandidates(row, col)) {
                    map.inc(key);
                }

            }
        }

        return map;
    }

    private CounterHashMap<Integer> getOccurenceInColMap(IMatrix matrix, int col) {
        CounterHashMap<Integer> map = new CounterHashMap();
        for (int row = 0; row < SIZE; row++) {
            for (int key : matrix.getCandidates(row, col)) {
                map.inc(key);
            }
        }
        return map;
    }

    private CounterHashMap<Integer> getOccurenceInRowMap(IMatrix matrix, int row) {
        CounterHashMap map = new CounterHashMap();
        for (int col = 0; col < SIZE; col++) {
            for (int key : matrix.getCandidates(row, col)) {
                map.inc(key);
            }
        }
        return map;
    }

    private void removeInBlockCol(IMatrix matrix, int col, int key, List<Integer> pos) {
        int rowBlock = pos.get(0);
        for (int rowTemp = 3 * (rowBlock / 3); rowTemp < 3 * (rowBlock / 3) + 3; rowTemp++) {
            for (int colTemp = 3 * (col / 3); colTemp < 3 * (col / 3) + 3; colTemp++) {
                if (colTemp != col && matrix.getCandidates(rowTemp, colTemp).contains(key)) {
                    matrix.removeCandidate(rowTemp, colTemp, key);
                }
            }
        }
    }

    private void removeInBlockRow(IMatrix matrix, int row, int key, List<Integer> pos) {
        for (int colTemp : Utils.it(pos.get(0))) {
            for (int rowTemp : Utils.it(row)) {
                if (rowTemp != row && matrix.getCandidates(rowTemp, colTemp).contains(key)) {
                    matrix.removeCandidate(rowTemp, colTemp, key);
                }
            }
        }
    }
}
