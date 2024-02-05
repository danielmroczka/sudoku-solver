package com.labs.dm.sudoku.solver.utils;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Matrix;
import com.labs.dm.sudoku.solver.core.Pair;
import com.labs.dm.sudoku.solver.io.MatrixLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.logging.Logger;

import static com.labs.dm.sudoku.solver.core.Matrix.BLOCK_SIZE;
import static com.labs.dm.sudoku.solver.core.Matrix.SIZE;

/**
 * Created by Daniel Mroczka on 2016-02-13.
 */
public class Utils {

    public static final List<Integer> FULL_LIST = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    private Utils() {
    }

    /**
     * Returns factorial value
     * For example factorial(4) = 1 * 2 * 3 * 4
     *
     * @param val
     * @return
     */
    public static int factorial(int val) {
        if (val < 0) {
            throw new IllegalArgumentException();
        }
        int res = 1;
        for (int i = 2; i <= val; i++) {
            res = res * i;
        }

        return res;
    }

    public static int combination(int n, int k) {
        return factorial(n) / (factorial(k) * factorial(n - k));
    }

    /**
     * Return unique combination of k-length elements from n-length list of unique numbers
     *
     * @param numbers
     * @param k
     * @return
     */
    public static List<List<Integer>> combinationList(List<Integer> numbers, int k) {
        List<List<Integer>> res = new ArrayList<>();
        combinationList(numbers, k, 0, new Integer[k], res);
        return res;
    }

    private static void combinationList(List<Integer> arr, int len, int startPosition, Integer[] result, List<List<Integer>> list) {
        if (len == 0) {
            list.add(Arrays.asList(Arrays.copyOf(result, result.length)));
            return;
        }
        for (int i = startPosition; i <= arr.size() - len; i++) {
            result[result.length - len] = arr.get(i);
            combinationList(arr, len - 1, i + 1, result, list);
        }
    }

    public static boolean theSameBlock(List<Integer> list) {
        return theSameBlock(list.toArray(new Integer[0]));
    }

    public static boolean theSameBlock(Integer... val) {
        int v = val[0] / 3;
        boolean res = true;
        for (int i : val) {
            res = res && i / 3 == v;
            v = i / 3;
        }
        return res;
    }

    private static boolean theSameRowBlock(Pair cell1, Pair cell2) {
        return cell1.row() / 3 == cell2.row() / 3;
    }

    private static boolean theSameColBlock(Pair cell1, Pair cell2) {
        return cell1.col() / 3 == cell2.col() / 3;
    }

    private static boolean theSameRow(Pair cell1, Pair cell2) {
        return cell1.row() == cell2.row();
    }

    private static boolean theSameCol(Pair cell1, Pair cell2) {
        return cell1.col() == cell2.col();
    }

    public static List<Pair> pairsOnIntersections(Pair cell1, Pair cell2) {
        List<Pair> result = new ArrayList<>();
        if (theSameColBlock(cell1, cell2) && theSameRowBlock(cell1, cell2)) {
            for (int row : blockElems(cell1.row())) {
                for (int col : blockElems(cell1.col())) {
                    if ((row == cell1.row() && col == cell1.col()) || (row == cell2.row() && col == cell2.col())) {
                        continue;
                    }
                    result.add(new Pair(row, col));
                }

            }
        } else if (theSameRow(cell1, cell2)) {
            for (int col = 0; col < SIZE; col++) {
                if (col != cell1.col() && col != cell2.col()) {
                    result.add(new Pair(cell1.row(), col));
                }
            }
        } else if (theSameCol(cell1, cell2)) {
            for (int row = 0; row < SIZE; row++) {
                if (row != cell1.row() && row != cell2.row()) {
                    result.add(new Pair(row, cell1.col()));
                }
            }
        } else if (theSameRowBlock(cell1, cell2)) {
            for (int col : blockElems(cell1.col())) {
                result.add(new Pair(cell2.row(), col));
            }
            for (int col : blockElems(cell2.col())) {
                result.add(new Pair(cell1.row(), col));
            }

        } else if (theSameColBlock(cell1, cell2)) {
            for (int row : blockElems(cell1.row())) {
                result.add(new Pair(row, cell2.col()));
            }
            for (int row : blockElems(cell2.row())) {
                result.add(new Pair(row, cell1.col()));
            }

        } else {
            result.add(new Pair(cell1.row(), cell2.col()));
            result.add(new Pair(cell2.row(), cell1.col()));
        }

        return result;
    }

