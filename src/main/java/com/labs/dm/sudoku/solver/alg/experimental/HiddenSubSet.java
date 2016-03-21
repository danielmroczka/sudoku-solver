package com.labs.dm.sudoku.solver.alg.experimental;

import com.labs.dm.sudoku.solver.alg.IAlgorithm;
import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.utils.CounterHashMap;
import com.labs.dm.sudoku.solver.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.labs.dm.sudoku.solver.core.IMatrix.BLOCK_SIZE;
import static com.labs.dm.sudoku.solver.core.IMatrix.SIZE;

/**
 * Created by Daniel Mroczka on 2016-03-21.
 */
public abstract class HiddenSubSet implements IAlgorithm {

    protected int size;

    @Override
    public void execute(IMatrix matrix) {
        findHiddenPairsInRows(matrix);
        // findHiddenPairsInCols(matrix);
        //  findHiddenPairsInBlocks(matrix);
    }

    private void findHiddenPairsInRows(IMatrix matrix) {
        List<Subset> subset = new ArrayList<>();

        for (int row = 0; row < SIZE; row++) {
            Integer[][] tab = new Integer[SIZE][SIZE];
            for (int col = 0; col < SIZE; col++) {
                for (int candidate : matrix.getCandidates(row, col)) {
                    tab[candidate - 1][col] = col;
                }
            }
            group(subset, tab);

            for (Subset s : subset) {
                System.out.println("Removing subset in row " + s);
                for (int col : s.getSubsetPosition()) {
                    removeCandidate(matrix, row, col, s.getSubsetNumber());

                }
                break;
            }
        }

        //  System.out.println(subset.size());
    }

    private void findHiddenPairsInCols(IMatrix matrix) {
        List<Subset> subset = new ArrayList<>();

        for (int col = 0; col < SIZE; col++) {
            Integer[][] tab = new Integer[SIZE][SIZE];
            for (int row = 0; row < SIZE; row++) {
                for (int candidate : matrix.getCandidates(row, col)) {
                    tab[candidate - 1][row] = row;
                }
            }
            group(subset, tab);
            for (Subset s : subset) {
                System.out.println("Removing subset in col " + col);

                for (int row : s.getSubsetPosition()) {
                    removeCandidate(matrix, row, col, s.getSubsetNumber());
                }
            }
        }

        //System.out.println(subset.size());

    }

    private void findHiddenPairsInBlocks(IMatrix matrix) {
        List<Subset> subset = new ArrayList<>();

        for (int rowGroup = 0; rowGroup < BLOCK_SIZE; rowGroup++) {
            for (int colGroup = 0; colGroup < BLOCK_SIZE; colGroup++) {
                Integer[][] tab = new Integer[SIZE][SIZE];
                for (int row = rowGroup * BLOCK_SIZE; row < (rowGroup + 1) * BLOCK_SIZE; row++) {
                    for (int col = colGroup * BLOCK_SIZE; col < (colGroup + 1) * BLOCK_SIZE; col++) {
                        for (int candidate : matrix.getCandidates(row, col)) {
                            int pos = (row % 3) * 3 + col % 3;
                            tab[candidate - 1][pos] = pos;
                        }
                    }
                }
                group(subset, tab);

                for (Subset s : subset) {
                    System.out.println("Removing subset in block " + s);

                    for (int pos : s.getSubsetPosition()) {
                        int row = rowGroup * BLOCK_SIZE + (pos / 3);
                        int col = colGroup * BLOCK_SIZE + pos % 3;
                        removeCandidate(matrix, row, col, s.getSubsetNumber());
                    }
                }
            }
        }

        //  System.out.println(subset.size());

    }

    private void group(List<Subset> subset, Integer[][] tab) {
        List<List<Integer>> list = Utils.combinationList(Utils.FULL_LIST, size);


        for (List<Integer> item : list) {
            CounterHashMap<Integer> map = new CounterHashMap<>();
            for (int i : item) {
                Integer[] pos = tab[i - 1];
                for (Integer p : pos) {
                    if (p != null) {
                        map.inc(p);

                    }
                }
            }
            if (map.size() == size) {
                boolean found = true;
                for (int i : map.values()) {
                    if (i < 2) {
                        found = false;
                    }
                }
                if (found) {
                    subset.add(new Subset(item, new ArrayList<>(map.keySet())));
                }
            }
        }
    }

    protected void removeCandidate(IMatrix matrix, int row, int col, List<Integer> subset) {
        if (matrix.getCandidates(row, col).isEmpty()) {
            return;
        }

        List<Integer> common = new ArrayList<>(matrix.getCandidates(row, col));
        common.retainAll(subset);
        if (common.size() >= 2) {
            List<Integer> diff = new ArrayList<>(matrix.getCandidates(row, col));
            diff.removeAll(subset);
            matrix.removeCandidate(row, col, diff);
        }
    }

    class Subset {

        private List<Integer> subsetNumber;
        private List<Integer> subsetPosition;

        public Subset(List<Integer> subsetNumber, List<Integer> subsetPosition) {
            this.subsetNumber = subsetNumber;
            this.subsetPosition = subsetPosition;
        }

        public List<Integer> getSubsetNumber() {
            return subsetNumber;
        }

        public List<Integer> getSubsetPosition() {
            return subsetPosition;
        }
    }

}
