package com.labs.dm.sudoku.solver.core.listener;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Created by Daniel Mroczka on 10-Mar-16.
 */
public class LogListener implements IMatrixListener, Serializable {

    private static final Logger LOGGER = Logger.getLogger("MatrixListener");

    @Override
    public void onChangeValue(int row, int col, int value) {
        LOGGER.info(String.format("Set cell value %d at: %d, %d", value, row, col));
    }

    @Override
    public void onResolved() {
        LOGGER.info("Resolved!");
    }

    @Override
    public void onRemoveCandidate(int row, int col, int removedCandidate) {
        LOGGER.info(String.format("Remove candidate %d at: %d, %d", removedCandidate, row, col));
    }
}
