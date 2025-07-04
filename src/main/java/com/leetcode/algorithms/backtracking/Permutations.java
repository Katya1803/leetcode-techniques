package com.leetcode.algorithms.backtracking;

import java.util.*;

/**
 * Permutations - Generate all possible arrangements of elements
 *
 * This class provides comprehensive implementations for generating permutations
 * of arrays, strings, and numbers with various constraints and optimizations.
 *
 * Key Concepts:
 * - Permutation: An arrangement of objects in a specific order
 * - With/without duplicates handling
 * - Lexicographical ordering
 * - Next permutation generation
 *
 * Mathematical Foundation:
 * - n! permutations for n distinct elements
 * - n!/(n1! × n2! × ... × nk!) for elements with repetitions
 *
 * Applications:
 * - Generating test cases
 * - Combinatorial optimization
 * - Cryptography and security
 * - Game development
 * - String manipulation problems
 */
public class Permutations {

    // =========================== Basic Permutations ===========================

    /**
     * Generate all permutations of distinct integers
     * Time Complexity: O(n! × n)
     * Space Complexity: O(n! × n) for storing all permutations + O(n) for recursion
     *
     * @param nums array of distinct integers
     * @return list of all permutations
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        boolean[] used = new boolean[nums.length];

        generatePermutations(nums, current, used, result);
        return result;
    }

    private void generatePermutations(int[] nums, List<Integer> current,
                                      boolean[] used, List<List<Integer>> result) {
        // Base case: permutation complete
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }

        // Try each unused element
        for (int i = 0; i < nums.length; i++) {
            if (!used[i]) {
                // Make choice
                current.add(nums[i]);
                used[i] = true;

                // Recurse
                generatePermutations(nums, current, used, result);

                // Backtrack
                current.remove(current.size() - 1);
                used[i] = false;
            }
        }
    }

    /**
     * Generate all permutations using swapping approach (space optimized)
     * Time Complexity: O(n! × n)
     * Space Complexity: O(n!) for storing results + O(n) for recursion
     *
     * @param nums array of integers
     * @return list of all permutations
     */
    public List<List<Integer>> permuteSwap(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        permuteSwapHelper(nums, 0, result);
        return result;
    }

