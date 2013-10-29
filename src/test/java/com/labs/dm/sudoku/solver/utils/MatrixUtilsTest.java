/*
 * Copyright daniel.mroczka@gmail.com. All rights reserved. 
 */

package com.labs.dm.sudoku.solver.utils;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author daniel
 */
public class MatrixUtilsTest {
    
    @Test
    public void testGetPos() {
        assertEquals(0, MatrixUtils.getPos(0).x);
        assertEquals(0, MatrixUtils.getPos(0).y);
        assertEquals(8, MatrixUtils.getPos(8).x);
        assertEquals(0, MatrixUtils.getPos(8).y);
        assertEquals(0, MatrixUtils.getPos(72).x);
        assertEquals(8, MatrixUtils.getPos(72).y);
        assertEquals(8, MatrixUtils.getPos(80).x);
        assertEquals(8, MatrixUtils.getPos(80).y);
    }
    
}
