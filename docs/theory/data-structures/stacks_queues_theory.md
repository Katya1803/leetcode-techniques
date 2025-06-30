# Stacks and Queues - CS Fundamentals

## Abstract Data Types (ADT) Definition

### Stack ADT
**Definition:** A Last-In-First-Out (LIFO) data structure

**Essential operations:**
- `push(item)`: Add item to top
- `pop()`: Remove and return top item
- `peek()/top()`: Return top item without removing
- `isEmpty()`: Check if stack is empty
- `size()`: Return number of elements

**Mathematical notation:**
```
Stack S = [bottom ... top]
push(S, x) → S' = [bottom ... top, x]
pop(S) → (x, S') where S = [bottom ... top, x]
```

### Queue ADT
**Definition:** A First-In-First-Out (FIFO) data structure

**Essential operations:**
- `enqueue(item)`: Add item to rear
- `dequeue()`: Remove and return front item
- `front()`: Return front item without removing
- `isEmpty()`: Check if queue is empty
- `size()`: Return number of elements

**Mathematical notation:**
```
Queue Q = [front ... rear]
enqueue(Q, x) → Q' = [front ... rear, x]
dequeue(Q) → (x, Q') where Q = [x, front ... rear]
```

## Implementation Strategies

### Stack Implementations

#### 1. Array-based Stack
```java
class ArrayStack<T> {
    private T[] array;
    private int top;
    private int capacity;
    
    public void push(T item) {
        if (top == capacity - 1) {
            resize();  // O(n) occasionally
        }
        array[++top] = item;  // O(1)
    }
    
    public T pop() {
        if (isEmpty()) throw new EmptyStackException();
        T item = array[top];
        array[top--] = null;  // Prevent memory leak
        return item;  // O(1)
    }
}
```

**Analysis:**
- **Time:** O(1) for all operations (amortized for push with resizing)
- **Space:** O(n) where n is number of elements
- **Advantages:** Cache-friendly, simple implementation
- **Disadvantages:** Fixed capacity (or expensive resizing)

#### 2. Linked List-based Stack
```java
class LinkedStack<T> {
    private Node<T> head;  // Top of stack
    
    private static class Node<T> {
        T data;
        Node<T> next;
    }
    
    public void push(T item) {
        Node<T> newNode = new Node<>();
        newNode.data = item;
        newNode.next = head;
        head = newNode;  // O(1)
    }
    
    public T pop() {
        if (isEmpty()) throw new EmptyStackException();
        T item = head.data;
        head = head.next;  // O(1)
        return item;
    }
}
```

**Analysis:**
- **Time:** O(1) for all operations
- **Space:** O(n) + pointer overhead
- **Advantages:** Dynamic size, no resizing needed
- **Disadvantages:** Extra memory for pointers, poor cache locality

### Queue Implementations

#### 1. Circular Array Queue
```java
class CircularQueue<T> {
    private T[] array;
    private int front, rear, size, capacity;
    
    public void enqueue(T item) {
        if (size == capacity) {
            resize();  // O(n) occasionally
        }
        array[rear] = item;
        rear = (rear + 1) % capacity;  // Circular increment
        size++;  // O(1)
    }
    
    public T dequeue() {
        if (isEmpty()) throw new EmptyQueueException();
        T item = array[front];
        array[front] = null;
        front = (front + 1) % capacity;  // Circular increment
        size--;
        return item;  // O(1)
    }
}
```

**Key insight:** Circular array prevents "false full" condition
```
Linear array issue:
[_, _, _, X, X, X] → rear at end, but space available at beginning

Circular array solution:
front = 3, rear = 0 → can continue adding at index 0, 1, 2
```

#### 2. Linked List Queue
```java
class LinkedQueue<T> {
    private Node<T> front, rear;
    
    public void enqueue(T item) {
        Node<T> newNode = new Node<>(item);
        if (isEmpty()) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }  // O(1)
    }
    
    public T dequeue() {
        if (isEmpty()) throw new EmptyQueueException();
        T item = front.data;
        front = front.next;
        if (front == null) rear = null;  // Queue became empty
        return item;  // O(1)
    }
}
```

## Advanced Queue Variants

### Double-ended Queue (Deque)
**Definition:** Supports insertion and deletion at both ends

