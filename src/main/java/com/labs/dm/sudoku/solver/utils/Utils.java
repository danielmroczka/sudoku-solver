package com.labs.dm.sudoku.solver.utils;

import com.labs.dm.sudoku.solver.core.Pair;

import java.util.*;

import static com.labs.dm.sudoku.solver.core.IMatrix.BLOCK_SIZE;
import static com.labs.dm.sudoku.solver.core.IMatrix.SIZE;

/**
 * Created by daniel on 2016-02-13.
 */
public class Utils {

    /**
     * Returns factorial value
     * For example factorial(4) = 1 * 2 * 3 * 4
     *
     * @param val
     * @return
     */
    public static int factorial(int val) {
        if (val < 0) {
            throw new IllegalArgumentException();
        }
        int res = 1;
        for (int i = 2; i <= val; i++) {
            res = res * i;
        }

        return res;
    }

    public static int combination(int n, int k) {
        return factorial(n) / (factorial(k) * factorial(n - k));
    }

    /**
     * Return unique combination of k-length elements from n-length list of unique numbers
     *
     * @param numbers
     * @param k
     * @return
     */
    public static List<List<Integer>> combinationList(List<Integer> numbers, int k) {
        List<List<Integer>> res = new ArrayList<>();
        combinationList(numbers, k, 0, new Integer[k], res);
        return res;
    }

    private static void combinationList(List<Integer> arr, int len, int startPosition, Integer[] result, List<List<Integer>> list) {
        if (len == 0) {
            list.add(Arrays.asList(Arrays.copyOf(result, result.length)));
            return;
        }
        for (int i = startPosition; i <= arr.size() - len; i++) {
            result[result.length - len] = arr.get(i);
            combinationList(arr, len - 1, i + 1, result, list);
        }
    }

    public static boolean theSameBlock(Integer... val) {
        int v = val[0] / 3;
        boolean res = true;
        for (int i : val) {
            res = res && i / 3 == v;
            v = i / 3;
        }
        return res;
    }

    private static boolean theSameRowBlock(Pair cell1, Pair cell2) {
        return cell1.getRow() / 3 == cell2.getRow() / 3;
    }

    private static boolean theSameColBlock(Pair cell1, Pair cell2) {
        return cell1.getCol() / 3 == cell2.getCol() / 3;
    }

    private static boolean theSameRow(Pair cell1, Pair cell2) {
        return cell1.getRow() == cell2.getRow();
    }

    private static boolean theSameCol(Pair cell1, Pair cell2) {
        return cell1.getCol() == cell2.getCol();
    }

    public static List<Pair> intersection(Pair cell1, Pair cell2) {
        List<Pair> result = new ArrayList<>();
        if (theSameColBlock(cell1, cell2) && theSameRowBlock(cell1, cell2)) {
            for (int row : Utils.it(cell1.getRow())) {
                for (int col : Utils.it(cell1.getCol())) {
                    if ((row == cell1.getRow() && col == cell1.getCol()) || (row == cell2.getRow() && col == cell2.getCol())) {
                        continue;
                    }
                    result.add(new Pair(row, col));
                }

            }
        } else if (theSameRow(cell1, cell2)) {
            for (int col = 0; col < SIZE; col++) {
                if (col != cell1.getCol() && col != cell2.getCol()) {
                    result.add(new Pair(cell1.getRow(), col));
                }
            }
        } else if (theSameCol(cell1, cell2)) {
            for (int row = 0; row < SIZE; row++) {
                if (row != cell1.getRow() && row != cell2.getRow()) {
                    result.add(new Pair(row, cell1.getCol()));
                }
            }
        } else if (theSameRowBlock(cell1, cell2)) {
            for (int col : Utils.it(cell1.getCol())) {
                result.add(new Pair(cell2.getRow(), col));
            }
            for (int col : Utils.it(cell2.getCol())) {
                result.add(new Pair(cell1.getRow(), col));
            }

        } else if (theSameColBlock(cell1, cell2)) {
            for (int row : Utils.it(cell1.getRow())) {
                result.add(new Pair(row, cell2.getCol()));
            }
            for (int row : Utils.it(cell2.getRow())) {
                result.add(new Pair(row, cell1.getCol()));
            }

        } else {
            result.add(new Pair(cell1.getRow(), cell2.getCol()));
            result.add(new Pair(cell2.getRow(), cell1.getCol()));
        }

        return result;
    }

    public static int[] it(int pos) {
        int[] res = new int[BLOCK_SIZE];
        int id = 0;
        for (int item = pos / BLOCK_SIZE * BLOCK_SIZE; item < pos / BLOCK_SIZE * BLOCK_SIZE + BLOCK_SIZE; item++) {
            res[id++] = item;
        }
        return res;
    }

    public static boolean compare(Collection<Integer> collection1, Collection<Integer> collection2, Collection<Integer> collection3) {
        if (collection1.size() != 2 && collection2.size() != 2 && collection3.size() != 3) {
            return false;
        }
        if (new HashSet<>(collection1).size() != 2 || new HashSet<>(collection2).size() != 2 || new HashSet<>(collection3).size() != 2) {
            return false;
        }

        List<Integer> list = new ArrayList<>();
        list.addAll(collection1);
        list.addAll(collection2);
        list.addAll(collection3);
        CounterHashMap<Integer> map = new CounterHashMap<>();
        for (int item : list) {
            map.inc(item);
        }
        boolean found = true;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() != 2) {
                found = false;
                break;
            }
        }

        return found && map.size() == 3;
    }

}
