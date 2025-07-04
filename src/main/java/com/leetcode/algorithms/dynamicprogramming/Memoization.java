package com.leetcode.algorithms.dynamicprogramming;

import java.util.*;

/**
 * Memoization Techniques for Dynamic Programming
 * Top-down approach with caching to avoid recomputation
 *
 * Key Principles:
 * 1. Recursive approach with result caching
 * 2. Converts exponential time to polynomial time
 * 3. Natural problem decomposition
 * 4. Memory vs computation trade-off
 *
 * Time Complexity: Usually O(states × transition_cost)
 * Space Complexity: O(states) for cache + O(depth) for recursion
 */
public class Memoization {

    // ==================== CLASSIC EXAMPLES ====================

    /**
     * Fibonacci with Memoization
     * Time: O(n), Space: O(n)
     */
    public int fibonacci(int n) {
        Map<Integer, Integer> memo = new HashMap<>();
        return fibHelper(n, memo);
    }

    private int fibHelper(int n, Map<Integer, Integer> memo) {
        if (n <= 1) return n;

        if (memo.containsKey(n)) {
            return memo.get(n);
        }

        int result = fibHelper(n - 1, memo) + fibHelper(n - 2, memo);
        memo.put(n, result);
        return result;
    }

    /**
     * Climbing Stairs with Memoization
     * Time: O(n), Space: O(n)
     */
    public int climbStairs(int n) {
        Map<Integer, Integer> memo = new HashMap<>();
        return climbHelper(n, memo);
    }

    private int climbHelper(int n, Map<Integer, Integer> memo) {
        if (n <= 2) return n;

        if (memo.containsKey(n)) {
            return memo.get(n);
        }

        int result = climbHelper(n - 1, memo) + climbHelper(n - 2, memo);
        memo.put(n, result);
        return result;
    }

    // ==================== GRID PROBLEMS ====================

    /**
     * Unique Paths with Memoization
     * Time: O(m*n), Space: O(m*n)
     */
    public int uniquePaths(int m, int n) {
        Map<String, Integer> memo = new HashMap<>();
        return pathHelper(m - 1, n - 1, memo);
    }

    private int pathHelper(int row, int col, Map<String, Integer> memo) {
        if (row == 0 || col == 0) return 1;

        String key = row + "," + col;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        int result = pathHelper(row - 1, col, memo) + pathHelper(row, col - 1, memo);
        memo.put(key, result);
        return result;
    }

    /**
     * Minimum Path Sum with Memoization
     * Time: O(m*n), Space: O(m*n)
     */
    public int minPathSum(int[][] grid) {
        Map<String, Integer> memo = new HashMap<>();
        return minPathHelper(grid, grid.length - 1, grid[0].length - 1, memo);
    }

    private int minPathHelper(int[][] grid, int row, int col, Map<String, Integer> memo) {
        if (row == 0 && col == 0) return grid[0][0];
        if (row < 0 || col < 0) return Integer.MAX_VALUE;

        String key = row + "," + col;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        int fromTop = minPathHelper(grid, row - 1, col, memo);
        int fromLeft = minPathHelper(grid, row, col - 1, memo);
        int result = grid[row][col] + Math.min(fromTop, fromLeft);

        memo.put(key, result);
        return result;
    }

    // ==================== STRING PROBLEMS ====================

    /**
     * Longest Common Subsequence with Memoization
     * Time: O(m*n), Space: O(m*n)
     */
    public int longestCommonSubsequence(String text1, String text2) {
        Map<String, Integer> memo = new HashMap<>();
        return lcsHelper(text1, text2, 0, 0, memo);
    }

    private int lcsHelper(String s1, String s2, int i, int j, Map<String, Integer> memo) {
        if (i >= s1.length() || j >= s2.length()) return 0;

        String key = i + "," + j;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        int result;
        if (s1.charAt(i) == s2.charAt(j)) {
            result = 1 + lcsHelper(s1, s2, i + 1, j + 1, memo);
        } else {
            result = Math.max(
                    lcsHelper(s1, s2, i + 1, j, memo),
                    lcsHelper(s1, s2, i, j + 1, memo)
            );
        }

        memo.put(key, result);
        return result;
    }

