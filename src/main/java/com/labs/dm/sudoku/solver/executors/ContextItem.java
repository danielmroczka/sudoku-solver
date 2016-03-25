package com.labs.dm.sudoku.solver.executors;

import java.io.Serializable;

/**
 * Class contains information about one algorithm execution
 * <p>
 * Created by Daniel Mroczka on 10-Mar-16.
 */
public class ContextItem implements Serializable {
    /**
     * The name of sudoku algorithm
     */
    private final String name;
    /**
     * How many candidates have been resolved in one step
     */
    private final int solved;
    /**
     * How many candidates have been removed in one step
     */
    private final int reducedCandidate;
    /**
     * Time spent on execution in ms
     */
    private final int time;

    public ContextItem(String name, int solved, int reducedCandidate, int time) {
        this.name = name;
        this.solved = solved;
        this.reducedCandidate = reducedCandidate;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public int getSolved() {
        return solved;
    }

    public int getReducedCandidate() {
        return reducedCandidate;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        return String.format("%n%s, %s, %s, %.3f[ms]", name, solved, reducedCandidate, time / 1000000f);
    }
}
