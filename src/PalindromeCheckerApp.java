import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

public class PalindromeCheckerApp {

    // -------------------------------------------------------
    // Benchmark Configuration Constants
    // -------------------------------------------------------
    static final int    ITERATIONS  = 100_000;   // Runs per algorithm
    static final int    WARMUP_RUNS = 10_000;    // JVM warm-up passes
    static final String TEST_INPUT  =            // Long palindrome for stress test
            "amanaplanacanalpanama";

    // =====================================================
    // ALGORITHM IMPLEMENTATIONS
    // Each method is self-contained, normalized input only.
    // =====================================================

    // -------------------------------------------------------
    // Algorithm 1: String Concatenation Loop (UC3)
    // Builds reversed string via + inside a for loop.
    // Creates new String object each iteration — inefficient.
    // Time: O(n²) due to repeated String object creation
    // -------------------------------------------------------
    static boolean stringConcatReverse(String s) {
        String reversed = "";
        for (int i = s.length() - 1; i >= 0; i--) {
            reversed = reversed + s.charAt(i);
        }
        return s.equals(reversed);
    }

    // -------------------------------------------------------
    // Algorithm 2: char[] Two-Pointer (UC4)
    // Converts to char[], compares from both ends inward.
    // No extra object creation per comparison step.
    // Time: O(n), Space: O(n) for the char[] copy
    // -------------------------------------------------------
    static boolean twoPointer(String s) {
        char[] chars = s.toCharArray();
        int left  = 0;
        int right = chars.length - 1;
        while (left < right) {
            if (chars[left] != chars[right]) return false;
            left++;
            right--;
        }
        return true;
    }

