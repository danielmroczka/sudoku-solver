package com.labs.dm.sudoku.solver.core;

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
    public String toString() {
        return "Pair{row=" + row + ", col=" + col + "}";
    }
}
