package com.leetcode.datastructures.arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ArrayTechniquesTest {

    private ArrayTechniques arrayTechniques;

    @BeforeEach
    void setUp() {
        arrayTechniques = new ArrayTechniques();
    }

    @Test
    @DisplayName("Rotate Array")
    void testRotateArray() {
        // Given
        int[] nums = {1, 2, 3, 4, 5, 6, 7};
        int[] expected = {5, 6, 7, 1, 2, 3, 4};

        // When
        arrayTechniques.rotate(nums, 3);

        // Then
        assertArrayEquals(expected, nums);
    }

    @Test
    @DisplayName("Rotate Array - Zero Steps")
    void testRotateArrayZeroSteps() {
        // Given
        int[] nums = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};

        // When
        arrayTechniques.rotate(nums, 0);

        // Then
        assertArrayEquals(expected, nums);
    }

    @Test
    @DisplayName("Rotate Array - Steps Greater Than Length")
    void testRotateArrayStepsGreaterThanLength() {
        // Given
        int[] nums = {-1, -100, 3, 99};
        int[] expected = {3, 99, -1, -100};

        // When
        arrayTechniques.rotate(nums, 6); // 6 % 4 = 2

        // Then
        assertArrayEquals(expected, nums);
    }

    @Test
    @DisplayName("Merge Sorted Arrays")
    void testMergeSortedArrays() {
        // Given
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int[] nums2 = {2, 5, 6};
        int[] expected = {1, 2, 2, 3, 5, 6};

        // When
        arrayTechniques.merge(nums1, 3, nums2, 3);

        // Then
        assertArrayEquals(expected, nums1);
    }

    @Test
    @DisplayName("Merge Sorted Arrays - Empty Second Array")
    void testMergeSortedArraysEmptySecond() {
        // Given
        int[] nums1 = {1, 2, 3};
        int[] nums2 = {};
        int[] expected = {1, 2, 3};

        // When
        arrayTechniques.merge(nums1, 3, nums2, 0);

        // Then
        assertArrayEquals(expected, nums1);
    }

    @Test
    @DisplayName("Move Zeroes")
    void testMoveZeroes() {
        // Given
        int[] nums = {0, 1, 0, 3, 12};
        int[] expected = {1, 3, 12, 0, 0};

        // When
        arrayTechniques.moveZeroes(nums);

        // Then
        assertArrayEquals(expected, nums);
    }

    @Test
    @DisplayName("Move Zeroes - All Zeros")
    void testMoveZeroesAllZeros() {
        // Given
        int[] nums = {0, 0, 0};
        int[] expected = {0, 0, 0};

        // When
        arrayTechniques.moveZeroes(nums);

        // Then
        assertArrayEquals(expected, nums);
    }

    @Test
    @DisplayName("Move Zeroes - No Zeros")
    void testMoveZeroesNoZeros() {
        // Given
        int[] nums = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};

        // When
        arrayTechniques.moveZeroes(nums);

        // Then
        assertArrayEquals(expected, nums);
    }

    @ParameterizedTest
    @CsvSource({
            "'3,0,1', 2",
            "'0,1', 2",
            "'9,6,4,2,3,5,7,0,1', 8",
            "'1', 0"
    })
    @DisplayName("Missing Number - Multiple Cases")
    void testMissingNumberParameterized(String input, int expected) {
        // Parse input
        int[] nums = Arrays.stream(input.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        // When
        int result = arrayTechniques.missingNumber(nums);

        // Then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Single Number")
    void testSingleNumber() {
        // Given
        int[] nums1 = {2, 2, 1};
        int[] nums2 = {4, 1, 2, 1, 2};

        // When & Then
        assertEquals(1, arrayTechniques.singleNumber(nums1));
        assertEquals(4, arrayTechniques.singleNumber(nums2));
    }

    @Test
    @DisplayName("Majority Element")
    void testMajorityElement() {
        // Given
        int[] nums1 = {3, 2, 3};
        int[] nums2 = {2, 2, 1, 1, 1, 2, 2};

        // When & Then
        assertEquals(3, arrayTechniques.majorityElement(nums1));
        assertEquals(2, arrayTechniques.majorityElement(nums2));
    }

    @Test
    @DisplayName("Product Except Self")
    void testProductExceptSelf() {
        // Given
        int[] nums = {1, 2, 3, 4};
        int[] expected = {24, 12, 8, 6};

        // When
        int[] result = arrayTechniques.productExceptSelf(nums);

        // Then
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Product Except Self - With Zero")
    void testProductExceptSelfWithZero() {
        // Given
        int[] nums = {-1, 1, 0, -3, 3};
        int[] expected = {0, 0, 9, 0, 0};

        // When
        int[] result = arrayTechniques.productExceptSelf(nums);

        // Then
        assertArrayEquals(expected, result);
    }
}