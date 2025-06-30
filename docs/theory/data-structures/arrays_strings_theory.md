# Arrays and Strings - CS Fundamentals

## Arrays: Core Properties and Analysis

### Mathematical Foundation of Array Access
**Why is array access O(1)?**
```
Array element address calculation:
address(arr[i]) = base_address + (i × element_size)

Example: int[] arr at address 1000
arr[0] → 1000 + (0 × 4) = 1000
arr[5] → 1000 + (5 × 4) = 1020
```

**Key insight:** This is simple arithmetic, independent of array size → O(1)

### Array vs Other Data Structures

| Property | Array | LinkedList | ArrayList |
|----------|-------|------------|-----------|
| Access by index | O(1) | O(n) | O(1) |
| Insert at end | N/A | O(1) | O(1) amortized |
| Insert at middle | O(n) | O(1)* | O(n) |
| Memory overhead | Minimal | High (pointers) | Minimal |
| Cache locality | Excellent | Poor | Excellent |

*\*Given reference to node*

### When to Choose Arrays
**Advantages:**
- Fast random access by index
- Memory efficient (no pointer overhead)
- Cache-friendly for sequential operations
- Simple and predictable performance

**Disadvantages:**
- Fixed size (in Java)
- Expensive insertion/deletion in middle
- Wasted space if not fully utilized

## Strings: Immutability and Performance

### String Immutability Principle
**Core concept:** In Java, String objects cannot be modified after creation

```java
String s = "Hello";
s = s + " World";  // Creates NEW string, doesn't modify original
```

**Why immutability matters:**
1. **Thread safety** - Multiple threads can read safely
2. **Caching** - Hash codes can be cached
3. **String pooling** - Memory optimization for literals
4. **Security** - Prevents accidental modification

### String Concatenation Analysis
**Problem:** Naive concatenation is O(n²)

```java
String result = "";
for (int i = 0; i < n; i++) {
    result += "a";  // Each += creates new string
}
```

**Complexity analysis:**
- Step 1: Copy 0 + copy 1 = 1 operation
- Step 2: Copy 1 + copy 1 = 2 operations  
- Step 3: Copy 2 + copy 1 = 3 operations
- ...
- Total: 1 + 2 + 3 + ... + n = n(n+1)/2 = O(n²)

**Solution:** StringBuilder with O(n) complexity
```java
StringBuilder sb = new StringBuilder();
for (int i = 0; i < n; i++) {
    sb.append("a");  // O(1) amortized
}
String result = sb.toString();  // O(n)
```

### StringBuilder vs StringBuffer
| Feature | StringBuilder | StringBuffer | String |
|---------|--------------|-------------|--------|
| Mutability | Mutable | Mutable | Immutable |
| Thread safety | No | Yes | Yes |
| Performance | Fastest | Slower (synchronization) | Slowest for concatenation |
| Use case | Single thread | Multi-thread | Simple operations |

## Fundamental Algorithms and Analysis

### Array Reversal
**In-place reversal using two pointers:**
```java
void reverse(int[] arr) {
    int left = 0, right = arr.length - 1;
    while (left < right) {
        swap(arr, left++, right--);
    }
}
```

**Analysis:**
- **Time:** O(n/2) = O(n)
- **Space:** O(1) - only using two variables
- **Invariant:** Elements outside [left, right] are in final position

### Duplicate Removal (Sorted Array)
**Two-pointer technique:**
```java
int removeDuplicates(int[] nums) {
    int writeIndex = 1;  // slow pointer
    for (int readIndex = 1; readIndex < nums.length; readIndex++) {  // fast pointer
        if (nums[readIndex] != nums[readIndex - 1]) {
            nums[writeIndex++] = nums[readIndex];
        }
    }
    return writeIndex;
}
```

**Invariant:** `nums[0...writeIndex-1]` contains unique elements in order

