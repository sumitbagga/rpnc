package com.capgemini.rpnc;

import ch.qos.logback.core.util.StringUtil;
import io.micrometer.common.util.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Set;

@SpringBootApplication
public class RpncApplication {
    final Set<String> BINARY_OPERATORS = Set.of("+", "-", "*", "/", "avg", "mod");
    final Set<String> UNARY_OPERATORS = Set.of("sqrt", "sin", "cos");

    public static void main(String[] args) {
        SpringApplication.run(RpncApplication.class, args);
    }

    public String calculate(String rpnEquation) {

        if (rpnEquation == null) return "";

        String[] tokens = rpnEquation.trim().split("\\s+");
        Deque<Double> stack = new ArrayDeque<>();

        if(tokens.length<=1){
         return  rpnEquation + " - Not a valid equation";
        }
        boolean isTokenHasValidOperation = isTokenHasValidOperation(tokens);
        if (!isTokenHasValidOperation){
            return rpnEquation + " - Valid operation not found in equation";
        }

        boolean isTokenHasValidNumber = isTokenHasValidNumber(tokens);
        if (!isTokenHasValidNumber){
            return rpnEquation + " - Not a valid equation";
        }

        try {
            for (String token : tokens) {
                if (BINARY_OPERATORS.contains(token)) {
                    if (stack.size() < 2) return rpnEquation + " - Not Reverse Polish Notation try backwards";
                    double b = stack.pop();
                    double a = stack.pop();
                    switch (token) {
                        case "+" -> stack.push(a + b);
                        case "-" -> stack.push(a - b);
                        case "*" -> stack.push(a * b);
                        case "/" -> stack.push(a / b);
                        case "avg" -> stack.push((a + b) / 2);
                        case "mod" -> stack.push(a % b);
                    }
                } else if (UNARY_OPERATORS.contains(token)) {
                    if (stack.isEmpty()) return rpnEquation + " - Not Reverse Polish Notation try backwards";
                    double a = stack.pop();
                    switch (token) {
                        case "sqrt" -> stack.push(Math.sqrt(a));
                        case "sin" -> stack.push(Math.sin(a));
                        case "cos" -> stack.push(Math.cos(a));
                    }
                } else {
                    try {
                        stack.push(Double.parseDouble(token));
                    } catch (NumberFormatException e) {
                        return rpnEquation + " - Not a valid equation";
                    }
                }
            }
            return stack.size() == 1 ? String.valueOf(stack.pop()) : rpnEquation + " - Not Reverse Polish Notation try backwards";
        } catch (Exception e) {
            return rpnEquation + " - Not Reverse Polish Notation try backwards";
        }
    }

    private boolean isTokenHasValidNumber(String[] tokens) {
        return Arrays.stream(tokens).anyMatch(NumberUtils::isCreatable);
    }

    private boolean isTokenHasValidOperation(String[] tokens) {
        return Arrays.stream(tokens).anyMatch(token -> (BINARY_OPERATORS.contains(token) || UNARY_OPERATORS.contains(token)));
    }
}

