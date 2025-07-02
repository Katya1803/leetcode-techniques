package com.leetcode.datastructures.hashmaps;

import java.util.*;

/**
 * Complement Lookup patterns using HashMap
 * Common use cases:
 * 1. Two Sum and variants
 * 2. Finding pairs with specific properties
 * 3. Complement-based searching
 * 4. Target sum problems
 */
public class ComplementLookup {

    /**
     * Two Sum - Find indices of two numbers that add up to target
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

        return new int[]{-1, -1}; // Not found
    }

    /**
     * Two Sum - Return all unique pairs that sum to target
     * Time: O(n), Space: O(n)
     */
    public List<List<Integer>> twoSumAllPairs(int[] nums, int target) {
        Set<List<Integer>> result = new HashSet<>();
        Set<Integer> seen = new HashSet<>();

        for (int num : nums) {
            int complement = target - num;

            if (seen.contains(complement)) {
                List<Integer> pair = Arrays.asList(
                        Math.min(num, complement),
                        Math.max(num, complement)
                );
                result.add(pair);
            }

            seen.add(num);
        }

        return new ArrayList<>(result);
    }

    /**
     * Three Sum - Find all unique triplets that sum to zero
     * Time: O(n²), Space: O(n)
     */
    public List<List<Integer>> threeSum(int[] nums) {
        Set<List<Integer>> result = new HashSet<>();
        Set<Integer> duplicates = new HashSet<>();

        for (int i = 0; i < nums.length; i++) {
            if (duplicates.add(nums[i])) {
                Set<Integer> seen = new HashSet<>();

                for (int j = i + 1; j < nums.length; j++) {
                    int complement = -(nums[i] + nums[j]);

                    if (seen.contains(complement)) {
                        List<Integer> triplet = Arrays.asList(nums[i], nums[j], complement);
                        triplet.sort(Integer::compareTo);
                        result.add(triplet);
                    }

                    seen.add(nums[j]);
                }
            }
        }

        return new ArrayList<>(result);
    }

    /**
     * Four Sum - Find all unique quadruplets that sum to target
     * Time: O(n³), Space: O(n)
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Set<List<Integer>> result = new HashSet<>();

        for (int i = 0; i < nums.length - 3; i++) {
            for (int j = i + 1; j < nums.length - 2; j++) {
                Set<Integer> seen = new HashSet<>();

                for (int k = j + 1; k < nums.length; k++) {
                    long complement = (long)target - nums[i] - nums[j] - nums[k];

                    if (seen.contains((int)complement) && complement >= Integer.MIN_VALUE && complement <= Integer.MAX_VALUE) {
                        List<Integer> quadruplet = Arrays.asList(nums[i], nums[j], nums[k], (int)complement);
                        quadruplet.sort(Integer::compareTo);
                        result.add(quadruplet);
                    }

                    seen.add(nums[k]);
                }
            }
        }

        return new ArrayList<>(result);
    }

    /**
     * Two Sum BST - Check if two nodes sum to target in BST
     * Time: O(n), Space: O(n)
     */
    public boolean findTarget(TreeNode root, int k) {
        Set<Integer> seen = new HashSet<>();
        return findTargetHelper(root, k, seen);
    }

    private boolean findTargetHelper(TreeNode node, int k, Set<Integer> seen) {
        if (node == null) return false;

        int complement = k - node.val;
        if (seen.contains(complement)) return true;

        seen.add(node.val);

        return findTargetHelper(node.left, k, seen) ||
                findTargetHelper(node.right, k, seen);
    }

    /**
     * Two Sum IV - Input is a BST (using inorder traversal)
     * Time: O(n), Space: O(h) where h is height
     */
    public boolean findTargetInorder(TreeNode root, int k) {
        List<Integer> inorder = new ArrayList<>();
        inorderTraversal(root, inorder);

        int left = 0, right = inorder.size() - 1;
        while (left < right) {
            int sum = inorder.get(left) + inorder.get(right);
            if (sum == k) return true;
            else if (sum < k) left++;
            else right--;
        }

        return false;
    }

