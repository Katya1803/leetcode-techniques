package com.leetcode.algorithms.backtracking;

import java.util.*;

/**
 * Combinations - Generate all possible selections of elements
 *
 * This class provides comprehensive implementations for generating combinations,
 * subsets, and related combinatorial structures with various constraints.
 *
 * Key Concepts:
 * - Combination: Selection of items without regard to order
 * - Subset: Any combination of elements (including empty set)
 * - Power Set: Set of all subsets
 * - Binomial coefficient: C(n,k) = n!/(k!(n-k)!)
 *
 * Mathematical Foundation:
 * - C(n,k) combinations of k elements from n elements
 * - 2^n total subsets for n elements
 * - Pascal's Triangle: C(n,k) = C(n-1,k-1) + C(n-1,k)
 *
 * Applications:
 * - Feature selection in machine learning
 * - Lottery and probability calculations
 * - Subset sum problems
 * - Portfolio optimization
 * - Test case generation
 */
public class Combinations {

    // =========================== Basic Combinations ===========================

    /**
     * Generate all combinations of k elements from range [1, n]
     * Time Complexity: O(C(n,k) × k)
     * Space Complexity: O(C(n,k) × k)
     *
     * @param n upper limit of range
     * @param k number of elements to select
     * @return list of all combinations
     */
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();

