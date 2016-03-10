package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.utils.CounterHashMap;

/**
 * Jellyfish algorithm
 * <p>
 * http://hodoku.sourceforge.net/en/tech_fishb.php#bf3
 * http://www.sudokuwiki.org/Jelly_Fish_Strategy
 * <p>
 * Created by daniel on 2016-03-03.
 */
public class JellyFish extends SwordFish {
    public JellyFish() {
        SIZE = 4;
        MIN_POINTS = 8; //?
    }

    protected boolean accept(CounterHashMap<Integer> rowsMap, CounterHashMap<Integer> colsMap) {
        boolean accept = true;
        for (int val : rowsMap.values()) {
            if (val < 2) accept = false;
        }
        for (int val : colsMap.values()) {
            if (val < 2) accept = false;
        }
        return accept && rowsMap.size() == SIZE && colsMap.size() == SIZE;
    }

}
