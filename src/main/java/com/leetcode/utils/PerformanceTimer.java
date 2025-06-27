package com.leetcode.utils;

/**
 * Utility class for measuring performance of algorithms
 */
public class PerformanceTimer {
    private long startTime;
    private long endTime;
    private final String operationName;

    public PerformanceTimer(String operationName) {
        this.operationName = operationName;
    }

    public void start() {
        startTime = System.nanoTime();
    }

    public void stop() {
        endTime = System.nanoTime();
    }

    public double getTimeInMilliseconds() {
        return (endTime - startTime) / 1_000_000.0;
    }

    public double getTimeInMicroseconds() {
        return (endTime - startTime) / 1_000.0;
    }

    public void printResult() {
        double timeMs = getTimeInMilliseconds();
        System.out.println(operationName + " completed in: " +
                String.format("%.2f ms", timeMs));
    }

    /**
     * Time a runnable operation
     */
    public static void timeOperation(String name, Runnable operation) {
        PerformanceTimer timer = new PerformanceTimer(name);
        timer.start();
        operation.run();
        timer.stop();
        timer.printResult();
    }

    /**
     * Time a function and return its result
     */
    public static <T> T timeFunction(String name, java.util.function.Supplier<T> function) {
        PerformanceTimer timer = new PerformanceTimer(name);
        timer.start();
        T result = function.get();
        timer.stop();
        timer.printResult();
        return result;
    }
}
