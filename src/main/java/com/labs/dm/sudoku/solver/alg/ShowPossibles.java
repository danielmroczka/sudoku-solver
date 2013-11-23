/*
 * Copyright daniel.mroczka@gmail.com. All rights reserved. 
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import static com.labs.dm.sudoku.solver.core.IMatrix.SIZE;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author daniel
 */
public class ShowPossibles implements IAlgorithm {

    private final List<Integer> fullSet = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

    @Override
    public void execute(IMatrix matrix) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Set<Integer> set = new HashSet<>(fullSet);
                
                int[] cols = matrix.getElemsInCol(col);
                int[] rows = matrix.getElemsInRow(row);
                int[] boxes = matrix.getElemsInBox(row/3, col/3);
                for (int i: cols) {
                    set.remove(i);
                }
                for (int i: rows) {
                    set.remove(i);
                }
                for (int i: boxes) {
                    set.remove(i);
                }
                
                matrix.getPossibleValues()[row][col] = set;
            }
        }
    }
}
