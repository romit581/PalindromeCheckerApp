import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

// =====================================================
// INTERFACE: PalindromeStrategy
// =====================================================
// Defines the CONTRACT that every palindrome algorithm
// must fulfil. Any class that implements this interface
// MUST provide a concrete implementation of check().
//
// Interface Key Points:
//   - Declared with the 'interface' keyword
//   - Methods are implicitly public and abstract
//   - No instance fields (only constants allowed)
//   - A class implements an interface with 'implements'
//   - Enables polymorphism: one reference type, many behaviours
//
// By coding to the interface (not the implementation),
// PalindromeContext can work with ANY strategy without
// knowing which concrete class is behind the reference.
// =====================================================
interface PalindromeStrategy {

    boolean check(String cleaned);
    String getName();
    String getDataStructure();
}


// =====================================================
// CONCRETE STRATEGY 1: TwoPointerStrategy
// =====================================================
// Implements PalindromeStrategy using the Two-Pointer
// technique on a char[] (from UC4).
// Data Structure: char[]
// =====================================================
class TwoPointerStrategy implements PalindromeStrategy {

    @Override
    public boolean check(String cleaned) {
        // Convert to char[] for index-based access
        char[] chars = cleaned.toCharArray();
        int left  = 0;
        int right = chars.length - 1;

        while (left < right) {
            if (chars[left] != chars[right]) return false;
            left++;
            right--;
        }
        return true;
    }

    @Override
    public String getName() { return "Two-Pointer Strategy"; }

    @Override
    public String getDataStructure() { return "char[]"; }
}


// =====================================================
// CONCRETE STRATEGY 2: StackStrategy
// =====================================================
// Implements PalindromeStrategy using a Stack (from UC5).
// Pushes all characters, pops to reverse, then compares.
// Data Structure: Stack<Character> (LIFO)
// =====================================================
class StackStrategy implements PalindromeStrategy {

    @Override
    public boolean check(String cleaned) {
        Stack<Character> stack = new Stack<>();

        // Push all characters onto the stack
        for (char c : cleaned.toCharArray()) {
            stack.push(c);
        }

        // Pop to build reversed string (LIFO reversal)
        StringBuilder reversed = new StringBuilder();
        while (!stack.isEmpty()) {
            reversed.append(stack.pop());
        }

        return cleaned.equals(reversed.toString());
    }

    @Override
    public String getName() { return "Stack Strategy"; }

    @Override
    public String getDataStructure() { return "Stack<Character> (LIFO)"; }
}


// =====================================================
// CONCRETE STRATEGY 3: DequeStrategy
// =====================================================
// Implements PalindromeStrategy using a Deque (from UC7).
// Compares front and rear elements simultaneously.
// Data Structure: Deque<Character> (Double Ended Queue)
// =====================================================
class DequeStrategy implements PalindromeStrategy {

    @Override
    public boolean check(String cleaned) {
        Deque<Character> deque = new ArrayDeque<>();

        // Load all characters into the deque
        for (char c : cleaned.toCharArray()) {
            deque.offerLast(c);
        }

        // Compare front and rear, moving inward
        while (deque.size() > 1) {
            if (deque.pollFirst() != deque.pollLast()) return false;
        }

        return true;
    }

    @Override
    public String getName() { return "Deque Strategy"; }

    @Override
    public String getDataStructure() { return "Deque<Character> (Double Ended Queue)"; }
}


// =====================================================
// CONCRETE STRATEGY 4: RecursiveStrategy
// =====================================================
// Implements PalindromeStrategy using Recursion (UC9).
// Delegates to a private recursive helper method.
// Data Structure: Call Stack (implicit)
// =====================================================
class RecursiveStrategy implements PalindromeStrategy {

    @Override
    public boolean check(String cleaned) {
        return recurse(cleaned, 0, cleaned.length() - 1);
    }

    // Private recursive helper — hidden from interface consumers
    private boolean recurse(String s, int start, int end) {
        if (start >= end) return true;
        if (s.charAt(start) != s.charAt(end)) return false;
        return recurse(s, start + 1, end - 1);
    }

    @Override
    public String getName() { return "Recursive Strategy"; }

