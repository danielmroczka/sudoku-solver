/*
 * Copyright Daniel Mroczka. All rights reserved.
 */

package com.labs.dm.sudoku.solver;

import com.labs.dm.sudoku.solver.io.Generator;

import java.io.IOException;

/**
 * Created by Daniel Mroczka on 2016-02-10.
 */
public class Main {

    private Main() {
    }

    public static void main(String[] args) throws IOException {
        Generator generator = new Generator();
        generator.generate(70);
//        MatrixLoader loader = new MatrixLoader();
//        IMatrix matrix = loader.load("src/test/resources/patterns/hard/hard6.txt");
//        matrix.addMatrixListener(new LogListener());
//        Flow flow = new Flow();
//        flow.execute(matrix);
//        System.out.println(matrix.getContext());
    }
}
