# Dynamic Programming - CS Fundamentals

## Core Concepts and Mathematical Foundation

### Definition
**Dynamic Programming:** An algorithmic technique for solving optimization problems by breaking them down into simpler subproblems and storing the results to avoid redundant calculations.

**Mathematical foundation:** Transform recursive problems with overlapping subproblems into efficient iterative solutions.

### Fundamental Principles

#### 1. Optimal Substructure
**Definition:** A problem has optimal substructure if an optimal solution to the problem contains optimal solutions to subproblems.

**Mathematical formulation:**
```
If S is optimal solution to problem P, and S uses solution S' to subproblem P',
then S' must be optimal solution to P'
```

**Example - Shortest Path:**
- If shortest path from A to C goes through B
- Then A→B must be shortest path from A to B
- And B→C must be shortest path from B to C

#### 2. Overlapping Subproblems
**Definition:** A problem has overlapping subproblems if the recursive algorithm revisits the same subproblems multiple times.

**Example - Fibonacci:**
```
fib(5) = fib(4) + fib(3)
fib(4) = fib(3) + fib(2)
fib(3) = fib(2) + fib(1)

fib(3) and fib(2) are computed multiple times
```

**Without overlapping subproblems:** Use divide and conquer (merge sort, binary search)
**With overlapping subproblems:** Use dynamic programming

### DP vs Other Techniques

| Technique | Optimal Substructure | Overlapping Subproblems | Example |
|-----------|---------------------|------------------------|---------|
| **Greedy** | ✅ | ❌ | Activity selection |
| **Divide & Conquer** | ✅ | ❌ | Merge sort |
| **Dynamic Programming** | ✅ | ✅ | Fibonacci, LCS |

## DP Implementation Approaches

### 1. Memoization (Top-Down)
**Concept:** Recursive approach with caching to store computed results

```java
// Example: Fibonacci with memoization
public int fibonacci(int n) {
    Map<Integer, Integer> memo = new HashMap<>();
    return fibHelper(n, memo);
}

private int fibHelper(int n, Map<Integer, Integer> memo) {
    if (n <= 1) return n;
    
    if (memo.containsKey(n)) {
        return memo.get(n);  // Return cached result
    }
    
    int result = fibHelper(n - 1, memo) + fibHelper(n - 2, memo);
    memo.put(n, result);  // Cache result
    return result;
}
```

**Advantages:**
- Natural recursive thinking
- Only computes needed subproblems
- Easy to implement from recursive solution

**Disadvantages:**
- Function call overhead
- May cause stack overflow for deep recursion
- Less cache-friendly memory access

### 2. Tabulation (Bottom-Up)
**Concept:** Iterative approach that builds solution from smallest subproblems

```java
// Example: Fibonacci with tabulation
public int fibonacci(int n) {
    if (n <= 1) return n;
    
    int[] dp = new int[n + 1];
    dp[0] = 0;
    dp[1] = 1;
    
    for (int i = 2; i <= n; i++) {
        dp[i] = dp[i - 1] + dp[i - 2];
    }
    
    return dp[n];
}
```

**Advantages:**
- No function call overhead
- Better cache locality
- Can optimize space usage easily
- No risk of stack overflow

**Disadvantages:**
- May compute unnecessary subproblems
- Less intuitive for complex problems
- Harder to implement directly

### Space Optimization
**Technique:** When DP state only depends on recent states, reduce space complexity

```java
// Space-optimized Fibonacci
public int fibonacci(int n) {
    if (n <= 1) return n;
    
    int prev2 = 0, prev1 = 1;
    
    for (int i = 2; i <= n; i++) {
        int current = prev1 + prev2;
        prev2 = prev1;
        prev1 = current;
    }
    
    return prev1;
}
```

**Space reduction:** O(n) → O(1)

## Classical DP Patterns

### 1. Linear DP (1D)

#### Climbing Stairs
**Problem:** Count ways to reach nth stair (can climb 1 or 2 steps)

```java
public int climbStairs(int n) {
    if (n <= 2) return n;
    
    int[] dp = new int[n + 1];
    dp[1] = 1;  // 1 way to reach stair 1
    dp[2] = 2;  // 2 ways to reach stair 2
    
    for (int i = 3; i <= n; i++) {
        dp[i] = dp[i - 1] + dp[i - 2];  // Sum of ways from previous 2 stairs
    }
    
    return dp[n];
}
```

**Recurrence relation:** `dp[i] = dp[i-1] + dp[i-2]`
**Time:** O(n), **Space:** O(n) → O(1) with optimization

#### House Robber
**Problem:** Rob houses to maximize money, can't rob adjacent houses

