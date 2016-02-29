/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.io.MatrixLoader;

import java.io.IOException;
import java.util.Date;

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

        int prevCount = matrix.getSolvedItems();

        int chance = 5;
        showPossibles.execute(matrix);
        System.out.println("Candidates = " + matrix.getCandidatesCount());
        matrix.validate();
        System.out.println("Set cells = " + matrix.getSolvedItems());
        while (!matrix.isSolved()) {
            System.out.println("flow execution " + matrix.getCandidatesCount());
            loneSingles.execute(matrix);
            //log(matrix);
            openSingles.execute(matrix);
            nakedPairs.execute(matrix);
            nakedTriples.execute(matrix);
            hiddenPairs.execute(matrix);
            hiddenTriples.execute(matrix);
            //log(matrix);
            hiddenSingles.execute(matrix);
            reduction.execute(matrix);
            xWing.execute(matrix);
            xyWing.execute(matrix);
            xyzWing.execute(matrix);
            System.out.println(matrix.printCandidates());
            matrix.validate();
            if (prevCount == matrix.getSolvedItems()) {
                chance--;
                if (chance == 0) {
                    break;
                }
            } else {
                chance = 5;
            }
            prevCount = matrix.getSolvedItems();
        }

        System.out.println("Candidates = " + matrix.getCandidatesCount());
        matrix.validate();
        System.out.println("Set cells = " + matrix.getSolvedItems());
        System.out.println(matrix);
    }

    private void log(IMatrix matrix) {
        try {
            new MatrixLoader().save(matrix, "target\\matrix_" + new Date().getTime());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
