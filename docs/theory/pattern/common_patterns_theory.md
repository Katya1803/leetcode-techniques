# Common Patterns - CS Fundamentals

## Introduction to Algorithmic Patterns

### What are Algorithmic Patterns?
**Definition:** Reusable problem-solving approaches that can be applied to multiple similar problems with consistent underlying structures.

**Why patterns matter:**
- **Recognition speed:** Quickly identify solution approaches
- **Interview efficiency:** Reduce time to find optimal solutions
- **Code quality:** Consistent, well-tested implementations
- **Learning acceleration:** Build from known solutions to new problems

### Pattern Classification Framework
```
Pattern Categories:
├── Data Structure Patterns
│   ├── Two Pointers
│   ├── Sliding Window
│   ├── Fast & Slow Pointers
│   └── Merge Intervals
├── Algorithmic Paradigms
│   ├── Divide & Conquer
│   ├── Dynamic Programming
│   ├── Greedy
│   └── Backtracking
├── Graph Patterns
│   ├── Tree Traversal (DFS/BFS)
│   ├── Topological Sort
│   ├── Union Find
│   └── Shortest Path
└── Mathematical Patterns
    ├── Bit Manipulation
    ├── Mathematical Formulas
    └── Number Theory
```

## Core Pattern Analysis

### 1. Two Pointers Pattern

#### Pattern Identification
**Use when:**
- Working with sorted arrays/strings
- Need to find pairs/triplets with specific properties
- Optimizing from O(n²) to O(n)
- Palindrome-related problems

#### Sub-patterns
**Opposite Direction:**
```java
// Template for opposite direction two pointers
public boolean twoPointersOpposite(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    
    while (left < right) {
        int sum = arr[left] + arr[right];
        if (sum == target) {
            return true;
        } else if (sum < target) {
            left++;
        } else {
            right--;
        }
    }
    return false;
}
```

**Same Direction (Fast-Slow):**
```java
// Template for same direction two pointers
public int removeDuplicates(int[] arr) {
    int slow = 0;
    for (int fast = 0; fast < arr.length; fast++) {
        if (shouldInclude(arr[fast], arr[slow])) {
            arr[slow++] = arr[fast];
        }
    }
    return slow;
}
```

**Problem Examples:**
- Two Sum II, 3Sum, 4Sum
- Valid Palindrome
- Container With Most Water
- Remove Duplicates
- Linked List Cycle Detection

### 2. Sliding Window Pattern

#### Pattern Identification
**Use when:**
- Working with contiguous subarrays/substrings
- Need to find optimal window size
- Optimization problems involving sequences
- String matching problems

#### Sub-patterns
**Fixed Size Window:**
```java
// Template for fixed size sliding window
public int fixedSizeWindow(int[] arr, int k) {
    int windowSum = 0;
    
    // Calculate first window
    for (int i = 0; i < k; i++) {
        windowSum += arr[i];
    }
    
    int maxSum = windowSum;
    
    // Slide window
    for (int i = k; i < arr.length; i++) {
        windowSum = windowSum - arr[i - k] + arr[i];
        maxSum = Math.max(maxSum, windowSum);
    }
    
    return maxSum;
}
```

**Variable Size Window:**
```java
// Template for variable size sliding window
public int variableSizeWindow(String s, Condition condition) {
    Map<Character, Integer> window = new HashMap<>();
    int left = 0, maxLength = 0;
    
    for (int right = 0; right < s.length(); right++) {
        // Expand window
        char rightChar = s.charAt(right);
        window.put(rightChar, window.getOrDefault(rightChar, 0) + 1);
        
        // Contract window while condition violated
        while (!condition.isValid(window)) {
            char leftChar = s.charAt(left);
            window.put(leftChar, window.get(leftChar) - 1);
            if (window.get(leftChar) == 0) {
                window.remove(leftChar);
            }
            left++;
        }
        
        maxLength = Math.max(maxLength, right - left + 1);
    }
    
    return maxLength;
}
```

**Problem Examples:**
- Maximum Subarray Sum of Size K
- Longest Substring Without Repeating Characters
- Minimum Window Substring
- Permutation in String