```java
public int rob(int[] nums) {
    if (nums.length == 0) return 0;
    if (nums.length == 1) return nums[0];
    
    int[] dp = new int[nums.length];
    dp[0] = nums[0];
    dp[1] = Math.max(nums[0], nums[1]);
    
    for (int i = 2; i < nums.length; i++) {
        dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
    }
    
    return dp[nums.length - 1];
}
```

**Recurrence relation:** `dp[i] = max(dp[i-1], dp[i-2] + nums[i])`

### 2. Grid DP (2D)

#### Unique Paths
**Problem:** Count paths from top-left to bottom-right in grid (only right/down moves)

```java
public int uniquePaths(int m, int n) {
    int[][] dp = new int[m][n];
    
    // Initialize boundaries
    for (int i = 0; i < m; i++) dp[i][0] = 1;  // First column
    for (int j = 0; j < n; j++) dp[0][j] = 1;  // First row
    
    for (int i = 1; i < m; i++) {
        for (int j = 1; j < n; j++) {
            dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
        }
    }
    
    return dp[m - 1][n - 1];
}
```

**Recurrence relation:** `dp[i][j] = dp[i-1][j] + dp[i][j-1]`

#### Minimum Path Sum
**Problem:** Find minimum sum path from top-left to bottom-right

```java
public int minPathSum(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    int[][] dp = new int[m][n];
    
    dp[0][0] = grid[0][0];
    
    // Initialize first row
    for (int j = 1; j < n; j++) {
        dp[0][j] = dp[0][j - 1] + grid[0][j];
    }
    
    // Initialize first column
    for (int i = 1; i < m; i++) {
        dp[i][0] = dp[i - 1][0] + grid[i][0];
    }
    
    for (int i = 1; i < m; i++) {
        for (int j = 1; j < n; j++) {
            dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
        }
    }
    
    return dp[m - 1][n - 1];
}
```

### 3. Sequence DP

#### Longest Increasing Subsequence (LIS)
**Problem:** Find length of longest strictly increasing subsequence

```java
// O(n²) solution
public int lengthOfLIS(int[] nums) {
    int n = nums.length;
    int[] dp = new int[n];
    Arrays.fill(dp, 1);  // Each element forms subsequence of length 1
    
    for (int i = 1; i < n; i++) {
        for (int j = 0; j < i; j++) {
            if (nums[j] < nums[i]) {
                dp[i] = Math.max(dp[i], dp[j] + 1);
            }
        }
    }
    
    return Arrays.stream(dp).max().getAsInt();
}

// O(n log n) solution with binary search
public int lengthOfLISOptimal(int[] nums) {
    List<Integer> tails = new ArrayList<>();
    
    for (int num : nums) {
        int pos = Collections.binarySearch(tails, num);
        if (pos < 0) pos = -(pos + 1);  // Convert to insertion point
        
        if (pos == tails.size()) {
            tails.add(num);  // Extend sequence
        } else {
            tails.set(pos, num);  // Replace with smaller value
        }
    }
    
    return tails.size();
}
```

**Key insight for O(n log n):** Maintain array of smallest tail values for each length

#### Longest Common Subsequence (LCS)
**Problem:** Find length of longest subsequence common to two strings

```java
public int longestCommonSubsequence(String text1, String text2) {
    int m = text1.length(), n = text2.length();
    int[][] dp = new int[m + 1][n + 1];
    
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                dp[i][j] = dp[i - 1][j - 1] + 1;
            } else {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
    }
    
    return dp[m][n];
}
```

**Recurrence relation:**
```
dp[i][j] = dp[i-1][j-1] + 1                    if text1[i-1] == text2[j-1]
         = max(dp[i-1][j], dp[i][j-1])         otherwise
```

## Knapsack Problems

### 0/1 Knapsack
**Problem:** Choose items to maximize value within weight capacity (each item used at most once)

```java
public int knapsack(int[] weights, int[] values, int capacity) {
    int n = weights.length;
    int[][] dp = new int[n + 1][capacity + 1];
    
    for (int i = 1; i <= n; i++) {
        for (int w = 1; w <= capacity; w++) {
            if (weights[i - 1] <= w) {
                // Can include current item
                dp[i][w] = Math.max(
                    dp[i - 1][w],  // Don't include
                    dp[i - 1][w - weights[i - 1]] + values[i - 1]  // Include
                );
            } else {
                // Can't include current item
                dp[i][w] = dp[i - 1][w];
            }
        }
    }
    
    return dp[n][capacity];
}
```

**Space optimization:**
```java
public int knapsackOptimized(int[] weights, int[] values, int capacity) {
    int[] dp = new int[capacity + 1];
    
    for (int i = 0; i < weights.length; i++) {
        // Traverse backwards to avoid using updated values
        for (int w = capacity; w >= weights[i]; w--) {
            dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
        }
    }
    
    return dp[capacity];
}
```