    // -------------------------------------------------------
    // Algorithm 3: Stack-Based Reversal (UC5)
    // Pushes all chars, pops to reverse, compares strings.
    // Stack overhead: object boxing char → Character per push.
    // Time: O(n), Space: O(n)
    // -------------------------------------------------------
    static boolean stackReversal(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) stack.push(c);
        StringBuilder reversed = new StringBuilder();
        while (!stack.isEmpty()) reversed.append(stack.pop());
        return s.equals(reversed.toString());
    }

    // -------------------------------------------------------
    // Algorithm 4: Queue + Stack Comparison (UC6)
    // Loads into both Queue and Stack, dequeues vs pops.
    // Two data structures: 2× memory allocation overhead.
    // Time: O(n), Space: O(2n)
    // -------------------------------------------------------
    static boolean queueStack(String s) {
        java.util.Queue<Character> queue = new java.util.LinkedList<>();
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            queue.offer(c);
            stack.push(c);
        }
        while (!queue.isEmpty()) {
            if (!queue.poll().equals(stack.pop())) return false;
        }
        return true;
    }

    // -------------------------------------------------------
    // Algorithm 5: Deque Front/Rear (UC7)
    // Single Deque, pollFirst + pollLast simultaneously.
    // One structure vs two in UC6, fewer allocations.
    // Time: O(n), Space: O(n)
    // -------------------------------------------------------
    static boolean dequeCheck(String s) {
        Deque<Character> deque = new ArrayDeque<>();
        for (char c : s.toCharArray()) deque.offerLast(c);
        while (deque.size() > 1) {
            if (deque.pollFirst() != deque.pollLast()) return false;
        }
        return true;
    }

    // -------------------------------------------------------
    // Algorithm 6: Linked List Simulated (UC8)
    // Simplified: finds mid via fast/slow pointer on char[],
    // reverses second half, compares.
    // Time: O(n), Space: O(n) for char[] array
    // -------------------------------------------------------
    static boolean linkedListStyle(String s) {
        if (s.isEmpty()) return true;
        char[] arr = s.toCharArray();
        int slow = 0, fast = 0;
        // Fast/slow pointer to find midpoint
        while (fast < arr.length && fast + 1 < arr.length) {
            slow++;
            fast += 2;
        }
        // Reverse second half in-place
        int left = slow, right = arr.length - 1;
        while (left < right) {
            char tmp  = arr[left];
            arr[left] = arr[right];
            arr[right] = tmp;
            left++;
            right--;
        }
        // Compare first half with reversed second half
        for (int i = 0; i < s.length() / 2; i++) {
            if (arr[i] != arr[s.length() - 1 - i]) return false;
        }
        return true;
    }

    // -------------------------------------------------------
    // Algorithm 7: Recursive (UC9)
    // Calls itself inward, uses the JVM call stack.
    // Stack frame creation overhead per recursive call.
    // Time: O(n), Space: O(n) call stack depth
    // -------------------------------------------------------
    static boolean recursive(String s, int start, int end) {
        if (start >= end) return true;
        if (s.charAt(start) != s.charAt(end)) return false;
        return recursive(s, start + 1, end - 1);
    }

    // =====================================================
    // BENCHMARKING ENGINE
    // =====================================================


    interface Algorithm {
        boolean run(String input);
    }

    static long benchmark(String name, Algorithm algo, String input) {

        // --- Warm-up phase (not timed) ---
        for (int i = 0; i < WARMUP_RUNS; i++) {
            algo.run(input);
        }

        // --- Timed phase ---
        long start = System.nanoTime();              // Capture start timestamp
        for (int i = 0; i < ITERATIONS; i++) {
            algo.run(input);
        }
        long end     = System.nanoTime();            // Capture end timestamp
        long total   = end - start;                  // Total elapsed nanoseconds
        long average = total / ITERATIONS;           // Average per call

        return average;
    }


    static String formatTime(long ns) {
        if (ns < 1_000) {
            return ns + " ns";
        } else if (ns < 1_000_000) {
            return String.format("%.2f µs", ns / 1_000.0);
        } else {
            return String.format("%.2f ms", ns / 1_000_000.0);
        }
    }

    static String bar(long value, long max, int width) {
        int filled = (int) Math.round(((double) value / max) * width);
        return "█".repeat(Math.max(1, filled));
    }


    public static void main(String[] args) {

        System.out.println("=====================================================");
        System.out.println("   Palindrome Checker Management System - UC13");
        System.out.println("   Performance Comparison: Algorithm Benchmarking");
        System.out.println("=====================================================");
        System.out.println();

        // -------------------------------------------------------
        // Benchmark Configuration Display
        // -------------------------------------------------------
        System.out.println("--- Benchmark Configuration ---");
        System.out.println();
        System.out.println("  Test Input   : \"" + TEST_INPUT + "\"");
        System.out.println("  Input Length : " + TEST_INPUT.length() + " characters");
        System.out.println("  Iterations   : " + String.format("%,d", ITERATIONS) + " runs per algorithm");
        System.out.println("  Warm-up Runs : " + String.format("%,d", WARMUP_RUNS) + " (JIT compilation trigger)");
        System.out.println();

        // -------------------------------------------------------
        // System.nanoTime() Explanation
        // -------------------------------------------------------
        System.out.println("--- Why System.nanoTime()? ---");
        System.out.println();
        System.out.println("  System.nanoTime()             → nanosecond precision timer");
        System.out.println("  System.currentTimeMillis()    → millisecond precision (too coarse)");
        System.out.println();
        System.out.println("  Pattern used:");
        System.out.println("    long start   = System.nanoTime();");
        System.out.println("    // ... run algorithm ITERATIONS times ...");
        System.out.println("    long end     = System.nanoTime();");
        System.out.println("    long elapsed = end - start;           // total ns");
        System.out.println("    long average = elapsed / ITERATIONS;  // avg ns per call");
        System.out.println();

        // -------------------------------------------------------
        // Register Algorithms
        // LinkedHashMap preserves insertion order for display
        // -------------------------------------------------------
        Map<String, Algorithm> algorithms = new LinkedHashMap<>();
        algorithms.put("UC3  String Concat Loop ",  input -> stringConcatReverse(input));
        algorithms.put("UC4  char[] Two-Pointer  ",  input -> twoPointer(input));
        algorithms.put("UC5  Stack Reversal      ",  input -> stackReversal(input));
        algorithms.put("UC6  Queue + Stack       ",  input -> queueStack(input));
        algorithms.put("UC7  Deque Front/Rear    ",  input -> dequeCheck(input));
        algorithms.put("UC8  Linked List Style   ",  input -> linkedListStyle(input));
        algorithms.put("UC9  Recursive           ",  input -> recursive(input, 0, input.length() - 1));

        // -------------------------------------------------------
        // Run All Benchmarks
        // -------------------------------------------------------
        System.out.println("--- Running Benchmarks (please wait...) ---");
        System.out.println();

        Map<String, Long> results = new LinkedHashMap<>();
        for (Map.Entry<String, Algorithm> entry : algorithms.entrySet()) {
            System.out.print("  Benchmarking: " + entry.getKey().trim() + " ... ");
            long avg = benchmark(entry.getKey(), entry.getValue(), TEST_INPUT);
            results.put(entry.getKey(), avg);
            System.out.println("done  (" + formatTime(avg) + " avg)");
        }
        System.out.println();

        // -------------------------------------------------------
        // Find fastest, slowest for relative comparison
        // -------------------------------------------------------
        long fastest = Long.MAX_VALUE;
        long slowest = Long.MIN_VALUE;
        String fastestName = "";
        String slowestName = "";

        for (Map.Entry<String, Long> e : results.entrySet()) {
            if (e.getValue() < fastest) { fastest = e.getValue(); fastestName = e.getKey(); }
            if (e.getValue() > slowest) { slowest = e.getValue(); slowestName = e.getKey(); }
        }

        // -------------------------------------------------------
        // Sort results fastest → slowest
        // -------------------------------------------------------
        java.util.List<Map.Entry<String, Long>> sorted = new java.util.ArrayList<>(results.entrySet());
        sorted.sort(java.util.Map.Entry.comparingByValue());

        // -------------------------------------------------------
        // Display Results Table
        // -------------------------------------------------------
        System.out.println("--- Performance Results (Fastest → Slowest) ---");
        System.out.println();
        System.out.printf("  %-6s | %-28s | %-12s | %-10s | %s%n",
                "Rank", "Algorithm", "Avg Time", "vs Fastest", "Bar Chart");
        System.out.println("  -------|------------------------------|--------------|------------|--------------------");

        int rank = 1;
        for (Map.Entry<String, Long> entry : sorted) {
            long   time    = entry.getValue();
            double vsBase  = (double) time / fastest;
            String ratio   = (rank == 1) ? "baseline" : String.format("%.1fx slower", vsBase);
            String barStr  = bar(time, slowest, 20);

            System.out.printf("  %-6d | %-28s | %-12s | %-10s | %s%n",
                    rank,
                    entry.getKey().trim(),
                    formatTime(time),
                    ratio,
                    barStr);
            rank++;
        }

        // -------------------------------------------------------
        // Summary Observations
        // -------------------------------------------------------
        System.out.println();
        System.out.println("--- Observations ---");
        System.out.println();
        System.out.println("  Fastest : " + fastestName.trim());
        System.out.println("  Slowest : " + slowestName.trim());
        System.out.println();
        System.out.println("  Key Observations:");
        System.out.println("  • UC3  String Concat Loop creates O(n²) temporary String");
        System.out.println("    objects — worst memory performance.");
        System.out.println("  • UC4  char[] Two-Pointer is typically fastest: no object");
        System.out.println("    boxing, no extra allocation beyond the char[] copy.");
        System.out.println("  • UC5  Stack and UC6 Queue+Stack incur boxing overhead");
        System.out.println("    (char → Character) for every element stored.");
        System.out.println("  • UC7  Deque is competitive but uses more method calls");
        System.out.println("    than the direct index access of Two-Pointer.");
        System.out.println("  • UC8  Linked List style uses in-place char[] swap —");
        System.out.println("    efficient but adds swap and mid-finding passes.");
        System.out.println("  • UC9  Recursion has call stack frame overhead per step,");
        System.out.println("    making it slower than iterative approaches.");
        System.out.println();

        // -------------------------------------------------------
        // Key Concept Summary
        // -------------------------------------------------------
        System.out.println("--- Key Concept Summary ---");
        System.out.println();
        System.out.printf("  %-26s | %s%n", "Concept", "Detail");
        System.out.println("  ---------------------------|----------------------------------------------");
        System.out.printf("  %-26s | %s%n", "System.nanoTime()",
                "High-resolution JVM timer; measures elapsed ns precisely");
        System.out.printf("  %-26s | %s%n", "Warm-up Phase",
                "Triggers JIT compilation; removes interpreter overhead");
        System.out.printf("  %-26s | %s%n", "Averaging over N runs",
                "Smooths out JVM scheduling noise and GC pauses");
        System.out.printf("  %-26s | %s%n", "Relative Comparison",
                "'X times slower' more meaningful than raw nanoseconds");
        System.out.printf("  %-26s | %s%n", "Boxing Overhead",
                "char → Character per Stack/Queue element adds allocation cost");
        System.out.printf("  %-26s | %s%n", "String Immutability Cost",
                "UC3 creates n new String objects in the loop = O(n²) space");
        System.out.printf("  %-26s | %s%n", "Algorithm Complexity",
                "All except UC3 are O(n) time; UC3 is O(n²) due to + in loop");
        System.out.println();
        System.out.println("=====================================================");
        System.out.println("   Program exits successfully.");
        System.out.println("=====================================================");
    }
}