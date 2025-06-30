# Two Pointers - CS Fundamentals

## Core Concept and Mathematical Foundation

### Definition
**Two Pointers Technique:** An algorithmic approach using two pointers to traverse data structures, typically to solve problems involving pairs, subsequences, or searching in linear time.

**Fundamental principle:** Instead of nested loops (O(n²)), use two pointers moving in coordinated fashion to achieve O(n) time complexity.

**Mathematical insight:**
```
Brute force: Check all pairs (i,j) where i < j
Time complexity: C(n,2) = n(n-1)/2 = O(n²)

Two pointers: Each element visited at most once by each pointer
Time complexity: O(n)
```

### Why Two Pointers Work
**Key mathematical property:** Many problems have **monotonic properties** that allow us to make decisions about pointer movement without losing potential solutions.

**Example - Two Sum in sorted array:**
```
If nums[left] + nums[right] > target:
  - Any pair (left, x) where x > right will also be > target
  - Safe to decrement right pointer
  - No valid solutions are lost
```

## Two Pointer Patterns

### Pattern 1: Opposite Direction (Converging)
**Setup:** Start from both ends, move towards center
```java
int left = 0;
int right = array.length - 1;

while (left < right) {
    // Process current pair
    // Move pointers based on condition
    if (condition) {
        left++;
    } else {
        right--;
    }
}
```

**When to use:**
- Sorted arrays with target sum/difference
- Palindrome checking
- Container/area problems
- Reversing operations

**Time complexity:** O(n) - each element visited once
**Space complexity:** O(1) - only pointer variables

### Pattern 2: Same Direction (Fast-Slow)
**Setup:** Both pointers start from same end, move at different speeds
```java
int slow = 0;
for (int fast = 0; fast < array.length; fast++) {
    if (condition) {
        array[slow] = array[fast];
        slow++;
    }
}
return slow;  // New length after modification
```

**When to use:**
- Remove duplicates/elements in-place
- Partition arrays
- Linked list cycle detection
- Finding middle elements

**Key insight:** Slow pointer maintains "valid" portion, fast pointer explores ahead

### Pattern 3: Fixed Distance
**Setup:** Maintain constant distance between pointers
```java
int k = targetDistance;
for (int right = 0; right < array.length; right++) {
    if (right >= k) {
        int left = right - k;
        // Process window [left, right]
    }
}
```

**When to use:**
- Sliding window problems
- K-distance problems
- Finding elements with specific gap

## Detailed Algorithm Analysis

### 1. Two Sum in Sorted Array
**Problem:** Find indices where `nums[i] + nums[j] = target`

```java
public int[] twoSum(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    
    while (left < right) {
        int sum = nums[left] + nums[right];
        
        if (sum == target) {
            return new int[]{left, right};
        } else if (sum < target) {
            left++;  // Need larger sum
        } else {
            right--; // Need smaller sum
        }
    }
    
    return new int[]{-1, -1};  // No solution found
}
```

**Correctness proof:**
- **Invariant:** All pairs (i,j) with i < left or j > right have been considered
- **Termination:** left and right will eventually meet
- **Completeness:** All valid pairs are examined exactly once

**Why this works:**
```
When sum < target: nums[left] + nums[right] < target
  → nums[left] + nums[k] < target for all k ≤ right
  → No valid pair starts at left, safe to increment left

When sum > target: nums[left] + nums[right] > target  
  → nums[k] + nums[right] > target for all k ≥ left
  → No valid pair ends at right, safe to decrement right
```

### 2. Container With Most Water
**Problem:** Find two lines that form container with maximum area

```java
public int maxArea(int[] height) {
    int left = 0, right = height.length - 1;
    int maxArea = 0;
    
    while (left < right) {
        int width = right - left;
        int currentArea = Math.min(height[left], height[right]) * width;
        maxArea = Math.max(maxArea, currentArea);
        
        // Move pointer with smaller height
        if (height[left] < height[right]) {
            left++;
        } else {
            right--;
        }
    }
    
    return maxArea;
}
```

**Key insight:** Always move the pointer with smaller height
**Reasoning:** 
- Area limited by shorter line
- Moving taller line can only decrease width without increasing height
- Moving shorter line might find taller line, potentially increasing area

