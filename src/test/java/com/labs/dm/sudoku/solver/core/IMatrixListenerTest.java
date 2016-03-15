package com.labs.dm.sudoku.solver.core;

import com.labs.dm.sudoku.solver.io.MatrixLoader;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

/**
 * Created by daniel on 10-Mar-16.
 */
public class IMatrixListenerTest {

    @Test
    public void shouldFireEvents() {
        //GIVEN
        IMatrix matrix = new Matrix();
        IMatrixListener listener = mock(IMatrixListener.class);
        matrix.addMatrixListener(listener);
        //WHEN
        matrix.addCandidates(1, 1, new Integer[]{1, 2, 3});
        matrix.setValueAt(0, 0, 1);
        matrix.removeCandidate(1, 1, 2);
        //THEN
        verify(listener, times(1)).onChangeValue(0, 0, 1);
        verify(listener, times(1)).onRemoveCandidate(1, 1, 2);
    }

    @Test
    public void shouldNotFireEvents() {
        //GIVEN
        IMatrix matrix = new Matrix();
        IMatrixListener listener = mock(IMatrixListener.class);
        matrix.addMatrixListener(listener);
        matrix.removeMatrixListener();
        //WHEN
        matrix.addCandidates(0, 0, new Integer[]{1, 2, 3});
        matrix.setValueAt(0, 0, 2);
        //THEN
        verify(listener, times(0)).onChangeValue(0, 0, 2);
        verify(listener, times(0)).onRemoveCandidate(0, 0, 2);
    }

    @Test
    public void shouldNotFireEventsWhenNoListener() {
        //GIVEN
        IMatrix matrix = new Matrix();
        IMatrixListener listener = mock(IMatrixListener.class);
        matrix.addCandidates(1, 1, new Integer[]{1, 2, 3});
        //WHEN
        matrix.setValueAt(0, 0, 1);
        matrix.removeCandidate(1, 1, 2);
        //THEN
        verify(listener, times(0)).onChangeValue(0, 0, 1);
        verify(listener, times(0)).onRemoveCandidate(1, 1, 2);
    }

    @Test
    public void onResolved() throws IOException {
        //GIVEN
        IMatrix matrix = new MatrixLoader().load("src/test/resources/patterns/solved.txt");
        IMatrixListener listener = mock(IMatrixListener.class);
        int temp = matrix.getValueAt(0, 0);
        matrix.setValueAt(0, 0, 0);
        matrix.addMatrixListener(listener);
        //WHEN
        matrix.setValueAt(0, 0, temp);
        //THEN
        verify(listener, times(1)).onResolved();
    }
}