package com.labs.dm.sudoku.solver.utils;

import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.Pair;
import com.labs.dm.sudoku.solver.io.MatrixLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

import static com.labs.dm.sudoku.solver.core.IMatrix.BLOCK_SIZE;
import static com.labs.dm.sudoku.solver.core.IMatrix.SIZE;

/**
 * Created by daniel on 2016-02-13.
 */
public class Utils {

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
        return cell1.getRow() / 3 == cell2.getRow() / 3;
    }

    private static boolean theSameColBlock(Pair cell1, Pair cell2) {
        return cell1.getCol() / 3 == cell2.getCol() / 3;
    }

    private static boolean theSameRow(Pair cell1, Pair cell2) {
        return cell1.getRow() == cell2.getRow();
    }

    private static boolean theSameCol(Pair cell1, Pair cell2) {
        return cell1.getCol() == cell2.getCol();
    }

    public static List<Pair> pairsOnIntersections(Pair cell1, Pair cell2) {
        List<Pair> result = new ArrayList<>();
        if (theSameColBlock(cell1, cell2) && theSameRowBlock(cell1, cell2)) {
            for (int row : it(cell1.getRow())) {
                for (int col : it(cell1.getCol())) {
                    if ((row == cell1.getRow() && col == cell1.getCol()) || (row == cell2.getRow() && col == cell2.getCol())) {
                        continue;
                    }
                    result.add(new Pair(row, col));
                }

            }
        } else if (theSameRow(cell1, cell2)) {
            for (int col = 0; col < SIZE; col++) {
                if (col != cell1.getCol() && col != cell2.getCol()) {
                    result.add(new Pair(cell1.getRow(), col));
                }
            }
        } else if (theSameCol(cell1, cell2)) {
            for (int row = 0; row < SIZE; row++) {
                if (row != cell1.getRow() && row != cell2.getRow()) {
                    result.add(new Pair(row, cell1.getCol()));
                }
            }
        } else if (theSameRowBlock(cell1, cell2)) {
            for (int col : it(cell1.getCol())) {
                result.add(new Pair(cell2.getRow(), col));
            }
            for (int col : it(cell2.getCol())) {
                result.add(new Pair(cell1.getRow(), col));
            }

        } else if (theSameColBlock(cell1, cell2)) {
            for (int row : it(cell1.getRow())) {
                result.add(new Pair(row, cell2.getCol()));
            }
            for (int row : it(cell2.getRow())) {
                result.add(new Pair(row, cell1.getCol()));
            }

        } else {
            result.add(new Pair(cell1.getRow(), cell2.getCol()));
            result.add(new Pair(cell2.getRow(), cell1.getCol()));
        }

        return result;
    }

    public static int[] it(int pos) {
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
            e.printStackTrace();
        }
    }

    public static void logCandidates(String tag, IMatrix matrix) {
        File file = new File("target\\matrix_cand_" + tag + "_" + new Date().getTime() + ".txt");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(matrix.printCandidates().getBytes(Charset.defaultCharset()));
            }
        } catch (IOException e) {
            e.printStackTrace();
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
                String line = "";
                for (int item : matrix.getCandidates(row, col)) {
                    line = line + item;
                }
                if (line.isEmpty()) {
                    line = "[" + matrix.getValueAt(row, col) + "]";
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
                for (int i = 0; i <= (((maxLength > 0 ? maxLength + 3 : 3) + 1) * 9) + 3; i++) {
                    sb.append("-");
                }
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
                    throw new IllegalStateException("Cells cannot be in state having only one candidate!");
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
        for (int rowGroup : Utils.it((row))) {
            for (int colGroup : Utils.it((col))) {
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

    public static List<List<Integer>> subset(List<List<Integer>> list, int size) {
        List<List<Integer>> map = new ArrayList<>();
        CounterHashMap<List<Integer>> counterMap = new CounterHashMap<>();
        for (List<Integer> item : list) {
            if (item.size() >= 2 && item.size() <= size) {
                counterMap.inc(item);
            }
        }

        /**
         * Finds following pattern:
         * 123, 123, 123 -> 123
         */
        for (Map.Entry<List<Integer>, Integer> entry : counterMap.entrySet()) {
            if (entry.getValue() == size) {
                for (int i = 0; i < size + 1; i++) {
                    map.add(entry.getKey());
                }
                return map;
            }
        }

        /**
         * Finds following pattern:
         * 24, 47, 27 -> 247
         */
        List<List<Integer>> listWithTwoLengthItems = new ArrayList<>();
        List<Integer> cc = new ArrayList<>();
        int i = 0;
        for (List<Integer> entry : list) {
            if (entry.size() >= 2 && entry.size() <= size - 1) {
                listWithTwoLengthItems.add(entry);
                cc.add(i++);
            }
        }

        List<List<Integer>> indexListCombination = Utils.combinationList(cc, 3);
        for (List<Integer> indexCombination : indexListCombination) {
            List<Integer> tmpList = new ArrayList<>();

            for (int idx : indexCombination) {
                tmpList.addAll(listWithTwoLengthItems.get(idx));
            }
            CounterHashMap<Integer> counter = new CounterHashMap();
            for (int item : tmpList) {
                counter.inc(item);
            }

            boolean status = true;
            for (int val : counter.values()) {
                if (val != 2) status = false;
            }

            if (status) {
                Set<Integer> set = new HashSet<>(tmpList);
                map.add(new ArrayList<Integer>(set));
                for (int idx : indexCombination) {
                    map.add(listWithTwoLengthItems.get(idx));
                }
                return map;
            }
        }


        /**
         * Finds following pattern:
         * 138, 18, 38 -> 138
         * 37, 57, 135, 1357 -> 1357
         *
         */
        for (List<Integer> entry : list) {
            if (entry.size() == size) {
                int cnt = 0;
                List<List<Integer>> combination = Utils.combinationList(entry, 2);

                for (List<Integer> item : list) {
                    if (!entry.equals(item) && item.size() >= 2 && item.size() <= size) {
                        for (List<Integer> elem : combination) {
                            if (item.equals(elem)) {
                                cnt++;
                                map.add(item);
                            }
                        }
                    }
                }

                if (cnt == 2) {
                    map.add(entry);
                    map.add(0, entry);
                    return map;
                }
            }
        }

        return map;
    }
}
