/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;

/**
 * @author Daniel Mroczka
 */
public interface IAlgorithm {

    void execute(IMatrix matrix);
}
