# Backtracking - CS Fundamentals

## Core Concepts and Mathematical Foundation

### Definition
**Backtracking:** A systematic method for solving constraint satisfaction problems by exploring the solution space and abandoning (backtracking from) partial solutions that cannot lead to valid complete solutions.

**Mathematical foundation:** Explore implicit tree/graph of partial solutions using depth-first search with pruning.

### Key Principles

#### 1. Solution Space as Tree
**Concept:** Represent all possible solutions as paths in a decision tree
```
                    Root
                  /   |   \
               Choice1 Choice2 Choice3
              /   \      |      /   \
            ...   ...   ...   ...  ...
```

**Each level:** Represents a decision point
**Each path:** Represents a partial solution
**Leaves:** Complete solutions (valid or invalid)

#### 2. Constraint Satisfaction
**Components:**
- **Variables:** What we're assigning values to
- **Domain:** Possible values for each variable
- **Constraints:** Rules that valid solutions must satisfy

**Example - N-Queens:**
- **Variables:** Position of queen in each row
- **Domain:** Columns {1, 2, ..., n}
- **Constraints:** No two queens attack each other

#### 3. Pruning Strategy
**Early termination:** Abandon partial solutions that cannot lead to valid complete solutions

**Mathematical insight:** If current partial solution violates constraints, all extensions of this solution will also be invalid.

```
If partial solution P violates constraints,
then for any extension E of P: P + E also violates constraints
```

## Backtracking Algorithm Template

### General Framework
```java
public void backtrack(State currentState, List<Solution> solutions) {
    // Base case: complete solution found
    if (isComplete(currentState)) {
        if (isValid(currentState)) {
            solutions.add(new Solution(currentState));
        }
        return;
    }
    
    // Try all possible choices for next step
    for (Choice choice : getPossibleChoices(currentState)) {
        // Make choice
        makeChoice(currentState, choice);
        
        // Recurse if choice doesn't violate constraints
        if (isValidPartialSolution(currentState)) {
            backtrack(currentState, solutions);
        }
        
        // Backtrack: undo choice
        undoChoice(currentState, choice);
    }
}
```

### Key Components
1. **State representation:** How to represent partial solutions
2. **Choice generation:** What options are available at each step
3. **Constraint checking:** When to prune invalid paths
4. **Base case:** When solution is complete
5. **Backtracking:** How to undo choices

## Classical Backtracking Problems

### 1. Generate All Subsets
**Problem:** Generate all possible subsets of given array

```java
public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    List<Integer> current = new ArrayList<>();
    generateSubsets(nums, 0, current, result);
    return result;
}

private void generateSubsets(int[] nums, int index, List<Integer> current, 
                           List<List<Integer>> result) {
    // Base case: processed all elements
    if (index == nums.length) {
        result.add(new ArrayList<>(current));  // Add copy of current subset
        return;
    }
    
    // Choice 1: Don't include current element
    generateSubsets(nums, index + 1, current, result);
    
    // Choice 2: Include current element
    current.add(nums[index]);
    generateSubsets(nums, index + 1, current, result);
    current.remove(current.size() - 1);  // Backtrack
}
```

**Alternative iterative approach:**
```java
public List<List<Integer>> subsetsIterative(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    result.add(new ArrayList<>());  // Empty subset
    
    for (int num : nums) {
        int size = result.size();
        for (int i = 0; i < size; i++) {
            List<Integer> newSubset = new ArrayList<>(result.get(i));
            newSubset.add(num);
            result.add(newSubset);
        }
    }
    
    return result;
}
```

**Time complexity:** O(2^n × n) where n = array length
**Space complexity:** O(2^n × n) for storing all subsets

### 2. Generate All Permutations
**Problem:** Generate all permutations of given array

```java
public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    List<Integer> current = new ArrayList<>();
    boolean[] used = new boolean[nums.length];
    
    generatePermutations(nums, current, used, result);
    return result;
}

private void generatePermutations(int[] nums, List<Integer> current, 
                                boolean[] used, List<List<Integer>> result) {
    // Base case: permutation complete
    if (current.size() == nums.length) {
        result.add(new ArrayList<>(current));
        return;
    }
    
    // Try each unused element
    for (int i = 0; i < nums.length; i++) {
        if (!used[i]) {
            // Make choice
            current.add(nums[i]);
            used[i] = true;
            
            // Recurse
            generatePermutations(nums, current, used, result);
            
            // Backtrack
            current.remove(current.size() - 1);
            used[i] = false;
        }
    }
}
```

