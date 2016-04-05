package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Pair;
import com.labs.dm.sudoku.solver.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.labs.dm.sudoku.solver.core.Matrix.BLOCK_SIZE;
import static com.labs.dm.sudoku.solver.core.Matrix.SIZE;

/**
 * Created by Daniel Mroczka on 3/31/2016.
 * <p>
 * Locked Candidates Type1 - Pointing Pairs:
 * If in a block all candidates of a specific digit are confined to only one row or column that digit cannot appear
 * outside that block in the same row or column
 * <p>
 * All the candidates for digit X in a box are confined to a single line (row or column). The surplus candidates are
 * eliminated from the part of the line that does not intersect with this box.
 * <p>
 * .-------.-------.-------.
 * | * * * | * * * | X X X |
 * |       |       | - - - |
 * |       |       | - - - |
 * '-------'-------'-------'
 * <p>
 * Legend:
 * X : cell which may contain a candidate for digit X
 * - : cell which does not contain a candidate for digit X
 * * : cell from which we may eliminate the candidates for digit X
 * <p>
 * Locked Candidates Type2 - Claiming or Box-Line Reduction:
 * If in a row or column all candidates of specific digit are only in one box that digit can be eliminated from all
 * other cells in this block.
 * All the candidates for digit X in a line are confined to a single box. The surplus candidates are eliminated from
 * the part of the box that does not intersect with this line.
 * <p>
 * .-------.-------.-------.
 * | - - - | - - - | X X X |
 * |       |       | * * * |
 * |       |       | * * * |
 * '-------'-------'-------'
 * <p>
 * http://www.thonky.com/sudoku/pointing-pairs-triples/
 */
public class LockedCandidates implements IAlgorithm {

    @Override
    public void execute(IMatrix matrix) {
        pointingPairsInRows(matrix);
        pointingPairsInCols(matrix);
        claiming(matrix);
    }

    private void pointingPairsInRows(IMatrix matrix) {
        for (int row = 0; row < SIZE; row++) {
            Map<Integer, List<Pair>> inRow = Utils.getOccurencesInRow(matrix, row);
            Map<Integer, List<Pair>> inBlock;
            for (int col = 0; col < SIZE; col++) {

                for (int candidate : matrix.getCandidates(row, col)) {
                    if (inRow.get(candidate).size() == 2 || inRow.get(candidate).size() == 3) {
                        boolean theSameLine = true;
                        for (int i = 0; i < inRow.get(candidate).size() - 1; i++) {
                            if (inRow.get(candidate).get(i).getCol() / 3 != inRow.get(candidate).get(i + 1).getCol() / 3) {
                                theSameLine = false;
                                break;
                            }
                        }

                        removeCandidatesFromRow(candidate, row, col);
                    }

                    if (col % 3 == 0) {
                        inBlock = Utils.getOccurencesInBlock(matrix, row / 3, col / 3);

                        if (inBlock.get(candidate).size() == 2 || inBlock.get(candidate).size() == 3) {
                            boolean theSameRow = true, theSameCol = true;
                            for (int i = 0; i < inRow.get(candidate).size() - 1; i++) {
                                if (inRow.get(candidate).get(i).getCol() != inRow.get(candidate).get(i + 1).getCol()) {
                                    theSameCol = false;
                                }
                                if (inRow.get(candidate).get(i).getRow() != inRow.get(candidate).get(i + 1).getRow()) {
                                    theSameRow = false;
                                }
                            }
                        }

                    }
                }


            }

           /* for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                List<Integer> pos = getPosInRow(matrix, row, entry.getKey());
                if (!pos.isEmpty() && Utils.theSameBlock(pos)) {
                    removeInBlockRow(matrix, row, entry.getKey(), pos);
                }
            }*/
        }
    }

    private void pointingPairsInCols(IMatrix matrix) {

    }

    private void claiming(IMatrix matrix) {
        for (int rowGroup = 0; rowGroup < BLOCK_SIZE; rowGroup++) {
            for (int colGroup = 0; colGroup < BLOCK_SIZE; colGroup++) {
                Map<Integer, List<Pair>> map = Utils.getOccurencesInBlock(matrix, rowGroup, colGroup);
                for (Map.Entry<Integer, List<Pair>> entry : map.entrySet()) {
                    if (acceptSize(entry)) {
                        boolean confinedInRow = true, confinedInCol = true;
                        for (int i = 1; i < entry.getValue().size(); i++) {
                            if (entry.getValue().get(i - 1).getRow() != entry.getValue().get(i).getRow()) {
                                confinedInRow = false;
                            }
                            if (entry.getValue().get(i - 1).getCol() != entry.getValue().get(i).getCol()) {
                                confinedInCol = false;
                            }
                        }

                        claimingInRows(matrix, entry, confinedInRow);
                        claimingInCols(matrix, entry, confinedInCol);
                    }
                }
            }
        }
    }

    private boolean acceptSize(Map.Entry<Integer, List<Pair>> entry) {
        return entry.getValue().size() == 2 || entry.getValue().size() == 3;
    }

    private void claimingInCols(IMatrix matrix, Map.Entry<Integer, List<Pair>> entry, boolean confinedInCol) {
        if (confinedInCol) {
            for (int row = 0; row < SIZE; row++) {
                List<Integer> rows = new ArrayList<>();
                for (Pair p : entry.getValue()) {
                    rows.add(p.getCol());
                }
                if (!rows.contains(row)) {
                    int col = entry.getValue().get(0).getCol();
                    matrix.removeCandidate(row, col, entry.getKey());
                }
            }
        }
    }

    private void claimingInRows(IMatrix matrix, Map.Entry<Integer, List<Pair>> entry, boolean confinedInRow) {
        if (confinedInRow) {
            for (int col = 0; col < SIZE; col++) {
                List<Integer> cols = new ArrayList<>();
                for (Pair p : entry.getValue()) {
                    cols.add(p.getCol());
                }
                if (!cols.contains(col)) {
                    int row = entry.getValue().get(0).getRow();
                    matrix.removeCandidate(row, col, entry.getKey());
                }
            }
        }
    }

    private void claimingInCols(IMatrix matrix) {
    }

    private void removeCandidatesFromRow(int candidate, int row, int col) {
        //remove at least 6 candidates from row except in block in col
    }

}
