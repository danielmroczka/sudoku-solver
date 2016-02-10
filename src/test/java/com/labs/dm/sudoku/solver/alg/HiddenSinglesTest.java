/*
 * Copyright daniel.mroczka@gmail.com. All rights reserved. 
 */

package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import org.junit.Test;

/**
 * @author daniel
 */
public class HiddenSinglesTest {

    @Test
    public void testExecute() {
        System.out.println("execute");
        IMatrix matrix = null;
        HiddenSingles instance = new HiddenSingles();
        instance.execute(matrix);
        // TODO review the generated test code and remove the default call to fail.
        //  fail("The test case is a prototype.");
    }

}
