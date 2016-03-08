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

    private final LoneSingles loneSingles = new LoneSingles();
    private final OpenSingles openSingles = new OpenSingles();
    private final IAlgorithm cand = new GenerateCandidates();
    private IMatrix matrix;
    private final int COUNT = 50;

    @Before
    public void before() throws IOException {
        matrix = new MatrixLoader().load("src/test/resources/patterns/hard.txt");
        cand.execute(matrix);
        matrix.validate();
    }

    @Test
    public void validateXWing() throws IOException {
        for (int i = 0; i < COUNT; i++) {
            loneSingles.execute(matrix);
            openSingles.execute(matrix);
            new XWing().execute(matrix);
            matrix.validate();
        }
    }

    @Test
    public void validateXYWing() throws IOException {
        for (int i = 0; i < COUNT; i++) {
            loneSingles.execute(matrix);
            openSingles.execute(matrix);
            new XYWing().execute(matrix);
            matrix.validate();
        }
    }

    @Test
    public void validateXYZWing() throws IOException {
        for (int i = 0; i < COUNT; i++) {
            loneSingles.execute(matrix);
            openSingles.execute(matrix);
            new XYZWing().execute(matrix);
            matrix.validate();
        }
    }

    @Test
    public void validateHiddenPairs() throws IOException {
        for (int i = 0; i < COUNT; i++) {
            loneSingles.execute(matrix);
            openSingles.execute(matrix);
            new HiddenPairs().execute(matrix);
            matrix.validate();
        }
    }

    @Test
    public void validateHiddenTriples() throws IOException {
        for (int i = 0; i < COUNT; i++) {
            loneSingles.execute(matrix);
            openSingles.execute(matrix);
            new HiddenTriples().execute(matrix);
            matrix.validate();
        }
    }

    @Test
    public void validateNakedTriples() throws IOException {
        for (int i = 0; i < COUNT; i++) {
            loneSingles.execute(matrix);
            openSingles.execute(matrix);
            new NakedTriplets().execute(matrix);
            matrix.validate();
        }
    }

    @Test
    public void validateHiddenSingles() throws IOException {
        for (int i = 0; i < COUNT; i++) {
            loneSingles.execute(matrix);
            openSingles.execute(matrix);
            new HiddenSingles().execute(matrix);
            matrix.validate();
        }
    }

    @Test
    public void validateNakedPairs() throws IOException {
        for (int i = 0; i < COUNT; i++) {
            loneSingles.execute(matrix);
            openSingles.execute(matrix);
            new NakedPairs().execute(matrix);
            matrix.validate();
        }
    }

    @Test
    public void validateReduction() throws IOException {
        for (int i = 0; i < COUNT; i++) {
            loneSingles.execute(matrix);
            openSingles.execute(matrix);
            new Reduction().execute(matrix);
            matrix.validate();
        }
    }

    @Test
    public void validateSwordFish() throws IOException {
        System.out.println(matrix.printCandidates());
        for (int i = 0; i < COUNT; i++) {
            loneSingles.execute(matrix);
            openSingles.execute(matrix);
            new SwordFish().execute(matrix);
            System.out.println(matrix.printCandidates());
            matrix.validate();
        }
    }
}