    private void permuteSwapHelper(int[] nums, int start, List<List<Integer>> result) {
        // Base case: all positions filled
        if (start == nums.length) {
            List<Integer> permutation = new ArrayList<>();
            for (int num : nums) {
                permutation.add(num);
            }
            result.add(permutation);
            return;
        }

        // Try each element in remaining positions
        for (int i = start; i < nums.length; i++) {
            // Swap current element to start position
            swap(nums, start, i);

            // Recurse with next position
            permuteSwapHelper(nums, start + 1, result);

            // Backtrack: restore original order
            swap(nums, start, i);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // =========================== Permutations with Duplicates ===========================

    /**
     * Generate all unique permutations when array contains duplicates
     * Time Complexity: O(n! × n) in worst case, better with pruning
     * Space Complexity: O(n! × n)
     *
     * @param nums array that may contain duplicates
     * @return list of unique permutations
     */
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        boolean[] used = new boolean[nums.length];

        // Sort to group duplicates together
        Arrays.sort(nums);

        generateUniquePermutations(nums, current, used, result);
        return result;
    }

    private void generateUniquePermutations(int[] nums, List<Integer> current,
                                            boolean[] used, List<List<Integer>> result) {
        // Base case: permutation complete
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue;

            // Skip duplicates: if current element same as previous and previous not used
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
                continue;
            }

            // Make choice
            current.add(nums[i]);
            used[i] = true;

            // Recurse
            generateUniquePermutations(nums, current, used, result);

            // Backtrack
            current.remove(current.size() - 1);
            used[i] = false;
        }
    }

    // =========================== String Permutations ===========================

    /**
     * Generate all permutations of a string
     * Time Complexity: O(n! × n)
     * Space Complexity: O(n! × n)
     *
     * @param s input string
     * @return list of all permutation strings
     */
    public List<String> stringPermutations(String s) {
        List<String> result = new ArrayList<>();
        char[] chars = s.toCharArray();
        Arrays.sort(chars); // Sort for consistent ordering
        boolean[] used = new boolean[chars.length];

        generateStringPermutations(chars, new StringBuilder(), used, result);
        return result;
    }

    private void generateStringPermutations(char[] chars, StringBuilder current,
                                            boolean[] used, List<String> result) {
        // Base case: string complete
        if (current.length() == chars.length) {
            result.add(current.toString());
            return;
        }

        for (int i = 0; i < chars.length; i++) {
            if (used[i]) continue;

            // Skip duplicates for strings with repeated characters
            if (i > 0 && chars[i] == chars[i - 1] && !used[i - 1]) {
                continue;
            }

            // Make choice
            current.append(chars[i]);
            used[i] = true;

            // Recurse
            generateStringPermutations(chars, current, used, result);

            // Backtrack
            current.deleteCharAt(current.length() - 1);
            used[i] = false;
        }
    }

    /**
     * Generate all unique permutations of string with duplicates
     * Time Complexity: O(n! × n)
     * Space Complexity: O(n! × n)
     *
     * @param s input string
     * @return set of unique permutation strings
     */
    public Set<String> uniqueStringPermutations(String s) {
        Set<String> result = new HashSet<>();
        char[] chars = s.toCharArray();

        generateUniqueStringPermutations(chars, 0, result);
        return result;
    }

    private void generateUniqueStringPermutations(char[] chars, int start, Set<String> result) {
        // Base case: permutation complete
        if (start == chars.length) {
            result.add(new String(chars));
            return;
        }

        Set<Character> used = new HashSet<>();

        for (int i = start; i < chars.length; i++) {
            // Skip if character already used at this level
            if (used.contains(chars[i])) {
                continue;
            }

            used.add(chars[i]);

            // Swap and recurse
            swap(chars, start, i);
            generateUniqueStringPermutations(chars, start + 1, result);
            swap(chars, start, i); // Backtrack
        }
    }

    private void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }

    // =========================== Next Permutation ===========================

    /**
     * Find the next lexicographically greater permutation
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     *
     * Algorithm (Narayanan-Khanna):
     * 1. Find largest index i such that nums[i] < nums[i+1]
     * 2. If no such i exists, array is last permutation
     * 3. Find largest index j such that nums[i] < nums[j]
     * 4. Swap nums[i] and nums[j]
     * 5. Reverse suffix starting at nums[i+1]
     *
     * @param nums array representing current permutation
     */
    public void nextPermutation(int[] nums) {
        int n = nums.length;

        // Step 1: Find largest index i such that nums[i] < nums[i+1]
        int i = n - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }

        // If no such i exists, this is the last permutation
        if (i >= 0) {
            // Step 2: Find largest index j such that nums[i] < nums[j]
            int j = n - 1;
            while (nums[j] <= nums[i]) {
                j--;
            }

            // Step 3: Swap nums[i] and nums[j]
            swap(nums, i, j);
        }

        // Step 4: Reverse the suffix starting at nums[i+1]
        reverse(nums, i + 1);
    }

    /**
     * Find the previous lexicographically smaller permutation
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     *
     * @param nums array representing current permutation
     */
    public void previousPermutation(int[] nums) {
        int n = nums.length;

        // Find largest index i such that nums[i] > nums[i+1]
        int i = n - 2;
        while (i >= 0 && nums[i] <= nums[i + 1]) {
            i--;
        }

        if (i >= 0) {
            // Find largest index j such that nums[i] > nums[j]
            int j = n - 1;
            while (nums[j] >= nums[i]) {
                j--;
            }

            // Swap nums[i] and nums[j]
            swap(nums, i, j);
        }

        // Reverse the suffix starting at nums[i+1]
        reverse(nums, i + 1);
    }

    private void reverse(int[] nums, int start) {
        int left = start;
        int right = nums.length - 1;

        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }

    // =========================== Specialized Permutations ===========================

    /**
     * Generate all permutations of first n natural numbers
     * Time Complexity: O(n! × n)
     * Space Complexity: O(n! × n)
     *
     * @param n upper limit (generates permutations of 1, 2, ..., n)
     * @return list of all permutations
     */
    public List<List<Integer>> permuteRange(int n) {
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = i + 1;
        }
        return permute(nums);
    }

    /**
     * Get the k-th permutation (0-indexed) of numbers 1 to n
     * Time Complexity: O(n²)
     * Space Complexity: O(n)
     *
     * Uses factorial number system to directly compute k-th permutation
     *
     * @param n range of numbers (1 to n)
     * @param k index of desired permutation (0-indexed)
     * @return k-th permutation as list
     */
    public List<Integer> getKthPermutation(int n, int k) {
        List<Integer> result = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();

        // Initialize available numbers
        for (int i = 1; i <= n; i++) {
            numbers.add(i);
        }

        // Precompute factorials
        int[] factorial = new int[n];
        factorial[0] = 1;
        for (int i = 1; i < n; i++) {
            factorial[i] = factorial[i - 1] * i;
        }

        // Convert k to 0-indexed
        k--;

        // Generate k-th permutation
        for (int i = n - 1; i >= 0; i--) {
            int index = k / factorial[i];
            result.add(numbers.remove(index));
            k %= factorial[i];
        }

        return result;
    }

    /**
     * Count total number of permutations with repetitions
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     *
     * Formula: n! / (n1! × n2! × ... × nk!)
     * where ni is count of element i
     *
     * @param elements array with possible duplicates
     * @return number of unique permutations
     */
    public long countUniquePermutations(int[] elements) {
        Map<Integer, Integer> frequency = new HashMap<>();

        // Count frequency of each element
        for (int element : elements) {
            frequency.put(element, frequency.getOrDefault(element, 0) + 1);
        }

        // Calculate n!
        long numerator = factorial(elements.length);

        // Calculate product of ni! for all distinct elements
        long denominator = 1;
        for (int count : frequency.values()) {
            denominator *= factorial(count);
        }

        return numerator / denominator;
    }

    private long factorial(int n) {
        if (n <= 1) return 1;
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    // =========================== Permutation Applications ===========================

    /**
     * Check if one string is a permutation of another
     * Time Complexity: O(n)
     * Space Complexity: O(1) assuming fixed character set
     *
     * @param s1 first string
     * @param s2 second string
     * @return true if s1 is permutation of s2
     */
    public boolean isPermutation(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }

        int[] charCount = new int[256]; // ASCII characters

        // Count characters in s1
        for (char c : s1.toCharArray()) {
            charCount[c]++;
        }

        // Subtract characters in s2
        for (char c : s2.toCharArray()) {
            charCount[c]--;
            if (charCount[c] < 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Find all anagrams of a pattern in a text
     * Time Complexity: O(n) where n is length of text
     * Space Complexity: O(1)
     *
     * @param text input text
     * @param pattern pattern to find anagrams of
     * @return list of starting indices where anagrams are found
     */
    public List<Integer> findAnagrams(String text, String pattern) {
        List<Integer> result = new ArrayList<>();

        if (text.length() < pattern.length()) {
            return result;
        }

        int[] patternCount = new int[26]; // For lowercase letters
        int[] windowCount = new int[26];

        // Count characters in pattern
        for (char c : pattern.toCharArray()) {
            patternCount[c - 'a']++;
        }

        int windowSize = pattern.length();

        // Sliding window approach
        for (int i = 0; i < text.length(); i++) {
            // Add character to window
            windowCount[text.charAt(i) - 'a']++;

            // Remove character from window if window size exceeded
            if (i >= windowSize) {
                windowCount[text.charAt(i - windowSize) - 'a']--;
            }

            // Check if current window is an anagram
            if (i >= windowSize - 1 && Arrays.equals(patternCount, windowCount)) {
                result.add(i - windowSize + 1);
            }
        }

        return result;
    }

    /**
     * Generate permutations with specific constraints
     * Example: Generate permutations where no element is in its original position
     *
     * @param nums input array
     * @return list of derangements (permutations with no fixed points)
     */
    public List<List<Integer>> generateDerangements(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        boolean[] used = new boolean[nums.length];

        generateDerangementsHelper(nums, current, used, result);
        return result;
    }

    private void generateDerangementsHelper(int[] nums, List<Integer> current,
                                            boolean[] used, List<List<Integer>> result) {
        // Base case: permutation complete
        if (current.size() == nums.length) {
            // Check if it's a derangement
            boolean isDerangement = true;
            for (int i = 0; i < current.size(); i++) {
                if (current.get(i) == nums[i]) {
                    isDerangement = false;
                    break;
                }
            }

            if (isDerangement) {
                result.add(new ArrayList<>(current));
            }
            return;
        }

        int position = current.size();

        for (int i = 0; i < nums.length; i++) {
            if (!used[i] && nums[i] != nums[position]) { // Constraint: not in original position
                // Make choice
                current.add(nums[i]);
                used[i] = true;

                // Recurse
                generateDerangementsHelper(nums, current, used, result);

                // Backtrack
                current.remove(current.size() - 1);
                used[i] = false;
            }
        }
    }

    // =========================== Utility Methods ===========================

    /**
     * Convert permutation list to array
     *
     * @param permutation list representation
     * @return array representation
     */
    public int[] listToArray(List<Integer> permutation) {
        return permutation.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * Convert permutation array to list
     *
     * @param permutation array representation
     * @return list representation
     */
    public List<Integer> arrayToList(int[] permutation) {
        List<Integer> result = new ArrayList<>();
        for (int num : permutation) {
            result.add(num);
        }
        return result;
    }

    /**
     * Check if array represents a valid permutation of 1 to n
     *
     * @param permutation array to check
     * @return true if valid permutation
     */
    public boolean isValidPermutation(int[] permutation) {
        int n = permutation.length;
        boolean[] seen = new boolean[n + 1]; // 1-indexed

        for (int num : permutation) {
            if (num < 1 || num > n || seen[num]) {
                return false;
            }
            seen[num] = true;
        }

        return true;
    }

    /**
     * Print all permutations in formatted way
     *
     * @param permutations list of permutations to print
     * @param label description label
     */
    public static void printPermutations(List<List<Integer>> permutations, String label) {
        System.out.println(label + " (" + permutations.size() + " permutations):");
        for (int i = 0; i < permutations.size(); i++) {
            System.out.println((i + 1) + ": " + permutations.get(i));
        }
        System.out.println();
    }

    /**
     * Get permutation rank (lexicographical index)
     * Time Complexity: O(n²)
     * Space Complexity: O(n)
     *
     * @param permutation input permutation
     * @return 0-indexed rank in lexicographical order
     */
    public long getPermutationRank(int[] permutation) {
        int n = permutation.length;
        List<Integer> available = new ArrayList<>();

        // Create list of available numbers (assuming 1 to n)
        for (int i = 1; i <= n; i++) {
            available.add(i);
        }

        long rank = 0;

        for (int i = 0; i < n; i++) {
            int element = permutation[i];
            int index = available.indexOf(element);

            // Add contribution of this position
            rank += index * factorial(n - 1 - i);

            // Remove this element from available list
            available.remove(index);
        }

        return rank;
    }
}