package com.leetcode.algorithms.advanced;

import java.util.*;

/**
 * Heap Data Structures and Related Algorithms
 *
 * This class implements various heap-based data structures and algorithms
 * including binary heaps, k-way merge, and heap-based solutions.
 */
public class Heaps {

    // =========================== Binary Heap Implementation ===========================

    /**
     * Min Heap implementation using array
     */
    public static class MinHeap {
        private List<Integer> heap;

        public MinHeap() {
            heap = new ArrayList<>();
        }

        public MinHeap(int[] arr) {
            heap = new ArrayList<>();
            for (int val : arr) {
                heap.add(val);
            }
            buildHeap();
        }

        /**
         * Build heap from arbitrary array - O(n) time complexity
         */
        private void buildHeap() {
            for (int i = parent(size() - 1); i >= 0; i--) {
                heapifyDown(i);
            }
        }

        /**
         * Insert element into heap - O(log n)
         */
        public void insert(int val) {
            heap.add(val);
            heapifyUp(size() - 1);
        }

        /**
         * Extract minimum element - O(log n)
         */
        public int extractMin() {
            if (isEmpty()) {
                throw new RuntimeException("Heap is empty");
            }

            int min = heap.get(0);
            int last = heap.remove(size() - 1);

            if (!isEmpty()) {
                heap.set(0, last);
                heapifyDown(0);
            }

            return min;
        }

        /**
         * Peek minimum element - O(1)
         */
        public int peek() {
            if (isEmpty()) {
                throw new RuntimeException("Heap is empty");
            }
            return heap.get(0);
        }

        /**
         * Remove element at specific index - O(log n)
         */
        public void remove(int index) {
            if (index < 0 || index >= size()) {
                throw new IndexOutOfBoundsException();
            }

            int last = heap.remove(size() - 1);

            if (index < size()) {
                int original = heap.get(index);
                heap.set(index, last);

                if (last < original) {
                    heapifyUp(index);
                } else {
                    heapifyDown(index);
                }
            }
        }

        /**
         * Decrease key at specific index - O(log n)
         */
        public void decreaseKey(int index, int newVal) {
            if (index < 0 || index >= size()) {
                throw new IndexOutOfBoundsException();
            }

            if (newVal > heap.get(index)) {
                throw new IllegalArgumentException("New value is greater than current value");
            }

            heap.set(index, newVal);
            heapifyUp(index);
        }

        private void heapifyUp(int index) {
            while (index > 0 && heap.get(parent(index)) > heap.get(index)) {
                swap(index, parent(index));
                index = parent(index);
            }
        }

        private void heapifyDown(int index) {
            while (leftChild(index) < size()) {
                int minChild = leftChild(index);

                if (rightChild(index) < size() &&
                        heap.get(rightChild(index)) < heap.get(leftChild(index))) {
                    minChild = rightChild(index);
                }

                if (heap.get(index) > heap.get(minChild)) {
                    swap(index, minChild);
                    index = minChild;
                } else {
                    break;
                }
            }
        }

        private void swap(int i, int j) {
            int temp = heap.get(i);
            heap.set(i, heap.get(j));
            heap.set(j, temp);
        }

        private int parent(int index) { return (index - 1) / 2; }
        private int leftChild(int index) { return 2 * index + 1; }
        private int rightChild(int index) { return 2 * index + 2; }

        public int size() { return heap.size(); }
        public boolean isEmpty() { return heap.isEmpty(); }

        public List<Integer> getHeap() {
            return new ArrayList<>(heap);
        }
    }

    /**
     * Max Heap implementation
     */
    public static class MaxHeap {
        private List<Integer> heap;

        public MaxHeap() {
            heap = new ArrayList<>();
        }

        public void insert(int val) {
            heap.add(val);
            heapifyUp(size() - 1);
        }

        public int extractMax() {
            if (isEmpty()) {
                throw new RuntimeException("Heap is empty");
            }

            int max = heap.get(0);
            int last = heap.remove(size() - 1);

            if (!isEmpty()) {
                heap.set(0, last);
                heapifyDown(0);
            }

            return max;
        }

        public int peek() {
            if (isEmpty()) {
                throw new RuntimeException("Heap is empty");
            }
            return heap.get(0);
        }

        private void heapifyUp(int index) {
            while (index > 0 && heap.get(parent(index)) < heap.get(index)) {
                swap(index, parent(index));
                index = parent(index);
            }
        }

