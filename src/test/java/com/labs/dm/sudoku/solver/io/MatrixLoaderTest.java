/*
 * Copyright daniel.mroczka@gmail.com. All rights reserved. 
 */
package com.labs.dm.sudoku.solver.io;

import com.labs.dm.sudoku.solver.core.IMatrix;
import java.io.FileNotFoundException;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author daniel
 */
public class MatrixLoaderTest {

    private final MatrixLoader loader = new MatrixLoader();

    @Test
    public void shouldReadFileAsString() throws Exception {
        //WHEN
        String result = loader.readFileAsString("empty.txt");
        //THEN
        assertNotNull(result);
        //assertEquals((9 + 8) * 9 + 8 * 2, result.length());
    }
    
    @Test
    public void shouldLoadTable() throws Exception {
        //WHEN
        IMatrix result = loader.loadTable("empty.txt");
        //THEN
        assertTrue(result.isEmpty());
    }    

    @Test(expected = FileNotFoundException.class)
    public void shouldNotReadNotExistFile() throws Exception {
        loader.readFileAsString("invalid.notexist");
    }

    @Test
    public void toTable() {
        String[] res = loader.toTable("line1\nline2\nline3");
        assertEquals(3, res.length);
    }

    @Test
    public void convertToIntTable() {
        String[] tab = {"1", "2", "3"};
        int[] intTab = loader.convertToIntTable(tab);
        assertEquals(3, intTab.length);
        assertArrayEquals(new int[]{1, 2, 3}, intTab);
    }

}
