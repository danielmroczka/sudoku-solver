/*
 * Copyright daniel.mroczka@gmail.com. All rights reserved. 
 */

package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.io.Generator;
import com.labs.dm.sudoku.solver.io.MatrixLoader;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author daniel
 */
public class FlowTest {

    @Test
    public void test() throws IOException {
        Flow flow = new Flow();
        IMatrix matrix = new MatrixLoader().load("patterns/alg/opensingles/002.txt");
        flow.execute(matrix);
        assertEquals(81, matrix.getSetElems());
    }

    @Test
    @Ignore
    public void test2() {
        Flow flow = new Flow();
        Generator g = new Generator();

        for (int i = 68; i > 0; i--) {
            IMatrix matrix = g.generate(i);
            flow.execute(matrix);
            System.out.println(matrix);
            assertTrue(matrix.isSolved());
        }
    }

}
