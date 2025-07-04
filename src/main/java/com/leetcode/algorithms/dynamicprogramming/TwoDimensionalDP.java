package com.leetcode.algorithms.dynamicprogramming;

import java.util.*;

/**
 * Two-Dimensional Dynamic Programming Algorithms
 * Covers problems requiring 2D state space and grid-based DP
 *
 * Key Patterns:
 * 1. Grid path problems (2D navigation)
 * 2. String matching and editing
 * 3. Matrix-based optimization
 * 4. Interval DP problems
 *
 * Time Complexity: Generally O(m*n) where m,n are dimensions
 * Space Complexity: O(m*n) or optimized to O(min(m,n))
 */
public class TwoDimensionalDP {

    // ==================== GRID PATH PROBLEMS ====================

    /**
     * Unique Paths - count paths from top-left to bottom-right
     * Can only move right or down
     * Time: O(m*n), Space: O(m*n)
     */
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];

        // Base cases: first row and first column
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int j = 0; j < n; j++) {
            dp[0][j] = 1;
        }

        // Fill the rest
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }

        return dp[m-1][n-1];
    }

    /**
     * Unique Paths - Space Optimized
     * Time: O(m*n), Space: O(n)
     */
    public int uniquePathsOptimized(int m, int n) {
        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[j] = dp[j] + dp[j-1];
            }
        }

        return dp[n-1];
    }

    /**
     * Unique Paths II - with obstacles
     * Time: O(m*n), Space: O(m*n)
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;

        if (obstacleGrid[0][0] == 1 || obstacleGrid[m-1][n-1] == 1) {
            return 0;
        }

        int[][] dp = new int[m][n];
        dp[0][0] = 1;

        // Fill first column
        for (int i = 1; i < m; i++) {
            dp[i][0] = (obstacleGrid[i][0] == 1) ? 0 : dp[i-1][0];
        }

        // Fill first row
        for (int j = 1; j < n; j++) {
            dp[0][j] = (obstacleGrid[0][j] == 1) ? 0 : dp[0][j-1];
        }

        // Fill the rest
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = dp[i-1][j] + dp[i][j-1];
                }
            }
        }

        return dp[m-1][n-1];
    }

    /**
     * Minimum Path Sum in grid
     * Time: O(m*n), Space: O(1) - modify input array
     */
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        // Fill first row
        for (int j = 1; j < n; j++) {
            grid[0][j] += grid[0][j-1];
        }

        // Fill first column
        for (int i = 1; i < m; i++) {
            grid[i][0] += grid[i-1][0];
        }

        // Fill the rest
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                grid[i][j] += Math.min(grid[i-1][j], grid[i][j-1]);
            }
        }

        return grid[m-1][n-1];
    }

    /**
     * Maximum Path Sum in triangle
     * Time: O(n²), Space: O(1) - modify input
     */
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();

        // Start from second last row and work upwards
        for (int i = n - 2; i >= 0; i--) {
            for (int j = 0; j < triangle.get(i).size(); j++) {
                int current = triangle.get(i).get(j);
                int left = triangle.get(i + 1).get(j);
                int right = triangle.get(i + 1).get(j + 1);
                triangle.get(i).set(j, current + Math.min(left, right));
            }
        }

        return triangle.get(0).get(0);
    }

    // ==================== STRING MATCHING PROBLEMS ====================

    /**
     * Longest Common Subsequence (LCS)
     * Time: O(m*n), Space: O(m*n)
     */
    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[m][n];
    }

    /**
     * LCS - Space Optimized
     * Time: O(m*n), Space: O(min(m,n))
     */
    public int longestCommonSubsequenceOptimized(String text1, String text2) {
        // Make text1 the shorter string
        if (text1.length() > text2.length()) {
            return longestCommonSubsequenceOptimized(text2, text1);
        }

        int m = text1.length();
        int n = text2.length();
        int[] prev = new int[m + 1];
        int[] curr = new int[m + 1];

        for (int j = 1; j <= n; j++) {
            for (int i = 1; i <= m; i++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    curr[i] = prev[i - 1] + 1;
                } else {
                    curr[i] = Math.max(prev[i], curr[i - 1]);
                }
            }
            prev = curr.clone();
        }

        return curr[m];
    }

    /**
     * Edit Distance (Levenshtein Distance)
     * Time: O(m*n), Space: O(m*n)
     */
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        int[][] dp = new int[m + 1][n + 1];

        // Base cases
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i; // Delete all characters
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j; // Insert all characters
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1]; // No operation needed
                } else {
                    dp[i][j] = 1 + Math.min(
                            Math.min(dp[i - 1][j],    // Delete
                                    dp[i][j - 1]),    // Insert
                            dp[i - 1][j - 1]          // Replace
                    );
                }
            }
        }

        return dp[m][n];
    }

    /**
     * Distinct Subsequences
     * Time: O(m*n), Space: O(m*n)
     */
    public int numDistinct(String s, String t) {
        int m = s.length();
        int n = t.length();
        long[][] dp = new long[m + 1][n + 1];

        // Base case: empty string t can be formed in 1 way
        for (int i = 0; i <= m; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = dp[i - 1][j]; // Don't use s[i-1]

                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[i][j] += dp[i - 1][j - 1]; // Use s[i-1]
                }
            }
        }

        return (int) dp[m][n];
    }

    // ==================== MATRIX PROBLEMS ====================

    /**
     * Maximum Square in binary matrix
     * Time: O(m*n), Space: O(m*n)
     */
    public int maximalSquare(char[][] matrix) {
        if (matrix.length == 0) return 0;

        int m = matrix.length;
        int n = matrix[0].length;
        int[][] dp = new int[m + 1][n + 1];
        int maxLen = 0;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (matrix[i - 1][j - 1] == '1') {
                    dp[i][j] = Math.min(
                            Math.min(dp[i - 1][j], dp[i][j - 1]),
                            dp[i - 1][j - 1]
                    ) + 1;
                    maxLen = Math.max(maxLen, dp[i][j]);
                }
            }
        }

        return maxLen * maxLen;
    }

    /**
     * Maximal Rectangle in binary matrix
     * Time: O(m*n), Space: O(n)
     */
    public int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0) return 0;

        int m = matrix.length;
        int n = matrix[0].length;
        int[] heights = new int[n];
        int maxArea = 0;

        for (int i = 0; i < m; i++) {
            // Update heights for current row
            for (int j = 0; j < n; j++) {
                heights[j] = matrix[i][j] == '1' ? heights[j] + 1 : 0;
            }

            // Find max rectangle in histogram
            maxArea = Math.max(maxArea, largestRectangleArea(heights));
        }

        return maxArea;
    }

    private int largestRectangleArea(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int maxArea = 0;
        int n = heights.length;

        for (int i = 0; i <= n; i++) {
            int currentHeight = (i == n) ? 0 : heights[i];

            while (!stack.isEmpty() && heights[stack.peek()] > currentHeight) {
                int height = heights[stack.pop()];
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                maxArea = Math.max(maxArea, height * width);
            }

            stack.push(i);
        }

        return maxArea;
    }

    // ==================== INTERVAL DP PROBLEMS ====================

    /**
     * Matrix Chain Multiplication
     * Time: O(n³), Space: O(n²)
     */
    public int matrixChainOrder(int[] p) {
        int n = p.length - 1; // Number of matrices
        int[][] dp = new int[n][n];

        // l is chain length
        for (int l = 2; l <= n; l++) {
            for (int i = 0; i < n - l + 1; i++) {
                int j = i + l - 1;
                dp[i][j] = Integer.MAX_VALUE;

                for (int k = i; k < j; k++) {
                    int cost = dp[i][k] + dp[k + 1][j] + p[i] * p[k + 1] * p[j + 1];
                    dp[i][j] = Math.min(dp[i][j], cost);
                }
            }
        }

        return dp[0][n - 1];
    }

    /**
     * Palindrome Partitioning II - minimum cuts
     * Time: O(n²), Space: O(n²)
     */
    public int minCut(String s) {
        int n = s.length();
        boolean[][] isPalindrome = new boolean[n][n];

        // Precompute palindrome information
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                if (s.charAt(i) == s.charAt(j) && (i - j <= 2 || isPalindrome[j + 1][i - 1])) {
                    isPalindrome[j][i] = true;
                }
            }
        }

        // DP for minimum cuts
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            if (isPalindrome[0][i]) {
                dp[i] = 0;
            } else {
                dp[i] = Integer.MAX_VALUE;
                for (int j = 1; j <= i; j++) {
                    if (isPalindrome[j][i]) {
                        dp[i] = Math.min(dp[i], dp[j - 1] + 1);
                    }
                }
            }
        }

        return dp[n - 1];
    }

    /**
     * Burst Balloons
     * Time: O(n³), Space: O(n²)
     */
    public int maxCoins(int[] nums) {
        int n = nums.length;
        int[] arr = new int[n + 2];
        arr[0] = arr[n + 1] = 1; // Virtual balloons

        for (int i = 0; i < n; i++) {
            arr[i + 1] = nums[i];
        }

        int[][] dp = new int[n + 2][n + 2];

        for (int len = 1; len <= n; len++) {
            for (int left = 1; left <= n - len + 1; left++) {
                int right = left + len - 1;

                for (int k = left; k <= right; k++) {
                    int coins = arr[left - 1] * arr[k] * arr[right + 1];
                    coins += dp[left][k - 1] + dp[k + 1][right];
                    dp[left][right] = Math.max(dp[left][right], coins);
                }
            }
        }

        return dp[1][n];
    }

    // ==================== GAME THEORY DP ====================

    /**
     * Stone Game - predict winner
     * Time: O(n²), Space: O(n²)
     */
    public boolean stoneGame(int[] piles) {
        int n = piles.length;
        int[][] dp = new int[n][n];

        // Base case: single pile
        for (int i = 0; i < n; i++) {
            dp[i][i] = piles[i];
        }

        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                dp[i][j] = Math.max(
                        piles[i] - dp[i + 1][j],  // Take left pile
                        piles[j] - dp[i][j - 1]   // Take right pile
                );
            }
        }

        return dp[0][n - 1] > 0;
    }

    // ==================== UTILITY METHODS ====================

    /**
     * Print 2D DP table for debugging
     */
    public void print2DTable(int[][] dp, String problem) {
        System.out.println("2D DP Table for " + problem + ":");
        for (int[] row : dp) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println();
    }

    /**
     * Generic 2D DP template
     */
    public int generic2DDP(int m, int n, int[][] values) {
        int[][] dp = new int[m][n];

        // Initialize base cases
        dp[0][0] = values[0][0];

        // Fill first row and column
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i-1][0] + values[i][0];
        }
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j-1] + values[0][j];
        }

        // Fill the rest
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]) + values[i][j];
            }
        }

        return dp[m-1][n-1];
    }
}