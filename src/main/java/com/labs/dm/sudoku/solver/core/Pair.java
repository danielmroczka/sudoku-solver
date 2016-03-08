package com.labs.dm.sudoku.solver.core;

import java.util.Comparator;

/**
 * Created by daniel on 2016-02-15.
 */
public class Pair {
    private final int row;
    private final int col;

    public Pair(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair pair = (Pair) o;

        if (row != pair.row) return false;
        return col == pair.col;

    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + col;
        return result;
    }

    @Override
    public String toString() {
        return "Pair{" + row + "," + col + "}";
    }

    public static class ComaparatorRows implements Comparator<Pair> {

        @Override
        public int compare(Pair o1, Pair o2) {
            if (o1 == null || o2 == null) {
                return 0;
            }

            return o1.getRow() != o2.getRow() ? o1.getRow() - o2.getRow() : o1.getCol() - o2.getCol();
        }
    }
}
