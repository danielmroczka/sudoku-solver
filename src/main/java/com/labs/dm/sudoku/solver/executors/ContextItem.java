package com.labs.dm.sudoku.solver.executors;

import java.io.Serializable;

/**
 * Class contains information about one algorithm execution
 * <p>
 * Created by Daniel Mroczka on 10-Mar-16.
 *
 * @param name             The name of sudoku algorithm
 * @param solved           How many candidates have been resolved in one step
 * @param reducedCandidate How many candidates have been removed in one step
 * @param time             Time spent on execution in ms
 */
public record ContextItem(String name, int solved, int reducedCandidate, int time) implements Serializable {

    @Override
    public String toString() {
        return String.format("%n%s, %s, %s, %.3f[ms]", name, solved, reducedCandidate, time / 1000000f);
    }
}
