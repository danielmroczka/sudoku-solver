package com.labs.dm.sudoku.solver.alg.naked;

import com.labs.dm.sudoku.solver.alg.IAlgorithm;
import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import com.labs.dm.sudoku.solver.utils.CounterHashMap;
import com.labs.dm.sudoku.solver.utils.Utils;

import java.util.*;

import static com.labs.dm.sudoku.solver.core.Matrix.BLOCK_SIZE;

/**
 * Abstract class for Naked Subset algorithms.
 * A naked subset is a set of N cells in a row, column, or block that have the same N candidates.
 * This allows the removal of these candidates from other cells in the same row, column, or block.
 *
 * @see <a href="http://www.sudokuwiki.org/Naked_Candidates">Naked Candidates</a>
 * <p>
 * Created by Daniel Mroczka on 2016-03-15.
 */
abstract class NakedSubset implements IAlgorithm {

    protected int subsetSize;

    @Override
    public void execute(IMatrix matrix) {
        findNakedSubsetsInRows(matrix);
        findNakedSubsetsInCols(matrix);
        findNakedSubsetsInBlocks(matrix);
    }

    /**
     * Finds naked subsets in each row and removes candidates from other cells in the same row.
     *
     * @param matrix The Sudoku matrix.
     */
    private void findNakedSubsetsInRows(IMatrix matrix) {
        for (int row = 0; row < Matrix.SIZE; row++) {
            List<List<Integer>> res = nakedSubset(matrix.getCandidatesInRow(row), subsetSize);

            if (res.size() != subsetSize + 1) {
                continue;
            }

            for (int col = 0; col < Matrix.SIZE; col++) {
                removeCandidate(matrix, res, row, col);
            }
        }
    }

    /**
     * Finds naked subsets in each column and removes candidates from other cells in the same column.
     *
     * @param matrix The Sudoku matrix.
     */
    private void findNakedSubsetsInCols(IMatrix matrix) {
        for (int col = 0; col < Matrix.SIZE; col++) {
            List<List<Integer>> res = nakedSubset(matrix.getCandidatesInCol(col), subsetSize);

            if (res.size() != subsetSize + 1) {
                continue;
            }

            for (int row = 0; row < Matrix.SIZE; row++) {
                removeCandidate(matrix, res, row, col);
            }
        }
    }

    /**
     * Finds naked subsets in each block and removes candidates from other cells in the same block.
     *
     * @param matrix The Sudoku matrix.
     */
    private void findNakedSubsetsInBlocks(IMatrix matrix) {
        for (int rowGroup = 0; rowGroup < BLOCK_SIZE; rowGroup++) {
            for (int colGroup = 0; colGroup < BLOCK_SIZE; colGroup++) {
                List<List<Integer>> res = nakedSubset(matrix.getCandidatesInBlock(rowGroup, colGroup), subsetSize);

                if (res.size() != subsetSize + 1) {
                    continue;
                }

                for (int row = rowGroup * BLOCK_SIZE; row < (rowGroup + 1) * BLOCK_SIZE; row++) {
                    for (int col = colGroup * BLOCK_SIZE; col < (colGroup + 1) * BLOCK_SIZE; col++) {
                        removeCandidate(matrix, res, row, col);
                    }
                }

            }
        }
    }

    /**
     * Removes candidates from a cell if it's not part of the naked subset.
     *
     * @param matrix The Sudoku matrix.
     * @param res    The result from the nakedSubset method.
     * @param row    The row of the cell.
     * @param col    The column of the cell.
     */
    private void removeCandidate(IMatrix matrix, List<List<Integer>> res, int row, int col) {
        if (matrix.getCandidates(row, col).isEmpty()) {
            return;
        }
        boolean contains = false;
        for (List<Integer> item : res) {
            if (item.equals(matrix.getCandidates(row, col))) {
                contains = true;
                break;
            }
        }
        if (!contains) {
            matrix.removeCandidates(row, col, res.get(0));
        }
    }

    /**
     * Returns naked subset.
     * If found returns list of subsetSize+1 elements
     * 0: naked subset
     * 1: first example of subset
     * n: n- element of subset
     * <p>
     * Elements are subsets of naked subsets:
     * 138: 138, 18, 38
     * 1357: 37, 57, 135, 1357
     * 247: 24, 47, 27
     * 123: 123, 123, 123
     *
     * @param list
     * @param size - size of naked subset. Two for pair, three for triple, four for quads
     * @return list of naked subsets
     */
    protected List<List<Integer>> nakedSubset(List<List<Integer>> list, int size) {
        List<List<Integer>> map = new ArrayList<>();
        CounterHashMap<List<Integer>> counterMap = new CounterHashMap<>();
        for (List<Integer> item : list) {
            if (item.size() >= 2 && item.size() <= size) {
                counterMap.inc(item);
            }
        }

        /**
         * Finds following pattern:
         * 123, 123, 123 -> 123
         */
        for (Map.Entry<List<Integer>, Integer> entry : counterMap.entrySet()) {
            if (entry.getValue() == size) {
                for (int i = 0; i < size + 1; i++) {
                    map.add(entry.getKey());
                }
                return map;
            }
        }

        /**
         * Finds following pattern:
         * 24, 47, 27 -> 247
         */
        List<List<Integer>> listWithTwoLengthItems = new ArrayList<>();
        List<Integer> cc = new ArrayList<>();
        int i = 0;
        for (List<Integer> entry : list) {
            if (entry.size() >= 2 && entry.size() <= size - 1) {
                listWithTwoLengthItems.add(entry);
                cc.add(i++);
            }
        }

        List<List<Integer>> indexListCombination = Utils.combinationList(cc, 3);
        for (List<Integer> indexCombination : indexListCombination) {
            List<Integer> tmpList = new ArrayList<>();

            for (int idx : indexCombination) {
                tmpList.addAll(listWithTwoLengthItems.get(idx));
            }
            CounterHashMap<Integer> counter = new CounterHashMap();
            for (int item : tmpList) {
                counter.inc(item);
            }

            boolean status = true;
            for (int val : counter.values()) {
                if (val != 2) {
                    status = false;
                    break;
                }
            }

            if (status) {
                Set<Integer> set = new HashSet<>(tmpList);
                map.add(new ArrayList<>(set));
                for (int idx : indexCombination) {
                    map.add(listWithTwoLengthItems.get(idx));
                }
                return map;
            }
        }

        /**
         * Finds following pattern:
         * 138, 18, 38 -> 138
         * 37, 57, 135, 1357 -> 1357
         *
         */
        for (List<Integer> entry : list) {
            if (entry.size() == size) {
                int cnt = 0;

                List<List<Integer>> combination = new ArrayList<>();
                for (int j = 2; j < size; j++) {
                    combination.addAll(Utils.combinationList(entry, j));
                }

                for (List<Integer> item : list) {
                    if (!entry.equals(item) && item.size() >= 2 && item.size() <= size) {
                        for (List<Integer> elem : combination) {
                            if (item.equals(elem)) {
                                cnt++;
                                map.add(item);
                            }
                        }
                    }
                }

                if (cnt == size - 1) {
                    map.add(entry);
                    map.add(0, entry);
                    return map;
                } else {
                    map.clear();
                }
            }
        }

        return map;
    }

}
