# Advanced Techniques - CS Fundamentals

## Bit Manipulation

### Fundamental Concepts
**Bit manipulation:** Operating on individual bits within binary representations of numbers.

**Key insight:** Many problems can be solved efficiently using bitwise operations instead of traditional approaches.

### Essential Bitwise Operations
```java
// Basic operations
int a = 5;  // 101 in binary
int b = 3;  // 011 in binary

int and = a & b;      // 001 = 1 (AND)
int or = a | b;       // 111 = 7 (OR)  
int xor = a ^ b;      // 110 = 6 (XOR)
int not = ~a;         // ...11111010 (NOT - all bits flipped)
int leftShift = a << 1;   // 1010 = 10 (multiply by 2)
int rightShift = a >> 1;  // 10 = 2 (divide by 2)
```

### Bit Manipulation Techniques

#### 1. Check if Number is Power of 2
```java
public boolean isPowerOfTwo(int n) {
    return n > 0 && (n & (n - 1)) == 0;
}
```
**Mathematical insight:** Powers of 2 have exactly one bit set
- 8 = 1000, 8-1 = 0111, 8 & 7 = 0000
- 6 = 0110, 6-1 = 0101, 6 & 5 = 0100 ≠ 0

#### 2. Count Set Bits (Brian Kernighan's Algorithm)
```java
public int hammingWeight(int n) {
    int count = 0;
    while (n != 0) {
        n &= (n - 1);  // Remove rightmost set bit
        count++;
    }
    return count;
}
```
**Key insight:** `n & (n-1)` always removes the rightmost set bit

#### 3. Find Single Number (XOR properties)
```java
public int singleNumber(int[] nums) {
    int result = 0;
    for (int num : nums) {
        result ^= num;  // XOR cancels out duplicates
    }
    return result;
}
```
**XOR properties:**
- a ⊕ a = 0
- a ⊕ 0 = a  
- XOR is commutative and associative

#### 4. Generate All Subsets using Bits
```java
public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    int n = nums.length;
    
    // Iterate through all possible bit combinations
    for (int mask = 0; mask < (1 << n); mask++) {
        List<Integer> subset = new ArrayList<>();
        
        for (int i = 0; i < n; i++) {
            if ((mask & (1 << i)) != 0) {  // Check if i-th bit is set
                subset.add(nums[i]);
            }
        }
        
        result.add(subset);
    }
    
    return result;
}
```

#### 5. Bit Manipulation for DP State Compression
```java
// Traveling Salesman Problem using bitmask DP
public int tsp(int[][] dist) {
    int n = dist.length;
    int[][] dp = new int[1 << n][n];
    
    // Initialize with infinity
    for (int[] row : dp) {
        Arrays.fill(row, Integer.MAX_VALUE);
    }
    
    dp[1][0] = 0;  // Start at city 0
    
    for (int mask = 1; mask < (1 << n); mask++) {
        for (int u = 0; u < n; u++) {
            if ((mask & (1 << u)) == 0 || dp[mask][u] == Integer.MAX_VALUE) {
                continue;
            }
            
            for (int v = 0; v < n; v++) {
                if ((mask & (1 << v)) != 0) continue;  // Already visited
                
                int newMask = mask | (1 << v);
                dp[newMask][v] = Math.min(dp[newMask][v], dp[mask][u] + dist[u][v]);
            }
        }
    }
    
    int result = Integer.MAX_VALUE;
    int finalMask = (1 << n) - 1;
    for (int i = 1; i < n; i++) {
        if (dp[finalMask][i] != Integer.MAX_VALUE) {
            result = Math.min(result, dp[finalMask][i] + dist[i][0]);
        }
    }
    
    return result;
}
```

## Union-Find (Disjoint Set Union)

### Core Concept
**Union-Find:** Data structure to efficiently handle disjoint set operations:
- **Find:** Determine which set an element belongs to
- **Union:** Merge two sets into one

### Basic Implementation
```java
class UnionFind {
    private int[] parent;
    private int[] rank;
    private int components;
    
    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        components = n;
        
        for (int i = 0; i < n; i++) {
            parent[i] = i;  // Each element is its own parent initially
            rank[i] = 0;
        }
    }
    
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);  // Path compression
        }
        return parent[x];
    }
    
    public boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        
        if (rootX == rootY) return false;  // Already in same set
        
        // Union by rank
        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
        
        components--;
        return true;
    }
    
    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }
    
    public int getComponents() {
        return components;
    }
}
```

