# Linked Lists - CS Fundamentals

## Core Concepts and Structure

### Linked List Definition
**Definition:** A linear data structure where elements (nodes) are stored in sequence, but not in contiguous memory locations. Each node contains data and a reference (pointer) to the next node.

**Basic node structure:**
```java
class ListNode {
    int val;
    ListNode next;
    
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
```

**Memory representation:**
```
Array in memory (contiguous):
[data1][data2][data3][data4] → cache-friendly, fixed size

Linked List in memory (scattered):
[data1|ptr] → [data3|ptr] → [data2|ptr] → [data4|null]
     ↓             ↓             ↓             ↓
   addr:100      addr:200      addr:150      addr:300
```

### Fundamental Properties
1. **Dynamic size:** Can grow/shrink during runtime
2. **Non-contiguous memory:** Nodes can be anywhere in memory
3. **Sequential access:** Must traverse from head to reach any element
4. **Pointer-based:** Each node contains reference to next node
5. **No random access:** Cannot directly access element at index i

## Linked List Variants

### 1. Singly Linked List
**Structure:** Each node points to the next node
```
head → [1|•] → [2|•] → [3|•] → [4|null]
```

**Operations complexity:**
- **Insert at head:** O(1)
- **Insert at tail:** O(n) without tail pointer, O(1) with tail pointer
- **Insert at position:** O(n)
- **Delete from head:** O(1)
- **Delete from tail:** O(n)
- **Search:** O(n)

### 2. Doubly Linked List
**Structure:** Each node has pointers to both next and previous nodes
```java
class DoublyListNode {
    int val;
    DoublyListNode next;
    DoublyListNode prev;
}
```

**Memory representation:**
```
null ← [1|•|•] ⇄ [2|•|•] ⇄ [3|•|•] ⇄ [4|•|•] → null
```

**Advantages:**
- **Bidirectional traversal**
- **O(1) deletion** given node reference
- **Easier insertion** before given node

**Disadvantages:**
- **Extra memory** for prev pointer
- **More complex** pointer management

### 3. Circular Linked List
**Structure:** Last node points back to first node
```
    ┌─────────────────────────┐
    ↓                         │
[1|•] → [2|•] → [3|•] → [4|•]─┘
```

**Key difference:** No null termination, must track starting point to avoid infinite loops

**Applications:**
- Round-robin scheduling
- Music playlist (repeat mode)
- Game turn management

## Pointer Manipulation Fundamentals

### Basic Operations

#### 1. Insertion at Head
```java
ListNode insertAtHead(ListNode head, int val) {
    ListNode newNode = new ListNode(val);
    newNode.next = head;
    return newNode;  // New head
}
```

**Visual:**
```
Before: head → [1] → [2] → [3]
After:  head → [0] → [1] → [2] → [3]
```

#### 2. Insertion at Position
```java
ListNode insertAtPosition(ListNode head, int pos, int val) {
    if (pos == 0) return insertAtHead(head, val);
    
    ListNode current = head;
    for (int i = 0; i < pos - 1 && current != null; i++) {
        current = current.next;
    }
    
    if (current == null) return head;  // Position out of bounds
    
    ListNode newNode = new ListNode(val);
    newNode.next = current.next;
    current.next = newNode;
    return head;
}
```

**Critical insight:** Always update pointers in correct order to avoid losing references

#### 3. Deletion by Value
```java
ListNode deleteByValue(ListNode head, int val) {
    // Handle head deletion
    if (head != null && head.val == val) {
        return head.next;
    }
    
    ListNode current = head;
    while (current != null && current.next != null) {
        if (current.next.val == val) {
            current.next = current.next.next;  // Skip the node to delete
            break;
        }
        current = current.next;
    }
    
    return head;
}
```

### Pointer Safety Rules
1. **Always check for null** before dereferencing
2. **Update pointers in correct order** to maintain connectivity
3. **Save references** before losing them
4. **Handle edge cases:** empty list, single node, head/tail operations

## Classic Algorithms

### 1. List Reversal

#### Iterative Approach
```java
ListNode reverseIterative(ListNode head) {
    ListNode prev = null;
    ListNode current = head;
    
    while (current != null) {
        ListNode nextTemp = current.next;  // Save next
        current.next = prev;               // Reverse link
        prev = current;                    // Move prev forward
        current = nextTemp;                // Move current forward
    }
    
    return prev;  // New head
}
```

**Step-by-step visualization:**
```
Initial: null ← prev, current → [1] → [2] → [3] → null

Step 1:  null ← [1] ← prev, current → [2] → [3] → null
Step 2:  null ← [1] ← [2] ← prev, current → [3] → null  
Step 3:  null ← [1] ← [2] ← [3] ← prev, current → null
```

