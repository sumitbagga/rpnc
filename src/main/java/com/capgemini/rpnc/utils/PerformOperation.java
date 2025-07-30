package com.capgemini.rpnc.utils;

public interface PerformOperation {
    double apply(double... operands);
    int arity(); // helpful to know how many operands are expected
}