### Optimizations

#### 1. Path Compression
**Before optimization:**
```
find(4): 4 → 3 → 2 → 1 → 0
```

**After path compression:**
```
find(4): 4 → 0 (all nodes point directly to root)
```

#### 2. Union by Rank
**Principle:** Always attach smaller tree under root of larger tree to keep tree shallow.

**Time complexity:** O(α(n)) where α is inverse Ackermann function (practically constant)

### Applications

#### 1. Number of Connected Components
```java
public int countComponents(int n, int[][] edges) {
    UnionFind uf = new UnionFind(n);
    
    for (int[] edge : edges) {
        uf.union(edge[0], edge[1]);
    }
    
    return uf.getComponents();
}
```

#### 2. Detect Cycle in Undirected Graph
```java
public boolean hasCycle(int n, int[][] edges) {
    UnionFind uf = new UnionFind(n);
    
    for (int[] edge : edges) {
        if (uf.connected(edge[0], edge[1])) {
            return true;  // Edge connects already connected components
        }
        uf.union(edge[0], edge[1]);
    }
    
    return false;
}
```

#### 3. Kruskal's MST Algorithm
```java
public int kruskalMST(int n, int[][] edges) {
    Arrays.sort(edges, (a, b) -> Integer.compare(a[2], b[2]));  // Sort by weight
    
    UnionFind uf = new UnionFind(n);
    int mstWeight = 0;
    int edgesUsed = 0;
    
    for (int[] edge : edges) {
        if (uf.union(edge[0], edge[1])) {
            mstWeight += edge[2];
            edgesUsed++;
            
            if (edgesUsed == n - 1) break;  // MST complete
        }
    }
    
    return mstWeight;
}
```

## Trie (Prefix Tree)

### Core Concept
**Trie:** Tree-like data structure for efficient storage and retrieval of strings, where each path from root to leaf represents a string.

### Implementation
```java
class TrieNode {
    TrieNode[] children;
    boolean isEndOfWord;
    
    public TrieNode() {
        children = new TrieNode[26];  // For lowercase English letters
        isEndOfWord = false;
    }
}

class Trie {
    private TrieNode root;
    
    public Trie() {
        root = new TrieNode();
    }
    
    public void insert(String word) {
        TrieNode current = root;
        
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (current.children[index] == null) {
                current.children[index] = new TrieNode();
            }
            current = current.children[index];
        }
        
        current.isEndOfWord = true;
    }
    
    public boolean search(String word) {
        TrieNode node = searchPrefix(word);
        return node != null && node.isEndOfWord;
    }
    
    public boolean startsWith(String prefix) {
        return searchPrefix(prefix) != null;
    }
    
    private TrieNode searchPrefix(String prefix) {
        TrieNode current = root;
        
        for (char c : prefix.toCharArray()) {
            int index = c - 'a';
            if (current.children[index] == null) {
                return null;
            }
            current = current.children[index];
        }
        
        return current;
    }
}
```

### Applications

#### 1. Word Search II (Multiple Pattern Matching)
```java
public List<String> findWords(char[][] board, String[] words) {
    Trie trie = new Trie();
    for (String word : words) {
        trie.insert(word);
    }
    
    List<String> result = new ArrayList<>();
    int m = board.length, n = board[0].length;
    
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            dfs(board, i, j, trie.root, "", result);
        }
    }
    
    return result;
}

private void dfs(char[][] board, int i, int j, TrieNode node, 
                String word, List<String> result) {
    if (i < 0 || i >= board.length || j < 0 || j >= board[0].length ||
        board[i][j] == '#') {
        return;
    }
    
    char c = board[i][j];
    TrieNode child = node.children[c - 'a'];
    if (child == null) return;
    
    word += c;
    if (child.isEndOfWord) {
        result.add(word);
        child.isEndOfWord = false;  // Avoid duplicates
    }
    
    board[i][j] = '#';  // Mark as visited
    
    // Explore all 4 directions
    dfs(board, i + 1, j, child, word, result);
    dfs(board, i - 1, j, child, word, result);
    dfs(board, i, j + 1, child, word, result);
    dfs(board, i, j - 1, child, word, result);
    
    board[i][j] = c;  // Backtrack
}
```

