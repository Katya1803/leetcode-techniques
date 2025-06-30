# Time and Space Complexity Analysis - CS Fundamentals

## Mathematical Foundation of Complexity Analysis

### Asymptotic Notation
**Purpose:** Describe algorithm performance as input size grows to infinity, focusing on growth rate rather than exact values.

#### Big-O Notation (Upper Bound)
**Definition:** f(n) = O(g(n)) if there exist positive constants c and n₀ such that:
```
f(n) ≤ c × g(n) for all n ≥ n₀
```

**Mathematical interpretation:** g(n) is an asymptotic upper bound for f(n)

**Examples:**
- 3n² + 2n + 1 = O(n²) because 3n² + 2n + 1 ≤ 4n² for n ≥ 2
- 100n = O(n²) but this is not tight
- log₂(n) = O(log(n)) regardless of base due to change of base formula

#### Big-Ω Notation (Lower Bound)
**Definition:** f(n) = Ω(g(n)) if there exist positive constants c and n₀ such that:
```
f(n) ≥ c × g(n) for all n ≥ n₀
```

**Use case:** Establishing best-case performance or proving impossibility results

#### Big-Θ Notation (Tight Bound)
**Definition:** f(n) = Θ(g(n)) if f(n) = O(g(n)) and f(n) = Ω(g(n))

**Mathematical meaning:** g(n) grows at exactly the same rate as f(n)

**Example:** Merge sort is Θ(n log n) - both upper and lower bound

### Little-o and Little-ω Notations
**Little-o:** f(n) = o(g(n)) means lim(n→∞) f(n)/g(n) = 0
**Little-ω:** f(n) = ω(g(n)) means lim(n→∞) f(n)/g(n) = ∞

**Practical meaning:** Strictly smaller/larger growth rates

## Common Complexity Classes

### Polynomial Time Complexities

#### O(1) - Constant Time
**Characteristics:**
- Independent of input size
- Most efficient possible
- Finite number of operations regardless of n

**Examples:**
```java
// Array access
int value = array[index];  // O(1)

// Hash map operations (average case)
map.get(key);  // O(1) average
map.put(key, value);  // O(1) average

// Arithmetic operations
int sum = a + b;  // O(1)

// Stack/Queue operations
stack.push(item);  // O(1)
queue.offer(item);  // O(1)
```

**Mathematical insight:** O(1) doesn't mean exactly one operation, but bounded by a constant

#### O(log n) - Logarithmic Time
**Characteristics:**
- Eliminates half of remaining possibilities each step
- Very efficient for large inputs
- Common in divide-and-conquer algorithms

**Examples:**
```java
// Binary search
public int binarySearch(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (arr[mid] == target) return mid;
        else if (arr[mid] < target) left = mid + 1;
        else right = mid - 1;
    }
    return -1;
}

// Balanced tree operations
TreeMap.get();  // O(log n)
TreeSet.contains();  // O(log n)

// Finding height of complete binary tree
int height = (int) Math.ceil(Math.log(n + 1) / Math.log(2));
```

**Mathematical analysis:**
- Each iteration reduces search space by half
- After k iterations: n/2^k elements remain
- When n/2^k = 1, we have k = log₂(n) iterations

#### O(n) - Linear Time
**Characteristics:**
- Single pass through input
- Most algorithms need at least O(n) to read all input
- Optimal for problems requiring examination of all elements

**Examples:**
```java
// Array traversal
public int findMax(int[] arr) {
    int max = arr[0];
    for (int i = 1; i < arr.length; i++) {
        max = Math.max(max, arr[i]);
    }
    return max;
}

// String operations
public boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) return false;
    int[] count = new int[26];
    for (int i = 0; i < s.length(); i++) {
        count[s.charAt(i) - 'a']++;
        count[t.charAt(i) - 'a']--;
    }
    return Arrays.stream(count).allMatch(c -> c == 0);
}
```

#### O(n log n) - Linearithmic Time
**Characteristics:**
- Optimal for comparison-based sorting
- Common in divide-and-conquer algorithms
- Slightly worse than linear but much better than quadratic

**Examples:**
```java
// Merge sort
public void mergeSort(int[] arr, int left, int right) {
    if (left < right) {
        int mid = left + (right - left) / 2;
        mergeSort(arr, left, mid);           // T(n/2)
        mergeSort(arr, mid + 1, right);      // T(n/2)
        merge(arr, left, mid, right);        // O(n)
    }
}
// Recurrence: T(n) = 2T(n/2) + O(n) = O(n log n)

// Heap sort, quick sort (average case)
Arrays.sort(arr);  // O(n log n)

// Building suffix array with comparison-based sorting
```

