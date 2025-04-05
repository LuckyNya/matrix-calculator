package com.yourpackage.operations;

public class MatrixTracer implements MatrixOperator {
    @Override public String getOperationName() { return "trace"; }
    
    @Override
    public double[][] operate(double[][] a, double[][] b) {
        if (a.length != a[0].length) {
            throw new IllegalArgumentException("Matrix must be square");
        }
        double trace = calculateTrace(a, 0);
        return new double[][]{{trace}}; // Return as 1x1 matrix
    }
    
    private double calculateTrace(double[][] matrix, int index) {
        if (index >= matrix.length) return 0;
        return matrix[index][index] + calculateTrace(matrix, index + 1);
    }
}