#### Recursive Approach
```java
ListNode reverseRecursive(ListNode head) {
    // Base case
    if (head == null || head.next == null) {
        return head;
    }
    
    // Reverse the rest of the list
    ListNode newHead = reverseRecursive(head.next);
    
    // Reverse current connection
    head.next.next = head;
    head.next = null;
    
    return newHead;
}
```

**Recursion analysis:**
- **Time:** O(n) - visit each node once
- **Space:** O(n) - call stack depth
- **Base case:** Single node or empty list
- **Recursive case:** Reverse subproblem + fix current connection

### 2. Cycle Detection (Floyd's Algorithm)

#### The Two-Pointer Technique
```java
boolean hasCycle(ListNode head) {
    if (head == null || head.next == null) return false;
    
    ListNode slow = head;
    ListNode fast = head;
    
    while (fast != null && fast.next != null) {
        slow = slow.next;        // Move 1 step
        fast = fast.next.next;   // Move 2 steps
        
        if (slow == fast) {
            return true;  // Cycle detected
        }
    }
    
    return false;  // No cycle
}
```

**Mathematical proof:**
- **Distance between pointers decreases by 1** each iteration
- **If cycle exists, pointers must eventually meet**
- **Meeting point exists within cycle length iterations**

#### Finding Cycle Start
```java
ListNode detectCycleStart(ListNode head) {
    ListNode slow = head, fast = head;
    
    // Phase 1: Detect if cycle exists
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast) break;
    }
    
    if (fast == null || fast.next == null) return null;  // No cycle
    
    // Phase 2: Find cycle start
    slow = head;
    while (slow != fast) {
        slow = slow.next;
        fast = fast.next;  // Both move 1 step now
    }
    
    return slow;  // Cycle start node
}
```

**Mathematical insight:**
```
Let: L = distance from head to cycle start
     C = cycle length
     k = distance from cycle start to meeting point

When they meet:
- Slow traveled: L + k
- Fast traveled: L + k + nC (for some integer n)
- Fast traveled 2× slow's distance: 2(L + k) = L + k + nC
- Simplifying: L + k = nC, so L = nC - k

This means: distance from head to cycle start = 
           n × cycle_length - distance_from_start_to_meeting_point
```

### 3. Merging Sorted Lists
```java
ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0);  // Simplifies edge cases
    ListNode current = dummy;
    
    while (l1 != null && l2 != null) {
        if (l1.val <= l2.val) {
            current.next = l1;
            l1 = l1.next;
        } else {
            current.next = l2;
            l2 = l2.next;
        }
        current = current.next;
    }
    
    // Attach remaining nodes
    current.next = (l1 != null) ? l1 : l2;
    
    return dummy.next;  // Skip dummy node
}
```

**Dummy node technique:**
- **Simplifies code** by eliminating special cases for first node
- **Common pattern** in linked list problems
- **Always remember** to return `dummy.next`

## Advanced Techniques

### 1. Fast-Slow Pointer Applications

#### Finding Middle Node
```java
ListNode findMiddle(ListNode head) {
    if (head == null) return null;
    
    ListNode slow = head;
    ListNode fast = head;
    
    while (fast.next != null && fast.next.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }
    
    return slow;  // Middle node (first middle if even length)
}
```

**Analysis:**
- **Odd length:** Fast reaches end when slow at exact middle
- **Even length:** Returns first of two middle nodes
- **Variation:** For second middle, use `fast != null` condition

#### Removing Nth Node from End
```java
ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    
    ListNode first = dummy;
    ListNode second = dummy;
    
    // Move first n+1 steps ahead
    for (int i = 0; i <= n; i++) {
        first = first.next;
    }
    
    // Move both until first reaches end
    while (first != null) {
        first = first.next;
        second = second.next;
    }
    
    // Remove nth node
    second.next = second.next.next;
    return dummy.next;
}
```

**Two-pointer insight:** Maintain constant gap of n nodes between pointers

### 2. Intersection of Two Lists
```java
ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    if (headA == null || headB == null) return null;
    
    ListNode a = headA;
    ListNode b = headB;
    
    // When reaching end, switch to other list's head
    while (a != b) {
        a = (a == null) ? headB : a.next;
        b = (b == null) ? headA : b.next;
    }
    
    return a;  // Intersection point or null
}
```

**Elegant insight:**
- **Both pointers travel same total distance**
- **If intersection exists, they meet at intersection**
- **If no intersection, they meet at null**

