# Two Sum (Easy)

## Problem Statement
**LeetCode 1 - Two Sum**

Given an array of integers `nums` and an integer `target`, return indices of the two numbers such that they add up to target.

**Constraints:**
- You may assume that each input would have exactly one solution
- You may not use the same element twice
- 2 ≤ nums.length ≤ 10⁴
- -10⁹ ≤ nums[i] ≤ 10⁹
- -10⁹ ≤ target ≤ 10⁹

## Examples
```
Input: nums = [2,7,11,15], target = 9
Output: [0,1]
Explanation: nums[0] + nums[1] = 2 + 7 = 9

Input: nums = [3,2,4], target = 6
Output: [1,2]

Input: nums = [3,3], target = 6
Output: [0,1]
```

## Pattern Analysis
**Primary Pattern:** Hash Table Complement Lookup  
**Secondary Patterns:** Two Pointers (if sorted), Brute Force

### Key Insight
Transform "find two numbers that sum to target" into "for each number x, find (target - x)". Hash table provides O(1) lookup for the complement.

## Solution Approaches

### Approach 1: Hash Map (Optimal)
**Algorithm:**
1. Iterate through array once
2. For each element, calculate complement = target - element
3. Check if complement exists in hash map
4. If yes, return indices; if no, store current element

**Complexity:**
- Time: O(n) - single pass through array
- Space: O(n) - hash map storage

**Trade-offs:**
- ✅ Optimal time complexity
- ✅ Single pass algorithm
- ❌ Uses extra space

### Approach 2: Brute Force
**Algorithm:**
1. For each element, check all subsequent elements
2. Return indices when sum equals target

**Complexity:**
- Time: O(n²) - nested loops
- Space: O(1) - no extra space

**Trade-offs:**
- ✅ Minimal space usage
- ❌ Poor time complexity
- ❌ Not scalable for large inputs

### Approach 3: Two Pointers (Modified)
**Algorithm:**
1. Create pairs of (value, original_index)
2. Sort pairs by value
3. Use two pointers technique
4. Return original indices

**Complexity:**
- Time: O(n log n) - sorting dominates
- Space: O(n) - storing value-index pairs

**Trade-offs:**
- ✅ Good for follow-up variations
- ❌ Sorting overhead
- ❌ More complex implementation

## Algorithm Selection Guide

| Array Size | Recommended Approach | Reason |
|------------|---------------------|---------|
| < 100 | Any approach works | Performance difference negligible |
| 100 - 10,000 | Hash Map | Clear performance advantage |
| > 10,000 | Hash Map | O(n²) becomes impractical |

## Edge Cases
1. **Minimum size array** [a, b] where a + b = target
2. **Negative numbers** - algorithm handles naturally
3. **Duplicate values** - use each element at most once
4. **Zero values** - treat like any other number
5. **Large numbers** - watch for integer overflow in sum

## Follow-up Variations
1. **Two Sum II - Sorted Array**: Use two pointers directly
2. **Two Sum III - Data Structure Design**: Optimize for multiple queries
3. **3Sum**: Extend to three numbers
4. **Two Sum - Count pairs**: Return count instead of indices
5. **Two Sum - All pairs**: Return all valid index pairs

## Implementation Notes
- Hash map collision handling is automatic in Java HashMap
- Consider TreeMap if you need sorted order of seen elements
- For very large arrays, consider memory usage of hash map
- Integer overflow: use `long` for intermediate calculations if needed

## Related Problems
- LC 167: Two Sum II - Input array is sorted
- LC 15: 3Sum  
- LC 18: 4Sum
- LC 454: 4Sum II
- LC 1: Two Sum (this problem)

## Interview Tips
- Start with brute force to show understanding
- Explain the complement insight clearly
- Discuss space-time tradeoffs
- Consider asking about input constraints
- Mention follow-up variations to show depth of understanding