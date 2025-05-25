package com.labs.dm.sudoku.solver.alg.experimental;

import com.labs.dm.sudoku.solver.alg.GenerateCandidates;
import com.labs.dm.sudoku.solver.alg.IAlgorithm;
import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.io.MatrixLoader;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Daniel Mroczka on 2016-03-10.
 */
public class BacktrackingTest {

    private final IAlgorithm back = new Backtracking();

    @Test
    @Disabled
    public void easy() throws Exception {
        IMatrix matrix = new MatrixLoader().load("patterns/easy/001.txt");
        new GenerateCandidates().execute(matrix);
        back.execute(matrix);
        assertTrue(matrix.isSolved());
    }

    @Test
    public void execute() throws Exception {
        //GIVEN
        IMatrix matrix = new MatrixLoader().load("patterns/solved.txt");
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
        //WHEN
        back.execute(matrix);
        //THEN
        assertTrue(matrix.isSolved());
    }
}


