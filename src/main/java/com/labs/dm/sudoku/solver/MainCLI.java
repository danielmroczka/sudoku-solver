/*
 * Copyright daniel.mroczka@gmail.com. All rights reserved. 
 */
package com.labs.dm.sudoku.solver;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.io.MatrixLoader;
import java.io.IOException;

/**
 * @author daniel
 */
public class MainCLI {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Input file name is missing!");
        }
        MatrixLoader loader = new MatrixLoader();
        IMatrix matrix = loader.loadTable(args[0]);

        System.out.println("Loaded matrix: " + matrix);
    }
}
