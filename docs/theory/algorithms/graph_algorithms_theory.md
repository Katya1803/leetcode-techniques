# Graph Algorithms - CS Fundamentals

## Core Concepts and Mathematical Foundation

### Graph Definition
**Graph:** A mathematical structure G = (V, E) consisting of:
- **V:** Set of vertices (nodes)
- **E:** Set of edges connecting vertices

**Mathematical notation:**
- |V| = n (number of vertices)
- |E| = m (number of edges)

### Graph Types

#### 1. Directed vs Undirected
**Undirected Graph:** Edges have no direction
```
A --- B
|     |
C --- D
```

**Directed Graph (Digraph):** Edges have direction
```
A --> B
^     |
|     v
C <-- D
```

#### 2. Weighted vs Unweighted
**Unweighted:** All edges have same "weight" (typically 1)
**Weighted:** Edges have associated weights/costs

#### 3. Connected vs Disconnected
**Connected:** Path exists between every pair of vertices
**Disconnected:** Some vertices unreachable from others

#### 4. Cyclic vs Acyclic
**Acyclic:** No cycles exist
**DAG:** Directed Acyclic Graph (special case)

### Graph Properties
**Degree of vertex:** Number of edges incident to vertex
- **Undirected:** degree(v) = number of edges touching v
- **Directed:** in-degree(v) + out-degree(v)

**Handshaking lemma:** ∑degree(v) = 2|E| for undirected graphs

**Maximum edges:**
- **Undirected:** C(n,2) = n(n-1)/2
- **Directed:** n(n-1)

## Graph Representations

### 1. Adjacency Matrix
**Definition:** 2D array where matrix[i][j] = 1 if edge exists from vertex i to j

```java
class GraphMatrix {
    private int[][] matrix;
    private int vertices;
    
    public GraphMatrix(int vertices) {
        this.vertices = vertices;
        this.matrix = new int[vertices][vertices];
    }
    
    public void addEdge(int src, int dest) {
        matrix[src][dest] = 1;
        // For undirected graph:
        // matrix[dest][src] = 1;
    }
    
    public boolean hasEdge(int src, int dest) {
        return matrix[src][dest] == 1;
    }
}
```

**Complexity:**
- **Space:** O(V²)
- **Add edge:** O(1)
- **Check edge:** O(1)
- **Get all neighbors:** O(V)

**When to use:**
- Dense graphs (many edges)
- Frequent edge queries
- Matrix operations needed

### 2. Adjacency List
**Definition:** Array of lists where list[i] contains all neighbors of vertex i

```java
class GraphList {
    private List<List<Integer>> adjList;
    private int vertices;
    
    public GraphList(int vertices) {
        this.vertices = vertices;
        this.adjList = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            adjList.add(new ArrayList<>());
        }
    }
    
    public void addEdge(int src, int dest) {
        adjList.get(src).add(dest);
        // For undirected graph:
        // adjList.get(dest).add(src);
    }
    
    public List<Integer> getNeighbors(int vertex) {
        return adjList.get(vertex);
    }
}
```

**Complexity:**
- **Space:** O(V + E)
- **Add edge:** O(1)
- **Check edge:** O(degree(v))
- **Get all neighbors:** O(degree(v))

**When to use:**
- Sparse graphs (few edges)
- Memory efficiency important
- Frequent neighbor iteration

### 3. Edge List
**Definition:** List of all edges in graph

```java
class Edge {
    int src, dest, weight;
    
    Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
}

class GraphEdgeList {
    private List<Edge> edges;
    private int vertices;
    
    public GraphEdgeList(int vertices) {
        this.vertices = vertices;
        this.edges = new ArrayList<>();
    }
    
    public void addEdge(int src, int dest, int weight) {
        edges.add(new Edge(src, dest, weight));
    }
}
```