**Time complexity:** O(n! × n)
**Space complexity:** O(n! × n)

**Mathematical analysis:**
- **Number of permutations:** n!
- **Time to generate each:** O(n) for copying
- **Recursion depth:** n

### 3. Generate Combinations
**Problem:** Generate all combinations of k elements from n elements

```java
public List<List<Integer>> combine(int n, int k) {
    List<List<Integer>> result = new ArrayList<>();
    List<Integer> current = new ArrayList<>();
    
    generateCombinations(1, n, k, current, result);
    return result;
}

private void generateCombinations(int start, int n, int k, 
                                List<Integer> current, List<List<Integer>> result) {
    // Base case: combination complete
    if (current.size() == k) {
        result.add(new ArrayList<>(current));
        return;
    }
    
    // Pruning: if remaining numbers insufficient, return early
    int remaining = k - current.size();
    int available = n - start + 1;
    if (remaining > available) return;
    
    // Try each number from start to n
    for (int i = start; i <= n; i++) {
        // Make choice
        current.add(i);
        
        // Recurse with next starting position
        generateCombinations(i + 1, n, k, current, result);
        
        // Backtrack
        current.remove(current.size() - 1);
    }
}
```

**Time complexity:** O(C(n,k) × k) where C(n,k) = n!/(k!(n-k)!)
**Space complexity:** O(C(n,k) × k)

## Advanced Backtracking Applications

### 1. N-Queens Problem
**Problem:** Place n queens on n×n chessboard so no two queens attack each other

```java
public List<List<String>> solveNQueens(int n) {
    List<List<String>> result = new ArrayList<>();
    int[] queens = new int[n];  // queens[i] = column of queen in row i
    
    solveNQueensHelper(0, n, queens, result);
    return result;
}

private void solveNQueensHelper(int row, int n, int[] queens, 
                               List<List<String>> result) {
    // Base case: all queens placed
    if (row == n) {
        result.add(generateBoard(queens, n));
        return;
    }
    
    // Try placing queen in each column of current row
    for (int col = 0; col < n; col++) {
        if (isSafe(row, col, queens)) {
            // Make choice
            queens[row] = col;
            
            // Recurse to next row
            solveNQueensHelper(row + 1, n, queens, result);
            
            // Backtrack (implicit - will be overwritten)
        }
    }
}

private boolean isSafe(int row, int col, int[] queens) {
    for (int prevRow = 0; prevRow < row; prevRow++) {
        int prevCol = queens[prevRow];
        
        // Check column conflict
        if (prevCol == col) return false;
        
        // Check diagonal conflicts
        if (Math.abs(prevRow - row) == Math.abs(prevCol - col)) {
            return false;
        }
    }
    return true;
}

private List<String> generateBoard(int[] queens, int n) {
    List<String> board = new ArrayList<>();
    for (int i = 0; i < n; i++) {
        StringBuilder row = new StringBuilder();
        for (int j = 0; j < n; j++) {
            row.append(queens[i] == j ? 'Q' : '.');
        }
        board.add(row.toString());
    }
    return board;
}
```

**Optimization with bit manipulation:**
```java
public int totalNQueens(int n) {
    return solveNQueensBit(0, 0, 0, 0, n);
}

private int solveNQueensBit(int row, int cols, int diag1, int diag2, int n) {
    if (row == n) return 1;
    
    int count = 0;
    int availablePositions = ((1 << n) - 1) & ~(cols | diag1 | diag2);
    
    while (availablePositions != 0) {
        int position = availablePositions & -availablePositions;  // Get rightmost bit
        availablePositions ^= position;  // Remove this position
        
        count += solveNQueensBit(
            row + 1,
            cols | position,
            (diag1 | position) << 1,
            (diag2 | position) >> 1,
            n
        );
    }
    
    return count;
}
```

### 2. Sudoku Solver
**Problem:** Fill 9×9 Sudoku grid following standard rules

