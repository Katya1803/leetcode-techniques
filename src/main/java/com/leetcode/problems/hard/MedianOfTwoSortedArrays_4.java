package com.leetcode.problems.hard;

import java.util.*;

/**
 * LeetCode 4: Median of Two Sorted Arrays
 * Multiple solution approaches from brute force to optimal binary search
 */
public class MedianOfTwoSortedArrays_4 {

    /**
     * Optimal Solution: Binary Search on Smaller Array
     * Time: O(log(min(m,n))), Space: O(1)
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Ensure nums1 is the smaller array
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int m = nums1.length;
        int n = nums2.length;
        int left = 0, right = m;

        while (left <= right) {
            // Partition nums1 at cut1, nums2 at cut2
            int cut1 = (left + right) / 2;
            int cut2 = (m + n + 1) / 2 - cut1;

            // Elements on left side of partition
            int maxLeft1 = (cut1 == 0) ? Integer.MIN_VALUE : nums1[cut1 - 1];
            int maxLeft2 = (cut2 == 0) ? Integer.MIN_VALUE : nums2[cut2 - 1];

            // Elements on right side of partition
            int minRight1 = (cut1 == m) ? Integer.MAX_VALUE : nums1[cut1];
            int minRight2 = (cut2 == n) ? Integer.MAX_VALUE : nums2[cut2];

            // Check if we found the correct partition
            if (maxLeft1 <= minRight2 && maxLeft2 <= minRight1) {
                // Found correct partition
                if ((m + n) % 2 == 0) {
                    // Even total length
                    return (Math.max(maxLeft1, maxLeft2) + Math.min(minRight1, minRight2)) / 2.0;
                } else {
                    // Odd total length
                    return Math.max(maxLeft1, maxLeft2);
                }
            } else if (maxLeft1 > minRight2) {
                // Too many elements from nums1 on left side
                right = cut1 - 1;
            } else {
                // Too few elements from nums1 on left side
                left = cut1 + 1;
            }
        }

        throw new IllegalArgumentException("Input arrays are not sorted");
    }

    /**
     * Merge-based Solution: Merge until median position
     * Time: O((m+n)/2) = O(m+n), Space: O(1)
     */
    public double findMedianSortedArraysMerge(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int total = m + n;
        boolean isEven = total % 2 == 0;
        int targetIndex = total / 2;

        int i = 0, j = 0;
        int prev = 0, curr = 0;

        // Merge until we reach median position(s)
        for (int count = 0; count <= targetIndex; count++) {
            prev = curr;

            if (i >= m) {
                curr = nums2[j++];
            } else if (j >= n) {
                curr = nums1[i++];
            } else if (nums1[i] <= nums2[j]) {
                curr = nums1[i++];
            } else {
                curr = nums2[j++];
            }
        }

        return isEven ? (prev + curr) / 2.0 : curr;
    }

    /**
     * Brute Force: Merge and Find Median
     * Time: O(m+n log(m+n)), Space: O(m+n)
     */
    public double findMedianSortedArraysBruteForce(int[] nums1, int[] nums2) {
        List<Integer> merged = new ArrayList<>();

        // Add all elements from both arrays
        for (int num : nums1) {
            merged.add(num);
        }
        for (int num : nums2) {
            merged.add(num);
        }

        // Sort the merged array
        Collections.sort(merged);

        int size = merged.size();
        if (size % 2 == 0) {
            return (merged.get(size / 2 - 1) + merged.get(size / 2)) / 2.0;
        } else {
            return merged.get(size / 2);
        }
    }

    /**
     * Alternative Binary Search: Search on Combined Range
     * Time: O(log(max - min) * log(m+n)), Space: O(1)
     */
    public double findMedianSortedArraysRangeSearch(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int total = m + n;
        boolean isEven = total % 2 == 0;

        if (isEven) {
            double left = findKthElement(nums1, nums2, total / 2);
            double right = findKthElement(nums1, nums2, total / 2 + 1);
            return (left + right) / 2.0;
        } else {
            return findKthElement(nums1, nums2, total / 2 + 1);
        }
    }

