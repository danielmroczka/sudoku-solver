package com.labs.dm.sudoku.solver.utils;

import com.labs.dm.sudoku.solver.core.Pair;
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

    @Test(expected = IllegalArgumentException.class)
    public void testFactorial2() {
        factorial(-1);
    }

    @Test
    public void testCombination() throws Exception {
        assertEquals(6, combination(4, 2));
        assertEquals(10, combination(5, 2));
        assertEquals(10, combination(5, 3));
        assertEquals(5, combination(5, 4));
        assertEquals(5, combination(5, 1));
    }

    @Test
    public void testCombination2() throws Exception {
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
//        assertEquals(1, combinationList(list1, 5).size());
        assertEquals(5, combinationList(list1, 4).size());
        assertEquals(10, combinationList(list1, 3).size());
        assertEquals(10, combinationList(list1, 2).size());
        assertEquals(5, combinationList(list1, 1).size());
    }

    @Test
    public void testTheSameBlock() throws Exception {
        assertTrue(theSameBlock(0, 2));
        assertTrue(theSameBlock(0, 1, 2));
        assertFalse(theSameBlock(0, 3));
        assertFalse(theSameBlock(0, 2, 3, 8));
    }

    @Test
    public void intersection() throws Exception {
        //the same cell
        assertEquals(7, Utils.intersection(new Pair(0, 2), new Pair(2, 0)).size());
        //cells in the same row or col
        assertEquals(7, Utils.intersection(new Pair(0, 0), new Pair(0, 5)).size());
        assertEquals(7, Utils.intersection(new Pair(0, 0), new Pair(5, 0)).size());
        //cells in the same row block or col block
        assertEquals(6, Utils.intersection(new Pair(0, 0), new Pair(2, 5)).size());
        assertEquals(6, Utils.intersection(new Pair(0, 0), new Pair(5, 2)).size());
        //different row and col
        assertEquals(2, Utils.intersection(new Pair(0, 8), new Pair(8, 0)).size());
    }

    @Test
    public void testIt() {
        assertEquals(3, Utils.it(3).length);
        assertArrayEquals(new int[]{0, 1, 2}, Utils.it(0));
        assertArrayEquals(new int[]{0, 1, 2}, Utils.it(1));
        assertArrayEquals(new int[]{0, 1, 2}, Utils.it(2));
        assertArrayEquals(new int[]{3, 4, 5}, Utils.it(3));
    }

    @Test
    public void compare() {
        assertTrue(Utils.compare(Arrays.asList(1, 2), Arrays.asList(2, 7), Arrays.asList(1, 7)));
        assertFalse(Utils.compare(Arrays.asList(1, 2), Arrays.asList(2, 7), Arrays.asList(8, 7)));
        assertFalse(Utils.compare(Arrays.asList(1, 1), Arrays.asList(2, 2), Arrays.asList(7, 7)));
    }

}