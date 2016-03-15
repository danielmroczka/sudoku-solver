package com.labs.dm.sudoku.solver.utils;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import com.labs.dm.sudoku.solver.core.Pair;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    public void testCombinationList() throws Exception {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        assertEquals(1, combinationList(list, 0).size());
        assertEquals(5, combinationList(list, 1).size());
        assertEquals(10, combinationList(list, 2).size());
        assertEquals(10, combinationList(list, 3).size());
        assertEquals(5, combinationList(list, 4).size());
        assertEquals(1, combinationList(list, 5).size());
        assertEquals(0, combinationList(list, 10).size());
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
        assertEquals(7, Utils.pairsOnIntersections(new Pair(0, 2), new Pair(2, 0)).size());
        //cells in the same row or col
        assertEquals(7, Utils.pairsOnIntersections(new Pair(0, 0), new Pair(0, 5)).size());
        assertEquals(7, Utils.pairsOnIntersections(new Pair(0, 0), new Pair(5, 0)).size());
        //cells in the same row block or col block
        assertEquals(6, Utils.pairsOnIntersections(new Pair(0, 0), new Pair(2, 5)).size());
        assertEquals(6, Utils.pairsOnIntersections(new Pair(0, 0), new Pair(5, 2)).size());
        //different row and col
        assertEquals(2, Utils.pairsOnIntersections(new Pair(0, 8), new Pair(8, 0)).size());
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
    public void candidatesMap() {
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{1, 2, 3});
        matrix.addCandidates(1, 1, new Integer[]{2, 3, 4});
        matrix.addCandidates(2, 2, new Integer[]{3, 4, 5});

        Map<Integer, List<Pair>> map = Utils.candidatesMap(matrix);

        assertEquals(5, map.size());
        assertEquals(1, map.get(1).size());
        assertEquals(2, map.get(2).size());
        assertEquals(3, map.get(3).size());
        assertEquals(2, map.get(4).size());
        assertEquals(1, map.get(5).size());
    }

    @Test
    @Ignore
    public void compare() {
        // assertTrue(Utils.acceptPivotAndPincets(Arrays.asList(1, 2), Arrays.asList(2, 7), Arrays.asList(1, 7)));
        //  assertFalse(Utils.acceptPivotAndPincets(Arrays.asList(1, 2), Arrays.asList(2, 7), Arrays.asList(8, 7)));
        //  assertFalse(Utils.acceptPivotAndPincets(Arrays.asList(1, 2), Arrays.asList(2, 7), Arrays.asList(2, 7)));
        //  assertFalse(Utils.acceptPivotAndPincets(Arrays.asList(1, 1), Arrays.asList(2, 2), Arrays.asList(7, 7)));
    }

    @Test
    public void testSubSet1() throws Exception {
        List<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(9));
        list.add(Arrays.asList(1, 2));
        list.add(Arrays.asList(1, 2, 3));
        list.add(Arrays.asList(1, 2, 3));
        list.add(Arrays.asList(1, 2, 3));
        list.add(Arrays.asList(7, 8, 9));

        List<List<Integer>> ids = Utils.subset(list, 3);
        assertArrayEquals(new Integer[]{1, 2, 3}, ids.get(0).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{1, 2, 3}, ids.get(1).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{1, 2, 3}, ids.get(2).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{1, 2, 3}, ids.get(3).toArray(new Integer[0]));

    }

    @Test
    public void testSubSet2() throws Exception {
        List<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(2, 4));
        list.add(Arrays.asList(4, 7));
        list.add(Arrays.asList(2, 7));

        list.add(Arrays.asList(2, 8));
        list.add(Arrays.asList(1, 7, 4));

        List<List<Integer>> ids = Utils.subset(list, 3);
        assertArrayEquals(new Integer[]{2, 4, 7}, ids.get(0).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{2, 4}, ids.get(1).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{4, 7}, ids.get(2).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{2, 7}, ids.get(3).toArray(new Integer[0]));

    }

    @Test
    public void testSubSet3() throws Exception {
        List<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(1, 3, 8));
        list.add(Arrays.asList(1, 8));
        list.add(Arrays.asList(3, 8));
        list.add(Arrays.asList(7, 8, 9));
        list.add(Arrays.asList(1, 2, 3));

        List<List<Integer>> ids = Utils.subset(list, 3);
        assertArrayEquals(new Integer[]{1, 3, 8}, ids.get(0).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{1, 8}, ids.get(1).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{3, 8}, ids.get(2).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{1, 3, 8}, ids.get(3).toArray(new Integer[0]));
    }

    @Test
    public void testSubSet4() throws Exception {
        List<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(5, 7));
        list.add(Arrays.asList(1, 3, 5));
        list.add(Arrays.asList(1, 3, 5, 7));
        list.add(Arrays.asList(4, 5, 6));
        list.add(Arrays.asList(2, 4, 5));
        list.add(Arrays.asList(6, 7, 9));
        list.add(Arrays.asList(2, 3, 9));
        list.add(Arrays.asList(3, 7));

        List<List<Integer>> ids = Utils.subset(list, 4);
        assertArrayEquals(new Integer[]{1, 3, 5, 7}, ids.get(0).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{5, 7}, ids.get(1).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{1, 3, 5}, ids.get(2).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{3, 7}, ids.get(3).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{1, 3, 5, 7}, ids.get(4).toArray(new Integer[0]));
    }
}