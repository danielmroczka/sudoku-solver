/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.executors.Flow;
import com.labs.dm.sudoku.solver.io.MatrixLoader;

import java.io.IOException;
import java.util.logging.LogManager;

/**
 * @author Daniel Mroczka
 */
public class MainCLI {

    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        LogManager.getLogManager().readConfiguration(classLoader.getResourceAsStream("log.properties"));
        if (args.length != 1) {
            throw new IllegalArgumentException("Input file name is missing!");
        }
        MatrixLoader loader = new MatrixLoader();
        IMatrix matrix = loader.load(args[0]);
        long start = System.currentTimeMillis();
        Flow flow = new Flow();
        flow.execute(matrix);
        loader.save(matrix, "target/" + args[0] + ".solved");
        System.out.println("Loaded matrix: \n" + matrix);
        System.out.println("Time elapsed: " + (System.currentTimeMillis() - start) + "ms");
    }
}