    /**
     * Returns position of block selected by start position parameter
     *
     * @param pos
     * @return
     */
    public static int[] blockElems(int pos) {
        int[] res = new int[BLOCK_SIZE];
        int id = 0;
        for (int item = pos / BLOCK_SIZE * BLOCK_SIZE; item < pos / BLOCK_SIZE * BLOCK_SIZE + BLOCK_SIZE; item++) {
            res[id++] = item;
        }
        return res;
    }

    public static boolean acceptPivotAndPincets(IMatrix matrix, Pair pivotPair, Pair pincetPair1, Pair pincetPair2, int size) {
        Collection<Integer> pivot = matrix.getCandidates(pivotPair);
        Collection<Integer> pincet1 = matrix.getCandidates(pincetPair1);
        Collection<Integer> pincet2 = matrix.getCandidates(pincetPair2);

        if (Arrays.equals(pincet1.toArray(), pincet2.toArray())) {
            return false;
        }

        if (pincet1.size() != 2 && pincet2.size() != 2) {
            return false;
        }
        if (new HashSet<>(pivot).size() != size || new HashSet<>(pincet1).size() != 2 || new HashSet<>(pincet2).size() != 2) {
            return false;
        }

        List<Integer> list = new ArrayList<>();
        list.addAll(pivot);
        list.addAll(pincet1);
        list.addAll(pincet2);

        if (new HashSet<>(list).size() != 3) {
            return false;
        }

        CounterHashMap<Integer> map = new CounterHashMap<>();
        for (int item : list) {
            map.inc(item);
        }
        boolean found = true;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() < 2) {
                found = false;
                break;
            }
        }

        List<Integer> copy1 = new ArrayList<>(pivot);
        List<Integer> copy2 = new ArrayList<>(pivot);
        copy1.retainAll(pincet1);
        copy2.retainAll(pincet2);

        return found && copy1.size() == (size - 1) && copy2.size() == (size - 1) && !Objects.equals(copy1.get(0), copy2.get(0)) && map.size() == 3;
    }

    public static boolean acceptPincet(Collection<Integer> pivot, Collection<Integer> pincet, int size) {
        if (pincet.size() != 2) {
            return false;
        }
        int cnt = 0;
        for (int i : pivot) {
            for (int j : pincet) {
                if (i == j) {
                    cnt++;
                }
            }
        }
        return cnt == size - 1;
    }

    public static void log(IMatrix matrix) {
        try {
            new MatrixLoader().save(matrix, "target\\matrix_" + new Date().getTime() + ".txt");
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    public static void logCandidates(String tag, IMatrix matrix) {
        File file;
        try {
            file = new File("target\\matrix_cand_" + tag + "_" + new Date().getTime() + ".txt");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(matrix.printCandidates().getBytes(Charset.defaultCharset()));
            }
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    public static String printMatrix(IMatrix matrix) {
        StringBuilder sb = new StringBuilder(100);
        if (matrix.isSolved()) {
            sb.append("Matrix Solved!");
        } else {
            sb.append("Solved cells = ").append(matrix.getSolvedItems()).append(", candidates = ").append(matrix.getCandidatesCount()).append(System.lineSeparator());
        }
        sb.append(System.lineSeparator());
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                sb.append(matrix.getValueAt(row, col));
                if (col < SIZE - 1) {
                    if (col % 3 == 2) {
                        sb.append(" | ");
                    } else {
                        sb.append(" ");
                    }
                }
            }
            sb.append(System.lineSeparator());
            if (row % 3 == 2 && row < SIZE - 1) {
                sb.append("------+-------+------").append(System.lineSeparator());
            }

        }
        return sb.toString();
    }

    public static String printCandidates(IMatrix matrix) {
        StringBuilder sb = new StringBuilder(100);
        boolean solved = matrix.isSolved();
        if (solved) {
            sb.append("Matrix Solved!");
        } else {
            sb.append("Solved cells = ").append(matrix.getSolvedItems()).append(", candidates = ").append(matrix.getCandidatesCount()).append(System.lineSeparator());
        }
        sb.append(System.lineSeparator());
        int maxLength = 0;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (matrix.getCandidates(row, col).size() > maxLength) {
                    maxLength = matrix.getCandidates(row, col).size();
                }
            }
        }

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                StringBuilder line = new StringBuilder();
                for (int item : matrix.getCandidates(row, col)) {
                    line.append(item);
                }
                if (line.length() == 0) {
                    line = new StringBuilder("[" + matrix.getValueAt(row, col) + "]");
                }

                sb.append(String.format("%1$" + (maxLength > 0 ? maxLength + 3 : 3) + "s", line));

                if (col < SIZE - 1) {
                    if (col % 3 == 2) {
                        sb.append(" |");
                    }
                    sb.append(" ");

                }
            }
            sb.append(System.lineSeparator());
            if (row % 3 == 2 && row < SIZE - 1) {
                sb.append("-".repeat(Math.max(0, (((maxLength > 0 ? maxLength + 3 : 3) + 1) * 9) + 4)));
                sb.append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    public static Map<Integer, List<Pair>> candidatesMap(IMatrix matrix) {
        Map<Integer, List<Pair>> map = new HashMap<>();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (matrix.getCandidates(row, col).size() == 1) {
                    // matrix.setValueWithCandidate(row, col);
                    //throw new IllegalStateException("Cells cannot be in state having only one candidate!");
                }
                for (int candidate : matrix.getCandidates(row, col)) {

                    List<Pair> list = map.get(candidate);
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(new Pair(row, col));
                    map.put(candidate, list);
                }
            }
        }

        return map;
    }

    public static List<Pair> getSurroundings(int row, int col) {
        List<Pair> res = new ArrayList<>(20);
        for (int r = 0; r < SIZE; r++) {
            if (r != row) {
                res.add(new Pair(r, col));
            }
        }
        for (int c = 0; c < SIZE; c++) {
            if (c != col) {
                res.add(new Pair(row, c));
            }
        }
        for (int rowGroup : Utils.blockElems(row)) {
            for (int colGroup : Utils.blockElems(col)) {
                if (rowGroup != row && colGroup != col) {
                    res.add(new Pair(rowGroup, colGroup));
                }
            }
        }

        return res;
    }

    public static void deepCopy(int[][] original, int[][] copy) {
        for (int row = 0; row < SIZE; row++) {
            System.arraycopy(original[row], 0, copy[row], 0, SIZE);
        }
    }

    public static int match(List<Integer> original, List<Integer> match) {
        List<Integer> list = new ArrayList<>(original);
        list.retainAll(match);
        return list.size();
    }

    public static List<List<Integer>> getCombinationList(int size, List<Integer> innerEntry) {
        List<List<Integer>> innerCombination = new ArrayList<>();
        for (int i = 2; i <= size; i++) {
            innerCombination.addAll(combinationList(innerEntry, i));
        }
        return innerCombination;
    }

    public static Map<Integer, List<Pair>> getOccurencesInRow(IMatrix matrix, int... rows) {
        Map<Integer, List<Pair>> map = new HashMap<>();

        for (int col = 0; col < Matrix.SIZE; col++) {
            for (int row : rows) {
                count(matrix, col, map, row);
            }
        }

        return map;
    }

    public static Map<Integer, List<Pair>> getOccurencesInCol(IMatrix matrix, int... cols) {
        Map<Integer, List<Pair>> map = new HashMap<>();

        for (int row = 0; row < Matrix.SIZE; row++) {
            for (int col : cols) {
                count(matrix, col, map, row);
            }
        }

        return map;
    }

    public static Map<Integer, List<Pair>> getOccurencesInBlock(IMatrix matrix, int rowGroup, int colGroup) {
        Map<Integer, List<Pair>> map = new HashMap<>();
        for (int row : Utils.blockElems(rowGroup * BLOCK_SIZE)) {
            for (int col : Utils.blockElems(colGroup * BLOCK_SIZE)) {
                count(matrix, col, map, row);
            }
        }
        return map;
    }

    private static void count(IMatrix matrix, int col, Map<Integer, List<Pair>> map, int row) {
        for (int candidate : matrix.getCandidates(row, col)) {
            List<Pair> list = map.get(candidate);
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(new Pair(row, col));
            map.put(candidate, list);
        }
    }
}
