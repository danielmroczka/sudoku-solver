package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import com.labs.dm.sudoku.solver.io.MatrixLoader;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by daniel on 2016-02-15.
 */
public class XWingTest {

    private IAlgorithm alg = new XWing();
    private IAlgorithm cand = new GenerateCandidates();
    private IAlgorithm alg1 = new OpenSingles();
    private IAlgorithm alg2 = new LoneSingles();

    @Test
    public void shouldTestEmptyMarix() {
        IMatrix matrix = new Matrix();
        cand.execute(matrix);
        alg.execute(matrix);
    }

    @Test
    public void testInRow() {
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.setPossibleValues(0, 0, new HashSet<>(Arrays.asList(new Integer[]{1, 3})));
        matrix.setPossibleValues(0, 4, new HashSet<>(Arrays.asList(new Integer[]{1, 3})));
        matrix.setPossibleValues(0, 6, new HashSet<>(Arrays.asList(new Integer[]{1, 3})));
        matrix.setPossibleValues(4, 0, new HashSet<>(Arrays.asList(new Integer[]{1, 3})));
        matrix.setPossibleValues(4, 4, new HashSet<>(Arrays.asList(new Integer[]{1, 3})));
        matrix.setPossibleValues(4, 6, new HashSet<>(Arrays.asList(new Integer[]{1, 3})));
        //WHEN
        alg.execute(matrix);
        //THEN
        assertEquals(0, matrix.getPossibleValues(0, 6).size());
        assertEquals(0, matrix.getPossibleValues(4, 6).size());
        assertEquals(2, matrix.getPossibleValues(0, 0).size());
        assertEquals(2, matrix.getPossibleValues(0, 4).size());
        assertEquals(2, matrix.getPossibleValues(4, 0).size());
        assertEquals(2, matrix.getPossibleValues(4, 4).size());
    }

    @Test
    public void testInCol() {
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.setPossibleValues(0, 0, new HashSet<>(Arrays.asList(new Integer[]{1, 3})));
        matrix.setPossibleValues(4, 0, new HashSet<>(Arrays.asList(new Integer[]{1, 3})));
        matrix.setPossibleValues(6, 0, new HashSet<>(Arrays.asList(new Integer[]{1, 3})));
        matrix.setPossibleValues(0, 4, new HashSet<>(Arrays.asList(new Integer[]{1, 3})));
        matrix.setPossibleValues(4, 4, new HashSet<>(Arrays.asList(new Integer[]{1, 3})));
        matrix.setPossibleValues(6, 4, new HashSet<>(Arrays.asList(new Integer[]{1, 3})));
        //WHEN
        alg.execute(matrix);

        //THEN
        assertEquals(0, matrix.getPossibleValues(6, 0).size());
        assertEquals(0, matrix.getPossibleValues(6, 4).size());
        assertEquals(2, matrix.getPossibleValues(4, 0).size());
        assertEquals(2, matrix.getPossibleValues(4, 4).size());
        assertEquals(2, matrix.getPossibleValues(0, 0).size());
        assertEquals(2, matrix.getPossibleValues(0, 4).size());
    }

    @Test
    public void testFromFile() throws IOException {
        IMatrix matrix = new MatrixLoader().load("src/test/resources/patterns/hiddenPair.txt");
        cand.execute(matrix);
        assertTrue(matrix.getCandidatesCount() <= 98);
        alg.execute(matrix);
        assertTrue(matrix.getCandidatesCount() <= 98);
    }

}