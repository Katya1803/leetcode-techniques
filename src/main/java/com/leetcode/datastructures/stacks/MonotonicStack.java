package com.leetcode.datastructures.stacks;

import java.util.*;

/**
 * Monotonic Stack implementations
 * A monotonic stack maintains elements in increasing or decreasing order
 *
 * Common use cases:
 * 1. Next/Previous Greater/Smaller Element
 * 2. Largest Rectangle in Histogram
 * 3. Daily Temperatures
 * 4. Stock Span Problems
 * 5. Maximum Rectangle in Binary Matrix
 */
public class MonotonicStack {

    /**
     * Next Greater Element I
     * Find next greater element for each element in nums1 from nums2
     * Time: O(n + m), Space: O(n)
     */
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> nextGreaterMap = new HashMap<>();
        Deque<Integer> stack = new ArrayDeque<>();

        // Build next greater elements for nums2
        for (int num : nums2) {
            while (!stack.isEmpty() && stack.peek() < num) {
                nextGreaterMap.put(stack.pop(), num);
            }
            stack.push(num);
        }

        // Build result for nums1
        int[] result = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            result[i] = nextGreaterMap.getOrDefault(nums1[i], -1);
        }

        return result;
    }

    /**
     * Next Greater Element II (Circular Array)
     * Time: O(n), Space: O(n)
     */
    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        Arrays.fill(result, -1);

        Deque<Integer> stack = new ArrayDeque<>();

        // Process array twice to handle circular nature
        for (int i = 0; i < 2 * n; i++) {
            int num = nums[i % n];

            while (!stack.isEmpty() && nums[stack.peek()] < num) {
                result[stack.pop()] = num;
            }

            if (i < n) {
                stack.push(i);
            }
        }

        return result;
    }

    /**
     * Previous Greater Element
     * Time: O(n), Space: O(n)
     */
    public int[] previousGreaterElement(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        Arrays.fill(result, -1);

        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            // Pop smaller or equal elements
            while (!stack.isEmpty() && nums[stack.peek()] <= nums[i]) {
                stack.pop();
            }

            if (!stack.isEmpty()) {
                result[i] = nums[stack.peek()];
            }

            stack.push(i);
        }

        return result;
    }

    /**
     * Daily Temperatures
     * Find how many days until a warmer temperature
     * Time: O(n), Space: O(n)
     */
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] result = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]) {
                int prevIndex = stack.pop();
                result[prevIndex] = i - prevIndex;
            }
            stack.push(i);
        }

        return result;
    }

    /**
     * Largest Rectangle in Histogram
     * Time: O(n), Space: O(n)
     */
    public int largestRectangleArea(int[] heights) {
        Deque<Integer> stack = new ArrayDeque<>();
        int maxArea = 0;
        int n = heights.length;

        for (int i = 0; i <= n; i++) {
            int currentHeight = (i == n) ? 0 : heights[i];

            while (!stack.isEmpty() && heights[stack.peek()] > currentHeight) {
                int height = heights[stack.pop()];
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                maxArea = Math.max(maxArea, height * width);
            }

            stack.push(i);
        }

        return maxArea;
    }

    /**
     * Maximum Rectangle in Binary Matrix
     * Time: O(m * n), Space: O(n)
     */
    public int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) return 0;

        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] heights = new int[cols];
        int maxArea = 0;

        for (int i = 0; i < rows; i++) {
            // Update heights array
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == '1') {
                    heights[j]++;
                } else {
                    heights[j] = 0;
                }
            }

            // Find largest rectangle in current histogram
            maxArea = Math.max(maxArea, largestRectangleArea(heights));
        }

        return maxArea;
    }

    /**
     * Stock Span Problem
     * Find span of stock's price for all n days
     * Time: O(n), Space: O(n)
     */
    public int[] stockSpan(int[] prices) {
        int n = prices.length;
        int[] spans = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            // Pop indices of days with prices <= current price
            while (!stack.isEmpty() && prices[stack.peek()] <= prices[i]) {
                stack.pop();
            }

            // Calculate span
            spans[i] = stack.isEmpty() ? i + 1 : i - stack.peek();

            stack.push(i);
        }

        return spans;
    }

    /**
     * Remove K Digits to make smallest number
     * Time: O(n), Space: O(n)
     */
    public String removeKdigits(String num, int k) {
        Deque<Character> stack = new ArrayDeque<>();

        for (char digit : num.toCharArray()) {
            // Remove larger digits from stack
            while (!stack.isEmpty() && k > 0 && stack.peek() > digit) {
                stack.pop();
                k--;
            }
            stack.push(digit);
        }

        // Remove remaining k digits from end
        while (k > 0 && !stack.isEmpty()) {
            stack.pop();
            k--;
        }

        // Build result
        StringBuilder result = new StringBuilder();
        boolean leadingZero = true;

        for (char digit : stack) {
            if (digit != '0' || !leadingZero) {
                result.append(digit);
                leadingZero = false;
            }
        }

        return result.length() == 0 ? "0" : result.toString();
    }

    /**
     * Sliding Window Maximum using Monotonic Deque
     * Time: O(n), Space: O(k)
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length == 0 || k == 0) return new int[0];

        Deque<Integer> deque = new ArrayDeque<>();
        int[] result = new int[nums.length - k + 1];

        for (int i = 0; i < nums.length; i++) {
            // Remove indices outside current window
            while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }

            // Remove indices of elements smaller than current
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            deque.offerLast(i);

            // Add to result when window is complete
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }

        return result;
    }

    /**
     * Sum of Subarray Minimums
     * Time: O(n), Space: O(n)
     */
    public int sumSubarrayMins(int[] arr) {
        final int MOD = 1_000_000_007;
        int n = arr.length;

        // Find previous smaller element for each element
        int[] prevSmaller = new int[n];
        Arrays.fill(prevSmaller, -1);
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            if (!stack.isEmpty()) {
                prevSmaller[i] = stack.peek();
            }
            stack.push(i);
        }

        // Find next smaller element for each element
        int[] nextSmaller = new int[n];
        Arrays.fill(nextSmaller, n);
        stack.clear();

        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                stack.pop();
            }
            if (!stack.isEmpty()) {
                nextSmaller[i] = stack.peek();
            }
            stack.push(i);
        }

        // Calculate sum
        long result = 0;
        for (int i = 0; i < n; i++) {
            long left = i - prevSmaller[i];
            long right = nextSmaller[i] - i;
            result = (result + (left * right % MOD) * arr[i] % MOD) % MOD;
        }

        return (int) result;
    }

    /**
     * Trapping Rain Water using Monotonic Stack
     * Time: O(n), Space: O(n)
     */
    public int trap(int[] height) {
        if (height.length == 0) return 0;

        Deque<Integer> stack = new ArrayDeque<>();
        int waterTrapped = 0;

        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int top = stack.pop();

                if (stack.isEmpty()) break;

                int distance = i - stack.peek() - 1;
                int boundedHeight = Math.min(height[i], height[stack.peek()]) - height[top];
                waterTrapped += distance * boundedHeight;
            }

            stack.push(i);
        }

        return waterTrapped;
    }

    /**
     * Find 132 Pattern using Monotonic Stack
     * Time: O(n), Space: O(n)
     */
    public boolean find132pattern(int[] nums) {
        if (nums.length < 3) return false;

        int n = nums.length;
        int[] mins = new int[n];
        mins[0] = nums[0];

        // Find minimum element to the left of each position
        for (int i = 1; i < n; i++) {
            mins[i] = Math.min(mins[i - 1], nums[i]);
        }

        Deque<Integer> stack = new ArrayDeque<>();

        // Traverse from right to left
        for (int j = n - 1; j >= 0; j--) {
            if (nums[j] > mins[j]) {
                // Remove elements <= mins[j] from stack
                while (!stack.isEmpty() && stack.peek() <= mins[j]) {
                    stack.pop();
                }

                // Check if we found a valid k
                if (!stack.isEmpty() && stack.peek() < nums[j]) {
                    return true;
                }

                stack.push(nums[j]);
            }
        }

        return false;
    }

    /**
     * Create Maximum Number using Monotonic Stack
     * Time: O(k * (m + n)), Space: O(k)
     */
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int m = nums1.length, n = nums2.length;
        int[] result = new int[k];

        for (int i = Math.max(0, k - n); i <= k && i <= m; i++) {
            int[] candidate = merge(
                    maxArray(nums1, i),
                    maxArray(nums2, k - i),
                    k
            );
            if (greater(candidate, 0, result, 0)) {
                result = candidate;
            }
        }

        return result;
    }

    private int[] maxArray(int[] nums, int k) {
        int[] result = new int[k];
        int j = 0;

        for (int i = 0; i < nums.length; i++) {
            while (j > 0 && result[j - 1] < nums[i] && nums.length - i + j > k) {
                j--;
            }
            if (j < k) {
                result[j++] = nums[i];
            }
        }

        return result;
    }

    private int[] merge(int[] nums1, int[] nums2, int k) {
        int[] result = new int[k];
        int i = 0, j = 0, r = 0;

        while (r < k) {
            if (greater(nums1, i, nums2, j)) {
                result[r++] = nums1[i++];
            } else {
                result[r++] = nums2[j++];
            }
        }

        return result;
    }

    private boolean greater(int[] nums1, int i, int[] nums2, int j) {
        int m = nums1.length, n = nums2.length;
        while (i < m && j < n && nums1[i] == nums2[j]) {
            i++;
            j++;
        }
        return j == n || (i < m && nums1[i] > nums2[j]);
    }
}