package com.leetcode.algorithms.advanced;

import java.util.*;

/**
 * Divide and Conquer Algorithms
 *
 * This class implements various divide and conquer algorithms that solve problems
 * by breaking them into smaller subproblems, solving recursively, and combining results.
 */
public class DivideAndConquer {

    // =========================== Classic Sorting Algorithms ===========================

    /**
     * Merge Sort - classic divide and conquer sorting
     * Time Complexity: O(n log n)
     * Space Complexity: O(n)
     */
    public void mergeSort(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        mergeSortHelper(arr, 0, arr.length - 1);
    }

    private void mergeSortHelper(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            mergeSortHelper(arr, left, mid);
            mergeSortHelper(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private void merge(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        while (j <= right) {
            temp[k++] = arr[j++];
        }

        for (i = 0; i < temp.length; i++) {
            arr[left + i] = temp[i];
        }
    }

    /**
     * Quick Sort - divide and conquer with pivot partitioning
     * Time Complexity: O(n log n) average, O(n^2) worst case
     * Space Complexity: O(log n) average
     */
    public void quickSort(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        quickSortHelper(arr, 0, arr.length - 1);
    }

    private void quickSortHelper(int[] arr, int low, int high) {
        if (low < high) {
            int pivot = partition(arr, low, high);
            quickSortHelper(arr, low, pivot - 1);
            quickSortHelper(arr, pivot + 1, high);
        }
    }

    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // =========================== Array Problems ===========================

    /**
     * Maximum Subarray Sum - Kadane's Algorithm using Divide and Conquer
     * Time Complexity: O(n log n)
     * Space Complexity: O(log n)
     */
    public int maxSubarraySum(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        return maxSubarrayHelper(nums, 0, nums.length - 1);
    }

    private int maxSubarrayHelper(int[] nums, int left, int right) {
        if (left == right) return nums[left];

        int mid = left + (right - left) / 2;

        int leftMax = maxSubarrayHelper(nums, left, mid);
        int rightMax = maxSubarrayHelper(nums, mid + 1, right);
        int crossMax = maxCrossingSum(nums, left, mid, right);

        return Math.max(Math.max(leftMax, rightMax), crossMax);
    }

    private int maxCrossingSum(int[] nums, int left, int mid, int right) {
        int leftSum = Integer.MIN_VALUE;
        int sum = 0;

        for (int i = mid; i >= left; i--) {
            sum += nums[i];
            leftSum = Math.max(leftSum, sum);
        }

        int rightSum = Integer.MIN_VALUE;
        sum = 0;

        for (int i = mid + 1; i <= right; i++) {
            sum += nums[i];
            rightSum = Math.max(rightSum, sum);
        }

        return leftSum + rightSum;
    }

    /**
     * Count Inversions in array
     * Time Complexity: O(n log n)
     * Space Complexity: O(n)
     */
    public long countInversions(int[] arr) {
        if (arr == null || arr.length <= 1) return 0;
        int[] temp = new int[arr.length];
        return mergeSortAndCount(arr, temp, 0, arr.length - 1);
    }

    private long mergeSortAndCount(int[] arr, int[] temp, int left, int right) {
        long invCount = 0;

        if (left < right) {
            int mid = left + (right - left) / 2;

            invCount += mergeSortAndCount(arr, temp, left, mid);
            invCount += mergeSortAndCount(arr, temp, mid + 1, right);
            invCount += mergeAndCount(arr, temp, left, mid, right);
        }

        return invCount;
    }

    private long mergeAndCount(int[] arr, int[] temp, int left, int mid, int right) {
        int i = left, j = mid + 1, k = left;
        long invCount = 0;

        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
                invCount += (mid - i + 1);
            }
        }

        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        while (j <= right) {
            temp[k++] = arr[j++];
        }

        for (i = left; i <= right; i++) {
            arr[i] = temp[i];
        }

        return invCount;
    }

    /**
     * Find k-th smallest element using QuickSelect
     * Time Complexity: O(n) average, O(n^2) worst case
     * Space Complexity: O(1)
     */
    public int quickSelect(int[] nums, int k) {
        return quickSelectHelper(nums, 0, nums.length - 1, k - 1);
    }

    private int quickSelectHelper(int[] nums, int left, int right, int k) {
        if (left == right) return nums[left];

        int pivotIndex = partition(nums, left, right);

        if (k == pivotIndex) {
            return nums[k];
        } else if (k < pivotIndex) {
            return quickSelectHelper(nums, left, pivotIndex - 1, k);
        } else {
            return quickSelectHelper(nums, pivotIndex + 1, right, k);
        }
    }

    // =========================== Matrix Operations ===========================

