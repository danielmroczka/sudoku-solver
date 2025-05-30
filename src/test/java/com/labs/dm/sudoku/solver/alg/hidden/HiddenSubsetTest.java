package com.labs.dm.sudoku.solver.alg.hidden;

import com.labs.dm.sudoku.solver.alg.helpers.Subset;
import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Daniel Mroczka on 2016-03-19.
 */
public class HiddenSubsetTest {

    private final HiddenSubset hiddenSubset = new HiddenSubset() {
        @Override
        public void execute(IMatrix matrix) {
            super.execute(matrix);
        }
    };

    @Test
    public void shouldRemove() {
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{1, 2, 4, 5, 6, 7, 8});
        List<Integer> subset = Arrays.asList(1, 2, 3);
        //WHEN
        hiddenSubset.removeCandidates(matrix, 0, 0, subset);
        //THEN
        assertEquals(2, matrix.getCandidates(0, 0).size());
    }

    @Test
    public void shouldNotRemove() {
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{1, 2, 3, 4, 5, 6});
        List<Integer> subset = Arrays.asList(7, 8, 9);
        //WHEN
        hiddenSubset.removeCandidates(matrix, 0, 0, subset);
        //THEN
        assertEquals(6, matrix.getCandidates(0, 0).size());
    }

    @Test
    public void testGroup() {
        //GIVEN
        Integer[][] tab = new Integer[9][9];
        //WHEN
        List<Subset> result = hiddenSubset.findSubsets(tab, 2);
        //THEN
        assertTrue(result.isEmpty());
    }

    @Test
    public void findSubset1() {
        //GIVEN
        Integer[][] tab = new Integer[9][9];
        tab[0][0] = 0;
        tab[1][0] = 0;
        tab[0][1] = 1;
        tab[1][1] = 1;
        //WHEN
        List<Subset> result = hiddenSubset.findSubsets(tab, 2);
        //THEN
        assertEquals(Arrays.asList(1, 2), result.get(0).subsetNumber());
        assertEquals(Arrays.asList(0, 1), result.get(0).subsetPosition());
    }

    @Test
    public void findSubset2() {
        //GIVEN
        Integer[][] tab = new Integer[9][9];
        tab[0][0] = 0;
        tab[2][0] = 0;
        tab[5][0] = 0;
        tab[0][3] = 3;
        tab[2][3] = 3;
        tab[0][4] = 4;
        tab[5][4] = 4;
        //WHEN
        List<Subset> result = hiddenSubset.findSubsets(tab, 3);
        //THEN
        assertEquals(Arrays.asList(1, 3, 6), result.get(0).subsetNumber());
        assertEquals(Arrays.asList(0, 3, 4), result.get(0).subsetPosition());
    }

    @Test
    public void findSubsetSmallBlock() {
        //GIVEN
        Integer[][] tab = new Integer[9][9];
        tab[0][0] = 0;
        tab[1][0] = 0;
        tab[3][0] = 0;
        tab[4][0] = 0;

        tab[3][4] = 4;
        tab[4][4] = 4;
        tab[1][8] = 8;
        tab[4][8] = 8;
        tab[5][8] = 8;
        //WHEN
        List<Subset> result = hiddenSubset.findSubsets(tab, 3);
        //THEN
        assertEquals(1, result.size());
        assertEquals(Arrays.asList(2, 4, 5), result.get(0).subsetNumber());
        assertEquals(Arrays.asList(0, 4, 8), result.get(0).subsetPosition());
    }

    @Test
    public void findSubset3() {
        //GIVEN
        Integer[][] tab = new Integer[9][9];
        tab[0][0] = 0;
        tab[1][0] = 0;
        tab[2][0] = 0;

        tab[0][1] = 1;
        tab[1][1] = 1;
        tab[2][1] = 1;

        tab[0][2] = 2;
        tab[1][2] = 2;
        tab[2][2] = 2;

        tab[1][3] = 3;
        List<Subset> result = hiddenSubset.findSubsets(tab, 3);
        //THEN
        assertThat(result, is(empty()));
    }

    @Test
    public void findSubsetPair() {
        //GIVEN
        Integer[][] tab = new Integer[9][9];
        tab[0][0] = 0;
        tab[1][0] = 0;
        tab[2][0] = 0;

        tab[1][1] = 1;
        tab[2][1] = 1;
        tab[3][1] = 1;

        List<Subset> result = hiddenSubset.findSubsets(tab, 2);
        //THEN
        assertEquals(1, result.size());
        assertEquals(Arrays.asList(2, 3), result.get(0).subsetNumber());
        assertEquals(Arrays.asList(0, 1), result.get(0).subsetPosition());
    }

    @Test
    public void fillTabRows() {
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{1, 2, 3});
        matrix.addCandidates(0, 1, new Integer[]{2, 3, 4});
        matrix.addCandidates(0, 2, new Integer[]{3, 4, 5});

        Integer[][] tab = hiddenSubset.fillTabRows(matrix, 0);
        assertEquals(Integer.valueOf(0), tab[0][0]);
        assertEquals(Integer.valueOf(1), tab[1][1]);
        assertEquals(Integer.valueOf(2), tab[2][2]);
    }

}
