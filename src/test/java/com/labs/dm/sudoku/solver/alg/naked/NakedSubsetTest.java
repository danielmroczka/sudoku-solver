package com.labs.dm.sudoku.solver.alg.naked;

import com.labs.dm.sudoku.solver.core.IMatrix;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by daniel on 2016-03-27.
 */
public class NakedSubsetTest {

    private final NakedSubset nakedSubset = new NakedSubset() {
        @Override
        public void execute(IMatrix matrix) {
            super.execute(matrix);
        }
    };

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
        List<List<Integer>> ids = nakedSubset.nakedSubset(list, 3);
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
        List<List<Integer>> ids = nakedSubset.nakedSubset(list, 3);
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
        List<List<Integer>> ids = nakedSubset.nakedSubset(list, 3);
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
        List<List<Integer>> ids = nakedSubset.nakedSubset(list, 4);
        //THEN
        assertArrayEquals(new Integer[]{1, 3, 5, 7}, ids.get(0).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{5, 7}, ids.get(1).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{1, 3, 5}, ids.get(2).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{3, 7}, ids.get(3).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{1, 3, 5, 7}, ids.get(4).toArray(new Integer[0]));
    }
}
