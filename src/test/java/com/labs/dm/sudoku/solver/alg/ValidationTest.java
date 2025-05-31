package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.alg.chains.ForcingChains;
import com.labs.dm.sudoku.solver.alg.fish.SwordFish;
import com.labs.dm.sudoku.solver.alg.fish.XWing;
import com.labs.dm.sudoku.solver.alg.fish.XYWing;
import com.labs.dm.sudoku.solver.alg.fish.XYZWing;
import com.labs.dm.sudoku.solver.alg.hidden.HiddenPairs;
import com.labs.dm.sudoku.solver.alg.hidden.HiddenQuads;
import com.labs.dm.sudoku.solver.alg.hidden.HiddenSingles;
import com.labs.dm.sudoku.solver.alg.hidden.HiddenTriples;
import com.labs.dm.sudoku.solver.alg.naked.NakedPairs;
import com.labs.dm.sudoku.solver.alg.naked.NakedQuads;
import com.labs.dm.sudoku.solver.alg.naked.NakedSingles;
import com.labs.dm.sudoku.solver.alg.naked.NakedTriplets;
import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.io.MatrixLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.labs.dm.sudoku.solver.executors.Executor.run;

/**
 * Created by Daniel Mroczka on 2016-02-17.
 */
public class ValidationTest {

    private final NakedSingles nakedSingles = new NakedSingles();
    private final OpenSingles openSingles = new OpenSingles();
    private final IAlgorithm cand = new GenerateCandidates();
    private final int COUNT = 50;
    private IMatrix matrix;

    @BeforeEach
    public void before() throws IOException {
        matrix = new MatrixLoader().load("patterns/hard/hard.txt");
        cand.execute(matrix);
        matrix.validate();
    }

    @Test
    public void validateXWing() {
        for (int i = 0; i < COUNT; i++) {
            nakedSingles.execute(matrix);
            openSingles.execute(matrix);
            run(matrix, XWing.class);
            matrix.validate();
        }
    }

    @Test
    public void validateXYWing() {
        for (int i = 0; i < COUNT; i++) {
            nakedSingles.execute(matrix);
            openSingles.execute(matrix);
            run(matrix, XYWing.class);
            matrix.validate();
        }
    }

    @Test
    public void validateXYZWing() {
        for (int i = 0; i < COUNT; i++) {
            nakedSingles.execute(matrix);
            openSingles.execute(matrix);
            run(matrix, XYZWing.class);
            matrix.validate();
        }
    }

    @Test
    public void validateHiddenPairs() {
        for (int i = 0; i < COUNT; i++) {
            nakedSingles.execute(matrix);
            openSingles.execute(matrix);
            run(matrix, HiddenPairs.class);
            matrix.validate();
        }
    }

    @Test
    public void validateHiddenTriples() {
        for (int i = 0; i < COUNT; i++) {
            nakedSingles.execute(matrix);
            openSingles.execute(matrix);
            run(matrix, HiddenTriples.class);
            matrix.validate();
        }
    }

    @Test
    public void validateHiddenQuads() {
        for (int i = 0; i < COUNT; i++) {
            nakedSingles.execute(matrix);
            openSingles.execute(matrix);
            run(matrix, HiddenQuads.class);
            matrix.validate();
        }
    }

    @Test
    public void validateNakedTriples() {
        for (int i = 0; i < COUNT; i++) {
            nakedSingles.execute(matrix);
            openSingles.execute(matrix);
            run(matrix, NakedTriplets.class);
            matrix.validate();
        }
    }

    @Test
    public void validateNakedQuads() {
        for (int i = 0; i < COUNT; i++) {
            nakedSingles.execute(matrix);
            openSingles.execute(matrix);
            run(matrix, NakedQuads.class);
            matrix.validate();
        }
    }

    @Test
    public void validateHiddenSingles() {
        for (int i = 0; i < COUNT; i++) {
            nakedSingles.execute(matrix);
            openSingles.execute(matrix);
            run(matrix, HiddenSingles.class);
            matrix.validate();
        }
    }

    @Test
    public void validateNakedPairs() {
        for (int i = 0; i < COUNT; i++) {
            nakedSingles.execute(matrix);
            openSingles.execute(matrix);
            run(matrix, NakedPairs.class);
            matrix.validate();
        }
    }

    @Test
    public void validateReduction() {
        for (int i = 0; i < COUNT; i++) {
            nakedSingles.execute(matrix);
            openSingles.execute(matrix);
            run(matrix, LockedCandidates.class);
            matrix.validate();
        }
    }

    @Test
    public void validateSwordFish() {
        for (int i = 0; i < COUNT; i++) {
            nakedSingles.execute(matrix);
            openSingles.execute(matrix);
            run(matrix, SwordFish.class);
            matrix.validate();
        }
    }

    @Test
    public void forcingChain() {
        for (int i = 0; i < COUNT; i++) {
            nakedSingles.execute(matrix);
            openSingles.execute(matrix);
            run(matrix, ForcingChains.class);
            matrix.validate();
        }
    }
}