#### 2. Auto-complete System
```java
class AutocompleteSystem {
    class TrieNode {
        Map<Character, TrieNode> children;
        Map<String, Integer> sentences;
        
        TrieNode() {
            children = new HashMap<>();
            sentences = new HashMap<>();
        }
    }
    
    private TrieNode root;
    private TrieNode current;
    private StringBuilder prefix;
    
    public AutocompleteSystem(String[] sentences, int[] times) {
        root = new TrieNode();
        current = root;
        prefix = new StringBuilder();
        
        for (int i = 0; i < sentences.length; i++) {
            insert(sentences[i], times[i]);
        }
    }
    
    private void insert(String sentence, int count) {
        TrieNode node = root;
        for (char c : sentence.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
            node.sentences.put(sentence, node.sentences.getOrDefault(sentence, 0) + count);
        }
    }
    
    public List<String> input(char c) {
        if (c == '#') {
            insert(prefix.toString(), 1);
            current = root;
            prefix = new StringBuilder();
            return new ArrayList<>();
        }
        
        prefix.append(c);
        if (current != null) {
            current = current.children.get(c);
        }
        
        if (current == null) return new ArrayList<>();
        
        return current.sentences.entrySet().stream()
            .sorted((a, b) -> {
                if (a.getValue().equals(b.getValue())) {
                    return a.getKey().compareTo(b.getKey());
                }
                return b.getValue() - a.getValue();
            })
            .limit(3)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }
}
```

## Segment Tree

### Core Concept
**Segment Tree:** Binary tree for efficiently handling range queries and updates on arrays.

**Applications:**
- Range sum queries
- Range minimum/maximum queries
- Range updates

### Implementation
```java
class SegmentTree {
    private int[] tree;
    private int n;
    
    public SegmentTree(int[] arr) {
        n = arr.length;
        tree = new int[4 * n];  // Upper bound for tree size
        build(arr, 0, 0, n - 1);
    }
    
    private void build(int[] arr, int node, int start, int end) {
        if (start == end) {
            tree[node] = arr[start];
        } else {
            int mid = (start + end) / 2;
            build(arr, 2 * node + 1, start, mid);
            build(arr, 2 * node + 2, mid + 1, end);
            tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
        }
    }
    
    public void update(int idx, int val) {
        update(0, 0, n - 1, idx, val);
    }
    
    private void update(int node, int start, int end, int idx, int val) {
        if (start == end) {
            tree[node] = val;
        } else {
            int mid = (start + end) / 2;
            if (idx <= mid) {
                update(2 * node + 1, start, mid, idx, val);
            } else {
                update(2 * node + 2, mid + 1, end, idx, val);
            }
            tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
        }
    }
    
    public int query(int l, int r) {
        return query(0, 0, n - 1, l, r);
    }
    
    private int query(int node, int start, int end, int l, int r) {
        if (r < start || end < l) {
            return 0;  // No overlap
        }
        if (l <= start && end <= r) {
            return tree[node];  // Complete overlap
        }
        
        // Partial overlap
        int mid = (start + end) / 2;
        int leftSum = query(2 * node + 1, start, mid, l, r);
        int rightSum = query(2 * node + 2, mid + 1, end, l, r);
        return leftSum + rightSum;
    }
}
```

**Time complexity:**
- Build: O(n)
- Query: O(log n)
- Update: O(log n)

### Lazy Propagation
**For range updates:**
```java
class LazySegmentTree {
    private int[] tree, lazy;
    private int n;
    
    public LazySegmentTree(int[] arr) {
        n = arr.length;
        tree = new int[4 * n];
        lazy = new int[4 * n];
        build(arr, 0, 0, n - 1);
    }
    
    private void push(int node, int start, int end) {
        if (lazy[node] != 0) {
            tree[node] += lazy[node] * (end - start + 1);
            
            if (start != end) {  // Not a leaf
                lazy[2 * node + 1] += lazy[node];
                lazy[2 * node + 2] += lazy[node];
            }
            
            lazy[node] = 0;
        }
    }
    
    public void updateRange(int l, int r, int val) {
        updateRange(0, 0, n - 1, l, r, val);
    }
    
    private void updateRange(int node, int start, int end, int l, int r, int val) {
        push(node, start, end);
        
        if (start > r || end < l) return;
        
        if (start >= l && end <= r) {
            lazy[node] += val;
            push(node, start, end);
            return;
        }
        
        int mid = (start + end) / 2;
        updateRange(2 * node + 1, start, mid, l, r, val);
        updateRange(2 * node + 2, mid + 1, end, l, r, val);
        
        push(2 * node + 1, start, mid);
        push(2 * node + 2, mid + 1, end);
        tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
    }
}
```