### 3. Remove Duplicates (Same Direction)
**Problem:** Remove duplicates from sorted array in-place

```java
public int removeDuplicates(int[] nums) {
    if (nums.length <= 1) return nums.length;
    
    int writeIndex = 1;  // Slow pointer (next position to write)
    
    for (int readIndex = 1; readIndex < nums.length; readIndex++) {
        if (nums[readIndex] != nums[readIndex - 1]) {
            nums[writeIndex] = nums[readIndex];
            writeIndex++;
        }
    }
    
    return writeIndex;  // New length
}
```

**Invariant:** `nums[0...writeIndex-1]` contains unique elements in sorted order

**Two-pointer insight:**
- **Write pointer (slow):** Maintains valid unique sequence
- **Read pointer (fast):** Explores array to find next unique element
- **Gap between pointers:** Represents "duplicate space" being compressed

## Advanced Two Pointer Techniques

### 1. Three Sum Problem
**Problem:** Find all unique triplets that sum to zero

```java
public List<List<Integer>> threeSum(int[] nums) {
    Arrays.sort(nums);  // Essential for two-pointer technique
    List<List<Integer>> result = new ArrayList<>();
    
    for (int i = 0; i < nums.length - 2; i++) {
        // Skip duplicates for first number
        if (i > 0 && nums[i] == nums[i - 1]) continue;
        
        // Two-pointer for remaining sum
        int left = i + 1, right = nums.length - 1;
        int target = -nums[i];
        
        while (left < right) {
            int sum = nums[left] + nums[right];
            
            if (sum == target) {
                result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                
                // Skip duplicates
                while (left < right && nums[left] == nums[left + 1]) left++;
                while (left < right && nums[right] == nums[right - 1]) right--;
                
                left++;
                right--;
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
    }
    
    return result;
}
```

**Complexity analysis:**
- **Sorting:** O(n log n)
- **Main loop:** O(n) × O(n) = O(n²)
- **Overall:** O(n²)

**Reduction technique:** 3Sum → 2Sum by fixing first element

### 2. Palindrome Verification
**Problem:** Check if string is palindrome (ignoring non-alphanumeric, case-insensitive)

```java
public boolean isPalindrome(String s) {
    int left = 0, right = s.length() - 1;
    
    while (left < right) {
        // Skip non-alphanumeric from left
        while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
            left++;
        }
        
        // Skip non-alphanumeric from right  
        while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
            right--;
        }
        
        // Compare characters
        if (Character.toLowerCase(s.charAt(left)) != 
            Character.toLowerCase(s.charAt(right))) {
            return false;
        }
        
        left++;
        right--;
    }
    
    return true;
}
```

**Pattern insight:** Two pointers with condition-based skipping

### 3. Dutch National Flag (3-Way Partition)
**Problem:** Sort array containing only 0s, 1s, and 2s

```java
public void sortColors(int[] nums) {
    int low = 0;    // Boundary for 0s
    int mid = 0;    // Current element
    int high = nums.length - 1;  // Boundary for 2s
    
    while (mid <= high) {
        if (nums[mid] == 0) {
            swap(nums, low++, mid++);
        } else if (nums[mid] == 2) {
            swap(nums, mid, high--);  // Don't increment mid!
        } else {  // nums[mid] == 1
            mid++;
        }
    }
}
```

**Three-pointer technique:**
- **Invariant 1:** `nums[0...low-1]` contains only 0s
- **Invariant 2:** `nums[low...mid-1]` contains only 1s  
- **Invariant 3:** `nums[high+1...n-1]` contains only 2s
- **Unknown region:** `nums[mid...high]`

## Fast-Slow Pointer Specialty

### 1. Linked List Cycle Detection (Floyd's Algorithm)
**Problem:** Detect if linked list has cycle

```java
public boolean hasCycle(ListNode head) {
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
    
    return false;
}
```

**Mathematical proof:**
- **If no cycle:** Fast pointer reaches end
- **If cycle exists:** Fast pointer catches up to slow pointer
- **Meeting guaranteed:** Distance decreases by 1 each iteration

