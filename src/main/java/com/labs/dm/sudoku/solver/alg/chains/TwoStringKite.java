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
 * Detects a Two-String Kite from row and column strong links for one digit
 * and removes that digit from cells that see both kite endpoints.
 */
public class TwoStringKite implements IAlgorithm {

    @Override
    public void execute(IMatrix matrix) {
        for (int candidate = Matrix.MIN_VALUE; candidate <= Matrix.MAX_VALUE; candidate++) {
            List<Link> rowLinks = collectRowLinks(matrix, candidate);
            List<Link> colLinks = collectColLinks(matrix, candidate);

            for (Link rowLink : rowLinks) {
                for (Link colLink : colLinks) {
                    applyKite(matrix, candidate, rowLink, colLink);
                }
            }
        }
    }

    private void applyKite(IMatrix matrix, int candidate, Link rowLink, Link colLink) {
        Pair[] rowEnds = new Pair[]{rowLink.first, rowLink.second};
        Pair[] colEnds = new Pair[]{colLink.first, colLink.second};

        for (int rowIndex = 0; rowIndex < rowEnds.length; rowIndex++) {
            for (int colIndex = 0; colIndex < colEnds.length; colIndex++) {
                Pair rowPivot = rowEnds[rowIndex];
                Pair colPivot = colEnds[colIndex];
                Pair rowEndpoint = rowEnds[1 - rowIndex];
                Pair colEndpoint = colEnds[1 - colIndex];

                if (!theSameBlock(rowPivot, colPivot)) {
                    continue;
                }
                // Degenerate: same pivot cell shared by both links — not a valid kite
                if (rowPivot.equals(colPivot)) {
                    continue;
                }
                // The row endpoint must not be in the same column as the col link,
                // and the col endpoint must not be in the same row as the row link
                if (rowEndpoint.col() == colPivot.col() || colEndpoint.row() == rowPivot.row()) {
                    continue;
                }

                Set<Pair> excluded = new HashSet<>(List.of(rowLink.first, rowLink.second, colLink.first, colLink.second));
                removeFromCommonPeers(matrix, candidate, rowEndpoint, colEndpoint, excluded);
            }
        }
    }

    private List<Link> collectRowLinks(IMatrix matrix, int candidate) {
        List<Link> links = new ArrayList<>();
        for (int row = 0; row < Matrix.SIZE; row++) {
            if (matrix.candidatesCountInRow(row, candidate) != 2) {
                continue;
            }
            List<Pair> positions = new ArrayList<>(2);
            for (int col = 0; col < Matrix.SIZE; col++) {
                if (matrix.getCandidates(row, col).contains(candidate)) {
                    positions.add(new Pair(row, col));
                }
            }
            if (positions.size() == 2) {
                links.add(new Link(positions.get(0), positions.get(1)));
            }
        }
        return links;
    }

    private List<Link> collectColLinks(IMatrix matrix, int candidate) {
        List<Link> links = new ArrayList<>();
        for (int col = 0; col < Matrix.SIZE; col++) {
            if (matrix.candidatesCountInCol(col, candidate) != 2) {
                continue;
            }
            List<Pair> positions = new ArrayList<>(2);
            for (int row = 0; row < Matrix.SIZE; row++) {
                if (matrix.getCandidates(row, col).contains(candidate)) {
                    positions.add(new Pair(row, col));
                }
            }
            if (positions.size() == 2) {
                links.add(new Link(positions.get(0), positions.get(1)));
            }
        }
        return links;
    }

    private void removeFromCommonPeers(IMatrix matrix, int candidate, Pair rowEndpoint, Pair colEndpoint, Set<Pair> excluded) {
        // The only valid elimination cell is at the intersection of:
        //   - the row of the column-link's non-pivot end
        //   - the column of the row-link's non-pivot end
        int targetRow = colEndpoint.row();
        int targetCol = rowEndpoint.col();
        Pair target = new Pair(targetRow, targetCol);

        if (excluded.contains(target)) {
            return;
        }
        if (matrix.getCandidates(targetRow, targetCol).contains(candidate)) {
            matrix.removeCandidate(targetRow, targetCol, candidate);
        }
    }

    private boolean theSameBlock(Pair first, Pair second) {
        return first.row() / Matrix.BLOCK_SIZE == second.row() / Matrix.BLOCK_SIZE
                && first.col() / Matrix.BLOCK_SIZE == second.col() / Matrix.BLOCK_SIZE;
    }

    private record Link(Pair first, Pair second) {
    }
}
