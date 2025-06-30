package com.leetcode.problems.hard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MedianOfTwoSortedArrays_4_Test {

    private MedianOfTwoSortedArrays_4 solution;
    private static final double DELTA = 1e-9; // For double comparison

    @BeforeEach
    void setUp() {
        solution = new MedianOfTwoSortedArrays_4();
    }

    @ParameterizedTest
    @MethodSource("provideMedianTestCases")
    @DisplayName("Median - Optimal Binary Search")
    void testFindMedianSortedArrays(int[] nums1, int[] nums2, double expected) {
        double result = solution.findMedianSortedArrays(nums1, nums2);
        assertEquals(expected, result, DELTA,
                String.format("Failed for nums1=%s, nums2=%s",
                        java.util.Arrays.toString(nums1),
                        java.util.Arrays.toString(nums2)));
    }

    @ParameterizedTest
    @MethodSource("provideMedianTestCases")
    @DisplayName("Median - Merge-based Solution")
    void testFindMedianSortedArraysMerge(int[] nums1, int[] nums2, double expected) {
        double result = solution.findMedianSortedArraysMerge(nums1, nums2);
        assertEquals(expected, result, DELTA);
    }

    @ParameterizedTest
    @MethodSource("provideMedianTestCases")
    @DisplayName("Median - Brute Force")
    void testFindMedianSortedArraysBruteForce(int[] nums1, int[] nums2, double expected) {
        double result = solution.findMedianSortedArraysBruteForce(nums1, nums2);
        assertEquals(expected, result, DELTA);
    }

    @ParameterizedTest
    @MethodSource("provideMedianTestCases")
    @DisplayName("Median - Range Search")
    void testFindMedianSortedArraysRangeSearch(int[] nums1, int[] nums2, double expected) {
        double result = solution.findMedianSortedArraysRangeSearch(nums1, nums2);
        assertEquals(expected, result, DELTA);
    }

    @ParameterizedTest
    @MethodSource("provideMedianTestCases")
    @DisplayName("Median - Recursive Binary Search")
    void testFindMedianSortedArraysRecursive(int[] nums1, int[] nums2, double expected) {
        double result = solution.findMedianSortedArraysRecursive(nums1, nums2);
        assertEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Edge Cases - Empty Arrays")
    void testEmptyArrays() {
        // One empty array
        assertEquals(2.0, solution.findMedianSortedArrays(new int[]{}, new int[]{2}), DELTA);
        assertEquals(2.5, solution.findMedianSortedArrays(new int[]{}, new int[]{2, 3}), DELTA);
        assertEquals(3.0, solution.findMedianSortedArrays(new int[]{3}, new int[]{}), DELTA);

        // Test robust version with null handling
        assertEquals(2.0, solution.findMedianSortedArraysRobust(null, new int[]{2}), DELTA);
        assertEquals(3.0, solution.findMedianSortedArraysRobust(new int[]{3}, null), DELTA);

        // Both empty should throw exception
        assertThrows(IllegalArgumentException.class,
                () -> solution.findMedianSortedArraysRobust(new int[]{}, new int[]{}));
    }

    @Test
    @DisplayName("Edge Cases - Single Elements")
    void testSingleElements() {
        assertEquals(1.5, solution.findMedianSortedArrays(new int[]{1}, new int[]{2}), DELTA);
        assertEquals(2.0, solution.findMedianSortedArrays(new int[]{2}, new int[]{2}), DELTA);
        assertEquals(1.5, solution.findMedianSortedArrays(new int[]{1}, new int[]{2}), DELTA);
    }

    @Test
    @DisplayName("Edge Cases - Different Sizes")
    void testDifferentSizes() {
        // Very different array sizes
        int[] small = {1};
        int[] large = {2, 3, 4, 5, 6, 7, 8};

        double result = solution.findMedianSortedArrays(small, large);
        assertEquals(4.5, result, DELTA);

        // Test symmetry (should give same result regardless of order)
        double resultReversed = solution.findMedianSortedArrays(large, small);
        assertEquals(result, resultReversed, DELTA);
    }

    @Test
    @DisplayName("Edge Cases - Negative Numbers")
    void testNegativeNumbers() {
        int[] nums1 = {-2, -1};
        int[] nums2 = {0, 1};
        assertEquals(-0.5, solution.findMedianSortedArrays(nums1, nums2), DELTA);

        int[] allNegative1 = {-5, -3, -1};
        int[] allNegative2 = {-4, -2};
        assertEquals(-3.0, solution.findMedianSortedArrays(allNegative1, allNegative2), DELTA);
    }

    @Test
    @DisplayName("Edge Cases - Large Numbers")
    void testLargeNumbers() {
        int[] nums1 = {1000000};
        int[] nums2 = {1000001};
        assertEquals(1000000.5, solution.findMedianSortedArrays(nums1, nums2), DELTA);

        // Test with Integer.MAX_VALUE and MIN_VALUE
        int[] extremes1 = {Integer.MIN_VALUE};
        int[] extremes2 = {Integer.MAX_VALUE};
        double expected = ((long)Integer.MIN_VALUE + Integer.MAX_VALUE) / 2.0;
        assertEquals(expected, solution.findMedianSortedArrays(extremes1, extremes2), DELTA);
    }

    @Test
    @DisplayName("Performance Comparison")
    void testPerformanceComparison() {
        // Generate large sorted arrays for performance testing
        int size1 = 1000, size2 = 2000;
        int[] large1 = new int[size1];
        int[] large2 = new int[size2];

        for (int i = 0; i < size1; i++) {
            large1[i] = i * 2; // Even numbers
        }
        for (int i = 0; i < size2; i++) {
            large2[i] = i * 2 + 1; // Odd numbers
        }

        long start, end;

        // Test optimal binary search
        start = System.nanoTime();
        double result1 = solution.findMedianSortedArrays(large1, large2);
        end = System.nanoTime();
        long binarySearchTime = end - start;

        // Test merge approach
        start = System.nanoTime();
        double result2 = solution.findMedianSortedArraysMerge(large1, large2);
        end = System.nanoTime();
        long mergeTime = end - start;

        // Results should be the same
        assertEquals(result1, result2, DELTA);

        // Binary search should be significantly faster for large inputs
        System.out.printf("Performance (ns) - Binary Search: %d, Merge: %d%n",
                binarySearchTime, mergeTime);

        // For large enough inputs, binary search should be faster
        assertTrue(binarySearchTime < mergeTime,
                "Binary search should be faster for large inputs");
    }

    @Test
    @DisplayName("Stress Test - Random Cases")
    void testRandomCases() {
        java.util.Random random = new java.util.Random(42); // Fixed seed for reproducibility

        for (int test = 0; test < 100; test++) {
            // Generate random sorted arrays
            int size1 = random.nextInt(20) + 1;
            int size2 = random.nextInt(20) + 1;

            int[] nums1 = generateSortedArray(size1, random);
            int[] nums2 = generateSortedArray(size2, random);

            // All methods should give same result
            double result1 = solution.findMedianSortedArrays(nums1, nums2);
            double result2 = solution.findMedianSortedArraysBruteForce(nums1, nums2);
            double result3 = solution.findMedianSortedArraysMerge(nums1, nums2);

            assertEquals(result2, result1, DELTA,
                    String.format("Binary search vs brute force mismatch on test %d", test));
            assertEquals(result2, result3, DELTA,
                    String.format("Merge vs brute force mismatch on test %d", test));
        }
    }

    @Test
    @DisplayName("Mathematical Properties")
    void testMathematicalProperties() {
        // Test symmetry property: median(A, B) = median(B, A)
        int[] nums1 = {1, 3, 5};
        int[] nums2 = {2, 4, 6};

        double result1 = solution.findMedianSortedArrays(nums1, nums2);
        double result2 = solution.findMedianSortedArrays(nums2, nums1);
        assertEquals(result1, result2, DELTA, "Median should be symmetric");

        // Test with identical arrays
        int[] identical1 = {1, 2, 3};
        int[] identical2 = {1, 2, 3};
        double identicalResult = solution.findMedianSortedArrays(identical1, identical2);
        assertEquals(2.0, identicalResult, DELTA);
    }

    @Test
    @DisplayName("Boundary Value Analysis")
    void testBoundaryValues() {
        // Test when all elements of one array are smaller
        int[] smaller = {1, 2, 3};
        int[] larger = {4, 5, 6};
        assertEquals(3.5, solution.findMedianSortedArrays(smaller, larger), DELTA);

        // Test when arrays are interleaved perfectly
        int[] evens = {2, 4, 6};
        int[] odds = {1, 3, 5};
        assertEquals(3.5, solution.findMedianSortedArrays(evens, odds), DELTA);
    }

    private int[] generateSortedArray(int size, java.util.Random random) {
        int[] array = new int[size];
        array[0] = random.nextInt(100) - 50; // Range [-50, 50)

        for (int i = 1; i < size; i++) {
            array[i] = array[i-1] + random.nextInt(10); // Ensure sorted
        }

        return array;
    }

    static Stream<Arguments> provideMedianTestCases() {
        return Stream.of(
                // Basic cases
                Arguments.of(new int[]{1, 3}, new int[]{2}, 2.0),
                Arguments.of(new int[]{1, 2}, new int[]{3, 4}, 2.5),

                // Empty array cases
                Arguments.of(new int[]{}, new int[]{1}, 1.0),
                Arguments.of(new int[]{2}, new int[]{}, 2.0),
                Arguments.of(new int[]{}, new int[]{2, 3}, 2.5),

                // Single element cases
                Arguments.of(new int[]{1}, new int[]{2}, 1.5),
                Arguments.of(new int[]{2}, new int[]{1}, 1.5),

                // Equal elements
                Arguments.of(new int[]{1, 1}, new int[]{1, 1}, 1.0),
                Arguments.of(new int[]{1, 2}, new int[]{1, 2}, 1.5),

                // Different sizes
                Arguments.of(new int[]{1}, new int[]{2, 3, 4}, 2.5),
                Arguments.of(new int[]{1, 2, 3, 4}, new int[]{5}, 3.0),

                // Negative numbers
                Arguments.of(new int[]{-2, -1}, new int[]{0, 1}, -0.5),
                Arguments.of(new int[]{-3, -1}, new int[]{-2}, -2.0),

                // Complex cases
                Arguments.of(new int[]{1, 3, 5, 7}, new int[]{2, 4, 6, 8}, 4.5),
                Arguments.of(new int[]{1, 2, 3}, new int[]{4, 5, 6, 7}, 4.0),
                Arguments.of(new int[]{1, 3, 8, 9, 15}, new int[]{7, 11, 18, 19, 21, 25}, 9.0)
        );
    }
}