```java
public void solveSudoku(char[][] board) {
    solveSudokuHelper(board);
}

private boolean solveSudokuHelper(char[][] board) {
    for (int row = 0; row < 9; row++) {
        for (int col = 0; col < 9; col++) {
            if (board[row][col] == '.') {
                // Try digits 1-9
                for (char digit = '1'; digit <= '9'; digit++) {
                    if (isValidSudoku(board, row, col, digit)) {
                        // Make choice
                        board[row][col] = digit;
                        
                        // Recurse
                        if (solveSudokuHelper(board)) {
                            return true;  // Solution found
                        }
                        
                        // Backtrack
                        board[row][col] = '.';
                    }
                }
                return false;  // No valid digit found
            }
        }
    }
    return true;  // All cells filled
}

private boolean isValidSudoku(char[][] board, int row, int col, char digit) {
    // Check row
    for (int j = 0; j < 9; j++) {
        if (board[row][j] == digit) return false;
    }
    
    // Check column
    for (int i = 0; i < 9; i++) {
        if (board[i][col] == digit) return false;
    }
    
    // Check 3×3 box
    int boxRow = (row / 3) * 3;
    int boxCol = (col / 3) * 3;
    for (int i = boxRow; i < boxRow + 3; i++) {
        for (int j = boxCol; j < boxCol + 3; j++) {
            if (board[i][j] == digit) return false;
        }
    }
    
    return true;
}
```

### 3. Word Search
**Problem:** Find if word exists in 2D character grid

```java
public boolean exist(char[][] board, String word) {
    int m = board.length, n = board[0].length;
    
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (board[i][j] == word.charAt(0)) {
                if (wordSearchDFS(board, word, i, j, 0)) {
                    return true;
                }
            }
        }
    }
    
    return false;
}

private boolean wordSearchDFS(char[][] board, String word, int row, int col, int index) {
    // Base case: found complete word
    if (index == word.length()) return true;
    
    // Boundary and character check
    if (row < 0 || row >= board.length || col < 0 || col >= board[0].length ||
        board[row][col] != word.charAt(index)) {
        return false;
    }
    
    // Mark current cell as visited
    char original = board[row][col];
    board[row][col] = '#';
    
    // Explore all 4 directions
    boolean found = wordSearchDFS(board, word, row + 1, col, index + 1) ||
                   wordSearchDFS(board, word, row - 1, col, index + 1) ||
                   wordSearchDFS(board, word, row, col + 1, index + 1) ||
                   wordSearchDFS(board, word, row, col - 1, index + 1);
    
    // Backtrack: restore original character
    board[row][col] = original;
    
    return found;
}
```

## Optimization Techniques

### 1. Pruning Strategies

#### Early Constraint Checking
```java
// Instead of checking constraints at the end
if (isComplete(state) && isValid(state)) {
    addSolution(state);
}

// Check constraints early to prune invalid branches
if (isValidPartialSolution(state)) {
    if (isComplete(state)) {
        addSolution(state);
    } else {
        // Continue exploring
        for (Choice choice : getChoices(state)) {
            makeChoice(state, choice);
            backtrack(state);
            undoChoice(state, choice);
        }
    }
}
```

#### Bound-based Pruning
```java
// For optimization problems, maintain best solution found so far
private int bestSolution = Integer.MAX_VALUE;

private void backtrack(State state, int currentCost) {
    // Prune if current cost already exceeds best solution
    if (currentCost >= bestSolution) return;
    
    // Prune if lower bound of remaining cost exceeds best solution
    if (currentCost + lowerBound(state) >= bestSolution) return;
    
    // Continue normal backtracking...
}
```

### 2. Ordering Heuristics

#### Most Constrained Variable First
```java
// Choose variable with fewest remaining valid values
private Variable chooseMostConstrainedVariable(State state) {
    Variable bestVar = null;
    int minRemainingValues = Integer.MAX_VALUE;
    
    for (Variable var : getUnassignedVariables(state)) {
        int remaining = countValidValues(var, state);
        if (remaining < minRemainingValues) {
            minRemainingValues = remaining;
            bestVar = var;
        }
    }
    
    return bestVar;
}
```

#### Least Constraining Value First
```java
// Choose value that eliminates fewest options for other variables
private List<Value> orderValuesByConstraint(Variable var, State state) {
    List<Value> values = getValidValues(var, state);
    
    values.sort((v1, v2) -> {
        int conflicts1 = countConflicts(var, v1, state);
        int conflicts2 = countConflicts(var, v2, state);
        return Integer.compare(conflicts1, conflicts2);
    });
    
    return values;
}
```

### 3. Symmetry Breaking
**Technique:** Reduce search space by eliminating symmetric solutions

```java
// Example: N-Queens with symmetry breaking
private void solveNQueensWithSymmetry(int row, int n, int[] queens, 
                                     List<List<String>> result) {
    if (row == n) {
        result.add(generateBoard(queens, n));
        return;
    }
    
    // For first row, only try first half of columns (break reflection symmetry)
    int maxCol = (row == 0) ? n / 2 : n;
    
    for (int col = 0; col < maxCol; col++) {
        if (isSafe(row, col, queens)) {
            queens[row] = col;
            solveNQueensWithSymmetry(row + 1, n, queens, result);
        }
    }
}
```

