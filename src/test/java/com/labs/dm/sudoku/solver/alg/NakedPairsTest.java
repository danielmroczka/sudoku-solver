package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

/**
 * Created by daniel on 2016-02-11.
 */
public class NakedPairsTest {

    @Test
    public void execute() throws Exception {
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.setCellValue(0, 1, 9);
        matrix.setCellValue(0, 4, 6);
        matrix.setCellValue(0, 7, 8);

        //MatrixUtils.initCandiates(matrix);

        matrix.setPossibleValues(0, 0, new HashSet<>(Arrays.asList(new Integer[]{1,2,4,5})));
        matrix.setPossibleValues(0, 2, new HashSet<>(Arrays.asList(new Integer[]{1,2,4,5,7})));
        matrix.setPossibleValues(0, 3, new HashSet<>(Arrays.asList(new Integer[]{2,4,5,7})));
        matrix.setPossibleValues(0, 5, new HashSet<>(Arrays.asList(new Integer[]{2,3})));
        matrix.setPossibleValues(0, 6, new HashSet<>(Arrays.asList(new Integer[]{2,3})));
        matrix.setPossibleValues(0, 8, new HashSet<>(Arrays.asList(new Integer[]{2,3,5})));

        IAlgorithm nakedPairs = new NakedPairs();
        //WHEN
        nakedPairs.execute(matrix);
        //THEN
        assertEquals(3, matrix.getPossibleValues(0, 0).size());
        assertEquals(4, matrix.getPossibleValues(0, 2).size());
        assertEquals(3, matrix.getPossibleValues(0, 3).size());
        assertEquals(0, matrix.getPossibleValues(0, 5).size());
        assertEquals(0, matrix.getPossibleValues(0, 6).size());
        assertEquals(1, matrix.getPossibleValues(0, 8).size());

    }

    @Test
    @Ignore
    public void executeBlock() throws Exception {
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.setCellValue(3, 3, 9);
        matrix.setCellValue(3, 4, 6);
        matrix.setCellValue(3, 5, 8);

        matrix.setPossibleValues(4, 3, new HashSet<>(Arrays.asList(new Integer[]{1,2,4,5})));
        matrix.setPossibleValues(4, 4, new HashSet<>(Arrays.asList(new Integer[]{1,2,4,5,7})));
        matrix.setPossibleValues(4, 5, new HashSet<>(Arrays.asList(new Integer[]{2,4,5,7})));
        matrix.setPossibleValues(5, 3, new HashSet<>(Arrays.asList(new Integer[]{2,3})));
        matrix.setPossibleValues(5, 4, new HashSet<>(Arrays.asList(new Integer[]{2,3})));
        matrix.setPossibleValues(5, 5, new HashSet<>(Arrays.asList(new Integer[]{2,3,5})));

        IAlgorithm nakedPairs = new NakedPairs();
        //WHEN
        nakedPairs.execute(matrix);
        //THEN
        assertEquals(3, matrix.getPossibleValues(4, 3).size());
        assertEquals(4, matrix.getPossibleValues(0, 2).size());
        assertEquals(3, matrix.getPossibleValues(0, 3).size());
        assertEquals(0, matrix.getPossibleValues(0, 5).size());
        assertEquals(0, matrix.getPossibleValues(0, 6).size());
        assertEquals(1, matrix.getPossibleValues(0, 8).size());

    }
}