package com.leetcode.algorithms.searching;

/**
 * Binary search on answer space (search for optimal value)
 */
public class SearchInAnswerSpace {

    /**
     * Koko eating bananas - minimum eating speed
     * Time: O(n * log(max)), Space: O(1)
     */
    public int minEatingSpeed(int[] piles, int h) {
        int left = 1, right = getMax(piles);

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (canFinish(piles, mid, h)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    private boolean canFinish(int[] piles, int speed, int h) {
        int hours = 0;
        for (int pile : piles) {
            hours += (pile + speed - 1) / speed; // Ceiling division
        }
        return hours <= h;
    }

    private int getMax(int[] arr) {
        int max = arr[0];
        for (int num : arr) {
            max = Math.max(max, num);
        }
        return max;
    }

    /**
     * Capacity to ship packages within D days
     * Time: O(n * log(sum)), Space: O(1)
     */
    public int shipWithinDays(int[] weights, int days) {
        int left = getMax(weights);
        int right = getSum(weights);

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (canShip(weights, mid, days)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    private boolean canShip(int[] weights, int capacity, int days) {
        int daysNeeded = 1;
        int currentWeight = 0;

        for (int weight : weights) {
            if (currentWeight + weight > capacity) {
                daysNeeded++;
                currentWeight = weight;
            } else {
                currentWeight += weight;
            }
        }

        return daysNeeded <= days;
    }

    private int getSum(int[] arr) {
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        return sum;
    }

    /**
     * Split array largest sum
     * Time: O(n * log(sum)), Space: O(1)
     */
    public int splitArray(int[] nums, int m) {
        int left = getMax(nums);
        int right = getSum(nums);

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (canSplit(nums, mid, m)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    private boolean canSplit(int[] nums, int maxSum, int m) {
        int subarrays = 1;
        int currentSum = 0;

        for (int num : nums) {
            if (currentSum + num > maxSum) {
                subarrays++;
                currentSum = num;
            } else {
                currentSum += num;
            }
        }

        return subarrays <= m;
    }

    /**
     * Find the smallest divisor given a threshold
     * Time: O(n * log(max)), Space: O(1)
     */
    public int smallestDivisor(int[] nums, int threshold) {
        int left = 1, right = getMax(nums);

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (getDivisionSum(nums, mid) <= threshold) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    private int getDivisionSum(int[] nums, int divisor) {
        int sum = 0;
        for (int num : nums) {
            sum += (num + divisor - 1) / divisor; // Ceiling division
        }
        return sum;
    }
}
