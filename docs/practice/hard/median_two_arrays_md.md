# Median of Two Sorted Arrays (Hard)

## Problem Statement
**LeetCode 4 - Median of Two Sorted Arrays**

Given two sorted arrays `nums1` and `nums2` of size `m` and `n` respectively, return the median of the two sorted arrays.

**Constraints:**
- The overall run time complexity should be O(log (m+n))
- nums1.length == m, nums2.length == n
- 0 ≤ m ≤ 1000, 0 ≤ n ≤ 1000
- 1 ≤ m + n ≤ 2000
- -10⁶ ≤ nums1[i], nums2[i] ≤ 10⁶

## Examples
```
Input: nums1 = [1,3], nums2 = [2]
Output: 2.0
Explanation: merged = [1,2,3], median = 2

Input: nums1 = [1,2], nums2 = [3,4]  
Output: 2.5
Explanation: merged = [1,2,3,4], median = (2+3)/2 = 2.5
```

## Pattern Analysis
**Primary Pattern:** Binary Search on Answer Space  
**Key Insight:** Find correct partition of both arrays such that left half ≤ right half

### Core Mathematical Concept
For median of combined array:
- **Total elements = m + n**
- **Left partition size = (m + n + 1) / 2**
- **Median condition:** max(left) ≤ min(right)

## Solution Approaches

### Approach 1: Optimal Binary Search (Target: O(log(min(m,n))))
**Core Algorithm:**
1. Binary search on smaller array for partition point
2. Calculate corresponding partition in larger array
3. Check if partition satisfies median property
4. Adjust partition based on comparison

**Implementation Strategy:**
```
Partition arrays at positions cut1 and cut2:
nums1: [... maxLeft1] | [minRight1 ...]
nums2: [... maxLeft2] | [minRight2 ...]

Valid partition when:
- maxLeft1 ≤ minRight2
- maxLeft2 ≤ minRight1
- |left partition| = |right partition| (±1)
```

**Complexity Analysis:**
- **Time:** O(log(min(m,n))) - binary search on smaller array
- **Space:** O(1) - only constant variables used

**Why log(min(m,n))?** Binary search space is the smaller array's indices [0, m].

### Approach 2: Merge Until Median
**Algorithm:**
1. Merge arrays using two pointers
2. Stop when reaching median position(s)
3. Return appropriate median value

**Complexity:**
- **Time:** O((m+n)/2) = O(m+n)  
- **Space:** O(1)

**Trade-off:** Simple logic but doesn't meet O(log(m+n)) requirement.

### Approach 3: Complete Merge + Sort
**Algorithm:**
1. Combine both arrays
2. Sort combined array
3. Find median of sorted array

**Complexity:**
- **Time:** O((m+n)log(m+n))
- **Space:** O(m+n)

**Use case:** Educational/brute force baseline.

### Approach 4: Recursive Binary Search (Find Kth Element)
**Algorithm:**
1. Convert median problem to "find kth smallest element"
2. Use binary search to eliminate k/2 elements per iteration
3. Recursively solve smaller subproblem

**Complexity:**
- **Time:** O(log(m+n))
- **Space:** O(log(m+n)) due to recursion

## Deep Dive: Optimal Binary Search Algorithm

### Partition Logic
```
For arrays of total size n = m + len:
- Left partition should have (m + n + 1) / 2 elements
- If cut1 elements from nums1, then cut2 = (m + n + 1) / 2 - cut1 from nums2

Boundary elements:
- maxLeft1 = nums1[cut1-1] (or -∞ if cut1 = 0)
- minRight1 = nums1[cut1] (or +∞ if cut1 = m)
- maxLeft2 = nums2[cut2-1] (or -∞ if cut2 = 0)  
- minRight2 = nums2[cut2] (or +∞ if cut2 = n)
```

### Decision Tree
```
if maxLeft1 ≤ minRight2 && maxLeft2 ≤ minRight1:
    // Found correct partition
    if (m + n) is even:
        return (max(maxLeft1, maxLeft2) + min(minRight1, minRight2)) / 2
    else:
        return max(maxLeft1, maxLeft2)
        
elif maxLeft1 > minRight2:
    // Too many elements from nums1 in left partition
    right = cut1 - 1
    
else:
    // Too few elements from nums1 in left partition  
    left = cut1 + 1
```

### Edge Case Handling

#### Empty Arrays
- **One empty:** Reduce to single array median
- **Both empty:** Invalid input, throw exception

#### Size Mismatches  
- **Always search on smaller array** to minimize search space
- **Swap arrays if necessary** before processing

#### Boundary Conditions
- **cut1 = 0:** No elements from nums1 in left partition
- **cut1 = m:** All elements from nums1 in left partition  
- **cut2 = 0 or cut2 = n:** Handle similarly

## Algorithm Comparison

| Approach | Time | Space | Pros | Cons |
|----------|------|-------|------|------|
| **Binary Search** | O(log(min(m,n))) | O(1) | Optimal complexity | Complex implementation |
| **Merge Until Median** | O(m+n) | O(1) | Simple logic | Doesn't meet constraint |
| **Complete Merge** | O((m+n)log(m+n)) | O(m+n) | Easiest to understand | Inefficient |
| **Recursive K-th** | O(log(m+n)) | O(log(m+n)) | Clean recursion | Stack overhead |

## Implementation Challenges