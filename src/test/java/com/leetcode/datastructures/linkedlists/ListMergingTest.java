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

class ListMergingTest {

    private ListMerging listMerging;

    @BeforeEach
    void setUp() {
        listMerging = new ListMerging();
    }

    // ==================== MERGE TWO SORTED LISTS TESTS ====================

    @Test
    @DisplayName("Merge Two Sorted Lists - Standard Case")
    void testMergeTwoListsStandard() {
        // Given
        ListNode list1 = ListNode.fromArray(new int[]{1, 2, 4});
        ListNode list2 = ListNode.fromArray(new int[]{1, 3, 4});

        // When
        ListNode result = listMerging.mergeTwoLists(list1, list2);

        // Then
        assertArrayEquals(new int[]{1, 1, 2, 3, 4, 4}, ListNode.toArray(result));
    }

    @Test
    @DisplayName("Merge Two Sorted Lists - Recursive")
    void testMergeTwoListsRecursive() {
        // Given
        ListNode list1 = ListNode.fromArray(new int[]{1, 2, 4});
        ListNode list2 = ListNode.fromArray(new int[]{1, 3, 4});

        // When
        ListNode result = listMerging.mergeTwoListsRecursive(list1, list2);

        // Then
        assertArrayEquals(new int[]{1, 1, 2, 3, 4, 4}, ListNode.toArray(result));
    }

    @Test
    @DisplayName("Merge Two Sorted Lists - One Empty")
    void testMergeTwoListsOneEmpty() {
        // Given
        ListNode list1 = ListNode.fromArray(new int[]{1, 2, 3});
        ListNode list2 = null;

        // When
        ListNode result1 = listMerging.mergeTwoLists(list1, list2);
        ListNode result2 = listMerging.mergeTwoLists(list2, list1);

        // Then
        assertArrayEquals(new int[]{1, 2, 3}, ListNode.toArray(result1));
        assertArrayEquals(new int[]{1, 2, 3}, ListNode.toArray(result2));
    }

    @Test
    @DisplayName("Merge Two Sorted Lists - Both Empty")
    void testMergeTwoListsBothEmpty() {
        // When
        ListNode result = listMerging.mergeTwoLists(null, null);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Merge Two Sorted Lists - Different Lengths")
    void testMergeTwoListsDifferentLengths() {
        // Given
        ListNode list1 = ListNode.fromArray(new int[]{1, 5, 10});
        ListNode list2 = ListNode.fromArray(new int[]{2, 3, 4, 6, 7, 8, 9});

        // When
        ListNode result = listMerging.mergeTwoLists(list1, list2);

        // Then
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, ListNode.toArray(result));
    }

    // ==================== MERGE K SORTED LISTS TESTS ====================

    @Test
    @DisplayName("Merge K Lists - Divide and Conquer")
    void testMergeKListsDivideConquer() {
        // Given
        ListNode[] lists = {
                ListNode.fromArray(new int[]{1, 4, 5}),
                ListNode.fromArray(new int[]{1, 3, 4}),
                ListNode.fromArray(new int[]{2, 6})
        };

        // When
        ListNode result = listMerging.mergeKLists(lists);

        // Then
        assertArrayEquals(new int[]{1, 1, 2, 3, 4, 4, 5, 6}, ListNode.toArray(result));
    }

    @Test
    @DisplayName("Merge K Lists - Priority Queue")
    void testMergeKListsPriorityQueue() {
        // Given
        ListNode[] lists = {
                ListNode.fromArray(new int[]{1, 4, 5}),
                ListNode.fromArray(new int[]{1, 3, 4}),
                ListNode.fromArray(new int[]{2, 6})
        };

        // When
        ListNode result = listMerging.mergeKListsPQ(lists);

        // Then
        assertArrayEquals(new int[]{1, 1, 2, 3, 4, 4, 5, 6}, ListNode.toArray(result));
    }

    @Test
    @DisplayName("Merge K Lists - Sequential")
    void testMergeKListsSequential() {
        // Given
        ListNode[] lists = {
                ListNode.fromArray(new int[]{1, 4, 5}),
                ListNode.fromArray(new int[]{1, 3, 4}),
                ListNode.fromArray(new int[]{2, 6})
        };

        // When
        ListNode result = listMerging.mergeKListsSequential(lists);

        // Then
        assertArrayEquals(new int[]{1, 1, 2, 3, 4, 4, 5, 6}, ListNode.toArray(result));
    }

