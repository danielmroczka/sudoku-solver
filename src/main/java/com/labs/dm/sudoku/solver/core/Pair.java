package com.labs.dm.sudoku.solver.core;

/**
 * Created by daniel on 2016-02-15.
 */
public class Pair {
    private final int col;
    private final int row;

    public Pair(int row, int col) {
        this.col = col;
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}
