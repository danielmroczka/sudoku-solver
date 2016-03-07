package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Pair;
import com.labs.dm.sudoku.solver.utils.CounterHashMap;
import com.labs.dm.sudoku.solver.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.labs.dm.sudoku.solver.core.IMatrix.SIZE;

/**
 * Swordfish algorithm
 * <p>
 * http://hodoku.sourceforge.net/en/tech_fishb.php#bf3
 * http://www.sudoku9981.com/sudoku-solving/swordfish.php
 * <p>
 * Created by daniel on 2016-03-03.
 */
public class Swordfish implements IAlgorithm {

    @Override
    public void execute(IMatrix matrix) {

        Map<Integer, List<Pair>> map = Utils.candidatesMap(matrix);

        for (Map.Entry<Integer, List<Pair>> entry : map.entrySet()) {
            if (entry.getValue().size() < 6) {
                continue;
            }

            CounterHashMap<Integer> rowsMap = new CounterHashMap<>();
            CounterHashMap<Integer> colsMap = new CounterHashMap<>();

            for (Pair pair : entry.getValue()) {
                rowsMap.inc(pair.getRow());
                colsMap.inc(pair.getCol());
            }

            List<Integer> cols = new ArrayList<>();
            List<Integer> rows = new ArrayList<>();

            for (Map.Entry<Integer, Integer> entryRows : rowsMap.entrySet()) {
                if (entryRows.getValue() >= 2) {
                    rows.add(entryRows.getKey());
                }
            }
            for (Map.Entry<Integer, Integer> entryCols : colsMap.entrySet()) {
                if (entryCols.getValue() >= 2) {
                    cols.add(entryCols.getKey());
                }
            }

            if (colsMap.size() == 3 && rowsMap.size() >= 3) {
                for (int col : colsMap.keySet()) {
                    for (int row = 0; row < SIZE; row++) {
                        if (!(rows.contains(row))) {
                            matrix.removeCandidate(row, col, entry.getKey());
                        }
                    }
                }
            } else if (colsMap.size() >= 3 && rowsMap.size() == 3) {
                for (int row : rowsMap.keySet()) {
                    for (int col = 0; col < SIZE; col++) {
                        if (!(cols.contains(col))) {
                            matrix.removeCandidate(row, col, entry.getKey());
                        }
                    }
                }

            }

            System.out.println(entry.getKey());
        }


    }
}
