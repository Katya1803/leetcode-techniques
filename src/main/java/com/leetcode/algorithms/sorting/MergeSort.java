package com.leetcode.algorithms.sorting;

/**
 * Merge Sort implementation and related problems
 */
public class MergeSort {

    /**
     * Standard merge sort
     * Time: O(n log n), Space: O(n)
     */
    public void mergeSort(int[] nums) {
        if (nums.length <= 1) return;
        mergeSortHelper(nums, 0, nums.length - 1);
    }

    private void mergeSortHelper(int[] nums, int left, int right) {
        if (left >= right) return;

        int mid = left + (right - left) / 2;
        mergeSortHelper(nums, left, mid);
        mergeSortHelper(nums, mid + 1, right);
        merge(nums, left, mid, right);
    }

    private void merge(int[] nums, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            if (nums[i] <= nums[j]) {
                temp[k++] = nums[i++];
            } else {
                temp[k++] = nums[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = nums[i++];
        }

        while (j <= right) {
            temp[k++] = nums[j++];
        }

        for (int idx = 0; idx < temp.length; idx++) {
            nums[left + idx] = temp[idx];
        }
    }

    /**
     * Count inversions using merge sort
     * Time: O(n log n), Space: O(n)
     */
    public int countInversions(int[] nums) {
        return countInversionsHelper(nums, 0, nums.length - 1);
    }

    private int countInversionsHelper(int[] nums, int left, int right) {
        if (left >= right) return 0;

        int mid = left + (right - left) / 2;
        int leftCount = countInversionsHelper(nums, left, mid);
        int rightCount = countInversionsHelper(nums, mid + 1, right);
        int mergeCount = mergeAndCount(nums, left, mid, right);

        return leftCount + rightCount + mergeCount;
    }

    private int mergeAndCount(int[] nums, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;
        int invCount = 0;

        while (i <= mid && j <= right) {
            if (nums[i] <= nums[j]) {
                temp[k++] = nums[i++];
            } else {
                temp[k++] = nums[j++];
                invCount += (mid - i + 1); // All elements from i to mid are greater than nums[j]
            }
        }

        while (i <= mid) {
            temp[k++] = nums[i++];
        }

        while (j <= right) {
            temp[k++] = nums[j++];
        }

        for (int idx = 0; idx < temp.length; idx++) {
            nums[left + idx] = temp[idx];
        }

        return invCount;
    }

    /**
     * Merge k sorted arrays
     * Time: O(n log k), Space: O(n)
     */
    public int[] mergeKSortedArrays(int[][] arrays) {
        if (arrays.length == 0) return new int[0];

        return mergeKArraysHelper(arrays, 0, arrays.length - 1);
    }

    private int[] mergeKArraysHelper(int[][] arrays, int start, int end) {
        if (start == end) return arrays[start];

        int mid = start + (end - start) / 2;
        int[] left = mergeKArraysHelper(arrays, start, mid);
        int[] right = mergeKArraysHelper(arrays, mid + 1, end);

        return mergeTwoArrays(left, right);
    }

    private int[] mergeTwoArrays(int[] arr1, int[] arr2) {
        int[] result = new int[arr1.length + arr2.length];
        int i = 0, j = 0, k = 0;

        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] <= arr2[j]) {
                result[k++] = arr1[i++];
            } else {
                result[k++] = arr2[j++];
            }
        }

        while (i < arr1.length) {
            result[k++] = arr1[i++];
        }

        while (j < arr2.length) {
            result[k++] = arr2[j++];
        }

        return result;
    }
}