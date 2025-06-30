# Sliding Window - CS Fundamentals

## Core Concept and Mathematical Foundation

### Definition
**Sliding Window Technique:** An algorithmic approach that maintains a "window" (subarray/substring) that slides over the input data structure, typically to solve optimization problems involving contiguous subsequences.

**Fundamental principle:** Instead of recalculating results for each possible subarray (O(n³) or O(n²)), maintain a window and update it incrementally in O(n) time.

**Mathematical insight:**
```
Brute force: Check all subarrays of size k
- Number of subarrays: n - k + 1  
- Cost per subarray: O(k)
- Total: O((n-k+1) × k) = O(nk)

Sliding window: Calculate first window, then slide
- Initial calculation: O(k)
- Sliding operations: O(n-k) updates, each O(1)
- Total: O(k) + O(n-k) = O(n)
```

### Why Sliding Window Works
**Key mathematical property:** Many problems have **incremental update property** where adding/removing one element from window can be computed efficiently.

**Example - Maximum sum subarray of size k:**
```
Instead of: sum([1,2,3]) = 6, sum([2,3,4]) = 9, sum([3,4,5]) = 12
Use: sum([1,2,3]) = 6, then 6 - 1 + 4 = 9, then 9 - 2 + 5 = 12
```

## Sliding Window Patterns

### Pattern 1: Fixed Size Window
**Setup:** Window size remains constant, slide one position at a time
```java
public resultType fixedWindow(int[] arr, int k) {
    // Calculate initial window
    for (int i = 0; i < k; i++) {
        // Process arr[i]
    }
    
    // Slide window
    for (int i = k; i < arr.length; i++) {
        // Remove arr[i-k] from window
        // Add arr[i] to window
        // Update result
    }
    
    return result;
}
```

**Time complexity:** O(n) where n = array length
**Space complexity:** O(1) or O(k) depending on what's stored

### Pattern 2: Variable Size Window (Expansion/Contraction)
**Setup:** Window size changes based on conditions
```java
public resultType variableWindow(int[] arr) {
    int left = 0;
    
    for (int right = 0; right < arr.length; right++) {
        // Expand window by including arr[right]
        
        // Contract window while condition violated
        while (windowInvalid()) {
            // Remove arr[left] from window
            left++;
        }
        
        // Update result with current valid window
    }
    
    return result;
}
```

**Key insight:** Right pointer always moves forward, left pointer moves forward only when necessary

### Pattern 3: Multiple Windows/Pointers
**Setup:** Track multiple windows simultaneously
```java
public resultType multipleWindows(int[] arr) {
    // Multiple window boundaries
    int start1 = 0, end1 = 0;
    int start2 = 0, end2 = 0;
    
    // Coordinate multiple windows
    while (condition) {
        // Update windows based on problem logic
        // May involve expanding/contracting different windows
    }
    
    return result;
}
```

## Detailed Algorithm Analysis

### 1. Maximum Sum Subarray of Size K (Fixed Window)
**Problem:** Find maximum sum of any contiguous subarray of size k

```java
public int maxSumSubarray(int[] nums, int k) {
    if (nums.length < k) return -1;
    
    // Calculate sum of first window
    int windowSum = 0;
    for (int i = 0; i < k; i++) {
        windowSum += nums[i];
    }
    
    int maxSum = windowSum;
    
    // Slide window and update sum
    for (int i = k; i < nums.length; i++) {
        windowSum = windowSum - nums[i - k] + nums[i];
        maxSum = Math.max(maxSum, windowSum);
    }
    
    return maxSum;
}
```

**Sliding operation analysis:**
```
Window [i-k+1, i-k+2, ..., i-1, i]
Next:   [i-k+2, i-k+3, ..., i,   i+1]

Update: new_sum = old_sum - nums[i-k+1] + nums[i+1]
```

**Correctness:** Every possible subarray of size k is examined exactly once

### 2. Longest Substring Without Repeating Characters (Variable Window)
**Problem:** Find length of longest substring with all unique characters

```java
public int lengthOfLongestSubstring(String s) {
    Set<Character> window = new HashSet<>();
    int left = 0, maxLength = 0;
    
    for (int right = 0; right < s.length(); right++) {
        char rightChar = s.charAt(right);
        
        // Contract window while duplicate exists
        while (window.contains(rightChar)) {
            window.remove(s.charAt(left));
            left++;
        }
        
        // Expand window
        window.add(rightChar);
        maxLength = Math.max(maxLength, right - left + 1);
    }
    
    return maxLength;
}
```

**Window invariant:** All characters in window [left, right] are unique

**Why this works:**
- When duplicate found, contract from left until duplicate removed
- This ensures we don't miss any valid longer substring
- Each character added/removed at most once → O(n) time

### 3. Minimum Window Substring (Variable Window)
**Problem:** Find minimum window in string s that contains all characters of string t