**Mathematical justification:**
- Master theorem: T(n) = 2T(n/2) + O(n)
- a = 2, b = 2, f(n) = O(n)
- log_b(a) = 1, so f(n) = Θ(n^1) = Θ(n^log_b(a))
- Therefore T(n) = Θ(n log n)

#### O(n²) - Quadratic Time
**Characteristics:**
- Nested loops over input
- Common in brute force algorithms
- Becomes impractical for large inputs

**Examples:**
```java
// Bubble sort
public void bubbleSort(int[] arr) {
    for (int i = 0; i < arr.length - 1; i++) {          // n iterations
        for (int j = 0; j < arr.length - 1 - i; j++) {  // ~n iterations
            if (arr[j] > arr[j + 1]) {
                swap(arr, j, j + 1);
            }
        }
    }
}
// Total: n × n = O(n²)

// Finding all pairs
public List<int[]> findAllPairs(int[] arr) {
    List<int[]> pairs = new ArrayList<>();
    for (int i = 0; i < arr.length; i++) {
        for (int j = i + 1; j < arr.length; j++) {
            pairs.add(new int[]{arr[i], arr[j]});
        }
    }
    return pairs;
}
// Exactly C(n,2) = n(n-1)/2 = O(n²) pairs
```

### Exponential and Factorial Complexities

#### O(2^n) - Exponential Time
**Characteristics:**
- Doubles with each additional input element
- Common in exhaustive search problems
- Quickly becomes intractable

**Examples:**
```java
// Generating all subsets
public List<List<Integer>> subsets(int[] nums) {
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
    
    // Two choices for each element: include or exclude
    generateSubsets(nums, index + 1, current, result);  // Exclude
    
    current.add(nums[index]);
    generateSubsets(nums, index + 1, current, result);  // Include
    current.remove(current.size() - 1);
}
// T(n) = 2T(n-1) + O(1) = O(2^n)

// Fibonacci (naive recursive)
public int fibonacci(int n) {
    if (n <= 1) return n;
    return fibonacci(n - 1) + fibonacci(n - 2);  // Two recursive calls
}
// T(n) = T(n-1) + T(n-2) + O(1) = O(2^n)
```

#### O(n!) - Factorial Time
**Characteristics:**
- All permutations or arrangements
- Extremely large growth rate
- Only feasible for very small inputs

**Examples:**
```java
// Generating all permutations
public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    generatePermutations(nums, new ArrayList<>(), new boolean[nums.length], result);
    return result;
}

private void generatePermutations(int[] nums, List<Integer> current, 
                                boolean[] used, List<List<Integer>> result) {
    if (current.size() == nums.length) {
        result.add(new ArrayList<>(current));
        return;
    }
    
    for (int i = 0; i < nums.length; i++) {  // n choices for first, n-1 for second, etc.
        if (!used[i]) {
            current.add(nums[i]);
            used[i] = true;
            generatePermutations(nums, current, used, result);
            current.remove(current.size() - 1);
            used[i] = false;
        }
    }
}
// n × (n-1) × (n-2) × ... × 1 = n!

// Traveling Salesman Problem (brute force)
public int tspBruteForce(int[][] dist) {
    // Try all possible routes: (n-1)! permutations
    return permutations_and_calculate_min_cost(); // O(n!)
}
```

## Space Complexity Analysis

### Memory Types in Algorithm Analysis

#### Auxiliary Space vs Total Space
**Auxiliary Space:** Extra space used by algorithm (excluding input)
**Total Space Complexity:** Auxiliary space + input space

**Example:**
```java
// Merge sort space analysis
public void mergeSort(int[] arr, int left, int right) {
    if (left < right) {
        int mid = left + (right - left) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);  // Creates temporary array of size O(n)
    }
}
// Auxiliary space: O(n) for temporary arrays
// Total space: O(n) input + O(n) auxiliary = O(n)
```

#### Call Stack Space
**Recursive algorithms:** Each recursive call uses stack space

**Example:**
```java
// DFS traversal space analysis
public void dfs(TreeNode root) {
    if (root == null) return;
    
    process(root);
    dfs(root.left);   // Stack frame
    dfs(root.right);  // Stack frame
}
// Space complexity: O(h) where h = height of tree
// Best case (balanced): O(log n)
// Worst case (skewed): O(n)
```

### Space Optimization Techniques

#### In-place Algorithms
**Definition:** Use O(1) extra space (excluding input)

