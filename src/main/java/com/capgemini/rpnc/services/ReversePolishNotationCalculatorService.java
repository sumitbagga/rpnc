package com.capgemini.rpnc.services;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Set;

@Service
public class ReversePolishNotationCalculatorService {
    private static final Set<String> BINARY_OPERATORS = Set.of("+", "-", "*", "/", "avg", "mod");
    private static final Set<String> UNARY_OPERATORS = Set.of("sqrt", "sin", "cos");

    public String calculate(String equation) {
        if (equation == null) return "";

        String[] tokens = equation.trim().split("\\s+");

        String validationMessage = validateTokens(equation, tokens);

        if (validationMessage != null) {
            return validationMessage;
        }

        ArrayDeque<Double> stack = new ArrayDeque<>();

        for (String token : tokens) {
            if (BINARY_OPERATORS.contains(token)) {
                if (stack.size() < 2) {
                    return equation + " - Not Reverse Polish Notation try backwards";
                }
                double b = stack.pop();
                double a = stack.pop();
                stack.push(switch (token) {
                    case "+" -> a + b;
                    case "-" -> a - b;
                    case "*" -> a * b;
                    case "/" -> a / b;
                    case "avg" -> (a + b) / 2;
                    case "mod" -> a % b;
                    default -> throw new IllegalStateException("Unexpected value: " + token);
                });
            } else if (UNARY_OPERATORS.contains(token)) {
                if (stack.isEmpty()){
                    return equation + " - Not Reverse Polish Notation try backwards";
                }
                double a = stack.pop();
                stack.push(switch (token) {
                    case "sqrt" -> Math.sqrt(a);
                    case "sin" -> Math.sin(a);
                    case "cos" -> Math.cos(a);
                    default -> throw new IllegalStateException("Unexpected value: " + token);
                });
            } else {
                try {
                    stack.push(Double.parseDouble(token));
                } catch (NumberFormatException e) {
                    return equation + " is Not a valid equation";
                }
            }
        }
        return String.valueOf(stack.pop());

    }

    private String validateTokens(String expression, String[] tokens) {
        if (tokens.length <= 1) {
            return expression + " is Not a valid equation";
        } else if (!containsValidOperator(tokens)) {
            return expression + " - Valid operation not found in equation";
        } else if (!containsValidNumber(tokens)) {
            return  "Not found any number in equation";
        }
        return null;
    }

    private boolean containsValidNumber(String[] tokens) {
        return Arrays.stream(tokens).anyMatch(NumberUtils::isCreatable);
    }

    private boolean containsValidOperator(String[] tokens) {
        return Arrays.stream(tokens).anyMatch(token -> BINARY_OPERATORS.contains(token) || UNARY_OPERATORS.contains(token));
    }
}
