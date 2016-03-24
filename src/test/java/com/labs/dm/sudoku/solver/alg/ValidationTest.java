package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.io.MatrixLoader;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.labs.dm.sudoku.solver.executors.Executor.run;

/**
 * Created by Daniel Mroczka on 2016-02-17.
 */
public class ValidationTest {

    private final NakedSingles nakedSingles = new NakedSingles();
    private final OpenSingles openSingles = new OpenSingles();
    private final IAlgorithm cand = new GenerateCandidates();
    private IMatrix matrix;
    private final int COUNT = 50;

    @Before
    public void before() throws IOException {
        matrix = new MatrixLoader().load("patterns/hard/hard.txt");
        cand.execute(matrix);
        matrix.validate();
    }

    @Test
    public void validateXWing() throws IOException {
        for (int i = 0; i < COUNT; i++) {
            nakedSingles.execute(matrix);
            openSingles.execute(matrix);
            run(matrix, XWing.class);
            matrix.validate();
        }
    }

    @Test
    public void validateXYWing() throws IOException {
        for (int i = 0; i < COUNT; i++) {
            nakedSingles.execute(matrix);
            openSingles.execute(matrix);
            run(matrix, XYWing.class);
            matrix.validate();
        }
    }

    @Test
    public void validateXYZWing() throws IOException {
        for (int i = 0; i < COUNT; i++) {
            nakedSingles.execute(matrix);
            openSingles.execute(matrix);
            run(matrix, XYZWing.class);
            matrix.validate();
        }
    }

    @Test
    public void validateHiddenPairs() throws IOException {
        for (int i = 0; i < COUNT; i++) {
            nakedSingles.execute(matrix);
            openSingles.execute(matrix);
            run(matrix, HiddenPairs.class);
            matrix.validate();
        }
    }

    @Test
    public void validateHiddenTriples() throws IOException {
        for (int i = 0; i < COUNT; i++) {
            nakedSingles.execute(matrix);
            openSingles.execute(matrix);
            run(matrix, HiddenTriples.class);
            matrix.validate();
        }
    }

    @Test
    public void validateHiddenQuads() throws IOException {
        for (int i = 0; i < COUNT; i++) {
            nakedSingles.execute(matrix);
            openSingles.execute(matrix);
            run(matrix, HiddenQuads.class);
            matrix.validate();
        }
    }

    @Test
    public void validateNakedTriples() throws IOException {
        for (int i = 0; i < COUNT; i++) {
            nakedSingles.execute(matrix);
            openSingles.execute(matrix);
            run(matrix, NakedTriplets.class);
            matrix.validate();
        }
    }

    @Test
    public void validateNakedQuads() throws IOException {
        for (int i = 0; i < COUNT; i++) {
            nakedSingles.execute(matrix);
            openSingles.execute(matrix);
            run(matrix, NakedQuads.class);
            matrix.validate();
        }
    }

    @Test
    public void validateHiddenSingles() throws IOException {
        for (int i = 0; i < COUNT; i++) {
            nakedSingles.execute(matrix);
            openSingles.execute(matrix);
            run(matrix, HiddenSingles.class);
            matrix.validate();
        }
    }

    @Test
    public void validateNakedPairs() throws IOException {
        for (int i = 0; i < COUNT; i++) {
            nakedSingles.execute(matrix);
            openSingles.execute(matrix);
            run(matrix, NakedPairs.class);
            matrix.validate();
        }
    }

    @Test
    public void validateReduction() throws IOException {
        for (int i = 0; i < COUNT; i++) {
            nakedSingles.execute(matrix);
            openSingles.execute(matrix);
            run(matrix, Reduction.class);
            matrix.validate();
        }
    }

    @Test
    public void validateSwordFish() throws IOException {
        for (int i = 0; i < COUNT; i++) {
            nakedSingles.execute(matrix);
            openSingles.execute(matrix);
            run(matrix, SwordFish.class);
            matrix.validate();
        }
    }

    @Test
    public void forcingChain() throws IOException {
        for (int i = 0; i < COUNT; i++) {
            nakedSingles.execute(matrix);
            openSingles.execute(matrix);
            run(matrix, ForcingChains.class);
            matrix.validate();
        }
    }
}
