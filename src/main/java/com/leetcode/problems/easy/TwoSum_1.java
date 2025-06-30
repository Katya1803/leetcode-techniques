package com.leetcode.problems.easy;

import java.util.*;

/**
 * LeetCode 1: Two Sum
 * Multiple solution approaches with different trade-offs
 */
public class TwoSum_1 {

    /**
     * Optimal Solution: Hash Map Complement Lookup
     * Time: O(n), Space: O(n)
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> complementMap = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];

            if (complementMap.containsKey(complement)) {
                return new int[]{complementMap.get(complement), i};
            }

            complementMap.put(nums[i], i);
        }

        throw new IllegalArgumentException("No solution exists");
    }

    /**
     * Brute Force Solution
     * Time: O(n²), Space: O(1)
     */
    public int[] twoSumBruteForce(int[] nums, int target) {
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }

        throw new IllegalArgumentException("No solution exists");
    }

    /**
     * Two Pointers Solution (requires sorting, loses original indices)
     * Time: O(n log n), Space: O(n)
     */
    public int[] twoSumTwoPointers(int[] nums, int target) {
        int[][] pairs = new int[nums.length][2];
        for (int i = 0; i < nums.length; i++) {
            pairs[i] = new int[]{nums[i], i};
        }

        Arrays.sort(pairs, Comparator.comparingInt(a -> a[0]));

        int left = 0, right = nums.length - 1;

        while (left < right) {
            int sum = pairs[left][0] + pairs[right][0];

            if (sum == target) {
                int idx1 = pairs[left][1];
                int idx2 = pairs[right][1];
                return new int[]{Math.min(idx1, idx2), Math.max(idx1, idx2)};
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }

        throw new IllegalArgumentException("No solution exists");
    }
}