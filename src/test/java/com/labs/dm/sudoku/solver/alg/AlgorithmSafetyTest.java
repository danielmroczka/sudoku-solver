package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.alg.chains.ForcingChains;
import com.labs.dm.sudoku.solver.alg.chains.TwoStringKite;
import com.labs.dm.sudoku.solver.alg.chains.XYChains;
import com.labs.dm.sudoku.solver.alg.fish.*;
import com.labs.dm.sudoku.solver.alg.hidden.*;
import com.labs.dm.sudoku.solver.alg.naked.*;
import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import com.labs.dm.sudoku.solver.io.MatrixLoader;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Parametrized safety tests that run every non-disabled IAlgorithm implementation
 * against a set of real puzzles and assert that no duplicates are introduced
 * in any row, column or 3x3 block after the algorithm executes.
 */
class AlgorithmSafetyTest {

    /**
     * All puzzle files used as test input.
     */
    private static final String[] PUZZLES = {
            "patterns/hard/hard.txt",
            "patterns/hard/hard3.txt",
            "patterns/hard/hard4.txt",
            "patterns/hard/hard5.txt",
            "patterns/hard/hard6.txt",
            "patterns/hard/hard7.txt",
            "patterns/real.txt",
            "patterns/easy/001.txt",
            "patterns/easy/002.txt",
    };

    /**
     * All algorithm classes to test (Backtracking is @Disabled — excluded intentionally).
     */
    @SuppressWarnings("unchecked")
    private static final Class<? extends IAlgorithm>[] ALGORITHMS = new Class[]{
            GenerateCandidates.class,
            OpenSingles.class,
            NakedSingles.class,
            NakedPairs.class,
            NakedTriplets.class,
            NakedQuads.class,
            NakedQuints.class,
            HiddenSingles.class,
            HiddenPairs.class,
            HiddenTriples.class,
            HiddenQuads.class,
            HiddenQuints.class,
            LockedCandidates.class,
            XWing.class,
            XYWing.class,
            XYZWing.class,
            SwordFish.class,
            JellyFish.class,
            TwoStringKite.class,
            RemotePairs.class,
            Colouring.class,
            ForcingChains.class,
            XYChains.class,
    };

    // -----------------------------------------------------------------------
    // Test arguments: cartesian product of (algorithm, puzzle)
    // -----------------------------------------------------------------------

    static Stream<Object[]> algorithmAndPuzzleProvider() {
        List<Object[]> cases = new ArrayList<>();
        for (Class<? extends IAlgorithm> alg : ALGORITHMS) {
            for (String puzzle : PUZZLES) {
                cases.add(new Object[]{alg.getSimpleName(), alg, puzzle});
            }
        }
        return cases.stream();
    }

    // -----------------------------------------------------------------------
    // Main parametrized test
    // -----------------------------------------------------------------------

    @ParameterizedTest(name = "{0}  →  {2}")
    @MethodSource("algorithmAndPuzzleProvider")
    void algorithmShouldNotIntroduceDuplicates(
            String algorithmName,
            Class<? extends IAlgorithm> algorithmClass,
            String puzzleFile) throws Exception {

        IMatrix matrix = loadWithCandidates(puzzleFile);

        IAlgorithm algorithm = algorithmClass.getDeclaredConstructor().newInstance();
        algorithm.execute(matrix);

        assertNoRowDuplicates(matrix, algorithmName, puzzleFile);
        assertNoColDuplicates(matrix, algorithmName, puzzleFile);
        assertNoBlockDuplicates(matrix, algorithmName, puzzleFile);
    }

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------

    private IMatrix loadWithCandidates(String puzzleFile) throws IOException {
        IMatrix matrix = new MatrixLoader().load(puzzleFile);
        new GenerateCandidates().execute(matrix);
        return matrix;
    }

    private void assertNoRowDuplicates(IMatrix matrix, String alg, String puzzle) {
        for (int row = 0; row < Matrix.SIZE; row++) {
            Set<Integer> seen = new HashSet<>();
            for (int col = 0; col < Matrix.SIZE; col++) {
                int v = matrix.getValueAt(row, col);
                if (v != 0 && !seen.add(v)) {
                    fail(String.format(
                            "[%s / %s] Duplicate value %d in ROW %d", alg, puzzle, v, row));
                }
            }
        }
    }

    private void assertNoColDuplicates(IMatrix matrix, String alg, String puzzle) {
        for (int col = 0; col < Matrix.SIZE; col++) {
            Set<Integer> seen = new HashSet<>();
            for (int row = 0; row < Matrix.SIZE; row++) {
                int v = matrix.getValueAt(row, col);
                if (v != 0 && !seen.add(v)) {
                    fail(String.format(
                            "[%s / %s] Duplicate value %d in COL %d", alg, puzzle, v, col));
                }
            }
        }
    }

    private void assertNoBlockDuplicates(IMatrix matrix, String alg, String puzzle) {
        for (int blockRow = 0; blockRow < Matrix.BLOCK_SIZE; blockRow++) {
            for (int blockCol = 0; blockCol < Matrix.BLOCK_SIZE; blockCol++) {
                Set<Integer> seen = new HashSet<>();
                for (int r = blockRow * Matrix.BLOCK_SIZE; r < (blockRow + 1) * Matrix.BLOCK_SIZE; r++) {
                    for (int c = blockCol * Matrix.BLOCK_SIZE; c < (blockCol + 1) * Matrix.BLOCK_SIZE; c++) {
                        int v = matrix.getValueAt(r, c);
                        if (v != 0 && !seen.add(v)) {
                            fail(String.format(
                                    "[%s / %s] Duplicate value %d in BLOCK (%d,%d)",
                                    alg, puzzle, v, blockRow, blockCol));
                        }
                    }
                }
            }
        }
    }
}

