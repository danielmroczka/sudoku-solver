/*
 * Copyright daniel.mroczka@gmail.com. All rights reserved. 
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;

/**
 * @author daniel
 */
public class Flow {

    public void execute(IMatrix matrix) {
        IAlgorithm showPossibles = new ShowPossibles();
        IAlgorithm openSingles = new OpenSingles();
        IAlgorithm loneSingles = new LoneSingles();
        int prevCount = matrix.getSetElems();
        int chance = 3;
        while (!matrix.isSolved()) {
            System.out.println("Matrix is valid=" + matrix.validate());
            openSingles.execute(matrix);
            showPossibles.execute(matrix);
            loneSingles.execute(matrix);
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