**Operations:**
- `addFirst(item)`, `addLast(item)`
- `removeFirst()`, `removeLast()`
- `peekFirst()`, `peekLast()`

**Implementation approach:** Circular array with both directions
```java
// Add to front: front = (front - 1 + capacity) % capacity
// Add to rear: rear = (rear + 1) % capacity
```

**Applications:**
- Sliding window problems
- Palindrome checking
- Undo/redo functionality

### Priority Queue
**Definition:** Elements have priorities; highest priority element is dequeued first

**Implementation:** Usually with heap data structure
- **Insert:** O(log n) - maintain heap property
- **Extract-max/min:** O(log n) - reheapify after removal
- **Peek:** O(1) - root element

**Heap property:**
- **Max heap:** Parent ≥ children
- **Min heap:** Parent ≤ children

## The Call Stack and Recursion

### Call Stack Mechanics
**What happens during function call:**
1. **Push activation record** onto call stack
2. **Activation record contains:**
   - Function parameters
   - Local variables
   - Return address
   - Previous frame pointer

**Example:**
```java
int factorial(int n) {
    if (n <= 1) return 1;           // Base case
    return n * factorial(n - 1);    // Recursive call
}

factorial(4) call stack:
┌─────────────────┐ ← Stack top
│ factorial(1)    │   n=1, return 1
├─────────────────┤
│ factorial(2)    │   n=2, waiting for factorial(1)
├─────────────────┤  
│ factorial(3)    │   n=3, waiting for factorial(2)
├─────────────────┤
│ factorial(4)    │   n=4, waiting for factorial(3)
└─────────────────┘ ← Stack bottom
```

### Stack Overflow
**Cause:** Too many recursive calls → stack memory exhausted

**Example of infinite recursion:**
```java
int badFactorial(int n) {
    return n * badFactorial(n - 1);  // No base case!
}
```

**Prevention strategies:**
1. **Proper base cases**
2. **Tail recursion optimization** (compiler dependent)
3. **Convert to iterative** approach
4. **Increase stack size** (JVM -Xss flag)

### Recursion to Stack Conversion
**Manual conversion using explicit stack:**

```java
// Recursive approach
void inorderTraversal(TreeNode root) {
    if (root == null) return;
    inorderTraversal(root.left);
    process(root);
    inorderTraversal(root.right);
}

// Iterative approach with stack
void inorderIterative(TreeNode root) {
    Stack<TreeNode> stack = new Stack<>();
    TreeNode current = root;
    
    while (current != null || !stack.isEmpty()) {
        while (current != null) {
            stack.push(current);
            current = current.left;
        }
        current = stack.pop();
        process(current);
        current = current.right;
    }
}
```

## Stack-based Algorithms

### 1. Expression Evaluation
**Infix to Postfix conversion:**
```java
String infixToPostfix(String infix) {
    Stack<Character> operators = new Stack<>();
    StringBuilder postfix = new StringBuilder();
    
    for (char c : infix.toCharArray()) {
        if (Character.isLetterOrDigit(c)) {
            postfix.append(c);
        } else if (c == '(') {
            operators.push(c);
        } else if (c == ')') {
            while (!operators.isEmpty() && operators.peek() != '(') {
                postfix.append(operators.pop());
            }
            operators.pop();  // Remove '('
        } else {  // Operator
            while (!operators.isEmpty() && 
                   precedence(operators.peek()) >= precedence(c)) {
                postfix.append(operators.pop());
            }
            operators.push(c);
        }
    }
    
    while (!operators.isEmpty()) {
        postfix.append(operators.pop());
    }
    
    return postfix.toString();
}
```

**Key principle:** Use stack to maintain operator precedence and associativity

### 2. Balanced Parentheses
```java
boolean isBalanced(String s) {
    Stack<Character> stack = new Stack<>();
    Map<Character, Character> pairs = Map.of(')', '(', '}', '{', ']', '[');
    
    for (char c : s.toCharArray()) {
        if (pairs.containsValue(c)) {  // Opening bracket
            stack.push(c);
        } else if (pairs.containsKey(c)) {  // Closing bracket
            if (stack.isEmpty() || stack.pop() != pairs.get(c)) {
                return false;
            }
        }
    }
    
    return stack.isEmpty();
}
```

**Invariant:** Stack contains unmatched opening brackets

