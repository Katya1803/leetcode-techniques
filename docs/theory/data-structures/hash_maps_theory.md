# Hash Maps - CS Fundamentals

## Core Concept and Mathematical Foundation

### What is a Hash Map?
**Definition:** A data structure that maps keys to values using a hash function to compute array indices.

**Basic principle:**
```
hash_function(key) → index → array[index] = value
```

**Example:**
```java
Map<String, Integer> map = new HashMap<>();
map.put("apple", 5);  // hash("apple") → index 3 → array[3] = 5
map.put("banana", 8); // hash("banana") → index 7 → array[7] = 8
```

### Hash Function Properties
**Essential properties for a good hash function:**

1. **Deterministic:** Same input always produces same output
2. **Uniform distribution:** Spreads keys evenly across array
3. **Fast computation:** O(1) time to compute hash
4. **Avalanche effect:** Small input changes → large output changes

**Simple hash function example:**
```java
int simpleHash(String key, int tableSize) {
    int hash = 0;
    for (char c : key.toCharArray()) {
        hash = (hash * 31 + c) % tableSize;
    }
    return Math.abs(hash);
}
```

## Complexity Analysis

### Time Complexity
| Operation | Average Case | Worst Case | Best Case |
|-----------|-------------|------------|-----------|
| get(key) | O(1) | O(n) | O(1) |
| put(key, value) | O(1) | O(n) | O(1) |
| remove(key) | O(1) | O(n) | O(1) |

**Why average case is O(1)?**
- Good hash function distributes keys uniformly
- Each bucket has expected load of α = n/m (load factor)
- With proper load factor (< 0.75), expected bucket size ≈ 1

**Why worst case is O(n)?**
- All keys hash to same bucket (hash collision)
- Degenerates to linear search in that bucket
- Rare with good hash function and reasonable load factor

### Space Complexity
**Space usage:** O(n + m) where n = number of entries, m = table size
- n for storing key-value pairs
- m for the underlying array (buckets)
- Load factor α = n/m typically kept < 0.75

## Collision Handling Strategies

### 1. Separate Chaining (Java HashMap approach)
**Concept:** Each bucket contains a list of key-value pairs

```
Bucket array:
[0] → null
[1] → (key1, val1) → (key3, val3) → null
[2] → (key2, val2) → null
[3] → null
```

**Implementation details:**
```java
class HashMapBucket {
    List<Entry> bucket = new LinkedList<>();  // or ArrayList
    
    void put(Key key, Value value) {
        for (Entry entry : bucket) {
            if (entry.key.equals(key)) {
                entry.value = value;  // Update existing
                return;
            }
        }
        bucket.add(new Entry(key, value));  // Add new
    }
}
```

**Advantages:**
- Simple implementation
- No clustering problems
- Can store more items than table size

**Disadvantages:**
- Extra memory for pointers
- Poor cache locality for long chains

### 2. Open Addressing (Probing)
**Concept:** Find next available slot when collision occurs

**Linear probing:**
```java
int probe(int hash, int attempt, int tableSize) {
    return (hash + attempt) % tableSize;
}
```

**Quadratic probing:**
```java
int probe(int hash, int attempt, int tableSize) {
    return (hash + attempt * attempt) % tableSize;
}
```

**Double hashing:**
```java
int probe(int hash, int attempt, int tableSize) {
    int stepSize = 7 - (hash % 7);  // Secondary hash function
    return (hash + attempt * stepSize) % tableSize;
}
```

## Load Factor and Performance

### Load Factor Definition
```
Load Factor (α) = Number of entries / Table size = n / m
```

**Critical thresholds:**
- α < 0.5: Excellent performance, some wasted space
- α ≈ 0.75: Good balance (Java HashMap default)
- α > 0.9: Performance degradation, many collisions

### Resize Operation
**When to resize:** Typically when α > 0.75

**Resize process:**
1. Create new table (usually 2× size)
2. Rehash all existing entries
3. Insert into new table
4. Update table reference

**Complexity analysis:**
```java
// Resize operation
void resize() {
    Entry[] oldTable = table;
    table = new Entry[oldTable.length * 2];  // Double size
    
    for (Entry entry : oldTable) {
        if (entry != null) {
            rehash(entry);  // O(1) per entry
        }
    }
}
```

**Amortized analysis:**
- Resize happens every n/2 insertions (when doubling)
- Cost of resize: O(n)
- Amortized cost per insertion: O(n)/(n/2) = O(1)

## Hash Map vs Other Data Structures

### Performance Comparison
| Data Structure | Search | Insert | Delete | Ordered | Space |
|----------------|--------|--------|--------|---------|-------|
| HashMap | O(1)avg | O(1)avg | O(1)avg | No | O(n) |
| TreeMap | O(log n) | O(log n) | O(log n) | Yes | O(n) |
| Array (unsorted) | O(n) | O(1) | O(n) | No | O(n) |
| Array (sorted) | O(log n) | O(n) | O(n) | Yes | O(n) |

