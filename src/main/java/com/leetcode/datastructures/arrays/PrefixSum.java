package com.leetcode.datastructures.arrays;

import java.util.*;

/**
 * Prefix Sum technique implementations
 */
public class PrefixSum {

    /**
     * Range sum query - immutable
     */
    public static class NumArray {
        private int[] prefixSum;

        public NumArray(int[] nums) {
            prefixSum = new int[nums.length + 1];
            for (int i = 0; i < nums.length; i++) {
                prefixSum[i + 1] = prefixSum[i] + nums[i];
            }
        }

        public int sumRange(int left, int right) {
            return prefixSum[right + 1] - prefixSum[left];
        }
    }

    /**
     * Subarray sum equals K
     * Time: O(n), Space: O(n)
     */
    public int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> prefixSumCount = new HashMap<>();
        prefixSumCount.put(0, 1);

        int count = 0, prefixSum = 0;

        for (int num : nums) {
            prefixSum += num;

            if (prefixSumCount.containsKey(prefixSum - k)) {
                count += prefixSumCount.get(prefixSum - k);
            }

            prefixSumCount.put(prefixSum, prefixSumCount.getOrDefault(prefixSum, 0) + 1);
        }

        return count;
    }

    /**
     * Contiguous array (equal number of 0s and 1s)
     * Time: O(n), Space: O(n)
     */
    public int findMaxLength(int[] nums) {
        Map<Integer, Integer> sumIndexMap = new HashMap<>();
        sumIndexMap.put(0, -1);

        int maxLength = 0, sum = 0;

        for (int i = 0; i < nums.length; i++) {
            sum += (nums[i] == 1) ? 1 : -1;

            if (sumIndexMap.containsKey(sum)) {
                maxLength = Math.max(maxLength, i - sumIndexMap.get(sum));
            } else {
                sumIndexMap.put(sum, i);
            }
        }

        return maxLength;
    }

    /**
     * Maximum subarray sum (Kadane's algorithm)
     * Time: O(n), Space: O(1)
     */
    public int maxSubArray(int[] nums) {
        int maxSoFar = nums[0];
        int maxEndingHere = nums[0];

        for (int i = 1; i < nums.length; i++) {
            maxEndingHere = Math.max(nums[i], maxEndingHere + nums[i]);
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }

        return maxSoFar;
    }

    /**
     * Pivot index
     * Time: O(n), Space: O(1)
     */
    public int pivotIndex(int[] nums) {
        int totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }

        int leftSum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (leftSum == totalSum - leftSum - nums[i]) {
                return i;
            }
            leftSum += nums[i];
        }

        return -1;
    }
}
