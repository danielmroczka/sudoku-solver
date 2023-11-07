package com.labs.dm.sudoku.solver.alg.chains;

import com.labs.dm.sudoku.solver.alg.IAlgorithm;
import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import com.labs.dm.sudoku.solver.core.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Daniel Mroczka on 4/7/2016.
 * <p>
 * http://www.sadmansoftware.com/sudoku/xychain.php
 */
public class XYChains implements IAlgorithm {
    @Override
    public void execute(IMatrix matrix) {
        Set<Pair> set = new HashSet<>();
        for (int row = 0; row < Matrix.SIZE; row++) {
            for (int col = 0; col < Matrix.SIZE; col++) {
                if (matrix.getCandidates(row, col).size() == 2) {
                    Pair pair = new Pair(row, col);
                    set.add(pair);
                }
            }
        }
        List<Pair> pairs = new ArrayList<>();
        findChains(matrix, set, pairs);
        System.out.println(pairs);
    }

    private void findChains(IMatrix matrix, Set<Pair> set, List<Pair> pairs) {


        for (int i = 0; i < set.size(); i++) {
            Pair pair = set.toArray(new Pair[0])[i];
            if (pairs.contains(pair)) {
                return;
            }

            if (pairs.isEmpty()) {
                pairs.add(pair);
            } else {
                Set<Integer> common = new HashSet<>();
                common.addAll(matrix.getCandidates(pairs.get(pairs.size() - 1)));
                common.addAll(matrix.getCandidates(pair));
                if (common.size() == 3) {
                    pairs.add(pair);
                }  //   else {
                findChains(matrix, set, pairs);
                // }
            }
        }
    }

    private void findChains2(IMatrix matrix, List<Pair> pairs) {
        for (int row = 0; row < Matrix.SIZE; row++) {
            for (int col = 0; col < Matrix.SIZE; col++) {
                if (matrix.getCandidates(row, col).size() == 2) {
                    Pair pair = new Pair(row, col);
                    System.out.println(pair);

                    if (pairs.contains(pair)) {
                        return;
                    }

                    if (pairs.isEmpty()) {
                        pairs.add(pair);
                    } else {
                        Set<Integer> set = new HashSet<>();
                        set.addAll(matrix.getCandidates(pairs.get(pairs.size() - 1)));
                        set.addAll(matrix.getCandidates(pair));
                        if (set.size() == 3) {
                            pairs.add(pair);
                        }  //   else {
                        //   findChains(matrix, set pairs);
                        // }
                    }
                }
            }
        }
    }
}