**Examples:**
```java
// In-place array reversal
public void reverse(int[] arr) {
    int left = 0, right = arr.length - 1;
    while (left < right) {
        // Swap without extra array
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
        left++;
        right--;
    }
}
// Space: O(1) auxiliary

// Quick sort (in-place)
public void quickSort(int[] arr, int low, int high) {
    if (low < high) {
        int pi = partition(arr, low, high);  // O(1) space
        quickSort(arr, low, pi - 1);
        quickSort(arr, pi + 1, high);
    }
}
// Space: O(log n) average case due to recursion stack
```

#### Space-Time Tradeoffs
**Memoization:** Trade space for time

**Example:**
```java
// Fibonacci with memoization
private Map<Integer, Integer> memo = new HashMap<>();

public int fibonacciMemo(int n) {
    if (n <= 1) return n;
    
    if (memo.containsKey(n)) {
        return memo.get(n);  // O(1) time, O(n) space
    }
    
    int result = fibonacciMemo(n - 1) + fibonacciMemo(n - 2);
    memo.put(n, result);
    return result;
}
// Time: O(n), Space: O(n)
// Without memoization: Time: O(2^n), Space: O(n)
```

## Amortized Analysis

### Concept
**Amortized Analysis:** Average cost per operation over a sequence of operations, where some operations may be expensive but are infrequent.

### Dynamic Array Resizing
**Problem:** ArrayList doubling strategy

**Analysis:**
```java
class DynamicArray {
    private int[] array;
    private int size;
    private int capacity;
    
    public void add(int element) {
        if (size == capacity) {
            resize();  // Expensive: O(n)
        }
        array[size++] = element;  // Cheap: O(1)
    }
    
    private void resize() {
        capacity *= 2;
        int[] newArray = new int[capacity];
        System.arraycopy(array, 0, newArray, 0, size);  // O(n)
        array = newArray;
    }
}
```

**Amortized Cost Calculation:**
- Resize operations occur at sizes: 1, 2, 4, 8, 16, ..., n
- Cost of resizes: 1 + 2 + 4 + 8 + ... + n = 2n - 1
- Total cost for n insertions: n (regular) + (2n - 1) (resize) = 3n - 1
- Amortized cost per insertion: (3n - 1)/n = O(1)

### Union-Find with Path Compression
**Inverse Ackermann Function α(n):**
```java
class UnionFind {
    private int[] parent;
    
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);  // Path compression
        }
        return parent[x];
    }
}
```

**Analysis:** Sequence of m operations on n elements takes O(m α(n)) time
- α(n) ≤ 4 for all practical values of n
- Amortized time per operation: O(α(n)) ≈ O(1)

## Practical Complexity Analysis

### Algorithm Comparison by Input Size

| n | O(1) | O(log n) | O(n) | O(n log n) | O(n²) | O(2^n) | O(n!) |
|---|------|----------|------|------------|-------|--------|-------|
| 10 | 1 | 3 | 10 | 33 | 100 | 1,024 | 3.6M |
| 100 | 1 | 7 | 100 | 664 | 10,000 | 1.3×10³⁰ | 9.3×10¹⁵⁷ |
| 1,000 | 1 | 10 | 1,000 | 9,966 | 1,000,000 | ∞ | ∞ |
| 10,000 | 1 | 13 | 10,000 | 132,877 | 100,000,000 | ∞ | ∞ |

### Performance Guidelines by Constraints

**Competitive Programming Rules:**
- **n ≤ 12:** O(n!) might work
- **n ≤ 25:** O(2^n) might work  
- **n ≤ 100:** O(n³) should work
- **n ≤ 1,000:** O(n²) should work
- **n ≤ 100,000:** O(n log n) should work
- **n ≤ 1,000,000:** O(n) should work
- **n > 1,000,000:** Need O(log n) or O(1)

### Memory Constraints
**Typical limits:**
- **32-bit integer:** 4 bytes
- **64-bit integer:** 8 bytes
- **Reference/pointer:** 8 bytes (64-bit JVM)
- **Array overhead:** ~16 bytes + element storage

**Memory estimation:**
```java
// Array of 1 million integers
int[] arr = new int[1_000_000];
// Memory: 16 bytes (overhead) + 1M × 4 bytes = ~4 MB

// ArrayList of 1 million Integer objects
List<Integer> list = new ArrayList<>();
// Memory: Array overhead + 1M × (4 bytes + 16 bytes object overhead) = ~20 MB
```

## Advanced Analysis Techniques

### Master Theorem
**For recurrences of form:** T(n) = aT(n/b) + f(n)

