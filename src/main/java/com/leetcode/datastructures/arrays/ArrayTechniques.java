package com.leetcode.datastructures.arrays;

/**
 * Array manipulation techniques for LeetCode problems
 */
public class ArrayTechniques {

    /**
     * Rotate array to the right by k steps
     * Time: O(n), Space: O(1)
     */
    public void rotate(int[] nums, int k) {
        k = k % nums.length;
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);
    }

    private void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }

    /**
     * Merge two sorted arrays in-place
     * Time: O(m + n), Space: O(1)
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1, j = n - 1, k = m + n - 1;

        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }

        while (j >= 0) {
            nums1[k--] = nums2[j--];
        }
    }

    /**
     * Move zeros to end while maintaining order of non-zero elements
     * Time: O(n), Space: O(1)
     */
    public void moveZeroes(int[] nums) {
        int writeIndex = 0;

        // Move all non-zero elements to front
        for (int readIndex = 0; readIndex < nums.length; readIndex++) {
            if (nums[readIndex] != 0) {
                nums[writeIndex++] = nums[readIndex];
            }
        }

        // Fill remaining positions with zeros
        while (writeIndex < nums.length) {
            nums[writeIndex++] = 0;
        }
    }

    /**
     * Find the missing number in array containing n distinct numbers in range [0, n]
     * Time: O(n), Space: O(1)
     */
    public int missingNumber(int[] nums) {
        int n = nums.length;
        int expectedSum = n * (n + 1) / 2;
        int actualSum = 0;

        for (int num : nums) {
            actualSum += num;
        }

        return expectedSum - actualSum;
    }

    /**
     * Find single number that appears once while others appear twice
     * Time: O(n), Space: O(1)
     */
    public int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num; // XOR operation
        }
        return result;
    }

    /**
     * Majority element (appears more than n/2 times) - Boyer-Moore Algorithm
     * Time: O(n), Space: O(1)
     */
    public int majorityElement(int[] nums) {
        int candidate = nums[0];
        int count = 1;

        for (int i = 1; i < nums.length; i++) {
            if (count == 0) {
                candidate = nums[i];
                count = 1;
            } else if (nums[i] == candidate) {
                count++;
            } else {
                count--;
            }
        }

        return candidate;
    }

    /**
     * Product of array except self
     * Time: O(n), Space: O(1) excluding output array
     */
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];

        // Left products
        result[0] = 1;
        for (int i = 1; i < n; i++) {
            result[i] = result[i - 1] * nums[i - 1];
        }

        // Right products
        int rightProduct = 1;
        for (int i = n - 1; i >= 0; i--) {
            result[i] *= rightProduct;
            rightProduct *= nums[i];
        }

        return result;
    }
}

