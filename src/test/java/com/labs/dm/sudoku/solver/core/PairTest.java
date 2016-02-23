package com.labs.dm.sudoku.solver.core;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by daniel on 23-Feb-16.
 */
public class PairTest {

    @Test
    public void test() {
        Pair pair = new Pair(0,0);
        Pair pair2 = new Pair(0,0);

        assertTrue(pair.equals(pair2));
    }
}