        generateCombinations(1, n, k, current, result);
        return result;
    }

    private void generateCombinations(int start, int n, int k,
                                      List<Integer> current, List<List<Integer>> result) {
        // Base case: combination complete
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }

        // Pruning: if remaining numbers insufficient, return early
        int remaining = k - current.size();
        int available = n - start + 1;
        if (remaining > available) return;

        // Try each number from start to n
        for (int i = start; i <= n; i++) {
            // Make choice
            current.add(i);

            // Recurse with next starting position
            generateCombinations(i + 1, n, k, current, result);

            // Backtrack
            current.remove(current.size() - 1);
        }
    }

    /**
     * Generate all combinations of k elements from given array
     * Time Complexity: O(C(n,k) × k)
     * Space Complexity: O(C(n,k) × k)
     *
     * @param nums input array
     * @param k number of elements to select
     * @return list of all combinations
     */
    public List<List<Integer>> combineArray(int[] nums, int k) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();

        generateArrayCombinations(nums, 0, k, current, result);
        return result;
    }

    private void generateArrayCombinations(int[] nums, int start, int k,
                                           List<Integer> current, List<List<Integer>> result) {
        // Base case: combination complete
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }

        // Pruning optimization
        int remaining = k - current.size();
        int available = nums.length - start;
        if (remaining > available) return;

        // Try each element from start position
        for (int i = start; i < nums.length; i++) {
            current.add(nums[i]);
            generateArrayCombinations(nums, i + 1, k, current, result);
            current.remove(current.size() - 1);
        }
    }

    // =========================== All Subsets (Power Set) ===========================

    /**
     * Generate all subsets of given array (Power Set)
     * Time Complexity: O(2^n × n)
     * Space Complexity: O(2^n × n)
     *
     * @param nums input array
     * @return list of all subsets
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();

        generateSubsets(nums, 0, current, result);
        return result;
    }

    private void generateSubsets(int[] nums, int index, List<Integer> current,
                                 List<List<Integer>> result) {
        // Base case: processed all elements
        if (index == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }

        // Choice 1: Don't include current element
        generateSubsets(nums, index + 1, current, result);

        // Choice 2: Include current element
        current.add(nums[index]);
        generateSubsets(nums, index + 1, current, result);
        current.remove(current.size() - 1); // Backtrack
    }

    /**
     * Generate all subsets using iterative approach
     * Time Complexity: O(2^n × n)
     * Space Complexity: O(2^n × n)
     *
     * @param nums input array
     * @return list of all subsets
     */
    public List<List<Integer>> subsetsIterative(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>()); // Empty subset

        for (int num : nums) {
            int size = result.size();
            for (int i = 0; i < size; i++) {
                List<Integer> newSubset = new ArrayList<>(result.get(i));
                newSubset.add(num);
                result.add(newSubset);
            }
        }

        return result;
    }

    /**
     * Generate all subsets using bit manipulation
     * Time Complexity: O(2^n × n)
     * Space Complexity: O(2^n × n)
     *
     * @param nums input array
     * @return list of all subsets
     */
    public List<List<Integer>> subsetsBitManipulation(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;

        // Generate all numbers from 0 to 2^n - 1
        for (int mask = 0; mask < (1 << n); mask++) {
            List<Integer> subset = new ArrayList<>();

            // Check each bit position
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    subset.add(nums[i]);
                }
            }

            result.add(subset);
        }

        return result;
    }

    // =========================== Subsets with Duplicates ===========================

    /**
     * Generate all unique subsets when array contains duplicates
     * Time Complexity: O(2^n × n)
     * Space Complexity: O(2^n × n)
     *
     * @param nums input array that may contain duplicates
     * @return list of unique subsets
     */
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();

        // Sort to group duplicates together
        Arrays.sort(nums);

        generateUniqueSubsets(nums, 0, current, result);
        return result;
    }

    private void generateUniqueSubsets(int[] nums, int start, List<Integer> current,
                                       List<List<Integer>> result) {
        // Add current subset
        result.add(new ArrayList<>(current));

        for (int i = start; i < nums.length; i++) {
            // Skip duplicates: only use first occurrence at each level
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }

            current.add(nums[i]);
            generateUniqueSubsets(nums, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }

    // =========================== Combination Sum Problems ===========================

    /**
     * Find all combinations that sum to target (elements can be reused)
     * Time Complexity: O(n^(target/min_element))
     * Space Complexity: O(target/min_element) for recursion depth
     *
     * @param candidates array of distinct positive integers
     * @param target target sum
     * @return list of all combinations that sum to target
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();

        Arrays.sort(candidates); // Sort for optimization
        generateCombinationSum(candidates, 0, target, current, result);
        return result;
    }

    private void generateCombinationSum(int[] candidates, int start, int target,
                                        List<Integer> current, List<List<Integer>> result) {
        // Base case: target reached
        if (target == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        // Base case: target exceeded
        if (target < 0) {
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            // Pruning: if current candidate > target, no need to continue
            if (candidates[i] > target) {
                break;
            }

            current.add(candidates[i]);
            // Note: use i (not i+1) to allow reuse of same element
            generateCombinationSum(candidates, i, target - candidates[i], current, result);
            current.remove(current.size() - 1);
        }
    }

    /**
     * Find all combinations that sum to target (each element used at most once)
     * Time Complexity: O(2^n)
     * Space Complexity: O(n) for recursion depth
     *
     * @param candidates array that may contain duplicates
     * @param target target sum
     * @return list of all unique combinations that sum to target
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();

        Arrays.sort(candidates);
        generateCombinationSum2(candidates, 0, target, current, result);
        return result;
    }

    private void generateCombinationSum2(int[] candidates, int start, int target,
                                         List<Integer> current, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        if (target < 0) {
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            // Skip duplicates at same level
            if (i > start && candidates[i] == candidates[i - 1]) {
                continue;
            }

            if (candidates[i] > target) {
                break;
            }

            current.add(candidates[i]);
            // Use i+1 to ensure each element used at most once
            generateCombinationSum2(candidates, i + 1, target - candidates[i], current, result);
            current.remove(current.size() - 1);
        }
    }

    /**
     * Find all combinations of k numbers that sum to target from range [1,9]
     * Time Complexity: O(C(9,k))
     * Space Complexity: O(k) for recursion depth
     *
     * @param k number of elements to select
     * @param n range limit (1 to n)
     * @param target target sum
     * @return list of all combinations
     */
    public List<List<Integer>> combinationSum3(int k, int n, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();

        generateCombinationSum3(1, k, n, target, current, result);
        return result;
    }

    private void generateCombinationSum3(int start, int k, int n, int target,
                                         List<Integer> current, List<List<Integer>> result) {
        // Base case: found valid combination
        if (current.size() == k && target == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        // Pruning conditions
        if (current.size() >= k || target <= 0) {
            return;
        }

        for (int i = start; i <= n; i++) {
            // Pruning: if current number > remaining target
            if (i > target) {
                break;
            }

            current.add(i);
            generateCombinationSum3(i + 1, k, n, target - i, current, result);
            current.remove(current.size() - 1);
        }
    }

    // =========================== Letter Combinations ===========================

    /**
     * Generate all letter combinations from phone number digits
     * Time Complexity: O(4^n) where n is number of digits
     * Space Complexity: O(4^n) for storing results
     *
     * @param digits string containing digits 2-9
     * @return list of all possible letter combinations
     */
    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();

        if (digits == null || digits.length() == 0) {
            return result;
        }

        String[] phoneMap = {
                "",     // 0
                "",     // 1
                "abc",  // 2
                "def",  // 3
                "ghi",  // 4
                "jkl",  // 5
                "mno",  // 6
                "pqrs", // 7
                "tuv",  // 8
                "wxyz"  // 9
        };

        generateLetterCombinations(digits, 0, new StringBuilder(), phoneMap, result);
        return result;
    }

    private void generateLetterCombinations(String digits, int index, StringBuilder current,
                                            String[] phoneMap, List<String> result) {
        // Base case: combination complete
        if (index == digits.length()) {
            result.add(current.toString());
            return;
        }

        int digit = digits.charAt(index) - '0';
        String letters = phoneMap[digit];

        for (char letter : letters.toCharArray()) {
            current.append(letter);
            generateLetterCombinations(digits, index + 1, current, phoneMap, result);
            current.deleteCharAt(current.length() - 1); // Backtrack
        }
    }

    // =========================== Advanced Combinations ===========================

    /**
     * Generate all combinations with replacement (multiset)
     * Time Complexity: O(C(n+k-1, k) × k)
     * Space Complexity: O(C(n+k-1, k) × k)
     *
     * @param nums array of elements
     * @param k number of elements to select (with replacement)
     * @return list of all combinations with replacement
     */
    public List<List<Integer>> combinationsWithReplacement(int[] nums, int k) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();

        Arrays.sort(nums);
        generateCombinationsWithReplacement(nums, 0, k, current, result);
        return result;
    }

    private void generateCombinationsWithReplacement(int[] nums, int start, int k,
                                                     List<Integer> current, List<List<Integer>> result) {
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = start; i < nums.length; i++) {
            current.add(nums[i]);
            // Use i (not i+1) to allow replacement
            generateCombinationsWithReplacement(nums, i, k, current, result);
            current.remove(current.size() - 1);
        }
    }

    /**
     * Generate all k-combinations in lexicographical order
     * Time Complexity: O(C(n,k) × k)
     * Space Complexity: O(C(n,k) × k)
     *
     * @param n range limit (1 to n)
     * @param k number of elements to select
     * @return list of combinations in lexicographical order
     */
    public List<List<Integer>> combinationsLexicographic(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();

        // Generate combinations iteratively using next combination algorithm
        List<Integer> current = new ArrayList<>();

        // Initialize first combination: [1, 2, ..., k]
        for (int i = 1; i <= k; i++) {
            current.add(i);
        }

        do {
            result.add(new ArrayList<>(current));
        } while (nextCombination(current, n, k));

        return result;
    }

    /**
     * Generate next combination in lexicographical order
     *
     * @param combination current combination (modified in place)
     * @param n upper limit
     * @param k size of combination
     * @return true if next combination exists, false if current is last
     */
    private boolean nextCombination(List<Integer> combination, int n, int k) {
        // Find rightmost element that can be incremented
        int i = k - 1;
        while (i >= 0 && combination.get(i) == n - k + i + 1) {
            i--;
        }

        if (i < 0) {
            return false; // Last combination
        }

        // Increment the found element
        combination.set(i, combination.get(i) + 1);

        // Set subsequent elements
        for (int j = i + 1; j < k; j++) {
            combination.set(j, combination.get(i) + j - i);
        }

        return true;
    }

    // =========================== Subset Sum Problems ===========================

    /**
     * Find all subsets that sum to target
     * Time Complexity: O(2^n)
     * Space Complexity: O(2^n) in worst case
     *
     * @param nums input array
     * @param target target sum
     * @return list of all subsets that sum to target
     */
    public List<List<Integer>> subsetSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();

        generateSubsetSum(nums, 0, target, current, result);
        return result;
    }

    private void generateSubsetSum(int[] nums, int index, int target,
                                   List<Integer> current, List<List<Integer>> result) {
        // Base case: target reached
        if (target == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        // Base case: processed all elements or target negative
        if (index >= nums.length || target < 0) {
            return;
        }

        // Choice 1: Include current element
        current.add(nums[index]);
        generateSubsetSum(nums, index + 1, target - nums[index], current, result);
        current.remove(current.size() - 1);

        // Choice 2: Exclude current element
        generateSubsetSum(nums, index + 1, target, current, result);
    }

    /**
     * Check if there exists a subset that sums to target
     * Time Complexity: O(2^n)
     * Space Complexity: O(n) for recursion stack
     *
     * @param nums input array
     * @param target target sum
     * @return true if subset exists
     */
    public boolean hasSubsetSum(int[] nums, int target) {
        return hasSubsetSumHelper(nums, 0, target);
    }

    private boolean hasSubsetSumHelper(int[] nums, int index, int target) {
        if (target == 0) return true;
        if (index >= nums.length || target < 0) return false;

        // Try including or excluding current element
        return hasSubsetSumHelper(nums, index + 1, target - nums[index]) ||
                hasSubsetSumHelper(nums, index + 1, target);
    }

    // =========================== Utility Methods ===========================

    /**
     * Calculate binomial coefficient C(n, k)
     * Time Complexity: O(min(k, n-k))
     * Space Complexity: O(1)
     *
     * @param n total elements
     * @param k elements to choose
     * @return binomial coefficient
     */
    public long binomialCoefficient(int n, int k) {
        if (k > n - k) {
            k = n - k; // Take advantage of symmetry
        }

        long result = 1;
        for (int i = 0; i < k; i++) {
            result = result * (n - i) / (i + 1);
        }

        return result;
    }

    /**
     * Calculate total number of subsets
     *
     * @param n number of elements
     * @return 2^n (total subsets)
     */
    public long totalSubsets(int n) {
        return 1L << n; // 2^n
    }

    /**
     * Generate Pascal's triangle up to row n
     * Time Complexity: O(n²)
     * Space Complexity: O(n²)
     *
     * @param numRows number of rows
     * @return Pascal's triangle
     */
    public List<List<Integer>> generatePascalTriangle(int numRows) {
        List<List<Integer>> triangle = new ArrayList<>();

        for (int i = 0; i < numRows; i++) {
            List<Integer> row = new ArrayList<>();

            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    row.add(1);
                } else {
                    int value = triangle.get(i - 1).get(j - 1) + triangle.get(i - 1).get(j);
                    row.add(value);
                }
            }

            triangle.add(row);
        }

        return triangle;
    }

    /**
     * Check if combination is valid
     *
     * @param combination list to check
     * @param n upper limit
     * @param k expected size
     * @return true if valid k-combination from [1,n]
     */
    public boolean isValidCombination(List<Integer> combination, int n, int k) {
        if (combination.size() != k) {
            return false;
        }

        Set<Integer> seen = new HashSet<>();
        for (int num : combination) {
            if (num < 1 || num > n || seen.contains(num)) {
                return false;
            }
            seen.add(num);
        }

        // Check if sorted (combinations are typically in ascending order)
        for (int i = 1; i < combination.size(); i++) {
            if (combination.get(i) <= combination.get(i - 1)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Convert combination to bit mask
     *
     * @param combination list of selected indices (0-based)
     * @param n total number of elements
     * @return bit mask representation
     */
    public long combinationToBitMask(List<Integer> combination, int n) {
        long mask = 0;
        for (int index : combination) {
            if (index >= 0 && index < n) {
                mask |= (1L << index);
            }
        }
        return mask;
    }

    /**
     * Convert bit mask to combination
     *
     * @param mask bit mask
     * @param n total number of elements
     * @return list of selected indices
     */
    public List<Integer> bitMaskToCombination(long mask, int n) {
        List<Integer> combination = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if ((mask & (1L << i)) != 0) {
                combination.add(i);
            }
        }
        return combination;
    }

    /**
     * Print combinations in formatted way
     *
     * @param combinations list of combinations to print
     * @param label description label
     */
    public static void printCombinations(List<List<Integer>> combinations, String label) {
        System.out.println(label + " (" + combinations.size() + " combinations):");
        for (int i = 0; i < combinations.size(); i++) {
            System.out.println((i + 1) + ": " + combinations.get(i));
        }
        System.out.println();
    }

    /**
     * Get k-th combination in lexicographical order (0-indexed)
     * Time Complexity: O(k)
     * Space Complexity: O(k)
     *
     * @param n range limit (1 to n)
     * @param k size of combination
     * @param index 0-indexed position in lexicographical order
     * @return k-th combination
     */
    public List<Integer> getKthCombination(int n, int k, long index) {
        List<Integer> result = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();

        // Initialize available numbers
        for (int i = 1; i <= n; i++) {
            numbers.add(i);
        }

        for (int i = 0; i < k; i++) {
            long combinations = binomialCoefficient(numbers.size() - 1, k - 1 - i);
            int pos = (int) (index / combinations);

            result.add(numbers.remove(pos));
            index %= combinations;
        }

        return result;
    }
}