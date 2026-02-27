class PalindromeResult {

    // -------------------------------------------------------
    // Private fields — Encapsulation
    // These cannot be accessed directly from outside the class.
    // External code must use the provided getter methods.
    // -------------------------------------------------------
    private final String rawInput;       // Original uncleaned input
    private final String cleanedInput;   // Normalized/preprocessed input
    private final boolean isPalindrome;  // Result of palindrome check
    private final String strategy;       // Strategy used for checking


    PalindromeResult(String rawInput, String cleanedInput,
                     boolean isPalindrome, String strategy) {
        this.rawInput      = rawInput;
        this.cleanedInput  = cleanedInput;
        this.isPalindrome  = isPalindrome;
        this.strategy      = strategy;
    }

    // -------------------------------------------------------
    // Public Getter Methods — Controlled Access (Encapsulation)
    // These are the ONLY way to read the private field values.
    // No setters are provided — the result is immutable.
    // -------------------------------------------------------

    /** @return the original raw input string */
    public String getRawInput()      { return rawInput; }

    /** @return the normalized/cleaned input string */
    public String getCleanedInput()  { return cleanedInput; }

    /** @return true if the input is a palindrome */
    public boolean isPalindrome()    { return isPalindrome; }

    /** @return the name of the strategy used */
    public String getStrategy()      { return strategy; }


    @Override
    public String toString() {
        return String.format(
                "  Raw Input    : \"%s\"%n" +
                        "  Cleaned      : \"%s\"%n" +
                        "  Strategy     : %s%n" +
                        "  Is Palindrome: %s",
                rawInput, cleanedInput, strategy,
                isPalindrome ? "✓ YES" : "✗ NO"
        );
    }
}



class PalindromeChecker {

    // -------------------------------------------------------
    // Private field — tracks how many checks have been run.
    // Demonstrates encapsulated state within the service.
    // -------------------------------------------------------
    private int checkCount;


    PalindromeChecker() {
        this.checkCount = 0;
    }

    // -------------------------------------------------------
    // Public API Method: checkPalindrome(String)
    //
    // Primary entry point for palindrome validation.
    // Orchestrates: normalize → check → package result.
    //
    // The caller only needs to pass a raw string.
    // All internal complexity is hidden (Abstraction).
    //
    // Uses the Two-Pointer strategy internally.
    // -------------------------------------------------------
     PalindromeResult checkPalindrome(String raw) {
        checkCount++;
        String cleaned = normalize(raw);
        boolean result = twoPointerCheck(cleaned);
        return new PalindromeResult(raw, cleaned, result, "Two-Pointer");
    }

    // -------------------------------------------------------
    // Overloaded Method: checkPalindrome(String, String)
    //
    // Method Overloading — same method name, different params.
    // Allows the caller to specify which strategy to use.
    //
    // Supported strategies:
    //   "twopointer" → Two-pointer char comparison (default)
    //   "stack"      → Stack-based reversal (LIFO)
    //   "recursive"  → Recursive start/end comparison
    // -------------------------------------------------------

    public PalindromeResult checkPalindrome(String raw, String strategy) {
        checkCount++;
        String cleaned = normalize(raw);
        boolean result = false;           // Default value; overwritten by switch
        String strategyLabel = "Two-Pointer"; // Default; overwritten by switch

        switch (strategy.toLowerCase()) {
            case "stack":
                result = stackCheck(cleaned);
                strategyLabel = "Stack (LIFO)";
                break;
            case "recursive":
                result = recursiveCheck(cleaned, 0, cleaned.length() - 1);
                strategyLabel = "Recursive";
                break;
            default:
                result = twoPointerCheck(cleaned);
                strategyLabel = "Two-Pointer";
        }

        return new PalindromeResult(raw, cleaned, result, strategyLabel);
    }

    // -------------------------------------------------------
    // Public Getter: getCheckCount()
    // Provides READ-ONLY access to the private checkCount field.
    // -------------------------------------------------------
    /**
     * Returns the total number of palindrome checks performed.
     * @return check count
     */
    public int getCheckCount() { return checkCount; }

    // =====================================================
    // PRIVATE METHODS — Internal Implementation Details
    // These are hidden from the outside world (Encapsulation).
    // Callers interact only with the public checkPalindrome() API.
    // =====================================================

    // -------------------------------------------------------
    // Private Method: normalize(String)
    //
    // Preprocessing pipeline (from UC10):
    //   Step 1: toLowerCase() — remove case differences
    //   Step 2: replaceAll([^a-z0-9], "") — strip non-alphanumeric
    // -------------------------------------------------------
    private String normalize(String raw) {
        return raw.toLowerCase().replaceAll("[^a-z0-9]", "");
    }

