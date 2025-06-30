package com.leetcode.algorithms.sorting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class CustomComparatorsTest {

    private CustomComparators customComparators;

    @BeforeEach
    void setUp() {
        customComparators = new CustomComparators();
    }

    @Test
    @DisplayName("Frequency Sort")
    void testFrequencySort() {
        // Given
        int[] nums = {1, 1, 2, 2, 2, 3};

        // When
        int[] result = customComparators.frequencySort(nums);

        // Then - Should be sorted by frequency (ascending), then by value (descending)
        int[] expected = {3, 1, 1, 2, 2, 2};
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Frequency Sort - All Same Frequency")
    void testFrequencySortSameFreq() {
        // Given
        int[] nums = {2, 3, 1};

        // When
        int[] result = customComparators.frequencySort(nums);

        // Then - Same frequency, so sorted by value descending
        int[] expected = {3, 2, 1};
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Sort Strings by Length")
    void testSortStringsByLength() {
        // Given
        String[] strs = {"apple", "pie", "washington", "book"};

        // When
        String[] result = customComparators.sortStringsByLength(strs);

        // Then - Sorted by length, then lexicographically
        String[] expected = {"pie", "book", "apple", "washington"};
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Sort Strings by Length - Same Length")
    void testSortStringsBySameLengthr() {
        // Given
        String[] strs = {"cat", "dog", "bat"};

        // When
        String[] result = customComparators.sortStringsByLength(strs);

        // Then - Same length, so lexicographical order
        String[] expected = {"bat", "cat", "dog"};
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Sort Intervals")
    void testSortIntervals() {
        // Given
        int[][] intervals = {{1, 3}, {6, 9}, {8, 10}, {2, 6}, {15, 18}};

        // When
        int[][] result = customComparators.sortIntervals(intervals);

        // Then - Sorted by start time
        int[][] expected = {{1, 3}, {2, 6}, {6, 9}, {8, 10}, {15, 18}};
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Can Attend Meetings")
    void testCanAttendMeetings() {
        // Given
        int[][] meetings1 = {{0, 30}, {5, 10}, {15, 20}};
        int[][] meetings2 = {{7, 10}, {2, 4}};

        // When & Then
        assertFalse(customComparators.canAttendMeetings(meetings1)); // Overlap
        assertTrue(customComparators.canAttendMeetings(meetings2)); // No overlap
    }

    @Test
    @DisplayName("K Closest Points to Origin")
    void testKClosestPointsToOrigin() {
        // Given
        int[][] points = {{1, 1}, {-2, -2}, {3, 4}, {-1, 0}};
        int k = 2;

        // When
        int[][] result = customComparators.kClosestPointsToOrigin(points, k);

        // Then - Should return 2 closest points
        assertEquals(2, result.length);
        // Points should be sorted by distance: {-1,0} (dist=1), {1,1} (dist=2)
        assertArrayEquals(new int[]{-1, 0}, result[0]);
        assertArrayEquals(new int[]{1, 1}, result[1]);
    }

    @Test
    @DisplayName("Sort by Parity")
    void testSortByParity() {
        // Given
        int[] nums = {3, 1, 2, 4};

        // When
        int[] result = customComparators.sortByParity(nums);

        // Then - Odd numbers first, then even numbers, each group sorted ascending
        int[] expected = {1, 3, 2, 4};
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Sort Students")
    void testSortStudents() {
        // Given
        CustomComparators.Student[] students = {
                new CustomComparators.Student("Alice", 85),
                new CustomComparators.Student("Bob", 90),
                new CustomComparators.Student("Charlie", 85),
                new CustomComparators.Student("David", 95)
        };

        // When
        CustomComparators.Student[] result = customComparators.sortStudents(students);

        // Then - Sorted by grade descending, then name ascending
        assertEquals("David", result[0].name);
        assertEquals(95, result[0].grade);
        assertEquals("Bob", result[1].name);
        assertEquals(90, result[1].grade);
        assertEquals("Alice", result[2].name); // Alice comes before Charlie (same grade)
        assertEquals(85, result[2].grade);
        assertEquals("Charlie", result[3].name);
        assertEquals(85, result[3].grade);
    }

    @Test
    @DisplayName("Largest Number")
    void testLargestNumber() {
        // Given
        int[] nums1 = {10, 2};
        int[] nums2 = {3, 30, 34, 5, 9};
        int[] nums3 = {0, 0};

        // When & Then
        assertEquals("210", customComparators.largestNumber(nums1));
        assertEquals("9534330", customComparators.largestNumber(nums2));
        assertEquals("0", customComparators.largestNumber(nums3));
    }

    @Test
    @DisplayName("Largest Number - Single Digit")
    void testLargestNumberSingleDigit() {
        // Given
        int[] nums = {1};

        // When
        String result = customComparators.largestNumber(nums);

        // Then
        assertEquals("1", result);
    }
}