package com.leetcode.algorithms.dynamicprogramming;

import java.util.*;

/**
 * Knapsack Problem Variants using Dynamic Programming
 * Classic optimization problem with multiple variations
 *
 * Problem Types:
 * 1. 0/1 Knapsack - each item can be taken at most once
 * 2. Unbounded Knapsack - unlimited quantity of each item
 * 3. Multi-dimensional Knapsack - multiple constraints
 * 4. Fractional Knapsack - can take fractions (greedy approach)
 *
 * Applications: Resource allocation, portfolio optimization, cutting stock
 */
public class KnapsackProblems {

    // ==================== 0/1 KNAPSACK ====================

    /**
     * Classic 0/1 Knapsack - each item can be taken at most once
     * Time: O(n*W), Space: O(n*W)
     */
    public int knapsack01(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= capacity; w++) {
                // Don't take item i-1
                dp[i][w] = dp[i - 1][w];

                // Take item i-1 if it fits
                if (weights[i - 1] <= w) {
                    dp[i][w] = Math.max(dp[i][w],
                            dp[i - 1][w - weights[i - 1]] + values[i - 1]);
                }
            }
        }

        return dp[n][capacity];
    }

    /**
     * 0/1 Knapsack - Space Optimized
     * Time: O(n*W), Space: O(W)
     */
    public int knapsack01Optimized(int[] weights, int[] values, int capacity) {
        int[] dp = new int[capacity + 1];

        for (int i = 0; i < weights.length; i++) {
            // Traverse backwards to avoid using updated values
            for (int w = capacity; w >= weights[i]; w--) {
                dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
            }
        }

        return dp[capacity];
    }

    /**
     * 0/1 Knapsack with items tracking
     * Returns both maximum value and selected items
     */
    public KnapsackResult knapsack01WithItems(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        int[][] dp = new int[n + 1][capacity + 1];

        // Fill DP table
        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= capacity; w++) {
                dp[i][w] = dp[i - 1][w];
                if (weights[i - 1] <= w) {
                    dp[i][w] = Math.max(dp[i][w],
                            dp[i - 1][w - weights[i - 1]] + values[i - 1]);
                }
            }
        }

        // Backtrack to find selected items
        List<Integer> selectedItems = new ArrayList<>();
        int w = capacity;
        for (int i = n; i > 0 && w > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                selectedItems.add(i - 1); // Item index
                w -= weights[i - 1];
            }
        }

        Collections.reverse(selectedItems);
        return new KnapsackResult(dp[n][capacity], selectedItems);
    }

    // ==================== UNBOUNDED KNAPSACK ====================

    /**
     * Unbounded Knapsack - unlimited quantity of each item
     * Time: O(n*W), Space: O(W)
     */
    public int unboundedKnapsack(int[] weights, int[] values, int capacity) {
        int[] dp = new int[capacity + 1];

        for (int w = 1; w <= capacity; w++) {
            for (int i = 0; i < weights.length; i++) {
                if (weights[i] <= w) {
                    dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
                }
            }
        }

        return dp[capacity];
    }

    /**
     * Coin Change - special case of unbounded knapsack
     * Time: O(amount * coins), Space: O(amount)
     */
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1); // Initialize with impossible value
        dp[0] = 0;

        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (coin <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }

        return dp[amount] > amount ? -1 : dp[amount];
    }

    /**
     * Coin Change II - number of ways to make amount
     * Time: O(amount * coins), Space: O(amount)
     */
    public int coinChangeWays(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;

        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                dp[i] += dp[i - coin];
            }
        }

        return dp[amount];
    }

    // ==================== SUBSET SUM PROBLEMS ====================

    /**
     * Subset Sum - check if subset with given sum exists
     * Time: O(n*sum), Space: O(sum)
     */
    public boolean canPartition(int[] nums, int targetSum) {
        boolean[] dp = new boolean[targetSum + 1];
        dp[0] = true;

        for (int num : nums) {
            for (int sum = targetSum; sum >= num; sum--) {
                dp[sum] = dp[sum] || dp[sum - num];
            }
        }

        return dp[targetSum];
    }

    /**
     * Partition Equal Subset Sum
     * Time: O(n*sum), Space: O(sum)
     */
    public boolean canPartitionEqualSum(int[] nums) {
        int totalSum = Arrays.stream(nums).sum();
        if (totalSum % 2 != 0) return false;

        int target = totalSum / 2;
        return canPartition(nums, target);
    }

    /**
     * Target Sum - assign +/- to each number to reach target
     * Time: O(n*sum), Space: O(sum)
     */
    public int findTargetSumWays(int[] nums, int target) {
        int sum = Arrays.stream(nums).sum();
        if (target > sum || target < -sum || (target + sum) % 2 != 0) {
            return 0;
        }

        int subsetSum = (target + sum) / 2;
        return countSubsets(nums, subsetSum);
    }

    private int countSubsets(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;

        for (int num : nums) {
            for (int sum = target; sum >= num; sum--) {
                dp[sum] += dp[sum - num];
            }
        }

        return dp[target];
    }

    // ==================== MULTI-DIMENSIONAL KNAPSACK ====================

    /**
     * 2D Knapsack - two constraints (weight and volume)
     * Time: O(n*W*V), Space: O(W*V)
     */
    public int knapsack2D(int[] weights, int[] volumes, int[] values,
                          int weightCapacity, int volumeCapacity) {
        int[][] dp = new int[weightCapacity + 1][volumeCapacity + 1];

        for (int i = 0; i < weights.length; i++) {
            for (int w = weightCapacity; w >= weights[i]; w--) {
                for (int v = volumeCapacity; v >= volumes[i]; v--) {
                    dp[w][v] = Math.max(dp[w][v],
                            dp[w - weights[i]][v - volumes[i]] + values[i]);
                }
            }
        }

        return dp[weightCapacity][volumeCapacity];
    }

    // ==================== BOUNDED KNAPSACK ====================

    /**
     * Bounded Knapsack - limited quantity of each item
     * Time: O(n*W*K), Space: O(W) where K is max quantity
     */
    public int boundedKnapsack(int[] weights, int[] values, int[] quantities, int capacity) {
        int[] dp = new int[capacity + 1];

        for (int i = 0; i < weights.length; i++) {
            for (int w = capacity; w >= weights[i]; w--) {
                for (int k = 1; k <= quantities[i] && k * weights[i] <= w; k++) {
                    dp[w] = Math.max(dp[w], dp[w - k * weights[i]] + k * values[i]);
                }
            }
        }

        return dp[capacity];
    }

    // ==================== KNAPSACK VARIANTS ====================

    /**
     * Complete Knapsack with minimum items constraint
     * Time: O(n*W), Space: O(W)
     */
    public int minItemsKnapsack(int[] weights, int[] values, int capacity, int minValue) {
        int[] dpValue = new int[capacity + 1];
        int[] dpItems = new int[capacity + 1];
        Arrays.fill(dpItems, Integer.MAX_VALUE);
        dpItems[0] = 0;

        for (int w = 1; w <= capacity; w++) {
            for (int i = 0; i < weights.length; i++) {
                if (weights[i] <= w && dpItems[w - weights[i]] != Integer.MAX_VALUE) {
                    if (dpValue[w - weights[i]] + values[i] >= minValue) {
                        if (dpValue[w] < dpValue[w - weights[i]] + values[i] ||
                                (dpValue[w] == dpValue[w - weights[i]] + values[i] &&
                                        dpItems[w] > dpItems[w - weights[i]] + 1)) {
                            dpValue[w] = dpValue[w - weights[i]] + values[i];
                            dpItems[w] = dpItems[w - weights[i]] + 1;
                        }
                    }
                }
            }
        }

        return dpItems[capacity] == Integer.MAX_VALUE ? -1 : dpItems[capacity];
    }

    /**
     * Profit Maximization Knapsack
     * Items have both cost and profit
     */
    public int maxProfitKnapsack(int[] costs, int[] profits, int budget) {
        int[] dp = new int[budget + 1];

        for (int i = 0; i < costs.length; i++) {
            for (int b = budget; b >= costs[i]; b--) {
                dp[b] = Math.max(dp[b], dp[b - costs[i]] + profits[i]);
            }
        }

        return dp[budget];
    }

    // ==================== OPTIMIZATION TECHNIQUES ====================

    /**
     * Branch and Bound optimization for exact solution
     * Used when DP is too memory intensive
     */
    public int knapsackBranchBound(int[] weights, int[] values, int capacity) {
        // Sort items by value/weight ratio (greedy heuristic)
        Item[] items = new Item[weights.length];
        for (int i = 0; i < weights.length; i++) {
            items[i] = new Item(weights[i], values[i], i);
        }
        Arrays.sort(items, (a, b) -> Double.compare(b.ratio, a.ratio));

        return branchBoundHelper(items, 0, 0, 0, capacity);
    }

    private int branchBoundHelper(Item[] items, int index, int currentWeight,
                                  int currentValue, int capacity) {
        if (index >= items.length || currentWeight >= capacity) {
            return currentValue;
        }

        // Upper bound calculation (fractional knapsack for remaining items)
        double upperBound = currentValue;
        int tempWeight = currentWeight;
        for (int i = index; i < items.length && tempWeight < capacity; i++) {
            if (tempWeight + items[i].weight <= capacity) {
                tempWeight += items[i].weight;
                upperBound += items[i].value;
            } else {
                upperBound += items[i].value * (double)(capacity - tempWeight) / items[i].weight;
                break;
            }
        }

        // Pruning: if upper bound is not better than current best, prune
        if (upperBound <= currentValue) {
            return currentValue;
        }

        // Include current item
        int include = 0;
        if (currentWeight + items[index].weight <= capacity) {
            include = branchBoundHelper(items, index + 1,
                    currentWeight + items[index].weight,
                    currentValue + items[index].value, capacity);
        }

        // Exclude current item
        int exclude = branchBoundHelper(items, index + 1, currentWeight, currentValue, capacity);

        return Math.max(include, exclude);
    }

    // ==================== HELPER CLASSES ====================

    static class Item {
        int weight, value, index;
        double ratio;

        Item(int weight, int value, int index) {
            this.weight = weight;
            this.value = value;
            this.index = index;
            this.ratio = (double) value / weight;
        }
    }

    static class KnapsackResult {
        int maxValue;
        List<Integer> selectedItems;

        KnapsackResult(int maxValue, List<Integer> selectedItems) {
            this.maxValue = maxValue;
            this.selectedItems = selectedItems;
        }

        @Override
        public String toString() {
            return String.format("Max Value: %d, Selected Items: %s", maxValue, selectedItems);
        }
    }

    // ==================== UTILITY METHODS ====================

    /**
     * Generate random knapsack problem for testing
     */
    public static KnapsackTestCase generateRandomKnapsack(int n, int maxWeight, int maxValue, int capacity) {
        Random rand = new Random();
        int[] weights = new int[n];
        int[] values = new int[n];

        for (int i = 0; i < n; i++) {
            weights[i] = rand.nextInt(maxWeight) + 1;
            values[i] = rand.nextInt(maxValue) + 1;
        }

        return new KnapsackTestCase(weights, values, capacity);
    }

    static class KnapsackTestCase {
        int[] weights, values;
        int capacity;

        KnapsackTestCase(int[] weights, int[] values, int capacity) {
            this.weights = weights;
            this.values = values;
            this.capacity = capacity;
        }
    }

    /**
     * Verify knapsack solution validity
     */
    public boolean verifySolution(int[] weights, int[] values, List<Integer> selectedItems,
                                  int capacity, int expectedValue) {
        int totalWeight = 0;
        int totalValue = 0;

        for (int index : selectedItems) {
            totalWeight += weights[index];
            totalValue += values[index];
        }

        return totalWeight <= capacity && totalValue == expectedValue;
    }

    /**
     * Calculate efficiency ratio for knapsack solution
     */
    public double calculateEfficiency(int[] weights, int[] values, List<Integer> selectedItems, int capacity) {
        int totalWeight = 0;
        int totalValue = 0;

        for (int index : selectedItems) {
            totalWeight += weights[index];
            totalValue += values[index];
        }

        return totalWeight == 0 ? 0 : (double) totalValue / totalWeight;
    }
}