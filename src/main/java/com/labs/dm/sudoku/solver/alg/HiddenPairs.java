/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.utils.Utils;

import java.util.*;

/**
 * Hidden Pairs algorithm implementation
 * <p/>
 * http://www.learn-sudoku.com/hidden-pairs.html
 *
 * @author daniel
 */
public class HiddenPairs implements IAlgorithm {

    @Override
    public void execute(IMatrix matrix) {
        findHiddenPairsInRows(matrix);
        findHiddenPairsInCols(matrix);
        findHiddenPairsInBlock(matrix);
    }

    private void findHiddenPairsInBlock(IMatrix matrix) {
        for (int rowGroup = 0; rowGroup < IMatrix.BLOCK_SIZE; rowGroup++) {
            for (int colGroup = 0; colGroup < IMatrix.BLOCK_SIZE; colGroup++) {
                List<List<Integer>> list = new ArrayList<>();
                for (int row = rowGroup * IMatrix.BLOCK_SIZE; row < (rowGroup + 1) * IMatrix.BLOCK_SIZE; row++) {
                    for (int col = colGroup * IMatrix.BLOCK_SIZE; col < (colGroup + 1) * IMatrix.BLOCK_SIZE; col++) {
                        List<Integer> l = new ArrayList<>();
                        l.addAll(matrix.getCandidates(row, col));
                        list.add(l);
                    }
                }

                Map<List<Integer>, Integer> mapWithOccurencies = new HashMap<>();
                group(list, mapWithOccurencies);

                for (Map.Entry<List<Integer>, Integer> entry : mapWithOccurencies.entrySet()) {
                    if (entry.getValue() == 2) {
                        List<Integer> pairs = entry.getKey();
                        int found = 0, unique = 0;

                        for (int row = rowGroup * IMatrix.BLOCK_SIZE; row < (rowGroup + 1) * IMatrix.BLOCK_SIZE; row++) {
                            for (int col = colGroup * IMatrix.BLOCK_SIZE; col < (colGroup + 1) * IMatrix.BLOCK_SIZE; col++) {
                                Collection<Integer> candidates = matrix.getCandidates(row, col);
                                if (candidates.contains(pairs.get(0)) || candidates.contains(pairs.get(1))) {
                                    found++;
                                }
                                if (candidates.containsAll(pairs)) {
                                    unique++;
                                }
                            }
                        }

                        if (unique == 2 && found == 2) {

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
            List<List<Integer>> list = new ArrayList<>();
            for (int row = 0; row < IMatrix.SIZE; row++) {
                List<Integer> l = new ArrayList<>();
                l.addAll(matrix.getCandidates(row, col));
                list.add(l);
            }

            Map<List<Integer>, Integer> mapWithOccurencies = new HashMap<>();
            group(list, mapWithOccurencies);
            for (Map.Entry<List<Integer>, Integer> entry : mapWithOccurencies.entrySet()) {
                if (entry.getValue() == 2) {
                    List<Integer> pairs = entry.getKey();
                    int found = 0, unique = 0;
                    for (int rowTemp = 0; rowTemp < IMatrix.SIZE; rowTemp++) {
                        Collection<Integer> candidates = matrix.getCandidates(rowTemp, col);
                        if (candidates.contains(pairs.get(0)) || candidates.contains(pairs.get(1))) {
                            found++;
                        }
                        if (candidates.containsAll(pairs)) {
                            unique++;
                        }
                    }
                    if (unique == 2 && found == 2) {
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
            List<List<Integer>> list = new ArrayList<>();
            for (int col = 0; col < IMatrix.SIZE; col++) {
                List<Integer> candidates = new ArrayList<>();
                candidates.addAll(matrix.getCandidates(row, col));
                if (!candidates.isEmpty()) {
                    list.add(candidates);
                }
            }

            Map<List<Integer>, Integer> mapWithOccurencies = new HashMap<>();
            group(list, mapWithOccurencies);

            for (Map.Entry<List<Integer>, Integer> entry : mapWithOccurencies.entrySet()) {
                if (entry.getValue() == 2) {
                    List<Integer> pairs = entry.getKey();
                    int foundAny = 0, foundAll = 0;
                    for (int colTemp = 0; colTemp < IMatrix.SIZE; colTemp++) {
                        Collection<Integer> candidates = matrix.getCandidates(row, colTemp);
                        if (candidates.contains(pairs.get(0)) || candidates.contains(pairs.get(1))) {
                            foundAny++;
                        }
                        if (candidates.containsAll(pairs)) {
                            foundAll++;
                        }
                    }
                    if (foundAll == 2 && foundAny == 2) {
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

    private void group(List<List<Integer>> list, Map<List<Integer>, Integer> map) {
        for (List<Integer> list1 : list) {
            if (list1.size() > 1) {
                List<List<Integer>> pairs = Utils.pairs(list1);
                for (List<Integer> item : pairs) {
                    Integer val = map.get(item);
                    if (val == null) {
                        val = 0;
                    }
                    map.put(item, ++val);
                }
            }
        }
    }

}
