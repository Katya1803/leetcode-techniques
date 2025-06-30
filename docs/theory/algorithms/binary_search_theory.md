# Binary Search - CS Fundamentals

## Core Concept and Mathematical Foundation

### Definition
**Binary Search:** A divide-and-conquer algorithm that finds a target value in a sorted array by repeatedly dividing the search space in half, eliminating half of the remaining elements in each iteration.

**Fundamental principle:** Use the sorted property to make decisions that eliminate large portions of the search space.

**Mathematical foundation:**
```
Search space reduction per iteration: n → n/2 → n/4 → n/8 → ... → 1
Number of iterations: log₂(n)
Time complexity: O(log n)
```

### Why Binary Search Works
**Invariant:** If the target exists in the array, it must be within the current search range [left, right].

**Decision principle:** At each step, compare target with middle element:
- If target < middle: target must be in left half (if exists)
- If target > middle: target must be in right half (if exists)  
- If target = middle: found the target

**Correctness proof:**
1. **Initialization:** Target is in [0, n-1] if it exists
2. **Maintenance:** Each iteration maintains the invariant while reducing search space
3. **Termination:** Either target found or search space becomes empty

## Binary Search Variants and Templates

### Template 1: Standard Binary Search (Exact Match)
```java
public int binarySearch(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;  // Prevent overflow
        
        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    return -1;  // Not found
}
```

**Key points:**
- **Condition:** `left <= right` (inclusive bounds)
- **Update:** `left = mid + 1` or `right = mid - 1` (exclude mid)
- **Overflow prevention:** `mid = left + (right - left) / 2`

### Template 2: Left Boundary (First Occurrence)
```java
public int findFirst(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    int result = -1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) {
            result = mid;
            right = mid - 1;  // Continue searching left
        } else if (nums[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    return result;
}
```

**Alternative template (more general):**
```java
public int findLeftBoundary(int[] nums, int target) {
    int left = 0, right = nums.length;  // Note: right = length
    
    while (left < right) {  // Note: left < right (not <=)
        int mid = left + (right - left) / 2;
        
        if (nums[mid] < target) {
            left = mid + 1;
        } else {
            right = mid;  // Include mid in next iteration
        }
    }
    
    return left;  // Insertion point or first occurrence
}
```

### Template 3: Right Boundary (Last Occurrence)
```java
public int findLast(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    int result = -1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) {
            result = mid;
            left = mid + 1;  // Continue searching right
        } else if (nums[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    return result;
}
```

## Mathematical Analysis of Binary Search

### Logarithmic Time Complexity
**Recurrence relation:**
```
T(n) = T(n/2) + O(1)
```

**Master theorem application:**
- a = 1 (one subproblem)
- b = 2 (divide by 2)  
- f(n) = O(1) (constant work per level)
- **Result:** T(n) = O(log n)

**Iterative analysis:**
```
Iteration 0: search space = n
Iteration 1: search space = n/2
Iteration 2: search space = n/4
...
Iteration k: search space = n/2^k

Stop when n/2^k ≤ 1
Therefore: 2^k ≥ n
Taking log: k ≥ log₂(n)
```

### Search Space Analysis
**Discrete logarithm:** For array of size n, maximum iterations = ⌊log₂(n)⌋ + 1

**Examples:**
- n = 1: 1 iteration
- n = 2: 2 iterations  
- n = 4: 3 iterations
- n = 8: 4 iterations
- n = 1000: 10 iterations
- n = 1,000,000: 20 iterations

### Integer Overflow Prevention
**Problem:** `mid = (left + right) / 2` can overflow when left + right > Integer.MAX_VALUE

**Solution:** `mid = left + (right - left) / 2`

**Mathematical equivalence:**
```
left + (right - left) / 2 
= left + right/2 - left/2
= left/2 + right/2  
= (left + right) / 2
```

## Advanced Binary Search Applications

### 1. Search in Rotated Sorted Array
**Problem:** Array was sorted, then rotated at some pivot point

```java
public int searchRotated(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) {
            return mid;
        }
        
        // Check which half is sorted
        if (nums[left] <= nums[mid]) {  // Left half is sorted
            if (target >= nums[left] && target < nums[mid]) {
                right = mid - 1;  // Target in left half
            } else {
                left = mid + 1;   // Target in right half
            }
        } else {  // Right half is sorted
            if (target > nums[mid] && target <= nums[right]) {
                left = mid + 1;   // Target in right half
            } else {
                right = mid - 1;  // Target in left half
            }
        }
    }
    
    return -1;
}
```