**Mathematical proof:**
```
List A: a1 → a2 → c1 → c2 → c3
List B: b1 → b2 → b3 → c1 → c2 → c3

Pointer A path: a1→a2→c1→c2→c3→null→b1→b2→b3→c1 (meets here)
Pointer B path: b1→b2→b3→c1→c2→c3→null→a1→a2→c1 (meets here)

Both travel distance: (length A) + (length B)
```

## Memory Management and Performance

### Memory Overhead Analysis
**Comparison with arrays:**

**Array storage:**
```
int[] array = {1, 2, 3, 4};
Memory: [1][2][3][4] = 16 bytes (4 × 4 bytes)
```

**Linked list storage:**
```
Node objects: 4 × (8 bytes object header + 4 bytes data + 4 bytes pointer)
Total: 64 bytes (4× more memory!)
```

### Cache Performance
**Why arrays are faster for sequential access:**
- **Spatial locality:** Adjacent elements in memory
- **Cache prefetching:** CPU loads nearby memory automatically
- **Fewer memory allocations:** One allocation vs multiple

**When linked lists are better:**
- **Frequent insertions/deletions** in middle
- **Unknown size** at compile time
- **Memory fragmentation** is not an issue

### Dynamic Memory Allocation
**Java garbage collection considerations:**
```java
// Good: Iterative deletion (constant stack space)
while (head != null) {
    ListNode temp = head;
    head = head.next;
    temp.next = null;  // Help GC by breaking references
}

// Problematic: Recursive deletion (O(n) stack space)
void deleteRecursive(ListNode node) {
    if (node == null) return;
    deleteRecursive(node.next);
    // Node becomes eligible for GC here
}
```

## Linked Lists vs Other Data Structures

### Comparison Table
| Operation | Array | LinkedList | ArrayList | Vector |
|-----------|--------|------------|-----------|---------|
| Access by index | O(1) | O(n) | O(1) | O(1) |
| Insert at head | O(n) | O(1) | O(n) | O(n) |
| Insert at tail | O(1)* | O(n)** | O(1)*** | O(1)*** |
| Insert at middle | O(n) | O(n) | O(n) | O(n) |
| Delete by index | O(n) | O(n) | O(n) | O(n) |
| Search | O(n) | O(n) | O(n) | O(n) |
| Memory overhead | Low | High | Medium | Medium |

*\*If space available*  
*\*\*O(1) with tail pointer*  
*\*\*\*Amortized due to resizing*

### When to Use Linked Lists
**Advantages:**
- **Dynamic size** without declaring capacity
- **O(1) insertion/deletion** at known positions
- **Memory efficient** for sparse data
- **No memory reallocation** needed

**Choose linked lists when:**
- Frequent insertions/deletions at beginning
- Size varies significantly during runtime
- Memory usage is more important than access speed
- Implementing other data structures (stacks, queues)

**Avoid linked lists when:**
- Need random access by index
- Memory usage is critical
- Cache performance is important
- Doing mathematical operations on sequences

## Common Pitfalls and Interview Tips

### Common Mistakes
1. **Null pointer exceptions:** Always check for null before accessing
2. **Losing references:** Save pointers before updating them
3. **Infinite loops:** In circular lists or incorrect cycle handling
4. **Memory leaks:** In languages with manual memory management
5. **Off-by-one errors:** In position-based operations

### Edge Cases to Consider
1. **Empty list:** `head == null`
2. **Single node:** `head.next == null`
3. **Two nodes:** Special case for many algorithms
4. **Cycles:** Infinite loops if not handled properly

### Interview Problem-Solving Strategy
1. **Draw the problem:** Visualize with boxes and arrows
2. **Identify pattern:** Reversal, cycle detection, merging, etc.
3. **Consider edge cases:** Empty, single node, cycles
4. **Use dummy nodes:** Simplifies insertion/deletion logic
5. **Two-pointer technique:** Often useful for linked list problems

### Problem Categories
**Manipulation:**
- Reverse linked list (iterative/recursive)
- Rotate list
- Swap nodes in pairs

**Detection:**
- Cycle detection and finding cycle start
- Intersection of two linked lists
- Palindrome linked list

**Merging/Splitting:**
- Merge two sorted lists
- Merge k sorted lists
- Split list into parts

**Advanced:**
- LRU cache implementation
- Design linked list from scratch
- Copy list with random pointers

### Code Template for Linked List Problems
```java
public ListNode solveLinkedListProblem(ListNode head) {
    // Handle edge cases
    if (head == null) return null;
    
    // Use dummy node for insertion/deletion problems
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    
    // Initialize pointers
    ListNode prev = dummy;
    ListNode current = head;
    
    // Main algorithm logic
    while (current != null) {
        // Process current node
        // Update pointers carefully
        current = current.next;
    }
    
    // Return appropriate result
    return dummy.next;  // or head, depending on problem
}
```