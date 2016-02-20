package com.labs.dm.sudoku.solver.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

}