### 3. Fast & Slow Pointers (Floyd's Cycle Detection)

#### Pattern Identification
**Use when:**
- Detecting cycles in sequences
- Finding middle elements
- Determining cycle start points
- K-th element from end problems

#### Implementation Template
```java
// Template for fast & slow pointers
public ListNode floydAlgorithm(ListNode head) {
    if (head == null || head.next == null) return null;
    
    ListNode slow = head, fast = head;
    
    // Phase 1: Detect cycle
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        
        if (slow == fast) {
            break; // Cycle detected
        }
    }
    
    // Phase 2: Find cycle start (if needed)
    if (fast == null || fast.next == null) return null; // No cycle
    
    slow = head;
    while (slow != fast) {
        slow = slow.next;
        fast = fast.next;
    }
    
    return slow; // Cycle start
}
```

**Problem Examples:**
- Linked List Cycle
- Find Duplicate Number
- Happy Number
- Middle of Linked List

### 4. Merge Intervals Pattern

#### Pattern Identification
**Use when:**
- Working with overlapping intervals
- Scheduling problems
- Range consolidation
- Timeline merging

#### Implementation Template
```java
// Template for merge intervals
public int[][] mergeIntervals(int[][] intervals) {
    if (intervals.length <= 1) return intervals;
    
    // Sort by start time
    Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
    
    List<int[]> merged = new ArrayList<>();
    int[] current = intervals[0];
    
    for (int i = 1; i < intervals.length; i++) {
        int[] next = intervals[i];
        
        if (current[1] >= next[0]) { // Overlap
            current[1] = Math.max(current[1], next[1]); // Merge
        } else {
            merged.add(current);
            current = next;
        }
    }
    
    merged.add(current);
    return merged.toArray(new int[merged.size()][]);
}
```

**Problem Examples:**
- Merge Intervals
- Insert Interval
- Meeting Rooms
- Non-overlapping Intervals

### 5. Cyclic Sort Pattern

#### Pattern Identification
**Use when:**
- Array contains numbers in specific range (1 to n)
- Finding missing/duplicate numbers
- Need O(n) time with O(1) space
- Numbers are close to their indices

#### Implementation Template
```java
// Template for cyclic sort
public void cyclicSort(int[] nums) {
    int i = 0;
    while (i < nums.length) {
        int correctIndex = nums[i] - 1; // For range [1, n]
        
        if (nums[i] != nums[correctIndex]) {
            swap(nums, i, correctIndex);
        } else {
            i++;
        }
    }
}

private void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
}
```

**Problem Examples:**
- Missing Number
- Find All Duplicates
- Find Corrupt Pair
- First Missing Positive

## Tree Traversal Patterns

### 6. Tree DFS Pattern

#### Sub-patterns by Traversal Type
**Preorder (Root-Left-Right):**
```java
// Template for preorder DFS
public void preorderDFS(TreeNode root, List<Integer> result) {
    if (root == null) return;
    
    result.add(root.val); // Process root
    preorderDFS(root.left, result);
    preorderDFS(root.right, result);
}
```

**Inorder (Left-Root-Right):**
```java
// Template for inorder DFS  
public void inorderDFS(TreeNode root, List<Integer> result) {
    if (root == null) return;
    
    inorderDFS(root.left, result);
    result.add(root.val); // Process root
    inorderDFS(root.right, result);
}
```

**Postorder (Left-Right-Root):**
```java
// Template for postorder DFS
public void postorderDFS(TreeNode root, List<Integer> result) {
    if (root == null) return;
    
    postorderDFS(root.left, result);
    postorderDFS(root.right, result);
    result.add(root.val); // Process root
}
```

#### Applications by Pattern
- **Preorder:** Tree serialization, expression trees
- **Inorder:** BST sorted traversal, validation
- **Postorder:** Tree deletion, size calculation

### 7. Tree BFS Pattern

