package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Daniel Mroczka on 2016-03-19.
 */
public class HiddenSubSetTest {

    private HiddenSubSet hiddenSubset = new HiddenSubSet() {
        @Override
        public void execute(IMatrix matrix) {
            super.execute(matrix);
        }
    };

    @Test
    public void shouldRemove() throws Exception {
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{1, 2, 4, 5, 6, 7, 8});
        List<Integer> subset = Arrays.asList(1, 2, 3);
        //WHEN
        hiddenSubset.removeCandidate(matrix, 0, 0, subset);
        //THEN
        assertEquals(2, matrix.getCandidates(0, 0).size());
    }

    @Test
    public void shouldNotRemove() throws Exception {
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{1, 2, 3, 4, 5, 6});
        List<Integer> subset = Arrays.asList(7, 8, 9);
        //WHEN
        hiddenSubset.removeCandidate(matrix, 0, 0, subset);
        //THEN
        assertEquals(6, matrix.getCandidates(0, 0).size());
    }

    @Test
    public void testGroup() throws Exception {
        //GIVEN
        List<HiddenSubSet.Subset> result = new ArrayList<>();
        Integer[][] tab = new Integer[9][9];
        //WHEN
        hiddenSubset.group(result, tab);
        //THEN
        //assertTrue(result.isEmpty());
    }
}
