public class PalindromeCheckerApp {
    public static void main(String[] args) {

        // -------------------------------------------------------
        // Step 1: Declare a hardcoded String literal
        // -------------------------------------------------------
        String original = "level";

        System.out.println("=====================================================");
        System.out.println("   Palindrome Checker Management System - UC4");
        System.out.println("   Method: Character Array + Two-Pointer Technique");
        System.out.println("=====================================================");
        System.out.println();
        System.out.println("Input String  : " + original);
        System.out.println();

        // -------------------------------------------------------
        // Step 2: Convert String to char[]
        //
        // toCharArray() breaks the String into individual
        // characters stored at index positions 0 to length-1.
        //
        // Example: "level" → ['l', 'e', 'v', 'e', 'l']
        //           Index  →   0    1    2    3    4
        //
        // char[] is a primitive array — lightweight, no object
        // overhead per element, ideal for index-based access.
        // -------------------------------------------------------
        char[] chars = original.toCharArray();

        // Display the char[] contents and indices for clarity
        System.out.print("char[] Contents : [ ");
        for (int i = 0; i < chars.length; i++) {
            System.out.print("'" + chars[i] + "'");
            if (i < chars.length - 1) System.out.print(", ");
        }
        System.out.println(" ]");

        System.out.print("char[] Indices  : [ ");
        for (int i = 0; i < chars.length; i++) {
            System.out.print(" " + i + " ");
            if (i < chars.length - 1) System.out.print(", ");
        }
        System.out.println(" ]");
        System.out.println();

        // -------------------------------------------------------
        // Step 3: Initialize Two Pointers
        //
        // left  → starts at index 0 (first character)
        // right → starts at index length-1 (last character)
        //
        // Visual for "level":
        //   left                    right
        //    ↓                        ↓
        //  [ 'l',  'e',  'v',  'e',  'l' ]
        //     0     1     2     3     4
        // -------------------------------------------------------
        int left  = 0;
        int right = chars.length - 1;

        boolean isPalindrome = true;  // Assume true until mismatch found

        System.out.println("--- Two-Pointer Comparison Trace ---");

        // -------------------------------------------------------
        // Step 4: Two-Pointer while loop
        //
        // Continue while left pointer is strictly less than right.
        // When left >= right, all necessary pairs have been checked.
        // The middle character (odd-length strings) is never compared
        // against itself — it doesn't affect palindrome validity.
        // -------------------------------------------------------
        while (left < right) {

            // Array Indexing: access characters at current pointer positions
            char leftChar  = chars[left];
            char rightChar = chars[right];

            System.out.println("  Comparing index [" + left + "] = '" + leftChar +
                    "'  <-->  index [" + right + "] = '" + rightChar + "'");

            // -------------------------------------------------------
            // Step 5: Compare characters at both pointers
            //
            // If they do NOT match → not a palindrome.
            // Early exit: no need to check remaining characters.
            // This is more efficient than always checking all pairs.
            // -------------------------------------------------------
            if (leftChar != rightChar) {
                isPalindrome = false;
                System.out.println("  ✗ Mismatch found! Exiting early.");
                break;
            } else {
                System.out.println("  ✓ Match!");
            }

            // -------------------------------------------------------
            // Step 6: Move pointers inward
            // left moves right (+1), right moves left (-1)
            // -------------------------------------------------------
            left++;
            right--;
        }

        // -------------------------------------------------------
        // Step 7: Display final result
        // -------------------------------------------------------
        System.out.println();
        System.out.println("--- Result ---");
        if (isPalindrome) {
            System.out.println("Result: \"" + original + "\" IS a Palindrome.");
        } else {
            System.out.println("Result: \"" + original + "\" is NOT a Palindrome.");
        }

        // -------------------------------------------------------
        // Time Complexity Summary
        // -------------------------------------------------------
        System.out.println();
        System.out.println("--- Complexity Analysis ---");
        System.out.println("String Length      : " + original.length());
        System.out.println("Comparisons Made   : " + (original.length() / 2) + "  (at most n/2)");
        System.out.println("Extra Objects Used : None  (in-place char[] access)");
        System.out.println("Time Complexity    : O(n)");
        System.out.println("Space Complexity   : O(n)  (for the char[] copy)");
        System.out.println();
        System.out.println("=====================================================");
        System.out.println("   Program exits successfully.");
        System.out.println("=====================================================");
    }
}
