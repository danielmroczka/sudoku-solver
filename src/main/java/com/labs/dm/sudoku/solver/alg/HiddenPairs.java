/*
 * Copyright Daniel Mroczka. All rights reserved.
 */
package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;

import java.util.ArrayList;
import java.util.List;

/**
 * Hidden Pairs algorithm implementation
 * <p>
 * http://www.learn-sudoku.com/hidden-pairs.html
 *
 * @author daniel
 */
public class HiddenPairs implements IAlgorithm {

    @Override
    public void execute(IMatrix matrix) {

        findHiddenPairsInRows(matrix);
        findHiddenPairsInCols(matrix);
        findHiddenPairsInBlock(matrix);
    }

    private void findHiddenPairsInBlock(IMatrix matrix) {

    }

    private void findHiddenPairsInCols(IMatrix matrix) {

    }

    private void findHiddenPairsInRows(IMatrix matrix) {
        for (int row = 0; row < IMatrix.SIZE; row++) {
            List<List<Integer>> list = new ArrayList<>();
            for (int col = 0; col < IMatrix.SIZE; col++) {
                List<Integer> l = new ArrayList<>();
                l.addAll(matrix.getPossibleValues(row, col));
                list.add(col, l);
            }

            for (int i = 0; i < list.size(); i++) {
                List<Integer> list1 = list.get(i);
                if (list1.size() > 1) {
                    System.out.println(list1);
                    for (int j = 0; j < list.size(); j++) {
                        List<Integer> list2 = list.get(j);
                        if (i == j || list2.size() < 2) {
                            continue;
                        }


                   //     System.out.println(list2);

                    }
                }
            }
            System.out.println("---");
        }
    }


}
