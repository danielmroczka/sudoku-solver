package com.labs.dm.sudoku.solver.loader;

import com.labs.dm.sudoku.solver.core.IMatrix;

import java.io.*;
import java.util.Scanner;

public class MatrixLoader {

    public IMatrix loadMatrix(String fileName) throws FileNotFoundException {
        Scanner scanner = readFileAsString(fileName);
        return null;
    }

    public void saveMatrix(String fileName) {

    }

    protected Scanner readFileAsString(String fileName) throws FileNotFoundException {
        return new Scanner(getResourceAsStream(fileName));
    }

    protected InputStream getResourceAsStream(String fileName) throws FileNotFoundException {
        if (getClass().getClassLoader().getResourceAsStream(fileName) == null) {
            throw new FileNotFoundException();
        }
        return getClass().getClassLoader().getResourceAsStream(fileName);
    }

}
