import java.util.Stack;
public class PalindromeCheckerApp {
    public static void main(String[] args) {

        // -------------------------------------------------------
        // Step 1: Declare a hardcoded String literal
        // -------------------------------------------------------
        String original = "madam";

        System.out.println("=====================================================");
        System.out.println("   Palindrome Checker Management System - UC5");
        System.out.println("   Method: Stack-Based Reversal (LIFO)");
        System.out.println("=====================================================");
        System.out.println();
        System.out.println("Input String  : " + original);
        System.out.println();

        // -------------------------------------------------------
        // Step 2: Create a Stack of Characters
        //
        // Stack<Character> uses Java's generic Stack class.
        // Character is the wrapper class for the primitive char.
        // The Stack internally manages a dynamic array of elements.
        //
        // Stack Structure (visualised as vertical):
        //   TOP → [ last pushed character ]
        //          [ ...                  ]
        //   BOT → [ first pushed character]
        // -------------------------------------------------------
        Stack<Character> stack = new Stack<>();

        // -------------------------------------------------------
        // Step 3: PUSH each character onto the Stack
        //
        // We iterate left to right through the original string.
        // charAt(i) extracts each character by index.
        // push() places the character on top of the stack.
        //
        // Push Trace for "madam":
        //   push('m') → Stack: [m]
        //   push('a') → Stack: [m, a]
        //   push('d') → Stack: [m, a, d]
        //   push('a') → Stack: [m, a, d, a]
        //   push('m') → Stack: [m, a, d, a, m]  ← TOP
        // -------------------------------------------------------
        System.out.println("--- PUSH Operation (left to right) ---");
        for (int i = 0; i < original.length(); i++) {
            char c = original.charAt(i);
            stack.push(c);
            System.out.println("  push('" + c + "')  →  Stack TOP: " + stack.peek());
        }

        System.out.println();
        System.out.println("  Stack after all pushes (bottom → top): " + stack);
        System.out.println();

        // -------------------------------------------------------
        // Step 4: POP characters off the Stack to reverse the string
        //
        // pop() removes and returns the TOP element (LIFO order).
        // The last character pushed ('m') is the first to be popped.
        // This naturally produces the string in reverse order.
        //
        // Pop Trace for "madam":
        //   pop() → 'm'  reversed so far: "m"
        //   pop() → 'a'  reversed so far: "ma"
        //   pop() → 'd'  reversed so far: "mad"
        //   pop() → 'a'  reversed so far: "mada"
        //   pop() → 'm'  reversed so far: "madam"
        // -------------------------------------------------------
        System.out.println("--- POP Operation (LIFO — top to bottom) ---");
        String reversed = "";

        while (!stack.isEmpty()) {
            char popped = stack.pop();
            reversed = reversed + popped;
            System.out.println("  pop()  → '" + popped + "'  →  Reversed so far: \"" + reversed + "\"");
        }

        // -------------------------------------------------------
        // Step 5: Compare original and reversed using equals()
        // -------------------------------------------------------
        System.out.println();
        System.out.println("--- Comparison ---");
        System.out.println("  Original : \"" + original + "\"");
        System.out.println("  Reversed : \"" + reversed + "\"");
        System.out.println();

        // -------------------------------------------------------
        // Step 6: Display final result
        // -------------------------------------------------------
        if (original.equals(reversed)) {
            System.out.println("Result: \"" + original + "\" IS a Palindrome.");
        } else {
            System.out.println("Result: \"" + original + "\" is NOT a Palindrome.");
        }

        // -------------------------------------------------------
        // Stack Concept Summary
        // -------------------------------------------------------
        System.out.println();
        System.out.println("--- Stack Concept Summary ---");
        System.out.println("  Data Structure : Stack<Character>");
        System.out.println("  Principle      : LIFO (Last In, First Out)");
        System.out.println("  Push Count     : " + original.length() + "  (one per character)");
        System.out.println("  Pop Count      : " + original.length() + "  (one per character)");
        System.out.println("  Reversal Logic : Stack naturally reverses element order");
        System.out.println("  Time Complexity: O(n)  — one pass to push, one pass to pop");
        System.out.println("  Space Complexity: O(n) — stack holds n characters");
        System.out.println();
        System.out.println("=====================================================");
        System.out.println("   Program exits successfully.");
        System.out.println("=====================================================");
    }
}
