package com.labs.dm.sudoku.solver.alg;

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
            group(subset, tab, size);

            if (subset.size() > 1) {
                System.out.println("__r_" + row + "_" + Arrays.toString(tab));
                //System.out.println("__r_" + Arrays.toString(subset.toArray()));
            }

            for (Subset s : subset) {

                for (int col : s.getSubsetPosition()) {
                    removeCandidate(matrix, row, col, s.getSubsetNumber());
                }
                //   break;
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


            group(subset, tab, size);
            if (subset.size() > 1) {
                //System.out.println("__c_" + Arrays.toString(subset.toArray()));
                System.out.println("__c_" + col + "_" + Arrays.toString(tab));
            }
            for (Subset s : subset) {
                for (int row : s.getSubsetPosition()) {
                    removeCandidate(matrix, row, col, s.getSubsetNumber());
                }
                // break;
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
                group(subset, tab, size);

                if (subset.size() > 1) {
                    //System.out.println("__b_" + Arrays.toString(subset.toArray()));

                    System.out.println("__b_" + rowGroup + " " + colGroup + "_" + Arrays.toString(tab));

                }

                for (Subset s : subset) {


                    for (int pos : s.getSubsetPosition()) {
                        int row = rowGroup * BLOCK_SIZE + (pos / 3);
                        int col = colGroup * BLOCK_SIZE + pos % 3;
                        removeCandidate(matrix, row, col, s.getSubsetNumber());
                    }
                    //  break;

                }
            }
        }
    }

    protected void group(List<Subset> result, Integer[][] tab, int subsetSize) {
        /** Set of proposal subset, each of them with the same size **/
        List<List<Integer>> subsets = Utils.combinationList(Utils.FULL_LIST, subsetSize);

        /** Subset is a unique list of number staring from 1 with declared size (2..4) **/
        for (List<Integer> subset : subsets) {
            CounterHashMap<Integer> counterMap = new CounterHashMap<>();
            boolean[] flags = new boolean[subsetSize];
            int id = 0;
            /** Iterates through subset */
            for (int item : subset) {
                Integer[] positions = tab[item - 1];

                int c = 0;
                for (Integer pos : positions) {
                    if (pos != null) {
                        c++;
                    }
                }
                if (c >= 2) {
                    for (Integer pos : positions) {
                        if (pos != null) {
                            counterMap.inc(pos);
                            flags[id] = true;
                        }
                    }
                    id++;
                }
            }

            if (counterMap.size() == subsetSize) {
                boolean found = !counterMap.isEmpty();
                for (int i : counterMap.values()) {
                    if (i < 2) {
                        found = false;
                        break;
                    }
                }
                for (boolean elem : flags) {
                    if (!elem) {
                        found = false;
                        break;
                    }
                }

                if (found) {
                    result.add(new Subset(subset, new ArrayList<>(counterMap.keySet())));
                }
            }
        }
    }

    /**
     * Removes candidates in selected cell by row and col which are not included in subset list.
     *
     * @param matrix
     * @param row
     * @param col
     * @param subset
     */
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
            }
        }
    }

    protected class Subset {

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
            return "Subset{subsetNumber=" + subsetNumber + ", subsetPosition=" + subsetPosition + "}";
        }
    }

}
