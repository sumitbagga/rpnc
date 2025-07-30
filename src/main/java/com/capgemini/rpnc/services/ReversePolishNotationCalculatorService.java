package com.capgemini.rpnc.services;

import com.capgemini.rpnc.utils.BinaryOperation;
import com.capgemini.rpnc.utils.PerformOperation;
import com.capgemini.rpnc.utils.UnaryOperation;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Map;

@Service
public class ReversePolishNotationCalculatorService {

    private static final Map<String, PerformOperation> OPERATIONS = Map.of(
            "+", new BinaryOperation(Double::sum),
            "-", new BinaryOperation((a, b) -> a - b),
            "*", new BinaryOperation((a, b) -> a * b),
            "/", new BinaryOperation((a, b) -> a / b),
            "avg", new BinaryOperation((a, b) -> (a + b) / 2),
            "mod", new BinaryOperation((a, b) -> a % b),
            "sqrt", new UnaryOperation(Math::sqrt),
            "sin", new UnaryOperation(Math::sin),
            "cos", new UnaryOperation(Math::cos)
    );

    public String calculate(String equation) {
        if (equation == null) return "";

        String[] tokens = equation.trim().split("\\s+");

        String validationMessage = validateTokens(equation, tokens);
        if (validationMessage != null) {
            return validationMessage;
        }

        ArrayDeque<Double> stack = new ArrayDeque<>();

        for (String token: tokens) {
            PerformOperation op = OPERATIONS.get(token);
            if (op != null) {
                if (stack.size() < op.arity()) {
                    return error(equation, "Not Reverse Polish Notation try backwards");
                }
                double[] args = new double[op.arity()];
                for (int i = op.arity() - 1; i >= 0; i--) {
                    args[i] = stack.pop();
                }
                stack.push(op.apply(args));
            } else {
                try {
                    stack.push(Double.parseDouble(token));
                } catch (NumberFormatException e) {
                    return equation + " is not a valid equation";
                }
            }
        }

        return stack.size() == 1
                ? String.valueOf(stack.pop())
                : error(equation, "Not Reverse Polish Notation try backwards");
    }

    private String validateTokens(String expression, String[] tokens) {
        if (tokens.length <= 1) {
            return expression + " is not a valid equation";
        }
        if (Arrays.stream(tokens).noneMatch(OPERATIONS::containsKey)) {
            return expression + " - Valid operation not found in equation";
        }
        if (Arrays.stream(tokens).noneMatch(NumberUtils::isCreatable)) {
            return "Not found any number in the equation";
        }
        return null;
    }

    private String error(String equation, String message) {
        return equation + " - " + message;
    }
}
