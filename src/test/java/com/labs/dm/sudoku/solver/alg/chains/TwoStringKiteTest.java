package com.labs.dm.sudoku.solver.alg.chains;

import com.labs.dm.sudoku.solver.alg.IAlgorithm;
import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TwoStringKiteTest {

    private final IAlgorithm algorithm = new TwoStringKite();

    @Test
    void executeRemovesCandidateForValidKite() {
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 1, new Integer[]{5, 9});
        matrix.addCandidates(0, 7, new Integer[]{5, 8});
        matrix.addCandidates(1, 2, new Integer[]{5, 6});
        matrix.addCandidates(6, 2, new Integer[]{5, 7});
        matrix.addCandidates(6, 7, new Integer[]{1, 5});

        algorithm.execute(matrix);

        assertEquals(1, matrix.getValueAt(6, 7));
    }

    @Test
    void executeDoesNotRemoveWhenNoSharedBlockPivot() {
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 1, new Integer[]{5, 9});
        matrix.addCandidates(0, 7, new Integer[]{5, 8});
        matrix.addCandidates(3, 2, new Integer[]{5, 6});
        matrix.addCandidates(6, 2, new Integer[]{5, 7});
        matrix.addCandidates(6, 7, new Integer[]{1, 5});

        algorithm.execute(matrix);

        assertEquals(0, matrix.getValueAt(6, 7));
        assertTrue(matrix.getCandidates(6, 7).contains(5));
    }

    @Test
    void executeDoesNotRemoveWhenRowIsNotStrongLink() {
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 1, new Integer[]{5, 9});
        matrix.addCandidates(0, 7, new Integer[]{5, 8});
        matrix.addCandidates(0, 8, new Integer[]{2, 5});
        matrix.addCandidates(0, 2, new Integer[]{5, 6});
        matrix.addCandidates(6, 2, new Integer[]{5, 7});
        matrix.addCandidates(6, 7, new Integer[]{1, 5});

        algorithm.execute(matrix);

        assertEquals(0, matrix.getValueAt(6, 7));
        assertTrue(matrix.getCandidates(6, 7).contains(5));
    }
}

