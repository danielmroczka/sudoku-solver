package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.io.MatrixLoader;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by daniel on 2016-02-17.
 */
public class ValidationTest {

    private IAlgorithm cand = new GenerateCandidates();
    private IMatrix matrix;
    private final int COUNT = 50;

    @Before
    public void before() throws IOException {
        matrix = new MatrixLoader().load("src/test/resources/patterns/tough1.txt");
        cand.execute(matrix);
        matrix.validate();
    }

    @Test
    public void validateXWing() throws IOException {
        for (int i = 0; i < COUNT; i++) {
            new LoneSingles().execute(matrix);
            new OpenSingles().execute(matrix);
            new XWing().execute(matrix);
            matrix.validate();
        }
    }

    @Test
    public void validateHiddenPairs() throws IOException {
        for (int i = 0; i < COUNT; i++) {
            new LoneSingles().execute(matrix);
            new OpenSingles().execute(matrix);
            new HiddenPairs().execute(matrix);
            matrix.validate();
        }
    }

    @Test
    public void validateHiddenSingles() throws IOException {
        for (int i = 0; i < COUNT; i++) {
            new LoneSingles().execute(matrix);
            new OpenSingles().execute(matrix);
            new HiddenSingles().execute(matrix);
            matrix.validate();
        }
    }

    @Test
    public void validateNakedPairs() throws IOException {
        for (int i = 0; i < COUNT; i++) {
            new LoneSingles().execute(matrix);
            new OpenSingles().execute(matrix);
            new NakedPairs().execute(matrix);
            matrix.validate();
        }
    }

    @Test
    public void validateReduction() throws IOException {
        for (int i = 0; i < COUNT; i++) {
            new LoneSingles().execute(matrix);
            new OpenSingles().execute(matrix);
            new Reduction().execute(matrix);
            matrix.validate();
        }
    }
}
