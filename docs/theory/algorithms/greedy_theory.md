# Greedy Algorithms - CS Fundamentals

## Core Concepts and Mathematical Foundation

### Definition
**Greedy Algorithm:** An algorithmic approach that makes the locally optimal choice at each step, hoping to find a global optimum.

**Key principle:** At each decision point, choose the option that looks best right now, without considering future consequences.

### Fundamental Properties

#### 1. Greedy Choice Property
**Definition:** A global optimum can be reached by making locally optimal (greedy) choices.

**Mathematical formulation:**
```
If S is an optimal solution to problem P,
and S contains greedy choice g,
then there exists an optimal solution S' that begins with g
```

**Proof technique:** Show that any optimal solution can be modified to include the greedy choice without losing optimality.

#### 2. Optimal Substructure
**Definition:** An optimal solution to the problem contains optimal solutions to subproblems.

**Mathematical formulation:**
```
If S is optimal solution to problem P,
and S = {g} ∪ S',
then S' is optimal solution to subproblem P'
```

**Combined requirement:** Both properties must hold for greedy algorithm to work.

### Greedy vs Dynamic Programming

| Property | Greedy | Dynamic Programming |
|----------|--------|-------------------|
| **Choice** | Make irrevocable local choice | Consider all choices |
| **Subproblems** | One subproblem remains | Multiple overlapping subproblems |
| **Efficiency** | O(n log n) typically | O(n²) or higher typically |
| **Correctness** | Not always optimal | Always optimal (if designed correctly) |

## Classical Greedy Algorithms

### 1. Activity Selection Problem
**Problem:** Select maximum number of non-overlapping activities

**Input:** Activities with start and finish times: [(s₁,f₁), (s₂,f₂), ..., (sₙ,fₙ)]
**Goal:** Maximum number of activities such that no two overlap

```java
public int activitySelection(int[] start, int[] finish) {
    int n = start.length;
    
    // Create array of activities and sort by finish time
    int[][] activities = new int[n][3];  // [start, finish, index]
    for (int i = 0; i < n; i++) {
        activities[i] = new int[]{start[i], finish[i], i};
    }
    
    Arrays.sort(activities, (a, b) -> Integer.compare(a[1], b[1]));
    
    int count = 1;
    int lastFinishTime = activities[0][1];
    
    for (int i = 1; i < n; i++) {
        if (activities[i][0] >= lastFinishTime) {  // No overlap
            count++;
            lastFinishTime = activities[i][1];
        }
    }
    
    return count;
}
```

**Greedy choice:** Always select activity that finishes earliest among remaining activities.

**Correctness proof:**
1. **Greedy choice property:** If there's an optimal solution not starting with earliest-finishing activity, we can replace the first activity with the earliest-finishing one without reducing the number of activities.
2. **Optimal substructure:** After choosing first activity, the problem reduces to selecting maximum activities from remaining non-overlapping ones.

**Time complexity:** O(n log n) due to sorting
**Space complexity:** O(1) excluding input

### 2. Fractional Knapsack
**Problem:** Fill knapsack to maximize value (items can be fractionally taken)

```java
public double fractionalKnapsack(int[] weights, int[] values, int capacity) {
    int n = weights.length;
    
    // Create array of items and sort by value-to-weight ratio
    Item[] items = new Item[n];
    for (int i = 0; i < n; i++) {
        items[i] = new Item(values[i], weights[i], (double)values[i] / weights[i]);
    }
    
    Arrays.sort(items, (a, b) -> Double.compare(b.ratio, a.ratio));
    
    double totalValue = 0;
    int currentWeight = 0;
    
    for (Item item : items) {
        if (currentWeight + item.weight <= capacity) {
            // Take whole item
            currentWeight += item.weight;
            totalValue += item.value;
        } else {
            // Take fraction of item
            int remainingCapacity = capacity - currentWeight;
            totalValue += item.value * ((double)remainingCapacity / item.weight);
            break;
        }
    }
    
    return totalValue;
}

class Item {
    int value, weight;
    double ratio;
    
    Item(int value, int weight, double ratio) {
        this.value = value;
        this.weight = weight;
        this.ratio = ratio;
    }
}
```

