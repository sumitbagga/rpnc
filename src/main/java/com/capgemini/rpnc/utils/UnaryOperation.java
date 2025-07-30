package com.capgemini.rpnc.utils;

import java.util.function.Function;

public class UnaryOperation implements PerformOperation {
    private final Function<Double, Double> operation;

    public UnaryOperation(Function<Double, Double> operation) {
        this.operation = operation;
    }

    @Override
    public double apply(double... operands) {
        return operation.apply(operands[0]);
    }

    @Override
    public int arity() {
        return 1;
    }
}

