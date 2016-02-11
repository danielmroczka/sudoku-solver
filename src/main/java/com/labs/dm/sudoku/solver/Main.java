package com.labs.dm.sudoku.solver;

import com.labs.dm.sudoku.solver.alg.Flow;
import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.io.MatrixLoader;

import java.io.IOException;

/**
 * Created by daniel on 2016-02-10.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        MatrixLoader loader = new MatrixLoader();
        IMatrix matrix = loader.load("src/test/resources/patterns/hard2.txt");
        Flow flow = new Flow();
        flow.execute(matrix);

        System.out.println(matrix.toString());
    }
}
