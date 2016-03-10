package com.labs.dm.sudoku.solver.core;

/**
 * Created by daniel on 10-Mar-16.
 */
public interface IMatrixListener {
    void onChangeValue(int row, int col, int value);

    void onResolved();

    void onRemoveCandidate(int row, int col, int removedCandidate);
}
