package com.leetcode.algorithms.graphs;

import java.util.*;

/**
 * Graph Traversal Algorithms - DFS and BFS implementations
 *
 * This class provides comprehensive implementations of graph traversal algorithms
 * including both recursive and iterative versions with various applications.
 *
 * Key Concepts:
 * - DFS: Goes as deep as possible before backtracking
 * - BFS: Explores all neighbors at current depth before going deeper
 */
public class GraphTraversal {

    // =========================== DFS Implementations ===========================

    /**
     * Recursive DFS traversal from a starting vertex
     * Time Complexity: O(V + E)
     * Space Complexity: O(V) for visited array + O(V) for recursion stack
     *
     * @param start starting vertex
     * @param graph adjacency list representation
     * @return list of visited vertices in DFS order
     */
    public List<Integer> dfsRecursive(int start, List<List<Integer>> graph) {
        List<Integer> result = new ArrayList<>();
        boolean[] visited = new boolean[graph.size()];
        dfsHelper(start, graph, visited, result);
        return result;
    }

    private void dfsHelper(int vertex, List<List<Integer>> graph,
                           boolean[] visited, List<Integer> result) {
        visited[vertex] = true;
        result.add(vertex);

        for (int neighbor : graph.get(vertex)) {
            if (!visited[neighbor]) {
                dfsHelper(neighbor, graph, visited, result);
            }
        }
    }

    /**
     * Iterative DFS traversal using explicit stack
     * Time Complexity: O(V + E)
     * Space Complexity: O(V) for visited array + O(V) for stack
     *
     * @param start starting vertex
     * @param graph adjacency list representation
     * @return list of visited vertices in DFS order
     */
    public List<Integer> dfsIterative(int start, List<List<Integer>> graph) {
        List<Integer> result = new ArrayList<>();
        boolean[] visited = new boolean[graph.size()];
        Stack<Integer> stack = new Stack<>();

        stack.push(start);

        while (!stack.isEmpty()) {
            int vertex = stack.pop();

            if (!visited[vertex]) {
                visited[vertex] = true;
                result.add(vertex);

                // Add neighbors in reverse order for same traversal as recursive
                List<Integer> neighbors = graph.get(vertex);
                for (int i = neighbors.size() - 1; i >= 0; i--) {
                    if (!visited[neighbors.get(i)]) {
                        stack.push(neighbors.get(i));
                    }
                }
            }
        }

        return result;
    }

    /**
     * DFS to check if there's a path between two vertices
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     *
     * @param start starting vertex
     * @param target target vertex
     * @param graph adjacency list representation
     * @return true if path exists, false otherwise
     */
    public boolean hasPathDFS(int start, int target, List<List<Integer>> graph) {
        if (start == target) return true;

        boolean[] visited = new boolean[graph.size()];
        return dfsPathHelper(start, target, graph, visited);
    }

    private boolean dfsPathHelper(int current, int target, List<List<Integer>> graph,
                                  boolean[] visited) {
        if (current == target) return true;

        visited[current] = true;

        for (int neighbor : graph.get(current)) {
            if (!visited[neighbor] && dfsPathHelper(neighbor, target, graph, visited)) {
                return true;
            }
        }

        return false;
    }

    // =========================== BFS Implementations ===========================

