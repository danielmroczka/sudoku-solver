package com.labs.dm.sudoku.solver.utils;

import java.util.HashMap;

/**
 * Created by daniel on 2016-02-15.
 */
public class CounterHashMap<K> extends HashMap<K, Integer> {

    public void inc(K key) {
        Integer value = get(key);
        if (value == null) {
            value = 0;
        }
        put(key, ++value);
    }
}
