package com.labs.dm.sudoku.solver;

import com.labs.dm.sudoku.solver.alg.Flow;
import com.labs.dm.sudoku.solver.alg.IAlgorithm;
import com.labs.dm.sudoku.solver.alg.NakedPairs;
import com.labs.dm.sudoku.solver.alg.ShowPossibles;
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
        IAlgorithm alg = new NakedPairs();
        IAlgorithm cand = new ShowPossibles();
        //flow.execute(matrix);
        cand.execute(matrix);
        System.out.println(matrix.toString());
        alg.execute(matrix);
        alg.execute(matrix);
        alg.execute(matrix);
        alg.execute(matrix);
        System.out.println(matrix.toString());
    }
}
