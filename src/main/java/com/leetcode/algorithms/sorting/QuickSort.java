package com.leetcode.algorithms.sorting;

import java.util.Random;

/**
 * Quick Sort implementation and related problems
 */
public class QuickSort {

    private final Random random = new Random();

    /**
     * Standard quick sort
     * Time: O(n log n) average, O(n²) worst, Space: O(log n)
     */
    public void quickSort(int[] nums) {
        if (nums.length <= 1) return;
        quickSortHelper(nums, 0, nums.length - 1);
    }

    private void quickSortHelper(int[] nums, int left, int right) {
        if (left >= right) return;

        int pivotIndex = partition(nums, left, right);
        quickSortHelper(nums, left, pivotIndex - 1);
        quickSortHelper(nums, pivotIndex + 1, right);
    }

    private int partition(int[] nums, int left, int right) {
        // Random pivot to avoid worst case
        int randomPivot = left + random.nextInt(right - left + 1);
        swap(nums, randomPivot, right);

        int pivot = nums[right];
        int i = left;

        for (int j = left; j < right; j++) {
            if (nums[j] <= pivot) {
                swap(nums, i++, j);
            }
        }

        swap(nums, i, right);
        return i;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * Quick select to find kth largest element
     * Time: O(n) average, O(n²) worst, Space: O(1)
     */
    public int findKthLargest(int[] nums, int k) {
        return quickSelect(nums, 0, nums.length - 1, nums.length - k);
    }

    private int quickSelect(int[] nums, int left, int right, int k) {
        int pivotIndex = partition(nums, left, right);

        if (pivotIndex == k) {
            return nums[pivotIndex];
        } else if (pivotIndex < k) {
            return quickSelect(nums, pivotIndex + 1, right, k);
        } else {
            return quickSelect(nums, left, pivotIndex - 1, k);
        }
    }

    /**
     * Three-way partition (Dutch National Flag)
     * Time: O(n), Space: O(1)
     */
    public void threeWayPartition(int[] nums, int target) {
        int low = 0, mid = 0, high = nums.length - 1;

        while (mid <= high) {
            if (nums[mid] < target) {
                swap(nums, low++, mid++);
            } else if (nums[mid] > target) {
                swap(nums, mid, high--);
            } else {
                mid++;
            }
        }
    }

    /**
     * Sort colors (0, 1, 2) using three-way partition
     * Time: O(n), Space: O(1)
     */
    public void sortColors(int[] nums) {
        int low = 0, mid = 0, high = nums.length - 1;

        while (mid <= high) {
            if (nums[mid] == 0) {
                swap(nums, low++, mid++);
            } else if (nums[mid] == 2) {
                swap(nums, mid, high--);
            } else {
                mid++;
            }
        }
    }

    /**
     * Wiggle sort - arrange array in wiggle pattern
     * Time: O(n), Space: O(1)
     */
    public void wiggleSort(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            if ((i % 2 == 1 && nums[i] < nums[i - 1]) ||
                    (i % 2 == 0 && nums[i] > nums[i - 1])) {
                swap(nums, i, i - 1);
            }
        }
    }
}