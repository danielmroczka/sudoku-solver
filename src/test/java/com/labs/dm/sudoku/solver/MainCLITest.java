/*
 * Copyright daniel.mroczka@gmail.com. All rights reserved. 
 */
package com.labs.dm.sudoku.solver;

import org.junit.Test;

/**
 *
 * @author daniel
 */
public class MainCLITest {

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenArgIsEmpty() throws Exception {
        MainCLI.main(new String[]{});
    }

}
