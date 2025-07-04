package com.leetcode.algorithms.greedy;

import java.util.*;

/**
 * Activity Selection and Job Scheduling Problems using Greedy Approach
 *
 * This class implements various greedy algorithms for scheduling optimization problems
 * where we need to select optimal set of activities, jobs, or tasks.
 */
public class ActivitySelection {

    /**
     * Activity representation
     */
    public static class Activity {
        public final int start;
        public final int finish;
        public final int index;
        public final int profit;

        public Activity(int start, int finish, int index) {
            this(start, finish, index, 1);
        }

        public Activity(int start, int finish, int index, int profit) {
            this.start = start;
            this.finish = finish;
            this.index = index;
            this.profit = profit;
        }

        @Override
        public String toString() {
            return String.format("Activity[%d: %d-%d, profit=%d]", index, start, finish, profit);
        }
    }

    /**
     * Job representation for scheduling with deadlines
     */
    public static class Job {
        public final int id;
        public final int deadline;
        public final int profit;

        public Job(int id, int deadline, int profit) {
            this.id = id;
            this.deadline = deadline;
            this.profit = profit;
        }

        @Override
        public String toString() {
            return String.format("Job[%d: deadline=%d, profit=%d]", id, deadline, profit);
        }
    }

    // =========================== Classic Activity Selection ===========================

    /**
     * Activity Selection Problem - select maximum number of non-overlapping activities
     * Time Complexity: O(n log n) for sorting
     * Space Complexity: O(1) excluding input storage
     *
     * Greedy Choice: Always select activity that finishes earliest among remaining activities
     *
     * @param activities array of activities with start and finish times
     * @return list of selected activities
     */
    public List<Activity> selectActivities(Activity[] activities) {
        if (activities == null || activities.length == 0) {
            return new ArrayList<>();
        }

        // Sort activities by finish time
        Arrays.sort(activities, Comparator.comparingInt(a -> a.finish));

        List<Activity> selected = new ArrayList<>();
        selected.add(activities[0]);
        int lastFinish = activities[0].finish;

        for (int i = 1; i < activities.length; i++) {
            if (activities[i].start >= lastFinish) {
                selected.add(activities[i]);
                lastFinish = activities[i].finish;
            }
        }

        return selected;
    }

    /**
     * Activity selection using arrays for start and finish times
     *
     * @param start array of start times
     * @param finish array of finish times
     * @return maximum number of activities that can be selected
     */
    public int activitySelectionCount(int[] start, int[] finish) {
        if (start == null || finish == null || start.length != finish.length) {
            return 0;
        }

        int n = start.length;
        if (n == 0) return 0;

        // Create activities and sort by finish time
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }

        Arrays.sort(indices, Comparator.comparingInt(i -> finish[i]));

        int count = 1;
        int lastFinish = finish[indices[0]];

        for (int i = 1; i < n; i++) {
            int currentIndex = indices[i];
            if (start[currentIndex] >= lastFinish) {
                count++;
                lastFinish = finish[currentIndex];
            }
        }

