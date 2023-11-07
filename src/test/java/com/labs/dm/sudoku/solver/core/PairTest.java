package com.labs.dm.sudoku.solver.core;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Daniel Mroczka on 23-Feb-16.
 */
public class PairTest {

    @Test
    public void test() {
        Pair pair1 = new Pair(0, 0);
        Pair pair2 = new Pair(0, 0);
        Pair pair3 = new Pair(1, 0);
        Pair pair4 = new Pair(0, 1);

        Assert.assertEquals(pair1, pair2);
        assertNotEquals(pair2, pair3);
        assertNotEquals(pair2, pair4);
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

        assertEquals(0, pairs.get(0).row());
        assertEquals(0, pairs.get(0).col());
        assertEquals(5, pairs.get(3).row());
        assertEquals(5, pairs.get(3).col());
    }
}