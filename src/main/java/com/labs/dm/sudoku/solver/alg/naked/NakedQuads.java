package com.labs.dm.sudoku.solver.alg.naked;

/**
 * Implements Naked Quads algorithm
 *
 * @see <a href="http://www.learn-sudoku.com/naked-quads.html">http://www.learn-sudoku.com/naked-quads.html</a>
 * <p/>
 * Created by Daniel Mroczka on 2016-02-19.
 */
public class NakedQuads extends NakedSubset {

    public NakedQuads() {
        subsetSize = 4;
    }
}
