/*
 * Copyright daniel.mroczka@gmail.com. All rights reserved. 
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.Assert;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author daniel
 */
public class OpenSinglesTest {

    private final OpenSingles singles = new OpenSingles();
    private IMatrix matrix;

    @Before
    public void setUp() {
        matrix = new Matrix();
    }

    @Test
    public void testOpenSingle() {
        int[] input = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        assertTrue(singles.fillOpenSingles(input));
        Assert.assertArrayEquals(new int[]{9, 1, 2, 3, 4, 5, 6, 7, 8}, input);
    }

    @Test
    public void testOpenSingleFilledArray() {
        int[] input = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertFalse(singles.fillOpenSingles(input));
        Assert.assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, input);
    }

    @Test
    public void testOpenSingleMoreGaps() {
        int[] input = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 0};
        assertFalse(singles.fillOpenSingles(input));
        Assert.assertArrayEquals(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 0}, input);
    }

    @Test
    public void testOpenSingleInCol() {
        matrix.setCols(4, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8});
        singles.execute(matrix);
        Assert.assertArrayEquals(new int[]{9, 1, 2, 3, 4, 5, 6, 7, 8}, matrix.getElemsInCol(4));
    }

    @Test
    public void testOpenSingleInRow() {
        matrix.setRows(4, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8});
        singles.execute(matrix);
        Assert.assertArrayEquals(new int[]{9, 1, 2, 3, 4, 5, 6, 7, 8}, matrix.getElemsInRow(4));
    }

    @Test
    public void testOpenSingleInBox() {
        matrix.setBox(1, 1, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8});
        singles.execute(matrix);
        System.out.println(matrix);
        Assert.assertArrayEquals(new int[]{9, 1, 2, 3, 4, 5, 6, 7, 8}, matrix.getElemsInBox(1, 1));
    }
}