**Cases:**
1. **If f(n) = O(n^(log_b(a) - ε))** for some ε > 0: **T(n) = Θ(n^log_b(a))**
2. **If f(n) = Θ(n^log_b(a)):** **T(n) = Θ(n^log_b(a) × log n)**
3. **If f(n) = Ω(n^(log_b(a) + ε))** for some ε > 0 and af(n/b) ≤ cf(n): **T(n) = Θ(f(n))**

**Examples:**
```java
// Binary search: T(n) = T(n/2) + O(1)
// a=1, b=2, f(n)=O(1), log_b(a) = 0
// Case 2: T(n) = Θ(log n)

// Merge sort: T(n) = 2T(n/2) + O(n)  
// a=2, b=2, f(n)=O(n), log_b(a) = 1
// Case 2: T(n) = Θ(n log n)

// Karatsuba multiplication: T(n) = 3T(n/2) + O(n)
// a=3, b=2, f(n)=O(n), log_b(a) = log₂(3) ≈ 1.585
// Case 1: T(n) = Θ(n^1.585)
```

### Substitution Method
**For complex recurrences not covered by Master Theorem:**

**Example:** T(n) = T(n/3) + T(2n/3) + O(n)

**Guess:** T(n) = O(n log n)
**Proof by induction:**
- **Base case:** T(1) = O(1) ≤ c × 1 × log(1) = 0 (needs adjustment)
- **Inductive step:** Assume T(k) ≤ ck log k for all k < n
- T(n) ≤ c(n/3)log(n/3) + c(2n/3)log(2n/3) + dn
- After algebraic manipulation: T(n) ≤ cn log n

## Complexity Analysis in Practice

### Algorithm Selection Framework
```java
// Decision framework for algorithm selection
public interface AlgorithmSelector {
    
    default Algorithm selectSortingAlgorithm(int n, boolean stable, boolean inPlace) {
        if (n < 50) return InsertionSort;           // O(n²) but low constant
        if (stable && !inPlace) return MergeSort;   // O(n log n), stable
        if (inPlace && !stable) return HeapSort;    // O(n log n), in-place
        return QuickSort;                           // O(n log n) average, in-place
    }
    
    default Algorithm selectSearchAlgorithm(int n, boolean sorted) {
        if (!sorted) return LinearSearch;           // O(n)
        if (n < 100) return LinearSearch;           // Binary search overhead
        return BinarySearch;                        // O(log n)
    }
}
```

### Profiling and Measurement
**Empirical analysis:**
```java
// Performance measurement template
public class PerformanceAnalyzer {
    
    public void analyzeAlgorithm(Function<Integer, Void> algorithm) {
        int[] sizes = {100, 1000, 10000, 100000};
        
        for (int n : sizes) {
            long startTime = System.nanoTime();
            algorithm.apply(n);
            long endTime = System.nanoTime();
            
            double timeMs = (endTime - startTime) / 1_000_000.0;
            System.out.printf("n=%d: %.2f ms%n", n, timeMs);
        }
    }
    
    public void memoryAnalysis(Supplier<Object> creator) {
        Runtime runtime = Runtime.getRuntime();
        long beforeMemory = runtime.totalMemory() - runtime.freeMemory();
        
        Object result = creator.get();
        
        long afterMemory = runtime.totalMemory() - runtime.freeMemory();
        long memoryUsed = afterMemory - beforeMemory;
        
        System.out.printf("Memory used: %d bytes%n", memoryUsed);
    }
}
```

### Common Optimization Strategies

#### Time Optimization
1. **Avoid nested loops:** Use hash maps for O(1) lookups
2. **Precompute results:** Use prefix sums, memoization
3. **Use appropriate data structures:** TreeMap vs HashMap
4. **Algorithmic improvements:** Better time complexity algorithms

#### Space Optimization  
1. **In-place algorithms:** Avoid auxiliary arrays
2. **Iterative vs recursive:** Eliminate call stack overhead
3. **Space-efficient data structures:** Bit manipulation, compressed structures
4. **Streaming algorithms:** Process data without storing entirely

### Complexity Analysis Checklist
**For any algorithm:**
1. **Identify basic operations:** What operation dominates?
2. **Count operations:** How many times is basic operation performed?
3. **Express as function of input size:** T(n) = ?
4. **Apply asymptotic notation:** Simplify to O, Ω, or Θ
5. **Consider all cases:** Best, average, worst case
6. **Verify with examples:** Test with small inputs
7. **Compare alternatives:** Is there a better approach?

This comprehensive analysis framework provides the mathematical foundation for understanding and predicting algorithm performance across all problem domains.