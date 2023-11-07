package com.labs.dm.sudoku.solver.alg.helpers;

import java.util.List;

/**
 * Created by daniel on 2016-03-27.
 */
public record Subset(List<Integer> subsetNumber, List<Integer> subsetPosition) {

    @Override
    public String toString() {
        return "Subset{subsetNumber=" + subsetNumber + ", subsetPosition=" + subsetPosition + "}";
    }
}
