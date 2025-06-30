package com.leetcode.datastructures.arrays;

import java.util.*;

/**
 * Sliding Window technique implementations
 */
public class SlidingWindow {

    /**
     * Maximum sum subarray of size k (fixed window)
     * Time: O(n), Space: O(1)
     */
    public int maxSumSubarray(int[] nums, int k) {
        if (nums.length < k) return -1;

        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += nums[i];
        }

        int maxSum = windowSum;

        for (int i = k; i < nums.length; i++) {
            windowSum = windowSum - nums[i - k] + nums[i];
            maxSum = Math.max(maxSum, windowSum);
        }

        return maxSum;
    }

    /**
     * Longest substring without repeating characters
     * Time: O(n), Space: O(min(m,n))
     */
    public int lengthOfLongestSubstring(String s) {
        Set<Character> window = new HashSet<>();
        int left = 0, maxLength = 0;

        for (int right = 0; right < s.length(); right++) {
            char rightChar = s.charAt(right);

            while (window.contains(rightChar)) {
                window.remove(s.charAt(left));
                left++;
            }

            window.add(rightChar);
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    /**
     * Minimum window substring
     * Time: O(|s| + |t|), Space: O(|t|)
     */
    public String minWindow(String s, String t) {
        if (s.length() < t.length()) return "";

        Map<Character, Integer> targetCount = new HashMap<>();
        for (char c : t.toCharArray()) {
            targetCount.put(c, targetCount.getOrDefault(c, 0) + 1);
        }

        Map<Character, Integer> windowCount = new HashMap<>();
        int left = 0, minLen = Integer.MAX_VALUE;
        int minStart = 0, formed = 0;
        int required = targetCount.size();

        for (int right = 0; right < s.length(); right++) {
            char rightChar = s.charAt(right);
            windowCount.put(rightChar, windowCount.getOrDefault(rightChar, 0) + 1);

            if (targetCount.containsKey(rightChar) &&
                    windowCount.get(rightChar).intValue() == targetCount.get(rightChar).intValue()) {
                formed++;
            }

            while (left <= right && formed == required) {
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    minStart = left;
                }

                char leftChar = s.charAt(left);
                windowCount.put(leftChar, windowCount.get(leftChar) - 1);
                if (targetCount.containsKey(leftChar) &&
                        windowCount.get(leftChar) < targetCount.get(leftChar)) {
                    formed--;
                }
                left++;
            }
        }

        return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLen);
    }

    /**
     * Find all anagrams in string
     * Time: O(|s| + |p|), Space: O(1)
     */
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        if (s.length() < p.length()) return result;

        int[] pCount = new int[26];
        int[] windowCount = new int[26];

        for (char c : p.toCharArray()) {
            pCount[c - 'a']++;
        }

        int windowSize = p.length();

        for (int i = 0; i < windowSize; i++) {
            windowCount[s.charAt(i) - 'a']++;
        }

        if (Arrays.equals(pCount, windowCount)) {
            result.add(0);
        }

        for (int i = windowSize; i < s.length(); i++) {
            windowCount[s.charAt(i) - 'a']++;
            windowCount[s.charAt(i - windowSize) - 'a']--;

            if (Arrays.equals(pCount, windowCount)) {
                result.add(i - windowSize + 1);
            }
        }

        return result;
    }
}
