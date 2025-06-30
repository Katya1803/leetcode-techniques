package com.leetcode.algorithms.sorting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {

    private QuickSort quickSort;

    @BeforeEach
    void setUp() {
        quickSort = new QuickSort();
    }

    @Test
    @DisplayName("Standard Quick Sort")
    void testQuickSort() {
        // Given
        int[] nums = {3, 6, 8, 10, 1, 2, 1};

        // When
        quickSort.quickSort(nums);

        // Then
        int[] expected = {1, 1, 2, 3, 6, 8, 10};
        assertArrayEquals(expected, nums);
    }

    @Test
    @DisplayName("Quick Sort - Already Sorted")
    void testQuickSortAlreadySorted() {
        // Given
        int[] nums = {1, 2, 3, 4, 5};

        // When
        quickSort.quickSort(nums);

        // Then
        int[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, nums);
    }

    @Test
    @DisplayName("Quick Sort - Single Element")
    void testQuickSortSingleElement() {
        // Given
        int[] nums = {42};

        // When
        quickSort.quickSort(nums);

        // Then
        assertArrayEquals(new int[]{42}, nums);
    }

    @Test
    @DisplayName("Find Kth Largest")
    void testFindKthLargest() {
        // Given
        int[] nums1 = {3, 2, 1, 5, 6, 4};
        int[] nums2 = {3, 2, 3, 1, 2, 4, 5, 5, 6};

        // When & Then
        assertEquals(5, quickSort.findKthLargest(nums1, 2)); // 2nd largest
        assertEquals(4, quickSort.findKthLargest(nums2, 4)); // 4th largest
    }

    @Test
    @DisplayName("Three Way Partition")
    void testThreeWayPartition() {
        // Given
        int[] nums = {2, 0, 2, 1, 1, 0};
        int target = 1;

        // When
        quickSort.threeWayPartition(nums, target);

        // Then - All 0s should be at beginning, 1s in middle, 2s at end
        assertTrue(isThreeWayPartitioned(nums, target));
    }

    private boolean isThreeWayPartitioned(int[] nums, int target) {
        int phase = 0; // 0: less than target, 1: equal to target, 2: greater than target

        for (int num : nums) {
            if (num < target) {
                if (phase > 0) return false;
            } else if (num == target) {
                if (phase > 1) return false;
                phase = Math.max(phase, 1);
            } else {
                phase = 2;
            }
        }

        return true;
    }

    @Test
    @DisplayName("Sort Colors")
    void testSortColors() {
        // Given
        int[] nums = {2, 0, 2, 1, 1, 0};
        int[] expected = {0, 0, 1, 1, 2, 2};

        // When
        quickSort.sortColors(nums);

        // Then
        assertArrayEquals(expected, nums);
    }

    @Test
    @DisplayName("Sort Colors - All Same")
    void testSortColorsAllSame() {
        // Given
        int[] nums = {1, 1, 1, 1};
        int[] expected = {1, 1, 1, 1};

        // When
        quickSort.sortColors(nums);

        // Then
        assertArrayEquals(expected, nums);
    }

    @Test
    @DisplayName("Wiggle Sort")
    void testWiggleSort() {
        // Given
        int[] nums = {3, 5, 2, 1, 6, 4};

        // When
        quickSort.wiggleSort(nums);

        // Then - Check wiggle pattern: nums[0] < nums[1] > nums[2] < nums[3]...
        assertTrue(isWiggleSorted(nums));
    }

    private boolean isWiggleSorted(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            if (i % 2 == 1) {
                // Odd indices should be greater than previous
                if (nums[i] <= nums[i - 1]) return false;
            } else {
                // Even indices should be less than previous
                if (nums[i] >= nums[i - 1]) return false;
            }
        }
        return true;
    }
}