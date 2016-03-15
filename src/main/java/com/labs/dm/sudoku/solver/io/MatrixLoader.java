package com.labs.dm.sudoku.solver.io;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Load / Save operation on Matrix object.
 *
 * @author Daniel Mroczka
 */
public class MatrixLoader {

    private static final String DEFAULT_DELIMITER = ",";

    public IMatrix load(String fileName) throws IOException {
        String inputText = this.readFileAsString(fileName);
        int[] tab = this.convertToIntTable(this.toTable(inputText));
        return new Matrix(tab);
    }

    public void save(IMatrix matrix, String fileName) throws IOException {
        StringBuilder sb = new StringBuilder(100);
        for (int row = 0; row < IMatrix.SIZE; row++) {
            for (int col = 0; col < IMatrix.SIZE; col++) {
                sb.append(matrix.getValueAt(row, col));
                if (col < IMatrix.SIZE - 1) {
                    sb.append(MatrixLoader.DEFAULT_DELIMITER);
                }
            }
            if (row < IMatrix.SIZE - 1) {
                sb.append(System.lineSeparator());
            }
        }
        File file = new File(fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(sb.toString().getBytes(Charset.defaultCharset()));
        }
    }

    protected int[] convertToIntTable(String[] stringTab) {
        int[] intTab = new int[stringTab.length];

        for (int i = 0; i < stringTab.length; i++) {
            intTab[i] = Integer.parseInt(stringTab[i].trim());
        }

        return intTab;
    }

    protected String[] toTable(String input) {
        input = input.trim().replaceAll("[\\s*,\\s*]+", ",").replaceAll("[\\s]+", ",");
        return input.split("[\n\t,;]");
    }

    protected String readFileAsString(String filePath) throws IOException {
        StringBuilder fileData = new StringBuilder(IMatrix.SIZE);
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            inputStream = new FileInputStream(filePath);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            char[] buf = new char[128];
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
