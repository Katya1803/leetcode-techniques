package com.leetcode.datastructures.arrays;

import java.util.Arrays;

/**
 * Two Pointers Technique Implementation
 * Common patterns:
 * 1. Opposite Direction (left, right pointers)
 * 2. Same Direction (slow, fast pointers)
 * 3. Fixed Distance (sliding window variant)
 */
public class TwoPointers {

    /**
     * Pattern 1: Two Sum - Sorted Array
     * Time: O(n), Space: O(1)
     */
    public int[] twoSumSorted(int[] nums, int target) {
        int left = 0, right = nums.length - 1;

        while (left < right) {
            int sum = nums[left] + nums[right];
            if (sum == target) {
                return new int[]{left, right};
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }

        return new int[]{-1, -1}; // Not found
    }

    /**
     * Pattern 2: Remove Duplicates from Sorted Array
     * Time: O(n), Space: O(1)
     */
    public int removeDuplicates(int[] nums) {
        if (nums.length <= 1) return nums.length;

        int writeIndex = 1; // slow pointer

        for (int readIndex = 1; readIndex < nums.length; readIndex++) { // fast pointer
            if (nums[readIndex] != nums[readIndex - 1]) {
                nums[writeIndex] = nums[readIndex];
                writeIndex++;
            }
        }

        return writeIndex;
    }

    /**
     * Pattern 3: Reverse Array
     * Time: O(n), Space: O(1)
     */
    public void reverseArray(int[] nums) {
        int left = 0, right = nums.length - 1;

        while (left < right) {
            // Swap elements
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;

            left++;
            right--;
        }
    }

    /**
     * Pattern 4: Is Palindrome
     * Time: O(n), Space: O(1)
     */
    public boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;

        while (left < right) {
            // Skip non-alphanumeric characters
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }

            // Compare characters (case insensitive)
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }

    /**
     * Pattern 5: Three Sum
     * Time: O(n²), Space: O(1)
     */
    public int[][] threeSum(int[] nums) {
        Arrays.sort(nums);
        java.util.List<int[]> result = new java.util.ArrayList<>();

        for (int i = 0; i < nums.length - 2; i++) {
            // Skip duplicates for first number
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            int left = i + 1, right = nums.length - 1;
            int target = -nums[i];

            while (left < right) {
                int sum = nums[left] + nums[right];

                if (sum == target) {
                    result.add(new int[]{nums[i], nums[left], nums[right]});

                    // Skip duplicates
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;

                    left++;
                    right--;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }

        return result.toArray(new int[result.size()][]);
    }
}