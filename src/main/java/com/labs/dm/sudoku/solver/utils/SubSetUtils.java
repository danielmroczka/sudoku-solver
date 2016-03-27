package com.labs.dm.sudoku.solver.utils;

import java.util.*;

import static com.labs.dm.sudoku.solver.utils.Utils.FULL_LIST;
import static com.labs.dm.sudoku.solver.utils.Utils.match;

/**
 *
 *
 * Created by Daniel Mroczka on 2016-03-21.
 */
public class SubsetUtils {

    /**
     * Returns hidden subset or empty List if no subsets are found.
     *
     * @param candidates - list of all candidates, grouped by row, col or block depends on usage
     * @param size       - size of hidden subset. Two for pair, three for triple, four for quads
     * @return hidden subset
     */
    public static List<List<Integer>> hiddenSubset(List<List<Integer>> candidates, int size) {
        final List<List<Integer>> combinations = Utils.combinationList(FULL_LIST, size);
        List<List<Integer>> subset = new ArrayList<>();
        CounterHashMap<List<Integer>> counterMap = new CounterHashMap<>();
        Map<List<Integer>, List<Integer>> map = new HashMap<>();

        for (List<Integer> combination : combinations) {
            CounterHashMap<Integer> innerCounterMap = new CounterHashMap<>();
            int id = 0;
            List<Integer> pos = new ArrayList<>();
            Set<List<Integer>> set = new HashSet<>();
            for (List<Integer> item : candidates) {
                for (int number : item) {
                    innerCounterMap.inc(number);
                }
                List<List<Integer>> innerCombination = Utils.getCombinationList(size, item);
                boolean matched = false;
                for (List<Integer> cb : innerCombination) {
                    if (match(cb, combination) >= 2) {
                        matched = true;
                        counterMap.inc(combination);
                    }
                }
                if (matched) {
                    set.add(item);
                    pos.add(id);
                }
                id++;
            }

            boolean allMatched = true;
            for (int c : combination) {
                if (innerCounterMap.get(c) == 0) {
                    allMatched = false;
                }
            }

            if (allMatched && pos.size() == size && set.size() == pos.size()) {
                map.put(combination, pos);
            }
        }

        Map<List<Integer>, List<Integer>> res = filter(candidates, map);
        if (res.size() > 0) {
            subset.add((List<Integer>) res.keySet().toArray()[0]);
            subset.add((List<Integer>) res.values().toArray()[0]);
        }
        return subset;
    }

    /**
     * Filter only allowed candidates
     *
     * @param list
     * @param inputMap
     * @return
     */
    private static Map<List<Integer>, List<Integer>> filter(List<List<Integer>> list, Map<List<Integer>, List<Integer>> inputMap) {
        Map<List<Integer>, List<Integer>> copy = new HashMap<>(inputMap);

        for (Map.Entry<List<Integer>, List<Integer>> entry : inputMap.entrySet()) {
            for (int item : entry.getKey()) {
                CounterHashMap<List<Integer>> counterHashMap = new CounterHashMap<>();
                for (int pos = 0; pos < list.size(); pos++) {
                    List<Integer> line = list.get(pos);
                    if (line.contains(item) && !entry.getValue().contains(pos)) {
                        copy.remove(entry.getKey());
                    }
                    counterHashMap.inc(line);
                }
            }
        }
        return copy;
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
    public static List<List<Integer>> nakedSubset(List<List<Integer>> list, int size) {
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
                if (val != 2) status = false;
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