**Key insight:** At least one half of a rotated sorted array is always sorted

### 2. Finding Peak Element
**Problem:** Find any peak element (element greater than its neighbors)

```java
public int findPeakElement(int[] nums) {
    int left = 0, right = nums.length - 1;
    
    while (left < right) {
        int mid = left + (right - left) / 2;
        
        if (nums[mid] > nums[mid + 1]) {
            right = mid;  // Peak is in left half (including mid)
        } else {
            left = mid + 1;  // Peak is in right half
        }
    }
    
    return left;
}
```

**Mathematical reasoning:**
- If `nums[mid] < nums[mid+1]`: slope is increasing, peak must be to the right
- If `nums[mid] > nums[mid+1]`: slope is decreasing, peak could be mid or to the left

### 3. Square Root (Integer)
**Problem:** Find integer square root of x

```java
public int mySqrt(int x) {
    if (x < 2) return x;
    
    long left = 2, right = x / 2;  // Use long to prevent overflow
    
    while (left <= right) {
        long mid = left + (right - left) / 2;
        long square = mid * mid;
        
        if (square == x) {
            return (int) mid;
        } else if (square < x) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    return (int) right;  // Largest integer whose square ≤ x
}
```

**Search space:** [2, x/2] since sqrt(x) ≤ x/2 for x ≥ 2

## Binary Search on Answer Space

### Concept
**Definition:** When the problem asks for an optimal value (minimum/maximum) that satisfies certain conditions, binary search on the range of possible answers.

**Template:**
```java
public int binarySearchOnAnswer(parameters) {
    int left = minPossibleAnswer;
    int right = maxPossibleAnswer;
    
    while (left < right) {
        int mid = left + (right - left) / 2;
        
        if (canAchieve(mid, parameters)) {
            right = mid;  // Try for better (smaller) answer
        } else {
            left = mid + 1;  // Need larger answer
        }
    }
    
    return left;
}

private boolean canAchieve(int answer, parameters) {
    // Check if this answer is feasible
    // Usually involves greedy or simulation
}
```

### 1. Koko Eating Bananas
**Problem:** Find minimum eating speed to finish all bananas within h hours

```java
public int minEatingSpeed(int[] piles, int h) {
    int left = 1;
    int right = Arrays.stream(piles).max().getAsInt();
    
    while (left < right) {
        int mid = left + (right - left) / 2;
        
        if (canFinish(piles, mid, h)) {
            right = mid;  // Try slower speed
        } else {
            left = mid + 1;  // Need faster speed
        }
    }
    
    return left;
}

private boolean canFinish(int[] piles, int speed, int h) {
    int hours = 0;
    for (int pile : piles) {
        hours += (pile + speed - 1) / speed;  // Ceiling division
    }
    return hours <= h;
}
```

**Answer space:** [1, max(piles)]
**Monotonic property:** If speed k works, then any speed > k also works

### 2. Capacity to Ship Packages
**Problem:** Find minimum ship capacity to ship all packages within days

```java
public int shipWithinDays(int[] weights, int days) {
    int left = Arrays.stream(weights).max().getAsInt();  // At least max weight
    int right = Arrays.stream(weights).sum();  // At most sum of all weights
    
    while (left < right) {
        int mid = left + (right - left) / 2;
        
        if (canShip(weights, mid, days)) {
            right = mid;
        } else {
            left = mid + 1;
        }
    }
    
    return left;
}

private boolean canShip(int[] weights, int capacity, int days) {
    int daysNeeded = 1;
    int currentWeight = 0;
    
    for (int weight : weights) {
        if (currentWeight + weight > capacity) {
            daysNeeded++;
            currentWeight = weight;
        } else {
            currentWeight += weight;
        }
    }
    
    return daysNeeded <= days;
}
```

## Invariants and Correctness

### Loop Invariants
**Standard binary search invariant:**
- If target exists in array, it must be in range [left, right]
- All elements before left are < target  
- All elements after right are > target

**Left boundary search invariant:**
- All elements in [0, left) are < target
- All elements in [right, n) are ≥ target
- Target's first occurrence (if exists) is in [left, right)