#### Implementation Template
```java
// Template for tree BFS
public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;
    
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

**Problem Examples:**
- Level Order Traversal
- Zigzag Traversal
- Binary Tree Right Side View
- Minimum Depth

## Advanced Patterns

### 8. Topological Sort Pattern

#### Pattern Identification
**Use when:**
- Dependency resolution problems
- Course scheduling
- Build systems
- Task ordering with prerequisites

#### Implementation Templates
**Kahn's Algorithm (BFS-based):**
```java
// Template for Kahn's topological sort
public List<Integer> topologicalSort(int numNodes, int[][] edges) {
    List<List<Integer>> graph = buildGraph(numNodes, edges);
    int[] inDegree = calculateInDegree(graph);
    
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < numNodes; i++) {
        if (inDegree[i] == 0) {
            queue.offer(i);
        }
    }
    
    List<Integer> result = new ArrayList<>();
    while (!queue.isEmpty()) {
        int node = queue.poll();
        result.add(node);
        
        for (int neighbor : graph.get(node)) {
            inDegree[neighbor]--;
            if (inDegree[neighbor] == 0) {
                queue.offer(neighbor);
            }
        }
    }
    
    return result.size() == numNodes ? result : new ArrayList<>(); // Check for cycles
}
```

**DFS-based:**
```java
// Template for DFS topological sort
public List<Integer> topologicalSortDFS(int numNodes, int[][] edges) {
    List<List<Integer>> graph = buildGraph(numNodes, edges);
    boolean[] visited = new boolean[numNodes];
    Stack<Integer> stack = new Stack<>();
    
    for (int i = 0; i < numNodes; i++) {
        if (!visited[i]) {
            dfs(i, graph, visited, stack);
        }
    }
    
    List<Integer> result = new ArrayList<>();
    while (!stack.isEmpty()) {
        result.add(stack.pop());
    }
    
    return result;
}

