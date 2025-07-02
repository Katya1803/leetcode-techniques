package com.leetcode.datastructures.linkedlists;

import com.leetcode.utils.ListNode;
import java.util.*;

/**
 * Fast-Slow Pointer (Floyd's Cycle Detection) patterns
 * Also known as "Tortoise and Hare" algorithm
 *
 * Common use cases:
 * 1. Cycle detection in linked lists
 * 2. Finding middle of linked list
 * 3. Finding kth node from end
 * 4. Intersection of two linked lists
 * 5. Palindrome checking
 */
public class FastSlowPointer {

    /**
     * Detect cycle in linked list - Floyd's Algorithm
     * Time: O(n), Space: O(1)
     */
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) return false;

        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                return true;
            }
        }

        return false;
    }

    /**
     * Find start of cycle in linked list
     * Time: O(n), Space: O(1)
     */
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) return null;

        ListNode slow = head;
        ListNode fast = head;

        // Phase 1: Detect if cycle exists
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) break;
        }

        // No cycle found
        if (fast == null || fast.next == null) return null;

        // Phase 2: Find start of cycle
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow;
    }

    /**
     * Find length of cycle in linked list
     * Time: O(n), Space: O(1)
     */
    public int cycleLength(ListNode head) {
        if (head == null || head.next == null) return 0;

        ListNode slow = head;
        ListNode fast = head;

        // Detect cycle
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                // Count nodes in cycle
                int length = 1;
                ListNode current = slow.next;
                while (current != slow) {
                    current = current.next;
                    length++;
                }
                return length;
            }
        }

        return 0; // No cycle
    }

    /**
     * Find middle of linked list
     * Time: O(n), Space: O(1)
     */
    public ListNode findMiddle(ListNode head) {
        if (head == null) return null;

        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    /**
     * Find middle of linked list - return first middle for even length
     * Time: O(n), Space: O(1)
     */
    public ListNode findMiddleFirst(ListNode head) {
        if (head == null) return null;

        ListNode slow = head;
        ListNode fast = head.next;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    /**
     * Remove middle node of linked list
     * Time: O(n), Space: O(1)
     */
    public ListNode deleteMiddle(ListNode head) {
        if (head == null || head.next == null) return null;

        ListNode slow = head;
        ListNode fast = head;
        ListNode prev = null;

        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        // Remove middle node
        if (prev != null) {
            prev.next = slow.next;
        }

        return head;
    }

    /**
     * Find nth node from end using fast-slow technique
     * Time: O(n), Space: O(1)
     */
    public ListNode findNthFromEnd(ListNode head, int n) {
        ListNode fast = head;
        ListNode slow = head;

        // Move fast pointer n steps ahead
        for (int i = 0; i < n; i++) {
            if (fast == null) return null; // n is larger than list length
            fast = fast.next;
        }

        // Move both pointers until fast reaches end
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow;
    }

    /**
     * Remove nth node from end
     * Time: O(n), Space: O(1)
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode fast = dummy;
        ListNode slow = dummy;

        // Move fast pointer n+1 steps ahead
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }

        // Move both pointers until fast reaches end
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }

        // Remove the nth node
        slow.next = slow.next.next;

        return dummy.next;
    }

    /**
     * Intersection of Two Linked Lists
     * Time: O(m + n), Space: O(1)
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;

        ListNode pA = headA;
        ListNode pB = headB;

        // When pA reaches end, redirect to headB
        // When pB reaches end, redirect to headA
        // They will meet at intersection or both reach null
        while (pA != pB) {
            pA = (pA == null) ? headB : pA.next;
            pB = (pB == null) ? headA : pB.next;
        }

        return pA;
    }

    /**
     * Check if linked list is palindrome using fast-slow
     * Time: O(n), Space: O(1)
     */
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) return true;

        // Find middle
        ListNode slow = head;
        ListNode fast = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Reverse second half
        ListNode secondHalf = reverseList(slow.next);

        // Compare first and second half
        ListNode p1 = head;
        ListNode p2 = secondHalf;

        while (p2 != null) {
            if (p1.val != p2.val) return false;
            p1 = p1.next;
            p2 = p2.next;
        }

        return true;
    }

    /**
     * Reorder List using fast-slow technique
     * Time: O(n), Space: O(1)
     */
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) return;

        // Find middle
        ListNode slow = head;
        ListNode fast = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Split and reverse second half
        ListNode secondHalf = reverseList(slow.next);
        slow.next = null;

        // Merge two halves alternately
        ListNode first = head;
        ListNode second = secondHalf;

        while (second != null) {
            ListNode temp1 = first.next;
            ListNode temp2 = second.next;

            first.next = second;
            second.next = temp1;

            first = temp1;
            second = temp2;
        }
    }

    /**
     * Split Linked List in Parts using fast-slow
     * Time: O(n), Space: O(k)
     */
    public ListNode[] splitListToParts(ListNode head, int k) {
        // Count total nodes
        int length = 0;
        ListNode current = head;
        while (current != null) {
            length++;
            current = current.next;
        }

        int partSize = length / k;
        int extraNodes = length % k;

        ListNode[] result = new ListNode[k];
        current = head;

        for (int i = 0; i < k; i++) {
            result[i] = current;
            int currentPartSize = partSize + (i < extraNodes ? 1 : 0);

            // Move to end of current part
            for (int j = 0; j < currentPartSize - 1 && current != null; j++) {
                current = current.next;
            }

            // Split the list
            if (current != null) {
                ListNode next = current.next;
                current.next = null;
                current = next;
            }
        }

        return result;
    }

    /**
     * Odd Even Linked List using fast-slow concept
     * Time: O(n), Space: O(1)
     */
    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode odd = head;
        ListNode even = head.next;
        ListNode evenHead = even;

        while (even != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }

        odd.next = evenHead;
        return head;
    }

    /**
     * Rotate List using fast-slow to find break point
     * Time: O(n), Space: O(1)
     */
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) return head;

        // Find length and connect tail to head
        int length = 1;
        ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
            length++;
        }
        tail.next = head;

        // Find new tail
        k = k % length;
        int stepsToNewTail = length - k;
        ListNode newTail = head;

        for (int i = 1; i < stepsToNewTail; i++) {
            newTail = newTail.next;
        }

        ListNode newHead = newTail.next;
        newTail.next = null;

        return newHead;
    }

    /**
     * Happy Number using cycle detection
     * Time: O(log n), Space: O(1)
     */
    public boolean isHappy(int n) {
        int slow = n;
        int fast = n;

        do {
            slow = getNext(slow);
            fast = getNext(getNext(fast));
        } while (slow != fast);

        return slow == 1;
    }

    private int getNext(int n) {
        int sum = 0;
        while (n > 0) {
            int digit = n % 10;
            sum += digit * digit;
            n /= 10;
        }
        return sum;
    }

    /**
     * Find Duplicate Number using cycle detection
     * Time: O(n), Space: O(1)
     */
    public int findDuplicate(int[] nums) {
        // Use array indices as "next" pointers
        int slow = nums[0];
        int fast = nums[0];

        // Phase 1: Find intersection point in cycle
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);

        // Phase 2: Find entrance to cycle
        slow = nums[0];
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }

        return slow;
    }

    /**
     * Linked List Cycle II with additional info
     * Returns an array: [hasCycle, cycleStart, cycleLength]
     */
    public Object[] cycleInfo(ListNode head) {
        if (head == null || head.next == null) {
            return new Object[]{false, null, 0};
        }

        ListNode slow = head;
        ListNode fast = head;

        // Detect cycle
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                // Find cycle start
                ListNode start = head;
                while (start != slow) {
                    start = start.next;
                    slow = slow.next;
                }

                // Find cycle length
                int length = 1;
                ListNode temp = slow.next;
                while (temp != slow) {
                    temp = temp.next;
                    length++;
                }

                return new Object[]{true, start, length};
            }
        }

        return new Object[]{false, null, 0};
    }

    // Helper methods
    private ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;

        while (current != null) {
            ListNode next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }

        return prev;
    }

    /**
     * Helper method to create cycle at given position
     */
    public static ListNode createListWithCycle(int[] values, int cyclePos) {
        if (values.length == 0) return null;

        ListNode head = ListNode.fromArray(values);

        if (cyclePos < 0 || cyclePos >= values.length) return head;

        // Find tail and cycle start node
        ListNode tail = head;
        ListNode cycleStart = head;

        // Move to cycle start position
        for (int i = 0; i < cyclePos; i++) {
            cycleStart = cycleStart.next;
        }

        // Move to tail
        while (tail.next != null) {
            tail = tail.next;
        }

        // Create cycle
        tail.next = cycleStart;

        return head;
    }

    /**
     * Helper method to convert list to array (breaks at cycle)
     */
    public static int[] listToArray(ListNode head, int maxLength) {
        if (head == null) return new int[0];

        List<Integer> result = new ArrayList<>();
        Set<ListNode> visited = new HashSet<>();
        int count = 0;

        while (head != null && count < maxLength && !visited.contains(head)) {
            visited.add(head);
            result.add(head.val);
            head = head.next;
            count++;
        }

        return result.stream().mapToInt(Integer::intValue).toArray();
    }
}