    /**
     * Matrix Multiplication using Strassen's Algorithm
     * Time Complexity: O(n^2.807)
     * Space Complexity: O(n^2)
     */
    public int[][] matrixMultiply(int[][] A, int[][] B) {
        int n = A.length;

        if (n <= 2) {
            return standardMatrixMultiply(A, B);
        }

        // Pad matrices to power of 2 if needed
        int size = nextPowerOfTwo(n);
        int[][] paddedA = padMatrix(A, size);
        int[][] paddedB = padMatrix(B, size);

        int[][] result = strassenMultiply(paddedA, paddedB);

        // Extract original size result
        return extractMatrix(result, n);
    }

    private int[][] strassenMultiply(int[][] A, int[][] B) {
        int n = A.length;

        if (n == 1) {
            return new int[][]{{A[0][0] * B[0][0]}};
        }

        int mid = n / 2;

        // Divide matrices into quarters
        int[][] A11 = new int[mid][mid], A12 = new int[mid][mid];
        int[][] A21 = new int[mid][mid], A22 = new int[mid][mid];
        int[][] B11 = new int[mid][mid], B12 = new int[mid][mid];
        int[][] B21 = new int[mid][mid], B22 = new int[mid][mid];

        divideMatrix(A, A11, 0, 0);
        divideMatrix(A, A12, 0, mid);
        divideMatrix(A, A21, mid, 0);
        divideMatrix(A, A22, mid, mid);

        divideMatrix(B, B11, 0, 0);
        divideMatrix(B, B12, 0, mid);
        divideMatrix(B, B21, mid, 0);
        divideMatrix(B, B22, mid, mid);

        // Calculate 7 products (Strassen's method)
        int[][] M1 = strassenMultiply(addMatrix(A11, A22), addMatrix(B11, B22));
        int[][] M2 = strassenMultiply(addMatrix(A21, A22), B11);
        int[][] M3 = strassenMultiply(A11, subtractMatrix(B12, B22));
        int[][] M4 = strassenMultiply(A22, subtractMatrix(B21, B11));
        int[][] M5 = strassenMultiply(addMatrix(A11, A12), B22);
        int[][] M6 = strassenMultiply(subtractMatrix(A21, A11), addMatrix(B11, B12));
        int[][] M7 = strassenMultiply(subtractMatrix(A12, A22), addMatrix(B21, B22));

        // Calculate result quarters
        int[][] C11 = addMatrix(subtractMatrix(addMatrix(M1, M4), M5), M7);
        int[][] C12 = addMatrix(M3, M5);
        int[][] C21 = addMatrix(M2, M4);
        int[][] C22 = addMatrix(subtractMatrix(addMatrix(M1, M3), M2), M6);

        // Combine result
        int[][] result = new int[n][n];
        combineMatrix(C11, result, 0, 0);
        combineMatrix(C12, result, 0, mid);
        combineMatrix(C21, result, mid, 0);
        combineMatrix(C22, result, mid, mid);

        return result;
    }

