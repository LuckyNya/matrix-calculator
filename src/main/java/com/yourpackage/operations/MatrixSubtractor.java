package com.yourpackage.operations;

public class MatrixSubtractor implements MatrixOperator {
    @Override
    public String getOperationName() {
        return "subtraction";
    }

    @Override
    public double[][] operate(double[][] a, double[][] b) {
        if (a.length != b.length || a[0].length != b[0].length) {
            throw new IllegalArgumentException(
                "Ma trận phải có cùng chiều"
            );
        }
        
        double[][] result = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                result[i][j] = a[i][j] - b[i][j];
            }
        }
        return result;
    }
}