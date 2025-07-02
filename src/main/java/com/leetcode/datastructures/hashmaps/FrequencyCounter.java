package com.leetcode.datastructures.hashmaps;

import java.util.*;

/**
 * Frequency Counter using HashMap patterns
 * Common use cases:
 * 1. Character/Element frequency counting
 * 2. Anagram detection
 * 3. Finding duplicates/unique elements
 * 4. Most/least frequent elements
 */
public class FrequencyCounter {

    /**
     * Count frequency of characters in a string
     * Time: O(n), Space: O(k) where k is unique characters
     */
    public Map<Character, Integer> characterFrequency(String s) {
        Map<Character, Integer> freq = new HashMap<>();

        for (char c : s.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        return freq;
    }

    /**
     * Count frequency of elements in array
     * Time: O(n), Space: O(k) where k is unique elements
     */
    public Map<Integer, Integer> arrayFrequency(int[] nums) {
        Map<Integer, Integer> freq = new HashMap<>();

        for (int num : nums) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }

        return freq;
    }

    /**
     * Check if two strings are anagrams
     * Time: O(n), Space: O(k)
     */
    public boolean isAnagram(String s1, String s2) {
        if (s1.length() != s2.length()) return false;

        Map<Character, Integer> freq1 = characterFrequency(s1);
        Map<Character, Integer> freq2 = characterFrequency(s2);

        return freq1.equals(freq2);
    }

    /**
     * Alternative anagram check using single map
     * Time: O(n), Space: O(k)
     */
    public boolean isAnagramOptimized(String s1, String s2) {
        if (s1.length() != s2.length()) return false;

        Map<Character, Integer> freq = new HashMap<>();

        // Increment for s1
        for (char c : s1.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        // Decrement for s2
        for (char c : s2.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) - 1);
            if (freq.get(c) == 0) {
                freq.remove(c);
            }
        }

        return freq.isEmpty();
    }

    /**
     * Find first non-repeating character in string
     * Time: O(n), Space: O(k)
     */
    public char firstNonRepeatingChar(String s) {
        Map<Character, Integer> freq = characterFrequency(s);

        for (char c : s.toCharArray()) {
            if (freq.get(c) == 1) {
                return c;
            }
        }

        return '\0'; // Not found
    }

    /**
     * Find most frequent element in array
     * Time: O(n), Space: O(k)
     */
    public int mostFrequentElement(int[] nums) {
        if (nums.length == 0) return -1;

        Map<Integer, Integer> freq = arrayFrequency(nums);

        int maxFreq = 0;
        int mostFrequent = nums[0];

        for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
            if (entry.getValue() > maxFreq) {
                maxFreq = entry.getValue();
                mostFrequent = entry.getKey();
            }
        }

        return mostFrequent;
    }

    /**
     * Find top K frequent elements
     * Time: O(n log k), Space: O(n)
     */
    public List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> freq = arrayFrequency(nums);

        // Min heap to keep track of top k frequent elements
        PriorityQueue<Map.Entry<Integer, Integer>> heap = new PriorityQueue<>(
                (a, b) -> a.getValue() - b.getValue()
        );

        for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
            heap.offer(entry);
            if (heap.size() > k) {
                heap.poll();
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!heap.isEmpty()) {
            result.add(0, heap.poll().getKey()); // Add to front to maintain order
        }

        return result;
    }

    /**
     * Alternative top K frequent using bucket sort
     * Time: O(n), Space: O(n)
     */
    public List<Integer> topKFrequentBucket(int[] nums, int k) {
        Map<Integer, Integer> freq = arrayFrequency(nums);

        // Bucket sort: index = frequency, value = list of numbers
        List<Integer>[] buckets = new List[nums.length + 1];

        for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
            int frequency = entry.getValue();
            if (buckets[frequency] == null) {
                buckets[frequency] = new ArrayList<>();
            }
            buckets[frequency].add(entry.getKey());
        }

        List<Integer> result = new ArrayList<>();
        for (int i = buckets.length - 1; i >= 0 && result.size() < k; i--) {
            if (buckets[i] != null) {
                result.addAll(buckets[i]);
            }
        }

        return result.subList(0, Math.min(k, result.size()));
    }

    /**
     * Group anagrams together
     * Time: O(n * m log m), Space: O(n * m)
     * where n = number of strings, m = average length of string
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> groups = new HashMap<>();

        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String sortedStr = new String(chars);

            groups.computeIfAbsent(sortedStr, k -> new ArrayList<>()).add(str);
        }

        return new ArrayList<>(groups.values());
    }

    /**
     * Alternative group anagrams using frequency map as key
     * Time: O(n * m), Space: O(n * m)
     */
    public List<List<String>> groupAnagramsFrequency(String[] strs) {
        Map<String, List<String>> groups = new HashMap<>();

        for (String str : strs) {
            String key = getFrequencyKey(str);
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
        }

        return new ArrayList<>(groups.values());
    }

    /**
     * Helper method to create frequency-based key
     */
    private String getFrequencyKey(String str) {
        int[] count = new int[26];
        for (char c : str.toCharArray()) {
            count[c - 'a']++;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (count[i] > 0) {
                sb.append((char) (i + 'a')).append(count[i]);
            }
        }

        return sb.toString();
    }

    /**
     * Find if array contains duplicates
     * Time: O(n), Space: O(n)
     */
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> seen = new HashSet<>();

        for (int num : nums) {
            if (!seen.add(num)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Find duplicates within k distance
     * Time: O(n), Space: O(k)
     */
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> indexMap = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            if (indexMap.containsKey(nums[i])) {
                int prevIndex = indexMap.get(nums[i]);
                if (i - prevIndex <= k) {
                    return true;
                }
            }
            indexMap.put(nums[i], i);
        }

        return false;
    }

    /**
     * Count subarrays with exactly k distinct elements
     * Time: O(n), Space: O(k)
     */
    public int subarraysWithKDistinct(int[] nums, int k) {
        return atMostK(nums, k) - atMostK(nums, k - 1);
    }

    /**
     * Helper: count subarrays with at most k distinct elements
     */
    private int atMostK(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        int left = 0, count = 0;

        for (int right = 0; right < nums.length; right++) {
            freq.put(nums[right], freq.getOrDefault(nums[right], 0) + 1);

            while (freq.size() > k) {
                freq.put(nums[left], freq.get(nums[left]) - 1);
                if (freq.get(nums[left]) == 0) {
                    freq.remove(nums[left]);
                }
                left++;
            }

            count += right - left + 1;
        }

        return count;
    }
}