package com.leetcode.datastructures.linkedlists;

import com.leetcode.utils.ListNode;
import java.util.*;

/**
 * List Merging patterns and techniques
 * Common use cases:
 * 1. Merge two sorted lists
 * 2. Merge k sorted lists
 * 3. Merge alternate nodes
 * 4. Sort and merge operations
 * 5. Intersection and union operations
 */
public class ListMerging {

    /**
     * Merge Two Sorted Lists
     * Time: O(m + n), Space: O(1)
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                current.next = list1;
                list1 = list1.next;
            } else {
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }

        // Append remaining nodes
        current.next = (list1 != null) ? list1 : list2;

        return dummy.next;
    }

    /**
     * Merge Two Sorted Lists - Recursive approach
     * Time: O(m + n), Space: O(m + n) due to recursion
     */
    public ListNode mergeTwoListsRecursive(ListNode list1, ListNode list2) {
        if (list1 == null) return list2;
        if (list2 == null) return list1;

        if (list1.val <= list2.val) {
            list1.next = mergeTwoListsRecursive(list1.next, list2);
            return list1;
        } else {
            list2.next = mergeTwoListsRecursive(list1, list2.next);
            return list2;
        }
    }

    /**
     * Merge k Sorted Lists - Divide and Conquer
     * Time: O(n log k), Space: O(log k)
     */
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        return mergeKListsHelper(lists, 0, lists.length - 1);
    }

    private ListNode mergeKListsHelper(ListNode[] lists, int start, int end) {
        if (start == end) return lists[start];
        if (start > end) return null;

        int mid = start + (end - start) / 2;
        ListNode left = mergeKListsHelper(lists, start, mid);
        ListNode right = mergeKListsHelper(lists, mid + 1, end);

        return mergeTwoLists(left, right);
    }

    /**
     * Merge k Sorted Lists - Priority Queue approach
     * Time: O(n log k), Space: O(k)
     */
    public ListNode mergeKListsPQ(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;

        PriorityQueue<ListNode> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(a.val, b.val)
        );

        // Add first node of each list to priority queue
        for (ListNode list : lists) {
            if (list != null) {
                pq.offer(list);
            }
        }

        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        while (!pq.isEmpty()) {
            ListNode node = pq.poll();
            current.next = node;
            current = current.next;

            if (node.next != null) {
                pq.offer(node.next);
            }
        }

        return dummy.next;
    }

    /**
     * Merge k Sorted Lists - Sequential merging
     * Time: O(kn), Space: O(1)
     */
    public ListNode mergeKListsSequential(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;

        ListNode result = lists[0];
        for (int i = 1; i < lists.length; i++) {
            result = mergeTwoLists(result, lists[i]);
        }

        return result;
    }

    /**
     * Merge Sort for Linked List
     * Time: O(n log n), Space: O(log n)
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;

        // Find middle and split
        ListNode middle = findMiddle(head);
        ListNode secondHalf = middle.next;
        middle.next = null;

        // Recursively sort both halves
        ListNode left = sortList(head);
        ListNode right = sortList(secondHalf);

        // Merge sorted halves
        return mergeTwoLists(left, right);
    }

    /**
     * Merge Two Sorted Lists in Reverse Order
     * Time: O(m + n), Space: O(1)
     */
    public ListNode mergeTwoListsReverse(ListNode list1, ListNode list2) {
        ListNode result = null;

        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                ListNode temp = list1.next;
                list1.next = result;
                result = list1;
                list1 = temp;
            } else {
                ListNode temp = list2.next;
                list2.next = result;
                result = list2;
                list2 = temp;
            }
        }

        // Add remaining nodes
        while (list1 != null) {
            ListNode temp = list1.next;
            list1.next = result;
            result = list1;
            list1 = temp;
        }

        while (list2 != null) {
            ListNode temp = list2.next;
            list2.next = result;
            result = list2;
            list2 = temp;
        }

        return result;
    }

    /**
     * Merge Nodes in Between Zeros
     * Time: O(n), Space: O(1)
     */
    public ListNode mergeNodes(ListNode head) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        ListNode temp = head.next; // Skip first zero
        int sum = 0;

        while (temp != null) {
            if (temp.val == 0) {
                current.next = new ListNode(sum);
                current = current.next;
                sum = 0;
            } else {
                sum += temp.val;
            }
            temp = temp.next;
        }

        return dummy.next;
    }

    /**
     * Merge Alternate Nodes
     * Time: O(min(m, n)), Space: O(1)
     */
    public ListNode mergeAlternate(ListNode list1, ListNode list2) {
        if (list1 == null) return list2;
        if (list2 == null) return list1;

        ListNode current1 = list1;
        ListNode current2 = list2;

        while (current1 != null && current2 != null) {
            ListNode next1 = current1.next;
            ListNode next2 = current2.next;

            current1.next = current2;
            current2.next = next1;

            current1 = next1;
            current2 = next2;
        }

        return list1;
    }

    /**
     * Intersection of Two Sorted Lists
     * Time: O(m + n), Space: O(1)
     */
    public ListNode intersection(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        while (list1 != null && list2 != null) {
            if (list1.val == list2.val) {
                current.next = new ListNode(list1.val);
                current = current.next;
                list1 = list1.next;
                list2 = list2.next;
            } else if (list1.val < list2.val) {
                list1 = list1.next;
            } else {
                list2 = list2.next;
            }
        }

        return dummy.next;
    }

    /**
     * Union of Two Sorted Lists (without duplicates)
     * Time: O(m + n), Space: O(1)
     */
    public ListNode union(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        while (list1 != null && list2 != null) {
            if (list1.val == list2.val) {
                current.next = new ListNode(list1.val);
                current = current.next;
                list1 = list1.next;
                list2 = list2.next;
            } else if (list1.val < list2.val) {
                current.next = new ListNode(list1.val);
                current = current.next;
                list1 = list1.next;
            } else {
                current.next = new ListNode(list2.val);
                current = current.next;
                list2 = list2.next;
            }
        }

        // Add remaining nodes
        while (list1 != null) {
            current.next = new ListNode(list1.val);
            current = current.next;
            list1 = list1.next;
        }

        while (list2 != null) {
            current.next = new ListNode(list2.val);
            current = current.next;
            list2 = list2.next;
        }

        return dummy.next;
    }

    /**
     * Merge In Between Lists
     * Time: O(n), Space: O(1)
     */
    public ListNode mergeInBetween(ListNode list1, int a, int b, ListNode list2) {
        ListNode current = list1;

        // Find node at position a-1
        for (int i = 0; i < a - 1; i++) {
            current = current.next;
        }

        ListNode nodeBeforeA = current;

        // Find node at position b+1
        for (int i = a - 1; i <= b; i++) {
            current = current.next;
        }

        ListNode nodeAfterB = current;

        // Connect list1[0:a-1] -> list2 -> list1[b+1:]
        nodeBeforeA.next = list2;

        // Find tail of list2
        while (list2.next != null) {
            list2 = list2.next;
        }

        list2.next = nodeAfterB;

        return list1;
    }

    /**
     * Add Two Numbers represented as linked lists
     * Time: O(max(m, n)), Space: O(max(m, n))
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        int carry = 0;

        while (l1 != null || l2 != null || carry != 0) {
            int sum = carry;

            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }

            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }

            carry = sum / 10;
            current.next = new ListNode(sum % 10);
            current = current.next;
        }

        return dummy.next;
    }

    /**
     * Subtract Two Numbers represented as linked lists
     * Assumes first number >= second number
     * Time: O(max(m, n)), Space: O(max(m, n))
     */
    public ListNode subtractTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        int borrow = 0;

        while (l1 != null || l2 != null) {
            int val1 = (l1 != null) ? l1.val : 0;
            int val2 = (l2 != null) ? l2.val : 0;

            int diff = val1 - val2 - borrow;

            if (diff < 0) {
                diff += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }

            current.next = new ListNode(diff);
            current = current.next;

            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }

        // Remove leading zeros
        return removeLeadingZeros(dummy.next);
    }

    /**
     * Multiply Two Numbers represented as linked lists
     * Time: O(m * n), Space: O(m + n)
     */
    public ListNode multiplyTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) return new ListNode(0);

        // Convert to arrays for easier manipulation
        List<Integer> num1 = listToArrayReverse(l1);
        List<Integer> num2 = listToArrayReverse(l2);

        int[] result = new int[num1.size() + num2.size()];

        // Multiply each digit
        for (int i = 0; i < num1.size(); i++) {
            for (int j = 0; j < num2.size(); j++) {
                result[i + j] += num1.get(i) * num2.get(j);
                result[i + j + 1] += result[i + j] / 10;
                result[i + j] %= 10;
            }
        }

        // Convert back to linked list
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        boolean leadingZero = true;

        for (int i = result.length - 1; i >= 0; i--) {
            if (result[i] != 0 || !leadingZero) {
                current.next = new ListNode(result[i]);
                current = current.next;
                leadingZero = false;
            }
        }

        return dummy.next != null ? dummy.next : new ListNode(0);
    }

    /**
     * Merge Sorted Array into Linked List
     * Time: O(m + n), Space: O(1)
     */
    public ListNode mergeSortedArray(ListNode list, int[] arr) {
        Arrays.sort(arr); // Ensure array is sorted

        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        int i = 0;

        while (list != null && i < arr.length) {
            if (list.val <= arr[i]) {
                current.next = list;
                list = list.next;
            } else {
                current.next = new ListNode(arr[i]);
                i++;
            }
            current = current.next;
        }

        // Add remaining elements
        while (list != null) {
            current.next = list;
            list = list.next;
            current = current.next;
        }

        while (i < arr.length) {
            current.next = new ListNode(arr[i]);
            current = current.next;
            i++;
        }

        return dummy.next;
    }

    /**
     * Partition List and merge parts
     * Time: O(n), Space: O(1)
     */
    public ListNode partition(ListNode head, int x) {
        ListNode beforeHead = new ListNode(0);
        ListNode before = beforeHead;
        ListNode afterHead = new ListNode(0);
        ListNode after = afterHead;

        while (head != null) {
            if (head.val < x) {
                before.next = head;
                before = before.next;
            } else {
                after.next = head;
                after = after.next;
            }
            head = head.next;
        }

        after.next = null;
        before.next = afterHead.next;

        return beforeHead.next;
    }

    // Helper methods
    private ListNode findMiddle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head.next;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    private ListNode removeLeadingZeros(ListNode head) {
        while (head != null && head.next != null && head.val == 0) {
            head = head.next;
        }
        return head;
    }

    private List<Integer> listToArrayReverse(ListNode head) {
        List<Integer> result = new ArrayList<>();
        while (head != null) {
            result.add(head.val);
            head = head.next;
        }
        return result;
    }
}
