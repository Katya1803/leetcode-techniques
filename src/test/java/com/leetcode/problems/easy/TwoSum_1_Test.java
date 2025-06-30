package com.leetcode.problems.easy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TwoSum_1_Test {

    private TwoSum_1 solution;

    @BeforeEach
    void setUp() {
        solution = new TwoSum_1();
    }

    @ParameterizedTest
    @MethodSource("provideTwoSumTestCases")
    @DisplayName("Two Sum - Hash Map Solution")
    void testTwoSum(int[] nums, int target, int[] expected) {
        int[] result = solution.twoSum(nums, target);

        // Verify result is valid
        assertEquals(2, result.length);
        assertTrue(result[0] != result[1]);
        assertEquals(target, nums[result[0]] + nums[result[1]]);

        // Check if result matches expected (order might differ)
        boolean matches = Arrays.equals(result, expected) ||
                Arrays.equals(result, new int[]{expected[1], expected[0]});
        assertTrue(matches,
                String.format("Expected %s but got %s", Arrays.toString(expected), Arrays.toString(result)));
    }

    @ParameterizedTest
    @MethodSource("provideTwoSumTestCases")
    @DisplayName("Two Sum - Brute Force Solution")
    void testTwoSumBruteForce(int[] nums, int target, int[] expected) {
        int[] result = solution.twoSumBruteForce(nums, target);

        assertEquals(2, result.length);
        assertTrue(result[0] != result[1]);
        assertEquals(target, nums[result[0]] + nums[result[1]]);

        boolean matches = Arrays.equals(result, expected) ||
                Arrays.equals(result, new int[]{expected[1], expected[0]});
        assertTrue(matches);
    }

    @ParameterizedTest
    @MethodSource("provideTwoSumTestCases")
    @DisplayName("Two Sum - Two Pointers Solution")
    void testTwoSumTwoPointers(int[] nums, int target, int[] expected) {
        int[] result = solution.twoSumTwoPointers(nums, target);

        assertEquals(2, result.length);
        assertTrue(result[0] != result[1]);
        assertEquals(target, nums[result[0]] + nums[result[1]]);

        boolean matches = Arrays.equals(result, expected) ||
                Arrays.equals(result, new int[]{expected[1], expected[0]});
        assertTrue(matches);
    }

    @Test
    @DisplayName("Two Sum - No Solution Exception")
    void testNoSolutionThrowsException() {
        int[] nums = {1, 2, 3};
        int target = 10;

        assertThrows(IllegalArgumentException.class, () -> solution.twoSum(nums, target));
        assertThrows(IllegalArgumentException.class, () -> solution.twoSumBruteForce(nums, target));
        assertThrows(IllegalArgumentException.class, () -> solution.twoSumTwoPointers(nums, target));
    }

    @Test
    @DisplayName("Two Sum - Edge Cases")
    void testEdgeCases() {
        // Minimum array size
        int[] minArray = {1, 2};
        int[] result = solution.twoSum(minArray, 3);
        assertArrayEquals(new int[]{0, 1}, result);

        // Negative numbers
        int[] negatives = {-3, 4, 3, 90};
        result = solution.twoSum(negatives, 0);
        assertEquals(0, negatives[result[0]] + negatives[result[1]]);

        // Same number twice
        int[] duplicates = {3, 3};
        result = solution.twoSum(duplicates, 6);
        assertArrayEquals(new int[]{0, 1}, result);
    }

    @Test
    @DisplayName("Performance Comparison")
    void testPerformanceComparison() {
        // Large array for performance testing
        int[] largeArray = new int[1000];
        for (int i = 0; i < 1000; i++) {
            largeArray[i] = i;
        }
        int target = 999; // indices 0 + 999

        // All solutions should work
        long start = System.nanoTime();
        int[] result1 = solution.twoSum(largeArray, target);
        long hashMapTime = System.nanoTime() - start;

        start = System.nanoTime();
        int[] result2 = solution.twoSumTwoPointers(largeArray, target);
        long twoPointersTime = System.nanoTime() - start;

        // Verify both solutions are correct
        assertEquals(target, largeArray[result1[0]] + largeArray[result1[1]]);
        assertEquals(target, largeArray[result2[0]] + largeArray[result2[1]]);

        // Hash map should be faster for large arrays
        assertTrue(hashMapTime < twoPointersTime * 10, // Allow some variance
                String.format("HashMap: %d ns, TwoPointers: %d ns", hashMapTime, twoPointersTime));
    }

    static Stream<Arguments> provideTwoSumTestCases() {
        return Stream.of(
                Arguments.of(new int[]{2, 7, 11, 15}, 9, new int[]{0, 1}),
                Arguments.of(new int[]{3, 2, 4}, 6, new int[]{1, 2}),
                Arguments.of(new int[]{3, 3}, 6, new int[]{0, 1}),
                Arguments.of(new int[]{-1, -2, -3, -4, -5}, -8, new int[]{2, 4}),
                Arguments.of(new int[]{0, 4, 3, 0}, 0, new int[]{0, 3}),
                Arguments.of(new int[]{1, 3, 7, 9, 2}, 11, new int[]{2, 4})
        );
    }
}