    @Override
    public String getDataStructure() { return "Call Stack (implicit recursion)"; }
}


class PalindromeContext {

    // -------------------------------------------------------
    // Private field typed to the INTERFACE, not a concrete class.
    // This is what enables polymorphism — any implementing class
    // can be assigned here and will respond to check() correctly.
    // -------------------------------------------------------
    private PalindromeStrategy strategy;


    PalindromeContext(PalindromeStrategy strategy) {
        this.strategy = strategy;
    }


    public void setStrategy(PalindromeStrategy strategy) {
        this.strategy = strategy;
    }


    public String getCurrentStrategyName() {
        return strategy.getName();
    }

    public String execute(String raw) {
        // Preprocessing (same pipeline as UC10)
        String cleaned = raw.toLowerCase().replaceAll("[^a-z0-9]", "");

        // Polymorphic dispatch — the JVM decides at runtime which
        // concrete check() to call based on the actual object type
        boolean result = strategy.check(cleaned);

        return String.format(
                "  Input     : \"%s\"%n" +
                        "  Cleaned   : \"%s\"%n" +
                        "  Strategy  : %s%n" +
                        "  Structure : %s%n" +
                        "  Result    : %s",
                raw,
                cleaned,
                strategy.getName(),
                strategy.getDataStructure(),
                result ? "✓ IS a Palindrome" : "✗ NOT a Palindrome"
        );
    }
}


// =====================================================
// MAIN CLASS: UseCase12PalindromeCheckerApp
// =====================================================
public class PalindromeCheckerApp {


