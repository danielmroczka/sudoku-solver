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

import static com.labs.dm.sudoku.solver.core.Matrix.BLOCK_SIZE;
import static com.labs.dm.sudoku.solver.core.Matrix.SIZE;
import static com.labs.dm.sudoku.solver.utils.Utils.blockElems;

/**
 * Created by Daniel Mroczka on 3/31/2016.
 * <p>
 * Locked Candidates Type1 - Pointing Pairs:
 * If in a block all candidates of a specific digit are confined to only one row or column that digit cannot appear
 * outside that block in the same row or column
 * <p>
 * All the candidates for digit X in a box are confined to a single line (row or column). The surplus candidates are
 * eliminated from the part of the line that does not intersect with this box.
 * <p>
 * .-------.-------.-------.
 * | * * * | * * * | X X X |
 * |       |       | - - - |
 * |       |       | - - - |
 * '-------'-------'-------'
 * <p>
 * Legend:
 * X : cell which may contain a candidate for digit X
 * - : cell which does not contain a candidate for digit X
 * * : cell from which we may eliminate the candidates for digit X
 * <p>
 * Locked Candidates Type2 - Claiming or Box-Line Reduction:
 * If in a row or column all candidates of specific digit are only in one box that digit can be eliminated from all
 * other cells in this block.
 * All the candidates for digit X in a line are confined to a single box. The surplus candidates are eliminated from
 * the part of the box that does not intersect with this line.
 * <p>
 * .-------.-------.-------.
 * | - - - | - - - | X X X |
 * |       |       | * * * |
 * |       |       | * * * |
 * '-------'-------'-------'
 * <p>
 * http://www.thonky.com/sudoku/pointing-pairs-triples/
 */
public class Reduction implements IAlgorithm {

    @Override
    public void execute(IMatrix matrix) {
        pointingPairsInRows(matrix);
        pointingPairsInCols(matrix);
        claiming(matrix);
    }