**Proof of correctness:**
1. **Initialization:** Invariant holds at start
2. **Maintenance:** Each iteration preserves invariant
3. **Termination:** When loop ends, invariant gives correct answer

### Boundary Analysis
**Why `left <= right` vs `left < right`?**

**Template 1 (`left <= right`):**
- Search space: [left, right] (inclusive)
- Updates exclude mid: `left = mid + 1`, `right = mid - 1`
- Terminates when left > right

**Template 2 (`left < right`):**
- Search space: [left, right) (right exclusive)
- Updates can include mid: `right = mid`
- Terminates when left == right

## Performance Characteristics

### Time Complexity Summary
| Operation | Complexity | Comparison |
|-----------|------------|------------|
| Binary search | O(log n) | vs O(n) linear search |
| Range queries | O(log n) | vs O(n) scan |
| Insert position | O(log n) | vs O(n) scan |
| Rotated search | O(log n) | vs O(n) scan |

### Space Complexity
**Iterative:** O(1) - only pointer variables
**Recursive:** O(log n) - call stack depth

```java
// Recursive binary search
public int binarySearchRecursive(int[] nums, int target, int left, int right) {
    if (left > right) return -1;
    
    int mid = left + (right - left) / 2;
    
    if (nums[mid] == target) {
        return mid;
    } else if (nums[mid] < target) {
        return binarySearchRecursive(nums, target, mid + 1, right);
    } else {
        return binarySearchRecursive(nums, target, left, mid - 1);
    }
}
```

### Cache Performance
**Binary search vs linear search:**
- **Binary search:** Random access pattern, poor cache locality
- **Linear search:** Sequential access, excellent cache locality
- **Crossover point:** For small arrays (< 100 elements), linear search can be faster

## Common Pitfalls and Interview Tips

### Common Mistakes
1. **Infinite loops:** Incorrect boundary updates
2. **Integer overflow:** Using `(left + right) / 2`
3. **Off-by-one errors:** Wrong boundary conditions
4. **Wrong template:** Using wrong variant for the problem
5. **Unsorted data:** Applying binary search to unsorted array

### Edge Cases to Handle
1. **Empty array:** Return appropriate default value
2. **Single element:** Ensure algorithm works correctly
3. **Target not found:** Return -1 or insertion point
4. **All elements same:** Handle duplicate scenarios
5. **Integer boundaries:** MIN_VALUE, MAX_VALUE edge cases

### Problem Recognition Patterns
**Binary search applicable when:**
- Data is sorted (or has monotonic property)
- Looking for exact value or boundary
- Need O(log n) search time
- Problem involves "find minimum/maximum value such that..."
- Can define a decision function (yes/no) that splits search space

### Interview Problem-Solving Strategy
1. **Identify search space:** What are we searching through?
2. **Define decision function:** How to eliminate half the space?
3. **Choose template:** Exact match, left boundary, right boundary?
4. **Handle edge cases:** Empty, not found, duplicates
5. **Verify correctness:** Walk through examples

### Classic Problem Categories

**Standard applications:**
- Search in sorted array
- Find first/last occurrence
- Search insert position
- Search in 2D matrix

**Rotated/modified arrays:**
- Search in rotated sorted array
- Find minimum in rotated array
- Search in mountain array

**Answer space search:**
- Koko eating bananas
- Capacity to ship packages
- Split array largest sum
- Find K-th smallest pair distance

**Mathematical applications:**
- Integer square root
- Perfect squares
- Nth root
- Find peak element

### Template Selection Guide
```java
// Use this decision tree:

if (looking for exact match) {
    use Template 1;  // Standard binary search
} else if (looking for first occurrence or left boundary) {
    use Template 2;  // Left boundary search
} else if (looking for last occurrence or right boundary) {
    use Template 3;  // Right boundary search
} else if (searching for optimal value with decision function) {
    use Answer Space template;
}
```

### Debugging Binary Search
**Common debugging techniques:**
1. **Trace through small examples:** Arrays of size 1, 2, 3
2. **Check invariants:** Verify invariant holds at each iteration
3. **Test boundaries:** What happens when target is first/last element?
4. **Verify termination:** Ensure left/right converge correctly
5. **Handle not found:** Return appropriate value when target absent