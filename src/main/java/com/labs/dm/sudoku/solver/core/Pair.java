package com.labs.dm.sudoku.solver.core;

import java.util.Comparator;

/**
 * Created by Daniel Mroczka on 2016-02-15.
 */
public record Pair(int row, int col) {

    @Override
    public String toString() {
        return "Pair{" + row + "," + col + "}";
    }

    public static class ComparatorRow implements Comparator<Pair> {

        @Override
        public int compare(Pair o1, Pair o2) {
            if (o1 == null || o2 == null) {
                return 0;
            }

            return Comparator.comparingInt(Pair::row)
                    .thenComparingInt(Pair::col)
                    .compare(o1, o2);
        }
    }
}
