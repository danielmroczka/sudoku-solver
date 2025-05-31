package com.labs.dm.sudoku.solver.alg.helpers;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Subset record
 */
class SubsetTest {

    @Test
    void testConstructorAndAccessors() {
        // Arrange
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        List<Integer> positions = Arrays.asList(4, 5, 6);

        // Act
        Subset subset = new Subset(numbers, positions);

        // Assert
        assertEquals(numbers, subset.subsetNumber());
        assertEquals(positions, subset.subsetPosition());
    }

    @Test
    void testToString() {
        // Arrange
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        List<Integer> positions = Arrays.asList(4, 5, 6);
        Subset subset = new Subset(numbers, positions);

        // Act
        String result = subset.toString();

        // Assert
        assertTrue(result.contains("subsetNumber=[1, 2, 3]"));
        assertTrue(result.contains("subsetPosition=[4, 5, 6]"));
    }

    @Test
    void testEquals() {
        // Arrange
        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> positions1 = Arrays.asList(4, 5, 6);
        Subset subset1 = new Subset(numbers1, positions1);

        List<Integer> numbers2 = Arrays.asList(1, 2, 3);
        List<Integer> positions2 = Arrays.asList(4, 5, 6);
        Subset subset2 = new Subset(numbers2, positions2);

        List<Integer> numbers3 = Arrays.asList(7, 8, 9);
        List<Integer> positions3 = Arrays.asList(4, 5, 6);
        Subset subset3 = new Subset(numbers3, positions3);

        // Assert
        assertEquals(subset1, subset2);
        assertNotEquals(subset1, subset3);
        assertNotEquals(subset1, null);
        assertNotEquals(subset1, "not a subset");
        assertEquals(subset1, subset1); // reflexivity
    }

    @Test
    void testHashCode() {
        // Arrange
        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> positions1 = Arrays.asList(4, 5, 6);
        Subset subset1 = new Subset(numbers1, positions1);

        List<Integer> numbers2 = Arrays.asList(1, 2, 3);
        List<Integer> positions2 = Arrays.asList(4, 5, 6);
        Subset subset2 = new Subset(numbers2, positions2);

        // Assert
        assertEquals(subset1.hashCode(), subset2.hashCode());
    }
}