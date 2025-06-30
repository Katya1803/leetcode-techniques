package com.leetcode.datastructures.arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PrefixSumTest {

    private PrefixSum prefixSum;

    @BeforeEach
    void setUp() {
        prefixSum = new PrefixSum();
    }

    @Test
    @DisplayName("NumArray - Range Sum Query")
    void testNumArray() {
        // Given
        int[] nums = {-2, 0, 3, -5, 2, -1};
        PrefixSum.NumArray numArray = new PrefixSum.NumArray(nums);

        // When & Then
        assertEquals(1, numArray.sumRange(0, 2));  // -2 + 0 + 3 = 1
        assertEquals(-1, numArray.sumRange(2, 5)); // 3 + (-5) + 2 + (-1) = -1
        assertEquals(-3, numArray.sumRange(0, 5)); // sum of all elements
    }

    @Test
    @DisplayName("NumArray - Single Element")
    void testNumArraySingleElement() {
        // Given
        int[] nums = {5};
        PrefixSum.NumArray numArray = new PrefixSum.NumArray(nums);

        // When & Then
        assertEquals(5, numArray.sumRange(0, 0));
    }

    @Test
    @DisplayName("Subarray Sum Equals K")
    void testSubarraySum() {
        // Given
        int[] nums1 = {1, 1, 1};
        int k1 = 2;

        int[] nums2 = {1, 2, 3};
        int k2 = 3;

        // When & Then
        assertEquals(2, prefixSum.subarraySum(nums1, k1)); // [1,1] and [1,1]
        assertEquals(2, prefixSum.subarraySum(nums2, k2)); // [3] and [1,2]
    }

    @Test
    @DisplayName("Subarray Sum Equals K - No Valid Subarray")
    void testSubarraySumNoValid() {
        // Given
        int[] nums = {1, 2, 3};
        int k = 7;

        // When
        int result = prefixSum.subarraySum(nums, k);

        // Then
        assertEquals(0, result);
    }

    @Test
    @DisplayName("Find Max Length - Contiguous Array")
    void testFindMaxLength() {
        // Given
        int[] nums1 = {0, 1};
        int[] nums2 = {0, 0, 1, 0, 0, 1, 1, 0};

        // When & Then
        assertEquals(2, prefixSum.findMaxLength(nums1));
        assertEquals(6, prefixSum.findMaxLength(nums2));
    }

    @Test
    @DisplayName("Find Max Length - All Same Elements")
    void testFindMaxLengthAllSame() {
        // Given
        int[] nums1 = {0, 0, 0};
        int[] nums2 = {1, 1, 1};

        // When & Then
        assertEquals(0, prefixSum.findMaxLength(nums1));
        assertEquals(0, prefixSum.findMaxLength(nums2));
    }

    @ParameterizedTest
    @CsvSource({
            "'-2,1,-3,4,-1,2,1,-5,4', 6",  // [4,-1,2,1] = 6
            "'1', 1",
            "'5,4,-1,7,8', 23"
    })
    @DisplayName("Max SubArray - Multiple Cases")
    void testMaxSubArray(String input, int expected) {
        // Parse input
        int[] nums = Arrays.stream(input.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        // When
        int result = prefixSum.maxSubArray(nums);

        // Then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Pivot Index")
    void testPivotIndex() {
        // Given
        int[] nums1 = {1, 7, 3, 6, 5, 6};
        int[] nums2 = {1, 2, 3};
        int[] nums3 = {2, 1, -1};

        // When & Then
        assertEquals(3, prefixSum.pivotIndex(nums1)); // index 3: left sum = 11, right sum = 11
        assertEquals(-1, prefixSum.pivotIndex(nums2)); // no pivot index
        assertEquals(0, prefixSum.pivotIndex(nums3)); // index 0: left sum = 0, right sum = 0
    }

    @Test
    @DisplayName("Pivot Index - Single Element")
    void testPivotIndexSingleElement() {
        // Given
        int[] nums = {0};

        // When
        int result = prefixSum.pivotIndex(nums);

        // Then
        assertEquals(0, result);
    }
}