    // -------------------------------------------------------
    // Private Method: twoPointerCheck(String)
    //
    // Two-pointer technique (from UC4):
    // Compare characters from both ends moving inward.
    // -------------------------------------------------------
    private boolean twoPointerCheck(String s) {
        int left  = 0;
        int right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) return false;
            left++;
            right--;
        }
        return true;
    }

    // -------------------------------------------------------
    // Private Method: stackCheck(String)
    //
    // Stack-based reversal (from UC5):
    // Push all chars, pop to reverse, compare with original.
    // -------------------------------------------------------
    private boolean stackCheck(String s) {
        java.util.Stack<Character> stack = new java.util.Stack<>();
        for (char c : s.toCharArray()) stack.push(c);

        StringBuilder reversed = new StringBuilder();
        while (!stack.isEmpty()) reversed.append(stack.pop());

        return s.equals(reversed.toString());
    }

    // -------------------------------------------------------
    // Private Method: recursiveCheck(String, int, int)
    //
    // Recursive comparison (from UC9):
    // Base condition: start >= end → true
    // Recursive case: compare ends, recurse inward.
    // -------------------------------------------------------
    private boolean recursiveCheck(String s, int start, int end) {
        if (start >= end) return true;
        if (s.charAt(start) != s.charAt(end)) return false;
        return recursiveCheck(s, start + 1, end - 1);
    }
}


public class PalindromeCheckerApp {

    public static void main(String[] args) {

        System.out.println("=====================================================");
        System.out.println("   Palindrome Checker Management System - UC11");
        System.out.println("   Method: Object-Oriented Palindrome Service");
        System.out.println("=====================================================");
        System.out.println();

        // -------------------------------------------------------
        // OOP in Action: Instantiate the Service Class
        //
        // An OBJECT of PalindromeChecker is created.
        // The caller does not know about private normalize(),
        // twoPointerCheck(), stackCheck(), or recursiveCheck().
        // This is Abstraction + Encapsulation working together.
        // -------------------------------------------------------
        System.out.println("--- OOP Class Structure ---");
        System.out.println();
        System.out.printf("  %-30s | %s%n", "Class", "Responsibility (SRP)");
        System.out.println("  -------------------------------|-------------------------------------------");
        System.out.printf("  %-30s | %s%n", "PalindromeResult",
                "Stores and exposes check outcome data");
        System.out.printf("  %-30s | %s%n", "PalindromeChecker",
                "Performs normalization and palindrome check");
        System.out.printf("  %-30s | %s%n", "UseCase11PalindromeCheckerApp",
                "Entry point; client of the service");
        System.out.println();

        // Instantiate the service — client creates ONE checker object
        PalindromeChecker checker = new PalindromeChecker();

        // -------------------------------------------------------
        // Demo 1: Default strategy (Two-Pointer) via overload 1
        // checkPalindrome(String)
        // -------------------------------------------------------
        System.out.println("--- Demo 1: Default Strategy (Two-Pointer) ---");
        System.out.println();

        String[] testInputs = {
                "madam",
                "RaceCar",
                "A man a plan a canal Panama",
                "Was it a car or a cat I saw?",
                "Hello World",
                "12321"
        };

        for (String input : testInputs) {
            PalindromeResult result = checker.checkPalindrome(input);
            System.out.println(result.toString());
            System.out.println();
        }

        // -------------------------------------------------------
        // Demo 2: Explicit strategy selection via overload 2
        // checkPalindrome(String, String)
        // Demonstrates Method Overloading — same method name,
        // different parameter lists, different behaviour.
        // -------------------------------------------------------
        System.out.println("--- Demo 2: Strategy Selection (Method Overloading) ---");
        System.out.println();

        String phrase = "Never odd or even";

        System.out.println("  Testing: \"" + phrase + "\" with all strategies:");
        System.out.println();

        String[] strategies = {"twopointer", "stack", "recursive"};
        for (String strat : strategies) {
            PalindromeResult r = checker.checkPalindrome(phrase, strat);
            System.out.printf("  %-16s → cleaned: \"%-20s\" → %s%n",
                    r.getStrategy(),
                    r.getCleanedInput(),
                    r.isPalindrome() ? "✓ IS a Palindrome" : "✗ NOT a Palindrome");
        }

        // -------------------------------------------------------
        // Demo 3: Encapsulated state — check count
        // -------------------------------------------------------
        System.out.println();
        System.out.println("--- Demo 3: Encapsulated State ---");
        System.out.println();
        System.out.println("  checker.getCheckCount() = " + checker.getCheckCount());
        System.out.println("  (Private field 'checkCount' accessed via public getter only)");

        // -------------------------------------------------------
        // OOP Concept Summary
        // -------------------------------------------------------
        System.out.println();
        System.out.println("--- OOP Concept Summary ---");
        System.out.println();
        System.out.printf("  %-28s | %s%n", "Concept", "Where Applied");
        System.out.println("  -----------------------------|------------------------------------------------");
        System.out.printf("  %-28s | %s%n", "Encapsulation",
                "private fields + public getters in both classes");
        System.out.printf("  %-28s | %s%n", "Single Responsibility",
                "Each class has exactly one clearly defined job");
        System.out.printf("  %-28s | %s%n", "Abstraction",
                "Caller uses checkPalindrome(); internals are hidden");
        System.out.printf("  %-28s | %s%n", "Method Overloading",
                "checkPalindrome(String) vs checkPalindrome(String, String)");
        System.out.printf("  %-28s | %s%n", "Access Modifiers",
                "private: fields & helpers | public: API methods");
        System.out.printf("  %-28s | %s%n", "Object Instantiation",
                "new PalindromeChecker() creates service object");
        System.out.printf("  %-28s | %s%n", "toString() Override",
                "PalindromeResult.toString() for readable output");
        System.out.println();
        System.out.println("=====================================================");
        System.out.println("   Program exits successfully.");
        System.out.println("=====================================================");
    }
}