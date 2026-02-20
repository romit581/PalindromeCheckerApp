import java.util.ArrayDeque;
import java.util.Deque;
public class PalindromeCheckerApp {
    public static void main(String[] args) {

        // -------------------------------------------------------
        // Step 1: Declare a hardcoded String literal
        // -------------------------------------------------------
        String original = "nurses run";

        System.out.println("=====================================================");
        System.out.println("   Palindrome Checker Management System - UC7");
        System.out.println("   Method: Deque (Double Ended Queue) Front & Rear");
        System.out.println("=====================================================");
        System.out.println();
        System.out.println("Input String  : \"" + original + "\"");
        System.out.println();

        // -------------------------------------------------------
        // Step 2: Pre-process — normalize to lowercase, keep only letters
        //
        // Real palindromes often ignore spaces, punctuation, and case.
        // "nurses run" → "nursesrun" (spaces removed, lowercased)
        // This step demonstrates practical palindrome validation.
        // -------------------------------------------------------
        String cleaned = original.toLowerCase().replaceAll("[^a-z0-9]", "");
        System.out.println("Cleaned String : \"" + cleaned + "\"  (lowercase, spaces removed)");
        System.out.println();

        // -------------------------------------------------------
        // Step 3: Create a Deque and load all characters
        //
        // Deque<Character>:
        //   - Interface from java.util
        //   - Implemented by ArrayDeque (resizable array, no null)
        //   - Supports: addFirst, addLast, peekFirst, peekLast,
        //               pollFirst, pollLast, offerFirst, offerLast
        //
        // offerLast() inserts each character at the REAR.
        // After loading "nursesrun":
        //   FRONT [ n, u, r, s, e, s, r, u, n ] REAR
        //           0  1  2  3  4  5  6  7  8
        // -------------------------------------------------------
        Deque<Character> deque = new ArrayDeque<>();

        for (int i = 0; i < cleaned.length(); i++) {
            deque.offerLast(cleaned.charAt(i));   // Insert at REAR
        }

        System.out.println("--- Loading Deque (offerLast for each character) ---");
        System.out.println("  Deque after loading: " + deque);
        System.out.println("  Size: " + deque.size() + " characters");
        System.out.println();

        // -------------------------------------------------------
        // Step 4: Compare FRONT and REAR simultaneously
        //
        // pollFirst() → removes and returns the FRONT element
        // pollLast()  → removes and returns the REAR  element
        //
        // Visual shrink for "nursesrun":
        //   Step 1: FRONT[n] == REAR[n] ✓ → deque: [u,r,s,e,s,r,u]
        //   Step 2: FRONT[u] == REAR[u] ✓ → deque: [r,s,e,s,r]
        //   Step 3: FRONT[r] == REAR[r] ✓ → deque: [s,e,s]
        //   Step 4: FRONT[s] == REAR[s] ✓ → deque: [e]
        //   Size == 1 → middle character, no comparison needed → Done!
        //
        // Loop condition: deque.size() > 1
        //   - If size == 1  → middle character of odd-length string
        //                     (a single character is always a match)
        //   - If size == 0  → even-length string fully consumed
        //   Both cases mean all pairs matched → Palindrome!
        // -------------------------------------------------------
        System.out.println("--- Front & Rear Comparison Trace ---");
        System.out.println();
        System.out.printf("  %-6s | %-12s | %-12s | %-10s | %s%n",
                "Step", "Front (poll)", "Rear (poll)", "Match?", "Deque Remaining");
        System.out.println("  -------|--------------|--------------|------------|------------------");

        boolean isPalindrome = true;
        int step = 1;

        while (deque.size() > 1) {

            // Front and Rear Access: remove from both ends simultaneously
            char front = deque.pollFirst();   // Removes from FRONT
            char rear  = deque.pollLast();    // Removes from REAR

            boolean match = (front == rear);

            System.out.printf("  %-6d | front → '%c'   | rear  → '%c'   | %-10s | %s%n",
                    step, front, rear,
                    match ? "✓ YES" : "✗ NO",
                    deque.toString());

            if (!match) {
                isPalindrome = false;
                System.out.println();
                System.out.println("  ✗ Mismatch detected! Stopping early.");
                break;
            }

            step++;
        }

        // Handle remaining middle character (odd-length string)
        if (!deque.isEmpty() && isPalindrome) {
            System.out.printf("  %-6s | %-12s | %-12s | %-10s | %s%n",
                    "-", "middle → '" + deque.peek() + "'",
                    "   (no pair)", "✓ SKIP",
                    "[ ] (empty after skip)");
        }

        // -------------------------------------------------------
        // Step 5: Display final result
        // -------------------------------------------------------
        System.out.println();
        System.out.println("--- Result ---");
        System.out.println("  Original Input : \"" + original + "\"");
        System.out.println("  Cleaned String : \"" + cleaned + "\"");
        System.out.println();

        if (isPalindrome) {
            System.out.println("Result: \"" + original + "\" IS a Palindrome.");
            System.out.println("  → Every front-rear pair matched across the Deque.");
        } else {
            System.out.println("Result: \"" + original + "\" is NOT a Palindrome.");
            System.out.println("  → A front-rear mismatch was detected.");
        }

        // -------------------------------------------------------
        // Step 6: Deque Operations Summary
        // -------------------------------------------------------
        System.out.println();
        System.out.println("--- Deque Operations Reference ---");
        System.out.println();
        System.out.printf("  %-20s | %-30s | %s%n", "Operation", "Method Used", "End Affected");
        System.out.println("  ---------------------|--------------------------------|-------------");
        System.out.printf("  %-20s | %-30s | %s%n", "Insert at rear",  "offerLast(e)",  "REAR");
        System.out.printf("  %-20s | %-30s | %s%n", "Insert at front", "offerFirst(e)", "FRONT");
        System.out.printf("  %-20s | %-30s | %s%n", "Remove from front","pollFirst()",  "FRONT");
        System.out.printf("  %-20s | %-30s | %s%n", "Remove from rear", "pollLast()",   "REAR");
        System.out.printf("  %-20s | %-30s | %s%n", "Peek at front",   "peekFirst()",  "FRONT (no remove)");
        System.out.printf("  %-20s | %-30s | %s%n", "Peek at rear",    "peekLast()",   "REAR  (no remove)");
        System.out.println();
        System.out.println("--- Why Deque is Optimal ---");
        System.out.println("  Single data structure  → No secondary Stack or Queue needed");
        System.out.println("  Direct front/rear access → No reversal algorithm required");
        System.out.println("  Shrinks inward per step → Only n/2 comparisons made");
        System.out.println("  Time Complexity        : O(n)");
        System.out.println("  Space Complexity       : O(n)  — one Deque holding n characters");
        System.out.println();
        System.out.println("=====================================================");
        System.out.println("   Program exits successfully.");
        System.out.println("=====================================================");
    }
}
