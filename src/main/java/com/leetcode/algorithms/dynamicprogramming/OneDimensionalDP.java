package com.leetcode.algorithms.dynamicprogramming;

import java.util.*;

/**
 * One-Dimensional Dynamic Programming Algorithms
 * Covers classic 1D DP patterns and optimizations
 *
 * Key Principles:
 * 1. Overlapping subproblems - same subproblems computed multiple times
 * 2. Optimal substructure - optimal solution contains optimal subsolutions
 * 3. State definition - what parameters uniquely define a subproblem
 * 4. Transition function - how to compute state from previous states
 */
public class OneDimensionalDP {

    // ==================== FIBONACCI SEQUENCE PATTERNS ====================

    /**
     * Classic Fibonacci - Recursive (Exponential Time)
     * Time: O(2^n), Space: O(n)
     */
    public int fibonacciRecursive(int n) {
        if (n <= 1) return n;
        return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
    }

    /**
     * Fibonacci with Memoization (Top-Down DP)
     * Time: O(n), Space: O(n)
     */
    public int fibonacciMemo(int n) {
        return fibonacciMemoHelper(n, new HashMap<>());
    }

    private int fibonacciMemoHelper(int n, Map<Integer, Integer> memo) {
        if (n <= 1) return n;

        if (memo.containsKey(n)) {
            return memo.get(n);
        }

        int result = fibonacciMemoHelper(n - 1, memo) + fibonacciMemoHelper(n - 2, memo);
        memo.put(n, result);
        return result;
    }

    /**
     * Fibonacci Tabulation (Bottom-Up DP)
     * Time: O(n), Space: O(n)
     */
    public int fibonacciTabulation(int n) {
        if (n <= 1) return n;

        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }

    /**
     * Fibonacci Space-Optimized
     * Time: O(n), Space: O(1)
     */
    public int fibonacciOptimized(int n) {
        if (n <= 1) return n;

        int prev2 = 0, prev1 = 1;
        for (int i = 2; i <= n; i++) {
            int current = prev1 + prev2;
            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }

    // ==================== CLIMBING STAIRS PATTERN ====================

    /**
     * Climbing Stairs - can climb 1 or 2 steps at a time
     * Time: O(n), Space: O(1)
     */
    public int climbStairs(int n) {
        if (n <= 2) return n;

        int one = 1, two = 2;
        for (int i = 3; i <= n; i++) {
            int current = one + two;
            one = two;
            two = current;
        }

        return two;
    }

    /**
     * Climbing Stairs with Variable Steps
     * Time: O(n * k), Space: O(n) where k = number of possible steps
     */
    public int climbStairsVariableSteps(int n, int[] steps) {
        int[] dp = new int[n + 1];
        dp[0] = 1;

        for (int i = 1; i <= n; i++) {
            for (int step : steps) {
                if (i >= step) {
                    dp[i] += dp[i - step];
                }
            }
        }

        return dp[n];
    }

    /**
     * Min Cost Climbing Stairs
     * Time: O(n), Space: O(1)
     */
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        int first = cost[0];
        int second = cost[1];

        for (int i = 2; i < n; i++) {
            int current = cost[i] + Math.min(first, second);
            first = second;
            second = current;
        }

        return Math.min(first, second);
    }

    // ==================== HOUSE ROBBER PATTERN ====================

    /**
     * House Robber - cannot rob adjacent houses
     * Time: O(n), Space: O(1)
     */
    public int rob(int[] nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];

        int prev2 = nums[0];
        int prev1 = Math.max(nums[0], nums[1]);

        for (int i = 2; i < nums.length; i++) {
            int current = Math.max(prev1, prev2 + nums[i]);
            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }

    /**
     * House Robber II - houses in circle (first and last are adjacent)
     * Time: O(n), Space: O(1)
     */
    public int robCircle(int[] nums) {
        if (nums.length == 1) return nums[0];
        if (nums.length == 2) return Math.max(nums[0], nums[1]);

        // Case 1: Rob first house, cannot rob last
        int case1 = robLinear(Arrays.copyOfRange(nums, 0, nums.length - 1));

        // Case 2: Rob last house, cannot rob first
        int case2 = robLinear(Arrays.copyOfRange(nums, 1, nums.length));

        return Math.max(case1, case2);
    }

    private int robLinear(int[] nums) {
        int prev2 = 0, prev1 = 0;

        for (int num : nums) {
            int current = Math.max(prev1, prev2 + num);
            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }

    // ==================== DECISION PROBLEMS ====================

    /**
     * Can Jump - determine if can reach last index
     * Time: O(n), Space: O(1)
     */
    public boolean canJump(int[] nums) {
        int maxReach = 0;

        for (int i = 0; i < nums.length; i++) {
            if (i > maxReach) return false;
            maxReach = Math.max(maxReach, i + nums[i]);
            if (maxReach >= nums.length - 1) return true;
        }

        return true;
    }

    /**
     * Jump Game II - minimum jumps to reach end
     * Time: O(n), Space: O(1)
     */
    public int jump(int[] nums) {
        int jumps = 0;
        int currentEnd = 0;
        int farthest = 0;

        for (int i = 0; i < nums.length - 1; i++) {
            farthest = Math.max(farthest, i + nums[i]);

            if (i == currentEnd) {
                jumps++;
                currentEnd = farthest;
            }
        }

        return jumps;
    }

    // ==================== SEQUENCE PROBLEMS ====================

    /**
     * Longest Increasing Subsequence (LIS)
     * Time: O(n²), Space: O(n)
     */
    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);

        int maxLength = 1;

        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLength = Math.max(maxLength, dp[i]);
        }

        return maxLength;
    }

    /**
     * LIS Optimized with Binary Search
     * Time: O(n log n), Space: O(n)
     */
    public int lengthOfLISOptimized(int[] nums) {
        List<Integer> tails = new ArrayList<>();

        for (int num : nums) {
            int pos = binarySearch(tails, num);

            if (pos == tails.size()) {
                tails.add(num);
            } else {
                tails.set(pos, num);
            }
        }

        return tails.size();
    }

    private int binarySearch(List<Integer> tails, int target) {
        int left = 0, right = tails.size();

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (tails.get(mid) < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    /**
     * Longest Decreasing Subsequence
     * Time: O(n²), Space: O(n)
     */
    public int lengthOfLDS(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);

        int maxLength = 1;

        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] > nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLength = Math.max(maxLength, dp[i]);
        }

        return maxLength;
    }

    // ==================== ARRAY MANIPULATION ====================

    /**
     * Maximum Subarray Sum (Kadane's Algorithm)
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
     * Maximum Product Subarray
     * Time: O(n), Space: O(1)
     */
    public int maxProduct(int[] nums) {
        int maxSoFar = nums[0];
        int maxHere = nums[0];
        int minHere = nums[0];

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < 0) {
                // Swap max and min when current number is negative
                int temp = maxHere;
                maxHere = minHere;
                minHere = temp;
            }

            maxHere = Math.max(nums[i], maxHere * nums[i]);
            minHere = Math.min(nums[i], minHere * nums[i]);
            maxSoFar = Math.max(maxSoFar, maxHere);
        }

        return maxSoFar;
    }

    /**
     * Best Time to Buy and Sell Stock
     * Time: O(n), Space: O(1)
     */
    public int maxProfit(int[] prices) {
        int minPrice = prices[0];
        int maxProfit = 0;

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            } else {
                maxProfit = Math.max(maxProfit, prices[i] - minPrice);
            }
        }
        return maxProfit;
    }
}