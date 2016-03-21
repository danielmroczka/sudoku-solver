package com.labs.dm.sudoku.solver.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.labs.dm.sudoku.solver.utils.SubSetUtils.hiddenSubset;
import static com.labs.dm.sudoku.solver.utils.SubSetUtils.nakedSubset;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by daniel on 2016-03-21.
 */
public class SubSetUtilsTest {

    @Test
    public void nakedSubSet() throws Exception {
        //GIVEN
        List<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(9));
        list.add(Arrays.asList(1, 2));
        list.add(Arrays.asList(1, 2, 3));
        list.add(Arrays.asList(1, 2, 3));
        list.add(Arrays.asList(1, 2, 3));
        list.add(Arrays.asList(7, 8, 9));
        //WHEN
        List<List<Integer>> ids = nakedSubset(list, 3);
        //THEN
        assertArrayEquals(new Integer[]{1, 2, 3}, ids.get(0).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{1, 2, 3}, ids.get(1).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{1, 2, 3}, ids.get(2).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{1, 2, 3}, ids.get(3).toArray(new Integer[0]));
    }

    @Test
    public void nakedSubSet2() throws Exception {
        //GIVEN
        List<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(2, 4));
        list.add(Arrays.asList(4, 7));
        list.add(Arrays.asList(2, 7));
        list.add(Arrays.asList(2, 8));
        list.add(Arrays.asList(1, 7, 4));
        //WHEN
        List<List<Integer>> ids = nakedSubset(list, 3);
        //THEN
        assertArrayEquals(new Integer[]{2, 4, 7}, ids.get(0).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{2, 4}, ids.get(1).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{4, 7}, ids.get(2).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{2, 7}, ids.get(3).toArray(new Integer[0]));
    }

    @Test
    public void nakedSubSet3() throws Exception {
        //GIVEN
        List<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(1, 3, 8));
        list.add(Arrays.asList(1, 8));
        list.add(Arrays.asList(3, 8));
        list.add(Arrays.asList(7, 8, 9));
        list.add(Arrays.asList(1, 2, 3));
        //WHEN
        List<List<Integer>> ids = nakedSubset(list, 3);
        //THEN
        assertArrayEquals(new Integer[]{1, 3, 8}, ids.get(0).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{1, 8}, ids.get(1).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{3, 8}, ids.get(2).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{1, 3, 8}, ids.get(3).toArray(new Integer[0]));
    }

    @Test
    public void nakedSubSet4() throws Exception {
        //GIVEN
        List<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(5, 7));
        list.add(Arrays.asList(1, 3, 5));
        list.add(Arrays.asList(1, 3, 5, 7));
        list.add(Arrays.asList(4, 5, 6));
        list.add(Arrays.asList(2, 4, 5));
        list.add(Arrays.asList(6, 7, 9));
        list.add(Arrays.asList(2, 3, 9));
        list.add(Arrays.asList(3, 7));
        //WHEN
        List<List<Integer>> ids = nakedSubset(list, 4);
        //THEN
        assertArrayEquals(new Integer[]{1, 3, 5, 7}, ids.get(0).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{5, 7}, ids.get(1).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{1, 3, 5}, ids.get(2).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{3, 7}, ids.get(3).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{1, 3, 5, 7}, ids.get(4).toArray(new Integer[0]));
    }

    @Test
    public void hiddenSubSet1() throws Exception {
        //GIVEN
        //expected hidden pair = 1,9
        List<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(1, 6, 9));
        list.add(Arrays.asList(1, 9));
        list.add(Arrays.asList(2, 6));
        //WHEN
        List<List<Integer>> ids = hiddenSubset(list, 2);
        //THEN
        assertEquals(2, ids.size());
        assertEquals(Arrays.asList(1, 9), ids.get(0));
        assertEquals(Arrays.asList(0, 1), ids.get(1));
    }

    @Test
    public void hiddenSubSet2() throws Exception {
        //GIVEN
        //expected hidden triple = 2,4,5
        List<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(1, 2, 4, 5));
        list.add(Arrays.asList(4, 5));
        list.add(Arrays.asList(2, 5, 6));
        list.add(Arrays.asList(1, 7, 8));
        list.add(Arrays.asList(7, 8));
        list.add(Arrays.asList(7, 6));
        list.add(Arrays.asList(1, 6));
        //WHEN
        List<List<Integer>> ids = hiddenSubset(list, 3);
        //THEN
        assertEquals(2, ids.size());
        assertEquals(Arrays.asList(2, 4, 5), ids.get(0));
        assertEquals(Arrays.asList(0, 1, 2), ids.get(1));
    }

    @Test
    public void hiddenSubSet3() throws Exception {
        //GIVEN
        //expected hidden triple =
        List<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(1, 3, 7));
        list.add(Arrays.asList(1, 3, 7));
        list.add(Arrays.asList(4, 6, 8));
        list.add(Arrays.asList(1, 3));
        list.add(Arrays.asList(4, 8));
        list.add(Arrays.asList(4, 6));
        //WHEN
        List<List<Integer>> ids = hiddenSubset(list, 3);
        //THEN
        assertEquals(2, ids.size());
        assertEquals(Arrays.asList(4, 6, 8), ids.get(0));
        assertEquals(Arrays.asList(2, 4, 5), ids.get(1));
    }
}
