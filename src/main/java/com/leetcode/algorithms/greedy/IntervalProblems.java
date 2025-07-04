package com.leetcode.algorithms.greedy;

import java.util.*;

/**
 * Interval Problems using Greedy Algorithms
 *
 * This class implements various greedy solutions for interval-based problems
 * including merging, partitioning, covering, and optimization problems.
 */
public class IntervalProblems {

    /**
     * Interval representation
     */
    public static class Interval {
        public final int start;
        public final int end;

        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "[" + start + "," + end + "]";
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Interval interval = (Interval) obj;
            return start == interval.start && end == interval.end;
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end);
        }
    }

    // =========================== Merge Intervals ===========================

    /**
     * Merge Overlapping Intervals
     * Time Complexity: O(n log n)
     * Space Complexity: O(n) for result
     *
     * @param intervals array of intervals
     * @return merged intervals
     */
    public int[][] mergeIntervals(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }

        // Sort by start time
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        List<int[]> merged = new ArrayList<>();
        int[] current = intervals[0];

        for (int i = 1; i < intervals.length; i++) {
            int[] next = intervals[i];

            if (current[1] >= next[0]) { // Overlapping
                current[1] = Math.max(current[1], next[1]);
            } else { // Non-overlapping
                merged.add(current);
                current = next;
            }
        }

        merged.add(current);
        return merged.toArray(new int[merged.size()][]);
    }

    /**
     * Merge intervals with custom overlap definition
     *
     * @param intervals list of Interval objects
     * @param allowTouch true if touching intervals should be merged
     * @return merged intervals
     */
    public List<Interval> mergeIntervals(List<Interval> intervals, boolean allowTouch) {
        if (intervals == null || intervals.size() <= 1) {
            return new ArrayList<>(intervals);
        }

        intervals.sort(Comparator.comparingInt(i -> i.start));

        List<Interval> merged = new ArrayList<>();
        Interval current = intervals.get(0);

        for (int i = 1; i < intervals.size(); i++) {
            Interval next = intervals.get(i);

            boolean shouldMerge = allowTouch ?
                    current.end >= next.start :
                    current.end > next.start;

            if (shouldMerge) {
                current = new Interval(current.start, Math.max(current.end, next.end));
            } else {
                merged.add(current);
                current = next;
            }
        }

        merged.add(current);
        return merged;
    }

    // =========================== Insert Interval ===========================

    /**
     * Insert Interval into sorted non-overlapping intervals
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     *
     * @param intervals sorted non-overlapping intervals
     * @param newInterval interval to insert
     * @return updated intervals after insertion and merging
     */
    public int[][] insertInterval(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        int i = 0;

        // Add all intervals that end before newInterval starts
        while (i < intervals.length && intervals[i][1] < newInterval[0]) {
            result.add(intervals[i]);
            i++;
        }

        // Merge overlapping intervals with newInterval
        while (i < intervals.length && intervals[i][0] <= newInterval[1]) {
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            i++;
        }
        result.add(newInterval);

        // Add remaining intervals
        while (i < intervals.length) {
            result.add(intervals[i]);
            i++;
        }

        return result.toArray(new int[result.size()][]);
    }

    // =========================== Remove Intervals ===========================

    /**
     * Remove Covered Intervals
     * Time Complexity: O(n log n)
     * Space Complexity: O(1)
     *
     * @param intervals array of intervals
     * @return number of intervals remaining after removing covered ones
     */
    public int removeCoveredIntervals(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        // Sort by start time, if same start then by end time descending
        Arrays.sort(intervals, (a, b) -> {
            if (a[0] == b[0]) {
                return Integer.compare(b[1], a[1]);
            }
            return Integer.compare(a[0], b[0]);
        });

        int count = 0;
        int prevEnd = 0;

        for (int[] interval : intervals) {
            if (interval[1] > prevEnd) {
                count++;
                prevEnd = interval[1];
            }
        }

        return count;
    }

    /**
     * Remove Interval from list of intervals
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     *
     * @param intervals list of non-overlapping intervals
     * @param toRemove interval to remove
     * @return updated intervals after removal
     */
    public List<int[]> removeInterval(int[][] intervals, int[] toRemove) {
        List<int[]> result = new ArrayList<>();

        for (int[] interval : intervals) {
            // No overlap
            if (interval[1] <= toRemove[0] || interval[0] >= toRemove[1]) {
                result.add(interval);
            }
            // Partial overlap - split if necessary
            else {
                if (interval[0] < toRemove[0]) {
                    result.add(new int[]{interval[0], toRemove[0]});
                }
                if (interval[1] > toRemove[1]) {
                    result.add(new int[]{toRemove[1], interval[1]});
                }
            }
        }

        return result;
    }

    // =========================== Minimum Intervals ===========================

    /**
     * Minimum Number of Arrows to Burst Balloons
     * Time Complexity: O(n log n)
     * Space Complexity: O(1)
     *
     * @param points array of balloon coordinates [start, end]
     * @return minimum number of arrows needed
     */
    public int findMinArrowShots(int[][] points) {
        if (points == null || points.length == 0) {
            return 0;
        }

        // Sort by end coordinate
        Arrays.sort(points, Comparator.comparingInt(a -> a[1]));

        int arrows = 1;
        int arrowPos = points[0][1];

        for (int i = 1; i < points.length; i++) {
            if (points[i][0] > arrowPos) {
                arrows++;
                arrowPos = points[i][1];
            }
        }

        return arrows;
    }

    /**
     * Minimum Number of Taps to Water Garden
     * Time Complexity: O(n log n)
     * Space Complexity: O(n)
     *
     * @param n garden length
     * @param ranges tap ranges
     * @return minimum taps needed, -1 if impossible
     */
    public int minTaps(int n, int[] ranges) {
        // Convert taps to intervals
        List<int[]> intervals = new ArrayList<>();

        for (int i = 0; i < ranges.length; i++) {
            if (ranges[i] > 0) {
                intervals.add(new int[]{Math.max(0, i - ranges[i]),
                        Math.min(n, i + ranges[i])});
            }
        }

        // Sort by start position
        intervals.sort(Comparator.comparingInt(a -> a[0]));

        int taps = 0;
        int covered = 0;
        int i = 0;

        while (covered < n) {
            int farthest = covered;

            // Find interval that starts before or at covered position
            // and extends the farthest
            while (i < intervals.size() && intervals.get(i)[0] <= covered) {
                farthest = Math.max(farthest, intervals.get(i)[1]);
                i++;
            }

            if (farthest == covered) {
                return -1; // Cannot extend coverage
            }

            taps++;
            covered = farthest;
        }

        return taps;
    }

    // =========================== Interval Partitioning ===========================

    /**
     * Partition Labels - partition string into as many parts as possible
     * Time Complexity: O(n)
     * Space Complexity: O(1) for fixed alphabet size
     *
     * @param s input string
     * @return list of partition sizes
     */
    public List<Integer> partitionLabels(String s) {
        if (s == null || s.length() == 0) {
            return new ArrayList<>();
        }

        // Find last occurrence of each character
        int[] lastIndex = new int[26];
        for (int i = 0; i < s.length(); i++) {
            lastIndex[s.charAt(i) - 'a'] = i;
        }

        List<Integer> partitions = new ArrayList<>();
        int start = 0;
        int end = 0;

        for (int i = 0; i < s.length(); i++) {
            end = Math.max(end, lastIndex[s.charAt(i) - 'a']);

            if (i == end) { // Reached end of current partition
                partitions.add(end - start + 1);
                start = end + 1;
            }
        }

        return partitions;
    }

    /**
     * Non-overlapping Intervals - remove minimum intervals to make rest non-overlapping
     * Time Complexity: O(n log n)
     * Space Complexity: O(1)
     *
     * @param intervals array of intervals
     * @return minimum number of intervals to remove
     */
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return 0;
        }

        // Sort by end time
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[1]));

        int nonOverlapping = 1;
        int lastEnd = intervals[0][1];

        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] >= lastEnd) {
                nonOverlapping++;
                lastEnd = intervals[i][1];
            }
        }

        return intervals.length - nonOverlapping;
    }

    // =========================== Interval Covering ===========================

    /**
     * Jump Game II - minimum jumps to reach end
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     *
     * @param nums array where nums[i] is max jump length from position i
     * @return minimum number of jumps to reach end
     */
    public int jumpGameII(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return 0;
        }

        int jumps = 0;
        int currentEnd = 0;
        int farthest = 0;

        for (int i = 0; i < nums.length - 1; i++) {
            farthest = Math.max(farthest, i + nums[i]);

            if (i == currentEnd) {
                jumps++;
                currentEnd = farthest;
            }
        }

        return jumps;
    }

    /**
     * Video Stitching - minimum clips to cover [0, time]
     * Time Complexity: O(n log n)
     * Space Complexity: O(1)
     *
     * @param clips array of clips [start, end]
     * @param time target time to cover
     * @return minimum clips needed, -1 if impossible
     */
    public int videoStitching(int[][] clips, int time) {
        if (clips == null || clips.length == 0) {
            return time == 0 ? 0 : -1;
        }

        // Sort by start time
        Arrays.sort(clips, Comparator.comparingInt(a -> a[0]));

        int clipsUsed = 0;
        int covered = 0;
        int i = 0;

        while (covered < time) {
            int farthest = covered;

            // Find clip that starts before or at covered time
            // and extends the farthest
            while (i < clips.length && clips[i][0] <= covered) {
                farthest = Math.max(farthest, clips[i][1]);
                i++;
            }

            if (farthest == covered) {
                return -1; // Cannot extend coverage
            }

            clipsUsed++;
            covered = farthest;
        }

        return clipsUsed;
    }

    /**
     * Gas Station - find starting station for circular trip
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     *
     * @param gas array of gas amounts at each station
     * @param cost array of gas costs to reach next station
     * @return starting station index, -1 if impossible
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        if (gas == null || cost == null || gas.length != cost.length) {
            return -1;
        }

        int totalGas = 0;
        int totalCost = 0;
        int currentGas = 0;
        int startStation = 0;

        for (int i = 0; i < gas.length; i++) {
            totalGas += gas[i];
            totalCost += cost[i];
            currentGas += gas[i] - cost[i];

            if (currentGas < 0) {
                startStation = i + 1;
                currentGas = 0;
            }
        }

        return totalGas >= totalCost ? startStation : -1;
    }

    // =========================== Fractional Problems ===========================

    /**
     * Fractional Knapsack Problem
     * Time Complexity: O(n log n)
     * Space Complexity: O(1)
     *
     * @param weights array of item weights
     * @param values array of item values
     * @param capacity knapsack capacity
     * @return maximum value achievable
     */
    public double fractionalKnapsack(int[] weights, int[] values, int capacity) {
        if (weights == null || values == null || weights.length != values.length) {
            return 0.0;
        }

        int n = weights.length;

        // Create items with value/weight ratio
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }

        // Sort by value/weight ratio in descending order
        Arrays.sort(indices, (a, b) -> {
            double ratioA = (double) values[a] / weights[a];
            double ratioB = (double) values[b] / weights[b];
            return Double.compare(ratioB, ratioA);
        });

        double totalValue = 0.0;
        int remainingCapacity = capacity;

        for (int index : indices) {
            if (remainingCapacity == 0) break;

            if (weights[index] <= remainingCapacity) {
                // Take whole item
                totalValue += values[index];
                remainingCapacity -= weights[index];
            } else {
                // Take fraction of item
                double fraction = (double) remainingCapacity / weights[index];
                totalValue += values[index] * fraction;
                remainingCapacity = 0;
            }
        }

        return totalValue;
    }

    // =========================== Scheduling Problems ===========================

    /**
     * Task Scheduler - minimum time to complete tasks with cooldown
     * Time Complexity: O(n)
     * Space Complexity: O(1) for fixed alphabet size
     *
     * @param tasks array of task types
     * @param n cooldown period between same tasks
     * @return minimum time units to complete all tasks
     */
    public int leastInterval(char[] tasks, int n) {
        if (tasks == null || tasks.length == 0) {
            return 0;
        }

        // Count frequency of each task
        int[] counts = new int[26];
        int maxCount = 0;

        for (char task : tasks) {
            counts[task - 'A']++;
            maxCount = Math.max(maxCount, counts[task - 'A']);
        }

        // Count how many tasks have maximum frequency
        int maxCountTasks = 0;
        for (int count : counts) {
            if (count == maxCount) {
                maxCountTasks++;
            }
        }

        // Calculate minimum time
        int partCount = maxCount - 1;
        int partLength = n - (maxCountTasks - 1);
        int emptySlots = partCount * partLength;
        int availableTasks = tasks.length - maxCount * maxCountTasks;
        int idles = Math.max(0, emptySlots - availableTasks);

        return tasks.length + idles;
    }

    /**
     * Reorganize String - rearrange so no two adjacent characters are same
     * Time Complexity: O(n log k) where k is unique characters
     * Space Complexity: O(k)
     *
     * @param s input string
     * @return rearranged string, empty if impossible
     */
    public String reorganizeString(String s) {
        if (s == null || s.length() <= 1) {
            return s;
        }

        // Count character frequencies
        Map<Character, Integer> counts = new HashMap<>();
        for (char c : s.toCharArray()) {
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }

        // Use max heap based on frequency
        PriorityQueue<Map.Entry<Character, Integer>> maxHeap = new PriorityQueue<>(
                (a, b) -> Integer.compare(b.getValue(), a.getValue())
        );
        maxHeap.addAll(counts.entrySet());

        StringBuilder result = new StringBuilder();
        Map.Entry<Character, Integer> prev = null;

        while (!maxHeap.isEmpty()) {
            Map.Entry<Character, Integer> current = maxHeap.poll();

            if (prev != null && prev.getValue() > 0) {
                maxHeap.offer(prev);
            }

            result.append(current.getKey());
            current.setValue(current.getValue() - 1);
            prev = current;
        }

        return result.length() == s.length() ? result.toString() : "";
    }

    // =========================== Utility Methods ===========================

    /**
     * Convert 2D array to Interval list
     *
     * @param intervals 2D array of intervals
     * @return list of Interval objects
     */
    public static List<Interval> arrayToIntervals(int[][] intervals) {
        List<Interval> result = new ArrayList<>();
        for (int[] interval : intervals) {
            result.add(new Interval(interval[0], interval[1]));
        }
        return result;
    }

    /**
     * Convert Interval list to 2D array
     *
     * @param intervals list of Interval objects
     * @return 2D array representation
     */
    public static int[][] intervalsToArray(List<Interval> intervals) {
        int[][] result = new int[intervals.size()][2];
        for (int i = 0; i < intervals.size(); i++) {
            result[i][0] = intervals.get(i).start;
            result[i][1] = intervals.get(i).end;
        }
        return result;
    }

    /**
     * Check if two intervals overlap
     *
     * @param a first interval
     * @param b second interval
     * @param includeTouch true if touching intervals count as overlapping
     * @return true if intervals overlap
     */
    public static boolean intervalsOverlap(Interval a, Interval b, boolean includeTouch) {
        if (includeTouch) {
            return a.start <= b.end && b.start <= a.end;
        } else {
            return a.start < b.end && b.start < a.end;
        }
    }

    /**
     * Calculate total length covered by intervals
     *
     * @param intervals list of potentially overlapping intervals
     * @return total length covered
     */
    public int calculateTotalCoverage(List<Interval> intervals) {
        if (intervals == null || intervals.isEmpty()) {
            return 0;
        }

        List<Interval> merged = mergeIntervals(intervals, false);

        int totalLength = 0;
        for (Interval interval : merged) {
            totalLength += interval.end - interval.start;
        }

        return totalLength;
    }

    /**
     * Find intersection of two intervals
     *
     * @param a first interval
     * @param b second interval
     * @return intersection interval, null if no intersection
     */
    public static Interval intersectIntervals(Interval a, Interval b) {
        int start = Math.max(a.start, b.start);
        int end = Math.min(a.end, b.end);

        return start < end ? new Interval(start, end) : null;
    }

    /**
     * Find union of two non-overlapping intervals
     *
     * @param a first interval
     * @param b second interval
     * @return union interval if they overlap/touch, null otherwise
     */
    public static Interval unionIntervals(Interval a, Interval b) {
        if (intervalsOverlap(a, b, true)) {
            return new Interval(Math.min(a.start, b.start), Math.max(a.end, b.end));
        }
        return null;
    }

    /**
     * Validate interval array (start <= end for each interval)
     *
     * @param intervals array to validate
     * @return true if all intervals are valid
     */
    public static boolean validateIntervals(int[][] intervals) {
        if (intervals == null) return false;

        for (int[] interval : intervals) {
            if (interval == null || interval.length != 2 || interval[0] > interval[1]) {
                return false;
            }
        }

        return true;
    }

    /**
     * Print intervals in formatted way
     *
     * @param intervals array of intervals
     * @param label description label
     */
    public static void printIntervals(int[][] intervals, String label) {
        System.out.println(label + ":");
        if (intervals == null || intervals.length == 0) {
            System.out.println("  (empty)");
        } else {
            for (int[] interval : intervals) {
                System.out.println("  [" + interval[0] + "," + interval[1] + "]");
            }
        }
        System.out.println();
    }

    /**
     * Print interval list
     *
     * @param intervals list of intervals
     * @param label description label
     */
    public static void printIntervals(List<Interval> intervals, String label) {
        System.out.println(label + ":");
        if (intervals == null || intervals.isEmpty()) {
            System.out.println("  (empty)");
        } else {
            for (Interval interval : intervals) {
                System.out.println("  " + interval);
            }
        }
        System.out.println();
    }

    /**
     * Generate random intervals for testing
     *
     * @param count number of intervals to generate
     * @param maxValue maximum coordinate value
     * @param maxLength maximum interval length
     * @return array of random intervals
     */
    public static int[][] generateRandomIntervals(int count, int maxValue, int maxLength) {
        Random random = new Random();
        int[][] intervals = new int[count][2];

        for (int i = 0; i < count; i++) {
            int start = random.nextInt(maxValue);
            int length = random.nextInt(maxLength) + 1;
            intervals[i][0] = start;
            intervals[i][1] = start + length;
        }

        return intervals;
    }

    /**
     * Check if intervals are sorted by start time
     *
     * @param intervals array to check
     * @return true if sorted by start time
     */
    public static boolean isSortedByStart(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return true;
        }

        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < intervals[i - 1][0]) {
                return false;
            }
        }

        return true;
    }

    /**
     * Find maximum overlap point among all intervals
     *
     * @param intervals array of intervals
     * @return maximum number of intervals overlapping at any point
     */
    public int findMaxOverlap(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        List<int[]> events = new ArrayList<>();

        // Create events for interval start (+1) and end (-1)
        for (int[] interval : intervals) {
            events.add(new int[]{interval[0], 1});  // Start
            events.add(new int[]{interval[1], -1}); // End
        }

        // Sort events by time, with end events before start events at same time
        events.sort((a, b) -> {
            if (a[0] == b[0]) {
                return Integer.compare(a[1], b[1]);
            }
            return Integer.compare(a[0], b[0]);
        });

        int currentOverlap = 0;
        int maxOverlap = 0;

        for (int[] event : events) {
            currentOverlap += event[1];
            maxOverlap = Math.max(maxOverlap, currentOverlap);
        }

        return maxOverlap;
    }
}