package com.labs.dm.sudoku.solver.alg.naked;

import com.labs.dm.sudoku.solver.alg.IAlgorithm;
import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Daniel Mroczka on 2016-02-11.
 */
public class NakedPairsTest {

    @Test
    public void execute() {
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.setValueAt(0, 1, 9);
        matrix.setValueAt(0, 4, 6);
        matrix.setValueAt(0, 7, 8);

        matrix.addCandidates(0, 0, new Integer[]{1, 2, 4, 5});
        matrix.addCandidates(0, 2, new Integer[]{1, 2, 4, 5, 7});
        matrix.addCandidates(0, 3, new Integer[]{2, 4, 5, 7});
        matrix.addCandidates(0, 5, new Integer[]{2, 3});
        matrix.addCandidates(0, 6, new Integer[]{2, 3});
        matrix.addCandidates(0, 8, new Integer[]{2, 3, 5});

        IAlgorithm nakedPairs = new NakedPairs();
        //WHEN
        nakedPairs.execute(matrix);
        //THEN
        assertEquals(2, matrix.getCandidates(0, 0).size());
        assertEquals(3, matrix.getCandidates(0, 2).size());
        assertEquals(2, matrix.getCandidates(0, 3).size());
        assertEquals(2, matrix.getCandidates(0, 5).size());
        assertEquals(2, matrix.getCandidates(0, 6).size());
        assertEquals(0, matrix.getCandidates(0, 8).size());

    }

    @Test
    public void executeBlock() {
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.setValueAt(3, 3, 9);
        matrix.setValueAt(3, 4, 6);
        matrix.setValueAt(3, 5, 8);

        matrix.addCandidates(4, 3, new Integer[]{1, 2, 4, 5});
        matrix.addCandidates(4, 4, new Integer[]{1, 2, 4, 5, 7});
        matrix.addCandidates(4, 5, new Integer[]{2, 4, 5, 7});
        matrix.addCandidates(5, 3, new Integer[]{2, 3});
        matrix.addCandidates(5, 4, new Integer[]{2, 3});
        matrix.addCandidates(5, 5, new Integer[]{2, 3, 5});

        IAlgorithm nakedPairs = new NakedPairs();
        //WHEN
        nakedPairs.execute(matrix);
        //THEN
        assertEquals(2, matrix.getCandidates(4, 3).size());
        assertEquals(3, matrix.getCandidates(4, 4).size());
        assertEquals(2, matrix.getCandidates(4, 5).size());
        assertEquals(2, matrix.getCandidates(5, 3).size());
        assertEquals(2, matrix.getCandidates(5, 4).size());
        assertEquals(0, matrix.getCandidates(5, 5).size());

    }
}
