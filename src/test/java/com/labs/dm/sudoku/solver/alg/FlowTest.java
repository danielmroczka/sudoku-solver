/*
 * Copyright daniel.mroczka@gmail.com. All rights reserved. 
 */

package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.io.MatrixLoader;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
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
    
}
