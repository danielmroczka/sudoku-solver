/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.utils.CounterHashMap;
import com.labs.dm.sudoku.solver.utils.Utils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Hidden Pairs algorithm implementation
 * <p/>
 * http://www.learn-sudoku.com/hidden-pairs.html
 *
 * @author daniel
 */
public class HiddenPairs implements IAlgorithm {

    protected int SIZE = 2;

    @Override
    public void execute(IMatrix matrix) {
        findHiddenPairsInRows(matrix);
        findHiddenPairsInCols(matrix);
        findHiddenPairsInBlock(matrix);
    }

    private void findHiddenPairsInBlock(IMatrix matrix) {
        for (int rowGroup = 0; rowGroup < IMatrix.BLOCK_SIZE; rowGroup++) {
            for (int colGroup = 0; colGroup < IMatrix.BLOCK_SIZE; colGroup++) {
                Map<List<Integer>, Integer> mapWithOccurencies = group(matrix.getCandidatesInBlock(rowGroup, colGroup));

                for (Map.Entry<List<Integer>, Integer> entry : mapWithOccurencies.entrySet()) {
                    if (entry.getValue() == SIZE) {
                        List<Integer> pairs = entry.getKey();
                        int found = 0, unique = 0;

                        for (int row = rowGroup * IMatrix.BLOCK_SIZE; row < (rowGroup + 1) * IMatrix.BLOCK_SIZE; row++) {
                            for (int col = colGroup * IMatrix.BLOCK_SIZE; col < (colGroup + 1) * IMatrix.BLOCK_SIZE; col++) {
                                Collection<Integer> candidates = matrix.getCandidates(row, col);

                                if (!Collections.disjoint(candidates, pairs)) { //candidates.contains(pairs.get(0)) || candidates.contains(pairs.get(1))) {
                                    found++;
                                }
                                if (candidates.containsAll(pairs)) {
                                    unique++;
                                }
                            }
                        }

                        if (unique == SIZE && found == SIZE) {

                            for (int row = rowGroup * IMatrix.BLOCK_SIZE; row < (rowGroup + 1) * IMatrix.BLOCK_SIZE; row++) {
                                for (int col = colGroup * IMatrix.BLOCK_SIZE; col < (colGroup + 1) * IMatrix.BLOCK_SIZE; col++) {
                                    Collection<Integer> candidates = matrix.getCandidates(row, col);
                                    if (candidates.containsAll(pairs)) {
                                        candidates.retainAll(pairs);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void findHiddenPairsInCols(IMatrix matrix) {
        for (int col = 0; col < IMatrix.SIZE; col++) {
            Map<List<Integer>, Integer> mapWithOccurencies = group(matrix.getCandidatesInCol(col));
            for (Map.Entry<List<Integer>, Integer> entry : mapWithOccurencies.entrySet()) {
                if (entry.getValue() == SIZE) {
                    List<Integer> pairs = entry.getKey();
                    int found = 0, unique = 0;
                    for (int rowTemp = 0; rowTemp < IMatrix.SIZE; rowTemp++) {
                        Collection<Integer> candidates = matrix.getCandidates(rowTemp, col);
                        if (!Collections.disjoint(candidates, pairs)) {
                            found++;
                        }
                        if (candidates.containsAll(pairs)) {
                            unique++;
                        }
                    }
                    if (unique == SIZE && found == SIZE) {
                        for (int rowTemp = 0; rowTemp < IMatrix.SIZE; rowTemp++) {
                            Collection<Integer> candidates = matrix.getCandidates(rowTemp, col);
                            if (candidates.containsAll(pairs)) {
                                candidates.retainAll(pairs);
                            }
                        }
                    }
                }
            }
        }
    }

    private void findHiddenPairsInRows(IMatrix matrix) {
        for (int row = 0; row < IMatrix.SIZE; row++) {
            Map<List<Integer>, Integer> mapWithOccurencies = group(matrix.getCandidatesInRow(row));

            for (Map.Entry<List<Integer>, Integer> entry : mapWithOccurencies.entrySet()) {
                if (entry.getValue() == SIZE) {
                    List<Integer> pairs = entry.getKey();
                    int foundAny = 0, foundAll = 0;
                    for (int colTemp = 0; colTemp < IMatrix.SIZE; colTemp++) {
                        Collection<Integer> candidates = matrix.getCandidates(row, colTemp);
                        if (!Collections.disjoint(candidates, pairs)) {
                            foundAny++;
                        }
                        if (candidates.containsAll(pairs)) {
                            foundAll++;
                        }
                    }
                    if (foundAll == SIZE && foundAny == SIZE) {
                        for (int colTemp = 0; colTemp < IMatrix.SIZE; colTemp++) {
                            Collection<Integer> candidates = matrix.getCandidates(row, colTemp);
                            if (candidates.containsAll(pairs)) {
                                candidates.retainAll(pairs);
                            }
                        }
                    }
                }
            }
        }
    }

    private Map<List<Integer>, Integer> group(List<List<Integer>> input) {
        CounterHashMap<List<Integer>> map = new CounterHashMap();
        for (List<Integer> item : input) {
            if (item.size() > 1) {
                List<List<Integer>> combination = Utils.combinationList(item, SIZE);
                for (List<Integer> elem : combination) {
                    map.inc(elem);
                }
            }
        }
        return map;
    }

}
