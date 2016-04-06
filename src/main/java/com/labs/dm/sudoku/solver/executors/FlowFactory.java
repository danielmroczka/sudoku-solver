package com.labs.dm.sudoku.solver.executors;

import com.labs.dm.sudoku.solver.alg.*;
import com.labs.dm.sudoku.solver.alg.fish.*;
import com.labs.dm.sudoku.solver.alg.hidden.HiddenPairs;
import com.labs.dm.sudoku.solver.alg.hidden.HiddenQuads;
import com.labs.dm.sudoku.solver.alg.hidden.HiddenSingles;
import com.labs.dm.sudoku.solver.alg.hidden.HiddenTriples;
import com.labs.dm.sudoku.solver.alg.naked.NakedPairs;
import com.labs.dm.sudoku.solver.alg.naked.NakedQuads;
import com.labs.dm.sudoku.solver.alg.naked.NakedSingles;
import com.labs.dm.sudoku.solver.alg.naked.NakedTriplets;
import com.labs.dm.sudoku.solver.core.IMatrix;
import com.labs.dm.sudoku.solver.core.listener.LogListener;
import com.labs.dm.sudoku.solver.io.MatrixLoader;

import java.io.IOException;
import java.util.*;

import static com.labs.dm.sudoku.solver.executors.Executor.run;

/**
 * Created by Daniel Mroczka on 2016-03-26.
 */
public class FlowFactory {

    private final Class<? extends IAlgorithm>[] classes = new Class[]{
            OpenSingles.class,
            NakedSingles.class,
            NakedPairs.class,
            NakedTriplets.class,
            NakedQuads.class,
            HiddenSingles.class,
            HiddenPairs.class,
            HiddenTriples.class,
            HiddenQuads.class,
            LockedCandidates.class,
            XWing.class,
            XYWing.class,
            XYZWing.class,
            SwordFish.class,
            JellyFish.class,
            ForcingChains.class
    };


    public static void main(String[] args) throws IOException {
        FlowFactory factory = new FlowFactory();
        MatrixLoader loader = new MatrixLoader();
        IMatrix matrix = loader.load("src/test/resources/patterns/hard/hard6.txt");
        matrix.addMatrixListener(new LogListener());
        factory.execute(matrix);
        System.out.println(matrix.getContext());
        int time = 0;
        for (ContextItem c : matrix.getContext()) {
            time += c.getTime();
        }
        System.out.println(time / 1000000);
    }

    private List<Class<? extends IAlgorithm>> list() {
        List<Class<? extends IAlgorithm>> result = new ArrayList<>();
        Random random = new Random();
        int range = random.nextInt(classes.length);
        Set<Integer> set = new LinkedHashSet<>();

        while (set.size() < range) {
            int pos = random.nextInt(classes.length);
            if (set.add(pos)) {
                result.add(classes[pos]);
            }
        }

        return result;
    }

    public void execute(IMatrix matrix) {
        int prevCount = matrix.getSolvedItems();
        int prevCandidates = matrix.getCandidatesCount();
        int chance = 3;
        run(matrix, GenerateCandidates.class);
        matrix.validate();
        while (!matrix.isSolved()) {
            List<Class<? extends IAlgorithm>> clz = list();
            for (Class<? extends IAlgorithm> c : clz) {
                run(matrix, c);
            }

            matrix.validate();
            if (prevCount == matrix.getSolvedItems() && prevCandidates == matrix.getCandidatesCount()) {
                chance--;
                if (chance == 0) {
                    break;
                }
            } else {
                chance = 3;
            }
            prevCount = matrix.getSolvedItems();
            prevCandidates = matrix.getCandidatesCount();
        }

        matrix.validate();
    }
}