**Greedy choice:** Always take item with highest value-to-weight ratio.

**Correctness proof:**
- If optimal solution takes less of highest-ratio item and more of lower-ratio item, we can exchange portions to increase total value.
- This contradicts optimality of original solution.

### 3. Huffman Coding
**Problem:** Build optimal prefix-free binary code for given character frequencies

```java
public Map<Character, String> huffmanCoding(Map<Character, Integer> frequencies) {
    PriorityQueue<HuffmanNode> pq = new PriorityQueue<>(
        Comparator.comparingInt(n -> n.frequency)
    );
    
    // Create leaf nodes for each character
    for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
        pq.offer(new HuffmanNode(entry.getKey(), entry.getValue()));
    }
    
    // Build Huffman tree
    while (pq.size() > 1) {
        HuffmanNode left = pq.poll();
        HuffmanNode right = pq.poll();
        
        HuffmanNode merged = new HuffmanNode('\0', left.frequency + right.frequency);
        merged.left = left;
        merged.right = right;
        
        pq.offer(merged);
    }
    
    HuffmanNode root = pq.poll();
    Map<Character, String> codes = new HashMap<>();
    generateCodes(root, "", codes);
    
    return codes;
}

private void generateCodes(HuffmanNode node, String code, Map<Character, String> codes) {
    if (node == null) return;
    
    if (node.character != '\0') {  // Leaf node
        codes.put(node.character, code.isEmpty() ? "0" : code);
        return;
    }
    
    generateCodes(node.left, code + "0", codes);
    generateCodes(node.right, code + "1", codes);
}

class HuffmanNode {
    char character;
    int frequency;
    HuffmanNode left, right;
    
    HuffmanNode(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
    }
}
```

**Greedy choice:** Always merge two nodes with lowest frequencies.

**Correctness proof:** Mathematical induction on tree structure shows that greedy merging produces optimal prefix-free code.

## Advanced Greedy Applications

### 1. Interval Scheduling Maximization
**Variant:** Weighted activity selection with different objective

```java
public int intervalScheduling(int[][] intervals) {
    // Sort by end time
    Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));
    
    int count = 0;
    int lastEnd = Integer.MIN_VALUE;
    
    for (int[] interval : intervals) {
        if (interval[0] >= lastEnd) {
            count++;
            lastEnd = interval[1];
        }
    }
    
    return count;
}
```

### 2. Minimum Number of Platforms
**Problem:** Find minimum number of railway platforms needed

```java
public int findPlatform(int[] arrivals, int[] departures) {
    Arrays.sort(arrivals);
    Arrays.sort(departures);
    
    int platforms = 1;
    int maxPlatforms = 1;
    int i = 1, j = 0;
    
    while (i < arrivals.length && j < departures.length) {
        if (arrivals[i] <= departures[j]) {
            platforms++;
            i++;
        } else {
            platforms--;
            j++;
        }
        
        maxPlatforms = Math.max(maxPlatforms, platforms);
    }
    
    return maxPlatforms;
}
```

**Greedy insight:** At any time, number of platforms needed equals number of trains currently at station.

### 3. Job Scheduling with Deadlines
**Problem:** Schedule jobs to maximize profit within deadlines

```java
public int jobScheduling(Job[] jobs) {
    // Sort by profit in descending order
    Arrays.sort(jobs, (a, b) -> Integer.compare(b.profit, a.profit));
    
    int maxDeadline = Arrays.stream(jobs).mapToInt(j -> j.deadline).max().orElse(0);
    boolean[] slot = new boolean[maxDeadline + 1];
    int totalProfit = 0;
    
    for (Job job : jobs) {
        // Find latest available slot before deadline
        for (int i = job.deadline; i >= 1; i--) {
            if (!slot[i]) {
                slot[i] = true;
                totalProfit += job.profit;
                break;
            }
        }
    }
    
    return totalProfit;
}

class Job {
    int id, deadline, profit;
    
    Job(int id, int deadline, int profit) {
        this.id = id;
        this.deadline = deadline;
        this.profit = profit;
    }
}
```

**Greedy choice:** Schedule highest-profit job as late as possible within its deadline.

## Minimum Spanning Tree (MST) Algorithms

