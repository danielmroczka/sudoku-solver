package com.labs.dm.sudoku.solver.core;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by daniel on 23-Feb-16.
 */
public class PairTest {

    @Test
    public void test() {
        Pair pair1 = new Pair(0, 0);
        Pair pair2 = new Pair(0, 0);
        Pair pair3 = new Pair(1, 0);
        Pair pair4 = new Pair(0, 1);

        assertTrue(pair1.equals(pair2));
        assertFalse(pair2.equals(pair3));
        assertFalse(pair2.equals(pair4));
    }

    @Test
    public void testSort() {
        List<Pair> pairs = new ArrayList<>();
        Pair pair1 = new Pair(5, 5);
        Pair pair2 = new Pair(0, 5);
        Pair pair3 = new Pair(5, 0);
        Pair pair4 = new Pair(0, 0);
        pairs.add(pair1);
        pairs.add(pair2);
        pairs.add(pair3);
        pairs.add(pair4);

        Collections.sort(pairs, new Pair.ComaparatorRows());

        assertEquals(0, pairs.get(0).getRow());
        assertEquals(0, pairs.get(0).getCol());
        assertEquals(5, pairs.get(3).getRow());
        assertEquals(5, pairs.get(3).getCol());
    }
}