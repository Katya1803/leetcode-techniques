package com.leetcode.datastructures.stacks;

import java.util.*;

/**
 * Expression Evaluation using Stack
 * Common use cases:
 * 1. Basic arithmetic expression evaluation
 * 2. Parentheses validation and matching
 * 3. Infix to Postfix conversion
 * 4. Calculator implementations
 * 5. Mathematical expression parsing
 */
public class ExpressionEvaluation {

    /**
     * Valid Parentheses - Check if string has valid parentheses
     * Time: O(n), Space: O(n)
     */
    public boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        Map<Character, Character> closeToOpen = Map.of(
                ')', '(',
                '}', '{',
                ']', '['
        );

        for (char c : s.toCharArray()) {
            if (closeToOpen.containsKey(c)) {
                // Closing bracket
                if (stack.isEmpty() || stack.pop() != closeToOpen.get(c)) {
                    return false;
                }
            } else {
                // Opening bracket
                stack.push(c);
            }
        }

        return stack.isEmpty();
    }

    /**
     * Basic Calculator I - Handle +, -, (, )
     * Time: O(n), Space: O(n)
     */
    public int calculate(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        int result = 0;
        int number = 0;
        int sign = 1;

        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                number = number * 10 + (c - '0');
            } else if (c == '+') {
                result += sign * number;
                number = 0;
                sign = 1;
            } else if (c == '-') {
                result += sign * number;
                number = 0;
                sign = -1;
            } else if (c == '(') {
                // Push current result and sign to stack
                stack.push(result);
                stack.push(sign);
                // Reset for new sub-expression
                result = 0;
                sign = 1;
            } else if (c == ')') {
                result += sign * number;
                number = 0;

                // Pop previous sign and result
                result *= stack.pop(); // Previous sign
                result += stack.pop(); // Previous result
            }
        }

        return result + sign * number;
    }

    /**
     * Basic Calculator II - Handle +, -, *, /
     * Time: O(n), Space: O(n)
     */
    public int calculateII(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        int number = 0;
        char operation = '+';

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (Character.isDigit(c)) {
                number = number * 10 + (c - '0');
            }

            if (!Character.isDigit(c) && c != ' ' || i == s.length() - 1) {
                switch (operation) {
                    case '+':
                        stack.push(number);
                        break;
                    case '-':
                        stack.push(-number);
                        break;
                    case '*':
                        stack.push(stack.pop() * number);
                        break;
                    case '/':
                        stack.push(stack.pop() / number);
                        break;
                }
                operation = c;
                number = 0;
            }
        }

        int result = 0;
        while (!stack.isEmpty()) {
            result += stack.pop();
        }

        return result;
    }

    /**
     * Basic Calculator III - Handle +, -, *, /, (, )
     * Time: O(n), Space: O(n)
     */
    public int calculateIII(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        Deque<Character> ops = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == ' ') continue;

            if (Character.isDigit(c)) {
                int number = 0;
                while (i < s.length() && Character.isDigit(s.charAt(i))) {
                    number = number * 10 + (s.charAt(i) - '0');
                    i++;
                }
                i--; // Adjust for loop increment
                stack.push(number);
            } else if (c == '(') {
                ops.push(c);
            } else if (c == ')') {
                while (!ops.isEmpty() && ops.peek() != '(') {
                    stack.push(applyOperation(ops.pop(), stack.pop(), stack.pop()));
                }
                ops.pop(); // Remove '('
            } else if (isOperator(c)) {
                while (!ops.isEmpty() && hasPrecedence(c, ops.peek())) {
                    stack.push(applyOperation(ops.pop(), stack.pop(), stack.pop()));
                }
                ops.push(c);
            }
        }

        while (!ops.isEmpty()) {
            stack.push(applyOperation(ops.pop(), stack.pop(), stack.pop()));
        }

        return stack.pop();
    }

    /**
     * Evaluate Reverse Polish Notation (Postfix)
     * Time: O(n), Space: O(n)
     */
    public int evalRPN(String[] tokens) {
        Deque<Integer> stack = new ArrayDeque<>();
        Set<String> operators = Set.of("+", "-", "*", "/");

        for (String token : tokens) {
            if (operators.contains(token)) {
                int b = stack.pop();
                int a = stack.pop();

                switch (token) {
                    case "+": stack.push(a + b); break;
                    case "-": stack.push(a - b); break;
                    case "*": stack.push(a * b); break;
                    case "/": stack.push(a / b); break;
                }
            } else {
                stack.push(Integer.parseInt(token));
            }
        }

        return stack.pop();
    }

    /**
     * Convert Infix to Postfix
     * Time: O(n), Space: O(n)
     */
    public String infixToPostfix(String infix) {
        StringBuilder result = new StringBuilder();
        Deque<Character> stack = new ArrayDeque<>();

        for (char c : infix.toCharArray()) {
            if (c == ' ') continue;

            if (Character.isLetterOrDigit(c)) {
                result.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop());
                }
                stack.pop(); // Remove '('
            } else if (isOperator(c)) {
                while (!stack.isEmpty() && hasPrecedence(c, stack.peek())) {
                    result.append(stack.pop());
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }

        return result.toString();
    }

    /**
     * Decode String - Handle nested encoded strings
     * Time: O(n), Space: O(n)
     */
    public String decodeString(String s) {
        Deque<Integer> countStack = new ArrayDeque<>();
        Deque<StringBuilder> stringStack = new ArrayDeque<>();
        StringBuilder currentString = new StringBuilder();
        int k = 0;

        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                k = k * 10 + (c - '0');
            } else if (c == '[') {
                countStack.push(k);
                stringStack.push(currentString);
                currentString = new StringBuilder();
                k = 0;
            } else if (c == ']') {
                StringBuilder decodedString = stringStack.pop();
                int currentK = countStack.pop();

                for (int i = 0; i < currentK; i++) {
                    decodedString.append(currentString);
                }

                currentString = decodedString;
            } else {
                currentString.append(c);
            }
        }

        return currentString.toString();
    }

    /**
     * Remove Invalid Parentheses - Find valid parentheses strings
     * Time: O(2^n), Space: O(n)
     */
    public List<String> removeInvalidParentheses(String s) {
        List<String> result = new ArrayList<>();
        if (s == null) return result;

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.offer(s);
        visited.add(s);
        boolean found = false;

        while (!queue.isEmpty()) {
            String str = queue.poll();

            if (isValidParentheses(str)) {
                result.add(str);
                found = true;
            }

            if (found) continue;

            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) != '(' && str.charAt(i) != ')') continue;

                String t = str.substring(0, i) + str.substring(i + 1);
                if (!visited.contains(t)) {
                    queue.offer(t);
                    visited.add(t);
                }
            }
        }

        return result;
    }

    /**
     * Simplify Path - Simplify Unix-style file paths
     * Time: O(n), Space: O(n)
     */
    public String simplifyPath(String path) {
        Deque<String> stack = new ArrayDeque<>();
        String[] components = path.split("/");

        for (String component : components) {
            if (component.equals("..")) {
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else if (!component.isEmpty() && !component.equals(".")) {
                stack.push(component);
            }
        }

        StringBuilder result = new StringBuilder();
        for (String dir : stack) {
            result.append("/").append(dir);
        }

        return result.length() > 0 ? result.toString() : "/";
    }

    /**
     * Minimum Add to Make Parentheses Valid
     * Time: O(n), Space: O(1)
     */
    public int minAddToMakeValid(String s) {
        int open = 0; // Count of unmatched '('
        int close = 0; // Count of unmatched ')'

        for (char c : s.toCharArray()) {
            if (c == '(') {
                open++;
            } else if (c == ')') {
                if (open > 0) {
                    open--;
                } else {
                    close++;
                }
            }
        }

        return open + close;
    }

    /**
     * Score of Parentheses
     * Time: O(n), Space: O(n)
     */
    public int scoreOfParentheses(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(0); // Initial score

        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(0);
            } else {
                int v = stack.pop();
                int w = stack.pop();
                stack.push(w + Math.max(2 * v, 1));
            }
        }

        return stack.pop();
    }

    // Helper methods
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) return false;
        return true;
    }

    private int applyOperation(char op, int b, int a) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return a / b;
        }
        return 0;
    }

    private boolean isValidParentheses(String s) {
        int count = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') count++;
            else if (c == ')') count--;
            if (count < 0) return false;
        }
        return count == 0;
    }

    /**
     * Different Ways to Add Parentheses
     * Time: O(4^n / n^(3/2)), Space: O(4^n / n^(3/2))
     */
    public List<Integer> diffWaysToCompute(String expression) {
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (c == '+' || c == '-' || c == '*') {
                String part1 = expression.substring(0, i);
                String part2 = expression.substring(i + 1);

                List<Integer> part1Result = diffWaysToCompute(part1);
                List<Integer> part2Result = diffWaysToCompute(part2);

                for (int p1 : part1Result) {
                    for (int p2 : part2Result) {
                        int c1 = 0;
                        switch (c) {
                            case '+': c1 = p1 + p2; break;
                            case '-': c1 = p1 - p2; break;
                            case '*': c1 = p1 * p2; break;
                        }
                        result.add(c1);
                    }
                }
            }
        }

        if (result.size() == 0) {
            result.add(Integer.valueOf(expression));
        }

        return result;
    }

    /**
     * Asteroid Collision
     * Time: O(n), Space: O(n)
     */
    public int[] asteroidCollision(int[] asteroids) {
        Deque<Integer> stack = new ArrayDeque<>();

        for (int asteroid : asteroids) {
            boolean exploded = false;

            while (!stack.isEmpty() && asteroid < 0 && stack.peek() > 0) {
                if (stack.peek() < -asteroid) {
                    stack.pop();
                    continue;
                } else if (stack.peek() == -asteroid) {
                    stack.pop();
                }
                exploded = true;
                break;
            }

            if (!exploded) {
                stack.push(asteroid);
            }
        }

        return stack.stream().mapToInt(Integer::intValue).toArray();
    }
}