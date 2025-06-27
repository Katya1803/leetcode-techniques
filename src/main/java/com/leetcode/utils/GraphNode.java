package com.leetcode.utils;

import java.util.*;

/**
 * Definition for graph node used in LeetCode problems
 */
public class GraphNode {
    public int val;
    public List<GraphNode> neighbors;

    public GraphNode() {
        val = 0;
        neighbors = new ArrayList<>();
    }

    public GraphNode(int val) {
        this.val = val;
        neighbors = new ArrayList<>();
    }

    public GraphNode(int val, List<GraphNode> neighbors) {
        this.val = val;
        this.neighbors = neighbors;
    }

    /**
     * Create graph from adjacency list representation
     * @param adjList adjacency list where adjList[i] contains neighbors of node i
     * @return array of GraphNode representing the graph
     */
    public static GraphNode[] fromAdjacencyList(int[][] adjList) {
        if (adjList == null || adjList.length == 0) {
            return new GraphNode[0];
        }

        GraphNode[] nodes = new GraphNode[adjList.length];

        // Create all nodes first
        for (int i = 0; i < adjList.length; i++) {
            nodes[i] = new GraphNode(i);
        }

        // Add neighbors
        for (int i = 0; i < adjList.length; i++) {
            for (int neighbor : adjList[i]) {
                nodes[i].neighbors.add(nodes[neighbor]);
            }
        }

        return nodes;
    }

    /**
     * Convert graph to adjacency list
     * @param nodes array of graph nodes
     * @return adjacency list representation
     */
    public static int[][] toAdjacencyList(GraphNode[] nodes) {
        if (nodes == null || nodes.length == 0) {
            return new int[0][];
        }

        int[][] adjList = new int[nodes.length][];

        for (int i = 0; i < nodes.length; i++) {
            List<Integer> neighbors = new ArrayList<>();
            for (GraphNode neighbor : nodes[i].neighbors) {
                neighbors.add(neighbor.val);
            }
            adjList[i] = neighbors.stream().mapToInt(x -> x).toArray();
        }

        return adjList;
    }

    /**
     * Print graph using BFS
     */
    public static void printGraph(GraphNode startNode) {
        if (startNode == null) return;

        Set<GraphNode> visited = new HashSet<>();
        Queue<GraphNode> queue = new LinkedList<>();

        queue.offer(startNode);
        visited.add(startNode);

        while (!queue.isEmpty()) {
            GraphNode current = queue.poll();
            System.out.print("Node " + current.val + " -> ");

            List<Integer> neighborVals = new ArrayList<>();
            for (GraphNode neighbor : current.neighbors) {
                neighborVals.add(neighbor.val);
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
            System.out.println(neighborVals);
        }
    }

    @Override
    public String toString() {
        return "GraphNode{val=" + val + ", neighbors=" +
                Arrays.toString(neighbors.stream().map(n -> String.valueOf(n.val)).toArray()) + "}";
    }
}
