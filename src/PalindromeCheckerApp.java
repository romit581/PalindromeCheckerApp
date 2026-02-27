
public class PalindromeCheckerApp {
    static class Node {
        char data;   // Character stored in this node
        Node next;   // Reference (pointer) to the next node

        /**
         * Constructor to create a new Node with given character data.
         * next is null by default — no successor yet.
         *
         * @param data Character to store in this node
         */
        Node(char data) {
            this.data = data;
            this.next = null;
        }
    }

    static Node buildLinkedList(String s) {
        if (s == null || s.isEmpty()) return null;

        Node head = new Node(s.charAt(0));   // First node (head)
        Node current = head;

        for (int i = 1; i < s.length(); i++) {
            current.next = new Node(s.charAt(i));  // Link new node
            current = current.next;                 // Advance pointer
        }

        return head;
    }

    static void printList(Node head, String label) {
        System.out.print("  " + label + ": ");
        Node current = head;
        while (current != null) {
            System.out.print("[" + current.data + "]");
            if (current.next != null) System.out.print(" → ");
            current = current.next;
        }
        System.out.println(" → null");
    }

    static Node reverseList(Node head) {
        Node prev    = null;
        Node current = head;

        while (current != null) {
            Node next    = current.next;  // Save next node
            current.next = prev;          // Reverse the link
            prev         = current;       // Move prev forward
            current      = next;          // Move current forward
        }

        return prev;  // prev is now the new head
    }
    public static void main(String[] args) {

        // -------------------------------------------------------
        // Step 1: Declare hardcoded String and build Linked List
        // -------------------------------------------------------
        String original = "racecar";

        System.out.println("=====================================================");
        System.out.println("   Palindrome Checker Management System - UC8");
        System.out.println("   Method: Singly Linked List + Fast/Slow Pointer");
        System.out.println("=====================================================");
        System.out.println();
        System.out.println("Input String : \"" + original + "\"");
        System.out.println();

        // Build the linked list from the input string
        Node head = buildLinkedList(original);

        System.out.println("--- Phase 1: Build Singly Linked List ---");
        printList(head, "Full List");
        System.out.println();
        System.out.println("  Each character = one Node");
        System.out.println("  Each Node.next  = reference to next Node");
        System.out.println("  Last Node.next  = null (end of list)");
        System.out.println();

        // -------------------------------------------------------
        // Step 2: Fast & Slow Pointer — Find the Middle
        //
        // slow pointer moves 1 step at a time.
        // fast pointer moves 2 steps at a time.
        //
        // When fast reaches the end, slow is at the MIDDLE.
        // This works because fast covers 2x the distance.
        //
        // Visual for "racecar" (length 7):
        //   Start:   slow=[r] fast=[r]
        //   Step 1:  slow=[a] fast=[c]
        //   Step 2:  slow=[c] fast=[e]
        //   Step 3:  slow=[e] fast=null (fast.next.next = null)
        //   → slow is at the middle node 'e' (index 3)
        //
        // For even-length strings, slow lands just past center.
        // -------------------------------------------------------
        System.out.println("--- Phase 2: Fast & Slow Pointer (Find Middle) ---");
        System.out.println();
        System.out.println("  slow moves 1 step  →  reaches middle");
        System.out.println("  fast moves 2 steps →  reaches end");
        System.out.println();

        Node slow = head;
        Node fast = head;
        int  stepCount = 0;

        System.out.printf("  %-8s | %-8s | %-8s%n", "Step", "slow", "fast");
        System.out.println("  ---------|----------|----------");
        System.out.printf("  %-8s | %-8s | %-8s%n", "Start",
                "[" + slow.data + "]", "[" + fast.data + "]");

        while (fast != null && fast.next != null) {
            slow = slow.next;               // Advance slow by 1
            fast = fast.next.next;          // Advance fast by 2
            stepCount++;
            System.out.printf("  %-8d | %-8s | %-8s%n",
                    stepCount,
                    "[" + slow.data + "]",
                    fast != null ? "[" + fast.data + "]" : "null");
        }

        System.out.println();
        System.out.println("  → Middle node found: [" + slow.data + "]");
        System.out.println();

        // -------------------------------------------------------
        // Step 3: In-Place Reversal of Second Half
        //
        // slow.next is the start of the second half.
        // We reverse everything from slow.next onwards.
        // The first half remains untouched.
        //
        // Before reversal:  [r]→[a]→[c]→[e]→[c]→[a]→[r]→null
        //                                   ↑
        //                                 (slow = middle)
        // Second half start: slow.next = [c]→[a]→[r]→null
        // After reversal:    secondHead = [r]→[a]→[c]→null
        // -------------------------------------------------------
        System.out.println("--- Phase 3: In-Place Reversal of Second Half ---");

        Node secondHalfHead = slow.next;   // Start of second half

        System.out.print("  Second half before reversal: ");
        Node temp = secondHalfHead;
        while (temp != null) {
            System.out.print("[" + temp.data + "]");
            if (temp.next != null) System.out.print(" → ");
            temp = temp.next;
        }
        System.out.println(" → null");

        // Reverse the second half in-place
        Node reversedSecondHead = reverseList(secondHalfHead);

        System.out.print("  Second half after  reversal: ");
        temp = reversedSecondHead;
        while (temp != null) {
            System.out.print("[" + temp.data + "]");
            if (temp.next != null) System.out.print(" → ");
            temp = temp.next;
        }
        System.out.println(" → null");
        System.out.println();
        System.out.println("  No new nodes created — only next pointers rewired.");
        System.out.println();

        // -------------------------------------------------------
        // Step 4: Compare First Half with Reversed Second Half
        //
        // Walk both halves simultaneously.
        // first  → starts at head (original first half)
        // second → starts at reversedSecondHead
        //
        // Stop when second half is exhausted.
        // (For odd-length strings, the middle node is skipped
        //  because it has no counterpart in the second half.)
        // -------------------------------------------------------
        System.out.println("--- Phase 4: Node-by-Node Comparison ---");
        System.out.println();
        System.out.printf("  %-6s | %-14s | %-14s | %s%n",
                "Step", "First Half", "Second Half", "Match?");
        System.out.println("  -------|----------------|----------------|--------");

        Node first  = head;
        Node second = reversedSecondHead;
        boolean isPalindrome = true;
        int compStep = 1;

        while (second != null) {
            System.out.printf("  %-6d | first  → '[%c]'  | second → '[%c]'  | %s%n",
                    compStep,
                    first.data,
                    second.data,
                    first.data == second.data ? "✓ YES" : "✗ NO  ← Mismatch!");

            if (first.data != second.data) {
                isPalindrome = false;
                break;
            }

            // Node Traversal: advance both pointers via .next reference
            first  = first.next;
            second = second.next;
            compStep++;
        }

        // -------------------------------------------------------
        // Step 5: Display final result
        // -------------------------------------------------------
        System.out.println();
        System.out.println("--- Result ---");
        if (isPalindrome) {
            System.out.println("Result: \"" + original + "\" IS a Palindrome.");
            System.out.println("  → All first-half nodes matched reversed second-half nodes.");
        } else {
            System.out.println("Result: \"" + original + "\" is NOT a Palindrome.");
            System.out.println("  → A node value mismatch was detected.");
        }

        // -------------------------------------------------------
        // Step 6: Concept Summary
        // -------------------------------------------------------
        System.out.println();
        System.out.println("--- Key Concept Summary ---");
        System.out.println();
        System.out.printf("  %-28s | %s%n", "Concept", "Details");
        System.out.println("  -----------------------------|--------------------------------------------");
        System.out.printf("  %-28s | %s%n", "Singly Linked List",
                "Nodes connected via .next references");
        System.out.printf("  %-28s | %s%n", "Node Traversal",
                "current = current.next to move forward");
        System.out.printf("  %-28s | %s%n", "Fast & Slow Pointer",
                "fast moves 2x, slow moves 1x → finds middle");
        System.out.printf("  %-28s | %s%n", "In-Place Reversal",
                "Rewires .next pointers; no new nodes created");
        System.out.printf("  %-28s | %s%n", "Time  Complexity",
                "O(n) — three linear passes over the list");
        System.out.printf("  %-28s | %s%n", "Space Complexity",
                "O(1) — only pointer variables, no extra structures");
        System.out.println();
        System.out.println("=====================================================");
        System.out.println("   Program exits successfully.");
        System.out.println("=====================================================");
    }
}
