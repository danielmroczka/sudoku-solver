package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.utils.CounterHashMap;

import java.util.ArrayList;
import java.util.List;

import static com.labs.dm.sudoku.solver.core.IMatrix.BLOCK_SIZE;
import static com.labs.dm.sudoku.solver.core.IMatrix.SIZE;
import static com.labs.dm.sudoku.solver.utils.Utils.*;

/**
 * Created by Daniel Mroczka on 2016-03-21.
 */
public abstract class HiddenSubSet implements IAlgorithm {

    protected int size;
    protected int minSize = 2;

    @Override
    public void execute(IMatrix matrix) {
        findHiddenPairsInRows(matrix);
        findHiddenPairsInCols(matrix);
        findHiddenPairsInBlocks(matrix);
    }

    protected void findHiddenPairsInRows(IMatrix matrix) {
        for (int row = 0; row < SIZE; row++) {
            List<Subset> subset = new ArrayList<>();
            Integer[][] tab = fillTabRows(matrix, row);
            group(subset, tab, size);

            for (Subset s : subset) {
                for (int col : s.getSubsetPosition()) {
                    removeCandidate(matrix, row, col, s.getSubsetNumber());
                }
            }
        }
    }

    protected void findHiddenPairsInCols(IMatrix matrix) {
        for (int col = 0; col < SIZE; col++) {
            List<Subset> subset = new ArrayList<>();
            Integer[][] tab = fillTabCols(matrix, col);
            group(subset, tab, size);

            for (Subset s : subset) {
                for (int row : s.getSubsetPosition()) {
                    removeCandidate(matrix, row, col, s.getSubsetNumber());
                }
            }
        }
    }

    protected void findHiddenPairsInBlocks(IMatrix matrix) {
        for (int rowGroup : blockElems(0)) {
            for (int colGroup : blockElems(0)) {
                List<Subset> subsets = new ArrayList<>();
                Integer[][] tab = fillTabBlock(matrix, rowGroup, colGroup);
                group(subsets, tab, size);

                for (Subset subset : subsets) {
                    for (int pos : subset.getSubsetPosition()) {
                        int row = rowGroup * BLOCK_SIZE + pos / 3;
                        int col = colGroup * BLOCK_SIZE + pos % 3;
                        removeCandidate(matrix, row, col, subset.getSubsetNumber());
                    }
                }
            }
        }
    }

    protected Integer[][] fillTabRows(IMatrix matrix, int row) {
        Integer[][] tab = new Integer[SIZE][SIZE];
        for (int col = 0; col < SIZE; col++) {
            for (int candidate : matrix.getCandidates(row, col)) {
                tab[candidate - 1][col] = col;
            }
        }
        return tab;
    }

    protected Integer[][] fillTabCols(IMatrix matrix, int col) {
        Integer[][] tab = new Integer[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int candidate : matrix.getCandidates(row, col)) {
                tab[candidate - 1][row] = row;
            }
        }
        return tab;
    }

    protected Integer[][] fillTabBlock(IMatrix matrix, int rowGroup, int colGroup) {
        Integer[][] tab = new Integer[SIZE][SIZE];
        for (int row : blockElems(rowGroup)) {
            for (int col : blockElems(colGroup)) {
                for (int candidate : matrix.getCandidates(row, col)) {
                    int pos = (row % 3) * 3 + col % 3;
                    tab[candidate - 1][pos] = pos;
                }
            }
        }
        return tab;
    }

    protected void group(List<Subset> result, Integer[][] tab, int subsetSize) {
        /** Set of proposal subset, each of them with the same size **/
        List<List<Integer>> subsets = combinationList(FULL_LIST, subsetSize);

        /** Subset is a unique list of number staring from 1 with declared size (2..4) **/
        for (List<Integer> subset : subsets) {
            CounterHashMap<Integer> counterMap = new CounterHashMap<>();
            /** Iterates through subset **/
            boolean found = true;
            for (int subsetItem : subset) {
                /** array of indices **/
                Integer[] positions = tab[subsetItem - 1];
                int selectedItems = 0;

                for (int pos = 0; pos < positions.length; pos++) {
                    if (positions[pos] != null) {
                        counterMap.inc(pos);
                        selectedItems++;
                    }
                }

                if (selectedItems < minSize) {
                    found = false;
                    break;
                }
            }

            if (found && counterMap.size() == subsetSize) {
                for (int value : counterMap.values()) {
                    if (value < minSize && value > subsetSize) {
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
        if (matrix.isCellSet(row, col) || matrix.getCandidates(row, col).isEmpty()) {
            return;
        }
        //TODO: Rewrite this part to make easier
        List<Integer> common = new ArrayList<>(matrix.getCandidates(row, col));
        common.retainAll(subset);
        if (common.size() >= minSize) {
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
