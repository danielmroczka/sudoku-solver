package com.labs.dm.sudoku.solver.executors;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for the Flow
 */
class FlowTest {

    @Test
    void testFlowCreation() {
        // Simply test that we can create a Flow object without exceptions
        Flow flow = new Flow();
        assertNotNull(flow);
    }

    @Test
    void testExecuteWithRealMatrix() {
        // Arrange
        IMatrix matrix = new Matrix();
        // Initialize the matrix with a simple pattern
        for (int i = 0; i < 9; i++) {
            matrix.setValueAt(i, i, i + 1);
        }

        Flow flow = new Flow();

        // Act - this should not throw any exceptions
        flow.execute(matrix);

        // Assert - just verify the execution completes without exceptions
        assertTrue(true);
    }
}
