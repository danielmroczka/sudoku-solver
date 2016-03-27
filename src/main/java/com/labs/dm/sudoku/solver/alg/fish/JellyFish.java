package com.labs.dm.sudoku.solver.alg.fish;

/**
 * Jellyfish algorithm
 * <p>
 * http://hodoku.sourceforge.net/en/tech_fishb.php#bf3
 * http://www.sudokuwiki.org/Jelly_Fish_Strategy
 * <p>
 * Created by Daniel Mroczka on 2016-03-03.
 */
public class JellyFish extends SwordFish {

    public JellyFish() {
        SIZE = 4;
        MIN_POINTS = 8;
    }

}
