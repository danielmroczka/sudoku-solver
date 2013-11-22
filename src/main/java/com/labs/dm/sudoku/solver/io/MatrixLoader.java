package com.labs.dm.sudoku.solver.io;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Class
 *
 * @author daniel
 */
public class MatrixLoader {

    public IMatrix loadTable(String fileName) throws IOException {
        String inputText = readFileAsString(fileName);
        int[] tab = convertToIntTable(toTable(inputText));
        IMatrix matrix = new Matrix(tab);
        return matrix;
    }

    protected int[] convertToIntTable(String[] stringTab) {
        int[] intTab = new int[stringTab.length];

        for (int i = 0; i < stringTab.length; i++) {
            intTab[i] = Integer.parseInt(stringTab[i].trim());
        }

        return intTab;
    }

    protected String[] toTable(String input) {
        return input.split("[\n,]");
    }

    protected String readFileAsString(String filePath) throws IOException {
        StringBuilder fileData = new StringBuilder(IMatrix.SIZE);
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new FileNotFoundException();
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            char[] buf = new char[1024];
            int numRead;
            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
                buf = new char[128];
            }
        }
        return fileData.toString();
    }

}