### 1. Kruskal's Algorithm
**Approach:** Edge-based greedy algorithm using Union-Find

```java
public List<Edge> kruskalMST(List<Edge> edges, int vertices) {
    List<Edge> mst = new ArrayList<>();
    UnionFind uf = new UnionFind(vertices);
    
    // Sort edges by weight
    edges.sort(Comparator.comparingInt(e -> e.weight));
    
    for (Edge edge : edges) {
        if (uf.find(edge.src) != uf.find(edge.dest)) {
            uf.union(edge.src, edge.dest);
            mst.add(edge);
            
            if (mst.size() == vertices - 1) break;
        }
    }
    
    return mst;
}
```

**Greedy choice:** Always add lightest edge that doesn't create cycle.

### 2. Prim's Algorithm
**Approach:** Vertex-based greedy algorithm

```java
public int primMST(int[][] graph) {
    int vertices = graph.length;
    int[] key = new int[vertices];
    boolean[] inMST = new boolean[vertices];
    
    Arrays.fill(key, Integer.MAX_VALUE);
    key[0] = 0;
    
    int totalWeight = 0;
    
    for (int count = 0; count < vertices; count++) {
        int u = getMinKeyVertex(key, inMST);
        inMST[u] = true;
        totalWeight += key[u];
        
        for (int v = 0; v < vertices; v++) {
            if (graph[u][v] != 0 && !inMST[v] && graph[u][v] < key[v]) {
                key[v] = graph[u][v];
            }
        }
    }
    
    return totalWeight;
}

private int getMinKeyVertex(int[] key, boolean[] inMST) {
    int min = Integer.MAX_VALUE;
    int minIndex = -1;
    
    for (int v = 0; v < key.length; v++) {
        if (!inMST[v] && key[v] < min) {
            min = key[v];
            minIndex = v;
        }
    }
    
    return minIndex;
}
```

**Greedy choice:** Always add minimum-weight edge connecting MST to non-MST vertex.

## Exchange Arguments and Correctness Proofs

### Exchange Argument Technique
**Template for proving greedy correctness:**

1. **Assumption:** Suppose optimal solution O differs from greedy solution G
2. **Exchange:** Show that we can modify O to be "more like" G without losing optimality
3. **Iteration:** Repeat until O = G
4. **Conclusion:** G is optimal

### Example: Activity Selection Proof
**Theorem:** Greedy algorithm (select earliest-finishing activity) produces optimal solution.

**Proof:**
1. Let O = {o₁, o₂, ..., oₖ} be optimal solution with activities sorted by finish time
2. Let G = {g₁, g₂, ..., gₘ} be greedy solution
3. **Claim:** m = k (greedy produces optimal number of activities)

**Exchange argument:**
- If f(g₁) ≤ f(o₁), then g₁ is valid first choice
- If f(g₁) > f(o₁), impossible since greedy picks earliest-finishing
- Replace o₁ with g₁ in O: still valid solution with same number of activities
- Apply recursively to remaining subproblem

### Example: Fractional Knapsack Proof
**Theorem:** Greedy algorithm (highest value/weight ratio first) is optimal.

**Proof by contradiction:**
1. Assume optimal solution O takes less of highest-ratio item i
2. O must take more of some lower-ratio item j
3. **Exchange:** Replace ε weight of item j with ε weight of item i
4. **Value change:** Δvalue = ε(ratio(i) - ratio(j)) > 0
5. **Contradiction:** O wasn't optimal

## When Greedy Algorithms Fail

### 0/1 Knapsack Problem
**Why greedy fails:**
```
Items: {value: 10, weight: 5}, {value: 20, weight: 10}, {value: 15, weight: 15}
Capacity: 20

Greedy (by ratio): Take first two items → value = 30
Optimal: Take second and third items → value = 35
```

**Root cause:** Cannot take fractions, so local optimum ≠ global optimum

### Shortest Path with Negative Edges
**Why greedy (Dijkstra) fails:**
```
Graph: A --(1)--> B --(−10)--> C
       A --(5)--> C

Greedy: A → B → C, cost = 1 + (−10) = −9
But greedy picks A → C first, cost = 5 (suboptimal)
```

**Root cause:** Negative edges can make longer paths shorter