## Complexity Analysis

### Time Complexity Patterns

#### Exponential Complexity
**General form:** O(b^d) where b = branching factor, d = depth

**Examples:**
- **Subsets:** O(2^n) - each element included or not
- **Permutations:** O(n!) - n choices for first, n-1 for second, etc.
- **N-Queens:** O(n^n) worst case, much better with pruning

#### Complexity Reduction Techniques
1. **Pruning:** Reduce effective branching factor
2. **Ordering:** Fail fast with good heuristics
3. **Memoization:** Cache results of subproblems (when applicable)

### Space Complexity
**Components:**
- **Recursion stack:** O(d) where d = maximum depth
- **State representation:** Size of partial solution
- **Solution storage:** Number of solutions × size per solution

```java
// Space optimization: avoid storing all solutions
public boolean existsSolution(Problem problem) {
    return backtrackExists(initialState);  // Return boolean instead of all solutions
}

private boolean backtrackExists(State state) {
    if (isComplete(state)) return isValid(state);
    
    for (Choice choice : getChoices(state)) {
        makeChoice(state, choice);
        if (backtrackExists(state)) return true;  // Early termination
        undoChoice(state, choice);
    }
    
    return false;
}
```

## Backtracking vs Other Approaches

### Comparison Table
| Problem Type | Backtracking | Dynamic Programming | Greedy |
|-------------|-------------|-------------------|--------|
| **Optimization** | Find optimal among all | Build optimal bottom-up | Make locally optimal choices |
| **Complete search** | Explores all possibilities | Avoids recomputation | Single path |
| **Space** | O(depth) | O(state space) | O(1) typically |
| **When to use** | Constraint satisfaction | Overlapping subproblems | Optimal substructure + greedy choice |

### Decision Framework
**Use backtracking when:**
- Need to find ALL solutions
- Constraint satisfaction problem
- Search space can be pruned effectively
- No overlapping subproblems (otherwise use DP)

**Use DP when:**
- Optimization problem with overlapping subproblems
- Can define optimal substructure
- Interested in optimal value, not all solutions

**Use greedy when:**
- Greedy choice property holds
- Local optimum leads to global optimum
- Efficiency is critical

## Common Pitfalls and Interview Tips

### Common Mistakes
1. **Forgetting to backtrack** - not undoing choices
2. **Modifying immutable parameters** - changing input arrays
3. **Inefficient constraint checking** - checking same constraints repeatedly
4. **Memory issues** - creating unnecessary copies
5. **Infinite recursion** - not handling base cases properly

### Optimization Tips
1. **Check constraints early** - prune invalid branches quickly
2. **Use pass-by-reference** - avoid copying large data structures
3. **Implement iterative versions** - for very deep recursion
4. **Use bit manipulation** - for boolean constraints (like N-Queens)
5. **Consider problem-specific optimizations** - symmetry breaking, ordering

### Interview Strategy
1. **Start with brute force** - ensure correctness first
2. **Add pruning gradually** - identify what makes partial solutions invalid
3. **Optimize step by step** - ordering, symmetry breaking, etc.
4. **Test with small examples** - verify backtracking logic
5. **Analyze complexity** - explain exponential nature and optimizations

### Template for Interview Problems
```java
public List<Solution> backtrackProblem(Input input) {
    List<Solution> result = new ArrayList<>();
    State initialState = createInitialState(input);
    
    backtrackHelper(initialState, result);
    return result;
}

private void backtrackHelper(State state, List<Solution> result) {
    // Base case
    if (isComplete(state)) {
        if (isValid(state)) {
            result.add(createSolution(state));
        }
        return;
    }
    
    // Try all valid choices
    for (Choice choice : getValidChoices(state)) {
        // Make choice
        makeChoice(state, choice);
        
        // Recurse (with optional pruning)
        if (shouldContinue(state)) {
            backtrackHelper(state, result);
        }
        
        // Backtrack
        undoChoice(state, choice);
    }
}
```

### Classic Problem Categories
**Generation problems:**
- Subsets, permutations, combinations
- Parentheses combinations
- Letter combinations

**Constraint satisfaction:**
- N-Queens, Sudoku
- Graph coloring
- Crossword puzzles

**Path finding:**
- Word search in grid
- Maze solving
- Knight's tour

**Optimization:**
- Subset sum with backtracking
- Traveling salesman (small instances)
- Job scheduling with constraints