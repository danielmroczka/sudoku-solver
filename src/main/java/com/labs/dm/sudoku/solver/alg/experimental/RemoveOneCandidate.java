package com.labs.dm.sudoku.solver.alg.experimental;

import com.labs.dm.sudoku.solver.alg.IAlgorithm;
import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Pair;
import com.labs.dm.sudoku.solver.utils.CounterHashMap;

import java.util.ArrayList;
import java.util.List;

import static com.labs.dm.sudoku.solver.core.IMatrix.SIZE;
import static com.labs.dm.sudoku.solver.utils.Utils.getSurroundings;

/**
 * TODO: need to find better class name
 * <p>
 * Created by daniel on 2016-03-10.
 */
public class RemoveOneCandidate implements IAlgorithm {
    @Override
    public void execute(IMatrix matrix) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                CounterHashMap<Integer> map = new CounterHashMap<>();
                for (Pair pair : getSurroundings(row, col)) {
                    for (int cand : matrix.getCandidates(pair)) {
                        map.inc(cand);
                    }
                }
                List<Integer> forcedToRemove = new ArrayList<>();
                for (int cand : matrix.getCandidates(row, col)) {
                    if (map.get(cand) == 0) {
                        forcedToRemove.add(cand);
                    }
                }

                matrix.removeCandidate(row, col, forcedToRemove);
            }
        }
    }
}
