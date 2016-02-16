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

/**
 * Redcution Algorithm.
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
        for (int colGroup = 0; colGroup < IMatrix.BLOCK_SIZE; colGroup++) {
            for (int rowGroup = 0; rowGroup < IMatrix.BLOCK_SIZE; rowGroup++) {
                CounterHashMap map = getOccurenceInBlockMap(matrix, rowGroup, colGroup);

                for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                    if (entry.getValue() == 2 || entry.getValue() == 3) {
                        List<Pair> list = new ArrayList<>();
                        for (int col = colGroup * IMatrix.BLOCK_SIZE; col < (colGroup + 1) * IMatrix.BLOCK_SIZE; col++) {
                            for (int row = rowGroup * IMatrix.BLOCK_SIZE; row < (rowGroup + 1) * IMatrix.BLOCK_SIZE; row++) {
                                if (matrix.getCandidates(row, col).contains(entry.getKey())) {
                                    list.add(new Pair(row, col));
                                }
                            }
                        }

                        boolean theSameRow = true;
                        boolean theSameCol = true;
                        Pair item = list.get(0);

                        for (Pair p : list) {
                            theSameCol = theSameCol && item.getCol() == p.getCol();
                            theSameRow = theSameRow && item.getRow() == p.getRow();
                        }

                        if (theSameCol) {
                            System.out.println("Removing " + entry.getKey() + " in col = " + item.getCol());
                            for (int row = 0; row < IMatrix.SIZE; row++) {
                                boolean f = true;
                                for (Pair p : list) {
                                    if (p.getRow() == row) {
                                        f = false;
                                    }
                                }
                                if (f) {
                                    matrix.getCandidates(row, item.getCol()).remove(entry.getKey());
                                }
                            }
                        }

                        if (theSameRow) {
                            System.out.println("Removing " + entry.getKey() + " in row = " + item.getRow());
                            for (int col = 0; col < IMatrix.SIZE; col++) {
                                boolean f = true;
                                for (Pair p : list) {
                                    if (p.getCol() == col) {
                                        f = false;
                                    }
                                }
                                if (f) {
                                    matrix.getCandidates(item.getRow(), col).remove(entry.getKey());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void removeInBlockCol(IMatrix matrix, int rowGroup, int colGroup, Integer key, List<Integer> pos) {
        System.out.println("TODO");
    }

    private List<Integer> getPositions(IMatrix matrix, int rowGroup, int colGroup, Integer key) {
        List<Integer> pos = new ArrayList<>();
        for (int col = colGroup * IMatrix.BLOCK_SIZE; col < (colGroup + 1) * IMatrix.BLOCK_SIZE; col++) {
            for (int row = rowGroup * IMatrix.BLOCK_SIZE; row < (rowGroup + 1) * IMatrix.BLOCK_SIZE; row++) {
                if (matrix.getCandidates(row, col).contains(key)) {
                    pos.add(row);
                }
            }
        }
        return pos;
    }

    private void reduceInCols(IMatrix matrix) {
        for (int col = 0; col < IMatrix.SIZE; col++) {
            CounterHashMap map = getOccurenceInColMap(matrix, col);

            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                if (entry.getValue() == 2 || entry.getValue() == 3) {
                    List<Integer> pos = getPositions(matrix, col, entry.getKey());
                    if (Utils.theSameBlock(pos.toArray(new Integer[pos.size()]))) {
                        removeInBlockCol(matrix, col, entry.getKey(), pos);
                    }
                }
            }
        }
    }

    private List<Integer> getPositions(IMatrix matrix, int col, int key) {
        List<Integer> pos = new ArrayList<>();
        for (int row = 0; row < IMatrix.SIZE; row++) {
            if (matrix.getCandidates(row, col).contains(key)) {
                pos.add(row);
            }
        }
        return pos;
    }

    private List<Integer> getPositions2(IMatrix matrix, int row, int key) {
        List<java.lang.Integer> pos = new ArrayList<>();
        for (int col = 0; col < IMatrix.SIZE; col++) {
            if (matrix.getCandidates(row, col).contains(key)) {
                pos.add(col);
            }
        }
        return pos;
    }

    private void reduceInRows(IMatrix matrix) {
        for (int row = 0; row < IMatrix.SIZE; row++) {
            CounterHashMap map = getOccurenceInRowMap(matrix, row);

            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                if (entry.getValue() == 2 || entry.getValue() == 3) {
                    List<Integer> pos = getPositions2(matrix, row, entry.getKey());
                    if (Utils.theSameBlock(pos.toArray(new Integer[pos.size()]))) {
                        removeInBlockRow(matrix, row, entry.getKey(), pos);
                    }
                }
            }
        }
    }

    private CounterHashMap getOccurenceInBlockMap(IMatrix matrix, int rowGroup, int colGroup) {
        CounterHashMap map = new CounterHashMap();

        for (int col = colGroup * IMatrix.BLOCK_SIZE; col < (colGroup + 1) * IMatrix.BLOCK_SIZE; col++) {
            for (int row = rowGroup * IMatrix.BLOCK_SIZE; row < (rowGroup + 1) * IMatrix.BLOCK_SIZE; row++) {
                for (int key : matrix.getCandidates(row, col)) {
                    map.inc(key);
                }

            }
        }

        return map;
    }

    private CounterHashMap getOccurenceInColMap(IMatrix matrix, int col) {
        CounterHashMap map = new CounterHashMap();
        for (int row = 0; row < IMatrix.SIZE; row++) {
            for (int key : matrix.getCandidates(row, col)) {
                map.inc(key);
            }
        }
        return map;
    }

    private CounterHashMap getOccurenceInRowMap(IMatrix matrix, int row) {
        CounterHashMap map = new CounterHashMap();
        for (int col = 0; col < IMatrix.SIZE; col++) {
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
                    System.out.println("Removing Col " + rowTemp + ", " + colTemp + ", " + key);
                    matrix.getCandidates(rowTemp, colTemp).remove(key);
                }
            }
        }
    }

    private void removeInBlockRow(IMatrix matrix, int row, int key, List<Integer> pos) {
        int colBlock = pos.get(0);
        for (int colTemp = 3 * (colBlock / 3); colTemp < 3 * (colBlock / 3) + 3; colTemp++) {
            for (int rowTemp = 3 * (row / 3); rowTemp < 3 * (row / 3) + 3; rowTemp++) {
                if (rowTemp != row && matrix.getCandidates(rowTemp, colTemp).contains(key)) {
                    System.out.println("Removing Row " + rowTemp + ", " + colTemp + ", " + key);
                    matrix.getCandidates(rowTemp, colTemp).remove(key);
                }
            }
        }
    }
}
