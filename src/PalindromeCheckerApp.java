public class PalindromeCheckerApp {
    public static void main(String[] args) {

        // -------------------------------------------------------
        // Step 1: Declare a hardcoded String literal
        // String is a built-in Java class; "madam" is a String
        // literal stored in the String constant pool.
        // -------------------------------------------------------
        String word = "madam";

        // -------------------------------------------------------
        // Step 2: Reverse the string using StringBuilder
        // StringBuilder.reverse() produces the reversed sequence.
        // -------------------------------------------------------
        String reversed = new StringBuilder(word).reverse().toString();

        // -------------------------------------------------------
        // Step 3: Check palindrome condition using if-else
        // String.equals() performs case-sensitive comparison.
        // -------------------------------------------------------
        System.out.println("=====================================================");
        System.out.println("   Palindrome Checker Management System - UC2");
        System.out.println("=====================================================");
        System.out.println();
        System.out.println("Input String  : " + word);
        System.out.println("Reversed      : " + reversed);
        System.out.println();

        // -------------------------------------------------------
        // Step 4: Conditional Statement (if-else)
        // Evaluates whether the original equals the reversed string.
        // -------------------------------------------------------
        if (word.equals(reversed)) {
            System.out.println("Result: \"" + word + "\" IS a Palindrome.");
        } else {
            System.out.println("Result: \"" + word + "\" is NOT a Palindrome.");
        }

        System.out.println();
        System.out.println("=====================================================");
        System.out.println("   Program exits successfully.");
        System.out.println("=====================================================");
    }
}