        return count;
    }

    // =========================== Weighted Activity Selection ===========================

    /**
     * Weighted Activity Selection - maximize total profit of non-overlapping activities
     * Note: This uses DP, but we include greedy approximation here
     * Time Complexity: O(n log n)
     * Space Complexity: O(1)
     *
     * @param activities array of activities with profits
     * @return maximum profit achievable (greedy approximation)
     */
    public int weightedActivitySelectionGreedy(Activity[] activities) {
        if (activities == null || activities.length == 0) {
            return 0;
        }

        // Sort by profit/duration ratio for greedy approximation
        Arrays.sort(activities, (a, b) -> {
            double ratioA = (double) a.profit / (a.finish - a.start);
            double ratioB = (double) b.profit / (b.finish - b.start);
            return Double.compare(ratioB, ratioA);
        });

        int totalProfit = 0;
        int lastFinish = 0;

        for (Activity activity : activities) {
            if (activity.start >= lastFinish) {
                totalProfit += activity.profit;
                lastFinish = activity.finish;
            }
        }

        return totalProfit;
    }

    // =========================== Job Scheduling with Deadlines ===========================

    /**
     * Job Scheduling with Deadlines - maximize profit within deadlines
     * Time Complexity: O(n² log n)
     * Space Complexity: O(max_deadline)
     *
     * Greedy Choice: Schedule highest profit job as late as possible within deadline
     *
     * @param jobs array of jobs with deadlines and profits
     * @return maximum profit achievable
     */
    public int jobSchedulingWithDeadlines(Job[] jobs) {
        if (jobs == null || jobs.length == 0) {
            return 0;
        }

        // Sort jobs by profit in descending order
        Arrays.sort(jobs, (a, b) -> Integer.compare(b.profit, a.profit));

        int maxDeadline = Arrays.stream(jobs).mapToInt(j -> j.deadline).max().orElse(0);
        boolean[] timeSlot = new boolean[maxDeadline + 1];
        int totalProfit = 0;

        for (Job job : jobs) {
            // Find latest available slot before or at deadline
            for (int t = job.deadline; t >= 1; t--) {
                if (!timeSlot[t]) {
                    timeSlot[t] = true;
                    totalProfit += job.profit;
                    break;
                }
            }
        }

        return totalProfit;
    }

    /**
     * Job scheduling with deadlines - return actual schedule
     *
     * @param jobs array of jobs
     * @return list of scheduled jobs in time order
     */
    public List<Job> scheduleJobsWithDeadlines(Job[] jobs) {
        if (jobs == null || jobs.length == 0) {
            return new ArrayList<>();
        }

        Arrays.sort(jobs, (a, b) -> Integer.compare(b.profit, a.profit));

        int maxDeadline = Arrays.stream(jobs).mapToInt(j -> j.deadline).max().orElse(0);
        Job[] schedule = new Job[maxDeadline + 1];

        for (Job job : jobs) {
            for (int t = job.deadline; t >= 1; t--) {
                if (schedule[t] == null) {
                    schedule[t] = job;
                    break;
                }
            }
        }

        List<Job> result = new ArrayList<>();
        for (int i = 1; i <= maxDeadline; i++) {
            if (schedule[i] != null) {
                result.add(schedule[i]);
            }
        }

        return result;
    }

    // =========================== Meeting Room Problems ===========================

    /**
     * Meeting Rooms I - check if person can attend all meetings
     * Time Complexity: O(n log n)
     * Space Complexity: O(1)
     *
     * @param intervals array of meeting intervals [start, end]
     * @return true if can attend all meetings
     */
    public boolean canAttendMeetings(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return true;
        }

        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < intervals[i - 1][1]) {
                return false; // Overlap detected
            }
        }

        return true;
    }

    /**
     * Meeting Rooms II - minimum number of meeting rooms required
     * Time Complexity: O(n log n)
     * Space Complexity: O(n)
     *
     * @param intervals array of meeting intervals
     * @return minimum number of meeting rooms needed
     */
    public int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        // Separate start and end times
        int[] starts = new int[intervals.length];
        int[] ends = new int[intervals.length];

        for (int i = 0; i < intervals.length; i++) {
            starts[i] = intervals[i][0];
            ends[i] = intervals[i][1];
        }

        Arrays.sort(starts);
        Arrays.sort(ends);

        int rooms = 0;
        int maxRooms = 0;
        int startPtr = 0, endPtr = 0;

        while (startPtr < starts.length) {
            if (starts[startPtr] < ends[endPtr]) {
                rooms++;
                startPtr++;
            } else {
                rooms--;
                endPtr++;
            }
            maxRooms = Math.max(maxRooms, rooms);
        }

        return maxRooms;
    }

    /**
     * Meeting Rooms II using priority queue approach
     *
     * @param intervals array of meeting intervals
     * @return minimum number of meeting rooms needed
     */
    public int minMeetingRoomsPQ(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        // Sort by start time
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        // Min heap to track end times of meetings in progress
        PriorityQueue<Integer> endTimes = new PriorityQueue<>();

        for (int[] interval : intervals) {
            // Remove meetings that have ended
            while (!endTimes.isEmpty() && endTimes.peek() <= interval[0]) {
                endTimes.poll();
            }

            // Add current meeting's end time
            endTimes.offer(interval[1]);
        }

        return endTimes.size();
    }

    // =========================== Platform Assignment Problems ===========================

    /**
     * Minimum Railway Platforms - find minimum platforms needed for train schedule
     * Time Complexity: O(n log n)
     * Space Complexity: O(1)
     *
     * @param arrivals array of arrival times
     * @param departures array of departure times
     * @return minimum number of platforms needed
     */
    public int findMinimumPlatforms(int[] arrivals, int[] departures) {
        if (arrivals == null || departures == null || arrivals.length != departures.length) {
            return 0;
        }

        Arrays.sort(arrivals);
        Arrays.sort(departures);

        int platforms = 1; // At least one train
        int maxPlatforms = 1;
        int i = 1, j = 0;

        while (i < arrivals.length && j < departures.length) {
            if (arrivals[i] <= departures[j]) {
                platforms++;
                i++;
            } else {
                platforms--;
                j++;
            }
            maxPlatforms = Math.max(maxPlatforms, platforms);
        }

        return maxPlatforms;
    }

    // =========================== CPU Scheduling ===========================

    /**
     * Shortest Job First (SJF) Scheduling
     * Time Complexity: O(n log n)
     * Space Complexity: O(1)
     *
     * @param processes array of process burst times
     * @return average waiting time
     */
    public double shortestJobFirst(int[] processes) {
        if (processes == null || processes.length == 0) {
            return 0.0;
        }

        Arrays.sort(processes);

        int totalWaitTime = 0;
        int currentTime = 0;

        for (int burstTime : processes) {
            totalWaitTime += currentTime;
            currentTime += burstTime;
        }

        return (double) totalWaitTime / processes.length;
    }

    /**
     * Process representation for priority scheduling
     */
    public static class Process {
        public final int id;
        public final int burstTime;
        public final int priority;
        public final int arrivalTime;

        public Process(int id, int burstTime, int priority, int arrivalTime) {
            this.id = id;
            this.burstTime = burstTime;
            this.priority = priority;
            this.arrivalTime = arrivalTime;
        }
    }

    /**
     * Priority Scheduling (preemptive)
     * Time Complexity: O(n log n)
     * Space Complexity: O(n)
     *
     * @param processes array of processes
     * @return average waiting time
     */
    public double priorityScheduling(Process[] processes) {
        if (processes == null || processes.length == 0) {
            return 0.0;
        }

        // Sort by arrival time first
        Arrays.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));

        PriorityQueue<Process> readyQueue = new PriorityQueue<>(
                Comparator.comparingInt(p -> p.priority)
        );

        int currentTime = 0;
        int totalWaitTime = 0;
        int processIndex = 0;

        while (processIndex < processes.length || !readyQueue.isEmpty()) {
            // Add all processes that have arrived
            while (processIndex < processes.length &&
                    processes[processIndex].arrivalTime <= currentTime) {
                readyQueue.offer(processes[processIndex]);
                processIndex++;
            }

            if (!readyQueue.isEmpty()) {
                Process current = readyQueue.poll();
                totalWaitTime += currentTime - current.arrivalTime;
                currentTime += current.burstTime;
            } else if (processIndex < processes.length) {
                currentTime = processes[processIndex].arrivalTime;
            }
        }

        return (double) totalWaitTime / processes.length;
    }

    // =========================== Utility Methods ===========================

    /**
     * Create activity from interval notation
     *
     * @param start start time
     * @param finish finish time
     * @param index activity index
     * @return Activity object
     */
    public static Activity createActivity(int start, int finish, int index) {
        return new Activity(start, finish, index);
    }

    /**
     * Create activities from 2D array
     *
     * @param intervals array of [start, finish] pairs
     * @return array of Activity objects
     */
    public static Activity[] createActivities(int[][] intervals) {
        Activity[] activities = new Activity[intervals.length];
        for (int i = 0; i < intervals.length; i++) {
            activities[i] = new Activity(intervals[i][0], intervals[i][1], i);
        }
        return activities;
    }

    /**
     * Create jobs from arrays
     *
     * @param deadlines array of deadlines
     * @param profits array of profits
     * @return array of Job objects
     */
    public static Job[] createJobs(int[] deadlines, int[] profits) {
        if (deadlines.length != profits.length) {
            throw new IllegalArgumentException("Arrays must have same length");
        }

        Job[] jobs = new Job[deadlines.length];
        for (int i = 0; i < deadlines.length; i++) {
            jobs[i] = new Job(i, deadlines[i], profits[i]);
        }
        return jobs;
    }

    /**
     * Print activity selection result
     *
     * @param activities list of selected activities
     * @param label description label
     */
    public static void printActivitySelection(List<Activity> activities, String label) {
        System.out.println(label + " (" + activities.size() + " activities):");
        for (Activity activity : activities) {
            System.out.println("  " + activity);
        }
        System.out.println();
    }

    /**
     * Print job scheduling result
     *
     * @param jobs list of scheduled jobs
     * @param label description label
     */
    public static void printJobSchedule(List<Job> jobs, String label) {
        System.out.println(label + " (" + jobs.size() + " jobs):");
        for (int i = 0; i < jobs.size(); i++) {
            System.out.println("  Time " + (i + 1) + ": " + jobs.get(i));
        }
        System.out.println();
    }

    /**
     * Validate activity selection solution
     *
     * @param activities list of selected activities
     * @return true if no overlapping activities
     */
    public boolean validateActivitySelection(List<Activity> activities) {
        if (activities.size() <= 1) {
            return true;
        }

        // Check if activities are non-overlapping when sorted by start time
        List<Activity> sorted = new ArrayList<>(activities);
        sorted.sort(Comparator.comparingInt(a -> a.start));

        for (int i = 1; i < sorted.size(); i++) {
            if (sorted.get(i).start < sorted.get(i - 1).finish) {
                return false;
            }
        }

        return true;
    }

    /**
     * Calculate total duration of selected activities
     *
     * @param activities list of activities
     * @return total duration
     */
    public int calculateTotalDuration(List<Activity> activities) {
        return activities.stream()
                .mapToInt(a -> a.finish - a.start)
                .sum();
    }

    /**
     * Calculate efficiency ratio for activity selection
     *
     * @param selected number of selected activities
     * @param total total number of activities
     * @return efficiency ratio (0.0 to 1.0)
     */
    public double calculateEfficiency(int selected, int total) {
        return total == 0 ? 0.0 : (double) selected / total;
    }
}