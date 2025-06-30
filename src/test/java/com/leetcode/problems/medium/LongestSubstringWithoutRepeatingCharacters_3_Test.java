package com.leetcode.problems.medium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LongestSubstringWithoutRepeatingCharacters_3_Test {

    private LongestSubstringWithoutRepeatingCharacters_3 solution;

    @BeforeEach
    void setUp() {
        solution = new LongestSubstringWithoutRepeatingCharacters_3();
    }

    @ParameterizedTest
    @MethodSource("provideLongestSubstringTestCases")
    @DisplayName("Longest Substring - HashSet Sliding Window")
    void testLengthOfLongestSubstring(String input, int expected) {
        int result = solution.lengthOfLongestSubstring(input);
        assertEquals(expected, result,
                String.format("Failed for input: '%s', expected: %d, got: %d", input, expected, result));
    }

    @ParameterizedTest
    @MethodSource("provideLongestSubstringTestCases")
    @DisplayName("Longest Substring - HashMap Optimized")
    void testLengthOfLongestSubstringOptimized(String input, int expected) {
        int result = solution.lengthOfLongestSubstringOptimized(input);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("provideLongestSubstringTestCases")
    @DisplayName("Longest Substring - Array-based")
    void testLengthOfLongestSubstringArray(String input, int expected) {
        int result = solution.lengthOfLongestSubstringArray(input);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("provideLongestSubstringTestCases")
    @DisplayName("Longest Substring - Brute Force")
    void testLengthOfLongestSubstringBruteForce(String input, int expected) {
        int result = solution.lengthOfLongestSubstringBruteForce(input);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Edge Cases")
    void testEdgeCases() {
        // Empty string
        assertEquals(0, solution.lengthOfLongestSubstring(""));
        assertEquals(0, solution.lengthOfLongestSubstring(null));

        // Single character
        assertEquals(1, solution.lengthOfLongestSubstring("a"));

        // All same characters
        assertEquals(1, solution.lengthOfLongestSubstring("aaaa"));

        // All unique characters
        assertEquals(5, solution.lengthOfLongestSubstring("abcde"));

        // Special characters
        assertEquals(3, solution.lengthOfLongestSubstring("!@#!@#"));

        // Numbers and letters
        assertEquals(3, solution.lengthOfLongestSubstring("a1b2a1"));
    }

    @Test
    @DisplayName("Return Actual Substring")
    void testGetLongestSubstring() {
        assertEquals("abc", solution.getLongestSubstring("abcabcbb"));
        assertEquals("b", solution.getLongestSubstring("bbbbb"));

        String result = solution.getLongestSubstring("pwwkew");
        assertTrue(result.equals("wke") || result.equals("kew") || result.equals("pwe"),
                "Result should be one of the valid longest substrings");
        assertEquals(3, result.length());

        assertEquals("", solution.getLongestSubstring(""));
        assertEquals("a", solution.getLongestSubstring("a"));
    }

    @ParameterizedTest
    @CsvSource({
            "'abcabcbb', 2, 3",  // at most 2 distinct: 'abc'
            "'eceba', 2, 3",     // at most 2 distinct: 'ece'
            "'aa', 1, 2",        // at most 1 distinct: 'aa'
            "'abcdef', 3, 3",    // at most 3 distinct: 'abc'
            "'', 1, 0"           // empty string
    })
    @DisplayName("Longest Substring with K Distinct Characters")
    void testLengthOfLongestSubstringKDistinct(String input, int k, int expected) {
        int result = solution.lengthOfLongestSubstringKDistinct(input, k);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Performance Comparison")
    void testPerformanceComparison() {
        // Generate a longer string for performance testing
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append((char) ('a' + (i % 26)));
        }
        String longString = sb.toString(); // 1000 chars with repeated pattern

        long start, end;

        // Test HashMap optimization
        start = System.nanoTime();
        int result1 = solution.lengthOfLongestSubstringOptimized(longString);
        end = System.nanoTime();
        long optimizedTime = end - start;

        // Test Array-based solution
        start = System.nanoTime();
        int result2 = solution.lengthOfLongestSubstringArray(longString);
        end = System.nanoTime();
        long arrayTime = end - start;

        // Test HashSet solution
        start = System.nanoTime();
        int result3 = solution.lengthOfLongestSubstring(longString);
        end = System.nanoTime();
        long hashSetTime = end - start;

        // All should give same result
        assertEquals(result1, result2);
        assertEquals(result2, result3);
        assertEquals(26, result1); // Should be 26 (all lowercase letters)

        // Array-based should be fastest for ASCII input
        System.out.printf("Performance (ns) - Optimized: %d, Array: %d, HashSet: %d%n",
                optimizedTime, arrayTime, hashSetTime);
    }

    @Test
    @DisplayName("Unicode Characters")
    void testUnicodeCharacters() {
        // Test with Unicode characters
        String unicode = "🌟🎯🚀🌟🎯";
        int result = solution.lengthOfLongestSubstring(unicode);
        assertEquals(3, result); // "🌟🎯🚀"

        // Array-based solution won't work with Unicode (> 128)
        // But HashMap solutions should work fine
        assertEquals(3, solution.lengthOfLongestSubstringOptimized(unicode));
    }

    @Test
    @DisplayName("Memory Usage Validation")
    void testMemoryUsage() {
        // Test that sliding window doesn't grow beyond input size
        String input = "abcdefghijklmnopqrstuvwxyz";

        // For this input, window should never exceed 26 characters
        int result = solution.lengthOfLongestSubstring(input);
        assertEquals(26, result);

        // Test with repeated pattern
        String repeated = "abcabc";
        result = solution.lengthOfLongestSubstring(repeated);
        assertEquals(3, result); // "abc"
    }

    @Test
    @DisplayName("All Solutions Consistency")
    void testAllSolutionsConsistency() {
        String[] testInputs = {
                "abcabcbb", "bbbbb", "pwwkew", "", "a", "au", "dvdf",
                "tmmzuxt", "ohvhjdml", "abcdef"
        };

        for (String input : testInputs) {
            int result1 = solution.lengthOfLongestSubstring(input);
            int result2 = solution.lengthOfLongestSubstringOptimized(input);
            int result3 = solution.lengthOfLongestSubstringArray(input);
            int result4 = solution.lengthOfLongestSubstringBruteForce(input);

            assertEquals(result1, result2, "HashSet vs HashMap optimization mismatch for: " + input);
            assertEquals(result2, result3, "HashMap vs Array mismatch for: " + input);
            assertEquals(result3, result4, "Array vs BruteForce mismatch for: " + input);
        }
    }

    static Stream<Arguments> provideLongestSubstringTestCases() {
        return Stream.of(
                Arguments.of("abcabcbb", 3),    // "abc"
                Arguments.of("bbbbb", 1),       // "b"
                Arguments.of("pwwkew", 3),      // "wke"
                Arguments.of("", 0),            // empty string
                Arguments.of("au", 2),          // "au"
                Arguments.of("dvdf", 3),        // "vdf"
                Arguments.of("anviaj", 5),      // "nviaj"
                Arguments.of("abcdef", 6),      // whole string
                Arguments.of("aab", 2),         // "ab"
                Arguments.of("cdd", 2),         // "cd"
                Arguments.of("abba", 2),        // "ab" or "ba"
                Arguments.of("tmmzuxt", 5),     // "mzuxt"
                Arguments.of("ohvhjdml", 6),    // "vhjdml"
                Arguments.of(" ", 1),           // single space
                Arguments.of("!@#$%", 5)        // special characters
        );
    }
}