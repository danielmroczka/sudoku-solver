package com.labs.dm.sudoku.solver.alg;

import java.lang.annotation.*;

/**
 * Marks an {@link IAlgorithm} implementation as disabled.
 * Classes annotated with {@code @Disabled} are silently skipped by
 * {@link com.labs.dm.sudoku.solver.executors.Executor#run} without throwing
 * any exception. Use this to exclude experimental or incomplete algorithms
 * from the solving flow without removing them from the codebase.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Disabled {

    /**
     * Optional reason explaining why the algorithm is disabled.
     */
    String reason() default "";
}

