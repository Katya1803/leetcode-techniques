package com.leetcode.algorithms.dynamicprogramming;

import java.util.*;

/**
 * Longest Subsequence Problems using Dynamic Programming
 * Various subsequence optimization problems
 *
 * Key Patterns:
 * 1. Longest Increasing Subsequence (LIS) and variants
 * 2. Longest Common Subsequence (LCS) and variants
 * 3. Longest Palindromic Subsequence
 * 4. Longest Arithmetic/Geometric Subsequence
 *
 * Subsequence: Elements in same relative order but not necessarily contiguous
 * Substring: Contiguous sequence of characters
 */
public class LongestSubsequence {

    // ==================== LONGEST INCREASING SUBSEQUENCE ====================

    /**
     * Longest Increasing Subsequence (LIS) - O(n²) approach
     * Time: O(n²), Space: O(n)
     */
    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0) return 0;

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
     * LIS with Binary Search optimization - O(n log n)
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
     * LIS with actual subsequence reconstruction
     * Time: O(n²), Space: O(n)
     */
    public List<Integer> findLIS(int[] nums) {
        if (nums.length == 0) return new ArrayList<>();

        int[] dp = new int[nums.length];
        int[] parent = new int[nums.length];
        Arrays.fill(dp, 1);
        Arrays.fill(parent, -1);

        int maxLength = 1;
        int maxIndex = 0;

        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    parent[i] = j;
                }
            }

            if (dp[i] > maxLength) {
                maxLength = dp[i];
                maxIndex = i;
            }
        }

        // Reconstruct LIS
        List<Integer> lis = new ArrayList<>();
        int current = maxIndex;
        while (current != -1) {
            lis.add(nums[current]);
            current = parent[current];
        }

        Collections.reverse(lis);
        return lis;
    }

    /**
     * Longest Decreasing Subsequence
     * Time: O(n²), Space: O(n)
     */
    public int lengthOfLDS(int[] nums) {
        if (nums.length == 0) return 0;

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

    /**
     * Longest Bitonic Subsequence (increasing then decreasing)
     * Time: O(n²), Space: O(n)
     */
    public int longestBitonicSubsequence(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;

        // LIS ending at each position
        int[] lis = new int[n];
        Arrays.fill(lis, 1);

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    lis[i] = Math.max(lis[i], lis[j] + 1);
                }
            }
        }

        // LDS starting from each position
        int[] lds = new int[n];
        Arrays.fill(lds, 1);

        for (int i = n - 2; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                if (nums[i] > nums[j]) {
                    lds[i] = Math.max(lds[i], lds[j] + 1);
                }
            }
        }

        int maxBitonic = 1;
        for (int i = 0; i < n; i++) {
            maxBitonic = Math.max(maxBitonic, lis[i] + lds[i] - 1);
        }

        return maxBitonic;
    }

    // ==================== LONGEST COMMON SUBSEQUENCE ====================

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
     * LCS with actual subsequence reconstruction
     * Time: O(m*n), Space: O(m*n)
     */
    public String findLCS(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        int[][] dp = new int[m + 1][n + 1];

        // Build DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // Reconstruct LCS
        StringBuilder lcs = new StringBuilder();
        int i = m, j = n;

        while (i > 0 && j > 0) {
            if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                lcs.append(text1.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        return lcs.reverse().toString();
    }

    /**
     * LCS of three strings
     * Time: O(m*n*p), Space: O(m*n*p)
     */
    public int longestCommonSubsequence3(String s1, String s2, String s3) {
        int m = s1.length(), n = s2.length(), p = s3.length();
        int[][][] dp = new int[m + 1][n + 1][p + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                for (int k = 1; k <= p; k++) {
                    if (s1.charAt(i - 1) == s2.charAt(j - 1) &&
                            s2.charAt(j - 1) == s3.charAt(k - 1)) {
                        dp[i][j][k] = dp[i - 1][j - 1][k - 1] + 1;
                    } else {
                        dp[i][j][k] = Math.max(Math.max(dp[i - 1][j][k], dp[i][j - 1][k]),
                                dp[i][j][k - 1]);
                    }
                }
            }
        }

        return dp[m][n][p];
    }

    /**
     * Shortest Common Supersequence
     * Time: O(m*n), Space: O(m*n)
     */
    public String shortestCommonSupersequence(String str1, String str2) {
        String lcs = findLCS(str1, str2);

        StringBuilder scs = new StringBuilder();
        int i = 0, j = 0, k = 0;

        while (k < lcs.length()) {
            // Add characters from str1 until LCS character
            while (i < str1.length() && str1.charAt(i) != lcs.charAt(k)) {
                scs.append(str1.charAt(i++));
            }

            // Add characters from str2 until LCS character
            while (j < str2.length() && str2.charAt(j) != lcs.charAt(k)) {
                scs.append(str2.charAt(j++));
            }

            // Add LCS character
            scs.append(lcs.charAt(k++));
            i++;
            j++;
        }

        // Add remaining characters
        scs.append(str1.substring(i));
        scs.append(str2.substring(j));

        return scs.toString();
    }

    // ==================== LONGEST PALINDROMIC SUBSEQUENCE ====================

    /**
     * Longest Palindromic Subsequence
     * Time: O(n²), Space: O(n²)
     */
    public int longestPalindromeSubseq(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];

        // Single characters are palindromes of length 1
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }

        // Check for palindromes of length 2 and more
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;

                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[0][n - 1];
    }

    /**
     * Count Palindromic Subsequences
     * Time: O(n²), Space: O(n²)
     */
    public int countPalindromicSubseq(String s) {
        int n = s.length();
        long[][] dp = new long[n][n];
        final int MOD = 1000000007;

        // Single characters
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }

        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;

                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = (dp[i + 1][j] + dp[i][j - 1] + 1) % MOD;
                } else {
                    dp[i][j] = (dp[i + 1][j] + dp[i][j - 1] - dp[i + 1][j - 1] + MOD) % MOD;
                }
            }
        }

        return (int) dp[0][n - 1];
    }

    // ==================== ARITHMETIC SUBSEQUENCES ====================

    /**
     * Longest Arithmetic Subsequence
     * Time: O(n²), Space: O(n²)
     */
    public int longestArithSeqLength(int[] nums) {
        int n = nums.length;
        if (n <= 2) return n;

        Map<Integer, Integer>[] dp = new Map[n];
        for (int i = 0; i < n; i++) {
            dp[i] = new HashMap<>();
        }

        int maxLength = 2;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                int diff = nums[i] - nums[j];
                int length = dp[j].getOrDefault(diff, 1) + 1;
                dp[i].put(diff, length);
                maxLength = Math.max(maxLength, length);
            }
        }

        return maxLength;
    }

    /**
     * Number of Arithmetic Slices (subarrays)
     * Time: O(n), Space: O(1)
     */
    public int numberOfArithmeticSlices(int[] nums) {
        if (nums.length < 3) return 0;

        int count = 0;
        int current = 0;

        for (int i = 2; i < nums.length; i++) {
            if (nums[i] - nums[i - 1] == nums[i - 1] - nums[i - 2]) {
                current++;
                count += current;
            } else {
                current = 0;
            }
        }

        return count;
    }

    // ==================== GEOMETRIC SUBSEQUENCES ====================

    /**
     * Longest Geometric Subsequence
     * Time: O(n² log(max_value)), Space: O(n²)
     */
    public int longestGeometricSubseq(int[] nums) {
        int n = nums.length;
        if (n <= 1) return n;

        Map<String, Integer> dp = new HashMap<>();
        int maxLength = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] == 0) continue;

                // Check if ratio is an integer
                if (nums[i] % nums[j] == 0) {
                    int ratio = nums[i] / nums[j];
                    String key = j + "," + ratio;
                    int length = dp.getOrDefault(key, 1) + 1;

                    String newKey = i + "," + ratio;
                    dp.put(newKey, length);
                    maxLength = Math.max(maxLength, length);
                }
            }
        }

        return maxLength;
    }

    // ==================== SPECIALIZED SUBSEQUENCES ====================

    /**
     * Russian Doll Envelopes (2D LIS)
     * Time: O(n log n), Space: O(n)
     */
    public int maxEnvelopes(int[][] envelopes) {
        Arrays.sort(envelopes, (a, b) -> {
            if (a[0] == b[0]) {
                return b[1] - a[1]; // Sort height in descending order for same width
            }
            return a[0] - b[0]; // Sort width in ascending order
        });

        // Extract heights and find LIS
        int[] heights = new int[envelopes.length];
        for (int i = 0; i < envelopes.length; i++) {
            heights[i] = envelopes[i][1];
        }

        return lengthOfLISOptimized(heights);
    }

    /**
     * Number of Longest Increasing Subsequence
     * Time: O(n²), Space: O(n)
     */
    public int findNumberOfLIS(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;

        int[] lengths = new int[n]; // lengths[i] = length of LIS ending at i
        int[] counts = new int[n];  // counts[i] = number of LIS ending at i

        Arrays.fill(lengths, 1);
        Arrays.fill(counts, 1);

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    if (lengths[j] + 1 > lengths[i]) {
                        lengths[i] = lengths[j] + 1;
                        counts[i] = counts[j];
                    } else if (lengths[j] + 1 == lengths[i]) {
                        counts[i] += counts[j];
                    }
                }
            }
        }

        int maxLength = Arrays.stream(lengths).max().orElse(0);
        int result = 0;

        for (int i = 0; i < n; i++) {
            if (lengths[i] == maxLength) {
                result += counts[i];
            }
        }

        return result;
    }

    // ==================== UTILITY METHODS ====================

    /**
     * Check if array contains increasing subsequence of length k
     * Time: O(n log k), Space: O(k)
     */
    public boolean hasIncreasingSubsequence(int[] nums, int k) {
        if (k <= 0) return true;
        if (nums.length < k) return false;

        List<Integer> tails = new ArrayList<>();

        for (int num : nums) {
            int pos = binarySearch(tails, num);

            if (pos == tails.size()) {
                tails.add(num);
                if (tails.size() >= k) return true;
            } else {
                tails.set(pos, num);
            }
        }

        return tails.size() >= k;
    }

    /**
     * Find all LIS in the array
     * Time: O(n² × 2^n) worst case, Space: O(n × 2^n)
     */
    public List<List<Integer>> findAllLIS(int[] nums) {
        if (nums.length == 0) return new ArrayList<>();

        int maxLength = lengthOfLIS(nums);
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();

        findAllLISHelper(nums, 0, current, result, maxLength, Integer.MIN_VALUE);
        return result;
    }

    private void findAllLISHelper(int[] nums, int index, List<Integer> current,
                                  List<List<Integer>> result, int targetLength, int lastValue) {
        if (current.size() == targetLength) {
            result.add(new ArrayList<>(current));
            return;
        }

        if (index >= nums.length || current.size() + (nums.length - index) < targetLength) {
            return;
        }

        for (int i = index; i < nums.length; i++) {
            if (nums[i] > lastValue) {
                current.add(nums[i]);
                if (current.size() + lengthOfLISFromIndex(nums, i + 1, nums[i]) >= targetLength) {
                    findAllLISHelper(nums, i + 1, current, result, targetLength, nums[i]);
                }
                current.remove(current.size() - 1);
            }
        }
    }

    private int lengthOfLISFromIndex(int[] nums, int startIndex, int minValue) {
        List<Integer> tails = new ArrayList<>();

        for (int i = startIndex; i < nums.length; i++) {
            if (nums[i] > minValue) {
                int pos = binarySearch(tails, nums[i]);
                if (pos == tails.size()) {
                    tails.add(nums[i]);
                } else {
                    tails.set(pos, nums[i]);
                }
            }
        }

        return tails.size();
    }

    /**
     * Print all subsequences of given length
     * Time: O(n × C(n,k)), Space: O(k)
     */
    public void printSubsequences(int[] nums, int length) {
        List<Integer> current = new ArrayList<>();
        printSubsequencesHelper(nums, 0, current, length);
    }

    private void printSubsequencesHelper(int[] nums, int index, List<Integer> current, int remaining) {
        if (remaining == 0) {
            System.out.println(current);
            return;
        }

        if (index >= nums.length || remaining > nums.length - index) {
            return;
        }

        // Include current element
        current.add(nums[index]);
        printSubsequencesHelper(nums, index + 1, current, remaining - 1);
        current.remove(current.size() - 1);

        // Exclude current element
        printSubsequencesHelper(nums, index + 1, current, remaining);
    }
}