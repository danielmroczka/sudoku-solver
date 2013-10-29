package com.labs.dm.sudoku.solver.io;

import com.labs.dm.sudoku.solver.core.IMatrix;

import java.io.*;

public class MatrixLoader {

    public IMatrix loadTable(String fileName) throws IOException {
        String inputText = readFileAsString(fileName);
        int[] tab = convertToIntTable(table(inputText));
        //Matrix matrix = new Matrix(tab);
        return null;
    }

    protected int[] convertToIntTable(String[] stringTab) {
        int[] intTab = new int[stringTab.length];

        for (int i = 0; i < stringTab.length; i++) {
            intTab[i] = Integer.parseInt(stringTab[i].trim());
        }

        return intTab;
    }

    protected String[] table(String input) {
        String[] split = input.split("[\n,]");

        return split;
    }

    protected String readFileAsString(String filePath) throws java.io.IOException {
        StringBuilder fileData = new StringBuilder(9);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(filePath)));
            char[] buf = new char[1024];
            int numRead = 0;
            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
                buf = new char[1024];
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return fileData.toString();
    }

}
