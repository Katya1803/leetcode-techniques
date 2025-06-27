package com.leetcode.datastructures.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class StringManipulationTest {

    private StringManipulation stringManipulation;

    @BeforeEach
    void setUp() {
        stringManipulation = new StringManipulation();
    }

    @ParameterizedTest
    @CsvSource({
            "'the sky is blue', 'blue is sky the'",
            "'  hello world  ', 'world hello'",
            "'a good   example', 'example good a'",
            "'  Bob    Loves  Alice   ', 'Alice Loves Bob'"
    })
    @DisplayName("Reverse Words - Multiple Cases")
    void testReverseWords(String input, String expected) {
        String result = stringManipulation.reverseWords(input);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Reverse String")
    void testReverseString() {
        // Given
        char[] s1 = {'h', 'e', 'l', 'l', 'o'};
        char[] expected1 = {'o', 'l', 'l', 'e', 'h'};

        char[] s2 = {'H', 'a', 'n', 'n', 'a', 'h'};
        char[] expected2 = {'h', 'a', 'n', 'n', 'a', 'H'};

        // When
        stringManipulation.reverseString(s1);
        stringManipulation.reverseString(s2);

        // Then
        assertArrayEquals(expected1, s1);
        assertArrayEquals(expected2, s2);
    }

    @Test
    @DisplayName("Reverse String - Single Character")
    void testReverseStringSingleChar() {
        // Given
        char[] s = {'a'};
        char[] expected = {'a'};

        // When
        stringManipulation.reverseString(s);

        // Then
        assertArrayEquals(expected, s);
    }

    @ParameterizedTest
    @CsvSource({
            "'anagram', 'nagaram', true",
            "'rat', 'car', false",
            "'listen', 'silent', true",
            "'a', 'ab', false",
            "'', '', true"
    })
    @DisplayName("Is Anagram - Multiple Cases")
    void testIsAnagram(String s, String t, boolean expected) {
        boolean result = stringManipulation.isAnagram(s, t);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Group Anagrams")
    void testGroupAnagrams() {
        // Given
        String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};

        // When
        List<List<String>> result = stringManipulation.groupAnagrams(strs);

        // Then
        assertEquals(3, result.size());

        // Verify each group has correct anagrams
        boolean foundEatGroup = false, foundTanGroup = false, foundBatGroup = false;

        for (List<String> group : result) {
            if (group.contains("eat")) {
                assertTrue(group.contains("tea") && group.contains("ate"));
                assertEquals(3, group.size());
                foundEatGroup = true;
            } else if (group.contains("tan")) {
                assertTrue(group.contains("nat"));
                assertEquals(2, group.size());
                foundTanGroup = true;
            } else if (group.contains("bat")) {
                assertEquals(1, group.size());
                foundBatGroup = true;
            }
        }

        assertTrue(foundEatGroup && foundTanGroup && foundBatGroup);
    }

    @Test
    @DisplayName("Group Anagrams - Empty String")
    void testGroupAnagramsEmptyString() {
        // Given
        String[] strs = {""};

        // When
        List<List<String>> result = stringManipulation.groupAnagrams(strs);

        // Then
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).size());
        assertEquals("", result.get(0).get(0));
    }

    @ParameterizedTest
    @CsvSource({
            "'flower,flow,flight', 'fl'",
            "'dog,racecar,car', ''",
            "'interspecies,interstellar,interstate', 'inters'",
            "'throne,throne', 'throne'"
    })
    @DisplayName("Longest Common Prefix - Multiple Cases")
    void testLongestCommonPrefix(String input, String expected) {
        String[] strs = input.split(",");
        String result = stringManipulation.longestCommonPrefix(strs);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Longest Common Prefix - Empty Array")
    void testLongestCommonPrefixEmpty() {
        // Given
        String[] strs = {};

        // When
        String result = stringManipulation.longestCommonPrefix(strs);

        // Then
        assertEquals("", result);
    }

    @ParameterizedTest
    @CsvSource({
            "'()', true",
            "'()[]{}', true",
            "'(]', false",
            "'([)]', false",
            "'{[]}', true",
            "'', true"
    })
    @DisplayName("Valid Parentheses - Multiple Cases")
    void testIsValid(String input, boolean expected) {
        boolean result = stringManipulation.isValid(input);
        assertEquals(expected, result);
    }
}