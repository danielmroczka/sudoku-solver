package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.utils.CounterHashMap;

import java.util.Collection;

/**
 * Implements Naked Triplets algortihm
 * http://www.learn-sudoku.com/naked-triplets.html
 * <p/>
 * Created by daniel on 2016-02-19.
 */
public class NakedTriplets extends NakedPairs {

    public NakedTriplets() {
        SIZE = 3;
    }

    protected void count(IMatrix matrix, CounterHashMap<Collection<Integer>> map, int row, int col) {
        Collection<Integer> key = matrix.getCandidates(row, col);
        if (accept(key.size())) {
            map.inc(key);
        }
    }
}
