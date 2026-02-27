
public class PalindromeCheckerApp {
    static String normalize(String raw) {
        return raw.toLowerCase()            // Step 1: Case normalization
                .replaceAll("[^a-z0-9]", ""); // Step 2: Strip non-alphanumeric
    }

    static boolean isPalindrome(String cleaned) {
        int left  = 0;
        int right = cleaned.length() - 1;

        while (left < right) {
            if (cleaned.charAt(left) != cleaned.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    static void checkAndDisplay(String raw) {
        System.out.println("  ┌─────────────────────────────────────────────────┐");
        System.out.println("  │ Input   : \"" + raw + "\"");

        // -------------------------------------------------------
        // Preprocessing Step 1: toLowerCase()
        //
        // Java's String.toLowerCase() converts every uppercase
        // character to its lowercase equivalent using Unicode rules.
        // A new String object is returned (String is immutable).
        //
        // Example: "A Man A Plan" → "a man a plan"
        // -------------------------------------------------------
        String lowered = raw.toLowerCase();
        System.out.println("  │ Step 1  : toLowerCase()         → \"" + lowered + "\"");

        // -------------------------------------------------------
        // Preprocessing Step 2: replaceAll(regex, replacement)
        //
        // Regular Expression: [^a-z0-9]
        //   [ ]     → character class (match one of these)
        //   ^       → negation inside [ ] (NOT any of these)
        //   a-z     → any lowercase letter (a,b,c,...z)
        //   0-9     → any digit (0,1,2,...9)
        //   Result  → matches any char that is NOT a letter/digit
        //
        // replaceAll replaces EVERY match with "" (removes them).
        // Java's regex engine scans the entire string in O(n).
        //
        // Example: "a man a plan" → "amanaplan"
        // -------------------------------------------------------
        String cleaned = lowered.replaceAll("[^a-z0-9]", "");
        System.out.println("  │ Step 2  : replaceAll([^a-z0-9]) → \"" + cleaned + "\"");
        System.out.println("  │");

        // -------------------------------------------------------
        // Two-Pointer Comparison Trace
        // -------------------------------------------------------
        System.out.print("  │ Indices : [");
        for (int i = 0; i < cleaned.length(); i++) {
            System.out.print(i);
            if (i < cleaned.length() - 1) System.out.print("|");
        }
        System.out.println("]");

        System.out.print("  │ Chars   : [");
        for (int i = 0; i < cleaned.length(); i++) {
            System.out.print(cleaned.charAt(i));
            if (i < cleaned.length() - 1) System.out.print("|");
        }
        System.out.println("]");
        System.out.println("  │");

        // Run two-pointer and show comparison pairs
        int left  = 0;
        int right = cleaned.length() - 1;
        boolean palindrome = true;

        System.out.println("  │ Two-Pointer Trace:");
        while (left < right) {
            char l = cleaned.charAt(left);
            char r = cleaned.charAt(right);
            boolean match = (l == r);
            System.out.printf("  │   [%d]'%c'  ←→  [%d]'%c'  %s%n",
                    left, l, right, r,
                    match ? "✓" : "✗ MISMATCH");
            if (!match) { palindrome = false; break; }
            left++;
            right--;
        }

        System.out.println("  │");
        System.out.println("  │ Result  : \"" + raw + "\"");
        System.out.println("  │          → " + (palindrome
                ? "✓ IS a Palindrome"
                : "✗ is NOT a Palindrome"));
        System.out.println("  └─────────────────────────────────────────────────┘");
        System.out.println();
    }
    public static void main(String[] args) {

        System.out.println("=====================================================");
        System.out.println("   Palindrome Checker Management System - UC10");
        System.out.println("   Method: String Preprocessing + Regex Normalization");
        System.out.println("=====================================================");
        System.out.println();

        // -------------------------------------------------------
        // Preprocessing Pipeline Reference
        // -------------------------------------------------------
        System.out.println("--- Preprocessing Pipeline ---");
        System.out.println();
        System.out.printf("  %-6s | %-25s | %-30s | %s%n",
                "Step", "Operation", "Method", "Effect");
        System.out.println("  -------|---------------------------|--------------------------------|---------------------------");
        System.out.printf("  %-6s | %-25s | %-30s | %s%n",
                "1", "Case Normalization",
                "String.toLowerCase()",
                "\"RaCeCaR\" → \"racecar\"");
        System.out.printf("  %-6s | %-25s | %-30s | %s%n",
                "2", "Strip Non-Alphanumeric",
                "replaceAll(\"[^a-z0-9]\", \"\")",
                "\"a man\" → \"aman\"");
        System.out.printf("  %-6s | %-25s | %-30s | %s%n",
                "3", "Palindrome Check",
                "Two-Pointer (char comparison)",
                "Compare from both ends inward");
        System.out.println();

        // -------------------------------------------------------
        // Regular Expression Breakdown
        // -------------------------------------------------------
        System.out.println("--- Regular Expression Breakdown: [^a-z0-9] ---");
        System.out.println();
        System.out.printf("  %-12s | %s%n", "Token", "Meaning");
        System.out.println("  -------------|-----------------------------------------------");
        System.out.printf("  %-12s | %s%n", "[ ]",    "Character class — match one character from this set");
        System.out.printf("  %-12s | %s%n", "^",      "Inside [ ]: negation — match anything NOT in the set");
        System.out.printf("  %-12s | %s%n", "a-z",    "Range: any lowercase letter from a to z");
        System.out.printf("  %-12s | %s%n", "0-9",    "Range: any digit from 0 to 9");
        System.out.printf("  %-12s | %s%n", "Together","Match any character that is NOT a letter or digit");
        System.out.printf("  %-12s | %s%n", "Action", "replaceAll removes every matched character");
        System.out.println();

        // -------------------------------------------------------
        // Test Suite: varied inputs
        // -------------------------------------------------------
        System.out.println("--- Test Suite ---");
        System.out.println();

        // Test Case 1: Classic single word — all lowercase
        checkAndDisplay("madam");

        // Test Case 2: Mixed case — requires toLowerCase()
        checkAndDisplay("RaceCar");

        // Test Case 3: Phrase with spaces — requires both steps
        checkAndDisplay("A man a plan a canal Panama");

        // Test Case 4: Phrase with punctuation
        checkAndDisplay("Was it a car or a cat I saw?");

        // Test Case 5: Non-palindrome with spaces
        checkAndDisplay("Hello World");

        // Test Case 6: Numbers
        checkAndDisplay("12321");

        
}
