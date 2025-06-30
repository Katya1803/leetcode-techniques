package com.leetcode.problems.medium;

import java.util.*;

/**
 * LeetCode 3: Longest Substring Without Repeating Characters
 * Multiple solution approaches with different optimizations
 */
public class LongestSubstringWithoutRepeatingCharacters_3 {

    /**
     * Optimal Solution: Sliding Window with HashSet
     * Time: O(n), Space: O(min(m,n)) where m is charset size
     */
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        Set<Character> window = new HashSet<>();
        int left = 0;
        int maxLength = 0;

        for (int right = 0; right < s.length(); right++) {
            char rightChar = s.charAt(right);

            // Shrink window from left while duplicate exists
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
     * Optimized Sliding Window with HashMap (Jump Left Pointer)
     * Time: O(n), Space: O(min(m,n))
     */
    public int lengthOfLongestSubstringOptimized(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        Map<Character, Integer> charToIndex = new HashMap<>();
        int left = 0;
        int maxLength = 0;

        for (int right = 0; right < s.length(); right++) {
            char rightChar = s.charAt(right);

            // If character seen before and within current window
            if (charToIndex.containsKey(rightChar)) {
                left = Math.max(left, charToIndex.get(rightChar) + 1);
            }

            charToIndex.put(rightChar, right);
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    /**
     * Array-based Solution for ASCII characters
     * Time: O(n), Space: O(1) - fixed 128 size array
     */
    public int lengthOfLongestSubstringArray(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int[] charIndex = new int[128]; // ASCII character set
        Arrays.fill(charIndex, -1);

        int left = 0;
        int maxLength = 0;

        for (int right = 0; right < s.length(); right++) {
            char rightChar = s.charAt(right);

            if (charIndex[rightChar] >= left) {
                left = charIndex[rightChar] + 1;
            }

            charIndex[rightChar] = right;
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    /**
     * Brute Force Solution - Check all substrings
     * Time: O(n³), Space: O(min(m,n))
     */
    public int lengthOfLongestSubstringBruteForce(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int maxLength = 0;

        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                if (hasUniqueCharacters(s, i, j)) {
                    maxLength = Math.max(maxLength, j - i + 1);
                } else {
                    break; // No point checking longer substrings starting at i
                }
            }
        }

        return maxLength;
    }

    private boolean hasUniqueCharacters(String s, int start, int end) {
        Set<Character> chars = new HashSet<>();
        for (int i = start; i <= end; i++) {
            if (chars.contains(s.charAt(i))) {
                return false;
            }
            chars.add(s.charAt(i));
        }
        return true;
    }

    /**
     * Follow-up: Return the actual longest substring
     * Time: O(n), Space: O(min(m,n))
     */
    public String getLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }

        Set<Character> window = new HashSet<>();
        int left = 0;
        int maxLength = 0;
        int resultStart = 0;

        for (int right = 0; right < s.length(); right++) {
            char rightChar = s.charAt(right);

            while (window.contains(rightChar)) {
                window.remove(s.charAt(left));
                left++;
            }

            window.add(rightChar);

            if (right - left + 1 > maxLength) {
                maxLength = right - left + 1;
                resultStart = left;
            }
        }

        return s.substring(resultStart, resultStart + maxLength);
    }

    /**
     * Variation: Longest substring with at most K distinct characters
     * Time: O(n), Space: O(k)
     */
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (s == null || s.length() == 0 || k <= 0) {
            return 0;
        }

        Map<Character, Integer> charCount = new HashMap<>();
        int left = 0;
        int maxLength = 0;

        for (int right = 0; right < s.length(); right++) {
            char rightChar = s.charAt(right);
            charCount.put(rightChar, charCount.getOrDefault(rightChar, 0) + 1);

            while (charCount.size() > k) {
                char leftChar = s.charAt(left);
                charCount.put(leftChar, charCount.get(leftChar) - 1);
                if (charCount.get(leftChar) == 0) {
                    charCount.remove(leftChar);
                }
                left++;
            }

            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }
}