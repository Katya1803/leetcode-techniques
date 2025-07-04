package com.leetcode.algorithms.advanced;

import java.util.*;

/**
 * Bit Manipulation Algorithms and Techniques
 *
 * This class provides comprehensive implementations of bit manipulation operations
 * and algorithms that efficiently solve problems using bitwise operations.
 */
public class BitManipulation {

    // =========================== Basic Bit Operations ===========================

    /**
     * Check if k-th bit is set (0-indexed from right)
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public boolean isBitSet(int num, int k) {
        return (num & (1 << k)) != 0;
    }

    /**
     * Set k-th bit (0-indexed from right)
     */
    public int setBit(int num, int k) {
        return num | (1 << k);
    }

    /**
     * Clear k-th bit (0-indexed from right)
     */
    public int clearBit(int num, int k) {
        return num & ~(1 << k);
    }

    /**
     * Toggle k-th bit (0-indexed from right)
     */
    public int toggleBit(int num, int k) {
        return num ^ (1 << k);
    }

    /**
     * Get value of k-th bit (0 or 1)
     */
    public int getBit(int num, int k) {
        return (num >> k) & 1;
    }

    // =========================== Power of 2 Operations ===========================

    /**
     * Check if number is power of 2
     * Uses property: power of 2 has exactly one bit set
     * Time Complexity: O(1)
     */
    public boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }

    /**
     * Find next power of 2 greater than or equal to n
     * Time Complexity: O(log n)
     */
    public int nextPowerOfTwo(int n) {
        if (n <= 1) return 1;

        n--;
        n |= n >> 1;
        n |= n >> 2;
        n |= n >> 4;
        n |= n >> 8;
        n |= n >> 16;

        return n + 1;
    }

    /**
     * Check if number is power of 4
     * Time Complexity: O(1)
     */
    public boolean isPowerOfFour(int n) {
        // Must be power of 2 and have bit set at even position
        return n > 0 && (n & (n - 1)) == 0 && (n & 0x55555555) != 0;
    }

    // =========================== Bit Counting ===========================

    /**
     * Count number of set bits (Hamming Weight) - Brian Kernighan's Algorithm
     * Time Complexity: O(number of set bits)
     * Space Complexity: O(1)
     */
    public int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            n &= (n - 1); // Remove rightmost set bit
            count++;
        }
        return count;
    }

    /**
     * Count set bits using built-in function
     */
    public int hammingWeightBuiltIn(int n) {
        return Integer.bitCount(n);
    }

    /**
     * Count set bits for all numbers from 0 to n
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public int[] countBits(int n) {
        int[] result = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            // result[i] = result[i >> 1] + (i & 1)
            result[i] = result[i / 2] + (i % 2);
        }

        return result;
    }

    /**
     * Hamming Distance between two numbers
     * Time Complexity: O(1)
     */
    public int hammingDistance(int x, int y) {
        return hammingWeight(x ^ y);
    }

    /**
     * Total Hamming Distance for all pairs in array
     * Time Complexity: O(32n) = O(n)
     * Space Complexity: O(1)
     */
    public int totalHammingDistance(int[] nums) {
        int total = 0;
        int n = nums.length;

        for (int i = 0; i < 32; i++) {
            int ones = 0;

            // Count numbers with i-th bit set
            for (int num : nums) {
                ones += (num >> i) & 1;
            }

            // Each pair with different i-th bit contributes 1 to distance
            total += ones * (n - ones);
        }

        return total;
    }

    // =========================== Single Number Problems ===========================

    /**
     * Find single number when all others appear twice
     * Uses XOR property: a ^ a = 0, a ^ 0 = a
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num;
        }
        return result;
    }

    /**
     * Find single number when all others appear three times
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public int singleNumberII(int[] nums) {
        int ones = 0, twos = 0;

        for (int num : nums) {
            ones = (ones ^ num) & ~twos;
            twos = (twos ^ num) & ~ones;
        }

        return ones;
    }

    /**
     * Find two single numbers when all others appear twice
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public int[] singleNumberIII(int[] nums) {
        int xor = 0;
        for (int num : nums) {
            xor ^= num;
        }

        // Find rightmost set bit in xor
        int rightmostSetBit = xor & (-xor);

        int num1 = 0, num2 = 0;
        for (int num : nums) {
            if ((num & rightmostSetBit) != 0) {
                num1 ^= num;
            } else {
                num2 ^= num;
            }
        }

        return new int[]{num1, num2};
    }

    // =========================== Subset Generation ===========================

    /**
     * Generate all subsets using bit manipulation
     * Time Complexity: O(2^n * n)
     * Space Complexity: O(2^n * n)
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;

        for (int mask = 0; mask < (1 << n); mask++) {
            List<Integer> subset = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    subset.add(nums[i]);
                }
            }

            result.add(subset);
        }

        return result;
    }

    /**
     * Generate all subsets of size k
     * Time Complexity: O(C(n,k) * k)
     */
    public List<List<Integer>> subsetsOfSizeK(int[] nums, int k) {
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;

        for (int mask = 0; mask < (1 << n); mask++) {
            if (Integer.bitCount(mask) == k) {
                List<Integer> subset = new ArrayList<>();

                for (int i = 0; i < n; i++) {
                    if ((mask & (1 << i)) != 0) {
                        subset.add(nums[i]);
                    }
                }

                result.add(subset);
            }
        }

        return result;
    }

    // =========================== Bit Manipulation Tricks ===========================

    /**
     * Swap two numbers without temporary variable
     */
    public void swapNumbers(int[] nums, int i, int j) {
        if (i != j) {
            nums[i] ^= nums[j];
            nums[j] ^= nums[i];
            nums[i] ^= nums[j];
        }
    }

    /**
     * Find minimum and maximum without branching
     */
    public int[] minMaxWithoutBranching(int a, int b) {
        int min = b ^ ((a ^ b) & -(a < b ? 1 : 0));
        int max = a ^ ((a ^ b) & -(a < b ? 1 : 0));
        return new int[]{min, max};
    }

    /**
     * Check if number has alternating bits (01010... or 10101...)
     */
    public boolean hasAlternatingBits(int n) {
        int xor = n ^ (n >> 1);
        return (xor & (xor + 1)) == 0;
    }

    /**
     * Reverse bits of a 32-bit unsigned integer
     * Time Complexity: O(log n)
     */
    public int reverseBits(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            result = (result << 1) | (n & 1);
            n >>= 1;
        }
        return result;
    }

    /**
     * Add two numbers without using + operator
     * Time Complexity: O(log n)
     */
    public int addWithoutPlus(int a, int b) {
        while (b != 0) {
            int carry = a & b;
            a = a ^ b;
            b = carry << 1;
        }
        return a;
    }

    /**
     * Multiply two numbers using bit shifting
     * Time Complexity: O(log n)
     */
    public int multiplyUsingBits(int a, int b) {
        int result = 0;
        while (b > 0) {
            if ((b & 1) != 0) {
                result += a;
            }
            a <<= 1;
            b >>= 1;
        }
        return result;
    }

    // =========================== Advanced Bit Patterns ===========================

    /**
     * Find missing number in array containing n distinct numbers from 0 to n
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public int missingNumber(int[] nums) {
        int missing = nums.length;
        for (int i = 0; i < nums.length; i++) {
            missing ^= i ^ nums[i];
        }
        return missing;
    }

    /**
     * Find duplicate number in array where each number appears once except one
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public int findDuplicate(int[] nums) {
        int duplicate = 0;

        for (int i = 0; i < nums.length; i++) {
            duplicate ^= (i + 1) ^ nums[i];
        }

        return duplicate;
    }

    /**
     * Maximum XOR of two numbers in array
     * Time Complexity: O(32n) = O(n)
     * Space Complexity: O(1)
     */
    public int findMaximumXOR(int[] nums) {
        int maxXor = 0;
        int mask = 0;

        for (int i = 30; i >= 0; i--) {
            mask |= (1 << i);
            Set<Integer> prefixes = new HashSet<>();

            for (int num : nums) {
                prefixes.add(num & mask);
            }

            int candidate = maxXor | (1 << i);

            for (int prefix : prefixes) {
                if (prefixes.contains(candidate ^ prefix)) {
                    maxXor = candidate;
                    break;
                }
            }
        }

        return maxXor;
    }

    /**
     * Count number of 1 bits in binary representation of all numbers from 1 to n
     * Time Complexity: O(log n)
     * Space Complexity: O(1)
     */
    public int countOnes(int n) {
        int count = 0;

        for (int bit = 1; bit <= n; bit <<= 1) {
            int cycle = bit << 1;
            int complete = n / cycle;
            int remainder = n % cycle;

            count += complete * bit;
            count += Math.max(0, remainder - bit + 1);
        }

        return count;
    }

    // =========================== Bit Manipulation in DP ===========================

    /**
     * Traveling Salesman Problem using bitmask DP
     * Time Complexity: O(n^2 * 2^n)
     * Space Complexity: O(n * 2^n)
     */
    public int travelingSalesman(int[][] dist) {
        int n = dist.length;
        int[][] dp = new int[1 << n][n];

        // Initialize with infinity
        for (int[] row : dp) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        dp[1][0] = 0; // Start at city 0

        for (int mask = 1; mask < (1 << n); mask++) {
            for (int u = 0; u < n; u++) {
                if ((mask & (1 << u)) == 0 || dp[mask][u] == Integer.MAX_VALUE) {
                    continue;
                }

                for (int v = 0; v < n; v++) {
                    if ((mask & (1 << v)) != 0) continue; // Already visited

                    int newMask = mask | (1 << v);
                    if (dp[mask][u] + dist[u][v] < dp[newMask][v]) {
                        dp[newMask][v] = dp[mask][u] + dist[u][v];
                    }
                }
            }
        }

        int result = Integer.MAX_VALUE;
        int finalMask = (1 << n) - 1;
        for (int i = 1; i < n; i++) {
            if (dp[finalMask][i] != Integer.MAX_VALUE) {
                result = Math.min(result, dp[finalMask][i] + dist[i][0]);
            }
        }

        return result == Integer.MAX_VALUE ? -1 : result;
    }

    /**
     * Minimum cost to make array palindrome using bit manipulation for states
     * Time Complexity: O(n * 2^n)
     */
    public int minCostPalindrome(int[] arr, int[][] cost) {
        int n = arr.length;
        Map<Integer, Integer> memo = new HashMap<>();

        return minCostHelper(arr, cost, 0, n - 1, 0, memo);
    }

    private int minCostHelper(int[] arr, int[][] cost, int left, int right,
                              int mask, Map<Integer, Integer> memo) {
        if (left >= right) return 0;

        int key = (left << 16) | (right << 8) | mask;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        int result = Integer.MAX_VALUE;

        // Try all possible values for both positions
        for (int val1 = 0; val1 < 10; val1++) {
            for (int val2 = 0; val2 < 10; val2++) {
                if (val1 == val2) { // Must be equal for palindrome
                    int currentCost = cost[left][val1] + cost[right][val2];
                    int remainingCost = minCostHelper(arr, cost, left + 1, right - 1, mask, memo);

                    if (remainingCost != Integer.MAX_VALUE) {
                        result = Math.min(result, currentCost + remainingCost);
                    }
                }
            }
        }

        memo.put(key, result);
        return result;
    }

    // =========================== Utility Methods ===========================

    /**
     * Convert integer to binary string with leading zeros
     */
    public String toBinaryString(int num, int width) {
        String binary = Integer.toBinaryString(num);
        StringBuilder sb = new StringBuilder();

        // Add leading zeros
        for (int i = binary.length(); i < width; i++) {
            sb.append('0');
        }
        sb.append(binary);

        return sb.toString();
    }

    /**
     * Print all subsets with their binary representations
     */
    public void printSubsetsWithBinary(int[] nums) {
        int n = nums.length;

        System.out.println("All subsets with binary representation:");
        for (int mask = 0; mask < (1 << n); mask++) {
            System.out.print("Mask " + toBinaryString(mask, n) + ": {");

            boolean first = true;
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    if (!first) System.out.print(", ");
                    System.out.print(nums[i]);
                    first = false;
                }
            }

            System.out.println("}");
        }
    }

    /**
     * Check if two numbers have same number of set bits
     */
    public boolean haveSamePopCount(int a, int b) {
        return Integer.bitCount(a) == Integer.bitCount(b);
    }

    /**
     * Find position of rightmost set bit (1-indexed)
     */
    public int rightmostSetBit(int n) {
        if (n == 0) return -1;
        return Integer.numberOfTrailingZeros(n) + 1;
    }

    /**
     * Find position of leftmost set bit (1-indexed from right)
     */
    public int leftmostSetBit(int n) {
        if (n == 0) return -1;
        return 32 - Integer.numberOfLeadingZeros(n);
    }

    /**
     * Check if only one bit is different between two numbers
     */
    public boolean oneBitDifferent(int a, int b) {
        int xor = a ^ b;
        return xor != 0 && (xor & (xor - 1)) == 0;
    }

    /**
     * Gray Code generation
     * Time Complexity: O(2^n)
     */
    public List<Integer> grayCode(int n) {
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < (1 << n); i++) {
            result.add(i ^ (i >> 1));
        }

        return result;
    }

    /**
     * Validate bit manipulation result
     */
    public boolean validateBitResult(int input, int expected, int actual) {
        boolean isValid = expected == actual;

        if (!isValid) {
            System.out.println("Validation failed:");
            System.out.println("Input: " + toBinaryString(input, 32));
            System.out.println("Expected: " + toBinaryString(expected, 32));
            System.out.println("Actual: " + toBinaryString(actual, 32));
        }

        return isValid;
    }
}