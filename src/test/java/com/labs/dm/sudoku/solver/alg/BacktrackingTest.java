package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.LogListener;
import com.labs.dm.sudoku.solver.io.MatrixLoader;

import static org.junit.Assert.assertTrue;

/**
 * Created by daniel on 2016-03-10.
 */
public class BacktrackingTest {

    private IAlgorithm back = new Backtracking();

    //@Test
    // @Ignore
    public void execute() throws Exception {
        IMatrix matrix = new MatrixLoader().load("patterns/solved.txt");
        matrix.addMatrixListener(new LogListener());
        matrix.setValueAt(0, 0, 0);
        matrix.setValueAt(0, 1, 0);
        matrix.setValueAt(1, 0, 0);
        matrix.setValueAt(2, 0, 0);
        matrix.setValueAt(0, 2, 0);
        matrix.setValueAt(1, 1, 0);

        matrix.setValueAt(0, 3, 0);
        matrix.setValueAt(1, 2, 0);
        matrix.setValueAt(2, 1, 0);
        matrix.setValueAt(3, 0, 0);

        new GenerateCandidates().execute(matrix);

        back.execute(matrix);

        assertTrue(matrix.isSolved());
    }
}