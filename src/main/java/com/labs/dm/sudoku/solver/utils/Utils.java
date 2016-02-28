package com.labs.dm.sudoku.solver.utils;

import com.labs.dm.sudoku.solver.core.IMatrix;
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

    public static boolean compare(IMatrix matrix, Pair pivotPair, Pair pincetPair1, Pair pincetPair2, int size) {
        Collection<Integer> pivot = matrix.getCandidates(pivotPair);
        Collection<Integer> pincet1 = matrix.getCandidates(pincetPair1);
        Collection<Integer> pincet2 = matrix.getCandidates(pincetPair2);

        if (Arrays.equals(pincet1.toArray(), pincet2.toArray())) {
            return false;
        }

        if (pincet1.size() != 2 && pincet2.size() != 2) {
            return false;
        }
        if (new HashSet<>(pivot).size() != size || new HashSet<>(pincet1).size() != 2 || new HashSet<>(pincet2).size() != 2) {
            return false;
        }

        List<Integer> list = new ArrayList<>();
        list.addAll(pivot);
        list.addAll(pincet1);
        list.addAll(pincet2);

        if (new HashSet<>(list).size() != size) {
            // return false; //TODO
        }

        CounterHashMap<Integer> map = new CounterHashMap<>();
        for (int item : list) {
            map.inc(item);
        }
        boolean found = true;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() < 2) {
                found = false;
                break;
            }
        }

        List<Integer> copy1 = new ArrayList<>(pivot);
        List<Integer> copy2 = new ArrayList<>(pivot);
        copy1.retainAll(pincet1);
        copy2.retainAll(pincet2);

        return found && copy1.size() == (size - 1) && copy2.size() == (size - 1) && copy1.get(0) != copy2.get(0) && map.size() == 3;
    }

    public static boolean accept(Collection<Integer> pivot, Collection<Integer> pincet, int size) {
        if (pincet.size() != 2) {
            return false;
        }
        int cnt = 0;
        for (int i : pivot) {
            for (int j : pincet) {
                if (i == j) {
                    cnt++;
                }
            }
        }
        return cnt == size - 1;
    }
}
