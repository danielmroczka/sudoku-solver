/*
 * Copyright daniel.mroczka@gmail.com. All rights reserved. 
 */
package com.labs.dm.sudoku.solver;

import org.junit.Test;

import java.io.FileNotFoundException;

/**
 * @author daniel
 */
public class MainCLITest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenArgIsEmpty() throws Exception {
        MainCLI.main(new String[]{});
    }

    @Test(expected = FileNotFoundException.class)
    public void shouldAcceptValidInput() throws Exception {
        MainCLI.main(new String[]{"not.existed.file"});
    }

    @Test
    public void shouldAcceptValidInput1() throws Exception {
        MainCLI.main(new String[]{"patterns/alg/opensingles/002.txt"});

    }

}
