package com.leetcode.utils;

import java.util.*;

/**
 * Utility class for generating test inputs for LeetCode problems
 */
public class InputGenerator {
    private static final Random random = new Random();

    /**
     * Generate random integer array
     */
    public static int[] randomIntArray(int size, int min, int max) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(max - min + 1) + min;
        }
        return arr;
    }

    /**
     * Generate sorted integer array
     */
    public static int[] sortedIntArray(int size, int min, int max) {
        int[] arr = randomIntArray(size, min, max);
        Arrays.sort(arr);
        return arr;
    }

    /**
     * Generate random string
     */
    public static String randomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append((char) ('a' + random.nextInt(26)));
        }
        return sb.toString();
    }

    /**
     * Generate random string with specific characters
     */
    public static String randomString(int length, String chars) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    /**
     * Generate random 2D matrix
     */
    public static int[][] randomMatrix(int rows, int cols, int min, int max) {
        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextInt(max - min + 1) + min;
            }
        }
        return matrix;
    }

    /**
     * Generate random linked list
     */
    public static ListNode randomLinkedList(int size, int min, int max) {
        if (size <= 0) return null;

        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        for (int i = 0; i < size; i++) {
            current.next = new ListNode(random.nextInt(max - min + 1) + min);
            current = current.next;
        }

        return dummy.next;
    }

    /**
     * Generate random binary tree
     */
    public static TreeNode randomBinaryTree(int maxDepth, int min, int max) {
        return randomBinaryTreeHelper(maxDepth, min, max, 0);
    }

    private static TreeNode randomBinaryTreeHelper(int maxDepth, int min, int max, int currentDepth) {
        if (currentDepth >= maxDepth || random.nextDouble() < 0.3) {
            return null;
        }

        TreeNode node = new TreeNode(random.nextInt(max - min + 1) + min);
        node.left = randomBinaryTreeHelper(maxDepth, min, max, currentDepth + 1);
        node.right = randomBinaryTreeHelper(maxDepth, min, max, currentDepth + 1);

        return node;
    }

    /**
     * Generate edge cases for arrays
     */
    public static List<int[]> getArrayEdgeCases() {
        List<int[]> edgeCases = new ArrayList<>();
        edgeCases.add(new int[]{}); // Empty array
        edgeCases.add(new int[]{1}); // Single element
        edgeCases.add(new int[]{1, 2}); // Two elements
        edgeCases.add(new int[]{1, 1, 1}); // All same elements
        edgeCases.add(new int[]{1, 2, 3, 4, 5}); // Sorted ascending
        edgeCases.add(new int[]{5, 4, 3, 2, 1}); // Sorted descending
        edgeCases.add(new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE}); // Min/Max values
        return edgeCases;
    }

    /**
     * Generate edge cases for strings
     */
    public static List<String> getStringEdgeCases() {
        List<String> edgeCases = new ArrayList<>();
        edgeCases.add(""); // Empty string
        edgeCases.add("a"); // Single character
        edgeCases.add("aa"); // Repeated character
        edgeCases.add("abc"); // All different
        edgeCases.add("abccba"); // Palindrome
        edgeCases.add("abcdefghijklmnopqrstuvwxyz"); // All lowercase letters
        return edgeCases;
    }
}