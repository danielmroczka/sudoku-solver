package com.labs.dm.sudoku.solver.executors;

import com.labs.dm.sudoku.solver.alg.IAlgorithm;
import com.labs.dm.sudoku.solver.core.IMatrix;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by daniel on 2016-03-09.
 */
public class Executor {

    private final Logger logger = Logger.getLogger("Executor");
    private final Map<Class<? extends IAlgorithm>, IAlgorithm> map = new HashMap<>();
    private static Executor instance;

    private Executor() {
    }

    public synchronized static void run(IMatrix matrix, Class<? extends IAlgorithm> clazz) {
        if (instance == null) {
            instance = new Executor();
        }
        instance.execute(matrix, clazz);
    }

    private void execute(IMatrix matrix, Class<? extends IAlgorithm> clazz) {
        IAlgorithm algInstance = map.get(clazz);
        try {
            if (algInstance == null) {
                algInstance = clazz.newInstance();
                map.put(clazz, algInstance);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return;
        }
        long time = System.nanoTime();
        int solved = matrix.getSolvedItems();
        int candidates = matrix.getCandidatesCount();
        algInstance.execute(matrix);
        solved = matrix.getSolvedItems() - solved;
        candidates = candidates - matrix.getCandidatesCount();
        time = System.nanoTime() - time;
        logger.fine("Executed " + clazz.getSimpleName() + " in " + time / 1000000d + " [ms]. Solved=" + solved + ", reduced candidates=" + candidates);

        if (solved > 0 || candidates > 0) {
            matrix.getContext().add(new ContextItem(algInstance.getClass().getSimpleName(), solved, candidates, (int) time));
            execute(matrix, clazz);
        }
    }

}
