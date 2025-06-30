package com.leetcode.algorithms.sorting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {

    private MergeSort mergeSort;

    @BeforeEach
    void setUp() {
        mergeSort = new MergeSort();
    }

    @Test
    @DisplayName("Standard Merge Sort")
    void testMergeSort() {
        // Given
        int[] nums = {38, 27, 43, 3, 9, 82, 10};
        int[] expected = {3, 9, 10, 27, 38, 43, 82};

        // When
        mergeSort.mergeSort(nums);

        // Then
        assertArrayEquals(expected, nums);
    }

    @Test
    @DisplayName("Merge Sort - Already Sorted")
    void testMergeSortAlreadySorted() {
        // Given
        int[] nums = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};

        // When
        mergeSort.mergeSort(nums);

        // Then
        assertArrayEquals(expected, nums);
    }

    @Test
    @DisplayName("Merge Sort - Reverse Sorted")
    void testMergeSortReverseSorted() {
        // Given
        int[] nums = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};

        // When
        mergeSort.mergeSort(nums);

        // Then
        assertArrayEquals(expected, nums);
    }

    @Test
    @DisplayName("Merge Sort - Single Element")
    void testMergeSortSingleElement() {
        // Given
        int[] nums = {42};
        int[] expected = {42};

        // When
        mergeSort.mergeSort(nums);

        // Then
        assertArrayEquals(expected, nums);
    }

    @Test
    @DisplayName("Merge Sort - Empty Array")
    void testMergeSortEmpty() {
        // Given
        int[] nums = {};
        int[] expected = {};

        // When
        mergeSort.mergeSort(nums);

        // Then
        assertArrayEquals(expected, nums);
    }

    @Test
    @DisplayName("Count Inversions")
    void testCountInversions() {
        // Given
        int[] nums1 = {2, 3, 8, 6, 1};
        int[] nums2 = {1, 2, 3, 4, 5};
        int[] nums3 = {5, 4, 3, 2, 1};

        // When & Then
        assertEquals(5, mergeSort.countInversions(nums1.clone()));
        assertEquals(0, mergeSort.countInversions(nums2.clone()));
        assertEquals(10, mergeSort.countInversions(nums3.clone()));
    }

    @Test
    @DisplayName("Merge K Sorted Arrays")
    void testMergeKSortedArrays() {
        // Given
        int[][] arrays = {
                {1, 4, 5},
                {1, 3, 4},
                {2, 6}
        };
        int[] expected = {1, 1, 2, 3, 4, 4, 5, 6};

        // When
        int[] result = mergeSort.mergeKSortedArrays(arrays);

        // Then
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Merge K Sorted Arrays - Single Array")
    void testMergeKSortedArraysSingle() {
        // Given
        int[][] arrays = {{1, 2, 3}};
        int[] expected = {1, 2, 3};

        // When
        int[] result = mergeSort.mergeKSortedArrays(arrays);

        // Then
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Merge K Sorted Arrays - Empty")
    void testMergeKSortedArraysEmpty() {
        // Given
        int[][] arrays = {};
        int[] expected = {};

        // When
        int[] result = mergeSort.mergeKSortedArrays(arrays);

        // Then
        assertArrayEquals(expected, result);
    }
}