**When to use:**
- Algorithms that process all edges (Kruskal's MST)
- Simple graph representation
- Edge-centric operations

## Graph Traversal Algorithms

### Depth-First Search (DFS)

#### Recursive Implementation
```java
public void dfs(int vertex, boolean[] visited, List<List<Integer>> graph) {
    visited[vertex] = true;
    System.out.println(vertex);  // Process vertex
    
    for (int neighbor : graph.get(vertex)) {
        if (!visited[neighbor]) {
            dfs(neighbor, visited, graph);
        }
    }
}

public void dfsTraversal(List<List<Integer>> graph) {
    int vertices = graph.size();
    boolean[] visited = new boolean[vertices];
    
    // Handle disconnected components
    for (int i = 0; i < vertices; i++) {
        if (!visited[i]) {
            dfs(i, visited, graph);
        }
    }
}
```

#### Iterative Implementation
```java
public void dfsIterative(int start, List<List<Integer>> graph) {
    boolean[] visited = new boolean[graph.size()];
    Stack<Integer> stack = new Stack<>();
    
    stack.push(start);
    
    while (!stack.isEmpty()) {
        int vertex = stack.pop();
        
        if (!visited[vertex]) {
            visited[vertex] = true;
            System.out.println(vertex);  // Process vertex
            
            // Add neighbors in reverse order for same traversal as recursive
            List<Integer> neighbors = graph.get(vertex);
            for (int i = neighbors.size() - 1; i >= 0; i--) {
                if (!visited[neighbors.get(i)]) {
                    stack.push(neighbors.get(i));
                }
            }
        }
    }
}
```

**Time complexity:** O(V + E)
**Space complexity:** O(V) for visited array + O(V) for recursion stack/explicit stack

**Applications:**
- Cycle detection
- Topological sorting
- Connected components
- Pathfinding in mazes

### Breadth-First Search (BFS)

#### Implementation
```java
public void bfs(int start, List<List<Integer>> graph) {
    boolean[] visited = new boolean[graph.size()];
    Queue<Integer> queue = new LinkedList<>();
    
    visited[start] = true;
    queue.offer(start);
    
    while (!queue.isEmpty()) {
        int vertex = queue.poll();
        System.out.println(vertex);  // Process vertex
        
        for (int neighbor : graph.get(vertex)) {
            if (!visited[neighbor]) {
                visited[neighbor] = true;
                queue.offer(neighbor);
            }
        }
    }
}
```

**Time complexity:** O(V + E)
**Space complexity:** O(V) for visited array + O(V) for queue

**Applications:**
- Shortest path in unweighted graphs
- Level-order traversal
- Connected components
- Bipartite graph check

### BFS for Shortest Path
```java
public int[] shortestPath(int start, List<List<Integer>> graph) {
    int vertices = graph.size();
    int[] distance = new int[vertices];
    Arrays.fill(distance, -1);  // -1 means unreachable
    
    Queue<Integer> queue = new LinkedList<>();
    distance[start] = 0;
    queue.offer(start);
    
    while (!queue.isEmpty()) {
        int vertex = queue.poll();
        
        for (int neighbor : graph.get(vertex)) {
            if (distance[neighbor] == -1) {  // Not visited
                distance[neighbor] = distance[vertex] + 1;
                queue.offer(neighbor);
            }
        }
    }
    
    return distance;
}
```

## Shortest Path Algorithms

### 1. Dijkstra's Algorithm (Single-Source, Non-negative weights)

```java
public int[] dijkstra(List<List<int[]>> graph, int start) {
    int vertices = graph.size();
    int[] distance = new int[vertices];
    Arrays.fill(distance, Integer.MAX_VALUE);
    distance[start] = 0;
    
    // Priority queue: [distance, vertex]
    PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
    pq.offer(new int[]{0, start});
    
    while (!pq.isEmpty()) {
        int[] current = pq.poll();
        int currentDist = current[0];
        int currentVertex = current[1];
        
        // Skip if we've found a better path
        if (currentDist > distance[currentVertex]) {
            continue;
        }
        
        // Check all neighbors
        for (int[] edge : graph.get(currentVertex)) {
            int neighbor = edge[0];
            int weight = edge[1];
            int newDist = distance[currentVertex] + weight;
            
            if (newDist < distance[neighbor]) {
                distance[neighbor] = newDist;
                pq.offer(new int[]{newDist, neighbor});
            }
        }
    }
    
    return distance;
}
```

**Time complexity:** O((V + E) log V) with binary heap
**Space complexity:** O(V)

**Key insight:** Greedy algorithm - always process closest unvisited vertex

**Mathematical proof of correctness:**
- **Invariant:** When vertex is removed from priority queue, its distance is optimal
- **Proof:** Suppose not optimal path exists through unprocessed vertex u with distance d[u]
- Since all weights ≥ 0, any path through u has length ≥ d[u] > d[v]
- Contradiction with assumption of shorter path

### 2. Bellman-Ford Algorithm (Single-Source, Negative weights allowed)

```java
public int[] bellmanFord(List<Edge> edges, int vertices, int start) {
    int[] distance = new int[vertices];
    Arrays.fill(distance, Integer.MAX_VALUE);
    distance[start] = 0;
    
    // Relax edges V-1 times
    for (int i = 0; i < vertices - 1; i++) {
        for (Edge edge : edges) {
            if (distance[edge.src] != Integer.MAX_VALUE &&
                distance[edge.src] + edge.weight < distance[edge.dest]) {
                distance[edge.dest] = distance[edge.src] + edge.weight;
            }
        }
    }
    
    // Check for negative cycles
    for (Edge edge : edges) {
        if (distance[edge.src] != Integer.MAX_VALUE &&
            distance[edge.src] + edge.weight < distance[edge.dest]) {
            throw new RuntimeException("Graph contains negative cycle");
        }
    }
    
    return distance;
}
```

**Time complexity:** O(VE)
**Space complexity:** O(V)

**Why V-1 iterations:** Longest simple path has at most V-1 edges

### 3. Floyd-Warshall Algorithm (All-Pairs Shortest Path)

```java
public int[][] floydWarshall(int[][] graph) {
    int vertices = graph.length;
    int[][] dist = new int[vertices][vertices];
    
    // Initialize distances
    for (int i = 0; i < vertices; i++) {
        for (int j = 0; j < vertices; j++) {
            dist[i][j] = graph[i][j];
        }
    }
    
    // Try all intermediate vertices
    for (int k = 0; k < vertices; k++) {
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                if (dist[i][k] != Integer.MAX_VALUE &&
                    dist[k][j] != Integer.MAX_VALUE &&
                    dist[i][k] + dist[k][j] < dist[i][j]) {
                    dist[i][j] = dist[i][k] + dist[k][j];
                }
            }
        }
    }
    
    return dist;
}
```

**Time complexity:** O(V³)
**Space complexity:** O(V²)

**Dynamic programming recurrence:**
```
dist[i][j][k] = min(dist[i][j][k-1], dist[i][k][k-1] + dist[k][j][k-1])
```

## Minimum Spanning Tree (MST)

### Definition
**Spanning Tree:** Subgraph that includes all vertices and is a tree (connected, acyclic)
**MST:** Spanning tree with minimum total edge weight

**Properties:**
- Has exactly V-1 edges
- Unique if all edge weights are distinct
- Removing any edge disconnects the tree
- Adding any edge creates exactly one cycle

### 1. Kruskal's Algorithm (Edge-based, Union-Find)

```java
public List<Edge> kruskalMST(List<Edge> edges, int vertices) {
    List<Edge> mst = new ArrayList<>();
    UnionFind uf = new UnionFind(vertices);
    
    // Sort edges by weight
    edges.sort(Comparator.comparingInt(e -> e.weight));
    
    for (Edge edge : edges) {
        // If adding edge doesn't create cycle
        if (uf.find(edge.src) != uf.find(edge.dest)) {
            uf.union(edge.src, edge.dest);
            mst.add(edge);
            
            // MST complete when we have V-1 edges
            if (mst.size() == vertices - 1) {
                break;
            }
        }
    }
    
    return mst;
}

class UnionFind {
    private int[] parent, rank;
    
    public UnionFind(int size) {
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }
    
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);  // Path compression
        }
        return parent[x];
    }
    
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        
        if (rootX != rootY) {
            // Union by rank
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }
}
```

**Time complexity:** O(E log E) dominated by sorting
**Space complexity:** O(V) for Union-Find

### 2. Prim's Algorithm (Vertex-based, Greedy)

```java
public List<Edge> primMST(List<List<int[]>> graph, int start) {
    int vertices = graph.size();
    List<Edge> mst = new ArrayList<>();
    boolean[] inMST = new boolean[vertices];
    
    // Priority queue: [weight, vertex, parent]
    PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
    pq.offer(new int[]{0, start, -1});
    
    while (!pq.isEmpty() && mst.size() < vertices - 1) {
        int[] current = pq.poll();
        int weight = current[0];
        int vertex = current[1];
        int parent = current[2];
        
        if (inMST[vertex]) continue;
        
        inMST[vertex] = true;
        if (parent != -1) {
            mst.add(new Edge(parent, vertex, weight));
        }
        
        // Add all neighbors to priority queue
        for (int[] edge : graph.get(vertex)) {
            int neighbor = edge[0];
            int edgeWeight = edge[1];
            
            if (!inMST[neighbor]) {
                pq.offer(new int[]{edgeWeight, neighbor, vertex});
            }
        }
    }
    
    return mst;
}
```

**Time complexity:** O((V + E) log V) with binary heap
**Space complexity:** O(V)

### MST Algorithm Comparison
| Algorithm | Time | Space | Best for |
|-----------|------|-------|----------|
| Kruskal | O(E log E) | O(V) | Sparse graphs |
| Prim | O((V + E) log V) | O(V) | Dense graphs |

## Topological Sorting

### Definition
**Topological Sort:** Linear ordering of vertices in DAG such that for every directed edge (u,v), vertex u comes before v in the ordering.

**Applications:**
- Course prerequisites
- Build systems
- Job scheduling

### 1. DFS-based Topological Sort
```java
public List<Integer> topologicalSort(List<List<Integer>> graph) {
    int vertices = graph.size();
    boolean[] visited = new boolean[vertices];
    Stack<Integer> stack = new Stack<>();
    
    // Visit all vertices
    for (int i = 0; i < vertices; i++) {
        if (!visited[i]) {
            dfsTopological(i, visited, graph, stack);
        }
    }
    
    // Convert stack to list
    List<Integer> result = new ArrayList<>();
    while (!stack.isEmpty()) {
        result.add(stack.pop());
    }
    
    return result;
}

private void dfsTopological(int vertex, boolean[] visited, 
                           List<List<Integer>> graph, Stack<Integer> stack) {
    visited[vertex] = true;
    
    for (int neighbor : graph.get(vertex)) {
        if (!visited[neighbor]) {
            dfsTopological(neighbor, visited, graph, stack);
        }
    }
    
    stack.push(vertex);  // Add to stack after processing all neighbors
}
```

### 2. Kahn's Algorithm (BFS-based)
```java
public List<Integer> kahnsTopologicalSort(List<List<Integer>> graph) {
    int vertices = graph.size();
    int[] inDegree = new int[vertices];
    
    // Calculate in-degrees
    for (int i = 0; i < vertices; i++) {
        for (int neighbor : graph.get(i)) {
            inDegree[neighbor]++;
        }
    }
    
    Queue<Integer> queue = new LinkedList<>();
    List<Integer> result = new ArrayList<>();
    
    // Add vertices with no incoming edges
    for (int i = 0; i < vertices; i++) {
        if (inDegree[i] == 0) {
            queue.offer(i);
        }
    }
    
    while (!queue.isEmpty()) {
        int vertex = queue.poll();
        result.add(vertex);
        
        // Remove this vertex from graph
        for (int neighbor : graph.get(vertex)) {
            inDegree[neighbor]--;
            if (inDegree[neighbor] == 0) {
                queue.offer(neighbor);
            }
        }
    }
    
    // Check for cycles
    if (result.size() != vertices) {
        throw new RuntimeException("Graph contains cycle - no topological sort possible");
    }
    
    return result;
}
```

**Time complexity:** O(V + E) for both methods
**Space complexity:** O(V)

## Cycle Detection

### 1. Cycle Detection in Undirected Graph (DFS)
```java
public boolean hasCycleUndirected(List<List<Integer>> graph) {
    int vertices = graph.size();
    boolean[] visited = new boolean[vertices];
    
    for (int i = 0; i < vertices; i++) {
        if (!visited[i]) {
            if (dfsUndirected(i, -1, visited, graph)) {
                return true;
            }
        }
    }
    
    return false;
}

private boolean dfsUndirected(int vertex, int parent, boolean[] visited, 
                             List<List<Integer>> graph) {
    visited[vertex] = true;
    
    for (int neighbor : graph.get(vertex)) {
        if (!visited[neighbor]) {
            if (dfsUndirected(neighbor, vertex, visited, graph)) {
                return true;
            }
        } else if (neighbor != parent) {
            return true;  // Back edge found (cycle)
        }
    }
    
    return false;
}
```

### 2. Cycle Detection in Directed Graph (DFS with colors)
```java
public boolean hasCycleDirected(List<List<Integer>> graph) {
    int vertices = graph.size();
    int[] color = new int[vertices];  // 0: white, 1: gray, 2: black
    
    for (int i = 0; i < vertices; i++) {
        if (color[i] == 0) {
            if (dfsDirected(i, color, graph)) {
                return true;
            }
        }
    }
    
    return false;
}

private boolean dfsDirected(int vertex, int[] color, List<List<Integer>> graph) {
    color[vertex] = 1;  // Mark as gray (being processed)
    
    for (int neighbor : graph.get(vertex)) {
        if (color[neighbor] == 1) {
            return true;  // Back edge to gray vertex (cycle)
        }
        if (color[neighbor] == 0 && dfsDirected(neighbor, color, graph)) {
            return true;
        }
    }
    
    color[vertex] = 2;  // Mark as black (completely processed)
    return false;
}
```

**Key insight:** 
- **Undirected:** Cycle exists if we reach a visited vertex that's not the parent
- **Directed:** Cycle exists if we reach a vertex currently being processed (gray)

## Advanced Graph Algorithms

### 1. Strongly Connected Components (Kosaraju's Algorithm)
```java
public List<List<Integer>> stronglyConnectedComponents(List<List<Integer>> graph) {
    int vertices = graph.size();
    boolean[] visited = new boolean[vertices];
    Stack<Integer> finishOrder = new Stack<>();
    
    // Step 1: Get finish order using DFS
    for (int i = 0; i < vertices; i++) {
        if (!visited[i]) {
            dfsFinishTime(i, visited, graph, finishOrder);
        }
    }
    
    // Step 2: Create transpose graph
    List<List<Integer>> transpose = getTranspose(graph);
    
    // Step 3: DFS on transpose in reverse finish order
    Arrays.fill(visited, false);
    List<List<Integer>> sccs = new ArrayList<>();
    
    while (!finishOrder.isEmpty()) {
        int vertex = finishOrder.pop();
        if (!visited[vertex]) {
            List<Integer> component = new ArrayList<>();
            dfsCollectComponent(vertex, visited, transpose, component);
            sccs.add(component);
        }
    }
    
    return sccs;
}
```

### 2. Bipartite Graph Check
```java
public boolean isBipartite(List<List<Integer>> graph) {
    int vertices = graph.size();
    int[] color = new int[vertices];  // 0: uncolored, 1: red, 2: blue
    
    for (int i = 0; i < vertices; i++) {
        if (color[i] == 0) {
            if (!bfsColorCheck(i, graph, color)) {
                return false;
            }
        }
    }
    
    return true;
}

private boolean bfsColorCheck(int start, List<List<Integer>> graph, int[] color) {
    Queue<Integer> queue = new LinkedList<>();
    color[start] = 1;  // Start with red
    queue.offer(start);
    
    while (!queue.isEmpty()) {
        int vertex = queue.poll();
        
        for (int neighbor : graph.get(vertex)) {
            if (color[neighbor] == 0) {
                color[neighbor] = 3 - color[vertex];  // Opposite color
                queue.offer(neighbor);
            } else if (color[neighbor] == color[vertex]) {
                return false;  // Same color adjacent vertices
            }
        }
    }
    
    return true;
}
```

## Graph Algorithm Complexity Summary

| Algorithm | Time | Space | Use Case |
|-----------|------|-------|----------|
| DFS | O(V + E) | O(V) | Connectivity, cycles |
| BFS | O(V + E) | O(V) | Shortest path (unweighted) |
| Dijkstra | O((V + E) log V) | O(V) | Shortest path (non-negative) |
| Bellman-Ford | O(VE) | O(V) | Shortest path (negative edges) |
| Floyd-Warshall | O(V³) | O(V²) | All-pairs shortest path |
| Kruskal MST | O(E log E) | O(V) | MST (sparse graphs) |
| Prim MST | O((V + E) log V) | O(V) | MST (dense graphs) |
| Topological Sort | O(V + E) | O(V) | DAG ordering |

## Common Pitfalls and Interview Tips

### Common Mistakes
1. **Forgetting disconnected components** in graph traversal
2. **Not handling self-loops** and multiple edges
3. **Confusing directed/undirected** graph properties
4. **Integer overflow** in distance calculations
5. **Not checking for negative cycles** in shortest path algorithms

### Edge Cases to Consider
1. **Empty graph:** No vertices or edges
2. **Single vertex:** Isolated node
3. **Disconnected graph:** Multiple components
4. **Self-loops:** Edge from vertex to itself
5. **Negative weights:** May require specific algorithms

### Problem Recognition Patterns
**Graph problems often involve:**
- Finding paths or connectivity
- Optimization (shortest/minimum)
- Ordering or scheduling
- Network flow
- Matching problems

### Interview Problem-Solving Strategy
1. **Identify graph structure:** What are vertices and edges?
2. **Choose representation:** Adjacency list vs matrix
3. **Select algorithm:** Based on problem requirements
4. **Handle edge cases:** Disconnected, cycles, weights
5. **Optimize if needed:** Consider time/space constraints

### Classic Problem Categories

**Traversal problems:**
- Number of islands
- Clone graph
- Word ladder

**Shortest path problems:**
- Network delay time
- Cheapest flights with K stops
- Minimum cost to make valid path

**MST problems:**
- Connecting cities with minimum cost
- Minimum cost to connect all points

**Topological sort problems:**
- Course schedule
- Alien dictionary
- Task scheduling

**Cycle detection:**
- Detect cycle in directed/undirected graph
- Find eventual safe states

### Template for Graph Problems
```java
public void graphAlgorithm(GraphRepresentation graph) {
    int vertices = graph.getVertexCount();
    boolean[] visited = new boolean[vertices];
    
    // Handle disconnected components
    for (int i = 0; i < vertices; i++) {
        if (!visited[i]) {
            // Choose traversal method based on problem
            if (needsBFS) {
                bfs(i, graph, visited);
            } else {
                dfs(i, graph, visited);
            }
        }
    }
}
```