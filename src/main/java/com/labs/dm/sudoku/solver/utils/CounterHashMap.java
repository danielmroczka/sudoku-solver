package com.labs.dm.sudoku.solver.utils;

import java.util.HashMap;

/**
 * Implementation of HashMap making easier counting occurrences of keys and returning zero in case of missing key.
 * <p>
 * Created by Daniel Mroczka on 2016-02-15.
 */
public class CounterHashMap<K> extends HashMap<K, Integer> {

    public void inc(K key) {
        put(key, get(key) + 1);
    }

    @Override
    public Integer get(Object key) {
        return super.getOrDefault(key, 0);
    }
}
