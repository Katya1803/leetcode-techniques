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

class FastSlowPointerTest {

    private FastSlowPointer fastSlowPointer;

    @BeforeEach
    void setUp() {
        fastSlowPointer = new FastSlowPointer();
    }

    // ==================== CYCLE DETECTION TESTS ====================

    @Test
    @DisplayName("Has Cycle - No Cycle")
    void testHasCycleNoCycle() {
        // Given
        ListNode head = ListNode.fromArray(new int[]{1, 2, 3, 4, 5});

        // When
        boolean result = fastSlowPointer.hasCycle(head);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Has Cycle - With Cycle")
    void testHasCycleWithCycle() {
        // Given - Create cycle: 1 -> 2 -> 3 -> 4 -> 2 (cycle)
        ListNode head = FastSlowPointer.createListWithCycle(new int[]{1, 2, 3, 4}, 1);

        // When
        boolean result = fastSlowPointer.hasCycle(head);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Has Cycle - Self Loop")
    void testHasCycleSelfLoop() {
        // Given - Single node pointing to itself
        ListNode head = new ListNode(1);
        head.next = head;

        // When
        boolean result = fastSlowPointer.hasCycle(head);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Has Cycle - Edge Cases")
    void testHasCycleEdgeCases() {
        // Null list
        assertFalse(fastSlowPointer.hasCycle(null));

        // Single node no cycle
        ListNode single = new ListNode(1);
        assertFalse(fastSlowPointer.hasCycle(single));

        // Two nodes with cycle
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = head;
        assertTrue(fastSlowPointer.hasCycle(head));
    }

    @Test
    @DisplayName("Detect Cycle Start")
    void testDetectCycleStart() {
        // Given - Cycle starts at position 1 (0-indexed)
        ListNode head = FastSlowPointer.createListWithCycle(new int[]{3, 2, 0, -4}, 1);

        // When
        ListNode cycleStart = fastSlowPointer.detectCycle(head);

        // Then
        assertNotNull(cycleStart);
        assertEquals(2, cycleStart.val);
    }

    @Test
    @DisplayName("Detect Cycle Start - No Cycle")
    void testDetectCycleStartNoCycle() {
        // Given
        ListNode head = ListNode.fromArray(new int[]{1, 2, 3, 4});

        // When
        ListNode cycleStart = fastSlowPointer.detectCycle(head);

        // Then
        assertNull(cycleStart);
    }

    @Test
    @DisplayName("Cycle Length")
    void testCycleLength() {
        // Given - Create cycle of length 3: nodes 2, 3, 4 form cycle
        ListNode head = FastSlowPointer.createListWithCycle(new int[]{1, 2, 3, 4}, 1);

        // When
        int length = fastSlowPointer.cycleLength(head);

        // Then
        assertEquals(3, length);
    }

    @Test
    @DisplayName("Cycle Length - No Cycle")
    void testCycleLengthNoCycle() {
        // Given
        ListNode head = ListNode.fromArray(new int[]{1, 2, 3, 4});

        // When
        int length = fastSlowPointer.cycleLength(head);

        // Then
        assertEquals(0, length);
    }

    // ==================== MIDDLE FINDING TESTS ====================

    @ParameterizedTest
    @MethodSource("provideFindMiddleTestCases")
    @DisplayName("Find Middle - Multiple Cases")
    void testFindMiddle(int[] input, int expectedValue) {
        // Given
        ListNode head = ListNode.fromArray(input);

        // When
        ListNode middle = fastSlowPointer.findMiddle(head);

        // Then
        if (input.length == 0) {
            assertNull(middle);
        } else {
            assertEquals(expectedValue, middle.val);
        }
    }

    private static Stream<Arguments> provideFindMiddleTestCases() {
        return Stream.of(
                Arguments.of(new int[]{1, 2, 3, 4, 5}, 3),     // Odd length - middle
                Arguments.of(new int[]{1, 2, 3, 4}, 3),        // Even length - second middle
                Arguments.of(new int[]{1}, 1),                 // Single element
                Arguments.of(new int[]{1, 2}, 2),              // Two elements
                Arguments.of(new int[]{}, 0)                   // Empty (special case)
        );
    }

    @Test
    @DisplayName("Find Middle First - Even Length")
    void testFindMiddleFirst() {
        // Given
        ListNode head = ListNode.fromArray(new int[]{1, 2, 3, 4});

        // When
        ListNode middle = fastSlowPointer.findMiddleFirst(head);

        // Then
        assertEquals(2, middle.val); // First middle for even length
    }

    @Test
    @DisplayName("Delete Middle Node")
    void testDeleteMiddle() {
        // Given
        ListNode head = ListNode.fromArray(new int[]{1, 2, 3, 4, 5});

        // When
        ListNode result = fastSlowPointer.deleteMiddle(head);

        // Then
        assertArrayEquals(new int[]{1, 2, 4, 5}, ListNode.toArray(result));
    }

    @Test
    @DisplayName("Delete Middle - Edge Cases")
    void testDeleteMiddleEdgeCases() {
        // Single node
        ListNode single = ListNode.fromArray(new int[]{1});
        assertNull(fastSlowPointer.deleteMiddle(single));

        // Two nodes
        ListNode pair = ListNode.fromArray(new int[]{1, 2});
        assertArrayEquals(new int[]{1}, ListNode.toArray(fastSlowPointer.deleteMiddle(pair)));

        // Empty list
        assertNull(fastSlowPointer.deleteMiddle(null));
    }

    // ==================== NTH FROM END TESTS ====================

    @Test
    @DisplayName("Find Nth From End")
    void testFindNthFromEnd() {
        // Given
        ListNode head = ListNode.fromArray(new int[]{1, 2, 3, 4, 5});

        // When & Then
        assertEquals(4, fastSlowPointer.findNthFromEnd(head, 2).val);
        assertEquals(1, fastSlowPointer.findNthFromEnd(head, 5).val);
        assertEquals(5, fastSlowPointer.findNthFromEnd(head, 1).val);
    }

    @Test
    @DisplayName("Find Nth From End - Edge Cases")
    void testFindNthFromEndEdgeCases() {
        // N larger than list length
        ListNode head = ListNode.fromArray(new int[]{1, 2, 3});
        assertNull(fastSlowPointer.findNthFromEnd(head, 5));

        // Empty list
        assertNull(fastSlowPointer.findNthFromEnd(null, 1));

        // Single node
        ListNode single = ListNode.fromArray(new int[]{42});
        assertEquals(42, fastSlowPointer.findNthFromEnd(single, 1).val);
        assertNull(fastSlowPointer.findNthFromEnd(single, 2));
    }

    @Test
    @DisplayName("Remove Nth From End")
    void testRemoveNthFromEnd() {
        // Given
        ListNode head = ListNode.fromArray(new int[]{1, 2, 3, 4, 5});

        // When
        ListNode result = fastSlowPointer.removeNthFromEnd(head, 2);

        // Then
        assertArrayEquals(new int[]{1, 2, 3, 5}, ListNode.toArray(result));
    }

    @Test
    @DisplayName("Remove Nth From End - Remove First")
    void testRemoveNthFromEndFirst() {
        // Given
        ListNode head = ListNode.fromArray(new int[]{1, 2, 3, 4, 5});

        // When
        ListNode result = fastSlowPointer.removeNthFromEnd(head, 5);

        // Then
        assertArrayEquals(new int[]{2, 3, 4, 5}, ListNode.toArray(result));
    }

    @Test
    @DisplayName("Remove Nth From End - Remove Last")
    void testRemoveNthFromEndLast() {
        // Given
        ListNode head = ListNode.fromArray(new int[]{1, 2, 3, 4, 5});

        // When
        ListNode result = fastSlowPointer.removeNthFromEnd(head, 1);

        // Then
        assertArrayEquals(new int[]{1, 2, 3, 4}, ListNode.toArray(result));
    }

    // ==================== INTERSECTION TESTS ====================

    @Test
    @DisplayName("Get Intersection Node - With Intersection")
    void testGetIntersectionNodeWithIntersection() {
        // Given - Create two lists with intersection
        ListNode common = ListNode.fromArray(new int[]{8, 4, 5});

        ListNode headA = ListNode.fromArray(new int[]{4, 1});
        ListNode headB = ListNode.fromArray(new int[]{5, 6, 1});

        // Connect to common part
        ListNode currA = headA;
        while (currA.next != null) currA = currA.next;
        currA.next = common;

        ListNode currB = headB;
        while (currB.next != null) currB = currB.next;
        currB.next = common;

        // When
        ListNode intersection = fastSlowPointer.getIntersectionNode(headA, headB);

        // Then
        assertNotNull(intersection);
        assertEquals(8, intersection.val);
    }

    @Test
    @DisplayName("Get Intersection Node - No Intersection")
    void testGetIntersectionNodeNoIntersection() {
        // Given
        ListNode headA = ListNode.fromArray(new int[]{2, 6, 4});
        ListNode headB = ListNode.fromArray(new int[]{1, 5});

        // When
        ListNode intersection = fastSlowPointer.getIntersectionNode(headA, headB);

        // Then
        assertNull(intersection);
    }

    @Test
    @DisplayName("Get Intersection Node - Edge Cases")
    void testGetIntersectionNodeEdgeCases() {
        // One null list
        ListNode head = ListNode.fromArray(new int[]{1, 2, 3});
        assertNull(fastSlowPointer.getIntersectionNode(head, null));
        assertNull(fastSlowPointer.getIntersectionNode(null, head));

        // Both null
        assertNull(fastSlowPointer.getIntersectionNode(null, null));

        // Same list
        ListNode same = ListNode.fromArray(new int[]{1, 2, 3});
        assertEquals(1, fastSlowPointer.getIntersectionNode(same, same).val);
    }

    // ==================== PALINDROME TESTS ====================

    @ParameterizedTest
    @MethodSource("providePalindromeTestCases")
    @DisplayName("Is Palindrome - Multiple Cases")
    void testIsPalindrome(int[] input, boolean expected) {
        // Given
        ListNode head = ListNode.fromArray(input);

        // When
        boolean result = fastSlowPointer.isPalindrome(head);

        // Then
        assertEquals(expected, result);
    }

    private static Stream<Arguments> providePalindromeTestCases() {
        return Stream.of(
                Arguments.of(new int[]{1, 2, 2, 1}, true),
                Arguments.of(new int[]{1, 2}, false),
                Arguments.of(new int[]{1}, true),
                Arguments.of(new int[]{1, 2, 3, 2, 1}, true),
                Arguments.of(new int[]{1, 2, 3, 4, 5}, false),
                Arguments.of(new int[]{}, true),
                Arguments.of(new int[]{1, 1}, true),
                Arguments.of(new int[]{1, 2, 1}, true)
        );
    }

    // ==================== REORDER LIST TESTS ====================

    @Test
    @DisplayName("Reorder List - Even Length")
    void testReorderListEven() {
        // Given
        ListNode head = ListNode.fromArray(new int[]{1, 2, 3, 4});

        // When
        fastSlowPointer.reorderList(head);

        // Then
        assertArrayEquals(new int[]{1, 4, 2, 3}, ListNode.toArray(head));
    }

    @Test
    @DisplayName("Reorder List - Odd Length")
    void testReorderListOdd() {
        // Given
        ListNode head = ListNode.fromArray(new int[]{1, 2, 3, 4, 5});

        // When
        fastSlowPointer.reorderList(head);

        // Then
        assertArrayEquals(new int[]{1, 5, 2, 4, 3}, ListNode.toArray(head));
    }

    @Test
    @DisplayName("Reorder List - Edge Cases")
    void testReorderListEdgeCases() {
        // Single node
        ListNode single = ListNode.fromArray(new int[]{1});
        fastSlowPointer.reorderList(single);
        assertArrayEquals(new int[]{1}, ListNode.toArray(single));

        // Two nodes
        ListNode pair = ListNode.fromArray(new int[]{1, 2});
        fastSlowPointer.reorderList(pair);
        assertArrayEquals(new int[]{1, 2}, ListNode.toArray(pair));

        // Empty list
        fastSlowPointer.reorderList(null); // Should not crash
    }

    // ==================== SPLIT LIST TESTS ====================

    @Test
    @DisplayName("Split List To Parts - Even Split")
    void testSplitListToPartsEven() {
        // Given
        ListNode head = ListNode.fromArray(new int[]{1, 2, 3, 4, 5, 6});

        // When
        ListNode[] parts = fastSlowPointer.splitListToParts(head, 3);

        // Then
        assertEquals(3, parts.length);
        assertArrayEquals(new int[]{1, 2}, ListNode.toArray(parts[0]));
        assertArrayEquals(new int[]{3, 4}, ListNode.toArray(parts[1]));
        assertArrayEquals(new int[]{5, 6}, ListNode.toArray(parts[2]));
    }

    @Test
    @DisplayName("Split List To Parts - Uneven Split")
    void testSplitListToPartsUneven() {
        // Given
        ListNode head = ListNode.fromArray(new int[]{1, 2, 3, 4, 5});

        // When
        ListNode[] parts = fastSlowPointer.splitListToParts(head, 3);

        // Then
        assertEquals(3, parts.length);
        assertArrayEquals(new int[]{1, 2}, ListNode.toArray(parts[0]));
        assertArrayEquals(new int[]{3, 4}, ListNode.toArray(parts[1]));
        assertArrayEquals(new int[]{5}, ListNode.toArray(parts[2]));
    }

    @Test
    @DisplayName("Split List To Parts - More Parts Than Nodes")
    void testSplitListToPartsMoreParts() {
        // Given
        ListNode head = ListNode.fromArray(new int[]{1, 2, 3});

        // When
        ListNode[] parts = fastSlowPointer.splitListToParts(head, 5);

        // Then
        assertEquals(5, parts.length);
        assertArrayEquals(new int[]{1}, ListNode.toArray(parts[0]));
        assertArrayEquals(new int[]{2}, ListNode.toArray(parts[1]));
        assertArrayEquals(new int[]{3}, ListNode.toArray(parts[2]));
        assertNull(parts[3]);
        assertNull(parts[4]);
    }

    // ==================== ODD EVEN LIST TESTS ====================

    @Test
    @DisplayName("Odd Even List - Standard Case")
    void testOddEvenList() {
        // Given
        ListNode head = ListNode.fromArray(new int[]{1, 2, 3, 4, 5});

        // When
        ListNode result = fastSlowPointer.oddEvenList(head);

        // Then
        assertArrayEquals(new int[]{1, 3, 5, 2, 4}, ListNode.toArray(result));
    }

    @Test
    @DisplayName("Odd Even List - Even Length")
    void testOddEvenListEven() {
        // Given
        ListNode head = ListNode.fromArray(new int[]{2, 1, 3, 5, 6, 4, 7});

        // When
        ListNode result = fastSlowPointer.oddEvenList(head);

        // Then
        assertArrayEquals(new int[]{2, 3, 6, 7, 1, 5, 4}, ListNode.toArray(result));
    }

    @Test
    @DisplayName("Odd Even List - Edge Cases")
    void testOddEvenListEdgeCases() {
        // Single node
        ListNode single = ListNode.fromArray(new int[]{1});
        ListNode result1 = fastSlowPointer.oddEvenList(single);
        assertArrayEquals(new int[]{1}, ListNode.toArray(result1));

        // Two nodes
        ListNode pair = ListNode.fromArray(new int[]{1, 2});
        ListNode result2 = fastSlowPointer.oddEvenList(pair);
        assertArrayEquals(new int[]{1, 2}, ListNode.toArray(result2));

        // Empty list
        assertNull(fastSlowPointer.oddEvenList(null));
    }

    // ==================== ROTATE RIGHT TESTS ====================

    @Test
    @DisplayName("Rotate Right - Standard Case")
    void testRotateRight() {
        // Given
        ListNode head = ListNode.fromArray(new int[]{1, 2, 3, 4, 5});

        // When
        ListNode result = fastSlowPointer.rotateRight(head, 2);

        // Then
        assertArrayEquals(new int[]{4, 5, 1, 2, 3}, ListNode.toArray(result));
    }

    @Test
    @DisplayName("Rotate Right - K larger than length")
    void testRotateRightKLarger() {
        // Given
        ListNode head = ListNode.fromArray(new int[]{1, 2, 3});

        // When
        ListNode result = fastSlowPointer.rotateRight(head, 4); // 4 % 3 = 1

        // Then
        assertArrayEquals(new int[]{3, 1, 2}, ListNode.toArray(result));
    }

    @Test
    @DisplayName("Rotate Right - Edge Cases")
    void testRotateRightEdgeCases() {
        // K = 0
        ListNode head1 = ListNode.fromArray(new int[]{1, 2, 3});
        ListNode result1 = fastSlowPointer.rotateRight(head1, 0);
        assertArrayEquals(new int[]{1, 2, 3}, ListNode.toArray(result1));

        // Single node
        ListNode single = ListNode.fromArray(new int[]{1});
        ListNode result2 = fastSlowPointer.rotateRight(single, 1);
        assertArrayEquals(new int[]{1}, ListNode.toArray(result2));

        // Empty list
        assertNull(fastSlowPointer.rotateRight(null, 2));
    }

    // ==================== HAPPY NUMBER TESTS ====================

    @ParameterizedTest
    @MethodSource("provideHappyNumberTestCases")
    @DisplayName("Is Happy Number - Multiple Cases")
    void testIsHappy(int input, boolean expected) {
        boolean result = fastSlowPointer.isHappy(input);
        assertEquals(expected, result);
    }

    private static Stream<Arguments> provideHappyNumberTestCases() {
        return Stream.of(
                Arguments.of(19, true),   // 1² + 9² = 82, 8² + 2² = 68, ... → 1
                Arguments.of(2, false),   // Eventually cycles
                Arguments.of(7, true),    // Happy number
                Arguments.of(1, true),    // Already happy
                Arguments.of(4, false)    // Known sad number
        );
    }

    // ==================== FIND DUPLICATE TESTS ====================

    @Test
    @DisplayName("Find Duplicate Number")
    void testFindDuplicate() {
        // Given
        int[] nums1 = {1, 3, 4, 2, 2};
        int[] nums2 = {3, 1, 3, 4, 2};

        // When & Then
        assertEquals(2, fastSlowPointer.findDuplicate(nums1));
        assertEquals(3, fastSlowPointer.findDuplicate(nums2));
    }

    @Test
    @DisplayName("Find Duplicate - Edge Cases")
    void testFindDuplicateEdgeCases() {
        // Minimum case
        int[] nums1 = {1, 1};
        assertEquals(1, fastSlowPointer.findDuplicate(nums1));

        // Multiple duplicates (finds any one)
        int[] nums2 = {2, 2, 2, 2, 2};
        assertEquals(2, fastSlowPointer.findDuplicate(nums2));
    }

    // ==================== CYCLE INFO TESTS ====================

    @Test
    @DisplayName("Cycle Info - With Cycle")
    void testCycleInfoWithCycle() {
        // Given
        ListNode head = FastSlowPointer.createListWithCycle(new int[]{3, 2, 0, -4}, 1);

        // When
        Object[] info = fastSlowPointer.cycleInfo(head);

        // Then
        assertTrue((Boolean) info[0]); // Has cycle
        assertEquals(2, ((ListNode) info[1]).val); // Cycle start value
        assertEquals(3, (Integer) info[2]); // Cycle length
    }

    @Test
    @DisplayName("Cycle Info - No Cycle")
    void testCycleInfoNoCycle() {
        // Given
        ListNode head = ListNode.fromArray(new int[]{1, 2, 3, 4});

        // When
        Object[] info = fastSlowPointer.cycleInfo(head);

        // Then
        assertFalse((Boolean) info[0]); // No cycle
        assertNull(info[1]); // No cycle start
        assertEquals(0, (Integer) info[2]); // No cycle length
    }

    // ==================== PERFORMANCE TESTS ====================

    @Test
    @DisplayName("Performance Test - Large List Middle Finding")
    void testPerformanceLargeListMiddle() {
        // Given - Large list with 100000 nodes
        int[] largeArray = new int[100000];
        for (int i = 0; i < 100000; i++) {
            largeArray[i] = i;
        }
        ListNode head = ListNode.fromArray(largeArray);

        // When
        long startTime = System.currentTimeMillis();
        ListNode middle = fastSlowPointer.findMiddle(head);
        long endTime = System.currentTimeMillis();

        // Then
        assertTrue(endTime - startTime < 100, "Large list middle finding should complete within 100ms");
        assertEquals(50000, middle.val); // Middle element
    }

    @Test
    @DisplayName("Performance Test - Cycle Detection Large List")
    void testPerformanceCycleDetectionLarge() {
        // Given - Large list with cycle
        int[] largeArray = new int[10000];
        for (int i = 0; i < 10000; i++) {
            largeArray[i] = i;
        }
        ListNode head = FastSlowPointer.createListWithCycle(largeArray, 5000);

        // When
        long startTime = System.currentTimeMillis();
        boolean hasCycle = fastSlowPointer.hasCycle(head);
        ListNode cycleStart = fastSlowPointer.detectCycle(head);
        long endTime = System.currentTimeMillis();

        // Then
        assertTrue(endTime - startTime < 200, "Large list cycle detection should complete within 200ms");
        assertTrue(hasCycle);
        assertNotNull(cycleStart);
    }

    // ==================== STRESS TESTS ====================

    @Test
    @DisplayName("Stress Test - Multiple Operations")
    void testStressMultipleOperations() {
        // Given
        ListNode head = ListNode.fromArray(new int[]{1, 2, 3, 4, 5, 6, 7, 8});

        // When - Apply multiple operations
        ListNode middle = fastSlowPointer.findMiddle(head);
        ListNode nthFromEnd = fastSlowPointer.findNthFromEnd(head, 3);
        fastSlowPointer.reorderList(head);
        ListNode newMiddle = fastSlowPointer.findMiddle(head);

        // Then - All operations should complete successfully
        assertNotNull(middle);
        assertNotNull(nthFromEnd);
        assertNotNull(newMiddle);
        assertEquals(6, nthFromEnd.val);
    }

    @Test
    @DisplayName("Stress Test - Palindrome Large List")
    void testStressPalindromeLargeList() {
        // Given - Large palindrome
        int[] palindromeArray = new int[1000];
        for (int i = 0; i < 500; i++) {
            palindromeArray[i] = i;
            palindromeArray[999 - i] = i;
        }
        ListNode head = ListNode.fromArray(palindromeArray);

        // When
        long startTime = System.currentTimeMillis();
        boolean isPalindrome = fastSlowPointer.isPalindrome(head);
        long endTime = System.currentTimeMillis();

        // Then
        assertTrue(endTime - startTime < 100, "Large palindrome check should complete within 100ms");
        assertTrue(isPalindrome);
    }

    // ==================== ALGORITHM CORRECTNESS TESTS ====================

    @Test
    @DisplayName("Algorithm Correctness - Floyd's Cycle Detection Properties")
    void testFloydsAlgorithmProperties() {
        // Property 1: If no cycle, both methods return consistent results
        ListNode noCycle = ListNode.fromArray(new int[]{1, 2, 3, 4, 5});
        assertFalse(fastSlowPointer.hasCycle(noCycle));
        assertNull(fastSlowPointer.detectCycle(noCycle));
        assertEquals(0, fastSlowPointer.cycleLength(noCycle));

        // Property 2: If cycle exists, all methods should detect it
        ListNode withCycle = FastSlowPointer.createListWithCycle(new int[]{1, 2, 3, 4}, 1);
        assertTrue(fastSlowPointer.hasCycle(withCycle));
        assertNotNull(fastSlowPointer.detectCycle(withCycle));
        assertTrue(fastSlowPointer.cycleLength(withCycle) > 0);
    }

    @Test
    @DisplayName("Algorithm Correctness - Middle Finding Properties")
    void testMiddleFindingProperties() {
        // Property: For odd length, middle should be at position (n-1)/2
        ListNode odd = ListNode.fromArray(new int[]{0, 1, 2, 3, 4});
        ListNode middle = fastSlowPointer.findMiddle(odd);
        assertEquals(2, middle.val); // Position 2, value 2

        // Property: For even length, should return second middle
        ListNode even = ListNode.fromArray(new int[]{0, 1, 2, 3});
        ListNode middleEven = fastSlowPointer.findMiddle(even);
        assertEquals(2, middleEven.val); // Second middle

        // Property: First middle should be different for even length
        ListNode firstMiddle = fastSlowPointer.findMiddleFirst(even);
        assertEquals(1, firstMiddle.val); // First middle
    }

    @Test
    @DisplayName("Memory Safety - No Stack Overflow")
    void testMemorySafetyNoStackOverflow() {
        // Test with reasonably large list to ensure no stack overflow
        int[] largeArray = new int[10000];
        for (int i = 0; i < 10000; i++) {
            largeArray[i] = i;
        }
        ListNode head = ListNode.fromArray(largeArray);

        // All these operations should complete without stack overflow
        assertDoesNotThrow(() -> {
            fastSlowPointer.findMiddle(head);
            fastSlowPointer.findNthFromEnd(head, 100);
            fastSlowPointer.isPalindrome(head);
            fastSlowPointer.hasCycle(head);
        });
    }

    @Test
    @DisplayName("Edge Cases - Comprehensive Coverage")
    void testEdgeCasesComprehensive() {
        // Test all methods with null input
        assertFalse(fastSlowPointer.hasCycle(null));
        assertNull(fastSlowPointer.detectCycle(null));
        assertEquals(0, fastSlowPointer.cycleLength(null));
        assertNull(fastSlowPointer.findMiddle(null));
        assertNull(fastSlowPointer.findNthFromEnd(null, 1));
        assertNull(fastSlowPointer.removeNthFromEnd(null, 1));
        assertNull(fastSlowPointer.getIntersectionNode(null, null));
        assertTrue(fastSlowPointer.isPalindrome(null));

        // Test with single node
        ListNode single = ListNode.fromArray(new int[]{42});
        assertFalse(fastSlowPointer.hasCycle(single));
        assertEquals(42, fastSlowPointer.findMiddle(single).val);
        assertTrue(fastSlowPointer.isPalindrome(single));
    }
}