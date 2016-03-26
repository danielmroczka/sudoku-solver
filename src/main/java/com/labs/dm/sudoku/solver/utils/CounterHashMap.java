package com.labs.dm.sudoku.solver.utils;

import java.util.HashMap;

/**
 * Created by Daniel Mroczka on 2016-02-15.
 */
public class CounterHashMap<K> extends HashMap<K, Integer> {

    public void inc(K key) {
        Integer value = get(key);
        put(key, ++value);
    }

    @Override
    public Integer get(Object key) {
        Integer res = super.get(key);
        if (res == null) {
            res = 0;
        }
        return res;
    }
}
