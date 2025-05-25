package com.labs.dm.sudoku.solver.alg.naked;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Daniel Mroczka on 2016-03-26.
 */
public class NakedQuintTest {

    private final NakedQuint nakedQuint = new NakedQuint();

    @Test
    @Disabled
    public void test() {
        // 1,2,3,6,8
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 1, new Integer[]{1, 8});
        matrix.addCandidates(0, 2, new Integer[]{3, 5, 6, 7, 8});
        matrix.addCandidates(0, 3, new Integer[]{5, 6, 7});
        matrix.addCandidates(0, 4, new Integer[]{3, 6, 8});
        matrix.addCandidates(0, 5, new Integer[]{1, 2, 3, 6, 8});
        matrix.addCandidates(0, 6, new Integer[]{1, 3, 6});
        matrix.addCandidates(0, 7, new Integer[]{1, 2, 3, 8});
        //WHEN
        nakedQuint.execute(matrix);
        //THEN
        assertEquals(2, matrix.getCandidates(0, 2).size());
        assertEquals(2, matrix.getCandidates(0, 3).size());
    }
}
