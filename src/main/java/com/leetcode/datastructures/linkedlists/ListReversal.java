package com.leetcode.datastructures.linkedlists;

import com.leetcode.utils.ListNode;

/**
 * List Reversal patterns and techniques
 * Common use cases:
 * 1. Reverse entire linked list
 * 2. Reverse sublist/range
 * 3. Reverse in groups (K-Group reversal)
 * 4. Reverse alternate nodes
 * 5. Palindrome checking using reversal
 */
public class ListReversal {

    /**
     * Reverse entire linked list - Iterative approach
     * Time: O(n), Space: O(1)
     */
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;

        while (current != null) {
            ListNode nextTemp = current.next;
            current.next = prev;
            prev = current;
            current = nextTemp;
        }

        return prev;
    }

    /**
     * Reverse entire linked list - Recursive approach
     * Time: O(n), Space: O(n) due to recursion stack
     */
    public ListNode reverseListRecursive(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode newHead = reverseListRecursive(head.next);
        head.next.next = head;
        head.next = null;

        return newHead;
    }

    /**
     * Reverse Linked List II - Reverse between positions m and n
     * Time: O(n), Space: O(1)
     */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || left == right) return head;

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prevStart = dummy;

        // Move to position before left
        for (int i = 1; i < left; i++) {
            prevStart = prevStart.next;
        }

        ListNode start = prevStart.next;
        ListNode then = start.next;

        // Reverse nodes between left and right
        for (int i = 0; i < right - left; i++) {
            start.next = then.next;
            then.next = prevStart.next;
            prevStart.next = then;
            then = start.next;
        }

        return dummy.next;
    }

    /**
     * Reverse Nodes in k-Group
     * Time: O(n), Space: O(1)
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k == 1) return head;

        // Check if we have k nodes left
        ListNode current = head;
        int count = 0;
        while (current != null && count < k) {
            current = current.next;
            count++;
        }

        if (count == k) {
            // Reverse next k nodes first
            current = reverseKGroup(current, k);

            // Reverse current k nodes
            while (count > 0) {
                ListNode nextTemp = head.next;
                head.next = current;
                current = head;
                head = nextTemp;
                count--;
            }
            head = current;
        }

        return head;
    }

    /**
     * Reverse Nodes in k-Group - Iterative approach
     * Time: O(n), Space: O(1)
     */
    public ListNode reverseKGroupIterative(ListNode head, int k) {
        if (head == null || k == 1) return head;

        // Count total nodes
        int length = getLength(head);

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;

        while (length >= k) {
            ListNode current = prev.next;
            ListNode next = current.next;

            // Reverse k nodes
            for (int i = 1; i < k; i++) {
                current.next = next.next;
                next.next = prev.next;
                prev.next = next;
                next = current.next;
            }

            prev = current;
            length -= k;
        }

        return dummy.next;
    }

    /**
     * Swap Nodes in Pairs
     * Time: O(n), Space: O(1)
     */
    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;

        while (prev.next != null && prev.next.next != null) {
            ListNode first = prev.next;
            ListNode second = prev.next.next;

            // Swap
            prev.next = second;
            first.next = second.next;
            second.next = first;

            // Move prev pointer
            prev = first;
        }

        return dummy.next;
    }

    /**
     * Reverse Alternate Nodes
     * Time: O(n), Space: O(1)
     */
    public ListNode reverseAlternateNodes(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode current = head;
        ListNode prev = null;

        while (current != null && current.next != null) {
            ListNode nextPair = current.next.next;

            // Reverse current pair
            current.next.next = current;
            if (prev != null) {
                prev.next = current.next;
            } else {
                head = current.next;
            }
            current.next = nextPair;

            prev = current;
            current = nextPair;
        }

        return head;
    }

    /**
     * Palindrome Linked List - Using reversal
     * Time: O(n), Space: O(1)
     */
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) return true;

        // Find middle of the list
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Reverse second half
        ListNode secondHalf = reverseList(slow);
        ListNode firstHalf = head;

        // Compare both halves
        while (secondHalf != null) {
            if (firstHalf.val != secondHalf.val) {
                return false;
            }
            firstHalf = firstHalf.next;
            secondHalf = secondHalf.next;
        }

        return true;
    }

    /**
     * Add Two Numbers - Reverse representation
     * Time: O(max(m,n)), Space: O(max(m,n))
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
     * Add Two Numbers II - Most significant digit first
     * Time: O(max(m,n)), Space: O(max(m,n))
     */
    public ListNode addTwoNumbersII(ListNode l1, ListNode l2) {
        // Reverse both lists
        ListNode rev1 = reverseList(l1);
        ListNode rev2 = reverseList(l2);

        // Add reversed lists
        ListNode result = addTwoNumbers(rev1, rev2);

        // Reverse result back
        return reverseList(result);
    }

    /**
     * Reverse Every K Alternate Nodes
     * Time: O(n), Space: O(1)
     */
    public ListNode reverseKAlternate(ListNode head, int k) {
        if (head == null || k <= 1) return head;

        ListNode current = head;
        ListNode prev = null;
        ListNode next = null;
        int count = 0;

        // Reverse first k nodes
        while (current != null && count < k) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
            count++;
        }

        if (head != null) {
            head.next = current;
        }

        // Skip next k nodes
        count = 0;
        while (current != null && count < k) {
            current = current.next;
            count++;
        }

        // Recursively process remaining list
        if (current != null) {
            head.next = reverseKAlternate(current, k);
        }

        return prev;
    }

    /**
     * Reverse Sublist Between Two Nodes
     * Time: O(n), Space: O(1)
     */
    public ListNode reverseSublist(ListNode head, ListNode start, ListNode end) {
        if (head == null || start == null || end == null || start == end) {
            return head;
        }

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prevStart = dummy;

        // Find node before start
        while (prevStart.next != start && prevStart.next != null) {
            prevStart = prevStart.next;
        }

        if (prevStart.next == null) return head; // start not found

        ListNode current = start;
        ListNode prev = end.next;

        // Reverse from start to end
        while (current != end.next) {
            ListNode nextTemp = current.next;
            current.next = prev;
            prev = current;
            current = nextTemp;
        }

        prevStart.next = prev;
        return dummy.next;
    }

    /**
     * Remove Nth Node From End using reversal technique
     * Time: O(n), Space: O(1)
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // Reverse the list
        ListNode reversed = reverseList(head);

        // Remove nth node from beginning
        if (n == 1) {
            reversed = reversed.next;
        } else {
            ListNode current = reversed;
            for (int i = 1; i < n - 1 && current != null; i++) {
                current = current.next;
            }
            if (current != null && current.next != null) {
                current.next = current.next.next;
            }
        }

        // Reverse back
        return reverseList(reversed);
    }

    /**
     * Rotate List using reversal
     * Time: O(n), Space: O(1)
     */
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) return head;

        int length = getLength(head);
        k = k % length;

        if (k == 0) return head;

        // Find the new tail (length - k - 1)th node
        ListNode newTail = head;
        for (int i = 0; i < length - k - 1; i++) {
            newTail = newTail.next;
        }

        ListNode newHead = newTail.next;
        newTail.next = null;

        // Connect old tail to old head
        ListNode current = newHead;
        while (current.next != null) {
            current = current.next;
        }
        current.next = head;

        return newHead;
    }

    /**
     * Plus One Linked List using reversal
     * Time: O(n), Space: O(1)
     */
    public ListNode plusOne(ListNode head) {
        // Reverse the list
        ListNode reversed = reverseList(head);

        // Add one
        ListNode current = reversed;
        int carry = 1;

        while (current != null && carry > 0) {
            int sum = current.val + carry;
            current.val = sum % 10;
            carry = sum / 10;

            if (carry > 0 && current.next == null) {
                current.next = new ListNode(carry);
                carry = 0;
            }

            current = current.next;
        }

        // Reverse back
        return reverseList(reversed);
    }

    // Helper methods
    private int getLength(ListNode head) {
        return ListNode.getLength(head);
    }
}
