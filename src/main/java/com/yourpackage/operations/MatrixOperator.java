package com.yourpackage.operations;

public interface MatrixOperator {
    double[][] operate(double[][] a, double[][] b) throws IllegalArgumentException;
    String getOperationName();
}