### Anagram Detection
**Approach 1: Sorting**
```java
boolean isAnagram(String s, String t) {
    char[] sArr = s.toCharArray();
    char[] tArr = t.toCharArray();
    Arrays.sort(sArr);  // O(n log n)
    Arrays.sort(tArr);  // O(n log n)
    return Arrays.equals(sArr, tArr);
}
```

**Approach 2: Frequency counting**
```java
boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) return false;
    
    int[] count = new int[26];  // Assuming lowercase English letters
    for (int i = 0; i < s.length(); i++) {
        count[s.charAt(i) - 'a']++;
        count[t.charAt(i) - 'a']--;
    }
    
    for (int c : count) {
        if (c != 0) return false;
    }
    return true;
}
```

**Trade-off analysis:**
- Sorting: O(n log n) time, O(1) extra space
- Frequency: O(n) time, O(k) space where k = alphabet size

## Character and String Processing

### ASCII vs Unicode Considerations
**ASCII (0-127):** English alphabet, digits, basic symbols
```java
char c = 'A';  // ASCII value 65
int index = c - 'A';  // Convert to 0-based index (0-25)
```

**Unicode:** International characters
```java
String emoji = "👋";  // May require multiple char units in Java
// Be careful with .length() and .charAt() for Unicode
```

**Interview tip:** Always clarify character set constraints!

### Common String Patterns

**1. Palindrome checking:**
```java
boolean isPalindrome(String s) {
    int left = 0, right = s.length() - 1;
    while (left < right) {
        // Skip non-alphanumeric characters
        while (left < right && !Character.isLetterOrDigit(s.charAt(left))) left++;
        while (left < right && !Character.isLetterOrDigit(s.charAt(right))) right--;
        
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

**2. Pattern matching (basic):**
```java
int indexOf(String haystack, String needle) {
    for (int i = 0; i <= haystack.length() - needle.length(); i++) {
        if (haystack.substring(i, i + needle.length()).equals(needle)) {
            return i;
        }
    }
    return -1;
}
```

## Complexity Analysis Fundamentals

### Big-O for Common Operations

**Array operations:**
- Access: O(1)
- Search (unsorted): O(n)
- Search (sorted): O(log n) with binary search
- Insert at end: O(1) if space available
- Insert at position: O(n) due to shifting
- Delete: O(n) due to shifting

**String operations:**
- Character access: O(1)
- Substring: O(k) where k = substring length
- Concatenation: O(n + m) for new string creation
- Search: O(n×m) naive, O(n) with KMP

### Space Complexity Patterns

**In-place algorithms:** O(1) extra space
- Array reversal, rotation
- Two-pointer techniques
- Some sorting algorithms

**Linear space algorithms:** O(n) extra space
- String building with StringBuilder
- Frequency counting with arrays/hashmaps
- Most recursive algorithms (call stack)

## Common Pitfalls and Interview Tips

### Array Pitfalls
1. **Index out of bounds:** Always check array length
2. **Off-by-one errors:** Careful with loop conditions
3. **Integer overflow:** Sum of integers might overflow
4. **Null arrays:** Handle null input gracefully

### String Pitfalls
1. **Immutability confusion:** Remember strings are immutable
2. **Character encoding:** Clarify ASCII vs Unicode
3. **Case sensitivity:** Ask if case matters
4. **Empty/null strings:** Handle edge cases

### Problem-Solving Approach
1. **Clarify constraints:** Array size, character set, mutability
2. **Identify pattern:** Two pointers, sliding window, etc.
3. **Start simple:** Brute force first, then optimize
4. **Test edge cases:** Empty, single element, all same
5. **Analyze complexity:** Time and space trade-offs

### When to Use What

**Use arrays when:**
- Need fast random access
- Memory efficiency is important
- Working with numerical data
- Size is known and fixed

**Use StringBuilder when:**
- Building strings incrementally
- Multiple concatenations needed
- Performance is critical

**Use String when:**
- Simple, infrequent operations
- Immutability is desired
- Thread safety needed without synchronization