/*
 * Copyright daniel.mroczka@gmail.com. All rights reserved. 
 */
package com.labs.dm.sudoku.solver;

import java.io.FileNotFoundException;
import org.junit.Test;

/**
 *
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
        MainCLI.main(new String[]{"patterns/easy1.txt"});
    }

}