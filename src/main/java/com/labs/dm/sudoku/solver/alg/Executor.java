package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;

/**
 * Created by daniel on 2016-03-09.
 */
public class Executor {

    private final IAlgorithm algorithm;
    private final IMatrix matrix;

    public Executor(IMatrix matrix, IAlgorithm algorithm) {
        this.algorithm = algorithm;
        this.matrix = matrix;
    }

    public void run() {
        long time = System.nanoTime();
        algorithm.execute(matrix);
        time = System.nanoTime() - time;

    }
}
