package com.leetcode.algorithms.graphs;

import java.util.*;

/**
 * Topological Sort Algorithms for Directed Acyclic Graphs (DAG)
 *
 * Topological Sort: Linear ordering of vertices in DAG such that for every
 * directed edge (u,v), vertex u comes before v in the ordering.
 *
 * Key Applications:
 * - Course scheduling with prerequisites
 * - Build systems and dependency resolution
 * - Task scheduling
 * - Package management
 * - Compiler symbol table construction
 */
public class TopologicalSort {

    // =========================== DFS-based Topological Sort ===========================

    /**
     * DFS-based topological sort using finish times
     * Time Complexity: O(V + E)
     * Space Complexity: O(V) for visited array and recursion stack
     *
     * Algorithm:
     * 1. Perform DFS on all unvisited vertices
     * 2. Add vertex to result after visiting all its neighbors
     * 3. Reverse the result to get topological order
     *
     * @param graph adjacency list representation of DAG
     * @return topologically sorted vertices, empty list if cycle detected
     */
    public List<Integer> topologicalSortDFS(List<List<Integer>> graph) {
        int vertices = graph.size();
        boolean[] visited = new boolean[vertices];
        Stack<Integer> stack = new Stack<>();

        // Check for cycles while performing DFS
        int[] color = new int[vertices];  // 0=white, 1=gray, 2=black

        for (int i = 0; i < vertices; i++) {
            if (color[i] == 0) {  // White (unvisited)
                if (hasCycleDFS(i, graph, color)) {
                    return new ArrayList<>();  // Cycle detected, no topological sort possible
                }
            }
        }

        // Reset for actual topological sort
        Arrays.fill(visited, false);

        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                dfsTopological(i, visited, graph, stack);
            }
        }

        // Convert stack to list (reverse order)
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

        // Add to stack after processing all neighbors (post-order)
        stack.push(vertex);
    }

    private boolean hasCycleDFS(int vertex, List<List<Integer>> graph, int[] color) {
        color[vertex] = 1;  // Gray (visiting)

        for (int neighbor : graph.get(vertex)) {
            if (color[neighbor] == 1) {  // Back edge to gray vertex
                return true;
            }
            if (color[neighbor] == 0 && hasCycleDFS(neighbor, graph, color)) {
                return true;
            }
        }

        color[vertex] = 2;  // Black (visited)
        return false;
    }

    // =========================== Kahn's Algorithm (BFS-based) ===========================

    /**
     * Kahn's algorithm for topological sort using in-degree
     * Time Complexity: O(V + E)
     * Space Complexity: O(V) for in-degree array and queue
     *
     * Algorithm:
     * 1. Calculate in-degree for all vertices
     * 2. Add all vertices with in-degree 0 to queue
     * 3. Process vertices from queue, reduce in-degree of neighbors
     * 4. Add neighbors with in-degree 0 to queue
     * 5. If all vertices processed, topological sort exists
     *
     * @param graph adjacency list representation of DAG
     * @return topologically sorted vertices, empty list if cycle detected
     */
    public List<Integer> topologicalSortKahn(List<List<Integer>> graph) {
        int vertices = graph.size();
        int[] inDegree = new int[vertices];

        // Calculate in-degree for all vertices
        for (int i = 0; i < vertices; i++) {
            for (int neighbor : graph.get(i)) {
                inDegree[neighbor]++;
            }
        }

        // Add all vertices with in-degree 0 to queue
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < vertices; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        List<Integer> result = new ArrayList<>();

        // Process vertices with in-degree 0
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            result.add(vertex);

            // Reduce in-degree of all neighbors
            for (int neighbor : graph.get(vertex)) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        // Check if all vertices are processed (no cycle)
        if (result.size() != vertices) {
            return new ArrayList<>();  // Cycle detected
        }

        return result;
    }

    // =========================== Course Schedule Problems ===========================

    /**
     * Course Schedule I - Check if all courses can be finished
     * LeetCode Problem: Can you finish all courses given prerequisites?
     *
     * @param numCourses number of courses
     * @param prerequisites array of [course, prerequisite] pairs
     * @return true if all courses can be finished, false otherwise
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = buildGraph(numCourses, prerequisites);
        return !topologicalSortKahn(graph).isEmpty();
    }

    /**
     * Course Schedule II - Return the order of courses to take
     * LeetCode Problem: Return a valid order to finish all courses
     *
     * @param numCourses number of courses
     * @param prerequisites array of [course, prerequisite] pairs
     * @return valid course order, empty array if impossible
     */
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = buildGraph(numCourses, prerequisites);
        List<Integer> topologicalOrder = topologicalSortKahn(graph);

        if (topologicalOrder.isEmpty()) {
            return new int[0];  // Cycle detected
        }

        // Convert to array
        return topologicalOrder.stream().mapToInt(i -> i).toArray();
    }

    private List<List<Integer>> buildGraph(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }

        // Build adjacency list: prerequisite -> course
        for (int[] prereq : prerequisites) {
            int course = prereq[0];
            int prerequisite = prereq[1];
            graph.get(prerequisite).add(course);
        }

        return graph;
    }

    // =========================== Advanced Applications ===========================

    /**
     * All possible topological sorts of a DAG
     * Time Complexity: O(V! * E) in worst case
     * Space Complexity: O(V)
     *
     * @param graph adjacency list representation of DAG
     * @return list of all possible topological orderings
     */
    public List<List<Integer>> allTopologicalSorts(List<List<Integer>> graph) {
        List<List<Integer>> result = new ArrayList<>();
        int vertices = graph.size();
        int[] inDegree = new int[vertices];

        // Calculate in-degree
        for (int i = 0; i < vertices; i++) {
            for (int neighbor : graph.get(i)) {
                inDegree[neighbor]++;
            }
        }

        List<Integer> currentPath = new ArrayList<>();
        boolean[] visited = new boolean[vertices];

        backtrackAllTopological(graph, inDegree, visited, currentPath, result);

        return result;
    }

    private void backtrackAllTopological(List<List<Integer>> graph, int[] inDegree,
                                         boolean[] visited, List<Integer> currentPath,
                                         List<List<Integer>> result) {
        // Find all vertices with in-degree 0 and not visited
        boolean hasZeroInDegree = false;

        for (int i = 0; i < graph.size(); i++) {
            if (!visited[i] && inDegree[i] == 0) {
                hasZeroInDegree = true;

                // Choose this vertex
                visited[i] = true;
                currentPath.add(i);

                // Reduce in-degree of neighbors
                for (int neighbor : graph.get(i)) {
                    inDegree[neighbor]--;
                }

                // Recurse
                backtrackAllTopological(graph, inDegree, visited, currentPath, result);

                // Backtrack
                for (int neighbor : graph.get(i)) {
                    inDegree[neighbor]++;
                }
                currentPath.remove(currentPath.size() - 1);
                visited[i] = false;
            }
        }

        // If no vertex with in-degree 0, we have a complete path
        if (!hasZeroInDegree && currentPath.size() == graph.size()) {
            result.add(new ArrayList<>(currentPath));
        }
    }

    /**
     * Lexicographically smallest topological sort
     * Time Complexity: O(V + E) log V
     * Space Complexity: O(V)
     *
     * @param graph adjacency list representation of DAG
     * @return lexicographically smallest topological ordering
     */
    public List<Integer> lexicographicalTopologicalSort(List<List<Integer>> graph) {
        int vertices = graph.size();
        int[] inDegree = new int[vertices];

        // Calculate in-degree
        for (int i = 0; i < vertices; i++) {
            for (int neighbor : graph.get(i)) {
                inDegree[neighbor]++;
            }
        }

        // Use min-heap to always pick smallest vertex with in-degree 0
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 0; i < vertices; i++) {
            if (inDegree[i] == 0) {
                pq.offer(i);
            }
        }

        List<Integer> result = new ArrayList<>();

        while (!pq.isEmpty()) {
            int vertex = pq.poll();
            result.add(vertex);

            for (int neighbor : graph.get(vertex)) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0) {
                    pq.offer(neighbor);
                }
            }
        }

        return result.size() == vertices ? result : new ArrayList<>();
    }

    /**
     * Check if given order is a valid topological sort
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     *
     * @param graph adjacency list representation
     * @param order proposed topological order
     * @return true if order is valid topological sort
     */
    public boolean isValidTopologicalSort(List<List<Integer>> graph, List<Integer> order) {
        if (order.size() != graph.size()) {
            return false;
        }

        // Create position map
        Map<Integer, Integer> position = new HashMap<>();
        for (int i = 0; i < order.size(); i++) {
            position.put(order.get(i), i);
        }

        // Check all edges
        for (int u = 0; u < graph.size(); u++) {
            for (int v : graph.get(u)) {
                // For edge u -> v, u should come before v
                if (position.get(u) >= position.get(v)) {
                    return false;
                }
            }
        }

        return true;
    }

    // =========================== Cycle Detection in Directed Graph ===========================

    /**
     * Detect cycle in directed graph using DFS (White-Gray-Black approach)
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     *
     * @param graph adjacency list representation
     * @return true if cycle exists, false otherwise
     */
    public boolean hasCycle(List<List<Integer>> graph) {
        int vertices = graph.size();
        int[] color = new int[vertices];  // 0=white, 1=gray, 2=black

        for (int i = 0; i < vertices; i++) {
            if (color[i] == 0) {  // White (unvisited)
                if (hasCycleDFS(i, graph, color)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Find a cycle in directed graph if exists
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     *
     * @param graph adjacency list representation
     * @return cycle as list of vertices, empty if no cycle
     */
    public List<Integer> findCycle(List<List<Integer>> graph) {
        int vertices = graph.size();
        int[] color = new int[vertices];
        int[] parent = new int[vertices];
        Arrays.fill(parent, -1);

        for (int i = 0; i < vertices; i++) {
            if (color[i] == 0) {
                List<Integer> cycle = findCycleDFS(i, graph, color, parent);
                if (!cycle.isEmpty()) {
                    return cycle;
                }
            }
        }

        return new ArrayList<>();
    }

    private List<Integer> findCycleDFS(int vertex, List<List<Integer>> graph,
                                       int[] color, int[] parent) {
        color[vertex] = 1;  // Gray

        for (int neighbor : graph.get(vertex)) {
            if (color[neighbor] == 1) {  // Back edge found
                // Reconstruct cycle
                List<Integer> cycle = new ArrayList<>();
                int current = vertex;
                do {
                    cycle.add(current);
                    current = parent[current];
                } while (current != neighbor && current != -1);
                cycle.add(neighbor);
                Collections.reverse(cycle);
                return cycle;
            }

            if (color[neighbor] == 0) {
                parent[neighbor] = vertex;
                List<Integer> cycle = findCycleDFS(neighbor, graph, color, parent);
                if (!cycle.isEmpty()) {
                    return cycle;
                }
            }
        }

        color[vertex] = 2;  // Black
        return new ArrayList<>();
    }

    // =========================== Strongly Connected Components ===========================

    /**
     * Find strongly connected components using Kosaraju's algorithm
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     *
     * @param graph adjacency list representation
     * @return list of strongly connected components
     */
    public List<List<Integer>> stronglyConnectedComponents(List<List<Integer>> graph) {
        int vertices = graph.size();

        // Step 1: Get finish order using DFS
        boolean[] visited = new boolean[vertices];
        Stack<Integer> finishOrder = new Stack<>();

        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                dfsFinishTime(i, graph, visited, finishOrder);
            }
        }

        // Step 2: Create transpose graph
        List<List<Integer>> transpose = getTransposeGraph(graph);

        // Step 3: DFS on transpose in reverse finish order
        Arrays.fill(visited, false);
        List<List<Integer>> sccs = new ArrayList<>();

        while (!finishOrder.isEmpty()) {
            int vertex = finishOrder.pop();
            if (!visited[vertex]) {
                List<Integer> scc = new ArrayList<>();
                dfsCollectSCC(vertex, transpose, visited, scc);
                sccs.add(scc);
            }
        }

        return sccs;
    }

    private void dfsFinishTime(int vertex, List<List<Integer>> graph,
                               boolean[] visited, Stack<Integer> finishOrder) {
        visited[vertex] = true;

        for (int neighbor : graph.get(vertex)) {
            if (!visited[neighbor]) {
                dfsFinishTime(neighbor, graph, visited, finishOrder);
            }
        }

        finishOrder.push(vertex);
    }

    private List<List<Integer>> getTransposeGraph(List<List<Integer>> graph) {
        int vertices = graph.size();
        List<List<Integer>> transpose = new ArrayList<>();

        for (int i = 0; i < vertices; i++) {
            transpose.add(new ArrayList<>());
        }

        for (int i = 0; i < vertices; i++) {
            for (int neighbor : graph.get(i)) {
                transpose.get(neighbor).add(i);
            }
        }

        return transpose;
    }

    private void dfsCollectSCC(int vertex, List<List<Integer>> graph,
                               boolean[] visited, List<Integer> scc) {
        visited[vertex] = true;
        scc.add(vertex);

        for (int neighbor : graph.get(vertex)) {
            if (!visited[neighbor]) {
                dfsCollectSCC(neighbor, graph, visited, scc);
            }
        }
    }

    // =========================== Utility Methods ===========================

    /**
     * Create adjacency list from edge list for directed graph
     *
     * @param vertices number of vertices
     * @param edges array of directed edges [from, to]
     * @return adjacency list representation
     */
    public static List<List<Integer>> createDirectedGraph(int vertices, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
        }

        return graph;
    }

    /**
     * Check if graph is DAG (Directed Acyclic Graph)
     *
     * @param graph adjacency list representation
     * @return true if DAG, false if has cycles
     */
    public boolean isDAG(List<List<Integer>> graph) {
        return !hasCycle(graph);
    }

    /**
     * Get longest path in DAG using topological sort
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     *
     * @param graph adjacency list representation
     * @param weights edge weights [from][to] = weight
     * @param start starting vertex
     * @return array of longest distances from start vertex
     */
    public int[] longestPathInDAG(List<List<Integer>> graph, int[][] weights, int start) {
        List<Integer> topologicalOrder = topologicalSortKahn(graph);
        if (topologicalOrder.isEmpty()) {
            throw new IllegalArgumentException("Graph has cycles - not a DAG");
        }

        int vertices = graph.size();
        int[] dist = new int[vertices];
        Arrays.fill(dist, Integer.MIN_VALUE);
        dist[start] = 0;

        // Process vertices in topological order
        for (int u : topologicalOrder) {
            if (dist[u] != Integer.MIN_VALUE) {
                for (int v : graph.get(u)) {
                    dist[v] = Math.max(dist[v], dist[u] + weights[u][v]);
                }
            }
        }

        return dist;
    }

    /**
     * Print topological sort result
     *
     * @param result topological sort result
     * @param label description of the sort
     */
    public static void printTopologicalSort(List<Integer> result, String label) {
        System.out.print(label + ": ");
        if (result.isEmpty()) {
            System.out.println("No valid topological sort (cycle detected)");
        } else {
            for (int i = 0; i < result.size(); i++) {
                System.out.print(result.get(i));
                if (i < result.size() - 1) System.out.print(" -> ");
            }
            System.out.println();
        }
    }
}