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

        int prevCount = matrix.getSetElems();
        int chance = 3;
        showPossibles.execute(matrix);
        while (!matrix.isSolved()) {
            System.out.println("Matrix is valid=" + matrix.validate());
            System.out.println("Matrix elems count = " + matrix.getSetElems());
            openSingles.execute(matrix);
            System.out.println("Matrix is valid=" + matrix.validate());
            System.out.println("Matrix elems count = " + matrix.getSetElems());
            //showPossibles.execute(matrix);
            loneSingles.execute(matrix);
            System.out.println("Matrix is valid=" + matrix.validate());
            System.out.println("Matrix elems count = " + matrix.getSetElems());
            //showPossibles.execute(matrix);
            nakedPairs.execute(matrix);
            System.out.println("Matrix is valid=" + matrix.validate());
            System.out.println("Matrix elems count = " + matrix.getSetElems());
            //showPossibles.execute(matrix);
            //hiddenSingles.execute(matrix);
            System.out.println("Matrix is valid=" + matrix.validate());
            System.out.println("Matrix elems count = " + matrix.getSetElems());
            //showPossibles.execute(matrix);
            hiddenPairs.execute(matrix);
            System.out.println("Matrix is valid=" + matrix.validate());

            System.out.println("Flow=" + prevCount);

            if (prevCount == matrix.getSetElems()) {
                chance--;
                if (chance == 0) {
                    break;
                }
            }
            prevCount = matrix.getSetElems();
            //chance = 3;
        }
    }

}