    private int[][] standardMatrixMultiply(int[][] A, int[][] B) {
        int n = A.length;
        int[][] result = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    result[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return result;
    }

    private void divideMatrix(int[][] source, int[][] dest, int row, int col) {
        for (int i = 0; i < dest.length; i++) {
            for (int j = 0; j < dest.length; j++) {
                dest[i][j] = source[i + row][j + col];
            }
        }
    }

    private void combineMatrix(int[][] source, int[][] dest, int row, int col) {
        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < source.length; j++) {
                dest[i + row][j + col] = source[i][j];
            }
        }
    }

    private int[][] addMatrix(int[][] A, int[][] B) {
        int n = A.length;
        int[][] result = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = A[i][j] + B[i][j];
            }
        }

        return result;
    }

    private int[][] subtractMatrix(int[][] A, int[][] B) {
        int n = A.length;
        int[][] result = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = A[i][j] - B[i][j];
            }
        }

        return result;
    }

    // =========================== Geometric Problems ===========================

    /**
     * Closest Pair of Points using Divide and Conquer
     * Time Complexity: O(n log n)
     * Space Complexity: O(n)
     */
    public double closestPairDistance(Point[] points) {
        if (points == null || points.length < 2) return Double.MAX_VALUE;

        Arrays.sort(points, Comparator.comparingDouble(p -> p.x));
        return closestPairHelper(points, 0, points.length - 1);
    }

    private double closestPairHelper(Point[] points, int left, int right) {
        if (right - left <= 3) {
            return bruteForceClosest(points, left, right);
        }

        int mid = left + (right - left) / 2;
        Point midPoint = points[mid];

        double leftMin = closestPairHelper(points, left, mid);
        double rightMin = closestPairHelper(points, mid + 1, right);

        double minDist = Math.min(leftMin, rightMin);

        // Find closest pair across the divide
        List<Point> strip = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            if (Math.abs(points[i].x - midPoint.x) < minDist) {
                strip.add(points[i]);
            }
        }

        return Math.min(minDist, stripClosest(strip, minDist));
    }

    private double bruteForceClosest(Point[] points, int left, int right) {
        double minDist = Double.MAX_VALUE;

        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                minDist = Math.min(minDist, distance(points[i], points[j]));
            }
        }

        return minDist;
    }

    private double stripClosest(List<Point> strip, double minDist) {
        strip.sort(Comparator.comparingDouble(p -> p.y));

        for (int i = 0; i < strip.size(); i++) {
            for (int j = i + 1; j < strip.size() &&
                    (strip.get(j).y - strip.get(i).y) < minDist; j++) {
                minDist = Math.min(minDist, distance(strip.get(i), strip.get(j)));
            }
        }

        return minDist;
    }

    private double distance(Point p1, Point p2) {
        return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
    }

    public static class Point {
        double x, y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    // =========================== Number Theory ===========================

    /**
     * Fast Exponentiation using Divide and Conquer
     * Time Complexity: O(log n)
     * Space Complexity: O(log n)
     */
    public long fastPower(long base, long exp) {
        if (exp == 0) return 1;
        if (exp == 1) return base;

        if (exp % 2 == 0) {
            long half = fastPower(base, exp / 2);
            return half * half;
        } else {
            return base * fastPower(base, exp - 1);
        }
    }

    /**
     * Fast Exponentiation with Modulo
     * Time Complexity: O(log n)
     * Space Complexity: O(log n)
     */
    public long fastPowerMod(long base, long exp, long mod) {
        if (exp == 0) return 1;
        if (exp == 1) return base % mod;

        if (exp % 2 == 0) {
            long half = fastPowerMod(base, exp / 2, mod);
            return (half * half) % mod;
        } else {
            return (base * fastPowerMod(base, exp - 1, mod)) % mod;
        }
    }

    /**
     * Fast Fibonacci using Matrix Exponentiation
     * Time Complexity: O(log n)
     * Space Complexity: O(log n)
     */
    public long fibonacci(int n) {
        if (n <= 1) return n;

        long[][] result = matrixPower(new long[][]{{1, 1}, {1, 0}}, n - 1);
        return result[0][0];
    }

    private long[][] matrixPower(long[][] matrix, int n) {
        if (n == 1) return matrix;

        if (n % 2 == 0) {
            long[][] half = matrixPower(matrix, n / 2);
            return matrixMultiply(half, half);
        } else {
            return matrixMultiply(matrix, matrixPower(matrix, n - 1));
        }
    }

    private long[][] matrixMultiply(long[][] A, long[][] B) {
        long[][] result = new long[2][2];

        result[0][0] = A[0][0] * B[0][0] + A[0][1] * B[1][0];
        result[0][1] = A[0][0] * B[0][1] + A[0][1] * B[1][1];
        result[1][0] = A[1][0] * B[0][0] + A[1][1] * B[1][0];
        result[1][1] = A[1][0] * B[0][1] + A[1][1] * B[1][1];

        return result;
    }

    // =========================== String Problems ===========================

    /**
     * Karatsuba Algorithm for Large Number Multiplication
     * Time Complexity: O(n^1.585)
     * Space Complexity: O(n)
     */
    public String karatsubaMultiply(String num1, String num2) {
        int n1 = num1.length();
        int n2 = num2.length();

        if (n1 == 0 || n2 == 0) return "0";

        // Make lengths equal by padding zeros
        int maxLen = Math.max(n1, n2);
        num1 = padZeros(num1, maxLen);
        num2 = padZeros(num2, maxLen);

        return removeLeadingZeros(karatsubaHelper(num1, num2));
    }

    private String karatsubaHelper(String num1, String num2) {
        int n = num1.length();

        if (n == 1) {
            int product = (num1.charAt(0) - '0') * (num2.charAt(0) - '0');
            return String.valueOf(product);
        }

        int mid = n / 2;
        String high1 = num1.substring(0, mid);
        String low1 = num1.substring(mid);
        String high2 = num2.substring(0, mid);
        String low2 = num2.substring(mid);

        String z0 = karatsubaHelper(low1, low2);
        String z1 = karatsubaHelper(addStrings(low1, high1), addStrings(low2, high2));
        String z2 = karatsubaHelper(high1, high2);

        String temp = subtractStrings(subtractStrings(z1, z2), z0);

        return addStrings(addStrings(multiplyByPowerOf10(z2, 2 * (n - mid)),
                multiplyByPowerOf10(temp, n - mid)), z0);
    }

    // =========================== Peak Finding ===========================

    /**
     * Find Peak Element using Divide and Conquer
     * Time Complexity: O(log n)
     * Space Complexity: O(log n)
     */
    public int findPeakElement(int[] nums) {
        return findPeakHelper(nums, 0, nums.length - 1);
    }

    private int findPeakHelper(int[] nums, int left, int right) {
        if (left == right) return left;

        int mid = left + (right - left) / 2;

        if (nums[mid] > nums[mid + 1]) {
            return findPeakHelper(nums, left, mid);
        } else {
            return findPeakHelper(nums, mid + 1, right);
        }
    }

    /**
     * Find Peak in 2D Matrix
     * Time Complexity: O(n log m) where n is rows, m is columns
     * Space Complexity: O(log m)
     */
    public int[] findPeakGrid(int[][] mat) {
        int left = 0, right = mat[0].length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int maxRow = findMaxRowInColumn(mat, mid);

            boolean leftBigger = mid > 0 && mat[maxRow][mid - 1] > mat[maxRow][mid];
            boolean rightBigger = mid < mat[0].length - 1 && mat[maxRow][mid + 1] > mat[maxRow][mid];

            if (!leftBigger && !rightBigger) {
                return new int[]{maxRow, mid};
            } else if (leftBigger) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return new int[]{-1, -1};
    }

    private int findMaxRowInColumn(int[][] mat, int col) {
        int maxRow = 0;
        for (int i = 1; i < mat.length; i++) {
            if (mat[i][col] > mat[maxRow][col]) {
                maxRow = i;
            }
        }
        return maxRow;
    }

    // =========================== Utility Methods ===========================

    private int nextPowerOfTwo(int n) {
        int power = 1;
        while (power < n) {
            power <<= 1;
        }
        return power;
    }

    private int[][] padMatrix(int[][] matrix, int size) {
        int[][] padded = new int[size][size];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                padded[i][j] = matrix[i][j];
            }
        }
        return padded;
    }

    private int[][] extractMatrix(int[][] matrix, int size) {
        int[][] result = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i][j] = matrix[i][j];
            }
        }
        return result;
    }

    private String padZeros(String num, int length) {
        StringBuilder sb = new StringBuilder(num);
        while (sb.length() < length) {
            sb.insert(0, '0');
        }
        return sb.toString();
    }

    private String removeLeadingZeros(String num) {
        int firstnonzero = 0;
        for (int i = 0; i < num.length(); i++) {
            if (num.charAt(i) != '0') {
                firstnonzero = i;
                break;
            }
        }
        return firstnonzero == 0 ? num : num.substring(firstnonzero);
    }

    private String addStrings(String num1, String num2) {
        StringBuilder result = new StringBuilder();
        int carry = 0;
        int i = num1.length() - 1;
        int j = num2.length() - 1;

        while (i >= 0 || j >= 0 || carry != 0) {
            int digit1 = i >= 0 ? num1.charAt(i) - '0' : 0;
            int digit2 = j >= 0 ? num2.charAt(j) - '0' : 0;
            int sum = digit1 + digit2 + carry;

            result.append(sum % 10);
            carry = sum / 10;

            i--;
            j--;
        }

        return result.reverse().toString();
    }

    private String subtractStrings(String num1, String num2) {
        // Assuming num1 >= num2
        StringBuilder result = new StringBuilder();
        int borrow = 0;
        int i = num1.length() - 1;
        int j = num2.length() - 1;

        while (i >= 0) {
            int digit1 = num1.charAt(i) - '0' - borrow;
            int digit2 = j >= 0 ? num2.charAt(j) - '0' : 0;

            if (digit1 < digit2) {
                digit1 += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }

            result.append(digit1 - digit2);
            i--;
            j--;
        }

        return removeLeadingZeros(result.reverse().toString());
    }

    private String multiplyByPowerOf10(String num, int power) {
        if (num.equals("0")) return num;

        StringBuilder result = new StringBuilder(num);
        for (int i = 0; i < power; i++) {
            result.append('0');
        }

        return result.toString();
    }

    /**
     * Validate divide and conquer result
     */
    public boolean validateResult(int[] input, int[] expected, int[] actual) {
        return Arrays.equals(expected, actual);
    }

    /**
     * Measure execution time of divide and conquer algorithm
     */
    public long measureExecutionTime(Runnable algorithm) {
        long startTime = System.nanoTime();
        algorithm.run();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    /**
     * Print array for debugging
     */
    public static void printArray(int[] arr, String label) {
        System.out.println(label + ": " + Arrays.toString(arr));
    }

    /**
     * Print matrix for debugging
     */
    public static void printMatrix(int[][] matrix, String label) {
        System.out.println(label + ":");
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println();
    }
}