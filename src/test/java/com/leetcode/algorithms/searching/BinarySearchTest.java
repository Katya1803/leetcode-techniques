package com.leetcode.algorithms.searching;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTest {

    private BinarySearch binarySearch;

    @BeforeEach
    void setUp() {
        binarySearch = new BinarySearch();
    }

    @Test
    @DisplayName("Standard Binary Search")
    void testSearch() {
        // Given
        int[] nums = {-1, 0, 3, 5, 9, 12};

        // When & Then
        assertEquals(4, binarySearch.search(nums, 9));
        assertEquals(-1, binarySearch.search(nums, 2));
        assertEquals(0, binarySearch.search(nums, -1));
        assertEquals(5, binarySearch.search(nums, 12));
    }

    @Test
    @DisplayName("Binary Search - Empty Array")
    void testSearchEmptyArray() {
        // Given
        int[] nums = {};

        // When
        int result = binarySearch.search(nums, 5);

        // Then
        assertEquals(-1, result);
    }

    @Test
    @DisplayName("Binary Search - Single Element")
    void testSearchSingleElement() {
        // Given
        int[] nums = {5};

        // When & Then
        assertEquals(0, binarySearch.search(nums, 5));
        assertEquals(-1, binarySearch.search(nums, 3));
    }

    @Test
    @DisplayName("Find First Occurrence")
    void testFindFirst() {
        // Given
        int[] nums = {5, 7, 7, 8, 8, 10};

        // When & Then
        assertEquals(1, binarySearch.findFirst(nums, 7));
        assertEquals(3, binarySearch.findFirst(nums, 8));
        assertEquals(-1, binarySearch.findFirst(nums, 6));
    }

    @Test
    @DisplayName("Find Last Occurrence")
    void testFindLast() {
        // Given
        int[] nums = {5, 7, 7, 8, 8, 10};

        // When & Then
        assertEquals(2, binarySearch.findLast(nums, 7));
        assertEquals(4, binarySearch.findLast(nums, 8));
        assertEquals(-1, binarySearch.findLast(nums, 6));
    }

    @Test
    @DisplayName("Search Range")
    void testSearchRange() {
        // Given
        int[] nums = {5, 7, 7, 8, 8, 10};

        // When & Then
        assertArrayEquals(new int[]{1, 2}, binarySearch.searchRange(nums, 7));
        assertArrayEquals(new int[]{3, 4}, binarySearch.searchRange(nums, 8));
        assertArrayEquals(new int[]{-1, -1}, binarySearch.searchRange(nums, 6));
    }

    @Test
    @DisplayName("Find Peak Element")
    void testFindPeakElement() {
        // Given
        int[] nums1 = {1, 2, 3, 1};
        int[] nums2 = {1, 2, 1, 3, 5, 6, 4};

        // When
        int result1 = binarySearch.findPeakElement(nums1);
        int result2 = binarySearch.findPeakElement(nums2);

        // Then
        assertEquals(2, result1); // Peak at index 2 (value 3)
        assertTrue(result2 == 1 || result2 == 5); // Peak at index 1 or 5
    }

    @ParameterizedTest
    @CsvSource({
            "4, 2",
            "8, 2",
            "0, 0",
            "1, 1",
            "16, 4",
            "2147395599, 46339"
    })
    @DisplayName("Square Root - Multiple Cases")
    void testMySqrt(int input, int expected) {
        int result = binarySearch.mySqrt(input);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Find Minimum in Rotated Array")
    void testFindMin() {
        // Given
        int[] nums1 = {3, 4, 5, 1, 2};
        int[] nums2 = {4, 5, 6, 7, 0, 1, 2};
        int[] nums3 = {11, 13, 15, 17};

        // When & Then
        assertEquals(1, binarySearch.findMin(nums1));
        assertEquals(0, binarySearch.findMin(nums2));
        assertEquals(11, binarySearch.findMin(nums3));
    }
}