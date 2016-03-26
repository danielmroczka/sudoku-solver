package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import com.labs.dm.sudoku.solver.io.MatrixLoader;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Daniel Mroczka on 15-Mar-16.
 */
public class HiddenQuadsTest {
    private final HiddenQuads hiddenQuads = new HiddenQuads();

    @Test
    public void shouldFoundQuadsInRow() {
        //Should find hidden quad: 1, 6, 7, 8
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{1, 2, 3, 6, 7});
        matrix.addCandidates(0, 1, new Integer[]{2, 4, 5, 6, 7});
        matrix.addCandidates(0, 2, new Integer[]{1, 4, 5, 6, 7, 8});
        matrix.addCandidates(0, 3, new Integer[]{5, 9});
        matrix.addCandidates(0, 4, new Integer[]{4, 2});
        matrix.addCandidates(0, 5, new Integer[]{7, 8});
        matrix.addCandidates(0, 6, new Integer[]{4, 3, 9});
        matrix.addCandidates(0, 7, new Integer[]{4, 2, 5});
        matrix.addCandidates(0, 8, new Integer[]{2, 3, 4, 5, 9});
        //WHEN
        hiddenQuads.execute(matrix);
        //THEN
        assertEquals(3, matrix.getCandidates(0, 0).size());
        assertEquals(2, matrix.getCandidates(0, 1).size());
        assertEquals(4, matrix.getCandidates(0, 2).size());
        assertEquals(2, matrix.getCandidates(0, 5).size());
    }

    @Test
    public void shouldFoundQuadsInRow2() {
        //Should find hidden quad: 1, 2, 6, 7
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{3, 5, 8});
        matrix.addCandidates(0, 1, new Integer[]{2, 5, 6, 7, 8});
        matrix.addCandidates(0, 2, new Integer[]{2, 3, 6, 8});
        matrix.addCandidates(0, 3, new Integer[]{1, 2, 3, 8});
        matrix.addCandidates(0, 4, new Integer[]{1, 2, 3, 6});
        matrix.addCandidates(0, 5, new Integer[]{2, 3, 5, 6});
        matrix.addCandidates(0, 6, new Integer[]{2, 6, 7});
        //WHEN
        int count = matrix.getCandidatesCount();
        hiddenQuads.execute(matrix);
        //THEN
        assertEquals(count, matrix.getCandidatesCount());
    }

    @Test
    public void shouldFoundQuadsInCol() {
        //Should find hidden quad: 1, 2, 8, 9
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{5, 8, 9});
        matrix.addCandidates(1, 0, new Integer[]{1, 4, 5, 8});
        matrix.addCandidates(2, 0, new Integer[]{4, 5});
        matrix.addCandidates(3, 0, new Integer[]{1, 2});
        matrix.addCandidates(4, 0, new Integer[]{2, 5, 9});
        matrix.addCandidates(5, 0, new Integer[]{5, 6, 7});
        matrix.addCandidates(6, 0, new Integer[]{4, 5, 7});
        matrix.addCandidates(7, 0, new Integer[]{3, 4, 5, 6});
        matrix.addCandidates(8, 0, new Integer[]{3, 4, 5, 7});
        //WHEN
        hiddenQuads.execute(matrix);
        //THEN
        assertEquals(2, matrix.getCandidates(0, 0).size());
        assertEquals(2, matrix.getCandidates(1, 0).size());
        assertEquals(2, matrix.getCandidates(2, 0).size());
        assertEquals(2, matrix.getCandidates(3, 0).size());
        assertEquals(2, matrix.getCandidates(4, 0).size());
    }

    @Test
    public void shouldFoundQuadsInBlock() {
        //Should find hidden quad: 1,4,6,9
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{1, 3, 4, 6, 7, 8, 9});
        matrix.addCandidates(0, 1, new Integer[]{3, 7, 8});
        matrix.addCandidates(0, 2, new Integer[]{3, 4, 6, 7, 8, 9});
        matrix.addCandidates(1, 0, new Integer[]{2, 3, 7, 8});
        matrix.addCandidates(1, 1, new Integer[]{2, 3, 5, 7, 8});
        matrix.addCandidates(1, 2, new Integer[]{2, 3, 5, 7, 8});
        matrix.addCandidates(2, 0, new Integer[]{1, 3, 4, 7, 8, 9});
        matrix.addCandidates(2, 1, new Integer[]{3, 5, 7, 8});
        matrix.addCandidates(2, 2, new Integer[]{3, 4, 5, 7, 8, 9});
        //WHEN
        int count = matrix.getCandidatesCount();
        hiddenQuads.execute(matrix);
        //THEN
        assertEquals(count - 13, matrix.getCandidatesCount());
        assertEquals(4, matrix.getCandidates(0, 0).size());
        assertEquals(3, matrix.getCandidates(0, 2).size());
        assertEquals(3, matrix.getCandidates(2, 0).size());
        assertEquals(2, matrix.getCandidates(2, 2).size());
    }

    @Test
    public void shouldFoundQuadsInBlock2() {
        //Should find hidden quad: 1,4,6,9
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.addCandidates(0, 0, new Integer[]{2, 3, 6, 8});
        matrix.addCandidates(0, 1, new Integer[]{1, 2, 3, 8});
        matrix.addCandidates(0, 2, new Integer[]{1, 2, 6});
        matrix.addCandidates(1, 0, new Integer[]{2, 3, 4, 6, 8, 9});
        matrix.addCandidates(1, 1, new Integer[]{1, 2});
        matrix.addCandidates(1, 2, new Integer[]{1, 2, 3, 4, 6});
        matrix.addCandidates(2, 1, new Integer[]{1, 2, 9});
        //WHEN
        int count = matrix.getCandidatesCount();
        hiddenQuads.execute(matrix);
        //THEN
        assertEquals(count - 0, matrix.getCandidatesCount());
    }

    @Test
    @Ignore
    public void shouldFoundPairFromFile() throws IOException {
        MatrixLoader loader = new MatrixLoader();
        IMatrix matrix = loader.load("src/test/resources/patterns/hard/hard6.txt");
        IAlgorithm cand = new GenerateCandidates();
        cand.execute(matrix);

        cand.execute(matrix);
        int count = matrix.getCandidatesCount();
        hiddenQuads.execute(matrix);

        assertEquals(count - 13, matrix.getCandidatesCount());
    }
}