## Mathematical Algorithms

### 1. Greatest Common Divisor (GCD)
```java
// Euclidean algorithm
public int gcd(int a, int b) {
    while (b != 0) {
        int temp = b;
        b = a % b;
        a = temp;
    }
    return a;
}

// Recursive version
public int gcdRecursive(int a, int b) {
    return b == 0 ? a : gcdRecursive(b, a % b);
}

// Extended Euclidean Algorithm (finds x, y such that ax + by = gcd(a,b))
public int[] extendedGcd(int a, int b) {
    if (b == 0) {
        return new int[]{a, 1, 0};  // gcd, x, y
    }
    
    int[] result = extendedGcd(b, a % b);
    int gcd = result[0];
    int x1 = result[1];
    int y1 = result[2];
    
    int x = y1;
    int y = x1 - (a / b) * y1;
    
    return new int[]{gcd, x, y};
}
```

### 2. Prime Number Algorithms
```java
// Sieve of Eratosthenes
public List<Integer> sieveOfEratosthenes(int n) {
    boolean[] isPrime = new boolean[n + 1];
    Arrays.fill(isPrime, true);
    isPrime[0] = isPrime[1] = false;
    
    for (int i = 2; i * i <= n; i++) {
        if (isPrime[i]) {
            for (int j = i * i; j <= n; j += i) {
                isPrime[j] = false;
            }
        }
    }
    
    List<Integer> primes = new ArrayList<>();
    for (int i = 2; i <= n; i++) {
        if (isPrime[i]) {
            primes.add(i);
        }
    }
    
    return primes;
}

// Miller-Rabin primality test (probabilistic)
public boolean isPrimeMR(long n, int k) {
    if (n < 2) return false;
    if (n == 2 || n == 3) return true;
    if (n % 2 == 0) return false;
    
    // Write n-1 as 2^r * d
    long d = n - 1;
    int r = 0;
    while (d % 2 == 0) {
        d /= 2;
        r++;
    }
    
    Random random = new Random();
    for (int i = 0; i < k; i++) {
        long a = 2 + random.nextLong() % (n - 3);
        long x = modPow(a, d, n);
        
        if (x == 1 || x == n - 1) continue;
        
        boolean composite = true;
        for (int j = 0; j < r - 1; j++) {
            x = (x * x) % n;
            if (x == n - 1) {
                composite = false;
                break;
            }
        }
        
        if (composite) return false;
    }
    
    return true;
}
```

### 3. Modular Arithmetic
```java
// Modular exponentiation
public long modPow(long base, long exp, long mod) {
    long result = 1;
    base %= mod;
    
    while (exp > 0) {
        if (exp % 2 == 1) {
            result = (result * base) % mod;
        }
        exp /= 2;
        base = (base * base) % mod;
    }
    
    return result;
}

// Modular inverse (when mod is prime)
public long modInverse(long a, long mod) {
    return modPow(a, mod - 2, mod);  // Fermat's little theorem
}

// Chinese Remainder Theorem
public long chineseRemainder(int[] remainders, int[] moduli) {
    long result = 0;
    long totalMod = 1;
    
    for (int mod : moduli) {
        totalMod *= mod;
    }
    
    for (int i = 0; i < remainders.length; i++) {
        long partialMod = totalMod / moduli[i];
        long inverse = modInverse(partialMod, moduli[i]);
        result = (result + remainders[i] * partialMod * inverse) % totalMod;
    }
    
    return result;
}
```

