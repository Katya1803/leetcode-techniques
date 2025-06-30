package com.leetcode.algorithms.searching;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;


import static org.junit.jupiter.api.Assertions.*;

class SearchInRotatedArrayTest {

    private SearchInRotatedArray searchInRotatedArray;

    @BeforeEach
    void setUp() {
        searchInRotatedArray = new SearchInRotatedArray();
    }

    @Test
    @DisplayName("Search in Rotated Sorted Array")
    void testSearch() {
        // Given
        int[] nums1 = {4, 5, 6, 7, 0, 1, 2};
        int[] nums2 = {4, 5, 6, 7, 0, 1, 2};
        int[] nums3 = {1};

        // When & Then
        assertEquals(4, searchInRotatedArray.search(nums1, 0));
        assertEquals(-1, searchInRotatedArray.search(nums2, 3));
        assertEquals(-1, searchInRotatedArray.search(nums3, 0));
    }

    @Test
    @DisplayName("Search in Rotated Array - No Rotation")
    void testSearchNoRotation() {
        // Given
        int[] nums = {1, 2, 3, 4, 5, 6, 7};

        // When & Then
        assertEquals(0, searchInRotatedArray.search(nums, 1));
        assertEquals(6, searchInRotatedArray.search(nums, 7));
        assertEquals(3, searchInRotatedArray.search(nums, 4));
        assertEquals(-1, searchInRotatedArray.search(nums, 8));
    }

    @Test
    @DisplayName("Search with Duplicates")
    void testSearchWithDuplicates() {
        // Given
        int[] nums1 = {2, 5, 6, 0, 0, 1, 2};
        int[] nums2 = {1, 0, 1, 1, 1};

        // When & Then
        assertTrue(searchInRotatedArray.searchWithDuplicates(nums1, 0));
        assertFalse(searchInRotatedArray.searchWithDuplicates(nums2, 2));
        assertTrue(searchInRotatedArray.searchWithDuplicates(nums2, 0));
    }

    @Test
    @DisplayName("Find Min with Duplicates")
    void testFindMinWithDuplicates() {
        // Given
        int[] nums1 = {1, 3, 5};
        int[] nums2 = {2, 2, 2, 0, 1};
        int[] nums3 = {1, 1, 1, 1};

        // When & Then
        assertEquals(1, searchInRotatedArray.findMinWithDuplicates(nums1));
        assertEquals(0, searchInRotatedArray.findMinWithDuplicates(nums2));
        assertEquals(1, searchInRotatedArray.findMinWithDuplicates(nums3));
    }

    @Test
    @DisplayName("Find Rotation Count")
    void testFindRotationCount() {
        // Given
        int[] nums1 = {3, 4, 5, 1, 2}; // rotated 3 times
        int[] nums2 = {4, 5, 6, 7, 0, 1, 2}; // rotated 4 times
        int[] nums3 = {1, 2, 3, 4, 5}; // not rotated

        // When & Then
        assertEquals(3, searchInRotatedArray.findRotationCount(nums1));
        assertEquals(4, searchInRotatedArray.findRotationCount(nums2));
        assertEquals(0, searchInRotatedArray.findRotationCount(nums3));
    }
}
