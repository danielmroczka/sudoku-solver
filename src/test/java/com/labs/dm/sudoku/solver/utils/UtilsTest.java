package com.labs.dm.sudoku.solver.utils;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.labs.dm.sudoku.solver.utils.Utils.*;
import static org.junit.Assert.*;

/**
 * Created by daniel on 2016-02-13.
 */
public class UtilsTest {
    @Test
    public void testFactorial() {
        assertEquals(1, factorial(0));
        assertEquals(1, factorial(1));
        assertEquals(2, factorial(2));
        assertEquals(6, factorial(3));
    }

    @Test
    public void testCombination() throws Exception {
        assertEquals(6, combination(4, 2));
    }


    @Test
    public void testPairs() throws Exception {
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4);

        List<List<Integer>> result = pairs(list1);
        assertEquals(6, result.size());
    }

    @Test
    public void testTheSameBlock() throws Exception {
        assertTrue(theSameBlock(0, 2));
        assertTrue(theSameBlock(0, 1, 2));
        assertFalse(theSameBlock(0, 3));
        assertFalse(theSameBlock(0, 2, 3, 8));
    }
}