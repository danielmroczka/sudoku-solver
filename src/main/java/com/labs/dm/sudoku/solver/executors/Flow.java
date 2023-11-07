/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.executors;

import com.labs.dm.sudoku.solver.alg.GenerateCandidates;
import com.labs.dm.sudoku.solver.alg.IAlgorithm;
import com.labs.dm.sudoku.solver.alg.LockedCandidates;
import com.labs.dm.sudoku.solver.alg.OpenSingles;
import com.labs.dm.sudoku.solver.alg.chains.ForcingChains;
import com.labs.dm.sudoku.solver.alg.fish.*;
import com.labs.dm.sudoku.solver.alg.hidden.HiddenPairs;
import com.labs.dm.sudoku.solver.alg.hidden.HiddenQuads;
import com.labs.dm.sudoku.solver.alg.hidden.HiddenSingles;
import com.labs.dm.sudoku.solver.alg.hidden.HiddenTriples;
import com.labs.dm.sudoku.solver.alg.naked.NakedPairs;
import com.labs.dm.sudoku.solver.alg.naked.NakedQuads;
import com.labs.dm.sudoku.solver.alg.naked.NakedSingles;
import com.labs.dm.sudoku.solver.alg.naked.NakedTriplets;
import com.labs.dm.sudoku.solver.core.IMatrix;

import java.util.logging.Logger;

import static com.labs.dm.sudoku.solver.executors.Executor.run;

/**
 * @author Daniel Mroczka
 */
public class Flow {

    private final Class<? extends IAlgorithm>[] all = new Class[]{NakedSingles.class};

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
            run(matrix, NakedSingles.class);
            run(matrix, OpenSingles.class);
            run(matrix, NakedPairs.class);
            run(matrix, NakedTriplets.class);
            run(matrix, NakedQuads.class);
            run(matrix, HiddenPairs.class);
            run(matrix, HiddenTriples.class);
            run(matrix, HiddenQuads.class);
            run(matrix, HiddenSingles.class);
            run(matrix, LockedCandidates.class);
            run(matrix, XWing.class);
            run(matrix, XYWing.class);
            run(matrix, XYZWing.class);
            run(matrix, SwordFish.class);
            run(matrix, JellyFish.class);
            run(matrix, ForcingChains.class);

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
