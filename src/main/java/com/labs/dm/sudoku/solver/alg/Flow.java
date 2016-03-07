/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;

/**
 * @author daniel
 */
public class Flow {

    public void execute(IMatrix matrix) {
        IAlgorithm showPossibles = new GenerateCandidates();
        IAlgorithm openSingles = new OpenSingles();
        IAlgorithm loneSingles = new LoneSingles();
        IAlgorithm nakedPairs = new NakedPairs();
        IAlgorithm hiddenTriples = new HiddenTriples();
        IAlgorithm hiddenSingles = new HiddenSingles();
        IAlgorithm hiddenPairs = new HiddenPairs();
        IAlgorithm nakedTriples = new NakedTriplets();
        IAlgorithm xWing = new XWing();
        IAlgorithm xyWing = new XYWing();
        IAlgorithm xyzWing = new XYZWing();
        IAlgorithm reduction = new Reduction();
        IAlgorithm swordfish = new Swordfish();

        int prevCount = matrix.getSolvedItems();
        int prevCandidates = matrix.getCandidatesCount();

        int chance = 3;
        showPossibles.execute(matrix);
        System.out.println("Candidates = " + matrix.getCandidatesCount());
        matrix.validate();
        System.out.println("Set cells = " + matrix.getSolvedItems());
        while (!matrix.isSolved()) {
            System.out.println("flow execution " + matrix.getCandidatesCount());
            loneSingles.execute(matrix);
            openSingles.execute(matrix);
            nakedPairs.execute(matrix);
            nakedTriples.execute(matrix);
            hiddenPairs.execute(matrix);
            hiddenTriples.execute(matrix);
            hiddenSingles.execute(matrix);
            reduction.execute(matrix);
            xWing.execute(matrix);
            xyWing.execute(matrix);
            xyzWing.execute(matrix);
            swordfish.execute(matrix);
            System.out.println(matrix.printCandidates());
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
        System.out.println(matrix);
    }

}
