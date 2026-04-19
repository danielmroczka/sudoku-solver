package com.labs.dm.sudoku.solver.alg;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import com.labs.dm.sudoku.solver.core.Pair;

import java.util.*;

/**
 * Created by Daniel Mroczka on 4/12/2016.
 * <p>
 * http://www.sadmansoftware.com/sudoku/remotepairs.php
 * <p>
 * Eliminates both digits of a bivalue pair from cells that see opposite colours
 * in a remote-pairs chain built from the same two candidates.
 */
public class RemotePairs implements IAlgorithm {
    @Override
    public void execute(IMatrix matrix) {
        if (matrix == null) {
            return;
        }

        Map<List<Integer>, List<Pair>> groups = collectPairs(matrix);
        for (Map.Entry<List<Integer>, List<Pair>> entry : groups.entrySet()) {
            if (entry.getValue().size() < 2) {
                continue;
            }

            Map<Pair, Set<Pair>> graph = buildGraph(entry.getValue());
            Set<Pair> visited = new HashSet<>();

            for (Pair start : entry.getValue()) {
                if (visited.contains(start)) {
                    continue;
                }
                Map<Pair, Integer> colours = colourComponent(start, graph, visited);
                if (colours.size() > 1) {
                    eliminateFromOppositeVisibility(matrix, colours, entry.getKey());
                }
            }
        }
    }

    private Map<List<Integer>, List<Pair>> collectPairs(IMatrix matrix) {
        Map<List<Integer>, List<Pair>> map = new HashMap<>();
        for (int row = 0; row < Matrix.SIZE; row++) {
            for (int col = 0; col < Matrix.SIZE; col++) {
                if (matrix.isCellSet(row, col) || matrix.getCandidates(row, col).size() != 2) {
                    continue;
                }
                List<Integer> pair = new ArrayList<>(matrix.getCandidates(row, col));
                Collections.sort(pair);
                map.computeIfAbsent(pair, ignored -> new ArrayList<>()).add(new Pair(row, col));
            }
        }
        return map;
    }

    private Map<Pair, Set<Pair>> buildGraph(List<Pair> cells) {
        Map<Pair, Set<Pair>> graph = new HashMap<>();
        for (Pair cell : cells) {
            graph.put(cell, new HashSet<>());
        }

        for (int i = 0; i < cells.size(); i++) {
            for (int j = i + 1; j < cells.size(); j++) {
                Pair left = cells.get(i);
                Pair right = cells.get(j);
                if (arePeers(left, right)) {
                    graph.get(left).add(right);
                    graph.get(right).add(left);
                }
            }
        }

        return graph;
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

    private void eliminateFromOppositeVisibility(IMatrix matrix, Map<Pair, Integer> colours, List<Integer> pair) {
        List<Pair> colourA = new ArrayList<>();
        List<Pair> colourB = new ArrayList<>();

        for (Map.Entry<Pair, Integer> entry : colours.entrySet()) {
            if (entry.getValue() == 0) {
                colourA.add(entry.getKey());
            } else {
                colourB.add(entry.getKey());
            }
        }

        int left = pair.get(0);
        int right = pair.get(1);

        for (int row = 0; row < Matrix.SIZE; row++) {
            for (int col = 0; col < Matrix.SIZE; col++) {
                Pair current = new Pair(row, col);
                if (colours.containsKey(current)) {
                    continue;
                }
                List<Integer> candidates = matrix.getCandidates(row, col);
                if (!candidates.contains(left) && !candidates.contains(right)) {
                    continue;
                }
                if (canSeeAny(current, colourA) && canSeeAny(current, colourB)) {
                    matrix.removeCandidate(row, col, left);
                    matrix.removeCandidate(row, col, right);
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
                || (first.row() / 3 == second.row() / 3 && first.col() / 3 == second.col() / 3);
    }
}
