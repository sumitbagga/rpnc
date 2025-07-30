package com.capgemini.rpnc.utils;

import java.util.function.BiFunction;

public class BinaryOperation implements PerformOperation {
    private final BiFunction<Double, Double, Double> operation;

    public BinaryOperation(BiFunction<Double, Double, Double> operation) {
        this.operation = operation;
    }

    @Override
    public double apply(double... operands) {
        return operation.apply(operands[0], operands[1]);
    }

    @Override
    public int arity() {
        return 2;
    }
}

