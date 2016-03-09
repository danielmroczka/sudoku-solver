/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;

import java.util.logging.Logger;

/**
 * @author daniel
 */
public class Flow {

    private Logger logger = Logger.getLogger("Flow");

    public void execute(IMatrix matrix) {
        int prevCount = matrix.getSolvedItems();
        int prevCandidates = matrix.getCandidatesCount();
        int chance = 3;
        new Executor(matrix, GenerateCandidates.class).run();
        logger.info("Candidates = " + matrix.getCandidatesCount());
        matrix.validate();
        logger.info("Set cells = " + matrix.getSolvedItems());
        int counter = 0;
        while (!matrix.isSolved()) {
            logger.info("Flow execution " + ++counter + " candidates:" + matrix.getCandidatesCount());
            new Executor(matrix, LoneSingles.class).run();
            new Executor(matrix, OpenSingles.class).run();
            new Executor(matrix, NakedPairs.class).run();
            new Executor(matrix, NakedTriplets.class).run();
            new Executor(matrix, HiddenPairs.class).run();
            new Executor(matrix, HiddenTriples.class).run();
            new Executor(matrix, HiddenSingles.class).run();
            new Executor(matrix, Reduction.class).run();
            new Executor(matrix, XWing.class).run();
            new Executor(matrix, XYWing.class).run();
            new Executor(matrix, XYZWing.class).run();
            new Executor(matrix, SwordFish.class).run();
            new Executor(matrix, JellyFish.class).run();

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