    private void pointingPairsInRows(IMatrix matrix) {
        for (int row = 0; row < SIZE; row++) {
            CounterHashMap<Integer> map = getOccurenceInRowMap(matrix, row);

            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                List<Integer> pos = getPosInRow(matrix, row, entry.getKey());
                if (!pos.isEmpty() && Utils.theSameBlock(pos)) {
                    removeInBlockRow(matrix, row, entry.getKey(), pos);
                }
            }
        }
    }

    private void pointingPairsInCols(IMatrix matrix) {
        for (int col = 0; col < SIZE; col++) {
            CounterHashMap<Integer> map = getOccurenceInColMap(matrix, col);

            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                List<Integer> pos = getPosInCol(matrix, col, entry.getKey());
                if (!pos.isEmpty() && Utils.theSameBlock(pos)) {
                    removeInBlockCol(matrix, col, entry.getKey(), pos);
                }
            }
        }
    }


    private void claiming2(IMatrix matrix) {
        for (int rowGroup = 0; rowGroup < BLOCK_SIZE; rowGroup++) {
            for (int colGroup = 0; colGroup < BLOCK_SIZE; colGroup++) {
                Map<Integer, List<Pair>> map = Utils.getOccurencesInBlock(matrix, rowGroup, colGroup);
                for (Map.Entry<Integer, List<Pair>> entry : map.entrySet()) {
                    if (acceptSize(entry)) {
                        boolean confinedInRow = true, confinedInCol = true;
                        for (int i = 1; i < entry.getValue().size(); i++) {
                            if (entry.getValue().get(i - 1).getRow() != entry.getValue().get(i).getRow()) {
                                confinedInRow = false;
                            }
                            if (entry.getValue().get(i - 1).getCol() != entry.getValue().get(i).getCol()) {
                                confinedInCol = false;
                            }
                        }

                        claimingInRows(matrix, entry, confinedInRow);
                        claimingInCols(matrix, entry, confinedInCol);
                    }
                }
            }
        }
    }

    private boolean acceptSize(Map.Entry<Integer, List<Pair>> entry) {
        return entry.getValue().size() == 2 || entry.getValue().size() == 3;
    }

    private void claimingInCols(IMatrix matrix, Map.Entry<Integer, List<Pair>> entry, boolean confinedInCol) {
        if (confinedInCol) {
            for (int row = 0; row < SIZE; row++) {
                List<Integer> rows = new ArrayList<>();
                for (Pair p : entry.getValue()) {
                    rows.add(p.getCol());
                }
                if (!rows.contains(row)) {
                    int col = entry.getValue().get(0).getCol();
                    matrix.removeCandidate(row, col, entry.getKey());
                }
            }
        }
    }

    private void claimingInRows(IMatrix matrix, Map.Entry<Integer, List<Pair>> entry, boolean confinedInRow) {
        if (confinedInRow) {
            for (int col = 0; col < SIZE; col++) {
                List<Integer> cols = new ArrayList<>();
                for (Pair p : entry.getValue()) {
                    cols.add(p.getCol());
                }
                if (!cols.contains(col)) {
                    int row = entry.getValue().get(0).getRow();
                    matrix.removeCandidate(row, col, entry.getKey());
                }
            }
        }
    }

    private void claiming(IMatrix matrix) {
        for (int colGroup = 0; colGroup < BLOCK_SIZE; colGroup++) {
            for (int rowGroup = 0; rowGroup < BLOCK_SIZE; rowGroup++) {
                CounterHashMap<Integer> map = getOccurenceInBlockMap(matrix, rowGroup, colGroup);

                for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                    List<Pair> list = new ArrayList<>();
                    for (int col : blockElems(colGroup * BLOCK_SIZE)) {
                        for (int row : blockElems(rowGroup * BLOCK_SIZE)) {
                            if (matrix.getCandidates(row, col).contains(entry.getKey())) {
                                list.add(new Pair(row, col));
                            }
                        }
                    }
                    if (list.isEmpty()) {
                        continue;
                    }

                    for (Pair item : list) {
                        boolean theSameRow = true, theSameCol = true;

                        for (Pair p : list) {
                            theSameCol = theSameCol && item.getCol() == p.getCol();
                            theSameRow = theSameRow && item.getRow() == p.getRow();
                        }

                        if (theSameCol) {
                            for (int row = 0; row < SIZE; row++) {
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
                            for (int col = 0; col < SIZE; col++) {
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

    private List<Integer> getPosInCol(IMatrix matrix, int col, int key) {
        List<Integer> pos = new ArrayList<>();
        for (int row = 0; row < SIZE; row++) {
            if (matrix.getCandidates(row, col).contains(key)) {
                pos.add(row);
            }
        }
        return pos;
    }

    private List<Integer> getPosInRow(IMatrix matrix, int row, int key) {
        List<Integer> pos = new ArrayList<>();
        for (int col = 0; col < SIZE; col++) {
            if (matrix.getCandidates(row, col).contains(key)) {
                pos.add(col);
            }
        }
        return pos;
    }

    private CounterHashMap<Integer> getOccurenceInBlockMap(IMatrix matrix, int rowGroup, int colGroup) {
        CounterHashMap<Integer> map = new CounterHashMap<>();

        for (int col : blockElems(colGroup * BLOCK_SIZE)) {
            for (int row : blockElems(rowGroup * BLOCK_SIZE)) {
                count(matrix, row, map, col);
            }
        }

        return map;
    }

    private CounterHashMap<Integer> getOccurenceInColMap(IMatrix matrix, int col) {
        CounterHashMap<Integer> map = new CounterHashMap<>();
        for (int row = 0; row < SIZE; row++) {
            count(matrix, row, map, col);
        }
        return map;
    }

    private CounterHashMap<Integer> getOccurenceInRowMap(IMatrix matrix, int row) {
        CounterHashMap<Integer> map = new CounterHashMap<>();
        for (int col = 0; col < SIZE; col++) {
            count(matrix, row, map, col);
        }
        return map;
    }

    private void count(IMatrix matrix, int row, CounterHashMap<Integer> map, int col) {
        for (int key : matrix.getCandidates(row, col)) {
            map.inc(key);
        }
    }

    private void removeInBlockCol(IMatrix matrix, int col, int key, List<Integer> pos) {
        for (int rowTemp : blockElems(pos.get(0))) {
            for (int colTemp : blockElems(col)) {
                if (colTemp != col && matrix.getCandidates(rowTemp, colTemp).contains(key)) {
                    matrix.removeCandidate(rowTemp, colTemp, key);
                }
            }
        }
    }

    private void removeInBlockRow(IMatrix matrix, int row, int key, List<Integer> pos) {
        for (int colTemp : blockElems(pos.get(0))) {
            for (int rowTemp : blockElems(row)) {
                if (rowTemp != row && matrix.getCandidates(rowTemp, colTemp).contains(key)) {
                    matrix.removeCandidate(rowTemp, colTemp, key);
                }
            }
        }
    }
}
