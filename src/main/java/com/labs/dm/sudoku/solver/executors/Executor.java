package com.labs.dm.sudoku.solver.executors;

import com.labs.dm.sudoku.solver.alg.IAlgorithm;
import com.labs.dm.sudoku.solver.core.IMatrix;

import java.util.logging.Logger;

/**
 * Created by daniel on 2016-03-09.
 */
public class Executor {

    private final Logger logger = Logger.getLogger("Executor");
    private final IAlgorithm algorithm;
    private final IMatrix matrix;

    public Executor(IMatrix matrix, IAlgorithm algorithm) {
        this.algorithm = algorithm;
        this.matrix = matrix;
    }

    public Executor(IMatrix matrix, Class<? extends IAlgorithm> clazz) {
        IAlgorithm algorithm = null;
        try {
            algorithm = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        this.algorithm = algorithm;
        this.matrix = matrix;
    }

    public void run() {
        long time = System.nanoTime();
        int solved = matrix.getSolvedItems();
        int candidates = matrix.getCandidatesCount();
        algorithm.execute(matrix);
        solved = matrix.getSolvedItems() - solved;
        candidates = candidates - matrix.getCandidatesCount();
        time = System.nanoTime() - time;
        logger.info("Executed " + algorithm.getClass().getSimpleName() + " in " + time / 1000000d + " [ms]. Solved=" + solved + ", reduced candidates=" + candidates);
    }
}