```java
public String minWindow(String s, String t) {
    Map<Character, Integer> targetCount = new HashMap<>();
    for (char c : t.toCharArray()) {
        targetCount.put(c, targetCount.getOrDefault(c, 0) + 1);
    }
    
    Map<Character, Integer> windowCount = new HashMap<>();
    int left = 0, minLen = Integer.MAX_VALUE, minStart = 0;
    int formed = 0, required = targetCount.size();
    
    for (int right = 0; right < s.length(); right++) {
        // Expand window
        char rightChar = s.charAt(right);
        windowCount.put(rightChar, windowCount.getOrDefault(rightChar, 0) + 1);
        
        if (targetCount.containsKey(rightChar) && 
            windowCount.get(rightChar).intValue() == targetCount.get(rightChar).intValue()) {
            formed++;
        }
        
        // Contract window while valid
        while (left <= right && formed == required) {
            // Update minimum window
            if (right - left + 1 < minLen) {
                minLen = right - left + 1;
                minStart = left;
            }
            
            char leftChar = s.charAt(left);
            windowCount.put(leftChar, windowCount.get(leftChar) - 1);
            if (targetCount.containsKey(leftChar) && 
                windowCount.get(leftChar) < targetCount.get(leftChar)) {
                formed--;
            }
            left++;
        }
    }
    
    return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLen);
}
```

**Complex window management:**
- **Expansion condition:** Always expand right
- **Contraction condition:** Contract left while window valid
- **State tracking:** Count of required characters satisfied

## Advanced Sliding Window Techniques

### 1. Sliding Window Maximum (Deque-based)
**Problem:** Find maximum element in each window of size k

```java
public int[] maxSlidingWindow(int[] nums, int k) {
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

**Deque invariant:** Contains indices in decreasing order of their values
**Key insight:** Elements smaller than current will never be maximum in future windows

### 2. Subarrays with K Different Integers
**Problem:** Count subarrays with exactly k distinct integers

```java
public int subarraysWithKDistinct(int[] nums, int k) {
    return atMostK(nums, k) - atMostK(nums, k - 1);
}

private int atMostK(int[] nums, int k) {
    Map<Integer, Integer> count = new HashMap<>();
    int left = 0, result = 0;
    
    for (int right = 0; right < nums.length; right++) {
        count.put(nums[right], count.getOrDefault(nums[right], 0) + 1);
        
        while (count.size() > k) {
            count.put(nums[left], count.get(nums[left]) - 1);
            if (count.get(nums[left]) == 0) {
                count.remove(nums[left]);
            }
            left++;
        }
        
        result += right - left + 1;  // All subarrays ending at right
    }
    
    return result;
}
```

**Mathematical insight:** exactly(k) = atMost(k) - atMost(k-1)
**Counting technique:** For each right position, count all valid subarrays ending there

### 3. Frequency-based Sliding Window
**Problem:** Find all anagrams of pattern p in string s

```java
public List<Integer> findAnagrams(String s, String p) {
    List<Integer> result = new ArrayList<>();
    if (s.length() < p.length()) return result;
    
    int[] pCount = new int[26];
    int[] windowCount = new int[26];
    
    // Initialize pattern frequency and first window
    for (int i = 0; i < p.length(); i++) {
        pCount[p.charAt(i) - 'a']++;
        windowCount[s.charAt(i) - 'a']++;
    }
    
    if (Arrays.equals(pCount, windowCount)) {
        result.add(0);
    }
    
    // Slide window
    for (int i = p.length(); i < s.length(); i++) {
        // Add new character
        windowCount[s.charAt(i) - 'a']++;
        
        // Remove old character
        windowCount[s.charAt(i - p.length()) - 'a']--;
        
        if (Arrays.equals(pCount, windowCount)) {
            result.add(i - p.length() + 1);
        }
    }
    
    return result;
}
```

**Frequency matching:** Compare frequency arrays instead of sorting

## Window State Management

### 1. State Representation
**Common state tracking approaches:**
```java
// Frequency counting
Map<Character, Integer> frequency = new HashMap<>();

// Set for uniqueness
Set<Character> uniqueChars = new HashSet<>();

// Custom state object
class WindowState {
    int sum;
    int maxElement;
    int distinctCount;
}

// Array for character frequencies (ASCII/Unicode)
int[] charCount = new int[26];  // or int[256], int[128]
```

### 2. State Update Operations
**Efficient state updates:**
```java
// Add element to window
void addToWindow(char c) {
    frequency.put(c, frequency.getOrDefault(c, 0) + 1);
    if (frequency.get(c) == 1) distinctCount++;
}

// Remove element from window  
void removeFromWindow(char c) {
    frequency.put(c, frequency.get(c) - 1);
    if (frequency.get(c) == 0) {
        frequency.remove(c);
        distinctCount--;
    }
}

