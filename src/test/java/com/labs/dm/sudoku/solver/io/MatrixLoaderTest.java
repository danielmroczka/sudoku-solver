/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.io;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Daniel Mroczka
 */
public class MatrixLoaderTest {

    private final MatrixLoader loader = new MatrixLoader();

    @Test
    public void shouldReadFileAsString() throws Exception {
        //WHEN
        String result = loader.readFileAsString("patterns/empty.txt");
        //THEN
        assertNotNull(result);
        //assertEquals((9 + 8) * 9 + 8 * 2, result.length());
    }

    @Test
    public void shouldLoadTable() throws Exception {
        //WHEN
        IMatrix result = loader.load("patterns/empty.txt");
        //THEN
        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldSaveTable() throws IOException {
        //GIVEN
        IMatrix matrix = new Matrix();
        matrix.setValueAt(4, 4, 4);
        //WHEN
        loader.save(matrix, "target/test/output.txt");
        //THEN
        IMatrix readMatrix = loader.load("target/test/output.txt");
        assertArrayEquals(matrix.toArray(), readMatrix.toArray());
    }

    @Test
    public void shouldOutputFileHasCorrectSize() throws IOException {
        //GIVEN
        IMatrix matrix = new Matrix();
        //WHEN
        loader.save(matrix, "target/test/output.txt");
        //THEN
        File file = new File("target/test/output.txt");
        assertTrue(file.exists());
        int expectedSize = Matrix.SIZE * (2 * Matrix.SIZE - 1) + +System.lineSeparator().length() * (Matrix.SIZE - 1);
        assertEquals(expectedSize, file.length());
    }

    @Test
    public void shouldNotReadNotExistFile() {
        assertThrows(FileNotFoundException.class, () -> loader.readFileAsString("invalid.notexist"));
    }

    @Test
    public void shouldConvertToTable() {
        assertEquals(3, loader.toTable("line1\nline2\nline3").length);
        //comma
        assertEquals(3, loader.toTable("1,2,3").length);
        assertEquals(9, loader.toTable("1,2,3\n4,5,6\n7,8,9").length);
        assertEquals(0, loader.toTable(",,,,,,,,,").length);
        //semicolon
        assertEquals(3, loader.toTable("1;2;3").length);
        assertEquals(9, loader.toTable("1;2;3\n4;5;6\n7;8;9").length);
        //whitespace
        assertEquals(3, loader.toTable("1 2 3").length);
        assertEquals(3, loader.toTable(" 1, 2, 3 ").length);
        assertEquals(3, loader.toTable("  1,  2,   3    ").length);
        assertEquals(9, loader.toTable("1 2 3\n4 5 6\n7 8 9").length);
        //tab
        assertEquals(3, loader.toTable("1\t2\t3").length);
        assertEquals(9, loader.toTable("1\t2\t3\n4\t5\t6\n7\t8\t9").length);
        //comma + whitespace + newline
        assertEquals(9, loader.toTable(" 1, 2, 3 \n 4, 5, 6 \n 7, 8, 9").length);
    }

    @Test
    public void convertToIntTable() {
        String[] tab = {"1", "2", "3"};
        int[] intTab = loader.convertToIntTable(tab);
        assertEquals(3, intTab.length);
        assertArrayEquals(new int[]{1, 2, 3}, intTab);
    }

}
