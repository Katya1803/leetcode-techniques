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

class ComplementLookupTest {

    private ComplementLookup complementLookup;

    @BeforeEach
    void setUp() {
        complementLookup = new ComplementLookup();
    }

    @Test
    @DisplayName("Two Sum - Basic Case")
    void testTwoSum() {
        // Given
        int[] nums = {2, 7, 11, 15};
        int target = 9;

        // When
        int[] result = complementLookup.twoSum(nums, target);

        // Then
        assertArrayEquals(new int[]{0, 1}, result);
    }

    @Test
    @DisplayName("Two Sum - No Solution")
    void testTwoSumNoSolution() {
        // Given
        int[] nums = {1, 2, 3};
        int target = 7;

        // When
        int[] result = complementLookup.twoSum(nums, target);

        // Then
        assertArrayEquals(new int[]{-1, -1}, result);
    }

    @Test
    @DisplayName("Two Sum - Same Numbers")
    void testTwoSumSameNumbers() {
        // Given
        int[] nums = {3, 3};
        int target = 6;

        // When
        int[] result = complementLookup.twoSum(nums, target);

        // Then
        assertArrayEquals(new int[]{0, 1}, result);
    }

    @ParameterizedTest
    @MethodSource("provideTwoSumTestCases")
    @DisplayName("Two Sum - Multiple Cases")
    void testTwoSumMultipleCases(int[] nums, int target, int[] expected) {
        int[] result = complementLookup.twoSum(nums, target);
        assertArrayEquals(expected, result);
    }

    private static Stream<Arguments> provideTwoSumTestCases() {
        return Stream.of(
                Arguments.of(new int[]{2, 7, 11, 15}, 9, new int[]{0, 1}),
                Arguments.of(new int[]{3, 2, 4}, 6, new int[]{1, 2}),
                Arguments.of(new int[]{3, 3}, 6, new int[]{0, 1}),
                Arguments.of(new int[]{1, 2, 3, 4, 5}, 8, new int[]{2, 4}),
                Arguments.of(new int[]{-1, -2, -3, -4, -5}, -8, new int[]{2, 4})
        );
    }

    @Test
    @DisplayName("Two Sum All Pairs")
    void testTwoSumAllPairs() {
        // Given
        int[] nums = {1, 2, 3, 4, 5, 6};
        int target = 7;

        // When
        List<List<Integer>> result = complementLookup.twoSumAllPairs(nums, target);

        // Then
        assertEquals(3, result.size());
        assertTrue(result.contains(Arrays.asList(1, 6)));
        assertTrue(result.contains(Arrays.asList(2, 5)));
        assertTrue(result.contains(Arrays.asList(3, 4)));
    }

    @Test
    @DisplayName("Two Sum All Pairs - With Duplicates")
    void testTwoSumAllPairsWithDuplicates() {
        // Given
        int[] nums = {1, 2, 2, 3, 4, 4};
        int target = 6;

        // When
        List<List<Integer>> result = complementLookup.twoSumAllPairs(nums, target);

        // Then
        assertEquals(2, result.size());
        assertTrue(result.contains(Arrays.asList(2, 4)));
    }

    @Test
    @DisplayName("Three Sum")
    void testThreeSum() {
        // Given
        int[] nums = {-1, 0, 1, 2, -1, -4};

        // When
        List<List<Integer>> result = complementLookup.threeSum(nums);

        // Then
        assertEquals(2, result.size());
        assertTrue(result.contains(Arrays.asList(-1, -1, 2)));
        assertTrue(result.contains(Arrays.asList(-1, 0, 1)));
    }

