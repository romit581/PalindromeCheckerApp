import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
public class PalindromeCheckerApp {
    public static void main(String[] args) {

        // -------------------------------------------------------
        // Step 1: Declare a hardcoded String literal
        // -------------------------------------------------------
        String original = "racecar";

        System.out.println("=====================================================");
        System.out.println("   Palindrome Checker Management System - UC6");
        System.out.println("   Method: Queue (FIFO) + Stack (LIFO) Comparison");
        System.out.println("=====================================================");
        System.out.println();
        System.out.println("Input String : " + original);
        System.out.println();

        // -------------------------------------------------------
        // Step 2: Create Queue and Stack data structures
        //
        // Queue<Character>:
        //   - Interface from java.util
        //   - Implemented by LinkedList (doubly-linked list)
        //   - Follows FIFO: first element enqueued is first dequeued
        //   - Front → [ first in ... last in ] ← Rear
        //
        // Stack<Character>:
        //   - Class from java.util
        //   - Follows LIFO: last element pushed is first popped
        //   - Bottom → [ first in ... last in ] ← TOP
        // -------------------------------------------------------
        Queue<Character> queue = new LinkedList<>();
        Stack<Character> stack = new Stack<>();

        // -------------------------------------------------------
        // Step 3: ENQUEUE into Queue & PUSH onto Stack simultaneously
        //
        // Both structures receive the exact same characters
        // in the exact same order.
        // The difference emerges only when elements are REMOVED.
        //
        // Enqueue/Push Trace for "racecar":
        //   Queue (FIFO): front[ r, a, c, e, c, a, r ]rear
        //   Stack (LIFO): bottom[ r, a, c, e, c, a, r ]TOP
        // -------------------------------------------------------
        System.out.println("--- ENQUEUE & PUSH (Loading both structures) ---");
        for (int i = 0; i < original.length(); i++) {
            char c = original.charAt(i);
            queue.offer(c);   // Enqueue: adds to the REAR of the Queue
            stack.push(c);    // Push:    adds to the TOP  of the Stack
            System.out.println("  enqueue('" + c + "') + push('" + c + "')");
        }

        System.out.println();
        System.out.println("  Queue state (front → rear) : " + queue);
        System.out.println("  Stack state (bottom → top) : " + stack);
        System.out.println();

        // -------------------------------------------------------
        // Step 4: DEQUEUE from Queue & POP from Stack — Compare
        //
        // Queue.poll() removes from the FRONT (FIFO):
        //   Returns characters in ORIGINAL order → r, a, c, e, c, a, r
        //
        // Stack.pop() removes from the TOP (LIFO):
        //   Returns characters in REVERSED order → r, a, c, e, c, a, r
        //
        // For a palindrome, FIFO output == LIFO output at every step.
        //
        // Side-by-Side Comparison Table:
        //   Step | Queue (FIFO) | Stack (LIFO) | Match?
        //   -----|------------- |------------- |-------
        //     1  |     'r'      |     'r'      |  YES
        //     2  |     'a'      |     'a'      |  YES
        //     3  |     'c'      |     'c'      |  YES
        //     4  |     'e'      |     'e'      |  YES
        //     5  |     'c'      |     'c'      |  YES
        //     6  |     'a'      |     'a'      |  YES
        //     7  |     'r'      |     'r'      |  YES
        // -------------------------------------------------------
        System.out.println("--- DEQUEUE vs POP Comparison (FIFO vs LIFO) ---");
        System.out.println();
        System.out.printf("  %-6s | %-14s | %-14s | %s%n",
                "Step", "Queue (FIFO)", "Stack (LIFO)", "Match?");
        System.out.println("  -------|----------------|----------------|--------");

        boolean isPalindrome = true;
        int step = 1;

        while (!queue.isEmpty() && !stack.isEmpty()) {

            // Dequeue: removes from FRONT of Queue (FIFO — original order)
            char fromQueue = queue.poll();

            // Pop: removes from TOP of Stack (LIFO — reversed order)
            char fromStack = stack.pop();

            // Logical Comparison: do the two outputs match?
            boolean match = (fromQueue == fromStack);

            System.out.printf("  %-6d | %-14s | %-14s | %s%n",
                    step,
                    "dequeue → '" + fromQueue + "'",
                    "pop     → '" + fromStack + "'",
                    match ? "✓ YES" : "✗ NO  ← Mismatch!");

            if (!match) {
                isPalindrome = false;
            }

            step++;
        }

        // -------------------------------------------------------
        // Step 5: Display final result
        // -------------------------------------------------------
        System.out.println();
        System.out.println("--- Result ---");
        if (isPalindrome) {
            System.out.println("Result: \"" + original + "\" IS a Palindrome.");
            System.out.println("  → All FIFO (Queue) outputs matched LIFO (Stack) outputs.");
        } else {
            System.out.println("Result: \"" + original + "\" is NOT a Palindrome.");
            System.out.println("  → At least one FIFO output did NOT match its LIFO counterpart.");
        }

        // -------------------------------------------------------
        // Step 6: FIFO vs LIFO Concept Summary
        // -------------------------------------------------------
        System.out.println();
        System.out.println("--- Queue vs Stack Summary ---");
        System.out.println();
        System.out.printf("  %-22s | %-20s | %-20s%n",
                "Property", "Queue", "Stack");
        System.out.println("  -----------------------|----------------------|-----------------------");
        System.out.printf("  %-22s | %-20s | %-20s%n",
                "Principle",      "FIFO (First In First Out)", "LIFO (Last In First Out)");
        System.out.printf("  %-22s | %-20s | %-20s%n",
                "Insert Operation",  "offer() / enqueue()",  "push()");
        System.out.printf("  %-22s | %-20s | %-20s%n",
                "Remove Operation",  "poll() / dequeue()",   "pop()");
        System.out.printf("  %-22s | %-20s | %-20s%n",
                "Removal Order",     "Original (forward)",   "Reversed (backward)");
        System.out.printf("  %-22s | %-20s | %-20s%n",
                "Java Implementation","LinkedList",           "Stack");
        System.out.printf("  %-22s | %-20s | %-20s%n",
                "Palindrome Role",   "Provides original",    "Provides reversed");
        System.out.println();
        System.out.println("  Core Insight: If FIFO output == LIFO output → Palindrome confirmed.");
        System.out.println();
        System.out.println("=====================================================");
        System.out.println("   Program exits successfully.");
        System.out.println("=====================================================");
    }
}
