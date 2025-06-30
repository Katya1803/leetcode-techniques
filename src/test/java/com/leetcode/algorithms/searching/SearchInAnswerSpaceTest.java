package com.leetcode.algorithms.searching;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class SearchInAnswerSpaceTest {

    private SearchInAnswerSpace searchInAnswerSpace;

    @BeforeEach
    void setUp() {
        searchInAnswerSpace = new SearchInAnswerSpace();
    }

    @Test
    @DisplayName("Koko Eating Bananas")
    void testMinEatingSpeed() {
        // Given
        int[] piles1 = {3, 6, 7, 11};
        int h1 = 8;

        int[] piles2 = {30, 11, 23, 4, 20};
        int h2 = 5;

        int[] piles3 = {30, 11, 23, 4, 20};
        int h3 = 6;

        // When & Then
        assertEquals(4, searchInAnswerSpace.minEatingSpeed(piles1, h1));
        assertEquals(30, searchInAnswerSpace.minEatingSpeed(piles2, h2));
        assertEquals(23, searchInAnswerSpace.minEatingSpeed(piles3, h3));
    }

    @Test
    @DisplayName("Ship Within Days")
    void testShipWithinDays() {
        // Given
        int[] weights1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int days1 = 5;

        int[] weights2 = {3, 2, 2, 4, 1, 4};
        int days2 = 3;

        int[] weights3 = {1, 2, 3, 1, 1};
        int days3 = 4;

        // When & Then
        assertEquals(15, searchInAnswerSpace.shipWithinDays(weights1, days1));
        assertEquals(6, searchInAnswerSpace.shipWithinDays(weights2, days2));
        assertEquals(3, searchInAnswerSpace.shipWithinDays(weights3, days3));
    }

    @Test
    @DisplayName("Split Array Largest Sum")
    void testSplitArray() {
        // Given
        int[] nums1 = {7, 2, 5, 10, 8};
        int m1 = 2;

        int[] nums2 = {1, 2, 3, 4, 5};
        int m2 = 2;

        // When & Then
        assertEquals(18, searchInAnswerSpace.splitArray(nums1, m1));
        assertEquals(9, searchInAnswerSpace.splitArray(nums2, m2));
    }

    @Test
    @DisplayName("Smallest Divisor")
    void testSmallestDivisor() {
        // Given
        int[] nums1 = {1, 2, 5, 9};
        int threshold1 = 6;

        int[] nums2 = {44, 22, 33, 11, 1};
        int threshold2 = 5;

        // When & Then
        assertEquals(5, searchInAnswerSpace.smallestDivisor(nums1, threshold1));
        assertEquals(44, searchInAnswerSpace.smallestDivisor(nums2, threshold2));
    }
}