package com.labs.dm.sudoku.solver.core;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Created by Daniel Mroczka on 23-Feb-16.
 */
public class PairTest {

    @Test
    public void testEquality() {
        Pair pair1 = new Pair(0, 0);
        Pair pair2 = new Pair(0, 0);
        Pair pair3 = new Pair(1, 0);
        Pair pair4 = new Pair(0, 1);

        assertEquals(pair1, pair2);
        assertNotEquals(pair2, pair3);
        assertNotEquals(pair2, pair4);
    }

    @Test
    public void testSorting() {
        List<Pair> pairs = List.of(
                new Pair(5, 5),
                new Pair(0, 5),
                new Pair(5, 0),
                new Pair(0, 0)
        );

        List<Pair> sortedPairs = new ArrayList<>(pairs);
        Collections.sort(sortedPairs, new Pair.ComparatorRow());

        assertEquals(0, sortedPairs.get(0).row());
        assertEquals(0, sortedPairs.get(0).col());
        assertEquals(5, sortedPairs.get(3).row());
        assertEquals(5, sortedPairs.get(3).col());
    }
}
