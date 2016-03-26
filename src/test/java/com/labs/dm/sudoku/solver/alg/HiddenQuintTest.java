package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by daniel on 2016-03-26.
 */
public class HiddenQuintTest {

    private final HiddenQuint hiddenQuint = new HiddenQuint();

    @Test
    @Ignore
    public void shouldFoundQuadsInRow() {
        //Should find hidden quad: 1,2,5,7,9
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{1, 7, 9});
        matrix.addCandidates(0, 1, new Integer[]{4, 7});
        matrix.addCandidates(0, 2, new Integer[]{3, 4});
        matrix.addCandidates(0, 3, new Integer[]{1, 2, 4, 5, 9});
        matrix.addCandidates(0, 4, new Integer[]{1, 2, 4, 5, 7});
        matrix.addCandidates(0, 5, new Integer[]{1, 2, 4, 7, 9});
        //WHEN
        hiddenQuint.execute(matrix);
        //THEN
        assertEquals(1, matrix.getCandidates(0, 1).size());
        assertEquals(4, matrix.getCandidates(0, 3).size());
        assertEquals(5, matrix.getCandidates(0, 4).size());
        assertEquals(4, matrix.getCandidates(0, 5).size());
    }

}