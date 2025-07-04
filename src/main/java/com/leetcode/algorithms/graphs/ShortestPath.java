package com.leetcode.algorithms.graphs;

import java.util.*;

/**
 * Shortest Path Algorithms for Weighted and Unweighted Graphs
 *
 * This class implements various shortest path algorithms:
 * - BFS for unweighted graphs
 * - Dijkstra's algorithm for non-negative weights
 * - Bellman-Ford algorithm for graphs with negative weights
 * - Floyd-Warshall algorithm for all-pairs shortest paths
 * - A* algorithm for heuristic-based pathfinding
 *
 * Key Concepts:
 * - Single-source vs All-pairs shortest paths
 * - Handling negative weights and negative cycles
 * - Trade-offs between time/space complexity
 *
 */
public class ShortestPath {

    /**
     * Edge representation for weighted graphs
     */
    public static class Edge {
        public final int to;
        public final int weight;

        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "(" + to + ", " + weight + ")";
        }
    }

    /**
     * Node for priority queue in Dijkstra's algorithm
     */
    private static class DijkstraNode {
        int vertex;
        int distance;

        DijkstraNode(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }

    // =========================== BFS for Unweighted Graphs ===========================

    /**
     * BFS shortest path for unweighted graphs
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     *
     * @param graph adjacency list representation (unweighted)
     * @param start starting vertex
     * @return array of shortest distances from start (-1 if unreachable)
     */
    public int[] bfsShortestPath(List<List<Integer>> graph, int start) {
        int vertices = graph.size();
        int[] distance = new int[vertices];
        Arrays.fill(distance, -1);

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
     * BFS to find actual shortest path between two vertices
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     *
     * @param graph adjacency list representation (unweighted)
     * @param start starting vertex
     * @param end ending vertex
     * @return shortest path as list of vertices, empty if no path
     */
    public List<Integer> bfsPath(List<List<Integer>> graph, int start, int end) {
        int vertices = graph.size();
        int[] distance = new int[vertices];
        int[] parent = new int[vertices];
        Arrays.fill(distance, -1);
        Arrays.fill(parent, -1);

        Queue<Integer> queue = new LinkedList<>();
        distance[start] = 0;
        queue.offer(start);

        while (!queue.isEmpty()) {
            int vertex = queue.poll();

            if (vertex == end) {
                break;  // Found target
            }

            for (int neighbor : graph.get(vertex)) {
                if (distance[neighbor] == -1) {
                    distance[neighbor] = distance[vertex] + 1;
                    parent[neighbor] = vertex;
                    queue.offer(neighbor);
                }
            }
        }

        // Reconstruct path
        if (distance[end] == -1) {
            return new ArrayList<>();  // No path found
        }

        List<Integer> path = new ArrayList<>();
        int current = end;
        while (current != -1) {
            path.add(current);
            current = parent[current];
        }
        Collections.reverse(path);

        return path;
    }

    // =========================== Dijkstra's Algorithm ===========================

    /**
     * Dijkstra's algorithm for single-source shortest path (non-negative weights)
     * Time Complexity: O((V + E) log V) with binary heap
     * Space Complexity: O(V)
     *
     * @param graph adjacency list with weighted edges
     * @param start starting vertex
     * @return array of shortest distances from start (Integer.MAX_VALUE if unreachable)
     */
    public int[] dijkstra(List<List<Edge>> graph, int start) {
        int vertices = graph.size();
        int[] distance = new int[vertices];
        Arrays.fill(distance, Integer.MAX_VALUE);

        PriorityQueue<DijkstraNode> pq = new PriorityQueue<>(
                Comparator.comparingInt(node -> node.distance)
        );

        distance[start] = 0;
        pq.offer(new DijkstraNode(start, 0));

        while (!pq.isEmpty()) {
            DijkstraNode current = pq.poll();
            int vertex = current.vertex;
            int dist = current.distance;

            // Skip if we've already found a better path
            if (dist > distance[vertex]) {
                continue;
            }

            for (Edge edge : graph.get(vertex)) {
                int neighbor = edge.to;
                int newDist = distance[vertex] + edge.weight;

                if (newDist < distance[neighbor]) {
                    distance[neighbor] = newDist;
                    pq.offer(new DijkstraNode(neighbor, newDist));
                }
            }
        }

        return distance;
    }

    /**
     * Dijkstra's algorithm with path reconstruction
     * Time Complexity: O((V + E) log V)
     * Space Complexity: O(V)
     *
     * @param graph adjacency list with weighted edges
     * @param start starting vertex
     * @param end ending vertex
     * @return shortest path as list of vertices, empty if no path
     */
    public List<Integer> dijkstraPath(List<List<Edge>> graph, int start, int end) {
        int vertices = graph.size();
        int[] distance = new int[vertices];
        int[] parent = new int[vertices];
        Arrays.fill(distance, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        PriorityQueue<DijkstraNode> pq = new PriorityQueue<>(
                Comparator.comparingInt(node -> node.distance)
        );

        distance[start] = 0;
        pq.offer(new DijkstraNode(start, 0));

        while (!pq.isEmpty()) {
            DijkstraNode current = pq.poll();
            int vertex = current.vertex;
            int dist = current.distance;

            if (vertex == end) {
                break;  // Found target
            }

            if (dist > distance[vertex]) {
                continue;
            }

            for (Edge edge : graph.get(vertex)) {
                int neighbor = edge.to;
                int newDist = distance[vertex] + edge.weight;

                if (newDist < distance[neighbor]) {
                    distance[neighbor] = newDist;
                    parent[neighbor] = vertex;
                    pq.offer(new DijkstraNode(neighbor, newDist));
                }
            }
        }

        // Reconstruct path
        if (distance[end] == Integer.MAX_VALUE) {
            return new ArrayList<>();  // No path found
        }

        List<Integer> path = new ArrayList<>();
        int current = end;
        while (current != -1) {
            path.add(current);
            current = parent[current];
        }
        Collections.reverse(path);

        return path;
    }

    // =========================== Bellman-Ford Algorithm ===========================

    /**
     * Bellman-Ford algorithm for single-source shortest path (handles negative weights)
     * Time Complexity: O(VE)
     * Space Complexity: O(V)
     *
     * @param vertices number of vertices
     * @param edges list of all edges [from, to, weight]
     * @param start starting vertex
     * @return array of shortest distances, null if negative cycle detected
     */
    public int[] bellmanFord(int vertices, List<int[]> edges, int start) {
        int[] distance = new int[vertices];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[start] = 0;

        // Relax all edges V-1 times
        for (int i = 0; i < vertices - 1; i++) {
            for (int[] edge : edges) {
                int from = edge[0];
                int to = edge[1];
                int weight = edge[2];

                if (distance[from] != Integer.MAX_VALUE &&
                        distance[from] + weight < distance[to]) {
                    distance[to] = distance[from] + weight;
                }
            }
        }

        // Check for negative cycles
        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            int weight = edge[2];

            if (distance[from] != Integer.MAX_VALUE &&
                    distance[from] + weight < distance[to]) {
                return null;  // Negative cycle detected
            }
        }

        return distance;
    }

    /**
     * Detect negative cycle using Bellman-Ford
     * Time Complexity: O(VE)
     * Space Complexity: O(V)
     *
     * @param vertices number of vertices
     * @param edges list of all edges [from, to, weight]
     * @return true if negative cycle exists
     */
    public boolean hasNegativeCycle(int vertices, List<int[]> edges) {
        return bellmanFord(vertices, edges, 0) == null;
    }

    /**
     * Find vertices affected by negative cycles
     * Time Complexity: O(VE)
     * Space Complexity: O(V)
     *
     * @param vertices number of vertices
     * @param edges list of all edges [from, to, weight]
     * @param start starting vertex
     * @return set of vertices reachable from negative cycles
     */
    public Set<Integer> findNegativeCycleVertices(int vertices, List<int[]> edges, int start) {
        int[] distance = new int[vertices];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[start] = 0;

        // Relax all edges V-1 times
        for (int i = 0; i < vertices - 1; i++) {
            for (int[] edge : edges) {
                int from = edge[0];
                int to = edge[1];
                int weight = edge[2];

                if (distance[from] != Integer.MAX_VALUE &&
                        distance[from] + weight < distance[to]) {
                    distance[to] = distance[from] + weight;
                }
            }
        }

        // Find vertices affected by negative cycles
        Set<Integer> affected = new HashSet<>();
        for (int i = 0; i < vertices; i++) {
            for (int[] edge : edges) {
                int from = edge[0];
                int to = edge[1];
                int weight = edge[2];

                if (distance[from] != Integer.MAX_VALUE &&
                        distance[from] + weight < distance[to]) {
                    distance[to] = Integer.MIN_VALUE;
                    affected.add(to);
                }

                if (distance[from] == Integer.MIN_VALUE) {
                    distance[to] = Integer.MIN_VALUE;
                    affected.add(to);
                }
            }
        }

        return affected;
    }

    // =========================== Floyd-Warshall Algorithm ===========================

    /**
     * Floyd-Warshall algorithm for all-pairs shortest path
     * Time Complexity: O(V³)
     * Space Complexity: O(V²)
     *
     * @param graph adjacency matrix (Integer.MAX_VALUE for no edge)
     * @return matrix of shortest distances between all pairs
     */
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

    /**
     * Floyd-Warshall with path reconstruction
     * Time Complexity: O(V³)
     * Space Complexity: O(V²)
     *
     * @param graph adjacency matrix
     * @return pair of distance matrix and next vertex matrix for path reconstruction
     */
    public FloydWarshallResult floydWarshallWithPath(int[][] graph) {
        int vertices = graph.length;
        int[][] dist = new int[vertices][vertices];
        int[][] next = new int[vertices][vertices];

        // Initialize
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                dist[i][j] = graph[i][j];
                if (graph[i][j] != Integer.MAX_VALUE && i != j) {
                    next[i][j] = j;
                } else {
                    next[i][j] = -1;
                }
            }
        }

        // Floyd-Warshall with path tracking
        for (int k = 0; k < vertices; k++) {
            for (int i = 0; i < vertices; i++) {
                for (int j = 0; j < vertices; j++) {
                    if (dist[i][k] != Integer.MAX_VALUE &&
                            dist[k][j] != Integer.MAX_VALUE &&
                            dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }

        return new FloydWarshallResult(dist, next);
    }

    /**
     * Result class for Floyd-Warshall with path reconstruction
     */
    public static class FloydWarshallResult {
        public final int[][] distances;
        public final int[][] next;

        public FloydWarshallResult(int[][] distances, int[][] next) {
            this.distances = distances;
            this.next = next;
        }

        /**
         * Reconstruct path between two vertices
         *
         * @param start starting vertex
         * @param end ending vertex
         * @return path as list of vertices
         */
        public List<Integer> getPath(int start, int end) {
            if (next[start][end] == -1) {
                return new ArrayList<>();  // No path
            }

            List<Integer> path = new ArrayList<>();
            int current = start;
            path.add(current);

            while (current != end) {
                current = next[current][end];
                path.add(current);
            }

            return path;
        }
    }

    // =========================== A* Algorithm ===========================

    /**
     * A* algorithm for shortest path with heuristic
     * Time Complexity: O(b^d) where b is branching factor, d is depth
     * Space Complexity: O(b^d)
     *
     * @param graph adjacency list with weighted edges
     * @param start starting vertex
     * @param goal goal vertex
     * @param heuristic heuristic function h(vertex) -> estimated cost to goal
     * @return shortest path as list of vertices
     */
    public List<Integer> aStar(List<List<Edge>> graph, int start, int goal,
                               Function<Integer, Integer> heuristic) {
        PriorityQueue<AStarNode> openSet = new PriorityQueue<>(
                Comparator.comparingInt(node -> node.fScore)
        );

        Map<Integer, Integer> gScore = new HashMap<>();
        Map<Integer, Integer> parent = new HashMap<>();

        gScore.put(start, 0);
        openSet.offer(new AStarNode(start, 0, heuristic.apply(start)));

        while (!openSet.isEmpty()) {
            AStarNode current = openSet.poll();
            int vertex = current.vertex;

            if (vertex == goal) {
                // Reconstruct path
                List<Integer> path = new ArrayList<>();
                int curr = goal;
                while (curr != start) {
                    path.add(curr);
                    curr = parent.get(curr);
                }
                path.add(start);
                Collections.reverse(path);
                return path;
            }

            for (Edge edge : graph.get(vertex)) {
                int neighbor = edge.to;
                int tentativeGScore = gScore.get(vertex) + edge.weight;

                if (tentativeGScore < gScore.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    parent.put(neighbor, vertex);
                    gScore.put(neighbor, tentativeGScore);
                    int fScore = tentativeGScore + heuristic.apply(neighbor);
                    openSet.offer(new AStarNode(neighbor, tentativeGScore, fScore));
                }
            }
        }

        return new ArrayList<>();  // No path found
    }

    /**
     * Node for A* algorithm
     */
    private static class AStarNode {
        int vertex;
        int gScore;  // Cost from start to this node
        int fScore;  // gScore + heuristic

        AStarNode(int vertex, int gScore, int fScore) {
            this.vertex = vertex;
            this.gScore = gScore;
            this.fScore = fScore;
        }
    }

    /**
     * Functional interface for heuristic function
     */
    @FunctionalInterface
    public interface Function<T, R> {
        R apply(T t);
    }

    // =========================== Special Applications ===========================

    /**
     * Shortest path in grid with obstacles
     * Time Complexity: O(mn)
     * Space Complexity: O(mn)
     *
     * @param grid 2D grid (0 = free, 1 = obstacle)
     * @param start starting position [row, col]
     * @param end ending position [row, col]
     * @return shortest distance, -1 if no path
     */
    public int shortestPathInGrid(int[][] grid, int[] start, int[] end) {
        int rows = grid.length;
        int cols = grid[0].length;

        if (grid[start[0]][start[1]] == 1 || grid[end[0]][end[1]] == 1) {
            return -1;  // Start or end is blocked
        }

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];

        queue.offer(new int[]{start[0], start[1], 0});  // row, col, distance
        visited[start[0]][start[1]] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            int dist = current[2];

            if (row == end[0] && col == end[1]) {
                return dist;
            }

            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols &&
                        !visited[newRow][newCol] && grid[newRow][newCol] == 0) {

                    visited[newRow][newCol] = true;
                    queue.offer(new int[]{newRow, newCol, dist + 1});
                }
            }
        }

        return -1;  // No path found
    }

    /**
     * Shortest path with K stops (Cheapest Flights Within K Stops)
     * Time Complexity: O(K * E)
     * Space Complexity: O(V)
     *
     * @param n number of cities
     * @param flights array of flights [from, to, price]
     * @param src source city
     * @param dst destination city
     * @param k maximum number of stops
     * @return minimum cost, -1 if no path within k stops
     */
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        // Relax edges k+1 times (allowing k intermediate stops)
        for (int i = 0; i <= k; i++) {
            int[] temp = Arrays.copyOf(dist, n);

            for (int[] flight : flights) {
                int from = flight[0];
                int to = flight[1];
                int price = flight[2];

                if (dist[from] != Integer.MAX_VALUE) {
                    temp[to] = Math.min(temp[to], dist[from] + price);
                }
            }

            dist = temp;
        }

        return dist[dst] == Integer.MAX_VALUE ? -1 : dist[dst];
    }

    // =========================== Utility Methods ===========================

    /**
     * Convert adjacency matrix to adjacency list with edges
     *
     * @param matrix adjacency matrix
     * @return adjacency list representation
     */
    public static List<List<Edge>> matrixToAdjacencyList(int[][] matrix) {
        int vertices = matrix.length;
        List<List<Edge>> graph = new ArrayList<>();

        for (int i = 0; i < vertices; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                if (matrix[i][j] != 0 && matrix[i][j] != Integer.MAX_VALUE) {
                    graph.get(i).add(new Edge(j, matrix[i][j]));
                }
            }
        }

        return graph;
    }

    /**
     * Create weighted adjacency list from edge list
     *
     * @param vertices number of vertices
     * @param edges array of edges [from, to, weight]
     * @param directed true for directed graph
     * @return adjacency list representation
     */
    public static List<List<Edge>> createWeightedGraph(int vertices, int[][] edges,
                                                       boolean directed) {
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            graph.get(edge[0]).add(new Edge(edge[1], edge[2]));
            if (!directed) {
                graph.get(edge[1]).add(new Edge(edge[0], edge[2]));
            }
        }

        return graph;
    }

    /**
     * Print shortest distances
     *
     * @param distances array of distances
     * @param source source vertex
     */
    public static void printDistances(int[] distances, int source) {
        System.out.println("Shortest distances from vertex " + source + ":");
        for (int i = 0; i < distances.length; i++) {
            if (distances[i] == Integer.MAX_VALUE) {
                System.out.println("To " + i + ": INFINITY");
            } else {
                System.out.println("To " + i + ": " + distances[i]);
            }
        }
    }

    /**
     * Print path
     *
     * @param path list of vertices forming the path
     */
    public static void printPath(List<Integer> path) {
        if (path.isEmpty()) {
            System.out.println("No path found");
        } else {
            System.out.print("Path: ");
            for (int i = 0; i < path.size(); i++) {
                System.out.print(path.get(i));
                if (i < path.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println();
        }
    }
}