### Unbounded Knapsack
**Problem:** Unlimited quantity of each item available

```java
public int unboundedKnapsack(int[] weights, int[] values, int capacity) {
    int[] dp = new int[capacity + 1];
    
    for (int w = 1; w <= capacity; w++) {
        for (int i = 0; i < weights.length; i++) {
            if (weights[i] <= w) {
                dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
            }
        }
    }
    
    return dp[capacity];
}
```

**Key difference:** Can use same item multiple times, so traverse capacity forward

## String DP Problems

### Edit Distance (Levenshtein Distance)
**Problem:** Minimum operations to transform one string to another (insert, delete, replace)

```java
public int minDistance(String word1, String word2) {
    int m = word1.length(), n = word2.length();
    int[][] dp = new int[m + 1][n + 1];
    
    // Base cases
    for (int i = 0; i <= m; i++) dp[i][0] = i;  // Delete all
    for (int j = 0; j <= n; j++) dp[0][j] = j;  // Insert all
    
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                dp[i][j] = dp[i - 1][j - 1];  // No operation needed
            } else {
                dp[i][j] = 1 + Math.min(
                    Math.min(dp[i - 1][j],     // Delete
                            dp[i][j - 1]),      // Insert
                    dp[i - 1][j - 1]           // Replace
                );
            }
        }
    }
    
    return dp[m][n];
}
```

### Palindromic Subsequences
**Problem:** Count palindromic subsequences in string

```java
public int countPalindromicSubseq(String s) {
    int n = s.length();
    int[][] dp = new int[n][n];
    
    // Single characters are palindromes
    for (int i = 0; i < n; i++) {
        dp[i][i] = 1;
    }
    
    // Check all substring lengths
    for (int len = 2; len <= n; len++) {
        for (int i = 0; i <= n - len; i++) {
            int j = i + len - 1;
            
            if (s.charAt(i) == s.charAt(j)) {
                dp[i][j] = dp[i + 1][j - 1] + dp[i + 1][j] + dp[i][j - 1] + 1;
            } else {
                dp[i][j] = dp[i + 1][j] + dp[i][j - 1] - dp[i + 1][j - 1];
            }
        }
    }
    
    return dp[0][n - 1];
}
```

## Advanced DP Patterns

### State Machine DP
**Example:** Best Time to Buy and Sell Stock with Cooldown

```java
public int maxProfit(int[] prices) {
    if (prices.length <= 1) return 0;
    
    // States: held[i] = max profit holding stock on day i
    //         sold[i] = max profit just sold stock on day i  
    //         rest[i] = max profit resting on day i
    
    int held = -prices[0];  // Buy on day 0
    int sold = 0;           // Can't sell on day 0
    int rest = 0;           // Rest on day 0
    
    for (int i = 1; i < prices.length; i++) {
        int prevHeld = held, prevSold = sold, prevRest = rest;
        
        held = Math.max(prevHeld, prevRest - prices[i]);  // Keep holding or buy today
        sold = prevHeld + prices[i];                      // Sell today
        rest = Math.max(prevRest, prevSold);              // Rest (cooldown from selling)
    }
    
    return Math.max(sold, rest);  // Can't end holding stock
}
```

### Interval DP
**Example:** Matrix Chain Multiplication

```java
public int matrixChainOrder(int[] dimensions) {
    int n = dimensions.length - 1;  // Number of matrices
    int[][] dp = new int[n][n];
    
    // l is chain length
    for (int l = 2; l <= n; l++) {
        for (int i = 0; i < n - l + 1; i++) {
            int j = i + l - 1;
            dp[i][j] = Integer.MAX_VALUE;
            
            for (int k = i; k < j; k++) {
                int cost = dp[i][k] + dp[k + 1][j] + 
                          dimensions[i] * dimensions[k + 1] * dimensions[j + 1];
                dp[i][j] = Math.min(dp[i][j], cost);
            }
        }
    }
    
    return dp[0][n - 1];
}
```

### Digit DP
**Pattern:** Count numbers with certain properties in range [L, R]

