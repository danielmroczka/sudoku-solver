package com.labs.dm.sudoku.solver.executors;

import com.labs.dm.sudoku.solver.alg.IAlgorithm;
import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for the Executor
 */
class ExecutorTest {

    @Test
    void testRunExecutesAlgorithm() {
        // Arrange
        IMatrix matrix = new Matrix();
        // Add candidates to the matrix so they can be removed by the algorithm
        matrix.addCandidates(0, 0, new Integer[]{1, 2, 3});

        // Act
        Executor.run(matrix, TestAlgorithm.class);

        // Assert - verify the algorithm was executed by checking the context
        assertFalse(matrix.getContext().isEmpty(), "Context should not be empty");
        assertEquals("TestAlgorithm", matrix.getContext().get(0).name());
    }

    @Test
    void testRunWithMultipleAlgorithms() {
        // Arrange
        IMatrix matrix = new Matrix();
        // Add candidates to the matrix so they can be removed by the algorithm
        matrix.addCandidates(0, 0, new Integer[]{1, 2, 3});
        matrix.addCandidates(0, 1, new Integer[]{4, 5, 6});

        // Act
        Executor.run(matrix, TestAlgorithm.class, TestAlgorithm.class);

        // Assert
        assertEquals(2, matrix.getContext().size(), "Context should have 2 items");
    }

    @Test
    void testRunWithInvalidMatrix() {
        // Arrange
        IMatrix matrix = Mockito.mock(IMatrix.class);
        List<ContextItem> contextItems = new ArrayList<>();

        when(matrix.validate(true)).thenReturn(false);
        when(matrix.getContext()).thenReturn(contextItems);
        when(matrix.getSolvedItems()).thenReturn(0);
        when(matrix.getCandidatesCount()).thenReturn(0);

        // Act - this should not throw an exception even with an invalid matrix
        Executor.run(matrix, TestAlgorithm.class);

        // Assert
        verify(matrix).validate(true);
    }

    @Test
    void testRunWithExceptionDuringInstantiation() {
        // Arrange
        IMatrix matrix = new Matrix();

        // Act - this should not throw an exception even if algorithm instantiation fails
        Executor.run(matrix, ExceptionAlgorithm.class);

        // Assert - no context items should be added
        assertTrue(matrix.getContext().isEmpty());
    }

    @Test
    void testPerformanceTracking() {
        // Arrange
        IMatrix matrix = Mockito.mock(IMatrix.class);
        List<ContextItem> contextItems = new ArrayList<>();

        when(matrix.validate(true)).thenReturn(true);
        when(matrix.getContext()).thenReturn(contextItems);
        when(matrix.getSolvedItems()).thenReturn(0, 2); // Return 0 first, then 2
        when(matrix.getCandidatesCount()).thenReturn(10, 5); // Return 10 first, then 5

        // Act
        Executor.run(matrix, TestAlgorithm.class);

        // Assert
        assertEquals(1, contextItems.size());
        ContextItem item = contextItems.get(0);
        assertEquals("TestAlgorithm", item.name());
        assertEquals(2, item.solved());
        assertEquals(5, item.reducedCandidate());
    }

    /**
     * Test algorithm implementation for testing Executor
     */
    static class TestAlgorithm implements IAlgorithm {
        private boolean executed = false;

        @Override
        public void execute(IMatrix matrix) {
            executed = true;
            // Modify the matrix to trigger context item creation
            if (matrix.getCandidatesCount() > 0) {
                // Remove a candidate to change the candidates count
                for (int row = 0; row < 9; row++) {
                    for (int col = 0; col < 9; col++) {
                        if (!matrix.getCandidates(row, col).isEmpty()) {
                            matrix.removeCandidates(row, col, List.of(matrix.getCandidates(row, col).iterator().next()));
                            return;
                        }
                    }
                }
            } else {
                // Add a candidate if there are none
                matrix.addCandidates(0, 0, new Integer[]{1});
                matrix.removeCandidates(0, 0, List.of(1));
            }
        }

        public boolean isExecuted() {
            return executed;
        }
    }

    /**
     * Test algorithm that throws an exception during instantiation
     */
    static class ExceptionAlgorithm implements IAlgorithm {
        public ExceptionAlgorithm() throws InstantiationException {
            throw new InstantiationException("Test exception");
        }

        @Override
        public void execute(IMatrix matrix) {
            // This will never be called
        }
    }
}
