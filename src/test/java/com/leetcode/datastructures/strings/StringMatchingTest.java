package com.leetcode.datastructures.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class StringMatchingTest {

    private StringMatching stringMatching;

    @BeforeEach
    void setUp() {
        stringMatching = new StringMatching();
    }

    @ParameterizedTest
    @CsvSource({
            "'hello', 'll', 2",
            "'aaaaa', 'bba', -1",
            "'', '', 0",
            "'a', 'a', 0",
            "'mississippi', 'issip', 4"
    })
    @DisplayName("StrStr - Multiple Cases")
    void testStrStr(String haystack, String needle, int expected) {
        int result = stringMatching.strStr(haystack, needle);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({
            "'hello', 'll', 2",
            "'aaaaa', 'bba', -1",
            "'', '', 0",
            "'a', 'a', 0",
            "'mississippi', 'issip', 4"
    })
    @DisplayName("StrStr KMP - Multiple Cases")
    void testStrStrKMP(String haystack, String needle, int expected) {
        int result = stringMatching.strStrKMP(haystack, needle);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("StrStr - Needle Not Found")
    void testStrStrNotFound() {
        // Given
        String haystack = "leetcode";
        String needle = "leeto";

        // When
        int result = stringMatching.strStr(haystack, needle);

        // Then
        assertEquals(-1, result);
    }

    @Test
    @DisplayName("Find Anagrams")
    void testFindAnagrams() {
        // Given
        String s = "abab";
        String p = "ab";

        // When
        List<Integer> result = stringMatching.findAnagrams(s, p);

        // Then
        assertEquals(Arrays.asList(0, 2), result);
    }

    @Test
    @DisplayName("Find Anagrams - Complex Case")
    void testFindAnagramsComplex() {
        // Given
        String s = "abacabad";
        String p = "abab";

        // When
        List<Integer> result = stringMatching.findAnagrams(s, p);

        // Then
        assertEquals(Arrays.asList(0, 2), result);
    }

    @Test
    @DisplayName("Find Anagrams - No Match")
    void testFindAnagramsNoMatch() {
        // Given
        String s = "abcdef";
        String p = "xyz";

        // When
        List<Integer> result = stringMatching.findAnagrams(s, p);

        // Then
        assertTrue(result.isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "'abcde', 'ace', 3",
            "'abc', 'abc', 3",
            "'abc', 'def', 0",
            "'bl', 'yby', 1"
    })
    @DisplayName("Longest Common Subsequence - Multiple Cases")
    void testLongestCommonSubsequence(String text1, String text2, int expected) {
        int result = stringMatching.longestCommonSubsequence(text1, text2);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Longest Common Subsequence - Empty Strings")
    void testLongestCommonSubsequenceEmpty() {
        // Given
        String text1 = "";
        String text2 = "abc";

        // When
        int result = stringMatching.longestCommonSubsequence(text1, text2);

        // Then
        assertEquals(0, result);
    }

    @Test
    @DisplayName("Longest Common Subsequence - Same Strings")
    void testLongestCommonSubsequenceSame() {
        // Given
        String text1 = "abc";
        String text2 = "abc";

        // When
        int result = stringMatching.longestCommonSubsequence(text1, text2);

        // Then
        assertEquals(3, result);
    }
}