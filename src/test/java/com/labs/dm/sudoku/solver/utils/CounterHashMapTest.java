package com.labs.dm.sudoku.solver.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by daniel on 2016-02-19.
 */
public class CounterHashMapTest {
    @Test
    public void shouldIncrementHashMap() {
        CounterHashMap<Integer> map = new CounterHashMap<>();
        map.inc(1);
        map.inc(2);
        map.inc(2);
        assertEquals(new Integer(1), map.get(1));
        assertEquals(new Integer(2), map.get(2));
    }

}