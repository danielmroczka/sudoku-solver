/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Daniel Mroczka
 */
public class MainCLITest {

    @Test
    public void shouldThrowExceptionWhenArgIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> MainCLI.main(new String[]{}));
    }

    @Test
    public void shouldAcceptValidInput() {
        assertThrows(FileNotFoundException.class, () -> MainCLI.main(new String[]{"not.existed.file"}));
    }

    @Test
    public void shouldAcceptValidInput1() throws Exception {
        MainCLI.main(new String[]{"patterns/alg/opensingles/002.txt"});

    }

}
