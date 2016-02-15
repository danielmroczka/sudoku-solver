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
        IAlgorithm hiddenSingles = new HiddenSingles();
        IAlgorithm hiddenPairs = new HiddenPairs();
        IAlgorithm xWing = new XWing();

        int prevCount = matrix.getSolvedItems();

        int chance = 5;
        showPossibles.execute(matrix);
        System.out.println("Candidates = " + matrix.getCandidates());
        matrix.validate();
        System.out.println("Set cells = " + matrix.getSolvedItems());
        // System.out.println(matrix);
        // System.out.println(matrix.printCandidates());
        while (!matrix.isSolved()) {
            loneSingles.execute(matrix);
            openSingles.execute(matrix);
            nakedPairs.execute(matrix);

            loneSingles.execute(matrix);
            openSingles.execute(matrix);

            hiddenPairs.execute(matrix);

            loneSingles.execute(matrix);
            openSingles.execute(matrix);

            hiddenSingles.execute(matrix);

            loneSingles.execute(matrix);
            openSingles.execute(matrix);

            //xWing.execute(matrix);

            System.out.println("Candidates = " + matrix.getCandidates());
            matrix.validate();
            System.out.println("Set cells = " + matrix.getSolvedItems());

            if (prevCount == matrix.getSolvedItems()) {
                chance--;
                if (chance == 0) {
                    break;
                }
            } else {
                chance = 5;
            }
            prevCount = matrix.getSolvedItems();
            //chance = 3;
        }

        System.out.println(matrix);
        System.out.println(matrix.printCandidates());
        matrix.validate();
    }

}
