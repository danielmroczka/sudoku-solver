package com.labs.dm.sudoku.solver.utils;

import java.util.HashMap;

/**
 * Created by daniel on 2016-02-15.
 */
public class CounterHashMap extends HashMap<Integer, Integer> {

    public void inc(int key) {
        Integer value = get(key);
        if (value == null) {
            value = 0;
        }
        put(key, ++value);
    }
}
