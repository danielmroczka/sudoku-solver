/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.executors;

import com.labs.dm.sudoku.solver.alg.*;
import com.labs.dm.sudoku.solver.core.IMatrix;

import java.util.logging.Logger;

import static com.labs.dm.sudoku.solver.executors.Executor.run;

/**
 * @author Daniel Mroczka
 */
public class Flow {

    private Class<? extends IAlgorithm>[] all = new Class[]{LoneSingles.class};

    private final Logger logger = Logger.getLogger("Flow");

    public void execute(IMatrix matrix) {
        int prevCount = matrix.getSolvedItems();
        int prevCandidates = matrix.getCandidatesCount();
        int chance = 3;
        run(matrix, GenerateCandidates.class);
        logger.info("Candidates = " + matrix.getCandidatesCount());
        matrix.validate();
        logger.info("Set cells = " + matrix.getSolvedItems());
        int counter = 0;
        while (!matrix.isSolved()) {
            logger.info("Flow execution " + ++counter + " candidates:" + matrix.getCandidatesCount());
            run(matrix, LoneSingles.class);
            run(matrix, OpenSingles.class);
            run(matrix, NakedPairs.class);
            run(matrix, NakedTriplets.class);
            run(matrix, HiddenPairs.class);
            run(matrix, HiddenTriples.class);
            run(matrix, HiddenSingles.class);
            run(matrix, Reduction.class);
            run(matrix, XWing.class);
            run(matrix, XYWing.class);
            run(matrix, XYZWing.class);
            run(matrix, SwordFish.class);
            run(matrix, JellyFish.class);

            logger.info(matrix.printCandidates());
            matrix.validate();
            if (prevCount == matrix.getSolvedItems() && prevCandidates == matrix.getCandidatesCount()) {
                chance--;
                if (chance == 0) {
                    break;
                }
            } else {
                chance = 3;
            }
            prevCount = matrix.getSolvedItems();
            prevCandidates = matrix.getCandidatesCount();
        }

        matrix.validate();
        logger.info(matrix.toString());
    }

}
