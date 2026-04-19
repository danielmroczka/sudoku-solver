package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import com.labs.dm.sudoku.solver.core.Pair;
import com.labs.dm.sudoku.solver.utils.Utils;

import java.util.*;

/**
 * Created by Daniel Mroczka on 4/12/2016.
 * <p>
 * http://www.sadmansoftware.com/sudoku/colouring.php
 */
public class Colouring implements IAlgorithm {
    @Override
    public void execute(IMatrix matrix) {
        if (matrix == null) {
            return;
        }

        for (int candidate = Matrix.MIN_VALUE; candidate <= Matrix.MAX_VALUE; candidate++) {
            Map<Pair, Set<Pair>> graph = buildStrongLinkGraph(matrix, candidate);
            Set<Pair> visited = new HashSet<>();

            for (Pair start : graph.keySet()) {
                if (visited.contains(start)) {
                    continue;
                }

                Map<Pair, Integer> colours = colourComponent(start, graph, visited);
                if (colours.size() > 1) {
                    eliminateSeenByBothColours(matrix, candidate, colours);
                }
            }
        }
    }

    private Map<Pair, Set<Pair>> buildStrongLinkGraph(IMatrix matrix, int candidate) {
        Map<Pair, Set<Pair>> graph = new HashMap<>();

        for (int row = 0; row < Matrix.SIZE; row++) {
            List<Pair> positions = candidatePositionsInRow(matrix, row, candidate);
            addStrongLink(graph, positions);
        }

        for (int col = 0; col < Matrix.SIZE; col++) {
            List<Pair> positions = candidatePositionsInCol(matrix, col, candidate);
            addStrongLink(graph, positions);
        }

        for (int rowBlock = 0; rowBlock < Matrix.BLOCK_SIZE; rowBlock++) {
            for (int colBlock = 0; colBlock < Matrix.BLOCK_SIZE; colBlock++) {
                List<Pair> positions = candidatePositionsInBlock(matrix, rowBlock, colBlock, candidate);
                addStrongLink(graph, positions);
            }
        }

        return graph;
    }

    private void addStrongLink(Map<Pair, Set<Pair>> graph, List<Pair> positions) {
        if (positions.size() != 2) {
            return;
        }

        Pair first = positions.get(0);
        Pair second = positions.get(1);
        graph.computeIfAbsent(first, ignored -> new HashSet<>()).add(second);
        graph.computeIfAbsent(second, ignored -> new HashSet<>()).add(first);
    }

    private List<Pair> candidatePositionsInRow(IMatrix matrix, int row, int candidate) {
        List<Pair> positions = new ArrayList<>();
        for (int col = 0; col < Matrix.SIZE; col++) {
            if (matrix.getCandidates(row, col).contains(candidate)) {
                positions.add(new Pair(row, col));
            }
        }
        return positions;
    }

    private List<Pair> candidatePositionsInCol(IMatrix matrix, int col, int candidate) {
        List<Pair> positions = new ArrayList<>();
        for (int row = 0; row < Matrix.SIZE; row++) {
            if (matrix.getCandidates(row, col).contains(candidate)) {
                positions.add(new Pair(row, col));
            }
        }
        return positions;
    }

    private List<Pair> candidatePositionsInBlock(IMatrix matrix, int rowBlock, int colBlock, int candidate) {
        List<Pair> positions = new ArrayList<>();
        for (int row : Utils.blockElems(rowBlock * Matrix.BLOCK_SIZE)) {
            for (int col : Utils.blockElems(colBlock * Matrix.BLOCK_SIZE)) {
                if (matrix.getCandidates(row, col).contains(candidate)) {
                    positions.add(new Pair(row, col));
                }
            }
        }
        return positions;
    }

    private Map<Pair, Integer> colourComponent(Pair start, Map<Pair, Set<Pair>> graph, Set<Pair> visited) {
        Map<Pair, Integer> colours = new HashMap<>();
        Queue<Pair> queue = new LinkedList<>();
        queue.add(start);
        colours.put(start, 0);
        visited.add(start);

        while (!queue.isEmpty()) {
            Pair current = queue.poll();
            int currentColour = colours.get(current);
            for (Pair next : graph.getOrDefault(current, Collections.emptySet())) {
                if (!colours.containsKey(next)) {
                    colours.put(next, 1 - currentColour);
                    queue.add(next);
                    visited.add(next);
                }
            }
        }

        return colours;
    }

    private void eliminateSeenByBothColours(IMatrix matrix, int candidate, Map<Pair, Integer> colours) {
        List<Pair> colourA = new ArrayList<>();
        List<Pair> colourB = new ArrayList<>();

        for (Map.Entry<Pair, Integer> entry : colours.entrySet()) {
            if (entry.getValue() == 0) {
                colourA.add(entry.getKey());
            } else {
                colourB.add(entry.getKey());
            }
        }

        for (int row = 0; row < Matrix.SIZE; row++) {
            for (int col = 0; col < Matrix.SIZE; col++) {
                Pair current = new Pair(row, col);
                if (colours.containsKey(current) || !matrix.getCandidates(row, col).contains(candidate)) {
                    continue;
                }
                if (canSeeAny(current, colourA) && canSeeAny(current, colourB)) {
                    matrix.removeCandidate(row, col, candidate);
                }
            }
        }
    }

    private boolean canSeeAny(Pair source, List<Pair> cells) {
        for (Pair candidate : cells) {
            if (arePeers(source, candidate)) {
                return true;
            }
        }
        return false;
    }

    private boolean arePeers(Pair first, Pair second) {
        if (first.equals(second)) {
            return false;
        }
        return first.row() == second.row()
                || first.col() == second.col()
                || (first.row() / Matrix.BLOCK_SIZE == second.row() / Matrix.BLOCK_SIZE
                && first.col() / Matrix.BLOCK_SIZE == second.col() / Matrix.BLOCK_SIZE);
    }
}