### When to Use Each

**Use HashMap when:**
- Need fast lookups, insertions, deletions
- Order doesn't matter
- Keys have good hash function
- Memory usage is acceptable

**Use TreeMap when:**
- Need sorted order
- Range queries required
- Consistent O(log n) performance needed
- Memory usage is critical

**Use Array when:**
- Keys are small integers (direct indexing)
- Maximum performance needed
- Memory usage is critical

## Common Hash Map Patterns

### 1. Frequency Counting
```java
Map<Character, Integer> frequency = new HashMap<>();
for (char c : string.toCharArray()) {
    frequency.put(c, frequency.getOrDefault(c, 0) + 1);
}
```

**Applications:**
- Character frequency in strings
- Element counting in arrays
- Finding duplicates
- Most/least frequent elements

### 2. Two Sum Pattern
```java
Map<Integer, Integer> seen = new HashMap<>();
for (int i = 0; i < nums.length; i++) {
    int complement = target - nums[i];
    if (seen.containsKey(complement)) {
        return new int[]{seen.get(complement), i};
    }
    seen.put(nums[i], i);
}
```

**Key insight:** Use hash map to store "what we've seen" for O(1) lookup

### 3. Grouping Pattern
```java
Map<String, List<String>> groups = new HashMap<>();
for (String item : items) {
    String key = computeKey(item);  // e.g., sorted chars for anagrams
    groups.computeIfAbsent(key, k -> new ArrayList<>()).add(item);
}
```

**Applications:**
- Group anagrams
- Group by property
- Partition elements

### 4. Memoization Pattern
```java
Map<String, Integer> memo = new HashMap<>();

public int expensiveFunction(String input) {
    if (memo.containsKey(input)) {
        return memo.get(input);  // O(1) cached result
    }
    
    int result = computeExpensiveResult(input);  // O(expensive)
    memo.put(input, result);
    return result;
}
```

**Trade-off:** Space for time - store computed results for faster retrieval

## Advanced Concepts

### Hash Code Implementation
**Java Object.hashCode() contract:**
1. Consistent: Multiple calls return same value
2. Equal objects have equal hash codes
3. Unequal objects should have different hash codes (ideal)

**Good hash code example:**
```java
class Person {
    String name;
    int age;
    
    @Override
    public int hashCode() {
        return Objects.hash(name, age);  // Combines multiple fields
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return age == person.age && Objects.equals(name, person.name);
    }
}
```

**Why both hashCode() and equals() matter:**
- hashCode() determines bucket
- equals() handles collisions within bucket

### Thread Safety Considerations
**HashMap is NOT thread-safe:**
```java
// Problematic in multi-threaded environment
Map<String, Integer> map = new HashMap<>();
// Multiple threads calling put/get can cause issues
```

**Thread-safe alternatives:**
1. **ConcurrentHashMap:** Segment-based locking
2. **Collections.synchronizedMap():** Method-level synchronization
3. **External synchronization:** Manual locking

## Common Pitfalls and Interview Tips

### Pitfalls to Avoid
1. **Forgetting to override both hashCode() and equals()**
2. **Using mutable objects as keys**
3. **Not handling null keys/values appropriately**
4. **Assuming insertion order is preserved** (use LinkedHashMap if needed)
5. **Not considering thread safety in concurrent environments**

### Interview Problem-Solving Approach
1. **Identify if hash map is appropriate:**
   - Need fast lookups?
   - Key-value relationship?
   - Frequency counting?

2. **Consider edge cases:**
   - Empty input
   - Null keys/values
   - Duplicate keys
   - Large datasets

3. **Analyze trade-offs:**
   - Time vs space complexity
   - Hash map vs other data structures
   - Custom hash function needs

### Performance Optimization Tips
1. **Choose appropriate initial capacity** to avoid resizing
2. **Use primitive collections** (TIntIntHashMap) for primitive types
3. **Consider memory usage** for large datasets
4. **Profile hash function quality** if performance is critical

### Common Interview Questions
- "Implement a hash map from scratch"
- "Why is HashMap O(1) average but O(n) worst case?"
- "How would you handle collisions?"
- "What happens when load factor is too high?"
- "Difference between HashMap and HashTable?"

## Mathematical Analysis

### Birthday Paradox and Collisions
**Probability of collision** in hash table with m slots and n keys:
```
P(collision) ≈ 1 - e^(-n²/2m)
```

**Example:** 23 people, 365 days → 50% chance of collision
**Hash table:** √m keys → 50% chance of collision

**Implication:** Even with good hash function, collisions are inevitable as table fills up.

### Expected Probe Distance (Open Addressing)
**Linear probing with load factor α:**
```
Expected probes = (1 + 1/(1-α)²) / 2
```

**Examples:**
- α = 0.5 → 1.5 probes average
- α = 0.75 → 2.5 probes average  
- α = 0.9 → 5.5 probes average

**Key insight:** Performance degrades rapidly as load factor approaches 1.0