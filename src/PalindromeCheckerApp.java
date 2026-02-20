public class PalindromeCheckerApp {
    public static void main(String[] args) {

        // -------------------------------------------------------
        // Step 1: Declare a hardcoded String literal
        // The String "racecar" is stored in the constant pool.
        // String is immutable - it cannot be changed once created.
        // -------------------------------------------------------
        String original = "racecar";

        // -------------------------------------------------------
        // Step 2: Reverse the string manually using a for loop
        //
        // Loop starts at the last index (length - 1) and
        // iterates backwards down to index 0.
        //
        // charAt(i) retrieves the character at position i.
        //
        // String Concatenation (+):
        // Each iteration creates a NEW String object in memory
        // because String is immutable. The variable 'reversed'
        // is reassigned to each newly created String.
        //
        // Memory Trace Example for "abc":
        //   Iteration 1: reversed = "" + 'c'  → new String "c"
        //   Iteration 2: reversed = "c" + 'b' → new String "cb"
        //   Iteration 3: reversed = "cb" + 'a'→ new String "cba"
        // -------------------------------------------------------
        String reversed = "";   // Start with an empty String literal

        for (int i = original.length() - 1; i >= 0; i--) {
            // charAt(i) extracts the character at index i
            // + operator concatenates it to the growing reversed string
            // Each + creates a new String object (String Immutability)
            reversed = reversed + original.charAt(i);
        }

        // -------------------------------------------------------
        // Step 3: Compare using equals()
        //
        // equals() compares the CONTENT of two String objects.
        // Using == would compare memory references (object address),
        // not the actual characters — which would be incorrect here.
        // -------------------------------------------------------
        System.out.println("=====================================================");
        System.out.println("   Palindrome Checker Management System - UC3");
        System.out.println("   Method: String Reverse Using for Loop");
        System.out.println("=====================================================");
        System.out.println();
        System.out.println("Input String  : " + original);
        System.out.println("Reversed      : " + reversed);
        System.out.println();

        // -------------------------------------------------------
        // Step 4: Conditional check and display result
        // -------------------------------------------------------
        if (original.equals(reversed)) {
            System.out.println("Result: \"" + original + "\" IS a Palindrome.");
        } else {
            System.out.println("Result: \"" + original + "\" is NOT a Palindrome.");
        }

        // -------------------------------------------------------
        // Note on String Immutability & Performance:
        // Using + inside a loop is inefficient for large strings
        // because each concatenation allocates a new String object.
        // For n characters, this creates O(n) temporary objects.
        // StringBuilder (UC4) avoids this by mutating a single buffer.
        // -------------------------------------------------------
        System.out.println();
        System.out.println("[ Note: Each + in the loop created a new String object ]");
        System.out.println("[ Total new String objects created: " + original.length() + " ]");
        System.out.println();
        System.out.println("=====================================================");
        System.out.println("   Program exits successfully.");
        System.out.println("=====================================================");
    }
}
