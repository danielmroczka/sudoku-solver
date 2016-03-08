package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Pair;
import com.labs.dm.sudoku.solver.utils.CounterHashMap;
import com.labs.dm.sudoku.solver.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.labs.dm.sudoku.solver.utils.Utils.combinationList;

/**
 * SwordFish algorithm
 * <p/>
 * http://hodoku.sourceforge.net/en/tech_fishb.php#bf3
 * http://www.sudoku9981.com/sudoku-solving/swordfish.php
 * http://www.dwojcik.republika.pl/sudoku/techniques/techn09.htm
 * <p/>
 * Created by daniel on 2016-03-03.
 */
public class SwordFish implements IAlgorithm {

    protected int SIZE = 3;
    protected int MIN_POINTS = 6;

    @Override
    public void execute(IMatrix matrix) {
        Map<Integer, List<List<Pair>>> pairs = detectSwordfish(matrix);
        for (Map.Entry<Integer, List<List<Pair>>> entry : pairs.entrySet()) {
            for (List<Pair> pair : entry.getValue()) {
                remove(matrix, entry.getKey(), pair);
            }
        }
    }

    private void remove(IMatrix matrix, int value, List<Pair> pairs) {
        CounterHashMap<Integer> rowMap = new CounterHashMap<>();
        CounterHashMap<Integer> colMap = new CounterHashMap<>();

        for (Pair pair : pairs) {
            rowMap.inc(pair.getRow());
            colMap.inc(pair.getCol());
        }

        if (rowMap.size() < SIZE || colMap.size() < SIZE) {
            return;
        }

        boolean removeInRows = canRemoveInRows(matrix, value, rowMap, colMap);
        boolean removeInCols = canRemoveInCols(matrix, value, rowMap, colMap);

        //removing in cols
        if (removeInRows) {
            removeInCols(matrix, value, rowMap, colMap);
        }
        //removing in rows
        if (removeInCols) {
            removeInRows(matrix, value, rowMap, colMap);
        }
        //System.out.println(rowMap);
        //System.out.println(colMap);
    }

    private boolean canRemoveInRows(IMatrix matrix, int value, CounterHashMap<Integer> rowMap, CounterHashMap<Integer> colMap) {
        for (int col : colMap.keySet()) {
            for (int row = 0; row < IMatrix.SIZE; row++) {
                if (!rowMap.containsKey(row)) {
                    if (matrix.getCandidates(row, col).contains(value)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean canRemoveInCols(IMatrix matrix, int value, CounterHashMap<Integer> rowMap, CounterHashMap<Integer> colMap) {
        for (int row : rowMap.keySet()) {
            for (int col = 0; col < IMatrix.SIZE; col++) {
                if (!colMap.containsKey(col)) {
                    if (matrix.getCandidates(row, col).contains(value)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void removeInCols(IMatrix matrix, int value, CounterHashMap<Integer> rowMap, CounterHashMap<Integer> colMap) {
        for (int row : rowMap.keySet()) {
            for (int col = 0; col < IMatrix.SIZE; col++) {
                if (!colMap.containsKey(col)) {
                    matrix.removeCandidate(row, col, value);
                }
            }
        }
    }

    private void removeInRows(IMatrix matrix, int value, CounterHashMap<Integer> rowMap, CounterHashMap<Integer> colMap) {
        for (int col : colMap.keySet()) {
            for (int row = 0; row < IMatrix.SIZE; row++) {
                if (!rowMap.containsKey(row)) {
                    matrix.removeCandidate(row, col, value);
                }
            }
        }
    }

    /**
     * Returns map
     *
     * @param matrix
     * @return
     */
    private Map<Integer, List<List<Pair>>> detectSwordfish(IMatrix matrix) {
        Map<Integer, List<List<Pair>>> res = new HashMap<>();
        Map<Integer, List<Pair>> map = Utils.candidatesMap(matrix);

        for (Map.Entry<Integer, List<Pair>> entry : map.entrySet()) {
            if (entry.getValue().size() < MIN_POINTS) {
                continue;
            }

            CounterHashMap<Integer> rowsMap = new CounterHashMap<>();
            CounterHashMap<Integer> colsMap = new CounterHashMap<>();

            for (Pair pair : entry.getValue()) {
                rowsMap.inc(pair.getRow());
                colsMap.inc(pair.getCol());
            }

            if (rowsMap.size() < SIZE || colsMap.size() < SIZE) {
                continue;
            }

            List<List<Integer>> rowComb = combinationList(new ArrayList<>(rowsMap.keySet()), SIZE);
            List<List<Integer>> colComb = combinationList(new ArrayList<>(colsMap.keySet()), SIZE);
            List<List<Pair>> pairs = new ArrayList<>();

            for (List<Integer> rows : rowComb) {
                for (List<Integer> cols : colComb) {
                    List<Pair> pair = getPairs(matrix, entry.getKey(), rows, cols);

                    CounterHashMap<Integer> rowsTempMap = new CounterHashMap<>();
                    CounterHashMap<Integer> colsTempMap = new CounterHashMap<>();
                    for (Pair p : pair) {
                        rowsTempMap.inc(p.getRow());
                        colsTempMap.inc(p.getCol());
                    }

                    boolean accept = true;
                    for (int t : rowsTempMap.values()) {
                        if (t < 2) {
                            accept = false;
                            break;
                        }
                    }
                    for (int t : colsTempMap.values()) {
                        if (t < 2) {
                            accept = false;
                            break;
                        }
                    }


                    if (accept && pair.size() >= MIN_POINTS) {
                        pairs.add(pair);
                    }
                }
            }
            if (!pairs.isEmpty()) {
                res.put(entry.getKey(), pairs);
            }

        }

        return res;
    }

    /**
     * For provided list of rows and columns returns list of Pair when parameter number exists in list of candidates.
     *
     * @param matrix
     * @param number
     * @param rows
     * @param cols
     * @return
     */
    private List<Pair> getPairs(IMatrix matrix, int number, List<Integer> rows, List<Integer> cols) {
        List<Pair> result = new ArrayList<>();
        for (int row : rows) {
            for (int col : cols) {
                if (matrix.getCandidates(row, col).contains(number)) {
                    result.add(new Pair(row, col));
                }
            }
        }
        return result;
    }
}
