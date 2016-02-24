package com.labs.dm.sudoku.solver.core;

import org.junit.Test;

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
}