    /**
     * Edit Distance with Memoization
     * Time: O(m*n), Space: O(m*n)
     */
    public int minDistance(String word1, String word2) {
        Map<String, Integer> memo = new HashMap<>();
        return editHelper(word1, word2, 0, 0, memo);
    }

    private int editHelper(String s1, String s2, int i, int j, Map<String, Integer> memo) {
        if (i >= s1.length()) return s2.length() - j;
        if (j >= s2.length()) return s1.length() - i;

        String key = i + "," + j;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        int result;
        if (s1.charAt(i) == s2.charAt(j)) {
            result = editHelper(s1, s2, i + 1, j + 1, memo);
        } else {
            int insert = editHelper(s1, s2, i, j + 1, memo);
            int delete = editHelper(s1, s2, i + 1, j, memo);
            int replace = editHelper(s1, s2, i + 1, j + 1, memo);
            result = 1 + Math.min(Math.min(insert, delete), replace);
        }

        memo.put(key, result);
        return result;
    }

    /**
     * Word Break with Memoization
     * Time: O(n³), Space: O(n)
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> wordSet = new HashSet<>(wordDict);
        Map<Integer, Boolean> memo = new HashMap<>();
        return wordBreakHelper(s, 0, wordSet, memo);
    }

    private boolean wordBreakHelper(String s, int start, Set<String> wordSet, Map<Integer, Boolean> memo) {
        if (start >= s.length()) return true;

        if (memo.containsKey(start)) {
            return memo.get(start);
        }

        for (int end = start + 1; end <= s.length(); end++) {
            String prefix = s.substring(start, end);
            if (wordSet.contains(prefix) && wordBreakHelper(s, end, wordSet, memo)) {
                memo.put(start, true);
                return true;
            }
        }

        memo.put(start, false);
        return false;
    }

    // ==================== ARRAY PROBLEMS ====================

    /**
     * House Robber with Memoization
     * Time: O(n), Space: O(n)
     */
    public int rob(int[] nums) {
        Map<Integer, Integer> memo = new HashMap<>();
        return robHelper(nums, 0, memo);
    }

    private int robHelper(int[] nums, int index, Map<Integer, Integer> memo) {
        if (index >= nums.length) return 0;

        if (memo.containsKey(index)) {
            return memo.get(index);
        }

        // Choice: rob current house or skip it
        int robCurrent = nums[index] + robHelper(nums, index + 2, memo);
        int skipCurrent = robHelper(nums, index + 1, memo);

        int result = Math.max(robCurrent, skipCurrent);
        memo.put(index, result);
        return result;
    }

    /**
     * Maximum Subarray with Memoization (demonstration)
     * Time: O(n²), Space: O(n²) - Note: not optimal for this problem
     */
    public int maxSubArray(int[] nums) {
        Map<String, Integer> memo = new HashMap<>();
        int maxSum = Integer.MIN_VALUE;

        for (int i = 0; i < nums.length; i++) {
            maxSum = Math.max(maxSum, maxSubHelper(nums, i, i, memo));
        }

        return maxSum;
    }

    private int maxSubHelper(int[] nums, int start, int end, Map<String, Integer> memo) {
        if (start > end || end >= nums.length) return Integer.MIN_VALUE;

        String key = start + "," + end;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        if (start == end) {
            memo.put(key, nums[start]);
            return nums[start];
        }

        // Include current element and extend or start fresh
        int includeCurrent = nums[end] + (end > start ? maxSubHelper(nums, start, end - 1, memo) : 0);
        int extendRight = end + 1 < nums.length ? maxSubHelper(nums, start, end + 1, memo) : Integer.MIN_VALUE;

        int result = Math.max(includeCurrent, extendRight);
        memo.put(key, result);
        return result;
    }

}