/*
 * Copyright daniel.mroczka@gmail.com. All rights reserved. 
 */
package com.labs.dm.sudoku.solver.utils;

/**
 * @author daniel
 */
public class MatrixUtils {

    public static class Pos {

        public Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int x;
        public int y;
    }

    public static Pos getPos(int index) {
        return new Pos(index % 9, index / 9);
    }
}
