package com.leetcode.datastructures.arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class TwoPointersTest {

    private TwoPointers twoPointers;

    @BeforeEach
    void setUp() {
        twoPointers = new TwoPointers();
    }

    @Test
    @DisplayName("Two Sum - Sorted Array")
    void testTwoSumSorted() {
        // Given
        int[] nums = {2, 7, 11, 15};
        int target = 9;

        // When
        int[] result = twoPointers.twoSumSorted(nums, target);

        // Then
        assertArrayEquals(new int[]{0, 1}, result);
    }

    @Test
    @DisplayName("Two Sum - Not Found")
    void testTwoSumNotFound() {
        // Given
        int[] nums = {1, 2, 3, 4};
        int target = 10;

        // When
        int[] result = twoPointers.twoSumSorted(nums, target);

        // Then
        assertArrayEquals(new int[]{-1, -1}, result);
    }

    @Test
    @DisplayName("Remove Duplicates")
    void testRemoveDuplicates() {
        // Given
        int[] nums = {1, 1, 2, 2, 3, 4, 4};
        int[] expected = {1, 2, 3, 4};

        // When
        int newLength = twoPointers.removeDuplicates(nums);

        // Then
        assertEquals(4, newLength);
        for (int i = 0; i < newLength; i++) {
            assertEquals(expected[i], nums[i]);
        }
    }

    @Test
    @DisplayName("Remove Duplicates - Empty Array")
    void testRemoveDuplicatesEmpty() {
        // Given
        int[] nums = {};

        // When
        int newLength = twoPointers.removeDuplicates(nums);

        // Then
        assertEquals(0, newLength);
    }

    @Test
    @DisplayName("Remove Duplicates - Single Element")
    void testRemoveDuplicatesSingle() {
        // Given
        int[] nums = {1};

        // When
        int newLength = twoPointers.removeDuplicates(nums);

        // Then
        assertEquals(1, newLength);
        assertEquals(1, nums[0]);
    }

    @Test
    @DisplayName("Reverse Array")
    void testReverseArray() {
        // Given
        int[] nums = {1, 2, 3, 4, 5};
        int[] expected = {5, 4, 3, 2, 1};

        // When
        twoPointers.reverseArray(nums);

        // Then
        assertArrayEquals(expected, nums);
    }

    @Test
    @DisplayName("Reverse Array - Empty")
    void testReverseArrayEmpty() {
        // Given
        int[] nums = {};

        // When & Then (should not throw exception)
        assertDoesNotThrow(() -> twoPointers.reverseArray(nums));
    }

    @Test
    @DisplayName("Reverse Array - Single Element")
    void testReverseArraySingle() {
        // Given
        int[] nums = {42};

        // When
        twoPointers.reverseArray(nums);

        // Then
        assertEquals(42, nums[0]);
    }

    @ParameterizedTest
    @CsvSource({
            "'A man, a plan, a canal: Panama', true",
            "'race a car', false",
            "'', true",
            "'a', true",
            "'Madam', true",
            "'hello', false"
    })
    @DisplayName("Is Palindrome - Multiple Cases")
    void testIsPalindromeParameterized(String input, boolean expected) {
        boolean result = twoPointers.isPalindrome(input);
        assertEquals(expected, result,
                "Failed for input: '" + input + "', expected: " + expected + ", got: " + result);
    }

    @Test
    @DisplayName("Three Sum")
    void testThreeSum() {
        // Given
        int[] nums = {-1, 0, 1, 2, -1, -4};

        // When
        int[][] result = twoPointers.threeSum(nums);

        // Then
        assertEquals(2, result.length);

        // Check first triplet: [-1, -1, 2]
        assertArrayEquals(new int[]{-1, -1, 2}, result[0]);

        // Check second triplet: [-1, 0, 1]
        assertArrayEquals(new int[]{-1, 0, 1}, result[1]);
    }

    @Test
    @DisplayName("Three Sum - No Solution")
    void testThreeSumNoSolution() {
        // Given
        int[] nums = {1, 2, 3};

        // When
        int[][] result = twoPointers.threeSum(nums);

        // Then
        assertEquals(0, result.length);
    }

    @Test
    @DisplayName("Three Sum - All Zeros")
    void testThreeSumAllZeros() {
        // Given
        int[] nums = {0, 0, 0, 0};

        // When
        int[][] result = twoPointers.threeSum(nums);

        // Then
        assertEquals(1, result.length);
        assertArrayEquals(new int[]{0, 0, 0}, result[0]);
    }
}