**Why different speeds work:**
```
In cycle of length C:
- After k iterations, slow is at position k % C
- Fast is at position 2k % C
- They meet when k ≡ 2k (mod C)
- This happens when k ≡ 0 (mod C), i.e., after C iterations
```

### 2. Finding Middle of Linked List
```java
public ListNode findMiddle(ListNode head) {
    if (head == null) return null;
    
    ListNode slow = head;
    ListNode fast = head;
    
    while (fast.next != null && fast.next.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }
    
    return slow;  // First middle for even length
}
```

**Insight:** When fast reaches end, slow is at middle

## When Two Pointers Apply

### Problem Characteristics
1. **Linear data structure** (array, string, linked list)
2. **Sorted input** or **monotonic property**
3. **Looking for pairs/subsequences** with specific properties
4. **Need to avoid O(n²) brute force**
5. **In-place modification** requirements

### Decision Framework
**Use opposite direction when:**
- Working with sorted arrays
- Looking for pairs with target sum/product
- Palindrome-related problems
- Container/area optimization problems

**Use same direction when:**
- Need to modify array in-place
- Removing/filtering elements
- Partitioning arrays
- Linked list problems

**Use fixed distance when:**
- Sliding window with fixed size
- K-distance problems
- Pattern matching with gaps

## Complexity Analysis

### Time Complexity Patterns
| Pattern | Typical Complexity | Reasoning |
|---------|-------------------|-----------|
| Opposite direction | O(n) | Each element visited once |
| Same direction | O(n) | Each element visited once |
| Fixed distance | O(n) | Linear scan with constant work |
| With sorting | O(n log n) | Dominated by sorting step |

### Space Complexity
- **Usually O(1):** Only pointer variables needed
- **Exception:** When storing results (e.g., all triplets in 3Sum)

## Common Pitfalls and Interview Tips

### Common Mistakes
1. **Infinite loops:** Forgetting to move pointers
2. **Index out of bounds:** Not checking array boundaries
3. **Missing edge cases:** Empty arrays, single elements
4. **Duplicate handling:** In problems requiring unique solutions
5. **Pointer initialization:** Starting from wrong positions

### Edge Cases to Consider
1. **Empty input:** Handle null/empty arrays
2. **Single element:** May need special handling
3. **All elements same:** Infinite loops in some algorithms
4. **No valid solution:** Return appropriate default value
5. **Sorted vs unsorted:** Some techniques require sorted input

### Problem Recognition Patterns
**Two pointers likely useful when:**
- Problem mentions "pairs" or "subsequences"
- Need to find elements with specific sum/difference
- Working with sorted arrays
- Need O(n) solution instead of O(n²)
- In-place array modification required

### Interview Problem-Solving Strategy
1. **Identify the pattern:** Which two-pointer variant applies?
2. **Determine pointer movement:** What conditions trigger movement?
3. **Establish invariants:** What properties are maintained?
4. **Handle edge cases:** Empty, single element, no solution
5. **Verify correctness:** Walk through examples

### Classic Problem Categories

**Sum-based problems:**
- Two Sum, 3Sum, 4Sum
- Two Sum II (sorted array)
- 3Sum closest

**Array manipulation:**
- Remove duplicates
- Remove element
- Move zeros
- Sort colors

**String problems:**
- Valid palindrome
- Reverse string
- Reverse words in string

**Linked list problems:**
- Cycle detection
- Finding middle
- Remove nth from end
- Merge sorted lists

**Container/optimization:**
- Container with most water
- Trapping rain water
- Longest mountain

### Template for Two Pointer Problems
```java
public returnType twoPointerProblem(inputType input) {
    // Handle edge cases
    if (input == null || input.length == 0) return defaultValue;
    
    // Initialize pointers based on pattern
    int left = 0;
    int right = input.length - 1;  // or other starting position
    
    // Main two-pointer loop
    while (left < right) {  // or other termination condition
        // Calculate current result
        calculateCurrentState();
        
        // Move pointers based on condition
        if (condition1) {
            left++;
        } else if (condition2) {
            right--;
        } else {
            // Both pointers move or other logic
            left++;
            right--;
        }
    }
    
    return result;
}
```