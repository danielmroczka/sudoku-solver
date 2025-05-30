package com.labs.dm.sudoku.solver.utils;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import com.labs.dm.sudoku.solver.core.Pair;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.labs.dm.sudoku.solver.utils.Utils.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Daniel Mroczka on 2016-02-13.
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
    public void testFactorial2() {
        assertThrows(IllegalArgumentException.class, () -> factorial(-1));
    }

    @Test
    public void testCombination() {
        assertEquals(6, combination(4, 2));
        assertEquals(10, combination(5, 2));
        assertEquals(10, combination(5, 3));
        assertEquals(5, combination(5, 4));
        assertEquals(5, combination(5, 1));
    }

    @Test
    public void testCombinationList() {
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
    public void testTheSameBlock() {
        assertTrue(theSameBlock(0, 2));
        assertTrue(theSameBlock(0, 1, 2));
        assertFalse(theSameBlock(0, 3));
        assertFalse(theSameBlock(0, 2, 3, 8));
    }

    @Test
    public void intersection() {
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
        assertEquals(3, Utils.blockElems(3).length);
        assertArrayEquals(new int[]{0, 1, 2}, Utils.blockElems(0));
        assertArrayEquals(new int[]{0, 1, 2}, Utils.blockElems(1));
        assertArrayEquals(new int[]{0, 1, 2}, Utils.blockElems(2));
        assertArrayEquals(new int[]{3, 4, 5}, Utils.blockElems(3));
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
    @Disabled
    public void compare() {
        // assertTrue(Utils.acceptPivotAndPincets(Arrays.asList(1, 2), Arrays.asList(2, 7), Arrays.asList(1, 7)));
        //  assertFalse(Utils.acceptPivotAndPincets(Arrays.asList(1, 2), Arrays.asList(2, 7), Arrays.asList(8, 7)));
        //  assertFalse(Utils.acceptPivotAndPincets(Arrays.asList(1, 2), Arrays.asList(2, 7), Arrays.asList(2, 7)));
        //  assertFalse(Utils.acceptPivotAndPincets(Arrays.asList(1, 1), Arrays.asList(2, 2), Arrays.asList(7, 7)));
    }

    @Test
    public void shouldMatch() {
        assertEquals(4, Utils.match(Arrays.asList(1, 2, 3, 4), Arrays.asList(1, 2, 3, 4)));
        assertEquals(3, Utils.match(Arrays.asList(1, 2, 3, 4), Arrays.asList(1, 2, 3)));
        assertEquals(2, Utils.match(Arrays.asList(1, 2), Arrays.asList(1, 2, 3, 4)));
        assertEquals(2, Utils.match(Arrays.asList(1, 2, 3, 4), Arrays.asList(1, 2)));

    }

    @Test
    public void shouldGetOccurencesInRow() {
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{1, 2, 3});
        matrix.addCandidates(0, 8, new Integer[]{2, 3, 4});
        matrix.addCandidates(1, 0, new Integer[]{3, 4, 5});
        //WHEN
        Map<Integer, List<Pair>> result = Utils.getOccurencesInRow(matrix, 0, 1);
        //THEN
        assertEquals(5, result.size());
        assertEquals(1, result.get(1).size());
        assertEquals(2, result.get(2).size());
        assertEquals(3, result.get(3).size());
        assertEquals(2, result.get(4).size());
        assertEquals(1, result.get(5).size());
        assertNull(result.get(9));
    }

    @Test
    public void shouldGetOccurencesInCol() {
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{1, 2, 3});
        matrix.addCandidates(8, 0, new Integer[]{2, 3, 4});
        matrix.addCandidates(0, 1, new Integer[]{3, 4, 5});
        //WHEN
        Map<Integer, List<Pair>> result = Utils.getOccurencesInCol(matrix, 0, 1);
        //THEN
        assertEquals(5, result.size());
        assertEquals(1, result.get(1).size());
        assertEquals(2, result.get(2).size());
        assertEquals(3, result.get(3).size());
        assertEquals(2, result.get(4).size());
        assertEquals(1, result.get(5).size());
        assertNull(result.get(9));
    }
}