    @Test
    @DisplayName("Three Sum - No Triplets")
    void testThreeSumNoTriplets() {
        // Given
        int[] nums = {1, 2, 3};

        // When
        List<List<Integer>> result = complementLookup.threeSum(nums);

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Three Sum - All Zeros")
    void testThreeSumAllZeros() {
        // Given
        int[] nums = {0, 0, 0};

        // When
        List<List<Integer>> result = complementLookup.threeSum(nums);

        // Then
        assertEquals(1, result.size());
        assertTrue(result.contains(Arrays.asList(0, 0, 0)));
    }

    @Test
    @DisplayName("Four Sum")
    void testFourSum() {
        // Given
        int[] nums = {1, 0, -1, 0, -2, 2};
        int target = 0;

        // When
        List<List<Integer>> result = complementLookup.fourSum(nums, target);

        // Then
        assertFalse(result.isEmpty());
        // Check one of the expected quadruplets
        assertTrue(result.contains(Arrays.asList(-2, -1, 1, 2)) ||
                result.contains(Arrays.asList(-2, 0, 0, 2)) ||
                result.contains(Arrays.asList(-1, 0, 0, 1)));
    }

    @Test
    @DisplayName("Four Sum - No Quadruplets")
    void testFourSumNoQuadruplets() {
        // Given
        int[] nums = {1, 2, 3};
        int target = 100;

        // When
        List<List<Integer>> result = complementLookup.fourSum(nums, target);

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Find Target in BST")
    void testFindTargetInBST() {
        // Given - Creating a simple BST: 5 -> 3,6 -> 2,4,null,7
        ComplementLookup.TreeNode root = new ComplementLookup.TreeNode(5);
        root.left = new ComplementLookup.TreeNode(3);
        root.right = new ComplementLookup.TreeNode(6);
        root.left.left = new ComplementLookup.TreeNode(2);
        root.left.right = new ComplementLookup.TreeNode(4);
        root.right.right = new ComplementLookup.TreeNode(7);

        // When & Then
        assertTrue(complementLookup.findTarget(root, 9));  // 2 + 7 = 9
        assertTrue(complementLookup.findTarget(root, 28)); // This should be false, fixing
        assertFalse(complementLookup.findTarget(root, 28));
        assertTrue(complementLookup.findTarget(root, 5));  // 2 + 3 = 5
    }

    @Test
    @DisplayName("Find Target in BST - Inorder")
    void testFindTargetInorderInBST() {
        // Given - Creating a simple BST
        ComplementLookup.TreeNode root = new ComplementLookup.TreeNode(5);
        root.left = new ComplementLookup.TreeNode(3);
        root.right = new ComplementLookup.TreeNode(6);
        root.left.left = new ComplementLookup.TreeNode(2);
        root.left.right = new ComplementLookup.TreeNode(4);
        root.right.right = new ComplementLookup.TreeNode(7);

        // When & Then
        assertTrue(complementLookup.findTargetInorder(root, 9));  // 2 + 7 = 9
        assertFalse(complementLookup.findTargetInorder(root, 28));
        assertTrue(complementLookup.findTargetInorder(root, 5));  // 2 + 3 = 5
    }

    @Test
    @DisplayName("Find Target - Single Node")
    void testFindTargetSingleNode() {
        // Given
        ComplementLookup.TreeNode root = new ComplementLookup.TreeNode(1);

        // When & Then
        assertFalse(complementLookup.findTarget(root, 2));
        assertFalse(complementLookup.findTargetInorder(root, 2));
    }

    @Test
    @DisplayName("Count Pairs With Sum")
    void testCountPairsWithSum() {
        // Given
        int[] nums = {1, 5, 7, -1, 5};
        int target = 6;

        // When
        int result = complementLookup.countPairsWithSum(nums, target);

        // Then
        assertEquals(3, result); // (1,5), (7,-1), (1,5) - second occurrence
    }

    @Test
    @DisplayName("Count Pairs With Sum - No Pairs")
    void testCountPairsWithSumNoPairs() {
        // Given
        int[] nums = {1, 2, 3};
        int target = 10;

        // When
        int result = complementLookup.countPairsWithSum(nums, target);

        // Then
        assertEquals(0, result);
    }

    @Test
    @DisplayName("Find Pairs With Difference")
    void testFindPairsWithDifference() {
        // Given
        int[] nums = {3, 1, 4, 1, 5};
        int k = 2;

        // When
        List<List<Integer>> result = complementLookup.findPairsWithDifference(nums, k);

        // Then
        assertEquals(2, result.size());
        assertTrue(result.contains(Arrays.asList(1, 3)));
        assertTrue(result.contains(Arrays.asList(3, 5)));
    }

    @Test
    @DisplayName("Find Pairs With Difference - Zero Difference")
    void testFindPairsWithDifferenceZero() {
        // Given
        int[] nums = {1, 2, 3, 4, 5};
        int k = 0;

        // When
        List<List<Integer>> result = complementLookup.findPairsWithDifference(nums, k);

        // Then
        assertTrue(result.isEmpty()); // No duplicates, so no pairs with difference 0
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
        assertEquals(2, complementLookup.subarraySum(nums1, k1)); // [1,1] at indices (0,1) and (1,2)
        assertEquals(2, complementLookup.subarraySum(nums2, k2)); // [3] and [1,2]
    }

    @Test
    @DisplayName("Subarray Sum Equals K - Negative Numbers")
    void testSubarraySumNegative() {
        // Given
        int[] nums = {1, -1, 0};
        int k = 0;

        // When
        int result = complementLookup.subarraySum(nums, k);

        // Then
        assertEquals(3, result); // [1,-1], [0], [1,-1,0]
    }

    @Test
    @DisplayName("Longest Subarray With Sum")
    void testLongestSubarrayWithSum() {
        // Given
        int[] nums = {1, -1, 5, -2, 3};
        int k = 3;

        // When
        int result = complementLookup.longestSubarrayWithSum(nums, k);

        // Then
        assertEquals(4, result); // Subarray [1, -1, 5, -2] has sum 3
    }

    @Test
    @DisplayName("Longest Subarray With Sum - No Valid Subarray")
    void testLongestSubarrayWithSumNoValid() {
        // Given
        int[] nums = {1, 2, 3};
        int k = 10;

        // When
        int result = complementLookup.longestSubarrayWithSum(nums, k);

        // Then
        assertEquals(0, result);
    }

    @Test
    @DisplayName("Max SubArray Length")
    void testMaxSubArrayLen() {
        // Given
        int[] nums = {1, -1, 5, -2, 3};
        int k = 3;

        // When
        int result = complementLookup.maxSubArrayLen(nums, k);

        // Then
        assertEquals(4, result);
    }

    @Test
    @DisplayName("Can Arrange Pairs")
    void testCanArrange() {
        // Given
        int[] arr1 = {1, 2, 3, 4, 5, 10, 6, 7, 8, 9};
        int k1 = 5;

        int[] arr2 = {1, 2, 3, 4, 5, 6};
        int k2 = 7;

        int[] arr3 = {1, 2, 3, 4, 5, 6};
        int k3 = 10;

        // When & Then
        assertTrue(complementLookup.canArrange(arr1, k1));
        assertTrue(complementLookup.canArrange(arr2, k2));
        assertFalse(complementLookup.canArrange(arr3, k3));
    }

    @Test
    @DisplayName("Can Arrange Pairs - Negative Numbers")
    void testCanArrangeNegative() {
        // Given
        int[] arr = {-10, -7, -4, -1, 2, 5, 8, 11};
        int k = 3;

        // When
        boolean result = complementLookup.canArrange(arr, k);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Can Arrange Pairs - All Divisible by K")
    void testCanArrangeAllDivisible() {
        // Given
        int[] arr = {5, 5, 1, 2, 3, 4};
        int k = 5;

        // When
        boolean result = complementLookup.canArrange(arr, k);

        // Then
        assertFalse(result); // 5,5 are divisible by 5, but we have 2 of them (even count needed)
    }

    @ParameterizedTest
    @MethodSource("provideComplexTestCases")
    @DisplayName("Complex Two Sum Cases")
    void testComplexTwoSumCases(int[] nums, int target, boolean shouldHaveSolution) {
        int[] result = complementLookup.twoSum(nums, target);
        boolean hasSolution = !Arrays.equals(result, new int[]{-1, -1});
        assertEquals(shouldHaveSolution, hasSolution);

        if (hasSolution) {
            assertEquals(target, nums[result[0]] + nums[result[1]]);
            assertNotEquals(result[0], result[1]);
        }
    }

    private static Stream<Arguments> provideComplexTestCases() {
        return Stream.of(
                Arguments.of(new int[]{0, 4, 3, 0}, 0, true),
                Arguments.of(new int[]{-3, 4, 3, 90}, 0, true),
                Arguments.of(new int[]{1, 2, 3}, 7, false),
                Arguments.of(new int[]{2, 5, 5, 11}, 10, true)
        );
    }

    @Test
    @DisplayName("Performance Test - Large Array")
    void testPerformanceLargeArray() {
        // Given - large array
        int[] nums = new int[10000];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = i;
        }
        int target = 19999; // Last two elements

        // When
        long startTime = System.currentTimeMillis();
        int[] result = complementLookup.twoSum(nums, target);
        long endTime = System.currentTimeMillis();

        // Then
        assertArrayEquals(new int[]{9999, 10000-1}, result);
        assertTrue(endTime - startTime < 50, "Should complete within 50ms");
    }
}