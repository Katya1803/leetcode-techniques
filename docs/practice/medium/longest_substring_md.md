# Longest Substring Without Repeating Characters (Medium)

## Problem Statement
**LeetCode 3 - Longest Substring Without Repeating Characters**

Given a string `s`, find the length of the longest substring without repeating characters.

**Constraints:**
- 0 ≤ s.length ≤ 5 × 10⁴
- s consists of English letters, digits, symbols and spaces

## Examples
```
Input: s = "abcabcbb"
Output: 3
Explanation: "abc" has length 3

Input: s = "bbbbb"  
Output: 1
Explanation: "b" has length 1

Input: s = "pwwkew"
Output: 3
Explanation: "wke" has length 3
```

## Pattern Analysis
**Primary Pattern:** Sliding Window with Hash Table  
**Key Insight:** Maintain a window [left, right] containing unique characters. When duplicate found, shrink window from left.

### Core Algorithm Strategy
1. **Expand:** Always extend right boundary
2. **Contract:** Shrink left boundary when constraint violated (duplicate found)  
3. **Track:** Maintain maximum window size seen so far

## Solution Approaches

### Approach 1: Sliding Window with HashSet
**Algorithm:**
```
window = empty set
left = 0, maxLength = 0

for right = 0 to s.length-1:
    while s[right] in window:
        remove s[left] from window
        left++
    
    add s[right] to window
    maxLength = max(maxLength, right - left + 1)
```

**Complexity:**
- Time: O(n) - each character added and removed at most once
- Space: O(min(m,n)) where m = charset size

**Why O(n)?** Each character is visited at most twice (once by right pointer, once by left pointer).

### Approach 2: Optimized Sliding Window with HashMap  
**Key Optimization:** Instead of moving left pointer one by one, jump directly to position after duplicate.

**Algorithm:**
```
charToIndex = empty map
left = 0, maxLength = 0

for right = 0 to s.length-1:
    if s[right] in charToIndex and charToIndex[s[right]] >= left:
        left = charToIndex[s[right]] + 1
    
    charToIndex[s[right]] = right
    maxLength = max(maxLength, right - left + 1)
```

**Advantage:** Fewer iterations of the inner loop, better constant factors.

### Approach 3: Array-based (ASCII Only)
**Optimization for limited character set:**
- Use fixed-size array instead of HashMap
- Direct array access: O(1) guaranteed vs O(1) amortized
- Only works for ASCII (128) or extended ASCII (256)

**Trade-offs:**
- ✅ Slightly faster for ASCII inputs
- ✅ Predictable memory usage
- ❌ Limited to specific character sets
- ❌ Wastes space for small alphabets

### Approach 4: Brute Force
**Algorithm:** Check all possible substrings
- Time: O(n³) - O(n²) substrings × O(n) uniqueness check
- Space: O(min(m,n)) - for uniqueness checking

## Performance Comparison

| Approach | Time | Space | Best For |
|----------|------|-------|----------|
| HashSet Sliding Window | O(n) | O(min(m,n)) | General purpose |
| HashMap Optimized | O(n) | O(min(m,n)) | Fewer character revisits |
| Array-based | O(n) | O(1) | ASCII-only inputs |
| Brute Force | O(n³) | O(min(m,n)) | Very small inputs |

## Implementation Details

### Window Invariant
**Invariant:** substring s[left...right] contains all unique characters

**Maintenance:**
- When extending right: check if new character breaks invariant
- When invariant broken: contract left until invariant restored
- Track maximum window size throughout process

### Edge Case Handling
1. **Empty string:** Return 0
2. **Single character:** Return 1  
3. **All same characters:** Return 1
4. **All unique characters:** Return string length
5. **Unicode characters:** HashMap approaches work, array approach doesn't

## Advanced Variations

### Variation 1: Return Actual Substring
Modify algorithm to track starting position of longest substring:
```java
if (currentLength > maxLength) {
    maxLength = currentLength;
    resultStart = left;
}
return s.substring(resultStart, resultStart + maxLength);
```

### Variation 2: At Most K Distinct Characters
Generalize to allow at most K distinct characters:
```java
while (charCount.size() > k) {
    // Remove leftmost character
    char leftChar = s.charAt(left);
    charCount.put(leftChar, charCount.get(leftChar) - 1);
    if (charCount.get(leftChar) == 0) {
        charCount.remove(leftChar);
    }
    left++;
}
```

### Variation 3: Exactly K Distinct Characters  
```
exactly(k) = atMost(k) - atMost(k-1)
```

## Algorithm Selection Guide

| Input Characteristics | Recommended Approach |
|----------------------|---------------------|
| ASCII only, performance critical | Array-based |
| Unicode characters | HashMap optimized |
| Educational/interview | HashSet (clearer logic) |
| Very small strings (< 10) | Any approach |

## Common Mistakes
1. **Off-by-one errors** in window boundaries
2. **Not handling empty strings**
3. **Forgetting to update character positions** in HashMap approach
4. **Incorrect window shrinking logic**
5. **Not considering Unicode characters**

## Related Problems
- LC 159: Longest Substring with At Most Two Distinct Characters
- LC 340: Longest Substring with At Most K Distinct Characters  
- LC 992: Subarrays with K Different Integers
- LC 76: Minimum Window Substring
- LC 438: Find All Anagrams in a String

## Interview Tips
- **Start with brute force** to show understanding
- **Explain sliding window concept** clearly
- **Discuss character set assumptions** (ASCII vs Unicode)
- **Walk through examples** step by step
- **Analyze time complexity** carefully (why it's O(n) not O(n²))
- **Consider follow-up variations**

## Key Takeaways
1. **Sliding window** is powerful for substring problems
2. **Hash table** provides efficient duplicate detection
3. **Two-pointer technique** maintains window efficiently  
4. **Amortized analysis** explains O(n) time complexity
5. **Character set constraints** affect implementation choices