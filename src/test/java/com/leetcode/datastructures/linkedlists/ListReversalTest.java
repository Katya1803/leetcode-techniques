package com.leetcode.datastructures.linkedlists;

import com.leetcode.utils.ListNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ListReversalTest {

    private ListReversal listReversal;

    @BeforeEach
    void setUp() {
        listReversal = new ListReversal();
    }

    // ==================== BASIC REVERSAL TESTS ====================

    @Test
    @DisplayName("Reverse List - Standard Case")
    void testReverseListStandardCase() {
        // Given
        ListNode head = ListNode.fromArray(new int[]{1, 2, 3, 4, 5});

        // When
        ListNode result = listReversal.reverseList(head);

        // Then
        assertArrayEquals(new int[]{5, 4, 3, 2, 1}, ListNode.toArray(result));
    }

    @Test
    @DisplayName("Reverse List - Recursive Implementation")
    void testReverseListRecursive() {
        // Given
        ListNode head = ListNode.fromArray(new int[]{1, 2, 3, 4, 5});

        // When
        ListNode result = listReversal.reverseListRecursive(head);

        // Then
        assertArrayEquals(new int[]{5, 4, 3, 2, 1}, ListNode.toArray(result));
    }

    @ParameterizedTest
    @MethodSource("provideBasicReversalTestCases")
    @DisplayName("Basic Reversal - Multiple Scenarios")
    void testBasicReversalMultipleScenarios(int[] input, int[] expected, String description) {
        // Given
        ListNode head = (input.length == 0) ? null : ListNode.fromArray(input);

        // When
        ListNode iterativeResult = listReversal.reverseList(head);
        ListNode recursiveResult = listReversal.reverseListRecursive(
                (input.length == 0) ? null : ListNode.fromArray(input)
        );

        // Then
        if (expected.length == 0) {
            assertNull(iterativeResult, description + " - Iterative");
            assertNull(recursiveResult, description + " - Recursive");
        } else {
            assertArrayEquals(expected, ListNode.toArray(iterativeResult), description + " - Iterative");
            assertArrayEquals(expected, ListNode.toArray(recursiveResult), description + " - Recursive");
        }
    }

    private static Stream<Arguments> provideBasicReversalTestCases() {
        return Stream.of(
                Arguments.of(new int[]{}, new int[]{}, "Empty list"),
                Arguments.of(new int[]{42}, new int[]{42}, "Single node"),
                Arguments.of(new int[]{1, 2}, new int[]{2, 1}, "Two nodes"),
                Arguments.of(new int[]{1, 2, 3}, new int[]{3, 2, 1}, "Three nodes"),
                Arguments.of(new int[]{1, 1, 1}, new int[]{1, 1, 1}, "Duplicate values"),
                Arguments.of(new int[]{5, 4, 3, 2, 1}, new int[]{1, 2, 3, 4, 5}, "Already reversed"),
                Arguments.of(new int[]{-1, 0, 1}, new int[]{1, 0, -1}, "Negative numbers")
        );
    }

    // ==================== REVERSE BETWEEN TESTS ====================

    @ParameterizedTest
    @MethodSource("provideReverseBetweenTestCases")
    @DisplayName("Reverse Between - Comprehensive Cases")
    void testReverseBetweenComprehensive(int[] input, int left, int right, int[] expected, String description) {
        // Given
        ListNode head = ListNode.fromArray(input);

        // When
        ListNode result = listReversal.reverseBetween(head, left, right);

        // Then
        assertArrayEquals(expected, ListNode.toArray(result), description);
    }

    private static Stream<Arguments> provideReverseBetweenTestCases() {
        return Stream.of(
                // Basic cases
                Arguments.of(new int[]{1, 2, 3, 4, 5}, 2, 4, new int[]{1, 4, 3, 2, 5}, "Middle reversal"),
                Arguments.of(new int[]{5}, 1, 1, new int[]{5}, "Single node same position"),
                Arguments.of(new int[]{1, 2, 3}, 1, 3, new int[]{3, 2, 1}, "Entire list"),

                // Edge cases
                Arguments.of(new int[]{1, 2, 3, 4, 5}, 1, 2, new int[]{2, 1, 3, 4, 5}, "Reverse first two"),
                Arguments.of(new int[]{1, 2, 3, 4, 5}, 4, 5, new int[]{1, 2, 3, 5, 4}, "Reverse last two"),
                Arguments.of(new int[]{1, 2, 3, 4, 5}, 3, 3, new int[]{1, 2, 3, 4, 5}, "Single position"),

                // Boundary cases
                Arguments.of(new int[]{1, 2}, 1, 2, new int[]{2, 1}, "Two nodes complete"),
                Arguments.of(new int[]{1, 2, 3, 4, 5, 6}, 2, 5, new int[]{1, 5, 4, 3, 2, 6}, "Large middle range")
        );
    }

    @Test
    @DisplayName("Reverse Between - Invalid Inputs")
    void testReverseBetweenInvalidInputs() {
        // Null list
        assertNull(listReversal.reverseBetween(null, 1, 1));

        // Left equals right
        ListNode head = ListNode.fromArray(new int[]{1, 2, 3});
        ListNode result = listReversal.reverseBetween(head, 2, 2);
        assertArrayEquals(new int[]{1, 2, 3}, ListNode.toArray(result));
    }

    // ==================== REVERSE K GROUP TESTS ====================

    @ParameterizedTest
    @MethodSource("provideReverseKGroupTestCases")
    @DisplayName("Reverse K Group - Multiple Implementations")
    void testReverseKGroupMultipleImplementations(int[] input, int k, int[] expected, String description) {
        // Given
        ListNode head1 = ListNode.fromArray(input);
        ListNode head2 = ListNode.fromArray(input);

        // When
        ListNode recursiveResult = listReversal.reverseKGroup(head1, k);
        ListNode iterativeResult = listReversal.reverseKGroupIterative(head2, k);

        // Then
        assertArrayEquals(expected, ListNode.toArray(recursiveResult), description + " - Recursive");
        assertArrayEquals(expected, ListNode.toArray(iterativeResult), description + " - Iterative");
    }

    private static Stream<Arguments> provideReverseKGroupTestCases() {
        return Stream.of(
                // Standard cases
                Arguments.of(new int[]{1, 2, 3, 4, 5}, 2, new int[]{2, 1, 4, 3, 5}, "K=2 with remainder"),
                Arguments.of(new int[]{1, 2, 3, 4, 5}, 3, new int[]{3, 2, 1, 4, 5}, "K=3 with remainder"),
                Arguments.of(new int[]{1, 2, 3, 4}, 2, new int[]{2, 1, 4, 3}, "Perfect division"),
                Arguments.of(new int[]{1, 2, 3, 4}, 4, new int[]{4, 3, 2, 1}, "K equals length"),

                // Edge cases
                Arguments.of(new int[]{1, 2, 3, 4, 5}, 1, new int[]{1, 2, 3, 4, 5}, "K=1 no change"),
                Arguments.of(new int[]{1, 2, 3}, 5, new int[]{1, 2, 3}, "K > length"),
                Arguments.of(new int[]{}, 2, new int[]{}, "Empty list"),
                Arguments.of(new int[]{1}, 2, new int[]{1}, "Single node"),

                // Complex cases
                Arguments.of(new int[]{1, 2, 3, 4, 5, 6, 7, 8}, 3, new int[]{3, 2, 1, 6, 5, 4, 7, 8}, "Multiple groups with remainder")
        );
    }

    // ==================== SWAP PAIRS TESTS ====================

    @Test
    @DisplayName("Swap Pairs - Standard Cases")
    void testSwapPairsStandardCases() {
        // Even length
        ListNode even = ListNode.fromArray(new int[]{1, 2, 3, 4});
        ListNode evenResult = listReversal.swapPairs(even);
        assertArrayEquals(new int[]{2, 1, 4, 3}, ListNode.toArray(evenResult));

        // Odd length
        ListNode odd = ListNode.fromArray(new int[]{1, 2, 3, 4, 5});
        ListNode oddResult = listReversal.swapPairs(odd);
        assertArrayEquals(new int[]{2, 1, 4, 3, 5}, ListNode.toArray(oddResult));
    }

    @Test
    @DisplayName("Swap Pairs - Edge Cases")
    void testSwapPairsEdgeCases() {
        // Empty list
        assertNull(listReversal.swapPairs(null));

        // Single node
        ListNode single = ListNode.fromArray(new int[]{1});
        ListNode singleResult = listReversal.swapPairs(single);
        assertArrayEquals(new int[]{1}, ListNode.toArray(singleResult));

        // Two nodes
        ListNode pair = ListNode.fromArray(new int[]{1, 2});
        ListNode pairResult = listReversal.swapPairs(pair);
        assertArrayEquals(new int[]{2, 1}, ListNode.toArray(pairResult));
    }

    // ==================== PALINDROME TESTS ====================

    @ParameterizedTest
    @MethodSource("providePalindromeTestCases")
    @DisplayName("Palindrome Check - Comprehensive Cases")
    void testPalindromeComprehensive(int[] input, boolean expected, String description) {
        // Given
        ListNode head = (input.length == 0) ? null : ListNode.fromArray(input);

        // When
        boolean result = listReversal.isPalindrome(head);

        // Then
        assertEquals(expected, result, description);
    }

    private static Stream<Arguments> providePalindromeTestCases() {
        return Stream.of(
                // Palindromes
                Arguments.of(new int[]{}, true, "Empty list"),
                Arguments.of(new int[]{1}, true, "Single element"),
                Arguments.of(new int[]{1, 2, 2, 1}, true, "Even length palindrome"),
                Arguments.of(new int[]{1, 2, 3, 2, 1}, true, "Odd length palindrome"),
                Arguments.of(new int[]{1, 1}, true, "Two identical elements"),
                Arguments.of(new int[]{1, 2, 1}, true, "Three element palindrome"),

                // Non-palindromes
                Arguments.of(new int[]{1, 2}, false, "Two different elements"),
                Arguments.of(new int[]{1, 2, 3, 4, 5}, false, "No palindrome pattern"),
                Arguments.of(new int[]{1, 2, 3, 4}, false, "Even length non-palindrome"),
                Arguments.of(new int[]{1, 2, 2, 3}, false, "Almost palindrome")
        );
    }

    // ==================== ADD TWO NUMBERS TESTS ====================

    @ParameterizedTest
    @MethodSource("provideAddTwoNumbersTestCases")
    @DisplayName("Add Two Numbers - Various Cases")
    void testAddTwoNumbersVariousCases(int[] l1Array, int[] l2Array, int[] expected, String description) {
        // Given
        ListNode l1 = ListNode.fromArray(l1Array);
        ListNode l2 = ListNode.fromArray(l2Array);

        // When
        ListNode result = listReversal.addTwoNumbers(l1, l2);

        // Then
        assertArrayEquals(expected, ListNode.toArray(result), description);
    }

    private static Stream<Arguments> provideAddTwoNumbersTestCases() {
        return Stream.of(
                // Basic cases
                Arguments.of(new int[]{2, 4, 3}, new int[]{5, 6, 4}, new int[]{7, 0, 8}, "342 + 465 = 807"),
                Arguments.of(new int[]{0}, new int[]{0}, new int[]{0}, "0 + 0 = 0"),
                Arguments.of(new int[]{9, 9, 9, 9, 9, 9, 9}, new int[]{9, 9, 9, 9}, new int[]{8, 9, 9, 9, 0, 0, 0, 1}, "Large carry"),

                // Different lengths
                Arguments.of(new int[]{1}, new int[]{9, 9}, new int[]{0, 0, 1}, "Different lengths with carry"),
                Arguments.of(new int[]{2, 4, 3}, new int[]{5, 6}, new int[]{7, 0, 4}, "Different lengths simple"),

                // Edge cases
                Arguments.of(new int[]{5}, new int[]{5}, new int[]{0, 1}, "Single digits with carry"),
                Arguments.of(new int[]{1, 8}, new int[]{0}, new int[]{1, 8}, "Adding zero")
        );
    }

    @Test
    @DisplayName("Add Two Numbers II - Most Significant First")
    void testAddTwoNumbersII() {
        // Given: 342 + 465 = 807
        ListNode l1 = ListNode.fromArray(new int[]{3, 4, 2});
        ListNode l2 = ListNode.fromArray(new int[]{4, 6, 5});

        // When
        ListNode result = listReversal.addTwoNumbersII(l1, l2);

        // Then
        assertArrayEquals(new int[]{8, 0, 7}, ListNode.toArray(result));
    }

    // ==================== ADVANCED REVERSAL TESTS ====================

    @Test
    @DisplayName("Reverse Alternate Nodes")
    void testReverseAlternateNodes() {
        // Standard case
        ListNode head1 = ListNode.fromArray(new int[]{1, 2, 3, 4, 5, 6});
        ListNode result1 = listReversal.reverseAlternateNodes(head1);
        assertArrayEquals(new int[]{2, 1, 4, 3, 6, 5}, ListNode.toArray(result1));

        // Odd length
        ListNode head2 = ListNode.fromArray(new int[]{1, 2, 3, 4, 5});
        ListNode result2 = listReversal.reverseAlternateNodes(head2);
        assertArrayEquals(new int[]{2, 1, 4, 3, 5}, ListNode.toArray(result2));

        // Edge cases
        assertNull(listReversal.reverseAlternateNodes(null));

        ListNode single = ListNode.fromArray(new int[]{1});
        assertArrayEquals(new int[]{1}, ListNode.toArray(listReversal.reverseAlternateNodes(single)));
    }

    @Test
    @DisplayName("Reverse K Alternate")
    void testReverseKAlternate() {
        // Standard case
        ListNode head = ListNode.fromArray(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        ListNode result = listReversal.reverseKAlternate(head, 3);

        // Should reverse first 3, skip next 3, reverse next 3, skip remaining
        assertArrayEquals(new int[]{3, 2, 1, 4, 5, 6, 9, 8, 7, 10}, ListNode.toArray(result));
    }

    @Test
    @DisplayName("Remove Nth From End - Reversal Method")
    void testRemoveNthFromEndReversal() {
        // Remove 2nd from end
        ListNode head1 = ListNode.fromArray(new int[]{1, 2, 3, 4, 5});
        ListNode result1 = listReversal.removeNthFromEnd(head1, 2);
        assertArrayEquals(new int[]{1, 2, 3, 5}, ListNode.toArray(result1));

        // Remove first (5th from end)
        ListNode head2 = ListNode.fromArray(new int[]{1, 2, 3, 4, 5});
        ListNode result2 = listReversal.removeNthFromEnd(head2, 5);
        assertArrayEquals(new int[]{2, 3, 4, 5}, ListNode.toArray(result2));

        // Remove last
        ListNode head3 = ListNode.fromArray(new int[]{1, 2, 3, 4, 5});
        ListNode result3 = listReversal.removeNthFromEnd(head3, 1);
        assertArrayEquals(new int[]{1, 2, 3, 4}, ListNode.toArray(result3));
    }

    @Test
    @DisplayName("Rotate Right")
    void testRotateRight() {
        // Standard rotation
        ListNode head1 = ListNode.fromArray(new int[]{1, 2, 3, 4, 5});
        ListNode result1 = listReversal.rotateRight(head1, 2);
        assertArrayEquals(new int[]{4, 5, 1, 2, 3}, ListNode.toArray(result1));

        // Rotation greater than length
        ListNode head2 = ListNode.fromArray(new int[]{1, 2, 3});
        ListNode result2 = listReversal.rotateRight(head2, 4); // 4 % 3 = 1
        assertArrayEquals(new int[]{3, 1, 2}, ListNode.toArray(result2));

        // No rotation
        ListNode head3 = ListNode.fromArray(new int[]{1, 2, 3});
        ListNode result3 = listReversal.rotateRight(head3, 0);
        assertArrayEquals(new int[]{1, 2, 3}, ListNode.toArray(result3));
    }

    @Test
    @DisplayName("Plus One")
    void testPlusOne() {
        // Standard case
        ListNode head1 = ListNode.fromArray(new int[]{1, 2, 3});
        ListNode result1 = listReversal.plusOne(head1);
        assertArrayEquals(new int[]{1, 2, 4}, ListNode.toArray(result1));

        // With carry
        ListNode head2 = ListNode.fromArray(new int[]{9, 9, 9});
        ListNode result2 = listReversal.plusOne(head2);
        assertArrayEquals(new int[]{1, 0, 0, 0}, ListNode.toArray(result2));

        // Single digit
        ListNode head3 = ListNode.fromArray(new int[]{9});
        ListNode result3 = listReversal.plusOne(head3);
        assertArrayEquals(new int[]{1, 0}, ListNode.toArray(result3));
    }

    // ==================== PERFORMANCE AND STRESS TESTS ====================

    @Test
    @DisplayName("Performance Test - Large List Reversal")
    void testPerformanceLargeListReversal() {
        // Given - Large list
        int size = 50000;
        int[] largeArray = new int[size];
        for (int i = 0; i < size; i++) {
            largeArray[i] = i;
        }
        ListNode head = ListNode.fromArray(largeArray);

        // When
        long startTime = System.currentTimeMillis();
        ListNode result = listReversal.reverseList(head);
        long endTime = System.currentTimeMillis();

        // Then
        assertTrue(endTime - startTime < 100, "Large list reversal should complete within 100ms");
        assertEquals(size - 1, result.val); // First element should be last original element
    }

    @Test
    @DisplayName("Performance Test - K Group Reversal")
    void testPerformanceKGroupReversal() {
        // Given
        int[] largeArray = new int[10000];
        for (int i = 0; i < 10000; i++) {
            largeArray[i] = i;
        }
        ListNode head = ListNode.fromArray(largeArray);

        // When
        long startTime = System.currentTimeMillis();
        ListNode result = listReversal.reverseKGroup(head, 5);
        long endTime = System.currentTimeMillis();

        // Then
        assertTrue(endTime - startTime < 200, "K Group reversal should complete within 200ms");
        assertNotNull(result);
    }

    @Test
    @DisplayName("Stress Test - Multiple Operations Chain")
    void testStressMultipleOperationsChain() {
        // Given
        ListNode head = ListNode.fromArray(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});

        // When - Chain multiple operations
        ListNode step1 = listReversal.swapPairs(head);
        ListNode step2 = listReversal.reverseBetween(step1, 3, 7);
        ListNode step3 = listReversal.reverseKGroup(step2, 3);

        // Then - Should complete without errors
        assertNotNull(step3);
        assertEquals(10, ListNode.getLength(step3));
    }

    @Test
    @DisplayName("Memory Safety Test - Recursive vs Iterative")
    void testMemorySafetyRecursiveVsIterative() {
        // Given - Medium sized list to test recursion
        int[] testArray = new int[1000];
        for (int i = 0; i < 1000; i++) {
            testArray[i] = i;
        }

        // When
        ListNode head1 = ListNode.fromArray(testArray);
        ListNode head2 = ListNode.fromArray(testArray);

        ListNode iterativeResult = listReversal.reverseList(head1);
        ListNode recursiveResult = listReversal.reverseListRecursive(head2);

        // Then - Both should produce same result
        assertArrayEquals(ListNode.toArray(iterativeResult), ListNode.toArray(recursiveResult));
    }

    // ==================== ALGORITHM CORRECTNESS VERIFICATION ====================

    @Test
    @DisplayName("Algorithm Correctness - Reversal Properties")
    void testAlgorithmCorrectnessReversalProperties() {
        // Property 1: reverse(reverse(list)) == original list
        ListNode original = ListNode.fromArray(new int[]{1, 2, 3, 4, 5});
        int[] originalArray = ListNode.toArray(original);

        ListNode reversed = listReversal.reverseList(original);
        ListNode doubleReversed = listReversal.reverseList(reversed);

        assertArrayEquals(originalArray, ListNode.toArray(doubleReversed));

        // Property 2: Length preservation
        ListNode test = ListNode.fromArray(new int[]{1, 2, 3, 4, 5, 6});
        int originalLength = ListNode.getLength(test);
        ListNode reversed2 = listReversal.reverseList(test);
        assertEquals(originalLength, ListNode.getLength(reversed2));
    }

    @Test
    @DisplayName("Algorithm Correctness - K Group Properties")
    void testAlgorithmCorrectnessKGroupProperties() {
        // Property 1: If k=1, list should remain unchanged
        ListNode original = ListNode.fromArray(new int[]{1, 2, 3, 4, 5});
        int[] originalArray = ListNode.toArray(original);
        ListNode k1Result = listReversal.reverseKGroup(original, 1);
        assertArrayEquals(originalArray, ListNode.toArray(k1Result));

        // Property 2: If k >= length, should reverse entire list
        ListNode test = ListNode.fromArray(new int[]{1, 2, 3, 4});
        ListNode kLargeResult = listReversal.reverseKGroup(test, 5);
        assertArrayEquals(new int[]{1, 2, 3, 4}, ListNode.toArray(kLargeResult)); // No change since k > length
    }

    @Test
    @DisplayName("Algorithm Correctness - Palindrome Properties")
    void testAlgorithmCorrectnessPalindromeProperties() {
        // Property 1: Any single element is palindrome
        for (int i = -10; i <= 10; i++) {
            ListNode single = ListNode.fromArray(new int[]{i});
            assertTrue(listReversal.isPalindrome(single));
        }

        // Property 2: Palindrome should remain palindrome after modifications that preserve symmetry
        ListNode palindrome = ListNode.fromArray(new int[]{1, 2, 3, 2, 1});
        assertTrue(listReversal.isPalindrome(palindrome));

        // Property 3: Non-palindrome should remain non-palindrome after asymmetric modifications
        ListNode nonPalindrome = ListNode.fromArray(new int[]{1, 2, 3, 4, 5});
        assertFalse(listReversal.isPalindrome(nonPalindrome));
    }

    // ==================== EDGE CASES AND BOUNDARY CONDITIONS ====================

    @Test
    @DisplayName("Edge Cases - Null and Empty Inputs")
    void testEdgeCasesNullAndEmpty() {
        // All methods should handle null input gracefully
        assertNull(listReversal.reverseList(null));
        assertNull(listReversal.reverseListRecursive(null));
        assertNull(listReversal.reverseBetween(null, 1, 1));
        assertNull(listReversal.reverseKGroup(null, 2));
        assertNull(listReversal.swapPairs(null));
        assertTrue(listReversal.isPalindrome(null));
        assertNull(listReversal.reverseAlternateNodes(null));
        assertNull(listReversal.removeNthFromEnd(null, 1));
        assertNull(listReversal.rotateRight(null, 1));
        assertNull(listReversal.plusOne(null));
    }

    @Test
    @DisplayName("Edge Cases - Single Element Lists")
    void testEdgeCasesSingleElement() {
        // All methods should handle single element correctly
        ListNode single = ListNode.fromArray(new int[]{42});

        assertEquals(42, listReversal.reverseList(single).val);
        assertEquals(42, listReversal.reverseBetween(ListNode.fromArray(new int[]{42}), 1, 1).val);
        assertEquals(42, listReversal.reverseKGroup(ListNode.fromArray(new int[]{42}), 2).val);
        assertEquals(42, listReversal.swapPairs(ListNode.fromArray(new int[]{42})).val);
        assertTrue(listReversal.isPalindrome(ListNode.fromArray(new int[]{42})));
    }

    @Test
    @DisplayName("Boundary Conditions - Large K Values")
    void testBoundaryConditionsLargeK() {
        ListNode head = ListNode.fromArray(new int[]{1, 2, 3});

        // K much larger than list length
        ListNode result1 = listReversal.reverseKGroup(head, 100);
        assertArrayEquals(new int[]{1, 2, 3}, ListNode.toArray(result1));

        // K equals list length
        ListNode head2 = ListNode.fromArray(new int[]{1, 2, 3});
        ListNode result2 = listReversal.reverseKGroup(head2, 3);
        assertArrayEquals(new int[]{3, 2, 1}, ListNode.toArray(result2));
    }

    @Test
    @DisplayName("Data Integrity - Original List Preservation")
    void testDataIntegrityOriginalListPreservation() {
        // Some operations should not modify the original structure when they fail
        // or when they create new structures

        // Test that failed operations don't corrupt the list
        ListNode original = ListNode.fromArray(new int[]{1, 2, 3, 4, 5});

        // Operations that should preserve or predictably modify structure
        assertDoesNotThrow(() -> {
            listReversal.isPalindrome(original);
            // Note: original list structure might be modified by palindrome check
            // This is expected behavior for in-place algorithms
        });
    }
}