    public static void main(String[] args) {

        System.out.println("=====================================================");
        System.out.println("   Palindrome Checker Management System - UC12");
        System.out.println("   Pattern : Strategy Design Pattern");
        System.out.println("=====================================================");
        System.out.println();

        // -------------------------------------------------------
        // Strategy Pattern Structure Overview
        // -------------------------------------------------------
        System.out.println("--- Strategy Pattern Structure ---");
        System.out.println();
        System.out.printf("  %-30s | %-22s | %s%n",
                "Class / Interface", "Role", "Data Structure");
        System.out.println("  -------------------------------|------------------------|---------------------------");
        System.out.printf("  %-30s | %-22s | %s%n",
                "PalindromeStrategy (interface)", "Contract / Abstraction", "N/A");
        System.out.printf("  %-30s | %-22s | %s%n",
                "TwoPointerStrategy", "Concrete Strategy", "char[]");
        System.out.printf("  %-30s | %-22s | %s%n",
                "StackStrategy", "Concrete Strategy", "Stack<Character>");
        System.out.printf("  %-30s | %-22s | %s%n",
                "DequeStrategy", "Concrete Strategy", "Deque<Character>");
        System.out.printf("  %-30s | %-22s | %s%n",
                "RecursiveStrategy", "Concrete Strategy", "Call Stack");
        System.out.printf("  %-30s | %-22s | %s%n",
                "PalindromeContext", "Context (uses strategy)", "Via strategy field");
        System.out.println();

        // -------------------------------------------------------
        // Interface & Polymorphism Explanation
        // -------------------------------------------------------
        System.out.println("--- Interface + Polymorphism ---");
        System.out.println();
        System.out.println("  All strategies implement PalindromeStrategy:");
        System.out.println("  PalindromeStrategy s;");
        System.out.println("  s = new TwoPointerStrategy(); → s.check() uses char[]");
        System.out.println("  s = new StackStrategy();      → s.check() uses Stack");
        System.out.println("  s = new DequeStrategy();      → s.check() uses Deque");
        System.out.println("  s = new RecursiveStrategy();  → s.check() uses recursion");
        System.out.println("  Same reference 's', four different runtime behaviours.");
        System.out.println("  This is Polymorphism.");
        System.out.println();

        // -------------------------------------------------------
        // Demo 1: Create context with initial strategy
        // -------------------------------------------------------
        System.out.println("--- Demo 1: Initial Strategy (Two-Pointer) ---");
        System.out.println();

        PalindromeContext context = new PalindromeContext(new TwoPointerStrategy());
        System.out.println(context.execute("racecar"));
        System.out.println();

        // -------------------------------------------------------
        // Demo 2: Runtime strategy swap — setStrategy()
        // The context object doesn't change; only the strategy does.
        // -------------------------------------------------------
        System.out.println("--- Demo 2: Runtime Strategy Swap ---");
        System.out.println();

        String[] testPhrases = {
                "A man a plan a canal Panama",
                "Was it a car or a cat I saw?",
                "Hello World"
        };

        PalindromeStrategy[] allStrategies = {
                new TwoPointerStrategy(),
                new StackStrategy(),
                new DequeStrategy(),
                new RecursiveStrategy()
        };

        for (String phrase : testPhrases) {
            System.out.println("  ┌── Input: \"" + phrase + "\"");
            for (PalindromeStrategy strat : allStrategies) {
                // Polymorphic strategy injection at runtime
                context.setStrategy(strat);
                String cleaned = phrase.toLowerCase().replaceAll("[^a-z0-9]", "");
                boolean result = strat.check(cleaned);
                System.out.printf("  │  %-28s [%-38s] → %s%n",
                        strat.getName(),
                        strat.getDataStructure(),
                        result ? "✓ Palindrome" : "✗ Not Palindrome");
            }
            System.out.println("  └──");
            System.out.println();
        }

        // -------------------------------------------------------
        // Demo 3: Pure Polymorphism demonstration
        // Same interface reference, four concrete types
        // -------------------------------------------------------
        System.out.println("--- Demo 3: Polymorphism in Action ---");
        System.out.println();
        System.out.println("  String testWord = \"level\";");
        System.out.println("  PalindromeStrategy strategy;  // interface reference");
        System.out.println();

        String testWord  = "level";
        PalindromeStrategy strategy;  // Typed to INTERFACE

        strategy = new TwoPointerStrategy();
        System.out.println("  strategy = new TwoPointerStrategy();");
        System.out.printf("  strategy.check(\"%s\") → %b  (%s)%n",
                testWord, strategy.check(testWord), strategy.getDataStructure());
        System.out.println();

        strategy = new StackStrategy();
        System.out.println("  strategy = new StackStrategy();");
        System.out.printf("  strategy.check(\"%s\") → %b  (%s)%n",
                testWord, strategy.check(testWord), strategy.getDataStructure());
        System.out.println();

        strategy = new DequeStrategy();
        System.out.println("  strategy = new DequeStrategy();");
        System.out.printf("  strategy.check(\"%s\") → %b  (%s)%n",
                testWord, strategy.check(testWord), strategy.getDataStructure());
        System.out.println();

        strategy = new RecursiveStrategy();
        System.out.println("  strategy = new RecursiveStrategy();");
        System.out.printf("  strategy.check(\"%s\") → %b  (%s)%n",
                testWord, strategy.check(testWord), strategy.getDataStructure());

        // -------------------------------------------------------
        // Key Concept Summary
        // -------------------------------------------------------
        System.out.println();
        System.out.println("--- Key Concept Summary ---");
        System.out.println();
        System.out.printf("  %-24s | %s%n", "Concept", "How It Is Applied");
        System.out.println("  -------------------------|------------------------------------------------------");
        System.out.printf("  %-24s | %s%n", "Interface",
                "PalindromeStrategy defines check(), getName(), getDataStructure()");
        System.out.printf("  %-24s | %s%n", "Polymorphism",
                "One interface reference holds any concrete strategy; JVM dispatches");
        System.out.printf("  %-24s | %s%n", "Strategy Pattern",
                "Algorithm family encapsulated per class; swapped via setStrategy()");
        System.out.printf("  %-24s | %s%n", "Encapsulation",
                "Each strategy hides its own internal data structure and logic");
        System.out.printf("  %-24s | %s%n", "Open/Closed Principle",
                "Add new algorithm = new class only; Context never modified");
        System.out.printf("  %-24s | %s%n", "Dependency Injection",
                "Strategy passed into Context via constructor or setStrategy()");
        System.out.printf("  %-24s | %s%n", "Runtime Flexibility",
                "Algorithm chosen dynamically; no if-else chains in the Context");
        System.out.println();
        System.out.println("=====================================================");
        System.out.println("   Program exits successfully.");
        System.out.println("=====================================================");
    }
}