### 3. Monotonic Stack
**Definition:** Stack where elements are in monotonic order (increasing or decreasing)

**Pattern for "next greater element":**
```java
int[] nextGreaterElement(int[] nums) {
    Stack<Integer> stack = new Stack<>();  // Stores indices
    int[] result = new int[nums.length];
    Arrays.fill(result, -1);
    
    for (int i = 0; i < nums.length; i++) {
        while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
            int index = stack.pop();
            result[index] = nums[i];  // Found next greater for nums[index]
        }
        stack.push(i);
    }
    
    return result;
}
```

**Applications:**
- Daily temperatures
- Stock span problem
- Largest rectangle in histogram
- Trapping rainwater

## Queue-based Algorithms

### 1. Level-order Tree Traversal (BFS)
```java
List<List<Integer>> levelOrder(TreeNode root) {
    if (root == null) return new ArrayList<>();
    
    List<List<Integer>> result = new ArrayList<>();
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    while (!queue.isEmpty()) {
        int levelSize = queue.size();
        List<Integer> currentLevel = new ArrayList<>();
        
        for (int i = 0; i < levelSize; i++) {
            TreeNode node = queue.poll();
            currentLevel.add(node.val);
            
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
        
        result.add(currentLevel);
    }
    
    return result;
}
```

**Key insight:** Queue naturally processes nodes level by level

### 2. Sliding Window Maximum
**Using deque to maintain maximum in window:**
```java
int[] maxSlidingWindow(int[] nums, int k) {
    Deque<Integer> deque = new ArrayDeque<>();  // Stores indices
    int[] result = new int[nums.length - k + 1];
    
    for (int i = 0; i < nums.length; i++) {
        // Remove indices outside current window
        while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
            deque.pollFirst();
        }
        
        // Remove indices with smaller values (not useful)
        while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
            deque.pollLast();
        }
        
        deque.offerLast(i);
        
        // Add to result when window is complete
        if (i >= k - 1) {
            result[i - k + 1] = nums[deque.peekFirst()];
        }
    }
    
    return result;
}
```

**Invariant:** Deque maintains indices in decreasing order of their values

## Performance Analysis and Trade-offs

### Stack vs Queue Comparison
| Aspect | Stack | Queue |
|--------|-------|-------|
| Access pattern | LIFO | FIFO |
| Primary operations | push/pop | enqueue/dequeue |
| Memory locality | Excellent (array) | Good (circular array) |
| Use cases | Recursion, parsing | BFS, scheduling |

### Implementation Trade-offs
| Implementation | Time | Space | Pros | Cons |
|----------------|------|-------|------|------|
| Array-based | O(1) amortized | O(n) | Fast, cache-friendly | Resize overhead |
| Linked-based | O(1) | O(n) + pointers | Dynamic size | Poor cache locality |

## Common Pitfalls and Interview Tips

### Stack Pitfalls
1. **Stack overflow** with deep recursion
2. **Empty stack operations** - always check `isEmpty()`
3. **Memory leaks** in array implementation - null out popped elements
4. **Confusing push/pop order** - remember LIFO

### Queue Pitfalls
1. **Circular array wraparound** - use modulo arithmetic correctly
2. **Front/rear pointer management** - handle empty queue edge case
3. **False full condition** in simple array implementation
4. **Forgetting to update both pointers** in linked implementation

### Problem Recognition Patterns
**Use Stack when:**
- Need to reverse order (LIFO nature)
- Parsing nested structures
- Function call simulation
- Maintaining state in recursion

**Use Queue when:**
- Need to preserve order (FIFO nature)
- Level-by-level processing
- Breadth-first algorithms
- Producer-consumer scenarios

**Use Deque when:**
- Need access to both ends
- Sliding window problems
- Palindrome checking
- Undo/redo functionality

### Interview Problem-Solving Approach
1. **Identify the access pattern** needed (LIFO vs FIFO)
2. **Consider edge cases** (empty, single element)
3. **Think about efficiency** (time vs space trade-offs)
4. **Simulate with examples** to verify correctness
5. **Consider overflow/underflow** conditions

### Classic Interview Problems
- **Stack:** Valid parentheses, evaluate expressions, next greater element
- **Queue:** Level-order traversal, sliding window maximum, implement stack using queues
- **Both:** Design browser history, LRU cache implementation