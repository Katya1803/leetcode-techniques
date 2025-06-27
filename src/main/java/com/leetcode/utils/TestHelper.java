package com.leetcode.utils;

import java.util.*;

/**
 * Helper class for testing LeetCode solutions
 */
public class TestHelper {

    /**
     * Compare two arrays for equality
     */
    public static boolean arraysEqual(int[] arr1, int[] arr2) {
        return Arrays.equals(arr1, arr2);
    }

    /**
     * Compare two 2D arrays for equality
     */
    public static boolean arrays2DEqual(int[][] arr1, int[][] arr2) {
        if (arr1.length != arr2.length) return false;
        for (int i = 0; i < arr1.length; i++) {
            if (!Arrays.equals(arr1[i], arr2[i])) return false;
        }
        return true;
    }

    /**
     * Compare two lists for equality
     */
    public static <T> boolean listsEqual(List<T> list1, List<T> list2) {
        return Objects.equals(list1, list2);
    }

    /**
     * Compare two lists of lists (for problems returning List<List<Integer>>)
     */
    public static <T> boolean listOfListsEqual(List<List<T>> list1, List<List<T>> list2) {
        if (list1.size() != list2.size()) return false;

        // Sort both lists for comparison (order might not matter)
        List<List<T>> sorted1 = new ArrayList<>(list1);
        List<List<T>> sorted2 = new ArrayList<>(list2);

        sorted1.sort((a, b) -> {
            for (int i = 0; i < Math.min(a.size(), b.size()); i++) {
                int cmp = a.get(i).toString().compareTo(b.get(i).toString());
                if (cmp != 0) return cmp;
            }
            return Integer.compare(a.size(), b.size());
        });

        sorted2.sort((a, b) -> {
            for (int i = 0; i < Math.min(a.size(), b.size()); i++) {
                int cmp = a.get(i).toString().compareTo(b.get(i).toString());
                if (cmp != 0) return cmp;
            }
            return Integer.compare(a.size(), b.size());
        });

        return sorted1.equals(sorted2);
    }

    /**
     * Compare two linked lists
     */
    public static boolean linkedListsEqual(ListNode l1, ListNode l2) {
        while (l1 != null && l2 != null) {
            if (l1.val != l2.val) return false;
            l1 = l1.next;
            l2 = l2.next;
        }
        return l1 == null && l2 == null;
    }

    /**
     * Compare two binary trees
     */
    public static boolean treesEqual(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return true;
        if (t1 == null || t2 == null) return false;
        return t1.val == t2.val &&
                treesEqual(t1.left, t2.left) &&
                treesEqual(t1.right, t2.right);
    }

    /**
     * Print test result
     */
    public static void printTestResult(String testName, boolean passed) {
        System.out.println(testName + ": " + (passed ? "✅ PASSED" : "❌ FAILED"));
    }

    /**
     * Print test result with expected and actual values
     */
    public static <T> void printTestResult(String testName, T expected, T actual) {
        boolean passed = Objects.equals(expected, actual);
        System.out.println(testName + ": " + (passed ? "✅ PASSED" : "❌ FAILED"));
        if (!passed) {
            System.out.println("  Expected: " + expected);
            System.out.println("  Actual: " + actual);
        }
    }

    /**
     * Run a test case with timing
     */
    public static <T> void runTest(String testName, java.util.function.Supplier<T> testFunction, T expected) {
        long startTime = System.nanoTime();
        T result = testFunction.get();
        long endTime = System.nanoTime();

        boolean passed = Objects.equals(expected, result);
        double timeMs = (endTime - startTime) / 1_000_000.0;

        System.out.println(testName + ": " + (passed ? "✅ PASSED" : "❌ FAILED") +
                String.format(" (%.2f ms)", timeMs));
        if (!passed) {
            System.out.println("  Expected: " + expected);
            System.out.println("  Actual: " + result);
        }
    }

    /**
     * Generate random array for testing
     */
    public static int[] generateRandomArray(int size, int min, int max) {
        Random random = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(max - min + 1) + min;
        }
        return arr;
    }

    /**
     * Generate sorted array for testing
     */
    public static int[] generateSortedArray(int size, int min, int max) {
        int[] arr = generateRandomArray(size, min, max);
        Arrays.sort(arr);
        return arr;
    }
}
