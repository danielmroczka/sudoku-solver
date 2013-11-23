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
        while (!matrix.isSolved()) {
           openSingles.execute(matrix);
           showPossibles.execute(matrix);
           loneSingles.execute(matrix);
           if (prevCount == matrix.getSetElems()) {
               break;
           } else {
               prevCount = matrix.getSetElems();
           }
        }
    }
    
}