// Check window validity
boolean isWindowValid() {
    return distinctCount <= k;  // Example condition
}
```

## Complexity Analysis Deep Dive

### Time Complexity Analysis
**Fixed window (size k):**
- Initial window calculation: O(k)
- Sliding operations: O(n-k) slides × O(1) per slide = O(n-k)
- **Total: O(k) + O(n-k) = O(n)**

**Variable window:**
- Each element added exactly once (right pointer): O(n)
- Each element removed at most once (left pointer): O(n)
- **Total: O(n) + O(n) = O(n)**

**With complex state (e.g., HashMap operations):**
- If state operations are O(1) amortized: Overall O(n)
- If state operations are O(log m): Overall O(n log m) where m = state size

### Space Complexity Patterns
| State Type | Space Complexity | Example |
|------------|-----------------|---------|
| Simple variables | O(1) | Sum, count, max |
| Fixed-size array | O(k) | Character frequency (26 letters) |
| Dynamic structures | O(m) | HashMap with m distinct elements |
| Window content | O(w) | Storing all elements in window |

### Amortized Analysis
**Why variable window is still O(n):**
```
Total pointer movements:
- Right pointer: moves from 0 to n-1 → n movements
- Left pointer: moves from 0 to at most n-1 → at most n movements
- Total movements: ≤ 2n → O(n)

Each movement does O(1) work → Total O(n)
```

## When Sliding Window Applies

### Problem Characteristics
1. **Contiguous subsequences:** Subarrays or substrings
2. **Optimization objective:** Maximum, minimum, count, existence
3. **Incremental property:** Adding/removing elements can be computed efficiently
4. **Monotonic property:** Some property that allows window expansion/contraction decisions

### Decision Framework
**Use fixed window when:**
- Problem specifies exact size k
- Need to examine all k-sized subarrays/substrings
- Examples: Max sum subarray of size k, sliding window average

**Use variable window when:**
- Find optimal size satisfying constraints
- Size varies based on content
- Examples: Longest substring with unique chars, minimum window containing pattern

**Use multiple windows when:**
- Complex constraints requiring multiple regions
- Comparing different parts of input
- Examples: Merge intervals, complex pattern matching

### Alternative Approaches Comparison
| Approach | Time | Space | When to Use |
|----------|------|-------|-------------|
| Brute force | O(n²) or O(n³) | O(1) | Small inputs, prototype |
| Sliding window | O(n) | O(k) | Contiguous subsequences |
| Two pointers | O(n) | O(1) | Sorted arrays, pairs |
| Prefix sum | O(n) | O(n) | Range queries, cumulative |

## Common Pitfalls and Interview Tips

### Common Mistakes
1. **Window boundary errors:** Off-by-one in window size calculation
2. **State inconsistency:** Forgetting to update state when sliding
3. **Infinite loops:** Incorrect contraction conditions in variable window
4. **Memory leaks:** Not removing elements from state structures
5. **Edge cases:** Empty input, window size larger than input

### Edge Cases to Handle
1. **Empty input:** Return appropriate default value
2. **Window size > input size:** Handle gracefully
3. **Single element:** Window operations on minimal input
4. **All elements same:** Avoid infinite expansion/contraction
5. **Invalid constraints:** Negative window size, impossible targets

### Problem Recognition Patterns
**Sliding window likely useful when:**
- Problem involves subarrays/substrings
- Looking for optimization (max/min/count)
- Mentions contiguous elements
- Brute force would be O(n²) or O(n³)
- Can incrementally update window state

### Interview Problem-Solving Strategy
1. **Identify window type:** Fixed vs variable size
2. **Define window state:** What information to maintain
3. **Determine sliding conditions:** When to expand/contract
4. **Handle edge cases:** Empty, oversized, boundary conditions
5. **Optimize state updates:** Ensure O(1) add/remove operations

### Classic Problem Categories

**Fixed window:**
- Maximum/minimum sum subarray of size k
- Average of subarrays of size k
- Sliding window maximum
- Find all anagrams

**Variable window:**
- Longest substring without repeating characters
- Minimum window substring
- Longest substring with at most k distinct characters
- Subarrays with product less than k

**Advanced patterns:**
- Sliding window median
- Substring with concatenation of all words
- Minimum size subarray sum
- Fruit into baskets

### Template for Sliding Window Problems
```java
public resultType slidingWindow(inputType input, int constraint) {
    // Handle edge cases
    if (input == null || input.length == 0) return defaultValue;
    
    // Initialize window state
    WindowState state = new WindowState();
    int left = 0;
    resultType result = initializeResult();
    
    // Sliding window main loop
    for (int right = 0; right < input.length; right++) {
        // Expand window - add input[right]
        state.add(input[right]);
        
        // Contract window while condition violated (variable window)
        while (windowInvalid(state, constraint)) {
            state.remove(input[left]);
            left++;
        }
        
        // Update result with current window
        result = updateResult(result, state, left, right);
    }
    
    return result;
}
```