    /**
     * BFS traversal from a starting vertex
     * Time Complexity: O(V + E)
     * Space Complexity: O(V) for visited array + O(V) for queue
     *
     * @param start starting vertex
     * @param graph adjacency list representation
     * @return list of visited vertices in BFS order
     */
    public List<Integer> bfs(int start, List<List<Integer>> graph) {
        List<Integer> result = new ArrayList<>();
        boolean[] visited = new boolean[graph.size()];
        Queue<Integer> queue = new LinkedList<>();

        visited[start] = true;
        queue.offer(start);

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            result.add(vertex);

            for (int neighbor : graph.get(vertex)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                }
            }
        }

        return result;
    }

    /**
     * BFS to find shortest path (unweighted graph)
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     *
     * @param start starting vertex
     * @param graph adjacency list representation
     * @return array of distances from start vertex (-1 if unreachable)
     */
    public int[] shortestPathBFS(int start, List<List<Integer>> graph) {
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

    /**
     * BFS to check if there's a path between two vertices
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     *
     * @param start starting vertex
     * @param target target vertex
     * @param graph adjacency list representation
     * @return true if path exists, false otherwise
     */
    public boolean hasPathBFS(int start, int target, List<List<Integer>> graph) {
        if (start == target) return true;

        boolean[] visited = new boolean[graph.size()];
        Queue<Integer> queue = new LinkedList<>();

        visited[start] = true;
        queue.offer(start);

        while (!queue.isEmpty()) {
            int vertex = queue.poll();

            for (int neighbor : graph.get(vertex)) {
                if (neighbor == target) return true;

                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                }
            }
        }

        return false;
    }

    /**
     * BFS Level-order traversal (returns nodes level by level)
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     *
     * @param start starting vertex
     * @param graph adjacency list representation
     * @return list of lists, each containing vertices at that level
     */
    public List<List<Integer>> bfsLevelOrder(int start, List<List<Integer>> graph) {
        List<List<Integer>> result = new ArrayList<>();
        boolean[] visited = new boolean[graph.size()];
        Queue<Integer> queue = new LinkedList<>();

        visited[start] = true;
        queue.offer(start);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();

            for (int i = 0; i < levelSize; i++) {
                int vertex = queue.poll();
                currentLevel.add(vertex);

                for (int neighbor : graph.get(vertex)) {
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        queue.offer(neighbor);
                    }
                }
            }

            result.add(currentLevel);
        }

        return result;
    }

    // =========================== Connected Components ===========================

    /**
     * Find all connected components in undirected graph using DFS
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     *
     * @param graph adjacency list representation
     * @return list of connected components, each component is a list of vertices
     */
    public List<List<Integer>> findConnectedComponents(List<List<Integer>> graph) {
        List<List<Integer>> components = new ArrayList<>();
        boolean[] visited = new boolean[graph.size()];

        for (int i = 0; i < graph.size(); i++) {
            if (!visited[i]) {
                List<Integer> component = new ArrayList<>();
                dfsComponent(i, graph, visited, component);
                components.add(component);
            }
        }

        return components;
    }

    private void dfsComponent(int vertex, List<List<Integer>> graph,
                              boolean[] visited, List<Integer> component) {
        visited[vertex] = true;
        component.add(vertex);

        for (int neighbor : graph.get(vertex)) {
            if (!visited[neighbor]) {
                dfsComponent(neighbor, graph, visited, component);
            }
        }
    }

    /**
     * Count number of connected components
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     *
     * @param graph adjacency list representation
     * @return number of connected components
     */
    public int countConnectedComponents(List<List<Integer>> graph) {
        boolean[] visited = new boolean[graph.size()];
        int count = 0;

        for (int i = 0; i < graph.size(); i++) {
            if (!visited[i]) {
                dfsMarkComponent(i, graph, visited);
                count++;
            }
        }

        return count;
    }

    private void dfsMarkComponent(int vertex, List<List<Integer>> graph, boolean[] visited) {
        visited[vertex] = true;

        for (int neighbor : graph.get(vertex)) {
            if (!visited[neighbor]) {
                dfsMarkComponent(neighbor, graph, visited);
            }
        }
    }

    // =========================== Cycle Detection ===========================

    /**
     * Detect cycle in undirected graph using DFS
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     *
     * @param graph adjacency list representation
     * @return true if cycle exists, false otherwise
     */
    public boolean hasCycleUndirected(List<List<Integer>> graph) {
        boolean[] visited = new boolean[graph.size()];

        for (int i = 0; i < graph.size(); i++) {
            if (!visited[i]) {
                if (dfsCycleUndirected(i, -1, graph, visited)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean dfsCycleUndirected(int vertex, int parent, List<List<Integer>> graph,
                                       boolean[] visited) {
        visited[vertex] = true;

        for (int neighbor : graph.get(vertex)) {
            if (!visited[neighbor]) {
                if (dfsCycleUndirected(neighbor, vertex, graph, visited)) {
                    return true;
                }
            } else if (neighbor != parent) {
                return true;  // Back edge found, cycle detected
            }
        }

        return false;
    }

    /**
     * Detect cycle in directed graph using DFS (3-color approach)
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     *
     * @param graph adjacency list representation
     * @return true if cycle exists, false otherwise
     */
    public boolean hasCycleDirected(List<List<Integer>> graph) {
        int[] color = new int[graph.size()];  // 0=white, 1=gray, 2=black

        for (int i = 0; i < graph.size(); i++) {
            if (color[i] == 0) {  // White (unvisited)
                if (dfsCycleDirected(i, graph, color)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean dfsCycleDirected(int vertex, List<List<Integer>> graph, int[] color) {
        color[vertex] = 1;  // Gray (visiting)

        for (int neighbor : graph.get(vertex)) {
            if (color[neighbor] == 1) {  // Back edge to gray vertex
                return true;
            }
            if (color[neighbor] == 0 && dfsCycleDirected(neighbor, graph, color)) {
                return true;
            }
        }

        color[vertex] = 2;  // Black (visited)
        return false;
    }

    // =========================== Bipartite Check ===========================

    /**
     * Check if graph is bipartite using BFS (2-coloring)
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     *
     * @param graph adjacency list representation
     * @return true if graph is bipartite, false otherwise
     */
    public boolean isBipartite(List<List<Integer>> graph) {
        int[] color = new int[graph.size()];  // 0=uncolored, 1=red, -1=blue

        for (int i = 0; i < graph.size(); i++) {
            if (color[i] == 0) {
                if (!bfsBipartite(i, graph, color)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean bfsBipartite(int start, List<List<Integer>> graph, int[] color) {
        Queue<Integer> queue = new LinkedList<>();
        color[start] = 1;  // Start with red
        queue.offer(start);

        while (!queue.isEmpty()) {
            int vertex = queue.poll();

            for (int neighbor : graph.get(vertex)) {
                if (color[neighbor] == 0) {  // Uncolored
                    color[neighbor] = -color[vertex];  // Opposite color
                    queue.offer(neighbor);
                } else if (color[neighbor] == color[vertex]) {  // Same color
                    return false;
                }
            }
        }

        return true;
    }

    // =========================== Utility Methods ===========================

    /**
     * Create adjacency list from edge list
     *
     * @param vertices number of vertices
     * @param edges array of edges [src, dest]
     * @param directed true for directed graph, false for undirected
     * @return adjacency list representation
     */
    public static List<List<Integer>> createAdjacencyList(int vertices, int[][] edges,
                                                          boolean directed) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            if (!directed) {
                graph.get(edge[1]).add(edge[0]);
            }
        }

        return graph;
    }

    /**
     * Print adjacency list representation
     *
     * @param graph adjacency list representation
     */
    public static void printGraph(List<List<Integer>> graph) {
        for (int i = 0; i < graph.size(); i++) {
            System.out.print("Vertex " + i + ": ");
            for (int neighbor : graph.get(i)) {
                System.out.print(neighbor + " ");
            }
            System.out.println();
        }
    }
}