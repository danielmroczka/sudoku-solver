package com.labs.dm.sudoku.solver.alg.experimental;

import com.labs.dm.sudoku.solver.alg.IAlgorithm;
import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.utils.CounterHashMap;
import com.labs.dm.sudoku.solver.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.labs.dm.sudoku.solver.core.IMatrix.BLOCK_SIZE;
import static com.labs.dm.sudoku.solver.core.IMatrix.SIZE;
import static com.labs.dm.sudoku.solver.utils.Utils.blockElems;

/**
 * Created by Daniel Mroczka on 2016-03-21.
 */
public abstract class HiddenSubSet implements IAlgorithm {

    protected int size;

    @Override
    public void execute(IMatrix matrix) {
        findHiddenPairsInRows(matrix);
        findHiddenPairsInCols(matrix);
        findHiddenPairsInBlocks(matrix);
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
                if (subset.size() > 1) {
                    System.out.println("__r_" + Arrays.toString(subset.toArray()));
                }

                for (int col : s.getSubsetPosition()) {
                    removeCandidate(matrix, row, col, s.getSubsetNumber());
                }
            }
        }
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
                if (subset.size() > 1) {
                    System.out.println("__c_" + Arrays.toString(subset.toArray()));
                }

                for (int row : s.getSubsetPosition()) {
                    removeCandidate(matrix, row, col, s.getSubsetNumber());
                }
            }
        }
    }

    private void findHiddenPairsInBlocks(IMatrix matrix) {
        List<Subset> subset = new ArrayList<>();

        for (int rowGroup = 0; rowGroup < BLOCK_SIZE; rowGroup++) {
            for (int colGroup = 0; colGroup < BLOCK_SIZE; colGroup++) {
                Integer[][] tab = new Integer[SIZE][SIZE];
                for (int row : blockElems(rowGroup)) {
                    for (int col : blockElems(colGroup)) {
                        for (int candidate : matrix.getCandidates(row, col)) {
                            int pos = (row % 3) * 3 + col % 3;
                            tab[candidate - 1][pos] = pos;
                        }
                    }
                }
                group(subset, tab);

                for (Subset s : subset) {
                    if (subset.size() > 1) {
                        System.out.println("__b_" + Arrays.toString(subset.toArray()));
                    }

                    for (int pos : s.getSubsetPosition()) {
                        int row = rowGroup * BLOCK_SIZE + (pos / 3);
                        int col = colGroup * BLOCK_SIZE + pos % 3;
                        removeCandidate(matrix, row, col, s.getSubsetNumber());
                    }
                }
            }
        }
    }

    private void group(List<Subset> result, Integer[][] tab) {
        /** Set of proposal subset, each of them with the same size **/
        List<List<Integer>> subsets = Utils.combinationList(Utils.FULL_LIST, size);

        /** Subset is a unique list of number staring from 1 with declared size (2..4) **/
        for (List<Integer> subset : subsets) {
            CounterHashMap<Integer> counterMap = new CounterHashMap<>();
            boolean[] flags = new boolean[size];
            int id = 0;

            for (int item : subset) {
                Integer[] positions = tab[item - 1];

                for (Integer pos : positions) {
                    if (pos != null) {
                        counterMap.inc(pos);
                        flags[id] = true;
                    }
                }
                id++;
            }

            if (counterMap.size() == size) {
                boolean found = true;
                for (int i : counterMap.values()) {
                    if (i < 2) {
                        found = false;
                    }
                }
                for (boolean elem : flags) {
                    if (!elem) {
                        found = false;
                    }
                }

                if (found) {
                    result.add(new Subset(subset, new ArrayList<>(counterMap.keySet())));
                }
            }
        }
    }

    protected void removeCandidate(IMatrix matrix, int row, int col, List<Integer> subset) {
        if (matrix.getCandidates(row, col).isEmpty()) {
            return;
        }

        //TODO: Rewrite this part to make easier
        List<Integer> common = new ArrayList<>(matrix.getCandidates(row, col));
        common.retainAll(subset);
        if (common.size() >= 2) {
            List<Integer> diff = new ArrayList<>(matrix.getCandidates(row, col));
            diff.removeAll(subset);
            if (diff.size() > 0) {
                matrix.removeCandidate(row, col, diff);
                System.out.println("Remove " + diff);
            }
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

        @Override
        public String toString() {
            return "Subset{" +
                    "subsetNumber=" + subsetNumber +
                    ", subsetPosition=" + subsetPosition +
                    '}';
        }
    }

}
