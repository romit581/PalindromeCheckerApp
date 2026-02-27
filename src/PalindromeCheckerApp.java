
public class PalindromeCheckerApp {
    static boolean isPalindrome(String str, int start, int end) {

        // ---------------------------------------------------
        // BASE CONDITION 1: Indices have crossed or met
        //
        // start > end → all pairs checked, none mismatched
        //   → Occurs for odd-length strings after middle char
        // start == end → single middle character remaining
        //   → A single character is always a palindrome
        //
        // This is what STOPS the recursion from going infinite.
        // Without this condition, calls would continue until
        // a StackOverflowError is thrown.
        // ---------------------------------------------------
        if (start >= end) {
            System.out.println("  [Base Condition] start(" + start +
                    ") >= end(" + end + ") → return true  ← UNWIND begins");
            return true;
        }

        // ---------------------------------------------------
        // BASE CONDITION 2: Characters do NOT match
        //
        // If outermost characters differ → not a palindrome.
        // Return false immediately (no deeper recursion needed).
        // ---------------------------------------------------
        if (str.charAt(start) != str.charAt(end)) {
            System.out.println("  [Mismatch] '" + str.charAt(start) +
                    "' != '" + str.charAt(end) +
                    "' at start(" + start + ") end(" + end +
                    ") → return false  ← UNWIND begins");
            return false;
        }

        // ---------------------------------------------------
        // RECURSIVE CASE:
        //
        // Outer characters match → recurse inward.
        // Reduce the problem: move start right, end left.
        // The result of the inner call determines the result
        // of this call (true only if ALL inner calls return true).
        // ---------------------------------------------------
        System.out.println("  [Recurse] '" + str.charAt(start) +
                "' == '" + str.charAt(end) +
                "' at indices [" + start + "] & [" + end +
                "] → call isPalindrome(str, " + (start + 1) +
                ", " + (end - 1) + ")");

        return isPalindrome(str, start + 1, end - 1);
    }
    public static void main(String[] args) {

        // -------------------------------------------------------
        // Step 1: Declare hardcoded String
        // -------------------------------------------------------
        String original = "racecar";

        System.out.println("=====================================================");
        System.out.println("   Palindrome Checker Management System - UC9");
        System.out.println("   Method: Recursion + Call Stack");
        System.out.println("=====================================================");
        System.out.println();
        System.out.println("Input String : \"" + original + "\"");
        System.out.println("Length       : " + original.length());
        System.out.println("Indices      : 0 to " + (original.length() - 1));
        System.out.println();

        // -------------------------------------------------------
        // Step 2: Display character index map for reference
        // -------------------------------------------------------
        System.out.println("--- Character Index Map ---");
        System.out.print("  Char  : ");
        for (int i = 0; i < original.length(); i++) {
            System.out.printf("  %c  ", original.charAt(i));
        }
        System.out.println();
        System.out.print("  Index : ");
        for (int i = 0; i < original.length(); i++) {
            System.out.printf("  %d  ", i);
        }
        System.out.println();
        System.out.println();

        // -------------------------------------------------------
        // Step 3: Initiate recursive palindrome check
        //
        // First call: start = 0 (first index), end = length - 1 (last index)
        // Each subsequent call moves start forward and end backward.
        // -------------------------------------------------------
        System.out.println("--- Recursive Call Stack Trace ---");
        System.out.println();
        System.out.println("  DESCENDING (building the call stack):");
        System.out.println();

        boolean result = isPalindrome(original, 0, original.length() - 1);

        // -------------------------------------------------------
        // Step 4: Visualise full Call Stack structure
        // -------------------------------------------------------
        System.out.println();
        System.out.println("--- Call Stack Structure Visualised ---");
        System.out.println();
        System.out.println("  Each row = one stack frame pushed into memory");
        System.out.println("  Frames unwind (pop) bottom-to-top after base condition");
        System.out.println();

        int frameWidth = 45;
        String separator = "  +" + "-".repeat(frameWidth) + "+";

        // Build and print call stack frames (top = deepest/base, bottom = first call)
        String[] frames = new String[original.length() / 2 + 2];
        int frameCount = 0;

        // Base frame
        int mid = original.length() / 2;
        if (original.length() % 2 == 1) {
            frames[frameCount++] = String.format(" BASE: isPalindrome(str, %d, %d) → start>=end → true", mid, mid);
        } else {
            frames[frameCount++] = String.format(" BASE: isPalindrome(str, %d, %d) → start>=end → true", mid, mid);
        }

        // Recursive frames (inner to outer)
        for (int i = mid - 1; i >= 0; i--) {
            int j = original.length() - 1 - i;
            frames[frameCount++] = String.format(" CALL: isPalindrome(str, %d, %d) '%c'=='%c' → recurse inward",
                    i, j,
                    original.charAt(i),
                    original.charAt(j));
        }

        // Print top-to-bottom (top = innermost = base)
        System.out.println("  TOP (most recent frame — base condition)");
        System.out.println(separator);
        for (int i = 0; i < frameCount; i++) {
            System.out.printf("  |%-" + frameWidth + "s|%n", frames[i]);
            System.out.println(separator);
        }
        System.out.println("  BOTTOM (first call from main)");

        // -------------------------------------------------------
        // Step 5: Display final result
        // -------------------------------------------------------
        System.out.println();
        System.out.println("--- Result ---");
        if (result) {
            System.out.println("Result: \"" + original + "\" IS a Palindrome.");
            System.out.println("  → All recursive character comparisons returned true.");
            System.out.println("  → Call stack unwound fully with no mismatches.");
        } else {
            System.out.println("Result: \"" + original + "\" is NOT a Palindrome.");
            System.out.println("  → A mismatch triggered false to propagate up the call stack.");
        }

        // -------------------------------------------------------
        // Step 6: Key Concept Summary
        // -------------------------------------------------------
        System.out.println();
        System.out.println("--- Key Concept Summary ---");
        System.out.println();
        System.out.printf("  %-22s | %s%n", "Concept", "Explanation");
        System.out.println("  -----------------------|-----------------------------------------------");
        System.out.printf("  %-22s | %s%n", "Recursion",
                "Method calls itself with smaller start/end range");
        System.out.printf("  %-22s | %s%n", "Base Condition 1",
                "start >= end → all pairs checked → return true");
        System.out.printf("  %-22s | %s%n", "Base Condition 2",
                "char mismatch → return false immediately");
        System.out.printf("  %-22s | %s%n", "Recursive Case",
                "chars match → isPalindrome(str, start+1, end-1)");
        System.out.printf("  %-22s | %s%n", "Call Stack",
                "Each call pushes a new frame; base pops them all");
        System.out.printf("  %-22s | %s%n", "Stack Depth",
                "n/2 frames maximum (one per character pair)");
        System.out.printf("  %-22s | %s%n", "Time Complexity",
                "O(n) — n/2 comparisons");
        System.out.printf("  %-22s | %s%n", "Space Complexity",
                "O(n) — n/2 stack frames held in memory");
        System.out.println();
        System.out.println("  Important: No loops, no arrays, no extra data structures.");
        System.out.println("  The call stack itself IS the data structure.");
        System.out.println();
        System.out.println("=====================================================");
        System.out.println("   Program exits successfully.");
        System.out.println("=====================================================");
    }
}
