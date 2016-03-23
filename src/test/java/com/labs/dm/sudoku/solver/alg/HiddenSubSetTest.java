package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

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
        hiddenSubset.group(result, tab, 2);
        //THEN
        assertTrue(result.isEmpty());
    }

    @Test
    public void findSubset1() throws Exception {
        //GIVEN
        List<HiddenSubSet.Subset> result = new ArrayList<>();
        Integer[][] tab = new Integer[9][9];
        tab[0][0] = 0;
        tab[1][0] = 0;
        tab[0][1] = 1;
        tab[1][1] = 1;
        //WHEN
        hiddenSubset.group(result, tab, 2);
        //THEN
        assertEquals(Arrays.asList(1, 2), result.get(0).getSubsetNumber());
        assertEquals(Arrays.asList(0, 1), result.get(0).getSubsetPosition());
    }

    @Test
    public void findSubset2() throws Exception {
        //GIVEN
        List<HiddenSubSet.Subset> result = new ArrayList<>();
        Integer[][] tab = new Integer[9][9];
        tab[0][0] = 0;
        tab[2][0] = 0;
        tab[5][0] = 0;
        tab[0][3] = 3;
        tab[2][3] = 3;
        tab[0][4] = 4;
        tab[5][4] = 4;
        //WHEN
        hiddenSubset.group(result, tab, 3);
        //THEN
        assertEquals(Arrays.asList(1, 3, 6), result.get(0).getSubsetNumber());
        assertEquals(Arrays.asList(0, 3, 4), result.get(0).getSubsetPosition());
    }

    @Test
    public void findSubsetSmallBlock() throws Exception {
        //GIVEN
        List<HiddenSubSet.Subset> result = new ArrayList<>();
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
        hiddenSubset.group(result, tab, 3);
        //THEN
        assertEquals(1, result.size());
        assertEquals(Arrays.asList(2, 4, 5), result.get(0).getSubsetNumber());
        assertEquals(Arrays.asList(0, 4, 8), result.get(0).getSubsetPosition());
    }

    @Test
    public void findSubset3() throws Exception {
        //GIVEN
        List<HiddenSubSet.Subset> result = new ArrayList<>();
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
        hiddenSubset.group(result, tab, 3);
        //THEN
        assertThat(result, is(empty()));
    }

    @Test
    public void findSubsetPair() throws Exception {
        //GIVEN
        List<HiddenSubSet.Subset> result = new ArrayList<>();
        Integer[][] tab = new Integer[9][9];
        tab[0][0] = 0;
        tab[1][0] = 0;
        tab[2][0] = 0;


        tab[1][1] = 1;
        tab[2][1] = 1;
        tab[3][1] = 1;


        hiddenSubset.group(result, tab, 2);
        //THEN
        assertEquals(1, result.size());
        assertEquals(Arrays.asList(2, 3), result.get(0).getSubsetNumber());
        assertEquals(Arrays.asList(0, 1), result.get(0).getSubsetPosition());
    }

    @Test
    public void fillTabRows() throws Exception {
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{1, 2, 3});
        matrix.addCandidates(0, 1, new Integer[]{2, 3, 4});
        matrix.addCandidates(0, 2, new Integer[]{3, 4, 5});

        Integer[][] tab = hiddenSubset.fillTabRows(matrix, 0);
        assertEquals(new Integer(0), tab[0][0]);
        assertEquals(new Integer(1), tab[1][1]);
        assertEquals(new Integer(2), tab[2][2]);
    }
}
