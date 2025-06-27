package com.leetcode.utils;

/**
 * Definition for singly-linked list node used in LeetCode problems
 */
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode() {}

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    /**
     * Create a linked list from array
     * @param values array of integers
     * @return head of the linked list
     */
    public static ListNode fromArray(int[] values) {
        if (values == null || values.length == 0) {
            return null;
        }

        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        for (int val : values) {
            current.next = new ListNode(val);
            current = current.next;
        }

        return dummy.next;
    }

    /**
     * Convert linked list to array
     * @param head head of the linked list
     * @return array representation
     */
    public static int[] toArray(ListNode head) {
        java.util.List<Integer> result = new java.util.ArrayList<>();
        ListNode current = head;

        while (current != null) {
            result.add(current.val);
            current = current.next;
        }

        return result.stream().mapToInt(i -> i).toArray();
    }

    /**
     * Print linked list for debugging
     * @param head head of the linked list
     */
    public static void printList(ListNode head) {
        ListNode current = head;
        StringBuilder sb = new StringBuilder();

        while (current != null) {
            sb.append(current.val);
            if (current.next != null) {
                sb.append(" -> ");
            }
            current = current.next;
        }

        System.out.println(sb.toString());
    }

    /**
     * Get length of linked list
     * @param head head of the linked list
     * @return length of the list
     */
    public static int getLength(ListNode head) {
        int length = 0;
        ListNode current = head;

        while (current != null) {
            length++;
            current = current.next;
        }

        return length;
    }

    @Override
    public String toString() {
        return "ListNode{val=" + val + "}";
    }
}