    private void inorderTraversal(TreeNode node, List<Integer> result) {
        if (node == null) return;
        inorderTraversal(node.left, result);
        result.add(node.val);
        inorderTraversal(node.right, result);
    }

    /**
     * Count pairs with given sum
     * Time: O(n), Space: O(n)
     */
    public int countPairsWithSum(int[] nums, int target) {
        Map<Integer, Integer> freq = new HashMap<>();
        int count = 0;

        for (int num : nums) {
            int complement = target - num;

            if (freq.containsKey(complement)) {
                count += freq.get(complement);
            }

            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }

        return count;
    }

    /**
     * Find pairs with difference k
     * Time: O(n), Space: O(n)
     */
    public List<List<Integer>> findPairsWithDifference(int[] nums, int k) {
        Set<List<Integer>> result = new HashSet<>();
        Set<Integer> numSet = new HashSet<>();

        for (int num : nums) {
            numSet.add(num);
        }

        for (int num : nums) {
            if (numSet.contains(num + k)) {
                result.add(Arrays.asList(num, num + k));
            }
        }

        return new ArrayList<>(result);
    }

    /**
     * Subarray sum equals K
     * Time: O(n), Space: O(n)
     */
    public int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> prefixSumCount = new HashMap<>();
        prefixSumCount.put(0, 1); // Empty subarray has sum 0

        int prefixSum = 0;
        int count = 0;

        for (int num : nums) {
            prefixSum += num;
            int complement = prefixSum - k;

            count += prefixSumCount.getOrDefault(complement, 0);
            prefixSumCount.put(prefixSum, prefixSumCount.getOrDefault(prefixSum, 0) + 1);
        }

        return count;
    }

    /**
     * Longest subarray with sum K
     * Time: O(n), Space: O(n)
     */
    public int longestSubarrayWithSum(int[] nums, int k) {
        Map<Integer, Integer> prefixSumIndex = new HashMap<>();
        prefixSumIndex.put(0, -1); // Empty prefix at index -1

        int prefixSum = 0;
        int maxLength = 0;

        for (int i = 0; i < nums.length; i++) {
            prefixSum += nums[i];
            int complement = prefixSum - k;

            if (prefixSumIndex.containsKey(complement)) {
                maxLength = Math.max(maxLength, i - prefixSumIndex.get(complement));
            }

            // Only store the first occurrence for maximum length
            if (!prefixSumIndex.containsKey(prefixSum)) {
                prefixSumIndex.put(prefixSum, i);
            }
        }

        return maxLength;
    }

    /**
     * Maximum size subarray sum equals k
     * Time: O(n), Space: O(n)
     */
    public int maxSubArrayLen(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);

        int sum = 0, maxLen = 0;

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];

            if (map.containsKey(sum - k)) {
                maxLen = Math.max(maxLen, i - map.get(sum - k));
            }

            if (!map.containsKey(sum)) {
                map.put(sum, i);
            }
        }

        return maxLen;
    }

    /**
     * Check if array can be divided into pairs with sum divisible by k
     * Time: O(n), Space: O(k)
     */
    public boolean canArrange(int[] arr, int k) {
        Map<Integer, Integer> remainderCount = new HashMap<>();

        // Count remainders
        for (int num : arr) {
            int remainder = ((num % k) + k) % k; // Handle negative numbers
            remainderCount.put(remainder, remainderCount.getOrDefault(remainder, 0) + 1);
        }

        // Check if pairs can be formed
        for (Map.Entry<Integer, Integer> entry : remainderCount.entrySet()) {
            int remainder = entry.getKey();
            int count = entry.getValue();

            if (remainder == 0) {
                // Numbers divisible by k must be even count
                if (count % 2 != 0) return false;
            } else {
                // remainder + complement = k
                int complement = k - remainder;
                if (!remainderCount.containsKey(complement) ||
                        !remainderCount.get(complement).equals(count)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Helper TreeNode class for BST problems
     */
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}