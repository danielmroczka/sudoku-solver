package com.labs.dm.sudoku.solver.alg.helpers;

import java.util.List;

/**
 * Created by daniel on 2016-03-27.
 */
public class Subset {

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
