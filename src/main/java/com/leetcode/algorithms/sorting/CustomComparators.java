package com.leetcode.algorithms.sorting;

import java.util.*;

/**
 * Custom sorting with comparators for complex scenarios
 */
public class CustomComparators {

    /**
     * Sort array by frequency (least frequent first)
     * Time: O(n log n), Space: O(n)
     */
    public int[] frequencySort(int[] nums) {
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        return Arrays.stream(nums)
                .boxed()
                .sorted((a, b) -> {
                    int freqCompare = freqMap.get(a) - freqMap.get(b);
                    if (freqCompare == 0) {
                        return b - a; // If same frequency, larger number first
                    }
                    return freqCompare;
                })
                .mapToInt(Integer::intValue)
                .toArray();
    }

    /**
     * Sort strings by length, then lexicographically
     * Time: O(n log n), Space: O(1)
     */
    public String[] sortStringsByLength(String[] strs) {
        Arrays.sort(strs, (a, b) -> {
            if (a.length() != b.length()) {
                return a.length() - b.length();
            }
            return a.compareTo(b);
        });
        return strs;
    }

    /**
     * Sort intervals by start time
     * Time: O(n log n), Space: O(1)
     */
    public int[][] sortIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> {
            if (a[0] != b[0]) {
                return a[0] - b[0]; // Sort by start time
            }
            return a[1] - b[1]; // If same start, sort by end time
        });
        return intervals;
    }

    /**
     * Custom sort for meeting rooms problem
     * Time: O(n log n), Space: O(1)
     */
    public boolean canAttendMeetings(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < intervals[i - 1][1]) {
                return false; // Overlap found
            }
        }

        return true;
    }

    /**
     * Sort points by distance from origin
     * Time: O(n log n), Space: O(1)
     */
    public int[][] kClosestPointsToOrigin(int[][] points, int k) {
        Arrays.sort(points, (a, b) -> {
            int dist1 = a[0] * a[0] + a[1] * a[1];
            int dist2 = b[0] * b[0] + b[1] * b[1];
            return dist1 - dist2;
        });

        return Arrays.copyOfRange(points, 0, k);
    }

    /**
     * Sort by custom priority (odd numbers first, then even)
     * Time: O(n log n), Space: O(1)
     */
    public int[] sortByParity(int[] nums) {
        Integer[] boxed = Arrays.stream(nums).boxed().toArray(Integer[]::new);

        Arrays.sort(boxed, (a, b) -> {
            boolean aOdd = (a % 2 == 1);
            boolean bOdd = (b % 2 == 1);

            if (aOdd && !bOdd) return -1; // a is odd, b is even
            if (!aOdd && bOdd) return 1;  // a is even, b is odd
            return a - b; // Same parity, sort by value
        });

        return Arrays.stream(boxed).mapToInt(Integer::intValue).toArray();
    }

    /**
     * Sort students by grade (descending), then by name (ascending)
     * Time: O(n log n), Space: O(1)
     */
    public static class Student {
        String name;
        int grade;

        public Student(String name, int grade) {
            this.name = name;
            this.grade = grade;
        }

        @Override
        public String toString() {
            return name + "(" + grade + ")";
        }
    }

    public Student[] sortStudents(Student[] students) {
        Arrays.sort(students, (a, b) -> {
            if (a.grade != b.grade) {
                return b.grade - a.grade; // Higher grade first
            }
            return a.name.compareTo(b.name); // Same grade, alphabetical order
        });
        return students;
    }

    /**
     * Largest number formed by concatenating array elements
     * Time: O(n log n), Space: O(n)
     */
    public String largestNumber(int[] nums) {
        String[] strs = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            strs[i] = String.valueOf(nums[i]);
        }

        Arrays.sort(strs, (a, b) -> (b + a).compareTo(a + b));

        if (strs[0].equals("0")) return "0";

        StringBuilder result = new StringBuilder();
        for (String str : strs) {
            result.append(str);
        }

        return result.toString();
    }
}