```java
public int countNumbers(int limit) {
    String s = String.valueOf(limit);
    int n = s.length();
    Integer[][][] memo = new Integer[n][2][2];  // pos, tight, started
    
    return solve(0, true, false, s, memo);
}

private int solve(int pos, boolean tight, boolean started, String s, Integer[][][] memo) {
    if (pos == s.length()) {
        return started ? 1 : 0;
    }
    
    int tightFlag = tight ? 1 : 0;
    int startedFlag = started ? 1 : 0;
    
    if (memo[pos][tightFlag][startedFlag] != null) {
        return memo[pos][tightFlag][startedFlag];
    }
    
    int limit = tight ? s.charAt(pos) - '0' : 9;
    int result = 0;
    
    for (int digit = 0; digit <= limit; digit++) {
        boolean newTight = tight && (digit == limit);
        boolean newStarted = started || (digit > 0);
        
        if (isValidDigit(digit, started)) {  // Custom validation
            result += solve(pos + 1, newTight, newStarted, s, memo);
        }
    }
    
    return memo[pos][tightFlag][startedFlag] = result;
}
```

## DP Optimization Techniques

### 1. Rolling Array / Space Optimization
**Technique:** When dp[i] only depends on dp[i-1], use O(1) space

```java
// From O(n) to O(1) space
public int fibonacci(int n) {
    if (n <= 1) return n;
    
    int prev2 = 0, prev1 = 1;
    for (int i = 2; i <= n; i++) {
        int current = prev1 + prev2;
        prev2 = prev1;
        prev1 = current;
    }
    return prev1;
}
```

### 2. Monotonic Queue/Stack Optimization
**Example:** Sliding Window Maximum in DP

```java
public int[] maxSlidingWindow(int[] nums, int k) {
    Deque<Integer> deque = new ArrayDeque<>();
    int[] result = new int[nums.length - k + 1];
    
    for (int i = 0; i < nums.length; i++) {
        // Remove indices outside window
        while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
            deque.pollFirst();
        }
        
        // Remove smaller elements
        while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
            deque.pollLast();
        }
        
        deque.offerLast(i);
        
        if (i >= k - 1) {
            result[i - k + 1] = nums[deque.peekFirst()];
        }
    }
    
    return result;
}
```

### 3. Convex Hull Optimization (CHT)
**Application:** Optimize DP transitions of form dp[i] = min(dp[j] + cost(j, i))

**When applicable:**
- Quadrilateral inequality holds
- Cost function satisfies certain monotonicity properties

## DP Problem-Solving Framework

### 1. Problem Identification
**DP problems typically have:**
- Optimization (minimize/maximize)
- Counting (number of ways)
- Decision problems (can/cannot)
- Overlapping subproblems
- Optimal substructure

### 2. State Design
**Questions to ask:**
- What parameters uniquely define a subproblem?
- What's the minimal information needed to make decisions?
- How does state space size affect complexity?

### 3. Transition Function
**Define:**
- Base cases (smallest subproblems)
- Recurrence relation (how to combine subproblems)
- Order of computation (dependencies)

### 4. Implementation Strategy
```java
// Template for DP problems
public int dpSolution(/* parameters */) {
    // 1. Define state space
    int[][] dp = new int[dimension1][dimension2];
    
    // 2. Initialize base cases
    // dp[base_case] = initial_value;
    
    // 3. Fill DP table
    for (int i = start; i < end; i++) {
        for (int j = start; j < end; j++) {
            // 4. Apply recurrence relation
            dp[i][j] = computeTransition(dp, i, j);
        }
    }
    
    // 5. Return final answer
    return dp[final_state];
}
```

## Common Pitfalls and Interview Tips

### Common Mistakes
1. **Incorrect state definition** - missing important parameters
2. **Wrong base cases** - not handling boundary conditions
3. **Incorrect transitions** - violating optimal substructure
4. **Off-by-one errors** - indexing issues
5. **Forgetting memoization** in recursive solutions

### Optimization Checklist
1. **Space optimization:** Can reduce dimensions?
2. **State compression:** Can represent state more efficiently?
3. **Early termination:** Can stop computation early?
4. **Mathematical simplification:** Can derive closed-form solution?

### Problem Recognition Patterns
**Look for keywords:**
- "Minimum/maximum"
- "Number of ways"
- "Optimal"
- "Best"
- "Count"

**Problem types:**
- Path problems in grids
- Subsequence problems
- Partition problems
- Game theory problems
- Resource allocation

### Interview Strategy
1. **Start with brute force** recursive solution
2. **Identify overlapping subproblems** 
3. **Add memoization** (top-down DP)
4. **Convert to tabulation** if needed (bottom-up DP)
5. **Optimize space** if possible
6. **Verify with examples** and edge cases

### Time/Space Complexity Analysis
**General patterns:**
- **1D DP:** O(n) time, O(n) space → O(1) space often possible
- **2D DP:** O(n²) time, O(n²) space → O(n) space sometimes possible
- **String DP:** Usually O(n×m) for two strings
- **Tree DP:** O(n) where n = number of nodes

**State space size determines complexity:**
- Number of states × Time per transition = Total time complexity