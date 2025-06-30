package com.leetcode.datastructures.arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SlidingWindowTest {

    private SlidingWindow slidingWindow;

    @BeforeEach
    void setUp() {
        slidingWindow = new SlidingWindow();
    }

    @Test
    @DisplayName("Max Sum Subarray")
    void testMaxSumSubarray() {
        // Given
        int[] nums = {2, 1, 5, 1, 3, 2};
        int k = 3;

        // When
        int result = slidingWindow.maxSumSubarray(nums, k);

        // Then
        assertEquals(9, result); // [5, 1, 3]
    }

    @Test
    @DisplayName("Max Sum Subarray - Single Element")
    void testMaxSumSubarraySingleElement() {
        // Given
        int[] nums = {5};
        int k = 1;

        // When
        int result = slidingWindow.maxSumSubarray(nums, k);

        // Then
        assertEquals(5, result);
    }

    @Test
    @DisplayName("Max Sum Subarray - K Greater Than Array Length")
    void testMaxSumSubarrayKGreaterThanLength() {
        // Given
        int[] nums = {1, 2, 3};
        int k = 5;

        // When
        int result = slidingWindow.maxSumSubarray(nums, k);

        // Then
        assertEquals(-1, result);
    }

    @ParameterizedTest
    @CsvSource({
            "'abcabcbb', 3",
            "'bbbbb', 1",
            "'pwwkew', 3",
            "'', 0",
            "'dvdf', 3"
    })
    @DisplayName("Length of Longest Substring - Multiple Cases")
    void testLengthOfLongestSubstring(String input, int expected) {
        int result = slidingWindow.lengthOfLongestSubstring(input);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Min Window Substring")
    void testMinWindow() {
        // Given
        String s = "ADOBECODEBANC";
        String t = "ABC";

        // When
        String result = slidingWindow.minWindow(s, t);

        // Then
        assertEquals("BANC", result);
    }

    @Test
    @DisplayName("Min Window Substring - No Valid Window")
    void testMinWindowNoValidWindow() {
        // Given
        String s = "a";
        String t = "aa";

        // When
        String result = slidingWindow.minWindow(s, t);

        // Then
        assertEquals("", result);
    }

    @Test
    @DisplayName("Min Window Substring - Same String")
    void testMinWindowSameString() {
        // Given
        String s = "a";
        String t = "a";

        // When
        String result = slidingWindow.minWindow(s, t);

        // Then
        assertEquals("a", result);
    }

    @Test
    @DisplayName("Find Anagrams")
    void testFindAnagrams() {
        // Given
        String s = "abab";
        String p = "ab";

        // When
        List<Integer> result = slidingWindow.findAnagrams(s, p);

        // Then
        assertEquals(Arrays.asList(0, 2), result);
    }

    @Test
    @DisplayName("Find Anagrams - No Anagrams")
    void testFindAnagramsNoAnagrams() {
        // Given
        String s = "abcdef";
        String p = "xyz";

        // When
        List<Integer> result = slidingWindow.findAnagrams(s, p);

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Find Anagrams - Pattern Longer Than String")
    void testFindAnagramsPatternLonger() {
        // Given
        String s = "ab";
        String p = "abab";

        // When
        List<Integer> result = slidingWindow.findAnagrams(s, p);

        // Then
        assertTrue(result.isEmpty());
    }
}