### 4. Fast Fourier Transform (FFT)
```java
// Polynomial multiplication using FFT
public class FFT {
    private static class Complex {
        double real, imag;
        
        Complex(double real, double imag) {
            this.real = real;
            this.imag = imag;
        }
        
        Complex multiply(Complex other) {
            return new Complex(
                real * other.real - imag * other.imag,
                real * other.imag + imag * other.real
            );
        }
        
        Complex add(Complex other) {
            return new Complex(real + other.real, imag + other.imag);
        }
        
        Complex subtract(Complex other) {
            return new Complex(real - other.real, imag - other.imag);
        }
    }
    
    public static int[] multiply(int[] a, int[] b) {
        int n = 1;
        while (n < a.length + b.length) n <<= 1;
        
        Complex[] fa = new Complex[n];
        Complex[] fb = new Complex[n];
        
        for (int i = 0; i < n; i++) {
            fa[i] = new Complex(i < a.length ? a[i] : 0, 0);
            fb[i] = new Complex(i < b.length ? b[i] : 0, 0);
        }
        
        fft(fa, false);
        fft(fb, false);
        
        for (int i = 0; i < n; i++) {
            fa[i] = fa[i].multiply(fb[i]);
        }
        
        fft(fa, true);
        
        int[] result = new int[a.length + b.length - 1];
        for (int i = 0; i < result.length; i++) {
            result[i] = (int) Math.round(fa[i].real);
        }
        
        return result;
    }
    
    private static void fft(Complex[] a, boolean invert) {
        int n = a.length;
        
        // Bit-reversal permutation
        for (int i = 1, j = 0; i < n; i++) {
            int bit = n >> 1;
            for (; (j & bit) != 0; bit >>= 1) {
                j ^= bit;
            }
            j ^= bit;
            
            if (i < j) {
                Complex temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
        }
        
        // FFT computation
        for (int len = 2; len <= n; len <<= 1) {
            double ang = 2 * Math.PI / len * (invert ? -1 : 1);
            Complex wlen = new Complex(Math.cos(ang), Math.sin(ang));
            
            for (int i = 0; i < n; i += len) {
                Complex w = new Complex(1, 0);
                for (int j = 0; j < len / 2; j++) {
                    Complex u = a[i + j];
                    Complex v = a[i + j + len / 2].multiply(w);
                    a[i + j] = u.add(v);
                    a[i + j + len / 2] = u.subtract(v);
                    w = w.multiply(wlen);
                }
            }
        }
        
        if (invert) {
            for (Complex c : a) {
                c.real /= n;
                c.imag /= n;
            }
        }
    }
}
```

## Complexity Analysis Summary

| Technique | Typical Complexity | Use Cases |
|-----------|-------------------|-----------|
| **Bit Manipulation** | O(1) to O(n) | Set operations, optimization |
| **Union-Find** | O(α(n)) per operation | Graph connectivity |
| **Trie** | O(m) per operation | String matching, prefix queries |
| **Segment Tree** | O(log n) per operation | Range queries/updates |
| **Mathematical** | Varies | Number theory, cryptography |

## Interview Tips and Common Patterns

### Problem Recognition
**Bit manipulation when:**
- Working with sets or subsets
- Need to optimize space/time
- Boolean operations on collections
- State compression in DP

**Union-Find when:**
- Graph connectivity problems
- Dynamic connectivity queries
- Minimum spanning tree
- Detecting cycles

**Trie when:**
- Multiple string pattern matching
- Prefix-based operations
- Auto-complete systems
- Word games

**Segment Tree when:**
- Range queries with updates
- Need better than O(n) for range operations
- Dynamic range problems

### Implementation Tips
1. **Bit manipulation:** Learn common bit tricks and patterns
2. **Union-Find:** Always use path compression and union by rank
3. **Trie:** Consider memory usage for large alphabets
4. **Segment Tree:** Handle edge cases in recursive calls
5. **Mathematical:** Be careful with integer overflow

### Template Patterns
```java
// Bit manipulation template
public int bitSolution(int n) {
    int result = 0;
    for (int i = 0; i < 32; i++) {
        if ((n & (1 << i)) != 0) {
            // i-th bit is set
            result += processSetBit(i);
        }
    }
    return result;
}

// Union-Find template  
public int graphConnectivityProblem(int n, int[][] connections) {
    UnionFind uf = new UnionFind(n);
    
    for (int[] connection : connections) {
        uf.union(connection[0], connection[1]);
    }
    
    return uf.getComponents();
}
```