    private double findKthElement(int[] nums1, int[] nums2, int k) {
        int m = nums1.length;
        int n = nums2.length;

        // Ensure nums1 is smaller
        if (m > n) {
            return findKthElement(nums2, nums1, k);
        }

        int low = Math.max(0, k - n);
        int high = Math.min(k, m);

        while (low <= high) {
            int cut1 = (low + high) / 2;
            int cut2 = k - cut1;

            int left1 = (cut1 == 0) ? Integer.MIN_VALUE : nums1[cut1 - 1];
            int left2 = (cut2 == 0) ? Integer.MIN_VALUE : nums2[cut2 - 1];
            int right1 = (cut1 == m) ? Integer.MAX_VALUE : nums1[cut1];
            int right2 = (cut2 == n) ? Integer.MAX_VALUE : nums2[cut2];

            if (left1 <= right2 && left2 <= right1) {
                return Math.max(left1, left2);
            } else if (left1 > right2) {
                high = cut1 - 1;
            } else {
                low = cut1 + 1;
            }
        }

        return -1;
    }

    /**
     * Recursive Binary Search Solution
     * Time: O(log(m+n)), Space: O(log(m+n)) due to recursion
     */
    public double findMedianSortedArraysRecursive(int[] nums1, int[] nums2) {
        int total = nums1.length + nums2.length;

        if (total % 2 == 1) {
            return findKthElementRecursive(nums1, 0, nums2, 0, total / 2 + 1);
        } else {
            double left = findKthElementRecursive(nums1, 0, nums2, 0, total / 2);
            double right = findKthElementRecursive(nums1, 0, nums2, 0, total / 2 + 1);
            return (left + right) / 2.0;
        }
    }

    private double findKthElementRecursive(int[] nums1, int start1, int[] nums2, int start2, int k) {
        // Base cases
        if (start1 >= nums1.length) {
            return nums2[start2 + k - 1];
        }
        if (start2 >= nums2.length) {
            return nums1[start1 + k - 1];
        }
        if (k == 1) {
            return Math.min(nums1[start1], nums2[start2]);
        }

        // Binary search optimization
        int mid1 = start1 + k / 2 - 1;
        int mid2 = start2 + k / 2 - 1;

        int value1 = (mid1 >= nums1.length) ? Integer.MAX_VALUE : nums1[mid1];
        int value2 = (mid2 >= nums2.length) ? Integer.MAX_VALUE : nums2[mid2];

        if (value1 < value2) {
            return findKthElementRecursive(nums1, mid1 + 1, nums2, start2, k - k / 2);
        } else {
            return findKthElementRecursive(nums1, start1, nums2, mid2 + 1, k - k / 2);
        }
    }

    /**
     * Edge Case Handler: Wrapper with comprehensive edge case handling
     */
    public double findMedianSortedArraysRobust(int[] nums1, int[] nums2) {
        // Handle null inputs
        if (nums1 == null) nums1 = new int[0];
        if (nums2 == null) nums2 = new int[0];

        // Handle case where one array is empty
        if (nums1.length == 0 && nums2.length == 0) {
            throw new IllegalArgumentException("Both arrays cannot be empty");
        }

        if (nums1.length == 0) {
            return findMedianSingleArray(nums2);
        }

        if (nums2.length == 0) {
            return findMedianSingleArray(nums1);
        }

        // Use optimal binary search for non-empty arrays
        return findMedianSortedArrays(nums1, nums2);
    }

    private double findMedianSingleArray(int[] nums) {
        int n = nums.length;
        if (n % 2 == 1) {
            return nums[n / 2];
        } else {
            return (nums[n / 2 - 1] + nums[n / 2]) / 2.0;
        }
    }
}