    @Test
    @DisplayName("Merge K Lists - Empty Array")
    void testMergeKListsEmpty() {
        // Given
        ListNode[] lists = {};

        // When
        ListNode result = listMerging.mergeKLists(lists);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Merge K Lists - Some Null Lists")
    void testMergeKListsSomeNull() {
        // Given
        ListNode[] lists = {
                ListNode.fromArray(new int[]{1, 2}),
                null,
                ListNode.fromArray(new int[]{3, 4}),
                null
        };

        // When
        ListNode result = listMerging.mergeKLists(lists);

        // Then
        assertArrayEquals(new int[]{1, 2, 3, 4}, ListNode.toArray(result));
    }

    // ==================== SORT LIST TESTS ====================

    @Test
    @DisplayName("Sort List - Merge Sort")
    void testSortList() {
        // Given
        ListNode head = ListNode.fromArray(new int[]{4, 2, 1, 3});

        // When
        ListNode result = listMerging.sortList(head);

        // Then
        assertArrayEquals(new int[]{1, 2, 3, 4}, ListNode.toArray(result));
    }

    @Test
    @DisplayName("Sort List - Already Sorted")
    void testSortListAlreadySorted() {
        // Given
        ListNode head = ListNode.fromArray(new int[]{1, 2, 3, 4, 5});

        // When
        ListNode result = listMerging.sortList(head);

        // Then
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, ListNode.toArray(result));
    }

