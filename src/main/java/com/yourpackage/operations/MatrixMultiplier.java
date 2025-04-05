package com.yourpackage.operations;

public class MatrixMultiplier implements MatrixOperator {
    @Override
    public String getOperationName() {
        return "multiplication";
    }

    @Override
    public double[][] operate(double[][] a, double[][] b) {
        if (a[0].length != b.length) {
            throw new IllegalArgumentException(
                "For " + getOperationName() + ", columns of first matrix (" + a[0].length + ") " +
                "must match rows of second matrix (" + b.length + ")"
            );
        }
        
        double[][] result = new double[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                for (int k = 0; k < b.length; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }
}