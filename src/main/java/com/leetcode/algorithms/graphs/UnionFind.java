package com.leetcode.algorithms.graphs;

import java.util.*;

/**
 * Union-Find (Disjoint Set Union) Data Structure
 *
 * Also known as Disjoint Set Union (DSU), this data structure efficiently
 * handles dynamic connectivity queries and union operations.
 *
 * Key Operations:
 * - Find: Determine which set an element belongs to
 * - Union: Merge two sets together
 *
 * Optimizations:
 * - Path Compression: Make find operations faster
 * - Union by Rank/Size: Keep trees balanced
 *
 * Applications:
 * - Kruskal's Minimum Spanning Tree algorithm
 * - Detecting cycles in undirected graphs
 * - Dynamic connectivity problems
 * - Image processing (connected components)
 * - Social network analysis
 */
public class UnionFind {

    private int[] parent;    // Parent array for each element
    private int[] rank;      // Rank (approximate depth) of each tree
    private int[] size;      // Size of each component
    private int components;  // Number of connected components

    // =========================== Constructors ===========================

    /**
     * Initialize Union-Find with n elements (0 to n-1)
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     *
     * @param n number of elements
     */
    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        size = new int[n];
        components = n;

        // Initially, each element is its own parent (self-loop)
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
            size[i] = 1;
        }
    }

    // =========================== Core Operations ===========================

    /**
     * Find the root of element x with path compression
     * Time Complexity: O(α(n)) amortized, where α is inverse Ackermann function
     * Space Complexity: O(1)
     *
     * Path compression: Make every node point directly to root
     *
     * @param x element to find root for
     * @return root of the set containing x
     */
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);  // Path compression
        }
        return parent[x];
    }

    /**
     * Find without path compression (for comparison)
     * Time Complexity: O(log n) to O(n) depending on tree structure
     *
     * @param x element to find root for
     * @return root of the set containing x
     */
    public int findWithoutCompression(int x) {
        while (parent[x] != x) {
            x = parent[x];
        }
        return x;
    }

    /**
     * Union two sets containing x and y using union by rank
     * Time Complexity: O(α(n)) amortized
     * Space Complexity: O(1)
     *
     * Union by rank: Always attach smaller tree under root of larger tree
     *
     * @param x first element
     * @param y second element
     * @return true if union was performed (elements were in different sets)
     */
    public boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY) {
            return false;  // Already in same set
        }

        // Union by rank
        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
            size[rootY] += size[rootX];
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
            size[rootX] += size[rootY];
        } else {
            parent[rootY] = rootX;
            size[rootX] += size[rootY];
            rank[rootX]++;  // Only increment rank when ranks are equal
        }

        components--;
        return true;
    }

    /**
     * Union by size instead of rank
     * Time Complexity: O(α(n)) amortized
     *
     * @param x first element
     * @param y second element
     * @return true if union was performed
     */
    public boolean unionBySize(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY) {
            return false;
        }

        // Always attach smaller tree to larger tree
        if (size[rootX] < size[rootY]) {
            parent[rootX] = rootY;
            size[rootY] += size[rootX];
        } else {
            parent[rootY] = rootX;
            size[rootX] += size[rootY];
        }

        components--;
        return true;
    }

    // =========================== Query Operations ===========================

    /**
     * Check if two elements are in the same connected component
     * Time Complexity: O(α(n)) amortized
     *
     * @param x first element
     * @param y second element
     * @return true if x and y are connected
     */
    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }

    /**
     * Get the size of the component containing element x
     * Time Complexity: O(α(n)) amortized
     *
     * @param x element
     * @return size of component containing x
     */
    public int getComponentSize(int x) {
        return size[find(x)];
    }

    /**
     * Get the number of connected components
     * Time Complexity: O(1)
     *
     * @return number of connected components
     */
    public int getComponentCount() {
        return components;
    }

    /**
     * Get all elements in the same component as x
     * Time Complexity: O(n)
     *
     * @param x element
     * @return list of all elements in same component as x
     */
    public List<Integer> getComponent(int x) {
        int root = find(x);
        List<Integer> component = new ArrayList<>();

        for (int i = 0; i < parent.length; i++) {
            if (find(i) == root) {
                component.add(i);
            }
        }

        return component;
    }

    /**
     * Get all connected components
     * Time Complexity: O(n)
     *
     * @return list of all connected components
     */
    public List<List<Integer>> getAllComponents() {
        Map<Integer, List<Integer>> componentMap = new HashMap<>();

        for (int i = 0; i < parent.length; i++) {
            int root = find(i);
            componentMap.computeIfAbsent(root, k -> new ArrayList<>()).add(i);
        }

        return new ArrayList<>(componentMap.values());
    }

    // =========================== Graph Applications ===========================

    /**
     * Count connected components in undirected graph
     * Time Complexity: O(E * α(V))
     * Space Complexity: O(V)
     *
     * @param n number of vertices
     * @param edges array of edges [u, v]
     * @return number of connected components
     */
    public static int countComponents(int n, int[][] edges) {
        UnionFind uf = new UnionFind(n);

        for (int[] edge : edges) {
            uf.union(edge[0], edge[1]);
        }

        return uf.getComponentCount();
    }

    /**
     * Detect cycle in undirected graph
     * Time Complexity: O(E * α(V))
     * Space Complexity: O(V)
     *
     * @param n number of vertices
     * @param edges array of edges [u, v]
     * @return true if graph contains cycle
     */
    public static boolean hasCycle(int n, int[][] edges) {
        UnionFind uf = new UnionFind(n);

        for (int[] edge : edges) {
            if (uf.connected(edge[0], edge[1])) {
                return true;  // Edge connects already connected components
            }
            uf.union(edge[0], edge[1]);
        }

        return false;
    }

    /**
     * Find redundant connection (cycle-forming edge)
     * Time Complexity: O(E * α(V))
     * Space Complexity: O(V)
     *
     * @param edges array of edges [u, v] in order they were added
     * @return first edge that creates a cycle
     */
    public static int[] findRedundantConnection(int[][] edges) {
        // Find maximum vertex to determine graph size
        int maxVertex = 0;
        for (int[] edge : edges) {
            maxVertex = Math.max(maxVertex, Math.max(edge[0], edge[1]));
        }

        UnionFind uf = new UnionFind(maxVertex + 1);

        for (int[] edge : edges) {
            if (uf.connected(edge[0], edge[1])) {
                return edge;  // This edge creates a cycle
            }
            uf.union(edge[0], edge[1]);
        }

        return new int[0];  // No cycle found
    }

    // =========================== Advanced Applications ===========================

    /**
     * Accounts merge problem using Union-Find
     * Time Complexity: O(N * M * α(N * M)) where N is accounts, M is emails per account
     *
     * @param accounts list of accounts, each account[0] is name, rest are emails
     * @return merged accounts
     */
    public static List<List<String>> accountsMerge(List<List<String>> accounts) {
        Map<String, Integer> emailToId = new HashMap<>();
        Map<String, String> emailToName = new HashMap<>();
        int id = 0;

        // Assign unique ID to each email
        for (List<String> account : accounts) {
            String name = account.get(0);
            for (int i = 1; i < account.size(); i++) {
                String email = account.get(i);
                if (!emailToId.containsKey(email)) {
                    emailToId.put(email, id++);
                }
                emailToName.put(email, name);
            }
        }

        UnionFind uf = new UnionFind(id);

        // Union emails within same account
        for (List<String> account : accounts) {
            int firstEmailId = emailToId.get(account.get(1));
            for (int i = 2; i < account.size(); i++) {
                uf.union(firstEmailId, emailToId.get(account.get(i)));
            }
        }

        // Group emails by root
        Map<Integer, List<String>> groups = new HashMap<>();
        for (String email : emailToId.keySet()) {
            int root = uf.find(emailToId.get(email));
            groups.computeIfAbsent(root, k -> new ArrayList<>()).add(email);
        }

        // Create result
        List<List<String>> result = new ArrayList<>();
        for (List<String> emails : groups.values()) {
            Collections.sort(emails);
            List<String> account = new ArrayList<>();
            account.add(emailToName.get(emails.get(0)));  // Add name
            account.addAll(emails);  // Add sorted emails
            result.add(account);
        }

        return result;
    }

    /**
     * Number of islands II (dynamic connectivity)
     * Time Complexity: O(K * α(M * N)) where K is number of operations
     *
     * @param m number of rows
     * @param n number of columns
     * @param positions array of positions where land is added
     * @return array showing number of islands after each operation
     */
    public static List<Integer> numIslands2(int m, int n, int[][] positions) {
        List<Integer> result = new ArrayList<>();
        UnionFind uf = new UnionFind(m * n);
        boolean[][] grid = new boolean[m][n];
        int islands = 0;

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] pos : positions) {
            int row = pos[0];
            int col = pos[1];

            if (grid[row][col]) {
                result.add(islands);  // Already land
                continue;
            }

            grid[row][col] = true;
            islands++;

            int currentId = row * n + col;

            // Check all 4 directions
            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];

                if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n &&
                        grid[newRow][newCol]) {

                    int neighborId = newRow * n + newCol;
                    if (uf.union(currentId, neighborId)) {
                        islands--;  // Two islands merged
                    }
                }
            }

            result.add(islands);
        }

        return result;
    }

    // =========================== Utility Methods ===========================

    /**
     * Reset Union-Find to initial state
     */
    public void reset() {
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
            rank[i] = 0;
            size[i] = 1;
        }
        components = parent.length;
    }

    /**
     * Get current state as string (for debugging)
     *
     * @return string representation of Union-Find state
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UnionFind State:\n");
        sb.append("Elements: ").append(parent.length).append("\n");
        sb.append("Components: ").append(components).append("\n");
        sb.append("Parent: ").append(Arrays.toString(parent)).append("\n");
        sb.append("Rank: ").append(Arrays.toString(rank)).append("\n");
        sb.append("Size: ").append(Arrays.toString(size)).append("\n");
        return sb.toString();
    }

    /**
     * Print all connected components
     */
    public void printComponents() {
        List<List<Integer>> allComponents = getAllComponents();
        System.out.println("Connected Components (" + allComponents.size() + "):");
        for (int i = 0; i < allComponents.size(); i++) {
            System.out.println("Component " + (i + 1) + ": " + allComponents.get(i));
        }
    }

    /**
     * Validate Union-Find invariants (for testing)
     *
     * @return true if all invariants hold
     */
    public boolean isValid() {
        // Check that each element's parent exists
        for (int i = 0; i < parent.length; i++) {
            if (parent[i] < 0 || parent[i] >= parent.length) {
                return false;
            }
        }

        // Check component count
        Set<Integer> roots = new HashSet<>();
        for (int i = 0; i < parent.length; i++) {
            roots.add(find(i));
        }

        return roots.size() == components;
    }
}