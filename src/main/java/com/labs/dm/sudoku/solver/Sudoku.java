/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver;

/**
 * @author daniel
 */
public class Sudoku {

    /**
     * Print the specified Sudoku problem and its solution. The problem is
     * encoded as specified in the class documentation above.
     *
     * @param args The command-line arguments encoding the problem.
     */
    public static void main(String[] args) {
        int[][] matrix = {
                {0, 0, 0, 0, 0, 2, 0, 0, 3},
                {0, 0, 0, 7, 0, 0, 9, 0, 0},
                {9, 0, 0, 0, 0, 6, 0, 4, 0},
                {0, 0, 0, 0, 1, 0, 8, 0, 4},
                {0, 0, 6, 0, 0, 0, 5, 0, 0},
                {7, 0, 9, 0, 8, 0, 0, 0, 0},
                {0, 3, 0, 4, 0, 0, 0, 0, 6},
                {0, 0, 2, 0, 0, 7, 0, 0, 0},
                {6, 0, 0, 3, 0, 0, 0, 0, 0}
        };//parseProblem(args);
        printMatrix(matrix);
        if (solve(0, 0, matrix)) // solves in place
        {
            printMatrix(matrix);
        } else {
            System.out.println("NONE");
        }
    }

    static boolean solve(int i, int j, int[][] cells) {
        if (i == 9) {
            i = 0;
            if (++j == 9) {
                return true;
            }
        }
        if (cells[i][j] != 0) // skip filled cells
        {
            return solve(i + 1, j, cells);
        }

        for (int val = 1; val <= 9; ++val) {
            if (accept(i, j, val, cells)) {
                cells[i][j] = val;
                if (solve(i + 1, j, cells)) {
                    return true;
                }
            }
        }
        cells[i][j] = 0; // reset on backtrack
        return false;
    }

    static boolean accept(int i, int j, int val, int[][] cells) {
        for (int k = 0; k < 9; ++k) // row
        {
            if (val == cells[k][j]) {
                return false;
            }
        }

        for (int k = 0; k < 9; ++k) // col
        {
            if (val == cells[i][k]) {
                return false;
            }
        }

        int boxRowOffset = (i / 3) * 3;
        int boxColOffset = (j / 3) * 3;
        for (int k = 0; k < 3; ++k) // box
        {
            for (int m = 0; m < 3; ++m) {
                if (val == cells[boxRowOffset + k][boxColOffset + m]) {
                    return false;
                }
            }
        }

        return true; // no violations, so it's legal
    }

    static void printMatrix(int[][] solution) {
        for (int i = 0; i < 9; ++i) {
            if (i % 3 == 0) {
                System.out.println(" -----------------------");
            }
            for (int j = 0; j < 9; ++j) {
                if (j % 3 == 0) {
                    System.out.print("| ");
                }
                System.out.print(solution[i][j] == 0
                        ? " "
                        : Integer.toString(solution[i][j]));

                System.out.print(' ');
            }
            System.out.println("|");
        }
        System.out.println(" -----------------------");
    }

}
