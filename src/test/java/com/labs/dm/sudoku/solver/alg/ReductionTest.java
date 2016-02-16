package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by daniel on 2016-02-15.
 */
public class ReductionTest {

    private IAlgorithm reduction = new Reduction();

    @Test
    public void simple2() throws Exception {
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{4, 6});
        matrix.addCandidates(0, 1, new Integer[]{1, 4, 5, 6});
        matrix.addCandidates(0, 4, new Integer[]{1, 2, 6});

        matrix.addCandidates(1, 2, new Integer[]{1, 4, 5, 6});
        matrix.addCandidates(2, 1, new Integer[]{1, 4, 6, 9});
        matrix.addCandidates(2, 2, new Integer[]{1, 4, 6, 9});

        matrix.addCandidates(3, 0, new Integer[]{1, 5, 6});
        matrix.addCandidates(3, 1, new Integer[]{1, 5, 6});
        matrix.addCandidates(3, 2, new Integer[]{1, 5, 6});
        matrix.addCandidates(3, 3, new Integer[]{1, 5, 6});

        matrix.addCandidates(1, 4, new Integer[]{1, 5, 6});
        matrix.addCandidates(2, 4, new Integer[]{1, 5, 6});
        matrix.addCandidates(3, 4, new Integer[]{1, 5, 6});
        int before = matrix.getCandidatesCount();
        reduction.execute(matrix);
        assertEquals(3, before - matrix.getCandidatesCount());
    }

    @Test
    public void execute2() throws Exception {
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{4, 6});
        matrix.addCandidates(0, 1, new Integer[]{1, 4, 5, 6});
        matrix.addCandidates(0, 4, new Integer[]{6});

        matrix.addCandidates(1, 0, new Integer[]{4, 6});
        matrix.addCandidates(2, 2, new Integer[]{4, 6});

        matrix.addCandidates(1, 4, new Integer[]{4, 6});
        matrix.addCandidates(2, 4, new Integer[]{4, 6});
        matrix.addCandidates(3, 0, new Integer[]{4, 6});
        matrix.addCandidates(3, 1, new Integer[]{4, 6});

        int before = matrix.getCandidatesCount();
        reduction.execute(matrix);

        assertEquals(1, matrix.getPossibleValues(1, 0).size());
        assertEquals(1, matrix.getPossibleValues(2, 2).size());

        assertEquals(2, matrix.getPossibleValues(1, 4).size());
        assertEquals(2, matrix.getPossibleValues(2, 4).size());
        assertEquals(2, matrix.getPossibleValues(3, 0).size());
        assertEquals(2, matrix.getPossibleValues(3, 1).size());

        assertEquals(2, before - matrix.getCandidatesCount());
    }

    @Test
    public void execute4() throws Exception {
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{4, 6});
        matrix.addCandidates(1, 0, new Integer[]{1, 4, 5, 6});
        matrix.addCandidates(4, 0, new Integer[]{6});

        matrix.addCandidates(0, 1, new Integer[]{4, 6});
        matrix.addCandidates(2, 2, new Integer[]{4, 6});

        matrix.addCandidates(4, 1, new Integer[]{4, 6});
        matrix.addCandidates(4, 2, new Integer[]{4, 6});
        matrix.addCandidates(0, 3, new Integer[]{4, 6});
        matrix.addCandidates(1, 3, new Integer[]{4, 6});

        int before = matrix.getCandidatesCount();
        reduction.execute(matrix);

        assertEquals(1, matrix.getPossibleValues(0, 1).size());
        assertEquals(1, matrix.getPossibleValues(2, 2).size());

        assertEquals(2, matrix.getPossibleValues(4, 1).size());
        assertEquals(2, matrix.getPossibleValues(4, 2).size());
        assertEquals(2, matrix.getPossibleValues(0, 3).size());
        assertEquals(2, matrix.getPossibleValues(1, 3).size());

        assertEquals(2, before - matrix.getCandidatesCount());
    }

    @Test
    @Ignore
    public void execute3() throws Exception {
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{4, 6});
        matrix.addCandidates(0, 1, new Integer[]{1, 4, 5, 6});
        matrix.addCandidates(0, 2, new Integer[]{4, 6});
        matrix.addCandidates(0, 3, new Integer[]{6});

        matrix.addCandidates(1, 0, new Integer[]{4, 6});
        matrix.addCandidates(1, 1, new Integer[]{4, 6});
        matrix.addCandidates(1, 2, new Integer[]{4, 6});
        matrix.addCandidates(2, 0, new Integer[]{4, 6});
        matrix.addCandidates(2, 1, new Integer[]{4, 6});
        matrix.addCandidates(2, 2, new Integer[]{4, 6});
        matrix.addCandidates(0, 4, new Integer[]{6});
        matrix.addCandidates(1, 4, new Integer[]{4, 6});
        matrix.addCandidates(2, 4, new Integer[]{4, 6});

        int before = matrix.getCandidatesCount();
        reduction.execute(matrix);
        assertEquals(6, before - matrix.getCandidatesCount());
    }
}