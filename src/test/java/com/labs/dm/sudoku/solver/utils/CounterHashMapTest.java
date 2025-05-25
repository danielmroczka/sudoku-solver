package com.labs.dm.sudoku.solver.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Daniel Mroczka on 2016-02-19.
 */
public class CounterHashMapTest {
    @Test
    public void shouldIncrementHashMap() {
        CounterHashMap<Integer> map = new CounterHashMap<>();
        map.inc(1);
        map.inc(2);
        map.inc(2);
        assertEquals(Integer.valueOf(1), map.get(1));
        assertEquals(Integer.valueOf(2), map.get(2));
        assertEquals(Integer.valueOf(0), map.get(3));
    }

}