private void dfs(int node, List<List<Integer>> graph, boolean[] visited, Stack<Integer> stack) {
    visited[node] = true;
    
    for (int neighbor : graph.get(node)) {
        if (!visited[neighbor]) {
            dfs(neighbor, graph, visited, stack);
        }
    }
    
    stack.push(node);
}
```

### 9. Modified Binary Search Pattern

#### Pattern Identification
**Use when:**
- Searching in rotated/modified sorted arrays
- Finding boundaries or ranges
- Answer space binary search
- Peak finding problems

#### Sub-patterns
**Search in Rotated Array:**
```java
// Template for rotated array search
public int searchRotated(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) return mid;
        
        if (nums[left] <= nums[mid]) { // Left half sorted
            if (target >= nums[left] && target < nums[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        } else { // Right half sorted
            if (target > nums[mid] && target <= nums[right]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
    }
    
    return -1;
}
```

**Binary Search on Answer Space:**
```java
// Template for answer space binary search
public int binarySearchOnAnswer(int[] nums, int target) {
    int left = getMinPossibleAnswer();
    int right = getMaxPossibleAnswer();
    
    while (left < right) {
        int mid = left + (right - left) / 2;
        
        if (canAchieve(nums, mid, target)) {
            right = mid; // Try for better answer
        } else {
            left = mid + 1; // Need larger answer
        }
    }
    
    return left;
}

private boolean canAchieve(int[] nums, int candidate, int target) {
    // Problem-specific feasibility check
    return false; // Placeholder
}
```

### 10. Subsets Pattern

#### Pattern Identification
**Use when:**
- Generating all combinations/permutations
- Backtracking problems
- Decision tree exploration
- Constraint satisfaction

#### Implementation Templates
**Iterative Subsets:**
```java
// Template for iterative subset generation
public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    result.add(new ArrayList<>()); // Empty subset
    
    for (int num : nums) {
        int size = result.size();
        for (int i = 0; i < size; i++) {
            List<Integer> newSubset = new ArrayList<>(result.get(i));
            newSubset.add(num);
            result.add(newSubset);
        }
    }
    
    return result;
}
```

**Backtracking Subsets:**
```java
// Template for backtracking subset generation
public List<List<Integer>> subsetsBacktrack(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    generateSubsets(nums, 0, new ArrayList<>(), result);
    return result;
}

private void generateSubsets(int[] nums, int index, List<Integer> current, 
                           List<List<Integer>> result) {
    if (index == nums.length) {
        result.add(new ArrayList<>(current));
        return;
    }
    
    // Don't include current element
    generateSubsets(nums, index + 1, current, result);
    
    // Include current element
    current.add(nums[index]);
    generateSubsets(nums, index + 1, current, result);
    current.remove(current.size() - 1); // Backtrack
}
```

## Pattern Combination Strategies

### Multi-Pattern Problems
**Example: Sliding Window + Hash Map**
```java
// Find longest substring with at most k distinct characters
public int lengthOfLongestSubstringKDistinct(String s, int k) {
    Map<Character, Integer> charCount = new HashMap<>();
    int left = 0, maxLength = 0;
    
    for (int right = 0; right < s.length(); right++) {
        char rightChar = s.charAt(right);
        charCount.put(rightChar, charCount.getOrDefault(rightChar, 0) + 1);
        
        while (charCount.size() > k) {
            char leftChar = s.charAt(left);
            charCount.put(leftChar, charCount.get(leftChar) - 1);
            if (charCount.get(leftChar) == 0) {
                charCount.remove(leftChar);
            }
            left++;
        }
        
        maxLength = Math.max(maxLength, right - left + 1);
    }
    
    return maxLength;
}
```

### Pattern Selection Framework
```
Decision Tree for Pattern Selection:

Input Type?
├── Array/String
│   ├── Sorted? → Two Pointers / Binary Search
│   ├── Contiguous subsequence? → Sliding Window
│   ├── All combinations? → Subsets/Backtracking
│   └── Range [1,n]? → Cyclic Sort
├── Tree
│   ├── Path problems? → DFS
│   ├── Level problems? → BFS
│   └── BST? → Inorder DFS
├── Graph
│   ├── Connectivity? → Union Find / DFS
│   ├── Shortest path? → BFS / Dijkstra
│   ├── Dependencies? → Topological Sort
│   └── Cycles? → DFS with colors
└── Optimization
    ├── Overlapping subproblems? → DP
    ├── Greedy choice property? → Greedy
    └── Search space? → Binary Search on Answer
```

## Pattern Complexity Analysis

### Time Complexity by Pattern
| Pattern | Typical Complexity | Space Complexity |
|---------|-------------------|------------------|
| **Two Pointers** | O(n) | O(1) |
| **Sliding Window** | O(n) | O(k) |
| **Fast & Slow** | O(n) | O(1) |
| **Merge Intervals** | O(n log n) | O(n) |
| **Tree DFS** | O(n) | O(h) |
| **Tree BFS** | O(n) | O(w) |
| **Topological Sort** | O(V + E) | O(V) |
| **Modified Binary Search** | O(log n) | O(1) |
| **Subsets** | O(2ⁿ) | O(n) |

*Where: n=elements, k=window size, h=height, w=width, V=vertices, E=edges*

## Interview Application Strategies

### Pattern Recognition Speed Training
1. **Read problem statement**
2. **Identify key characteristics:**
   - Data structure type
   - Optimization goal
   - Constraints
3. **Match to pattern family**
4. **Select specific variant**
5. **Apply template and customize**

### Common Pattern Combinations
- **Sliding Window + Hash Map:** String problems with character constraints
- **DFS + Memoization:** Tree DP problems
- **Binary Search + Greedy:** Optimization problems with decision functions
- **Union Find + Sorting:** Graph connectivity with edge processing
- **Two Pointers + Sorting:** Array problems with pair finding

### Template Customization Guidelines
1. **Identify core logic:** What makes this problem unique?
2. **Modify condition checks:** Problem-specific validity conditions
3. **Adjust data structures:** Use appropriate containers for problem
4. **Handle edge cases:** Empty inputs, single elements, boundary conditions
5. **Optimize for constraints:** Consider time/space requirements

### Practice Progression
**Level 1:** Master individual patterns
**Level 2:** Recognize patterns in mixed problems  
**Level 3:** Combine multiple patterns fluently
**Level 4:** Derive new patterns from first principles

This comprehensive pattern framework provides the foundation for systematically approaching algorithmic interview problems with confidence and efficiency.