### Longest Common Subsequence
**Why greedy fails:**
```
String 1: "ABCDGH"
String 2: "AEDFHR"

Greedy (match first possible): A, D, H → length = 3
Optimal: A, D, H or A, H → length = 3 (tie)
But greedy might miss better alignment in complex cases
```

## Greedy Algorithm Design Strategy

### 1. Problem Analysis Framework
**Questions to ask:**
1. Can problem be broken into subproblems?
2. Is there a clear "best" local choice?
3. Does local optimum lead to global optimum?
4. Can optimal substructure be proven?

### 2. Correctness Verification
**Steps to prove correctness:**
1. **Identify greedy choice:** What local decision does algorithm make?
2. **Prove greedy choice property:** Local optimum leads to global optimum
3. **Prove optimal substructure:** Optimal solution contains optimal subsolutions
4. **Use exchange argument:** Transform any optimal solution to greedy solution

### 3. Common Greedy Patterns

#### Sort-based Greedy
```java
// Template: Sort by some criteria, then make greedy choices
public int greedySolution(Item[] items) {
    Arrays.sort(items, customComparator);
    
    int result = 0;
    for (Item item : items) {
        if (isValidChoice(item)) {
            result += makeChoice(item);
        }
    }
    
    return result;
}
```

#### Priority Queue Greedy
```java
// Template: Use priority queue to always get optimal next choice
public int greedyWithPQ(List<Item> items) {
    PriorityQueue<Item> pq = new PriorityQueue<>(customComparator);
    pq.addAll(items);
    
    int result = 0;
    while (!pq.isEmpty()) {
        Item best = pq.poll();
        if (isValidChoice(best)) {
            result += processChoice(best);
            // Potentially add new items to queue
        }
    }
    
    return result;
}
```

## Complexity Analysis

### Time Complexity Patterns
| Pattern | Complexity | Example |
|---------|------------|---------|
| **Sort-based** | O(n log n) | Activity selection |
| **Priority queue** | O(n log n) | Huffman coding |
| **Linear scan** | O(n) | Some interval problems |
| **Graph-based** | O(E log V) | MST algorithms |

### Space Complexity
**Typically O(1) to O(n):**
- O(1): Simple greedy choices
- O(n): When storing intermediate results or using auxiliary data structures

## Common Pitfalls and Interview Tips

### Common Mistakes
1. **Assuming greedy works** without proof
2. **Wrong greedy choice** - not identifying correct local optimum
3. **Ignoring edge cases** - empty input, single element
4. **Incorrect sorting criteria** - using wrong comparison function
5. **Not handling ties** properly in optimization

### Problem Recognition
**Greedy likely works when:**
- Problem asks for optimal value (max/min)
- Local choices seem to preserve global optimality
- Sorting by some criteria gives insight
- Exchange argument can be constructed

**Greedy likely fails when:**
- Need to consider multiple future choices
- Local optimum clearly doesn't lead to global optimum
- Problem has exponential solution space

### Interview Strategy
1. **Identify greedy choice** - what's the obvious local optimum?
2. **Test with examples** - does greedy work on small cases?
3. **Attempt exchange argument** - can you prove correctness?
4. **Consider edge cases** - empty, single element, ties
5. **Implement and verify** - code clean solution

### Template for Interview Problems
```java
public int greedyAlgorithm(Input input) {
    // Handle edge cases
    if (input.isEmpty()) return 0;
    
    // Sort or prepare data for greedy choices
    prepareData(input);
    
    int result = 0;
    State currentState = initializeState();
    
    // Make greedy choices
    while (hasMoreChoices(currentState)) {
        Choice best = makeGreedyChoice(currentState);
        result += processChoice(best);
        updateState(currentState, best);
    }
    
    return result;
}
```

### Classic Problem Categories
**Scheduling problems:**
- Activity selection
- Job scheduling with deadlines
- Meeting rooms

**Graph problems:**
- Minimum spanning tree
- Shortest path (non-negative weights)
- Network flow (some variants)

**String/Array problems:**
- Huffman coding
- Fractional knapsack
- Gas station

**Mathematical optimization:**
- Change making (with certain coin systems)
- Load balancing
- Resource allocation