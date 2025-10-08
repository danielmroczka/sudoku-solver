/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;

/**
 * @author Daniel Mroczka
 */
public interface IAlgorithm {

    /**
     * Executes the algorithm on the given Sudoku matrix.
     *
     * @param matrix The Sudoku matrix.
     */
    void execute(IMatrix matrix);
}
