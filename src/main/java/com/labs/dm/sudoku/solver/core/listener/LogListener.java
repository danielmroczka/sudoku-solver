package com.labs.dm.sudoku.solver.core.listener;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Created by Daniel Mroczka on 10-Mar-16.
 */
public class LogListener implements IMatrixListener, Serializable {
    private final Logger logger = Logger.getLogger("MatrixListener");

    @Override
    public void onChangeValue(int row, int col, int value) {
        logger.info("Set cell value " + value + " at: " + row + ", " + col);
    }

    @Override
    public void onResolved() {
        logger.info("Resolved!");
    }

    @Override
    public void onRemoveCandidate(int row, int col, int removedCandidate) {
        logger.info("Remove candid. " + removedCandidate + " at: " + row + ", " + col);
    }
}
