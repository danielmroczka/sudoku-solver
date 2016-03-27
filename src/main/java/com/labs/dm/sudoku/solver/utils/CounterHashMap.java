package com.labs.dm.sudoku.solver.utils;

import java.util.HashMap;

/**
 * Implementation of HashMap making easier counting occurrences of keys and returns zero in case of missing key.
 *
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
        return res == null ? 0 : res;
    }
}
