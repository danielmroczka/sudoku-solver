package com.labs.dm.sudoku.solver.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 2016-02-13.
 */
public class Utils {
    public static int factorial(int val) {
        if (val < 0) {
            throw new IllegalArgumentException();
        }
        int res = 1;
        for (int i = 1; i <= val; i++) {
            res = res * i;
        }

        return res;
    }

    public static int combination(int n, int k) {
        return factorial(n) / (factorial(k) * factorial(n - k));
    }

    public static List<List<Integer>> pairs(List<Integer> list) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            for (int j = i; j < list.size(); j++) {
                if (i == j) continue;
                List<Integer> tab = new ArrayList<>();
                tab.add(list.get(i));
                tab.add(list.get(j));
                result.add(tab);
            }
        }

        return result;
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
