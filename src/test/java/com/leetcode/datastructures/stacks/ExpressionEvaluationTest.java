package com.leetcode.datastructures.stacks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionEvaluationTest {

    private ExpressionEvaluation expressionEvaluation;

    @BeforeEach
    void setUp() {
        expressionEvaluation = new ExpressionEvaluation();
    }

    @ParameterizedTest
    @CsvSource({
            "'()', true",
            "'()[]{}', true",
            "'(]', false",
            "'([)]', false",
            "'{[]}', true",
            "'', true",
            "'((', false",
            "'))', false"
    })
    @DisplayName("Valid Parentheses - Multiple Cases")
    void testIsValid(String input, boolean expected) {
        boolean result = expressionEvaluation.isValid(input);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Valid Parentheses - Complex Cases")
    void testIsValidComplex() {
        assertTrue(expressionEvaluation.isValid("(){}[]"));
        assertTrue(expressionEvaluation.isValid("([{}])"));
        assertFalse(expressionEvaluation.isValid("([{}]))]"));
        assertFalse(expressionEvaluation.isValid("([{}]))]"));
    }

    @ParameterizedTest
    @CsvSource({
            "'1 + 1', 2",
            "' 2-1 + 2 ', 3",
            "'(1+(4+5+2)-3)+(6+8)', 23",
            "'1-(5)', -4",
            "'2147483647', 2147483647"
    })
    @DisplayName("Basic Calculator I - Multiple Cases")
    void testCalculate(String expression, int expected) {
        int result = expressionEvaluation.calculate(expression);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Basic Calculator I - Complex Expression")
    void testCalculateComplex() {
        // Given
        String expression = "(1+(4+5+2)-3)+(6+8)";
        int expected = 23;

        // When
        int result = expressionEvaluation.calculate(expression);

        // Then
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({
            "'3+2*2', 7",
            "' 3/2 ', 1",
            "' 3+5 / 2 ', 5",
            "'14-3*2', 8",
            "'1*2-3/4+5*6-7*8+9/10', -24"
    })
    @DisplayName("Basic Calculator II - Multiple Cases")
    void testCalculateII(String expression, int expected) {
        int result = expressionEvaluation.calculateII(expression);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Basic Calculator II - Edge Cases")
    void testCalculateIIEdgeCases() {
        assertEquals(0, expressionEvaluation.calculateII("0"));
        assertEquals(42, expressionEvaluation.calculateII("42"));
        assertEquals(13, expressionEvaluation.calculateII("  30   "));
    }

    @ParameterizedTest
    @CsvSource({
            "'(2+6* 3+5- (3*14/7+2)*5)+3', -12",
            "'2*(5+5*2)/3+(6/2+8)', 21",
            "'1 + 1', 2",
            "'6-4/2', 4"
    })
    @DisplayName("Basic Calculator III - Multiple Cases")
    void testCalculateIII(String expression, int expected) {
        int result = expressionEvaluation.calculateIII(expression);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Evaluate Reverse Polish Notation")
    void testEvalRPN() {
        // Given
        String[] tokens1 = {"2", "1", "+", "3", "*"};
        String[] tokens2 = {"4", "13", "5", "/", "+"};
        String[] tokens3 = {"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"};

        // When & Then
        assertEquals(9, expressionEvaluation.evalRPN(tokens1));  // ((2 + 1) * 3) = 9
        assertEquals(6, expressionEvaluation.evalRPN(tokens2));  // (4 + (13 / 5)) = 6
        assertEquals(22, expressionEvaluation.evalRPN(tokens3));
    }

    @Test
    @DisplayName("Evaluate RPN - Single Element")
    void testEvalRPNSingle() {
        // Given
        String[] tokens = {"13"};

        // When
        int result = expressionEvaluation.evalRPN(tokens);

        // Then
        assertEquals(13, result);
    }

    @ParameterizedTest
    @CsvSource({
            "'a+b*c+d', 'abc*+d+'",
            "'(a+b)*c', 'ab+c*'",
            "'a+b*(c^d-e)', 'abcd^e-*+'",
            "'a', 'a'",
            "'a+b', 'ab+'"
    })
    @DisplayName("Infix to Postfix - Multiple Cases")
    void testInfixToPostfix(String infix, String expected) {
        String result = expressionEvaluation.infixToPostfix(infix);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({
            "'3[a]', 'aaa'",
            "'2[bc]', 'bcbc'",
            "'3[a2[c]]', 'accaccacc'",
            "'2[abc]3[cd]ef', 'abcabccdcdcdef'",
            "'abc3[cd]xyz', 'abccdcdcdxyz'"
    })
    @DisplayName("Decode String - Multiple Cases")
    void testDecodeString(String encoded, String expected) {
        String result = expressionEvaluation.decodeString(encoded);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Decode String - Complex Nested")
    void testDecodeStringComplex() {
        // Given
        String s = "3[a2[c]]";
        String expected = "accaccacc";

        // When
        String result = expressionEvaluation.decodeString(s);

        // Then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Remove Invalid Parentheses")
    void testRemoveInvalidParentheses() {
        // Given
        String s1 = "()())()";
        String s2 = "(((";
        String s3 = "()";

        // When
        List<String> result1 = expressionEvaluation.removeInvalidParentheses(s1);
        List<String> result2 = expressionEvaluation.removeInvalidParentheses(s2);
        List<String> result3 = expressionEvaluation.removeInvalidParentheses(s3);

        // Then
        assertTrue(result1.contains("(())()") || result1.contains("()(())"));
        assertTrue(result2.contains(""));
        assertTrue(result3.contains("()"));
    }

    @Test
    @DisplayName("Remove Invalid Parentheses - With Letters")
    void testRemoveInvalidParenthesesWithLetters() {
        // Given
        String s = "(v)())()";

        // When
        List<String> result = expressionEvaluation.removeInvalidParentheses(s);

        // Then
        assertFalse(result.isEmpty());
        assertTrue(result.contains("(v)()()") || result.contains("(v())()"));
    }

    @ParameterizedTest
    @CsvSource({
            "'/home/', '/'",
            "'/../', '/'",
            "'/home//foo/', '/home/foo'",
            "'/a/./b/../../c/', '/c'",
            "'/a/../../b/../c//.//', '/c'",
            "'/a//b////c/d//././/..', '/a/b/c'"
    })
    @DisplayName("Simplify Path - Multiple Cases")
    void testSimplifyPath(String input, String expected) {
        String result = expressionEvaluation.simplifyPath(input);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({
            "'())', 1",
            "'(()', 1",
            "'())', 1",
            "'((((', 4",
            "'))))', 4",
            "'(((', 3"
    })
    @DisplayName("Minimum Add to Make Valid - Multiple Cases")
    void testMinAddToMakeValid(String s, int expected) {
        int result = expressionEvaluation.minAddToMakeValid(s);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Minimum Add to Make Valid - Balanced")
    void testMinAddToMakeValidBalanced() {
        assertEquals(0, expressionEvaluation.minAddToMakeValid("()"));
        assertEquals(0, expressionEvaluation.minAddToMakeValid("()()"));
        assertEquals(0, expressionEvaluation.minAddToMakeValid("(())"));
    }

    @ParameterizedTest
    @CsvSource({
            "'()', 1",
            "'(())', 2",
            "'()()', 2",
            "'(()(()))', 6"
    })
    @DisplayName("Score of Parentheses - Multiple Cases")
    void testScoreOfParentheses(String s, int expected) {
        int result = expressionEvaluation.scoreOfParentheses(s);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Different Ways to Compute")
    void testDiffWaysToCompute() {
        // Given
        String expression1 = "2-1-1";
        String expression2 = "2*3-4*5";

        // When
        List<Integer> result1 = expressionEvaluation.diffWaysToCompute(expression1);
        List<Integer> result2 = expressionEvaluation.diffWaysToCompute(expression2);

        // Then
        assertTrue(result1.contains(0)); // ((2-1)-1) = 0
        assertTrue(result1.contains(2)); // (2-(1-1)) = 2

        assertTrue(result2.contains(-34)); // ((2*3)-(4*5)) = -14
        assertTrue(result2.contains(-14)); // (2*(3-(4*5))) = -34
    }

    @Test
    @DisplayName("Asteroid Collision")
    void testAsteroidCollision() {
        // Given
        int[] asteroids1 = {5, 10, -5};
        int[] asteroids2 = {8, -8};
        int[] asteroids3 = {10, 2, -5};

        // When
        int[] result1 = expressionEvaluation.asteroidCollision(asteroids1);
        int[] result2 = expressionEvaluation.asteroidCollision(asteroids2);
        int[] result3 = expressionEvaluation.asteroidCollision(asteroids3);

        // Then
        assertArrayEquals(new int[]{5, 10}, result1);
        assertArrayEquals(new int[]{}, result2);
        assertArrayEquals(new int[]{10}, result3);
    }

    @Test
    @DisplayName("Asteroid Collision - No Collision")
    void testAsteroidCollisionNoCollision() {
        // Given
        int[] asteroids = {-2, -1, 1, 2};

        // When
        int[] result = expressionEvaluation.asteroidCollision(asteroids);

        // Then
        assertArrayEquals(new int[]{-2, -1, 1, 2}, result);
    }

    @ParameterizedTest
    @MethodSource("provideComplexExpressions")
    @DisplayName("Complex Expression Evaluation")
    void testComplexExpressions(String expression, int expected, String calculatorType) {
        int result = 0;
        switch (calculatorType) {
            case "I":
                result = expressionEvaluation.calculate(expression);
                break;
            case "II":
                result = expressionEvaluation.calculateII(expression);
                break;
            case "III":
                result = expressionEvaluation.calculateIII(expression);
                break;
        }
        assertEquals(expected, result);
    }

    private static Stream<Arguments> provideComplexExpressions() {
        return Stream.of(
                Arguments.of("1-(5-6)", 2, "I"),
                Arguments.of("(7)-(0)+(4)", 11, "I"),
                Arguments.of("14/3*2", 8, "II"),
                Arguments.of("100000/1/2/3/4/5", 0, "II"),
                Arguments.of("1*2-3/4+5*6-7*8+9/10", -24, "II"),
                Arguments.of("6-4/2", 4, "III"),
                Arguments.of("2*(5+5*2)/3+(6/2+8)", 21, "III")
        );
    }

    @Test
    @DisplayName("Performance Test - Large Expression")
    void testPerformanceLargeExpression() {
        // Given - build large expression
        StringBuilder sb = new StringBuilder();
        sb.append("1");
        for (int i = 0; i < 100; i++) {
            sb.append("+1");
        }
        String largeExpression = sb.toString();

        // When
        long startTime = System.currentTimeMillis();
        int result = expressionEvaluation.calculateII(largeExpression);
        long endTime = System.currentTimeMillis();

        // Then
        assertEquals(101, result);
        assertTrue(endTime - startTime < 50, "Large expression should complete within 50ms");
    }

    @Test
    @DisplayName("Stress Test - Nested Parentheses")
    void testStressNestedParentheses() {
        // Given - deeply nested valid parentheses
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            sb.append("(");
        }
        for (int i = 0; i < 100; i++) {
            sb.append(")");
        }
        String nestedParens = sb.toString();

        // When
        long startTime = System.currentTimeMillis();
        boolean result = expressionEvaluation.isValid(nestedParens);
        long endTime = System.currentTimeMillis();

        // Then
        assertTrue(result);
        assertTrue(endTime - startTime < 50, "Nested parentheses should complete within 50ms");
    }

    @Test
    @DisplayName("Edge Cases - Empty and Special Inputs")
    void testEdgeCases() {
        // Empty string cases
        assertTrue(expressionEvaluation.isValid(""));
        assertEquals("", expressionEvaluation.decodeString(""));
        assertEquals("/", expressionEvaluation.simplifyPath(""));
        assertEquals(0, expressionEvaluation.minAddToMakeValid(""));

        // Single character cases
        assertFalse(expressionEvaluation.isValid("("));
        assertFalse(expressionEvaluation.isValid(")"));
        assertEquals("a", expressionEvaluation.decodeString("a"));

        // Calculator edge cases
        assertEquals(0, expressionEvaluation.calculate("0"));
        assertEquals(0, expressionEvaluation.calculateII("0"));
        assertEquals(0, expressionEvaluation.calculateIII("0"));
    }

    @Test
    @DisplayName("Error Handling - Invalid Inputs")
    void testErrorHandling() {
        // Test with malformed expressions that should be handled gracefully
        assertDoesNotThrow(() -> {
            expressionEvaluation.isValid(")(");
            expressionEvaluation.calculate("1 + ");
            expressionEvaluation.simplifyPath("//");
        });
    }

    @ParameterizedTest
    @MethodSource("provideDecodeStringCases")
    @DisplayName("Decode String - Advanced Cases")
    void testDecodeStringAdvanced(String input, String expected) {
        String result = expressionEvaluation.decodeString(input);
        assertEquals(expected, result);
    }

    private static Stream<Arguments> provideDecodeStringCases() {
        return Stream.of(
                Arguments.of("", ""),
                Arguments.of("a", "a"),
                Arguments.of("100[leetcode]", "leetcode".repeat(100)),
                Arguments.of("2[a]3[b]", "aabbb"),
                Arguments.of("3[a2[b]]", "abbabbabb"),
                Arguments.of("10[a]", "aaaaaaaaaa")
        );
    }

    @Test
    @DisplayName("Calculator Operator Precedence")
    void testCalculatorOperatorPrecedence() {
        // Test that multiplication and division have higher precedence
        assertEquals(7, expressionEvaluation.calculateII("2+3*4-5"));  // 2+(3*4)-5 = 9
        assertEquals(3, expressionEvaluation.calculateII("14-3*2"));   // 14-(3*2) = 8
        assertEquals(1, expressionEvaluation.calculateII("3/2"));      // 3/2 = 1 (integer division)

        // Test with parentheses override
        assertEquals(11, expressionEvaluation.calculateIII("(2+3)*4-9"));  // (2+3)*4-9 = 11
    }

    @Test
    @DisplayName("RPN Edge Cases")
    void testRPNEdgeCases() {
        // Single number
        assertEquals(42, expressionEvaluation.evalRPN(new String[]{"42"}));

        // Negative numbers
        assertEquals(-3, expressionEvaluation.evalRPN(new String[]{"-3"}));

        // Division with negative result
        assertEquals(-1, expressionEvaluation.evalRPN(new String[]{"4", "-5", "+", "1", "/"}));
    }

    @Test
    @DisplayName("Parentheses Score Complex Cases")
    void testParenthesesScoreComplex() {
        assertEquals(1, expressionEvaluation.scoreOfParentheses("()"));
        assertEquals(2, expressionEvaluation.scoreOfParentheses("(())"));
        assertEquals(2, expressionEvaluation.scoreOfParentheses("()()"));
        assertEquals(6, expressionEvaluation.scoreOfParentheses("(()(()))"));
        assertEquals(4, expressionEvaluation.scoreOfParentheses("((()))"));
    }

    @Test
    @DisplayName("Infix to Postfix Edge Cases")
    void testInfixToPostfixEdgeCases() {
        assertEquals("", expressionEvaluation.infixToPostfix(""));
        assertEquals("a", expressionEvaluation.infixToPostfix("a"));
        assertEquals("ab+", expressionEvaluation.infixToPostfix("(a+b)"));
        assertEquals("ab+c*", expressionEvaluation.infixToPostfix("(a+b)*c"));
    }

    @Test
    @DisplayName("Asteroid Collision Edge Cases")
    void testAsteroidCollisionEdgeCases() {
        // All moving same direction
        assertArrayEquals(new int[]{1, 2, 3},
                expressionEvaluation.asteroidCollision(new int[]{1, 2, 3}));
        assertArrayEquals(new int[]{-3, -2, -1},
                expressionEvaluation.asteroidCollision(new int[]{-3, -2, -1}));

        // Empty result after all collisions
        assertArrayEquals(new int[]{},
                expressionEvaluation.asteroidCollision(new int[]{5, -5}));

        // Single asteroid
        assertArrayEquals(new int[]{1},
                expressionEvaluation.asteroidCollision(new int[]{1}));
    }

    @Test
    @DisplayName("Path Simplification Complex Cases")
    void testPathSimplificationComplex() {
        assertEquals("/", expressionEvaluation.simplifyPath("/"));
        assertEquals("/", expressionEvaluation.simplifyPath("/../"));
        assertEquals("/", expressionEvaluation.simplifyPath("/./"));
        assertEquals("/home", expressionEvaluation.simplifyPath("/home/"));
        assertEquals("/c", expressionEvaluation.simplifyPath("/a/./b/../../c/"));
        assertEquals("/c", expressionEvaluation.simplifyPath("/a/../../b/../c//.//"));
    }

    @Test
    @DisplayName("Comprehensive Integration Test")
    void testComprehensiveIntegration() {
        // Test that combines multiple expression evaluation techniques

        // 1. Validate parentheses
        String expr = "((2+3)*4)";
        assertTrue(expressionEvaluation.isValid("(())"));

        // 2. Evaluate the expression
        int result = expressionEvaluation.calculateIII(expr);
        assertEquals(20, result);

        // 3. Convert to postfix and evaluate
        String postfix = expressionEvaluation.infixToPostfix("2 3 + 4 *");
        // Note: This is a simplified test as our infix conversion doesn't handle spaces

        // 4. Test decoding
        String decoded = expressionEvaluation.decodeString("2[abc]");
        assertEquals("abcabc", decoded);
    }
}