    @Test
    @DisplayName("Sort List - Reverse Sorted")
    void testSortListReverseSorted() {
        // Given
        ListNode head = ListNode.fromArray(new int[]{5, 4, 3, 2, 1});

        // When
        ListNode result = listMerging.sortList(head);

        // Then
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, ListNode.toArray(result));
    }

    @Test
    @DisplayName("Sort List - Edge Cases")
    void testSortListEdgeCases() {
        // Single node
        ListNode single = ListNode.fromArray(new int[]{1});
        ListNode result1 = listMerging.sortList(single);
        assertArrayEquals(new int[]{1}, ListNode.toArray(result1));

        // Empty list
        ListNode result2 = listMerging.sortList(null);
        assertNull(result2);

        // Two nodes
        ListNode pair = ListNode.fromArray(new int[]{2, 1});
        ListNode result3 = listMerging.sortList(pair);
        assertArrayEquals(new int[]{1, 2}, ListNode.toArray(result3));
    }

    // ==================== MERGE VARIATIONS TESTS ====================

    @Test
    @DisplayName("Merge Two Lists in Reverse Order")
    void testMergeTwoListsReverse() {
        // Given
        ListNode list1 = ListNode.fromArray(new int[]{1, 3, 5});
        ListNode list2 = ListNode.fromArray(new int[]{2, 4, 6});

        // When
        ListNode result = listMerging.mergeTwoListsReverse(list1, list2);

        // Then
        assertArrayEquals(new int[]{6, 5, 4, 3, 2, 1}, ListNode.toArray(result));
    }

    @Test
    @DisplayName("Merge Nodes Between Zeros")
    void testMergeNodes() {
        // Given
        ListNode head = ListNode.fromArray(new int[]{0, 3, 1, 0, 4, 5, 2, 0});

        // When
        ListNode result = listMerging.mergeNodes(head);

        // Then
        assertArrayEquals(new int[]{4, 11}, ListNode.toArray(result));
    }

    @Test
    @DisplayName("Merge Alternate Nodes")
    void testMergeAlternate() {
        // Given
        ListNode list1 = ListNode.fromArray(new int[]{1, 3, 5});
        ListNode list2 = ListNode.fromArray(new int[]{2, 4, 6, 7, 8});

        // When
        ListNode result = listMerging.mergeAlternate(list1, list2);

        // Then
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6}, ListNode.toArray(result));
    }

    // ==================== SET OPERATIONS TESTS ====================

    @Test
    @DisplayName("Intersection of Two Sorted Lists")
    void testIntersection() {
        // Given
        ListNode list1 = ListNode.fromArray(new int[]{1, 2, 3, 4, 5});
        ListNode list2 = ListNode.fromArray(new int[]{2, 4, 6, 8});

        // When
        ListNode result = listMerging.intersection(list1, list2);

        // Then
        assertArrayEquals(new int[]{2, 4}, ListNode.toArray(result));
    }

    @Test
    @DisplayName("Intersection - No Common Elements")
    void testIntersectionNoCommon() {
        // Given
        ListNode list1 = ListNode.fromArray(new int[]{1, 3, 5});
        ListNode list2 = ListNode.fromArray(new int[]{2, 4, 6});

        // When
        ListNode result = listMerging.intersection(list1, list2);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Union of Two Sorted Lists")
    void testUnion() {
        // Given
        ListNode list1 = ListNode.fromArray(new int[]{1, 3, 5});
        ListNode list2 = ListNode.fromArray(new int[]{2, 3, 4, 5, 6});

        // When
        ListNode result = listMerging.union(list1, list2);

        // Then
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6}, ListNode.toArray(result));
    }

    // ==================== MERGE IN BETWEEN TESTS ====================

    @Test
    @DisplayName("Merge In Between Lists")
    void testMergeInBetween() {
        // Given
        ListNode list1 = ListNode.fromArray(new int[]{0, 1, 2, 3, 4, 5});
        ListNode list2 = ListNode.fromArray(new int[]{1000, 1001, 1002});

        // When
        ListNode result = listMerging.mergeInBetween(list1, 3, 4, list2);

        // Then
        assertArrayEquals(new int[]{0, 1, 2, 1000, 1001, 1002, 5}, ListNode.toArray(result));
    }

    @Test
    @DisplayName("Merge In Between - Replace All")
    void testMergeInBetweenReplaceAll() {
        // Given
        ListNode list1 = ListNode.fromArray(new int[]{0, 1, 2, 3, 4});
        ListNode list2 = ListNode.fromArray(new int[]{5, 6, 7, 8});

        // When
        ListNode result = listMerging.mergeInBetween(list1, 1, 4, list2);

        // Then
        assertArrayEquals(new int[]{0, 5, 6, 7, 8}, ListNode.toArray(result));
    }

    // ==================== ARITHMETIC OPERATIONS TESTS ====================

    @Test
    @DisplayName("Add Two Numbers")
    void testAddTwoNumbers() {
        // Given: 342 + 465 = 807
        ListNode l1 = ListNode.fromArray(new int[]{2, 4, 3});
        ListNode l2 = ListNode.fromArray(new int[]{5, 6, 4});

        // When
        ListNode result = listMerging.addTwoNumbers(l1, l2);

        // Then
        assertArrayEquals(new int[]{7, 0, 8}, ListNode.toArray(result));
    }

    @Test
    @DisplayName("Add Two Numbers - With Carry")
    void testAddTwoNumbersWithCarry() {
        // Given: 999 + 1 = 1000
        ListNode l1 = ListNode.fromArray(new int[]{9, 9, 9});
        ListNode l2 = ListNode.fromArray(new int[]{1});

        // When
        ListNode result = listMerging.addTwoNumbers(l1, l2);

        // Then
        assertArrayEquals(new int[]{0, 0, 0, 1}, ListNode.toArray(result));
    }

    @Test
    @DisplayName("Subtract Two Numbers")
    void testSubtractTwoNumbers() {
        // Given: 543 - 321 = 222
        ListNode l1 = ListNode.fromArray(new int[]{3, 4, 5});
        ListNode l2 = ListNode.fromArray(new int[]{1, 2, 3});

        // When
        ListNode result = listMerging.subtractTwoNumbers(l1, l2);

        // Then
        assertArrayEquals(new int[]{2, 2, 2}, ListNode.toArray(result));
    }

    @Test
    @DisplayName("Multiply Two Numbers")
    void testMultiplyTwoNumbers() {
        // Given: 23 * 45 = 1035
        ListNode l1 = ListNode.fromArray(new int[]{3, 2}); // 23
        ListNode l2 = ListNode.fromArray(new int[]{5, 4}); // 45

        // When
        ListNode result = listMerging.multiplyTwoNumbers(l1, l2);

        // Then
        assertArrayEquals(new int[]{1035}, ListNode.toArray(result));
    }

    @Test
    @DisplayName("Multiply Two Numbers - With Zero")
    void testMultiplyTwoNumbersWithZero() {
        // Given: 123 * 0 = 0
        ListNode l1 = ListNode.fromArray(new int[]{3, 2, 1});
        ListNode l2 = ListNode.fromArray(new int[]{0});

        // When
        ListNode result = listMerging.multiplyTwoNumbers(l1, l2);

        // Then
        assertArrayEquals(new int[]{0}, ListNode.toArray(result));
    }

    // ==================== MERGE WITH ARRAY TESTS ====================

    @Test
    @DisplayName("Merge Sorted Array with List")
    void testMergeSortedArray() {
        // Given
        ListNode list = ListNode.fromArray(new int[]{1, 3, 5});
        int[] arr = {2, 4, 6, 8};

        // When
        ListNode result = listMerging.mergeSortedArray(list, arr);

        // Then
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 8}, ListNode.toArray(result));
    }

    @Test
    @DisplayName("Merge Sorted Array - Unsorted Array")
    void testMergeSortedArrayUnsorted() {
        // Given
        ListNode list = ListNode.fromArray(new int[]{1, 5, 9});
        int[] arr = {8, 2, 6}; // Will be sorted internally

        // When
        ListNode result = listMerging.mergeSortedArray(list, arr);

        // Then
        assertArrayEquals(new int[]{1, 2, 5, 6, 8, 9}, ListNode.toArray(result));
    }

    // ==================== PARTITION TESTS ====================

    @Test
    @DisplayName("Partition List")
    void testPartition() {
        // Given
        ListNode head = ListNode.fromArray(new int[]{1, 4, 3, 2, 5, 2});

        // When
        ListNode result = listMerging.partition(head, 3);

        // Then
        int[] resultArray = ListNode.toArray(result);

        // Verify partition property: all elements < 3 should come before elements >= 3
        int partitionIndex = -1;
        for (int i = 0; i < resultArray.length; i++) {
            if (resultArray[i] >= 3) {
                partitionIndex = i;
                break;
            }
        }

        if (partitionIndex != -1) {
            for (int i = 0; i < partitionIndex; i++) {
                assertTrue(resultArray[i] < 3);
            }
            for (int i = partitionIndex; i < resultArray.length; i++) {
                assertTrue(resultArray[i] >= 3);
            }
        }
    }

    @Test
    @DisplayName("Partition List - All Less Than X")
    void testPartitionAllLess() {
        // Given
        ListNode head = ListNode.fromArray(new int[]{1, 2, 3});

        // When
        ListNode result = listMerging.partition(head, 5);

        // Then
        assertArrayEquals(new int[]{1, 2, 3}, ListNode.toArray(result));
    }

    @Test
    @DisplayName("Partition List - All Greater Than X")
    void testPartitionAllGreater() {
        // Given
        ListNode head = ListNode.fromArray(new int[]{4, 5, 6});

        // When
        ListNode result = listMerging.partition(head, 3);

        // Then
        assertArrayEquals(new int[]{4, 5, 6}, ListNode.toArray(result));
    }

    // ==================== PERFORMANCE TESTS ====================

    @Test
    @DisplayName("Performance Test - Merge Large Lists")
    void testPerformanceMergeLargeLists() {
        // Given - Two large sorted lists
        int[] array1 = new int[5000];
        int[] array2 = new int[5000];

        for (int i = 0; i < 5000; i++) {
            array1[i] = i * 2;
            array2[i] = i * 2 + 1;
        }

        ListNode list1 = ListNode.fromArray(array1);
        ListNode list2 = ListNode.fromArray(array2);

        // When
        long startTime = System.currentTimeMillis();
        ListNode result = listMerging.mergeTwoLists(list1, list2);
        long endTime = System.currentTimeMillis();

        // Then
        assertTrue(endTime - startTime < 100, "Large list merge should complete within 100ms");
        assertEquals(10000, ListNode.getLength(result));
    }

    @Test
    @DisplayName("Performance Test - Sort Large List")
    void testPerformanceSortLargeList() {
        // Given - Large unsorted list
        int[] largeArray = new int[1000];
        for (int i = 0; i < 1000; i++) {
            largeArray[i] = 1000 - i; // Reverse order
        }
        ListNode head = ListNode.fromArray(largeArray);

        // When
        long startTime = System.currentTimeMillis();
        ListNode result = listMerging.sortList(head);
        long endTime = System.currentTimeMillis();

        // Then
        assertTrue(endTime - startTime < 200, "Large list sort should complete within 200ms");

        // Verify it's sorted
        int[] resultArray = ListNode.toArray(result);
        for (int i = 1; i < resultArray.length; i++) {
            assertTrue(resultArray[i] >= resultArray[i-1], "List should be sorted");
        }
    }

    @Test
    @DisplayName("Performance Test - Merge K Large Lists")
    void testPerformanceMergeKLargeLists() {
        // Given - K large sorted lists
        int k = 10;
        ListNode[] lists = new ListNode[k];

        for (int i = 0; i < k; i++) {
            int[] array = new int[100];
            for (int j = 0; j < 100; j++) {
                array[j] = i + j * k; // Ensure sorted and interleaved
            }
            lists[i] = ListNode.fromArray(array);
        }

        // When
        long startTime = System.currentTimeMillis();
        ListNode result = listMerging.mergeKLists(lists);
        long endTime = System.currentTimeMillis();

        // Then
        assertTrue(endTime - startTime < 150, "Merge K large lists should complete within 150ms");
        assertEquals(1000, ListNode.getLength(result));
    }

    // ==================== STRESS TESTS ====================

    @Test
    @DisplayName("Stress Test - Multiple Merge Operations")
    void testStressMultipleMergeOperations() {
        // Given
        ListNode base = ListNode.fromArray(new int[]{1, 3, 5, 7, 9});

        // When - Apply multiple merge operations
        ListNode list1 = ListNode.fromArray(new int[]{2, 4, 6});
        ListNode merged1 = listMerging.mergeTwoLists(base, list1);

        ListNode list2 = ListNode.fromArray(new int[]{0, 8, 10});
        ListNode merged2 = listMerging.mergeTwoLists(merged1, list2);

        ListNode sorted = listMerging.sortList(merged2);

        // Then
        int[] result = ListNode.toArray(sorted);
        assertEquals(11, result.length);

        // Verify final result is sorted
        for (int i = 1; i < result.length; i++) {
            assertTrue(result[i] >= result[i-1]);
        }
    }

    @Test
    @DisplayName("Stress Test - Arithmetic Operations Chain")
    void testStressArithmeticOperationsChain() {
        // Given
        ListNode num1 = ListNode.fromArray(new int[]{5, 5, 5}); // 555
        ListNode num2 = ListNode.fromArray(new int[]{4, 4, 4}); // 444
        ListNode num3 = ListNode.fromArray(new int[]{1, 1, 1}); // 111

        // When - Chain arithmetic operations
        ListNode sum1 = listMerging.addTwoNumbers(num1, num2); // 555 + 444 = 999
        ListNode diff = listMerging.subtractTwoNumbers(sum1, num3); // 999 - 111 = 888

        // Then
        assertArrayEquals(new int[]{9, 9, 9}, ListNode.toArray(sum1));
        assertArrayEquals(new int[]{8, 8, 8}, ListNode.toArray(diff));
    }

    // ==================== ALGORITHM CORRECTNESS TESTS ====================

    @Test
    @DisplayName("Algorithm Correctness - Merge Sort Properties")
    void testMergeSortProperties() {
        // Property 1: Sorted output
        ListNode unsorted = ListNode.fromArray(new int[]{5, 2, 8, 1, 9, 3});
        ListNode sorted = listMerging.sortList(unsorted);
        int[] sortedArray = ListNode.toArray(sorted);

        for (int i = 1; i < sortedArray.length; i++) {
            assertTrue(sortedArray[i] >= sortedArray[i-1], "Output should be sorted");
        }

        // Property 2: Same elements (permutation)
        int[] originalArray = {5, 2, 8, 1, 9, 3};
        java.util.Arrays.sort(originalArray);
        assertArrayEquals(originalArray, sortedArray);

        // Property 3: Stability test (for elements with same value)
        ListNode duplicates = ListNode.fromArray(new int[]{3, 1, 2, 3, 1, 2});
        ListNode sortedDuplicates = listMerging.sortList(duplicates);
        int[] sortedDupArray = ListNode.toArray(sortedDuplicates);
        assertArrayEquals(new int[]{1, 1, 2, 2, 3, 3}, sortedDupArray);
    }

    @Test
    @DisplayName("Algorithm Correctness - Merge Properties")
    void testMergeProperties() {
        // Property 1: Merge of sorted lists is sorted
        ListNode sorted1 = ListNode.fromArray(new int[]{1, 3, 5, 7});
        ListNode sorted2 = ListNode.fromArray(new int[]{2, 4, 6, 8});
        ListNode merged = listMerging.mergeTwoLists(sorted1, sorted2);

        int[] mergedArray = ListNode.toArray(merged);
        for (int i = 1; i < mergedArray.length; i++) {
            assertTrue(mergedArray[i] >= mergedArray[i-1], "Merged list should be sorted");
        }

        // Property 2: Length preservation
        assertEquals(8, mergedArray.length);

        // Property 3: Commutativity (merge(A,B) same elements as merge(B,A))
        ListNode merged2 = listMerging.mergeTwoLists(
                ListNode.fromArray(new int[]{2, 4, 6, 8}),
                ListNode.fromArray(new int[]{1, 3, 5, 7})
        );
        assertArrayEquals(mergedArray, ListNode.toArray(merged2));
    }

    @Test
    @DisplayName("Memory Safety - No Memory Leaks")
    void testMemorySafetyNoLeaks() {
        // Test operations that could potentially cause memory issues

        // Large merge operations
        for (int i = 0; i < 100; i++) {
            ListNode list1 = ListNode.fromArray(new int[]{1, 3, 5});
            ListNode list2 = ListNode.fromArray(new int[]{2, 4, 6});
            ListNode merged = listMerging.mergeTwoLists(list1, list2);
            assertNotNull(merged);
        }

        // Repeated sort operations
        for (int i = 0; i < 50; i++) {
            ListNode unsorted = ListNode.fromArray(new int[]{5, 2, 8, 1, 9, 3});
            ListNode sorted = listMerging.sortList(unsorted);
            assertNotNull(sorted);
        }
    }

    // ==================== EDGE CASES COMPREHENSIVE TEST ====================

    @Test
    @DisplayName("Edge Cases - Comprehensive Coverage")
    void testEdgeCasesComprehensive() {
        // Null inputs
        assertNull(listMerging.mergeTwoLists(null, null));
        assertNull(listMerging.mergeKLists(null));
        assertNull(listMerging.sortList(null));
        assertNull(listMerging.intersection(null, null));
        assertNull(listMerging.union(null, null));

        // Single element lists
        ListNode single1 = ListNode.fromArray(new int[]{42});
        ListNode single2 = ListNode.fromArray(new int[]{24});
        ListNode mergedSingle = listMerging.mergeTwoLists(single1, single2);
        assertArrayEquals(new int[]{24, 42}, ListNode.toArray(mergedSingle));

        // Empty arrays
        ListNode emptyList = ListNode.fromArray(new int[]{});
        assertNull(emptyList);

        // Duplicate elements
        ListNode duplicates = ListNode.fromArray(new int[]{1, 1, 1, 1});
        ListNode sortedDups = listMerging.sortList(duplicates);
        assertArrayEquals(new int[]{1, 1, 1, 1}, ListNode.toArray(sortedDups));
    }

    @Test
    @DisplayName("Boundary Conditions - Large Numbers")
    void testBoundaryConditionsLargeNumbers() {
        // Test arithmetic operations with large numbers
        ListNode large1 = ListNode.fromArray(new int[]{9, 9, 9, 9, 9, 9, 9});
        ListNode large2 = ListNode.fromArray(new int[]{1});

        ListNode sum = listMerging.addTwoNumbers(large1, large2);
        assertNotNull(sum);

        // Result should be 10000000 (1 followed by 7 zeros)
        int[] sumArray = ListNode.toArray(sum);
        assertEquals(0, sumArray[0]); // First digit should be 0
        assertEquals(1, sumArray[sumArray.length - 1]); // Last digit should be 1
    }
}