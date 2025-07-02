package com.leetcode.datastructures.hashmaps;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FrequencyCounterTest {

    private FrequencyCounter frequencyCounter;

    @BeforeEach
    void setUp() {
        frequencyCounter = new FrequencyCounter();
    }

    @Test
    @DisplayName("Character Frequency")
    void testCharacterFrequency() {
        // Given
        String input = "hello";

        // When
        Map<Character, Integer> result = frequencyCounter.characterFrequency(input);

        // Then
        assertEquals(4, result.size());
        assertEquals(1, result.get('h'));
        assertEquals(1, result.get('e'));
        assertEquals(2, result.get('l'));
        assertEquals(1, result.get('o'));
    }

    @Test
    @DisplayName("Character Frequency - Empty String")
    void testCharacterFrequencyEmpty() {
        // Given
        String input = "";

        // When
        Map<Character, Integer> result = frequencyCounter.characterFrequency(input);

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Array Frequency")
    void testArrayFrequency() {
        // Given
        int[] nums = {1, 2, 2, 3, 3, 3};

        // When
        Map<Integer, Integer> result = frequencyCounter.arrayFrequency(nums);

        // Then
        assertEquals(3, result.size());
        assertEquals(1, result.get(1));
        assertEquals(2, result.get(2));
        assertEquals(3, result.get(3));
    }

    @ParameterizedTest
    @CsvSource({
            "'anagram', 'nagaram', true",
            "'rat', 'car', false",
            "'listen', 'silent', true",
            "'hello', 'bello', false",
            "'', '', true"
    })
    @DisplayName("Is Anagram - Multiple Cases")
    void testIsAnagram(String s1, String s2, boolean expected) {
        boolean result = frequencyCounter.isAnagram(s1, s2);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({
            "'anagram', 'nagaram', true",
            "'rat', 'car', false",
            "'listen', 'silent', true",
            "'hello', 'bello', false",
            "'', '', true"
    })
    @DisplayName("Is Anagram Optimized - Multiple Cases")
    void testIsAnagramOptimized(String s1, String s2, boolean expected) {
        boolean result = frequencyCounter.isAnagramOptimized(s1, s2);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("First Non-Repeating Character")
    void testFirstNonRepeatingChar() {
        // Test cases
        assertEquals('l', frequencyCounter.firstNonRepeatingChar("leetcode"));
        assertEquals('b', frequencyCounter.firstNonRepeatingChar("loveleetcode"));
        assertEquals('\0', frequencyCounter.firstNonRepeatingChar("aabb"));
        assertEquals('a', frequencyCounter.firstNonRepeatingChar("a"));
        assertEquals('\0', frequencyCounter.firstNonRepeatingChar(""));
    }

    @Test
    @DisplayName("Most Frequent Element")
    void testMostFrequentElement() {
        // Given
        int[] nums1 = {1, 2, 2, 3, 3, 3};
        int[] nums2 = {1};
        int[] nums3 = {};

        // When & Then
        assertEquals(3, frequencyCounter.mostFrequentElement(nums1));
        assertEquals(1, frequencyCounter.mostFrequentElement(nums2));
        assertEquals(-1, frequencyCounter.mostFrequentElement(nums3));
    }

    @Test
    @DisplayName("Top K Frequent Elements")
    void testTopKFrequent() {
        // Given
        int[] nums = {1, 1, 1, 2, 2, 3};
        int k = 2;

        // When
        List<Integer> result = frequencyCounter.topKFrequent(nums, k);

        // Then
        assertEquals(2, result.size());
        assertTrue(result.contains(1));
        assertTrue(result.contains(2));
    }

    @Test
    @DisplayName("Top K Frequent Elements - Bucket Sort")
    void testTopKFrequentBucket() {
        // Given
        int[] nums = {1, 1, 1, 2, 2, 3};
        int k = 2;

        // When
        List<Integer> result = frequencyCounter.topKFrequentBucket(nums, k);

        // Then
        assertEquals(2, result.size());
        assertTrue(result.contains(1));
        assertTrue(result.contains(2));
    }

    @Test
    @DisplayName("Top K Frequent - Single Element")
    void testTopKFrequentSingle() {
        // Given
        int[] nums = {1};
        int k = 1;

        // When
        List<Integer> result = frequencyCounter.topKFrequent(nums, k);

        // Then
        assertEquals(1, result.size());
        assertEquals(1, result.get(0));
    }

    @Test
    @DisplayName("Group Anagrams")
    void testGroupAnagrams() {
        // Given
        String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};

        // When
        List<List<String>> result = frequencyCounter.groupAnagrams(strs);

        // Then
        assertEquals(3, result.size());

        // Check that anagrams are grouped together
        boolean foundEatGroup = false, foundTanGroup = false, foundBatGroup = false;

        for (List<String> group : result) {
            if (group.size() == 3 && group.contains("eat") && group.contains("tea") && group.contains("ate")) {
                foundEatGroup = true;
            } else if (group.size() == 2 && group.contains("tan") && group.contains("nat")) {
                foundTanGroup = true;
            } else if (group.size() == 1 && group.contains("bat")) {
                foundBatGroup = true;
            }
        }

        assertTrue(foundEatGroup && foundTanGroup && foundBatGroup);
    }

    @Test
    @DisplayName("Group Anagrams - Frequency Based")
    void testGroupAnagramsFrequency() {
        // Given
        String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};

        // When
        List<List<String>> result = frequencyCounter.groupAnagramsFrequency(strs);

        // Then
        assertEquals(3, result.size());
    }

    @Test
    @DisplayName("Group Anagrams - Empty Array")
    void testGroupAnagramsEmpty() {
        // Given
        String[] strs = {};

        // When
        List<List<String>> result = frequencyCounter.groupAnagrams(strs);

        // Then
        assertTrue(result.isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "'1,2,3,1', true",
            "'1,2,3,4', false",
            "'1,1,1,3,3,4,3,2,4,2', true",
            "'', false"
    })
    @DisplayName("Contains Duplicate - Multiple Cases")
    void testContainsDuplicate(String numsStr, boolean expected) {
        if (numsStr.isEmpty()) {
            int[] nums = {};
            assertEquals(expected, frequencyCounter.containsDuplicate(nums));
            return;
        }

        int[] nums = Arrays.stream(numsStr.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        boolean result = frequencyCounter.containsDuplicate(nums);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Contains Nearby Duplicate")
    void testContainsNearbyDuplicate() {
        // Given
        int[] nums1 = {1, 2, 3, 1};
        int[] nums2 = {1, 0, 1, 1};
        int[] nums3 = {1, 2, 3, 1, 2, 3};

        // When & Then
        assertTrue(frequencyCounter.containsNearbyDuplicate(nums1, 3));
        assertTrue(frequencyCounter.containsNearbyDuplicate(nums2, 1));
        assertFalse(frequencyCounter.containsNearbyDuplicate(nums3, 2));
    }

    @Test
    @DisplayName("Contains Nearby Duplicate - Edge Cases")
    void testContainsNearbyDuplicateEdgeCases() {
        // Given
        int[] nums1 = {1};
        int[] nums2 = {};
        int[] nums3 = {1, 2, 1};

        // When & Then
        assertFalse(frequencyCounter.containsNearbyDuplicate(nums1, 1));
        assertFalse(frequencyCounter.containsNearbyDuplicate(nums2, 0));
        assertTrue(frequencyCounter.containsNearbyDuplicate(nums3, 2));
    }

    @Test
    @DisplayName("Subarrays With K Distinct")
    void testSubarraysWithKDistinct() {
        // Given
        int[] nums1 = {1, 2, 1, 2, 3};
        int[] nums2 = {1, 2, 1, 3, 4};

        // When & Then
        assertEquals(7, frequencyCounter.subarraysWithKDistinct(nums1, 2));
        assertEquals(3, frequencyCounter.subarraysWithKDistinct(nums2, 3));
    }

    @Test
    @DisplayName("Subarrays With K Distinct - Edge Cases")
    void testSubarraysWithKDistinctEdgeCases() {
        // Given
        int[] nums1 = {1, 1, 1};
        int[] nums2 = {1, 2, 3};
        int[] nums3 = {};

        // When & Then
        assertEquals(3, frequencyCounter.subarraysWithKDistinct(nums1, 1));
        assertEquals(0, frequencyCounter.subarraysWithKDistinct(nums2, 4));
        assertEquals(0, frequencyCounter.subarraysWithKDistinct(nums3, 1));
    }

    @ParameterizedTest
    @MethodSource("provideCharacterFrequencyTestCases")
    @DisplayName("Character Frequency - Complex Cases")
    void testCharacterFrequencyComplex(String input, Map<Character, Integer> expected) {
        Map<Character, Integer> result = frequencyCounter.characterFrequency(input);
        assertEquals(expected, result);
    }

    private static Stream<Arguments> provideCharacterFrequencyTestCases() {
        return Stream.of(
                Arguments.of("aab", Map.of('a', 2, 'b', 1)),
                Arguments.of("abcdef", Map.of('a', 1, 'b', 1, 'c', 1, 'd', 1, 'e', 1, 'f', 1)),
                Arguments.of("aaaa", Map.of('a', 4)),
                Arguments.of("abcabc", Map.of('a', 2, 'b', 2, 'c', 2))
        );
    }

    @ParameterizedTest
    @MethodSource("provideTopKTestCases")
    @DisplayName("Top K Frequent - Various Scenarios")
    void testTopKFrequentVariousScenarios(int[] nums, int k, List<Integer> expectedElements) {
        List<Integer> result = frequencyCounter.topKFrequent(nums, k);
        assertEquals(k, result.size());

        // Check that all expected elements are present
        for (int element : expectedElements) {
            assertTrue(result.contains(element),
                    "Result should contain element: " + element);
        }
    }

    private static Stream<Arguments> provideTopKTestCases() {
        return Stream.of(
                Arguments.of(new int[]{4, 1, -1, 2, -1, 2, 3}, 2, Arrays.asList(-1, 2)),
                Arguments.of(new int[]{1}, 1, Arrays.asList(1)),
                Arguments.of(new int[]{1, 2}, 2, Arrays.asList(1, 2)),
                Arguments.of(new int[]{5, 5, 5, 5}, 1, Arrays.asList(5))
        );
    }

    @Test
    @DisplayName("Performance Test - Large Input")
    void testPerformanceLargeInput() {
        // Given - large array with many duplicates
        int[] nums = new int[10000];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = i % 100; // Create frequency pattern
        }

        // When - should complete quickly
        long startTime = System.currentTimeMillis();
        Map<Integer, Integer> result = frequencyCounter.arrayFrequency(nums);
        long endTime = System.currentTimeMillis();

        // Then
        assertEquals(100, result.size()); // 100 unique elements
        assertTrue(endTime - startTime < 100, "Should complete within 100ms");
    }

    @Test
    @DisplayName("Stress Test - Character Frequency")
    void testStressCharacterFrequency() {
        // Given - string with all characters having same frequency
        StringBuilder sb = new StringBuilder();
        for (char c = 'a'; c <= 'z'; c++) {
            sb.append(String.valueOf(c).repeat(5));
        }
        String input = sb.toString();

        // When
        Map<Character, Integer> result = frequencyCounter.characterFrequency(input);

        // Then
        assertEquals(26, result.size());
        for (char c = 'a'; c <= 'z'; c++) {
            assertEquals(5, result.get(c));
        }
    }
}