package com.leetcode.datastructures.stacks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MonotonicStackTest {

    private MonotonicStack monotonicStack;

    @BeforeEach
    void setUp() {
        monotonicStack = new MonotonicStack();
    }

    @Test
    @DisplayName("Next Greater Element I")
    void testNextGreaterElement() {
        // Given
        int[] nums1 = {4, 1, 2};
        int[] nums2 = {1, 3, 4, 2};
        int[] expected = {-1, 3, -1};

        // When
        int[] result = monotonicStack.nextGreaterElement(nums1, nums2);

        // Then
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Next Greater Element I - All Elements Found")
    void testNextGreaterElementAllFound() {
        // Given
        int[] nums1 = {2, 4};
        int[] nums2 = {1, 2, 3, 4};
        int[] expected = {3, -1};

        // When
        int[] result = monotonicStack.nextGreaterElement(nums1, nums2);

        // Then
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Next Greater Elements II - Circular")
    void testNextGreaterElementsCircular() {
        // Given
        int[] nums = {1, 2, 1};
        int[] expected = {2, -1, 2};

        // When
        int[] result = monotonicStack.nextGreaterElements(nums);

        // Then
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Next Greater Elements II - All Same")
    void testNextGreaterElementsCircularAllSame() {
        // Given
        int[] nums = {1, 1, 1};
        int[] expected = {-1, -1, -1};

        // When
        int[] result = monotonicStack.nextGreaterElements(nums);

        // Then
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Previous Greater Element")
    void testPreviousGreaterElement() {
        // Given
        int[] nums = {2, 1, 2, 4, 3, 1};
        int[] expected = {-1, 2, -1, -1, 4, 3};

        // When
        int[] result = monotonicStack.previousGreaterElement(nums);

        // Then
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Daily Temperatures")
    void testDailyTemperatures() {
        // Given
        int[] temperatures = {73, 74, 75, 71, 69, 72, 76, 73};
        int[] expected = {1, 1, 4, 2, 1, 1, 0, 0};

        // When
        int[] result = monotonicStack.dailyTemperatures(temperatures);

        // Then
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Daily Temperatures - Single Day")
    void testDailyTemperaturesSingleDay() {
        // Given
        int[] temperatures = {30};
        int[] expected = {0};

        // When
        int[] result = monotonicStack.dailyTemperatures(temperatures);

        // Then
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Largest Rectangle in Histogram")
    void testLargestRectangleArea() {
        // Given
        int[] heights = {2, 1, 5, 6, 2, 3};
        int expected = 10; // Rectangle with height 5 and width 2

        // When
        int result = monotonicStack.largestRectangleArea(heights);

        // Then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Largest Rectangle - All Same Height")
    void testLargestRectangleAllSame() {
        // Given
        int[] heights = {2, 2, 2, 2};
        int expected = 8; // All bars form one rectangle

        // When
        int result = monotonicStack.largestRectangleArea(heights);

        // Then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Largest Rectangle - Empty Array")
    void testLargestRectangleEmpty() {
        // Given
        int[] heights = {};
        int expected = 0;

        // When
        int result = monotonicStack.largestRectangleArea(heights);

        // Then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Maximal Rectangle in Binary Matrix")
    void testMaximalRectangle() {
        // Given
        char[][] matrix = {
                {'1', '0', '1', '0', '0'},
                {'1', '0', '1', '1', '1'},
                {'1', '1', '1', '1', '1'},
                {'1', '0', '0', '1', '0'}
        };
        int expected = 6; // 3x2 rectangle

        // When
        int result = monotonicStack.maximalRectangle(matrix);

        // Then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Maximal Rectangle - Empty Matrix")
    void testMaximalRectangleEmpty() {
        // Given
        char[][] matrix = {};
        int expected = 0;

        // When
        int result = monotonicStack.maximalRectangle(matrix);

        // Then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Stock Span Problem")
    void testStockSpan() {
        // Given
        int[] prices = {100, 80, 60, 70, 60, 75, 85};
        int[] expected = {1, 1, 1, 2, 1, 4, 6};

        // When
        int[] result = monotonicStack.stockSpan(prices);

        // Then
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Stock Span - Increasing Prices")
    void testStockSpanIncreasing() {
        // Given
        int[] prices = {10, 20, 30, 40, 50};
        int[] expected = {1, 2, 3, 4, 5};

        // When
        int[] result = monotonicStack.stockSpan(prices);

        // Then
        assertArrayEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("provideRemoveKDigitsTestCases")
    @DisplayName("Remove K Digits - Multiple Cases")
    void testRemoveKdigits(String num, int k, String expected) {
        String result = monotonicStack.removeKdigits(num, k);
        assertEquals(expected, result);
    }

    private static Stream<Arguments> provideRemoveKDigitsTestCases() {
        return Stream.of(
                Arguments.of("1432219", 3, "1219"),
                Arguments.of("10200", 1, "200"),
                Arguments.of("10", 2, "0"),
                Arguments.of("9", 1, "0"),
                Arguments.of("112", 1, "11")
        );
    }

    @Test
    @DisplayName("Sliding Window Maximum")
    void testMaxSlidingWindow() {
        // Given
        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;
        int[] expected = {3, 3, 5, 5, 6, 7};

        // When
        int[] result = monotonicStack.maxSlidingWindow(nums, k);

        // Then
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Sliding Window Maximum - Window Size 1")
    void testMaxSlidingWindowSizeOne() {
        // Given
        int[] nums = {1, 2, 3, 4, 5};
        int k = 1;
        int[] expected = {1, 2, 3, 4, 5};

        // When
        int[] result = monotonicStack.maxSlidingWindow(nums, k);

        // Then
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Sum of Subarray Minimums")
    void testSumSubarrayMins() {
        // Given
        int[] arr = {3, 1, 2, 4};
        int expected = 17;

        // When
        int result = monotonicStack.sumSubarrayMins(arr);

        // Then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Sum of Subarray Minimums - Single Element")
    void testSumSubarrayMinsSingle() {
        // Given
        int[] arr = {11};
        int expected = 11;

        // When
        int result = monotonicStack.sumSubarrayMins(arr);

        // Then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Trapping Rain Water")
    void testTrap() {
        // Given
        int[] height = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        int expected = 6;

        // When
        int result = monotonicStack.trap(height);

        // Then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Trapping Rain Water - No Water")
    void testTrapNoWater() {
        // Given
        int[] height = {1, 2, 3, 4, 5};
        int expected = 0;

        // When
        int result = monotonicStack.trap(height);

        // Then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Find 132 Pattern")
    void testFind132Pattern() {
        // Given
        int[] nums1 = {1, 2, 3, 4};
        int[] nums2 = {3, 1, 4, 2};
        int[] nums3 = {-1, 3, 2, 0};

        // When & Then
        assertFalse(monotonicStack.find132pattern(nums1));
        assertTrue(monotonicStack.find132pattern(nums2));
        assertTrue(monotonicStack.find132pattern(nums3));
    }

    @Test
    @DisplayName("Find 132 Pattern - Too Short Array")
    void testFind132PatternTooShort() {
        // Given
        int[] nums = {1, 2};

        // When
        boolean result = monotonicStack.find132pattern(nums);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Create Maximum Number")
    void testMaxNumber() {
        // Given
        int[] nums1 = {3, 4, 6, 5};
        int[] nums2 = {9, 1, 2, 5, 8, 3};
        int k = 5;
        int[] expected = {9, 8, 6, 5, 3};

        // When
        int[] result = monotonicStack.maxNumber(nums1, nums2, k);

        // Then
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Create Maximum Number - Edge Case")
    void testMaxNumberEdgeCase() {
        // Given
        int[] nums1 = {6, 7};
        int[] nums2 = {6, 0, 4};
        int k = 5;
        int[] expected = {6, 7, 6, 0, 4};

        // When
        int[] result = monotonicStack.maxNumber(nums1, nums2, k);

        // Then
        assertArrayEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("provideLargeTestCases")
    @DisplayName("Performance Test - Large Inputs")
    void testPerformanceLargeInputs(int[] input, String testType) {
        // When
        long startTime = System.currentTimeMillis();

        switch (testType) {
            case "dailyTemperatures":
                monotonicStack.dailyTemperatures(input);
                break;
            case "largestRectangle":
                monotonicStack.largestRectangleArea(input);
                break;
            case "trap":
                monotonicStack.trap(input);
                break;
        }

        long endTime = System.currentTimeMillis();

        // Then - should complete within reasonable time
        assertTrue(endTime - startTime < 100,
                "Large input should complete within 100ms for " + testType);
    }

    private static Stream<Arguments> provideLargeTestCases() {
        int[] largeArray = new int[1000];
        Arrays.fill(largeArray, 1);

        return Stream.of(
                Arguments.of(largeArray, "dailyTemperatures"),
                Arguments.of(largeArray, "largestRectangle"),
                Arguments.of(largeArray, "trap")
        );
    }

    @Test
    @DisplayName("Stress Test - Monotonic Stack Properties")
    void testMonotonicStackProperties() {
        // Given - Array with random values
        int[] nums = {5, 2, 8, 1, 9, 3, 7, 4, 6};

        // When
        int[] nextGreater = monotonicStack.nextGreaterElements(nums);
        int[] dailyTemp = monotonicStack.dailyTemperatures(nums);

        // Then - Verify properties
        for (int i = 0; i < nums.length; i++) {
            if (nextGreater[i] != -1) {
                assertTrue(nextGreater[i] > nums[i],
                        "Next greater element should be larger");
            }

            assertTrue(dailyTemp[i] >= 0,
                    "Daily temperature result should be non-negative");
        }
    }

    @Test
    @DisplayName("Edge Cases - Empty and Single Element Arrays")
    void testEdgeCases() {
        // Empty arrays
        assertArrayEquals(new int[0], monotonicStack.nextGreaterElements(new int[0]));
        assertArrayEquals(new int[0], monotonicStack.dailyTemperatures(new int[0]));
        assertEquals(0, monotonicStack.largestRectangleArea(new int[0]));

        // Single element arrays
        assertArrayEquals(new int[]{-1}, monotonicStack.nextGreaterElements(new int[]{5}));
        assertArrayEquals(new int[]{0}, monotonicStack.dailyTemperatures(new int[]{5}));
        assertEquals(5, monotonicStack.largestRectangleArea(new int[]{5}));
    }
}