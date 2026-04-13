package com.labs.dm.sudoku.solver.executors;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for the FlowFactory
 */
class FlowFactoryTest {

    @Test
    void testFlowFactoryCreation() {
        // Simply test that we can create a FlowFactory object without exceptions
        FlowFactory factory = new FlowFactory();
        assertNotNull(factory);
    }

    @Test
    void testExecuteWithRealMatrix() {
        // Arrange
        IMatrix matrix = new Matrix();
        // Initialize the matrix with a simple pattern
        for (int i = 0; i < 9; i++) {
            matrix.setValueAt(i, i, i + 1);
        }

        FlowFactory factory = new FlowFactory();

        // Act - this should not throw any exceptions
        factory.execute(matrix);

        // Assert - just verify the execution completes without exceptions
        assertTrue(true);
    }

    @Test
    void testExecuteWithSolvedMatrix() {
        // Arrange
        IMatrix matrix = new Matrix();
        // Initialize the matrix with a complete valid Sudoku solution
        int[] solution = {
                1, 2, 3, 4, 5, 6, 7, 8, 9,
                4, 5, 6, 7, 8, 9, 1, 2, 3,
                7, 8, 9, 1, 2, 3, 4, 5, 6,
                2, 3, 4, 5, 6, 7, 8, 9, 1,
                5, 6, 7, 8, 9, 1, 2, 3, 4,
                8, 9, 1, 2, 3, 4, 5, 6, 7,
                3, 4, 5, 6, 7, 8, 9, 1, 2,
                6, 7, 8, 9, 1, 2, 3, 4, 5,
                9, 1, 2, 3, 4, 5, 6, 7, 8
        };

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                matrix.setValueAt(i, j, solution[i * 9 + j]);
            }
        }

        FlowFactory factory = new FlowFactory();

        // Act - this should not throw any exceptions and should terminate quickly
        factory.execute(matrix);

        // Assert - just verify the execution completes without exceptions
        assertTrue(matrix.isSolved());
    }
}