        private void heapifyDown(int index) {
            while (leftChild(index) < size()) {
                int maxChild = leftChild(index);

                if (rightChild(index) < size() &&
                        heap.get(rightChild(index)) > heap.get(leftChild(index))) {
                    maxChild = rightChild(index);
                }

                if (heap.get(index) < heap.get(maxChild)) {
                    swap(index, maxChild);
                    index = maxChild;
                } else {
                    break;
                }
            }
        }

        private void swap(int i, int j) {
            int temp = heap.get(i);
            heap.set(i, heap.get(j));
            heap.set(j, temp);
        }

        private int parent(int index) { return (index - 1) / 2; }
        private int leftChild(int index) { return 2 * index + 1; }
        private int rightChild(int index) { return 2 * index + 2; }

        public int size() { return heap.size(); }
        public boolean isEmpty() { return heap.isEmpty(); }
    }

    // =========================== Heap Sort ===========================

    /**
     * Heap Sort implementation
     * Time Complexity: O(n log n)
     * Space Complexity: O(1)
     */
    public void heapSort(int[] arr) {
        int n = arr.length;

        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // Extract elements from heap one by one
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    private void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, n, largest);
        }
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // =========================== K-Way Merge ===========================

    /**
     * Merge k sorted arrays using min heap
     * Time Complexity: O(n log k) where n is total elements
     * Space Complexity: O(k)
     */
    public List<Integer> mergeKSortedArrays(int[][] arrays) {
        List<Integer> result = new ArrayList<>();

        if (arrays == null || arrays.length == 0) {
            return result;
        }

        PriorityQueue<ArrayElement> minHeap = new PriorityQueue<>(
                Comparator.comparingInt(a -> a.value)
        );

        // Add first element of each array to heap
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i].length > 0) {
                minHeap.offer(new ArrayElement(arrays[i][0], i, 0));
            }
        }

        while (!minHeap.isEmpty()) {
            ArrayElement current = minHeap.poll();
            result.add(current.value);

            // Add next element from same array if available
            if (current.index + 1 < arrays[current.arrayIndex].length) {
                minHeap.offer(new ArrayElement(
                        arrays[current.arrayIndex][current.index + 1],
                        current.arrayIndex,
                        current.index + 1
                ));
            }
        }

        return result;
    }

    private static class ArrayElement {
        int value;
        int arrayIndex;
        int index;

        ArrayElement(int value, int arrayIndex, int index) {
            this.value = value;
            this.arrayIndex = arrayIndex;
            this.index = index;
        }
    }

    /**
     * Merge k sorted linked lists using min heap
     * Time Complexity: O(n log k)
     * Space Complexity: O(k)
     */
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(
                Comparator.comparingInt(node -> node.val)
        );

        // Add first node of each list to heap
        for (ListNode node : lists) {
            if (node != null) {
                minHeap.offer(node);
            }
        }

        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        while (!minHeap.isEmpty()) {
            ListNode node = minHeap.poll();
            current.next = node;
            current = current.next;

            if (node.next != null) {
                minHeap.offer(node.next);
            }
        }

        return dummy.next;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    // =========================== Top K Problems ===========================

    /**
     * Find K largest elements using min heap
     * Time Complexity: O(n log k)
     * Space Complexity: O(k)
     */
    public List<Integer> findKLargest(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int num : nums) {
            minHeap.offer(num);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        return new ArrayList<>(minHeap);
    }

    /**
     * Find K smallest elements using max heap
     * Time Complexity: O(n log k)
     * Space Complexity: O(k)
     */
    public List<Integer> findKSmallest(int[] nums, int k) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        for (int num : nums) {
            maxHeap.offer(num);
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }

        return new ArrayList<>(maxHeap);
    }

    /**
     * Find Kth largest element using quick select with heap fallback
     * Time Complexity: O(n) average, O(n log k) worst case
     * Space Complexity: O(k)
     */
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int num : nums) {
            minHeap.offer(num);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        return minHeap.peek();
    }

    /**
     * Top K frequent elements
     * Time Complexity: O(n log k)
     * Space Complexity: O(n + k)
     */
    public List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> frequency = new HashMap<>();
        for (int num : nums) {
            frequency.put(num, frequency.getOrDefault(num, 0) + 1);
        }

        PriorityQueue<Map.Entry<Integer, Integer>> minHeap = new PriorityQueue<>(
                Comparator.comparingInt(Map.Entry::getValue)
        );

        for (Map.Entry<Integer, Integer> entry : frequency.entrySet()) {
            minHeap.offer(entry);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            result.add(minHeap.poll().getKey());
        }

        Collections.reverse(result);
        return result;
    }

    // =========================== Running Median ===========================

    /**
     * Data structure to find median from data stream
     */
    public static class MedianFinder {
        private PriorityQueue<Integer> maxHeap; // Lower half
        private PriorityQueue<Integer> minHeap; // Upper half

        public MedianFinder() {
            maxHeap = new PriorityQueue<>(Collections.reverseOrder());
            minHeap = new PriorityQueue<>();
        }

        /**
         * Add number to data structure - O(log n)
         */
        public void addNum(int num) {
            if (maxHeap.isEmpty() || num <= maxHeap.peek()) {
                maxHeap.offer(num);
            } else {
                minHeap.offer(num);
            }

            // Balance heaps
            if (maxHeap.size() > minHeap.size() + 1) {
                minHeap.offer(maxHeap.poll());
            } else if (minHeap.size() > maxHeap.size() + 1) {
                maxHeap.offer(minHeap.poll());
            }
        }

        /**
         * Find median - O(1)
         */
        public double findMedian() {
            if (maxHeap.size() == minHeap.size()) {
                return (maxHeap.peek() + minHeap.peek()) / 2.0;
            } else if (maxHeap.size() > minHeap.size()) {
                return maxHeap.peek();
            } else {
                return minHeap.peek();
            }
        }
    }

    // =========================== Sliding Window Maximum ===========================

    /**
     * Sliding Window Maximum using deque (not heap but related problem)
     * Time Complexity: O(n)
     * Space Complexity: O(k)
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }

        Deque<Integer> deque = new ArrayDeque<>();
        int[] result = new int[nums.length - k + 1];
        int resultIndex = 0;

        for (int i = 0; i < nums.length; i++) {
            // Remove elements outside window
            while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }

            // Remove smaller elements from rear
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            deque.offerLast(i);

            // Add to result when window is complete
            if (i >= k - 1) {
                result[resultIndex++] = nums[deque.peekFirst()];
            }
        }

        return result;
    }

    // =========================== Priority Queue Applications ===========================

    /**
     * Task Scheduler using heap
     * Time Complexity: O(n log k) where k is unique tasks
     * Space Complexity: O(k)
     */
    public int leastInterval(char[] tasks, int n) {
        Map<Character, Integer> frequency = new HashMap<>();
        for (char task : tasks) {
            frequency.put(task, frequency.getOrDefault(task, 0) + 1);
        }

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        maxHeap.addAll(frequency.values());

        int time = 0;
        Queue<int[]> cooldown = new LinkedList<>(); // [frequency, availableTime]

        while (!maxHeap.isEmpty() || !cooldown.isEmpty()) {
            time++;

            // Add back tasks that are no longer in cooldown
            if (!cooldown.isEmpty() && cooldown.peek()[1] == time) {
                maxHeap.offer(cooldown.poll()[0]);
            }

            if (!maxHeap.isEmpty()) {
                int freq = maxHeap.poll() - 1;
                if (freq > 0) {
                    cooldown.offer(new int[]{freq, time + n + 1});
                }
            }
        }

        return time;
    }

    /**
     * Meeting Rooms II using heap
     * Time Complexity: O(n log n)
     * Space Complexity: O(n)
     */
    public int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(); // End times

        for (int[] interval : intervals) {
            if (!minHeap.isEmpty() && minHeap.peek() <= interval[0]) {
                minHeap.poll();
            }
            minHeap.offer(interval[1]);
        }

        return minHeap.size();
    }

    /**
     * Ugly Number II using multiple heaps
     * Time Complexity: O(n log n)
     * Space Complexity: O(n)
     */
    public int nthUglyNumber(int n) {
        PriorityQueue<Long> minHeap = new PriorityQueue<>();
        Set<Long> seen = new HashSet<>();

        minHeap.offer(1L);
        seen.add(1L);

        long ugly = 1;
        for (int i = 0; i < n; i++) {
            ugly = minHeap.poll();

            for (int prime : new int[]{2, 3, 5}) {
                long next = ugly * prime;
                if (!seen.contains(next)) {
                    minHeap.offer(next);
                    seen.add(next);
                }
            }
        }

        return (int) ugly;
    }

    // =========================== Advanced Heap Structures ===========================

    /**
     * Indexed Priority Queue (supports decrease key operation)
     */
    public static class IndexedPriorityQueue {
        private int[] heap;        // heap[i] = index of key
        private int[] position;    // position[i] = position of key i in heap
        private int[] keys;        // keys[i] = priority of key i
        private int size;

        public IndexedPriorityQueue(int capacity) {
            heap = new int[capacity + 1];
            position = new int[capacity + 1];
            keys = new int[capacity + 1];
            size = 0;

            Arrays.fill(position, -1);
        }

        public void insert(int index, int key) {
            if (contains(index)) {
                throw new IllegalArgumentException("Index already exists");
            }

            size++;
            position[index] = size;
            heap[size] = index;
            keys[index] = key;

            swim(size);
        }

        public int delMin() {
            if (isEmpty()) {
                throw new RuntimeException("Priority queue is empty");
            }

            int min = heap[1];
            exchange(1, size--);
            sink(1);

            position[min] = -1;
            return min;
        }

        public void decreaseKey(int index, int key) {
            if (!contains(index)) {
                throw new IllegalArgumentException("Index not in queue");
            }

            if (keys[index] <= key) {
                throw new IllegalArgumentException("Key is not smaller");
            }

            keys[index] = key;
            swim(position[index]);
        }

        public boolean contains(int index) {
            return position[index] != -1;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        private void swim(int k) {
            while (k > 1 && greater(k / 2, k)) {
                exchange(k / 2, k);
                k = k / 2;
            }
        }

        private void sink(int k) {
            while (2 * k <= size) {
                int j = 2 * k;
                if (j < size && greater(j, j + 1)) j++;
                if (!greater(k, j)) break;
                exchange(k, j);
                k = j;
            }
        }

        private boolean greater(int i, int j) {
            return keys[heap[i]] > keys[heap[j]];
        }

        private void exchange(int i, int j) {
            int temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;

            position[heap[i]] = i;
            position[heap[j]] = j;
        }
    }

    // =========================== Utility Methods ===========================

    /**
     * Check if array represents a valid min heap
     */
    public boolean isMinHeap(int[] arr) {
        for (int i = 0; i <= (arr.length - 2) / 2; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < arr.length && arr[i] > arr[left]) {
                return false;
            }

            if (right < arr.length && arr[i] > arr[right]) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check if array represents a valid max heap
     */
    public boolean isMaxHeap(int[] arr) {
        for (int i = 0; i <= (arr.length - 2) / 2; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < arr.length && arr[i] < arr[left]) {
                return false;
            }

            if (right < arr.length && arr[i] < arr[right]) {
                return false;
            }
        }

        return true;
    }

    /**
     * Convert min heap to max heap in place
     * Time Complexity: O(n)
     */
    public void convertMinToMaxHeap(int[] arr) {
        for (int i = (arr.length - 2) / 2; i >= 0; i--) {
            maxHeapify(arr, arr.length, i);
        }
    }

    private void maxHeapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != i) {
            swap(arr, i, largest);
            maxHeapify(arr, n, largest);
        }
    }

    /**
     * Print heap as tree structure
     */
    public void printHeap(List<Integer> heap) {
        if (heap.isEmpty()) {
            System.out.println("Empty heap");
            return;
        }

        System.out.println("Heap structure:");
        printHeapHelper(heap, 0, 0);
    }

    private void printHeapHelper(List<Integer> heap, int index, int level) {
        if (index >= heap.size()) return;

        printHeapHelper(heap, 2 * index + 2, level + 1); // Right child

        for (int i = 0; i < level; i++) {
            System.out.print("    ");
        }
        System.out.println(heap.get(index));

        printHeapHelper(heap, 2 * index + 1, level + 1); // Left child
    }

    /**
     * Get heap height
     */
    public int getHeapHeight(int size) {
        return (int) Math.floor(Math.log(size) / Math.log(2));
    }

    /**
     * Check if heap is complete binary tree
     */
    public boolean isCompleteHeap(List<Integer> heap) {
        boolean foundNull = false;

        for (int i = 0; i < heap.size(); i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < heap.size()) {
                if (foundNull) return false;
            } else {
                foundNull = true;
            }

            if (right < heap.size()) {
                if (foundNull) return false;
            } else {
                foundNull = true;
            }
        }

        return true;
    }

    /**
     * Merge two heaps
     * Time Complexity: O(n + m)
     */
    public MinHeap mergeHeaps(MinHeap heap1, MinHeap heap2) {
        List<Integer> combined = new ArrayList<>();
        combined.addAll(heap1.getHeap());
        combined.addAll(heap2.getHeap());

        int[] arr = combined.stream().mapToInt(i -> i).toArray();
        return new MinHeap(arr);
    }

    /**
     * Find median in stream of integers using two heaps
     */
    public double[] getMedianStream(int[] nums) {
        MedianFinder medianFinder = new MedianFinder();
        double[] result = new double[nums.length];

        for (int i = 0; i < nums.length; i++) {
            medianFinder.addNum(nums[i]);
            result[i] = medianFinder.findMedian();
        }

        return result;
    }
}